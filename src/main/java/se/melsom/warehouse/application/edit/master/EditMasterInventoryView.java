package se.melsom.warehouse.application.edit.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.model.EntityName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

public class EditMasterInventoryView extends JDialog {
	private static final Logger logger = LoggerFactory.getLogger(EditMasterInventoryView.class);
	private final JTextField itemNumberField;
	private final JTextField itemNameField;
	private final JButton selectItemButton;
	private final JTextField identityField;
	private final JButton cancelButton;
	private final JButton saveButton;
	private final JTextField sourceField;
	private final JLabel quantityLabel;
	private final JTextField quantityField;

    public EditMasterInventoryView(JFrame parent) {
		super(parent, true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Redigera individ");
		getContentPane().setLayout(new BorderLayout());
		
		JPanel contentPanel = new JPanel();
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] {0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_itemNumberField.anchor = GridBagConstraints.WEST;
		gbc_itemNumberField.gridwidth = 2;
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
		gbc_itemNameField.gridwidth = 2;
		gbc_itemNameField.insets = new Insets(0, 5, 0, 0);
		gbc_itemNameField.gridx = 2;
		gbc_itemNameField.gridy = 1;
		contentPanel.add(itemNameField, gbc_itemNameField);
		itemNameField.setColumns(20);
		
		selectItemButton = new JButton("...");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 1;
		contentPanel.add(selectItemButton, gbc_btnNewButton);
		
		quantityLabel = new JLabel(EntityName.INVENTORY_NOMINAL_QUANTITY);
		GridBagConstraints gbc_quantityLabel = new GridBagConstraints();
		gbc_quantityLabel.anchor = GridBagConstraints.WEST;
		gbc_quantityLabel.insets = new Insets(5, 5, 0, 0);
		gbc_quantityLabel.gridx = 0;
		gbc_quantityLabel.gridy = 2;
		contentPanel.add(quantityLabel, gbc_quantityLabel);
		
		JLabel sourceLabel = new JLabel(EntityName.INVENTORY_SOURCE);
		GridBagConstraints gbc_sourceLabel = new GridBagConstraints();
		gbc_sourceLabel.anchor = GridBagConstraints.WEST;
		gbc_sourceLabel.insets = new Insets(5, 5, 0, 0);
		gbc_sourceLabel.gridx = 3;
		gbc_sourceLabel.gridy = 2;
		contentPanel.add(sourceLabel, gbc_sourceLabel);
		
		JLabel identityLabel = new JLabel(EntityName.INVENTORY_IDENTITY);
		GridBagConstraints gbc_identityLabel = new GridBagConstraints();
		gbc_identityLabel.anchor = GridBagConstraints.WEST;
		gbc_identityLabel.insets = new Insets(5, 5, 0, 0);
		gbc_identityLabel.gridx = 1;
		gbc_identityLabel.gridy = 2;
		contentPanel.add(identityLabel, gbc_identityLabel);
		
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
		identityField.setColumns(10);
		
		sourceField = new JTextField();
		GridBagConstraints gbc_sourceField = new GridBagConstraints();
		gbc_sourceField.anchor = GridBagConstraints.WEST;
		gbc_sourceField.insets = new Insets(0, 5, 5, 0);
		gbc_sourceField.gridx = 3;
		gbc_sourceField.gridy = 3;
		contentPanel.add(sourceField, gbc_sourceField);
		sourceField.setColumns(5);
				
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

	public String getIdentity() {
		return identityField.getText();
	}

    public void setIdentity(String identity) {
		identityField.setText(identity);
	}

    public void setIdentityFieldListener(String actionCommand, ActionListener actionListener, FocusListener focusListener) {
		identityField.setActionCommand(actionCommand);
		identityField.addActionListener(actionListener);
		identityField.addFocusListener(focusListener);
	}

	public String getSource() {
		return sourceField.getText();
	}

    public void setSource(String source) {
		sourceField.setText(source);
	}

    public void setSourceFieldListener(String actionCommand, ActionListener actionListener, FocusListener focusListener) {
		sourceField.setActionCommand(actionCommand);
		sourceField.addActionListener(actionListener);
		sourceField.addFocusListener(focusListener);
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
