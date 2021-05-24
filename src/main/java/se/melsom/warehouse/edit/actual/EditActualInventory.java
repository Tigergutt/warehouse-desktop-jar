package se.melsom.warehouse.edit.actual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.common.selection.SelectItem;
import se.melsom.warehouse.common.selection.SelectItemListener;
import se.melsom.warehouse.data.service.ItemService;
import se.melsom.warehouse.data.service.StockLocationService;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.data.vo.ItemVO;
import se.melsom.warehouse.data.vo.StockLocationVO;
import se.melsom.warehouse.model.EntityName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Set;
import java.util.TreeSet;

public class EditActualInventory extends JDialog implements SelectItemListener {
	private static final Logger logger = LoggerFactory.getLogger(EditActualInventory.class);

	private JTextField itemNumberField;
	private JTextField itemNameField;
	private JButton selectItemButton;
	private JComboBox<String> locationSectionField;
	private JComboBox<String> locationSlotField;
	private JTextField identityField;
	private JButton cancelButton;
	private JButton saveButton;
	private JTextField quantityField;
	private JLabel quantityLabel;

	private boolean isUpdating = false;
	private final EditActualInventoryListener editActualInventoryListener;
	private final StockLocationService stockLocationService;
	private final ItemService itemService;
	private final JFrame parentFrame;
	private ActualInventoryVO actualInventoryVO = null;

	public EditActualInventory(EditActualInventoryListener editActualInventoryListener, StockLocationService stockLocationService, ItemService itemService, JFrame parentFrame) {
		super(parentFrame, true);

		this.editActualInventoryListener = editActualInventoryListener;
		this.stockLocationService = stockLocationService;
		this.itemService = itemService;
		this.parentFrame = parentFrame;

		initializeView();
		initializeListeners();
	}

	public void editInventory(ActualInventoryVO actualInventoryVO) {
		setActualInventory(actualInventoryVO);
		setVisible(true);
	}

	public void setActualInventory(ActualInventoryVO actualInventoryVO) {
		this.actualInventoryVO = actualInventoryVO;

		if (actualInventoryVO.getItem() != null) {
			setItemNumber(actualInventoryVO.getItem().getNumber());
			setItemName(actualInventoryVO.getItem().getName());
		}

		if (actualInventoryVO.getStockLocation() != null) {
			setCurrentSection(actualInventoryVO.getStockLocation().getSection());
			setCurrentSlot(actualInventoryVO.getStockLocation().getSlot());
		}

		Set<String> locationSections = new TreeSet<>();

		for (StockLocationVO location : stockLocationService.getStockLocations()) {
			locationSections.add(location.getSection());
		}

		setSections(locationSections);
	}

	public void updateQuantity(String quantity) {
		if (actualInventoryVO == null) {
			return;
		}

		try {
			actualInventoryVO.setQuantity(Integer.parseInt(quantity));
		} catch (NumberFormatException e) {
			actualInventoryVO.setQuantity(0);
		}

		setSaveEnabled(actualInventoryVO.isValid());
	}

	public void updateIdentity(String identity) {
		if (actualInventoryVO == null) {
			return;
		}

		actualInventoryVO.setIdentity(identity);
		setSaveEnabled(actualInventoryVO.isValid());
	}

	private void handleSelectItemAction(ActionEvent actionEvent) {
		if (isUpdating) {
			return;
		}

		SelectItem selector = new SelectItem(this, this);
		selector.selectItem(itemService.getItems());
	}

	@Override
	public void handleSelectedItem(ItemVO selectedItem) {
		logger.trace("Selected item=" + selectedItem);

		if (selectedItem != null) {
			logger.debug("Selected item=" + selectedItem);
			actualInventoryVO.setItem(selectedItem);
			setItemNumber(actualInventoryVO.getItem().getNumber());
			setItemName(actualInventoryVO.getItem().getName());
		}
	}

	private void handleSelectSectionAction(ActionEvent actionEvent) {
		if (isUpdating) {
			return;
		}

		String section = (String) locationSectionField.getSelectedItem();
		Set<String> locationSlots = new TreeSet<>();

		for (StockLocationVO location : stockLocationService.getStockLocations()) {
			if (location.getSection().equals(section)) {
				locationSlots.add(location.getSlot());
			}
		}

		setSlots(locationSlots);
		setSaveEnabled(actualInventoryVO != null && actualInventoryVO.isValid());
	}

