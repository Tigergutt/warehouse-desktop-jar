package se.melsom.warehouse.presentation.holding;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.presentation.common.table.SortedTable;
import se.melsom.warehouse.presentation.common.table.SortedTableModel;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class InventoryHoldingView extends JInternalFrame {
	private static Logger logger = Logger.getLogger(InventoryHoldingView.class);
	private JCheckBox extendedEditCheckBox;
	private JComboBox<String> superiorUnitSelector;
	private JComboBox<String> unitSelector;
	private JLabel superiorUnitLabel;
	private JLabel unitLabel;
	private JButton generateReportButton;
	private SortedTable inventoryTable;
	private JPanel editPanel;
	private JButton insertButton;
	private JButton removeButton;
	private JButton editButton;

	public InventoryHoldingView(InventoryHoldingController controller, SortedTableModel tableModel) {
		addComponentListener(controller);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(true);
		setTitle("Materiellista för vald enhet");
		
		initializeView(controller, tableModel);
	}

	// Extended edit checkbox access methods
	public boolean isExtendedEditEnabled() {
		return extendedEditCheckBox.isSelected();
	}

	public void setExtendedEditEnabled(boolean isEnabled) {
		extendedEditCheckBox.setSelected(isEnabled);
	}

	void setExtendedEditAction(String name, ActionListener listener) {
		extendedEditCheckBox.setActionCommand(name);
		extendedEditCheckBox.addActionListener(listener);
	}

	// Generate report button access methods
	void setGenerateReportAction(String name, ActionListener listener) {
		generateReportButton.setActionCommand(name);
		generateReportButton.addActionListener(listener);
	}

	void setGenerateButtonEnabled(boolean value) {
		generateReportButton.setEnabled(value);
	}
	
	// Section selector access methods
	void setSuperiorUnitSelectorItems(Set<String> units) {
		logger.trace("Set section selector items.");
		unitSelector.removeAllItems();
		superiorUnitSelector.removeAllItems();

		for (String item : units) {
			superiorUnitSelector.addItem(item);
		}
	}

	void setSuperiorUnitSelectedAction(String name, ActionListener listener) {
		superiorUnitSelector.setActionCommand(name);
		superiorUnitSelector.addActionListener(listener);
	}

	int getSelectedSuperiorUnitIndex() {
		return superiorUnitSelector.getSelectedIndex();
	}

	String getSelectedSuperiorUnitItem() {
		return (String) superiorUnitSelector.getSelectedItem();
	}

	// Slot selecor access methods
	void setUnitSelectorItems(Set<String> slots) {
		logger.trace("Set slot selector items.");
		unitSelector.removeAllItems();

		for (String item : slots) {
			unitSelector.addItem(item);
		}
	}
	
	void setUnitSelectedAction(String name, ActionListener listener) {
		unitSelector.setActionCommand(name);
		unitSelector.addActionListener(listener);
	}

	int getSelectedUnitIndex() {
		return unitSelector.getSelectedIndex();
	}

	String getSelectedUnitItem() {
		return (String) unitSelector.getSelectedItem();
	}
	
	// Inventory table access methods
	public int getSelectedTableRow() {
		return inventoryTable.getSelectedRow();
	}

	public void setSelectedTableRow(int rowIndex) {
		inventoryTable.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
	}
	
	public void setSelectedInventoryAction(String name, ListSelectionListener listener) {
		inventoryTable.setName(name);
		ListSelectionModel selectionModel = inventoryTable.getSelectionModel();
		selectionModel.addListSelectionListener(listener);
	}

	public int getSelectedInventoryColumn() {
		return inventoryTable.getSelectedColumn();
	}
	
	public void setCellEditor(TableCellEditor editor, int atColumnIndex) {
		TableColumnModel columnModel = inventoryTable.getColumnModel();
		TableColumn nameColumn = columnModel.getColumn(atColumnIndex);
		
		nameColumn.setCellEditor(editor);
	}

	//
	public void setEditButtonEnabled(boolean isEnabled) {
		editButton.setEnabled(isEnabled);
	}
	
	public void setEditButtonAction(String name, ActionListener listener) {
		editButton.setActionCommand(name);
		editButton.addActionListener(listener);
	}

	//
	public void setInsertButtonEnabled(boolean isEnabled) {
		insertButton.setEnabled(isEnabled);
	}
	
	public void setInsertButtonAction(String name, ActionListener listener) {
		insertButton.setActionCommand(name);
		insertButton.addActionListener(listener);
	}

	//
	public void setRemoveButtonEnabled(boolean isEnabled) {
		removeButton.setEnabled(isEnabled);
	}
	
	public void setRemoveButtonAction(String name, ActionListener listener) {
		removeButton.setActionCommand(name);
		removeButton.addActionListener(listener);
	}

	private void initializeView(InventoryHoldingController controller, SortedTableModel tableModel) {
		JPanel controlPanel = new JPanel();
		getContentPane().add(controlPanel, BorderLayout.NORTH);
		GridBagLayout gbl_controller = new GridBagLayout();
		gbl_controller.columnWidths = new int[] { 0, 52, 0, 52, 0, 0, 0, 0 };
		gbl_controller.rowHeights = new int[] { 29, 0 };
		gbl_controller.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_controller.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		controlPanel.setLayout(gbl_controller);

		superiorUnitLabel = new JLabel(EntityName.ORGANIZATIONAL_SUPERIOR_UNIT);
		GridBagConstraints gbc_superiorUnitLabel = new GridBagConstraints();
		gbc_superiorUnitLabel.anchor = GridBagConstraints.WEST;
		gbc_superiorUnitLabel.insets = new Insets(0, 5, 0, 5);
		gbc_superiorUnitLabel.gridx = 0;
		gbc_superiorUnitLabel.gridy = 0;
		controlPanel.add(superiorUnitLabel, gbc_superiorUnitLabel);

		superiorUnitSelector = new JComboBox<>();

		GridBagConstraints gbc_superiorUnitSelector = new GridBagConstraints();
		gbc_superiorUnitSelector.anchor = GridBagConstraints.WEST;
		gbc_superiorUnitSelector.insets = new Insets(0, 0, 0, 5);
		gbc_superiorUnitSelector.gridx = 1;
		gbc_superiorUnitSelector.gridy = 0;
		controlPanel.add(superiorUnitSelector, gbc_superiorUnitSelector);

		unitLabel = new JLabel(EntityName.ORGANIZATIONAL_UNIT);
		
		GridBagConstraints gbc_unitLabel = new GridBagConstraints();
		gbc_unitLabel.anchor = GridBagConstraints.WEST;
		gbc_unitLabel.insets = new Insets(0, 0, 0, 5);
		gbc_unitLabel.gridx = 2;
		gbc_unitLabel.gridy = 0;
		controlPanel.add(unitLabel, gbc_unitLabel);

		unitSelector = new JComboBox<>();

		GridBagConstraints gbc_unitSelector = new GridBagConstraints();
		gbc_unitSelector.anchor = GridBagConstraints.WEST;
		gbc_unitSelector.insets = new Insets(0, 0, 0, 5);
		gbc_unitSelector.gridx = 3;
		gbc_unitSelector.gridy = 0;
		controlPanel.add(unitSelector, gbc_unitSelector);

		generateReportButton = new JButton("Ta ut lista...");
		generateReportButton.setEnabled(false);
		
		extendedEditCheckBox = new JCheckBox("Utökad redigering");
		String toolTipText = "";
		
		toolTipText += "<html>";
		toolTipText += "Utökad redigering innebär möjlighet att lägga till och ta bort rader.";
		toolTipText += "</html>";
		
		extendedEditCheckBox.setToolTipText(toolTipText);

		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.anchor = GridBagConstraints.EAST;
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_chckbxNewCheckBox.gridx = 5;
		gbc_chckbxNewCheckBox.gridy = 0;
		controlPanel.add(extendedEditCheckBox, gbc_chckbxNewCheckBox);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnNewButton.gridx = 6;
		gbc_btnNewButton.gridy = 0;
		controlPanel.add(generateReportButton, gbc_btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		inventoryTable = new SortedTable(tableModel);		
		scrollPane.setViewportView(inventoryTable);
		editPanel = new JPanel();

		FlowLayout flowLayout = (FlowLayout) editPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(editPanel, BorderLayout.SOUTH);
		
		removeButton = new JButton("Ta bort");
		editPanel.add(removeButton);
		
		editButton = new JButton("Redigera...");
		editPanel.add(editButton);
		
		insertButton = new JButton("Lägg till...");
		editPanel.add(insertButton);
	}
}
