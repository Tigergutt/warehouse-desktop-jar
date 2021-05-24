package se.melsom.warehouse.edit.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.model.EntityName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.Collection;

public class EditItemView extends JDialog {
	private static final Logger logger = LoggerFactory.getLogger(EditItemView.class);

	private JTextField itemNumberField;
	private JTextField itemNameField;
	private JComboBox<String> packagingField;
	private JButton cancelButton;
	private JButton saveButton;

	public EditItemView(JFrame parentFrame) {
		super(parentFrame, true);
		initialize();
		saveButton.addActionListener(this::handleSaveAction);
		cancelButton.addActionListener(this::cancelSaveAction);
	}

	private void cancelSaveAction(ActionEvent actionEvent) {
		dispose();
	}

	private void handleSaveAction(ActionEvent actionEvent) {
		dispose();
	}

	public void initialize() {
		logger.debug("Initialize.");
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

	public void setFocusOnItemNumber() {
		itemNumberField.requestFocus();
	}

    public String getItemNumber() {
		return itemNumberField.getText();
	}

    public void setItemNumber(String itemNumber) {
		itemNumberField.setText(itemNumber);
	}

    public void setItemNumberAction(String actionCommand, ActionListener actionListener, FocusListener focusListener) {
		itemNumberField.setActionCommand(actionCommand);
		itemNumberField.setName(actionCommand);
		itemNumberField.addActionListener(actionListener);
		itemNumberField.addFocusListener(focusListener);
	}

	public String getItemName() {
		return itemNameField.getText();
	}

    public void setItemName(String itemName) {
		itemNameField.setText(itemName);
	}

    public void setItemNameAction(String actionCommand, ActionListener actionListener, FocusListener focusListener) {
		itemNameField.setActionCommand(actionCommand);
		itemNameField.setName(actionCommand);
		itemNameField.addActionListener(actionListener);
		itemNameField.addFocusListener(focusListener);
	}

	public String getPackaging() {
		return (String) packagingField.getSelectedItem();
	}

    public void setPackaging(String packaging) {
		 packagingField.setSelectedItem(packaging);
	}

    public void setPackagingSelectorItems(Collection<String> packagingItemList) {
		packagingField.removeAllItems();
		
		for (String item : packagingItemList) {
			packagingField.addItem(item);
		}
	}

    public void setPackagingSelectorAction(String actionCommand, ActionListener actionListener) {
		packagingField.setActionCommand(actionCommand);
		packagingField.addActionListener(actionListener);
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