	private void handleSelectSlotAction(ActionEvent actionEvent) {
		String section = (String) locationSlotField.getSelectedItem();
		String slot = (String) locationSlotField.getSelectedItem();
		StockLocationVO location = stockLocationService.getStockLocation(section, slot);

		if (location != null) {
			actualInventoryVO.setStockLocation(location);
		}

		setSaveEnabled(actualInventoryVO != null && actualInventoryVO.isValid());
	}

	private void handleSaveAction(ActionEvent actionEvent) {
		dispose();
	}

	private void handleCancelAction(ActionEvent actionEvent) {
		dispose();
	}

	public void initializeView() {
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setTitle("Redigera individ");
		getContentPane().setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel itemNumberLabel = new JLabel(EntityName.ITEM_NUMBER);
		GridBagConstraints gbc_itemNumberLabel = new GridBagConstraints();
		gbc_itemNumberLabel.anchor = GridBagConstraints.WEST;
		gbc_itemNumberLabel.insets = new Insets(5, 5, 0, 0);
		gbc_itemNumberLabel.gridx = 0;
		gbc_itemNumberLabel.gridy = 0;
		contentPanel.add(itemNumberLabel, gbc_itemNumberLabel);
		
		itemNumberField = new JTextField();
		itemNumberField.setEditable(false);
		GridBagConstraints gbc_itemNumberField = new GridBagConstraints();
		gbc_itemNumberField.gridwidth = 2;
		gbc_itemNumberField.anchor = GridBagConstraints.WEST;
		gbc_itemNumberField.insets = new Insets(0, 5, 0, 0);
		gbc_itemNumberField.gridx = 0;
		gbc_itemNumberField.gridy = 1;
		contentPanel.add(itemNumberField, gbc_itemNumberField);
		itemNumberField.setColumns(10);
		
		JLabel itemNameLabel = new JLabel(EntityName.ITEM_NAME);
		GridBagConstraints gbc_itemNameLabel = new GridBagConstraints();
		gbc_itemNameLabel.anchor = GridBagConstraints.WEST;
		gbc_itemNameLabel.insets = new Insets(5, 5, 0, 0);
		gbc_itemNameLabel.gridx = 2;
		gbc_itemNameLabel.gridy = 0;
		contentPanel.add(itemNameLabel, gbc_itemNameLabel);
		
		itemNameField = new JTextField();
		itemNameField.setEditable(false);
		GridBagConstraints gbc_itemNameField = new GridBagConstraints();
		gbc_itemNameField.anchor = GridBagConstraints.WEST;
		gbc_itemNameField.gridwidth = 3;
		gbc_itemNameField.insets = new Insets(0, 5, 0, 0);
		gbc_itemNameField.gridx = 2;
		gbc_itemNameField.gridy = 1;
		contentPanel.add(itemNameField, gbc_itemNameField);
		itemNameField.setColumns(20);
		
		selectItemButton = new JButton("...");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.gridx = 5;
		gbc_btnNewButton.gridy = 1;
		contentPanel.add(selectItemButton, gbc_btnNewButton);
		
		quantityLabel = new JLabel(EntityName.INVENTORY_ACTUAL_QUANTITY);
		GridBagConstraints gbc_quantityLabel = new GridBagConstraints();
		gbc_quantityLabel.anchor = GridBagConstraints.WEST;
		gbc_quantityLabel.insets = new Insets(5, 5, 0, 0);
		gbc_quantityLabel.gridx = 0;
		gbc_quantityLabel.gridy = 2;
		contentPanel.add(quantityLabel, gbc_quantityLabel);
		
		JLabel identityLabel = new JLabel(EntityName.INVENTORY_IDENTITY);
		GridBagConstraints gbc_identityLabel = new GridBagConstraints();
		gbc_identityLabel.anchor = GridBagConstraints.WEST;
		gbc_identityLabel.insets = new Insets(5, 5, 0, 0);
		gbc_identityLabel.gridx = 1;
		gbc_identityLabel.gridy = 2;
		contentPanel.add(identityLabel, gbc_identityLabel);
		
		JLabel locationSectionLabel = new JLabel(EntityName.STOCK_LOCATION_DESIGNATION_SECTION);
		GridBagConstraints gbc_locationSectionLabel = new GridBagConstraints();
		gbc_locationSectionLabel.anchor = GridBagConstraints.WEST;
		gbc_locationSectionLabel.insets = new Insets(5, 5, 0, 0);
		gbc_locationSectionLabel.gridx = 3;
		gbc_locationSectionLabel.gridy = 2;
		contentPanel.add(locationSectionLabel, gbc_locationSectionLabel);
		
		JLabel locationSlotLabel = new JLabel(EntityName.STOCK_LOCATION_DESIGNATION_SLOT);
		GridBagConstraints gbc_locationSlotLabel = new GridBagConstraints();
		gbc_locationSlotLabel.anchor = GridBagConstraints.WEST;
		gbc_locationSlotLabel.insets = new Insets(5, 5, 0, 0);
		gbc_locationSlotLabel.gridx = 4;
		gbc_locationSlotLabel.gridy = 2;
		contentPanel.add(locationSlotLabel, gbc_locationSlotLabel);
		
		quantityField = new JTextField();
		GridBagConstraints gbc_quantityField = new GridBagConstraints();
		gbc_quantityField.anchor = GridBagConstraints.WEST;
		gbc_quantityField.insets = new Insets(0, 5, 5, 0);
		gbc_quantityField.gridx = 0;
		gbc_quantityField.gridy = 3;
		contentPanel.add(quantityField, gbc_quantityField);
		quantityField.setColumns(5);
		
		identityField = new JTextField();
		GridBagConstraints gbc_identityField = new GridBagConstraints();
		gbc_identityField.gridwidth = 2;
		gbc_identityField.insets = new Insets(0, 5, 5, 0);
		gbc_identityField.anchor = GridBagConstraints.NORTHWEST;
		gbc_identityField.gridx = 1;
		gbc_identityField.gridy = 3;
		contentPanel.add(identityField, gbc_identityField);
		identityField.setColumns(8);
		
		locationSectionField = new JComboBox<>();
		GridBagConstraints gbc_locationSectionField = new GridBagConstraints();
		gbc_locationSectionField.anchor = GridBagConstraints.NORTHWEST;
		gbc_locationSectionField.insets = new Insets(0, 5, 5, 0);
		gbc_locationSectionField.gridx = 3;
		gbc_locationSectionField.gridy = 3;
		contentPanel.add(locationSectionField, gbc_locationSectionField);
		
		locationSlotField = new JComboBox<>();
		GridBagConstraints gbc_locationSlotField = new GridBagConstraints();
		gbc_locationSlotField.anchor = GridBagConstraints.WEST;
		gbc_locationSlotField.insets = new Insets(0, 5, 5, 5);
		gbc_locationSlotField.gridx = 4;
		gbc_locationSlotField.gridy = 3;
		contentPanel.add(locationSlotField, gbc_locationSlotField);
				
		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		FlowLayout flowLayout = (FlowLayout) buttonPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		
		saveButton = new JButton("Spara");
		buttonPanel.add(saveButton);
		
		cancelButton = new JButton("Avbryt");
		buttonPanel.add(cancelButton);
		pack();
	}

