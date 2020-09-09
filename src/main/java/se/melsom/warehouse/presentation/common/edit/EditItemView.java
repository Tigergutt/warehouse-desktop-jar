package se.melsom.warehouse.presentation.common.edit;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import se.melsom.warehouse.model.EntityName;

/**
 * The type Edit item view.
 */
@SuppressWarnings("serial")
public class EditItemView extends JDialog {
	private JTextField itemNumberField;
	private JTextField itemNameField;
	private JComboBox<String> packagingField;
	private JButton cancelButton;
	private JButton saveButton;

    /**
     * Instantiates a new Edit item view.
     *
     * @param parent the parent
     */
    public EditItemView(JFrame parent) {
		super(parent, true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Redigera artikel");
		getContentPane().setLayout(new BorderLayout());
		
		JPanel contentPanel = new JPanel();
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] {0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel itemNumberLabel = new JLabel(EntityName.ITEM_NUMBER);
		GridBagConstraints gbc_itemNumberLabel = new GridBagConstraints();
		gbc_itemNumberLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_itemNumberLabel.gridwidth = 3;
		gbc_itemNumberLabel.insets = new Insets(5, 5, 0, 0);
		gbc_itemNumberLabel.gridx = 0;
		gbc_itemNumberLabel.gridy = 0;
		contentPanel.add(itemNumberLabel, gbc_itemNumberLabel);
		
		itemNumberField = new JTextField();
		GridBagConstraints gbc_itemNumberField = new GridBagConstraints();
		gbc_itemNumberField.fill = GridBagConstraints.HORIZONTAL;
		gbc_itemNumberField.gridwidth = 3;
		gbc_itemNumberField.insets = new Insets(0, 5, 5, 0);
		gbc_itemNumberField.gridx = 0;
		gbc_itemNumberField.gridy = 1;
		contentPanel.add(itemNumberField, gbc_itemNumberField);
		itemNumberField.setColumns(10);
		
		JLabel itemNameLabel = new JLabel(EntityName.ITEM_NAME);
		GridBagConstraints gbc_itemNameLabel = new GridBagConstraints();
		gbc_itemNameLabel.anchor = GridBagConstraints.WEST;
		gbc_itemNameLabel.insets = new Insets(5, 5, 0, 5);
		gbc_itemNameLabel.gridx = 3;
		gbc_itemNameLabel.gridy = 0;
		contentPanel.add(itemNameLabel, gbc_itemNameLabel);
		
		itemNameField = new JTextField();
		GridBagConstraints gbc_itemNameField = new GridBagConstraints();
		gbc_itemNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_itemNameField.anchor = GridBagConstraints.WEST;
		gbc_itemNameField.insets = new Insets(0, 5, 5, 5);
		gbc_itemNameField.gridx = 3;
		gbc_itemNameField.gridy = 1;
		contentPanel.add(itemNameField, gbc_itemNameField);
		itemNameField.setColumns(20);
		
		JLabel packagingLabel = new JLabel(EntityName.ITEM_PACKAGING);
		GridBagConstraints gbc_packagingLabel = new GridBagConstraints();
		gbc_packagingLabel.gridwidth = 2;
		gbc_packagingLabel.anchor = GridBagConstraints.WEST;
		gbc_packagingLabel.insets = new Insets(5, 5, 0, 0);
		gbc_packagingLabel.gridx = 0;
		gbc_packagingLabel.gridy = 2;
		contentPanel.add(packagingLabel, gbc_packagingLabel);
		
		packagingField = new JComboBox<>();
		GridBagConstraints gbc_packagingField = new GridBagConstraints();
		gbc_packagingField.gridwidth = 3;
		gbc_packagingField.anchor = GridBagConstraints.NORTHWEST;
		gbc_packagingField.insets = new Insets(0, 5, 5, 0);
		gbc_packagingField.gridx = 0;
		gbc_packagingField.gridy = 3;
		contentPanel.add(packagingField, gbc_packagingField);
		
		
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
     * Sets focus on item number.
     */
    /*
	 * Item number field
	 */
	public void setFocusOnItemNumber() {
		itemNumberField.requestFocus();
	}

    /**
     * Gets item number.
     *
     * @return the item number
     */
    public String getItemNumber() {
		return itemNumberField.getText();
	}

    /**
     * Sets item number.
     *
     * @param itemNumber the item number
     */
    public void setItemNumber(String itemNumber) {
		itemNumberField.setText(itemNumber);
	}

    /**
     * Sets item number action.
     *
     * @param actionCommand  the action command
     * @param actionListener the action listener
     * @param focusListener  the focus listener
     */
    public void setItemNumberAction(String actionCommand, ActionListener actionListener, FocusListener focusListener) {
		itemNumberField.setActionCommand(actionCommand);
		itemNumberField.setName(actionCommand);
		itemNumberField.addActionListener(actionListener);
		itemNumberField.addFocusListener(focusListener);
	}

    /**
     * Gets item name.
     *
     * @return the item name
     */
    /*
	 * Item name field
	 */
	public String getItemName() {
		return itemNameField.getText();
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
     * Sets item name action.
     *
     * @param actionCommand  the action command
     * @param actionListener the action listener
     * @param focusListener  the focus listener
     */
    public void setItemNameAction(String actionCommand, ActionListener actionListener, FocusListener focusListener) {
		itemNameField.setActionCommand(actionCommand);
		itemNameField.setName(actionCommand);
		itemNameField.addActionListener(actionListener);
		itemNameField.addFocusListener(focusListener);
	}

    /**
     * Gets packaging.
     *
     * @return the packaging
     */
    /*
	 * Packaging fields
	 */
	public String getPackaging() {
		return (String) packagingField.getSelectedItem();
	}

    /**
     * Sets packaging.
     *
     * @param packaging the packaging
     */
    public void setPackaging(String packaging) {
		 packagingField.setSelectedItem(packaging);
	}

    /**
     * Sets packaging selector items.
     *
     * @param packagingItemList the packaging item list
     */
    public void setPackagingSelectorItems(Collection<String> packagingItemList) {
		packagingField.removeAllItems();
		
		for (String item : packagingItemList) {
			packagingField.addItem(item);
		}
	}

    /**
     * Sets packaging selector action.
     *
     * @param actionCommand  the action command
     * @param actionListener the action listener
     */
    public void setPackagingSelectorAction(String actionCommand, ActionListener actionListener) {
		packagingField.setActionCommand(actionCommand);
		packagingField.addActionListener(actionListener);
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
