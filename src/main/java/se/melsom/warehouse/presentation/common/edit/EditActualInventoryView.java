package se.melsom.warehouse.presentation.common.edit;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.EntityName;

/**
 * The type Edit actual inventory view.
 */
@SuppressWarnings("serial")
public class EditActualInventoryView extends JDialog {
	private static Logger logger = Logger.getLogger(EditActualInventoryView.class);
	
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

    /**
     * Instantiates a new Edit actual inventory view.
     *
     * @param parent the parent
     */
    public EditActualInventoryView(JFrame parent) {
		super(parent, true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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


    /**
     * Sets item number.
     *
     * @param itemNumber the item number
     */
    /*
	 * Item fields
	 */
	public void setItemNumber(String itemNumber) {
		itemNumberField.setText(itemNumber);
	}

    /**
     * Sets item name.
     *
     * @param itemName the item name
     */
    public void setItemName(String itemName) {
		itemNameField.setText(itemName);
	}

    /**
     * Sets item selector listener.
     *
     * @param actionCommand  the action command
     * @param actionListener the action listener
     */
    public void setItemSelectorListener(String actionCommand, ActionListener actionListener) {
		selectItemButton.setActionCommand(actionCommand);
		selectItemButton.addActionListener(actionListener);
	}

    /**
     * Gets location section.
     *
     * @return the location section
     */
    /*
	 * Location fields
	 */
	public String getLocationSection() {
		return (String) locationSectionField.getSelectedItem();
	}

    /**
     * Sets section selector items.
     *
     * @param locationSections the location sections
     */
    public void setSectionSelectorItems(Set<String> locationSections) {
		locationSectionField.removeAllItems();
		
		for (String item : locationSections) {
			locationSectionField.addItem(item);
		}
	}

    /**
     * Sets location section field listener.
     *
     * @param actionCommand  the action command
     * @param actionListener the action listener
     * @param focusListener  the focus listener
     */
    public void setLocationSectionFieldListener(String actionCommand, ActionListener actionListener, FocusListener focusListener) {
		locationSectionField.setActionCommand(actionCommand);
		locationSectionField.addActionListener(actionListener);
		locationSectionField.addFocusListener(focusListener);
	}

    /**
     * Gets location slot.
     *
     * @return the location slot
     */
    public String getLocationSlot() {
		return (String) locationSlotField.getSelectedItem();
	}

    /**
     * Sets selected location section.
     *
     * @param section the section
     */
    public void setSelectedLocationSection(String section) {
		locationSectionField.setSelectedItem(section);
	}

    /**
     * Sets selected location slot.
     *
     * @param meterCount the meter count
     */
    public void setSelectedLocationSlot(String meterCount) {
		locationSlotField.setSelectedItem(meterCount);
	}

    /**
     * Sets slot selector items.
     *
     * @param locationSlots the location slots
     */
    public void setSlotSelectorItems(Set<String> locationSlots) {
		locationSlotField.removeAllItems();
		
		for (String item : locationSlots) {
			locationSlotField.addItem(item);
		}
	}

    /**
     * Sets location slot field listener.
     *
     * @param actionCommand  the action command
     * @param actionListener the action listener
     * @param focusListener  the focus listener
     */
    public void setLocationSlotFieldListener(String actionCommand, ActionListener actionListener, FocusListener focusListener) {
		locationSlotField.setActionCommand(actionCommand);
		locationSlotField.addActionListener(actionListener);
		locationSlotField.addFocusListener(focusListener);
	}

    /**
     * Gets identity.
     *
     * @return the identity
     */
    /*
	 * Identity fields
	 */
	public String getIdentity() {
		return identityField.getText();
	}

    /**
     * Sets identity.
     *
     * @param identity the identity
     */
    public void setIdentity(String identity) {
		identityField.setText(identity);
	}

    /**
     * Sets identity field listener.
     *
     * @param actionCommand  the action command
     * @param actionListener the action listener
     * @param focusListener  the focus listener
     */
    public void setIdentityFieldListener(String actionCommand, ActionListener actionListener, FocusListener focusListener) {
		identityField.setActionCommand(actionCommand);
		identityField.addActionListener(actionListener);
		identityField.addFocusListener(focusListener);
	}

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    /*
	 * Quantity field
	 */
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

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
		quantityField.setText("" + quantity);
	}

    /**
     * Sets quantity field listener.
     *
     * @param actionCommand  the action command
     * @param actionListener the action listener
     * @param focusListener  the focus listener
     */
    public void setQuantityFieldListener(String actionCommand, ActionListener actionListener, FocusListener focusListener) {
		quantityField.setActionCommand(actionCommand);
		quantityField.addActionListener(actionListener);
		quantityField.addFocusListener(focusListener);
	}

    /**
     * Sets save button enabled.
     *
     * @param isEnabled the is enabled
     */
    /*
	 * Buttons
	 */
	public void setSaveButtonEnabled(boolean isEnabled) {
		saveButton.setEnabled(isEnabled);
	}

    /**
     * Sets cancel button listener.
     *
     * @param actionCommand  the action command
     * @param actionListener the action listener
     */
    public void setCancelButtonListener(String actionCommand, ActionListener actionListener) {
		cancelButton.setActionCommand(actionCommand);
		cancelButton.addActionListener(actionListener);
	}

    /**
     * Sets save button listener.
     *
     * @param actionCommand  the action command
     * @param actionListener the action listener
     */
    public void setSaveButtonListener(String actionCommand, ActionListener actionListener) {
		saveButton.setActionCommand(actionCommand);
		saveButton.addActionListener(actionListener);
	}

}
