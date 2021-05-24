package se.melsom.warehouse.edit.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.model.EntityName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.Vector;


public class EditItemApplicationView extends JDialog {
	private static final Logger logger = LoggerFactory.getLogger(EditItemApplicationView.class);
	
	private JTextField itemNumberField;
	private JTextField itemNameField;
	private JButton selectItemButton;
	private JComboBox<String> categoryField;
	private JButton cancelButton;
	private JButton saveButton;
	private JTextField quantityField;
	private JLabel quantityLabel;

	protected EditItemApplicationView(JFrame parentFrame) {
		super(parentFrame, true);
		initializeView();
	}

	public void initializeView() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Redigera användning");
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
		gbc_itemNumberLabel.gridwidth = 2;
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
		
		quantityLabel = new JLabel(EntityName.INVENTORY_NOMINAL_QUANTITY);
		GridBagConstraints gbc_quantityLabel = new GridBagConstraints();
		gbc_quantityLabel.anchor = GridBagConstraints.WEST;
		gbc_quantityLabel.insets = new Insets(5, 5, 0, 0);
		gbc_quantityLabel.gridx = 0;
		gbc_quantityLabel.gridy = 2;
		contentPanel.add(quantityLabel, gbc_quantityLabel);
		
		JLabel locationSectionLabel = new JLabel("Kategori");
		GridBagConstraints gbc_locationSectionLabel = new GridBagConstraints();
		gbc_locationSectionLabel.anchor = GridBagConstraints.WEST;
		gbc_locationSectionLabel.insets = new Insets(5, 5, 0, 0);
		gbc_locationSectionLabel.gridx = 1;
		gbc_locationSectionLabel.gridy = 2;
		contentPanel.add(locationSectionLabel, gbc_locationSectionLabel);
		
		quantityField = new JTextField();
		GridBagConstraints gbc_quantityField = new GridBagConstraints();
		gbc_quantityField.anchor = GridBagConstraints.WEST;
		gbc_quantityField.insets = new Insets(0, 5, 5, 0);
		gbc_quantityField.gridx = 0;
		gbc_quantityField.gridy = 3;
		contentPanel.add(quantityField, gbc_quantityField);
		quantityField.setColumns(5);
		
		categoryField = new JComboBox<>();
		GridBagConstraints gbc_locationSectionField = new GridBagConstraints();
		gbc_locationSectionField.gridwidth = 2;
		gbc_locationSectionField.anchor = GridBagConstraints.NORTHWEST;
		gbc_locationSectionField.insets = new Insets(0, 5, 5, 0);
		gbc_locationSectionField.gridx = 1;
		gbc_locationSectionField.gridy = 3;
		contentPanel.add(categoryField, gbc_locationSectionField);
				
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

	public void setItemNumber(String itemNumber) {
		itemNumberField.setText(itemNumber);
	}

    public void setItemName(String itemName) {
		itemNameField.setText(itemName);
	}

    public void setItemSelectorListener(String actionCommand, ActionListener actionListener) {
		selectItemButton.setActionCommand(actionCommand);
		selectItemButton.addActionListener(actionListener);
	}

	public String getSelectedCategory() {
		return (String) categoryField.getSelectedItem();
	}

    public void setSelectedCategory(String section) {
		categoryField.setSelectedItem(section);
	}

    public void setCategoryItems(Vector<String> locationSections) {
		categoryField.removeAllItems();
		
		for (String item : locationSections) {
			categoryField.addItem(item);
		}
	}

    public void setCategoryFieldListener(String actionCommand, ActionListener actionListener, FocusListener focusListener) {
		categoryField.setActionCommand(actionCommand);
		categoryField.addActionListener(actionListener);
		categoryField.addFocusListener(focusListener);
	}

	public int getQuantity() {
		try {
			return Integer.parseInt(quantityField.getText());
		} catch (Exception e) {
			logger.warn("Not a number quantity=" + quantityField.getText(), e);
		}
		
		int quantity = 1;
		
		setQuantity(quantity);

		return quantity;
	}

    public void setQuantity(int quantity) {
		quantityField.setText("" + quantity);
	}

    public void setQuantityFieldListener(String actionCommand, ActionListener actionListener, FocusListener focusListener) {
		quantityField.setActionCommand(actionCommand);
		quantityField.addActionListener(actionListener);
		quantityField.addFocusListener(focusListener);
	}

	public void setSaveButtonEnabled(boolean isEnabled) {
		saveButton.setEnabled(isEnabled);
	}

    public void setCancelButtonListener(String actionCommand, ActionListener actionListener) {
		cancelButton.setActionCommand(actionCommand);
		cancelButton.addActionListener(actionListener);
	}

    public void setSaveButtonListener(String actionCommand, ActionListener actionListener) {
		saveButton.setActionCommand(actionCommand);
		saveButton.addActionListener(actionListener);
	}
}