	private void initializeListeners() {
		quantityField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				updateQuantity(quantityField.getText());
			}
		});
		identityField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				updateIdentity(identityField.getText());
			}
		});
		selectItemButton.addActionListener(this::handleSelectItemAction);
		locationSectionField.addActionListener(this::handleSelectSectionAction);
		locationSlotField.addActionListener(this::handleSelectSlotAction);
		saveButton.addActionListener(this::handleSaveAction);
		cancelButton.addActionListener(this::handleCancelAction);
	}

	public void setSections(Set<String> sections) {
		locationSectionField.removeAllItems();
		isUpdating = true;
		for (String item : sections) {
			locationSectionField.addItem(item);
		}
		isUpdating = false;
	}

	public void setCurrentSection(String section) {
		locationSectionField.setSelectedItem(section);
	}

	public void setSlots(Set<String> slots) {
		locationSlotField.removeAllItems();
		isUpdating = true;
		for (String item : slots) {
			locationSlotField.addItem(item);
		}
		isUpdating = false;
	}

	public void setCurrentSlot(String slot) {
		locationSlotField.setSelectedItem(slot);
	}

	public void setItemNumber(String number) {
		itemNumberField.setText(number);
	}

	public void setItemName(String name) {
		itemNameField.setText(name);
	}

	public void setQuantity(int quantity) {
		quantityField.setText(String.format("%d", quantity));
	}

	public void setIdentity(String identity) {
		identityField.setText(identity);
	}

	public void setSaveEnabled(boolean isEnabled) {
		saveButton.setEnabled(isEnabled);
	}
}
