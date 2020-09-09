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

/**
 * The type Inventory holding view.
 */
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

    /**
     * Instantiates a new Inventory holding view.
     *
     * @param controller the controller
     * @param tableModel the table model
     */
    public InventoryHoldingView(InventoryHoldingController controller, SortedTableModel tableModel) {
		addComponentListener(controller);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(true);
		setTitle("Materiellista för vald enhet");
		
		initializeView(controller, tableModel);
	}

    /**
     * Is extended edit enabled boolean.
     *
     * @return the boolean
     */
// Extended edit checkbox access methods
	public boolean isExtendedEditEnabled() {
		return extendedEditCheckBox.isSelected();
	}

    /**
     * Sets extended edit enabled.
     *
     * @param isEnabled the is enabled
     */
    public void setExtendedEditEnabled(boolean isEnabled) {
		extendedEditCheckBox.setSelected(isEnabled);
	}

    /**
     * Sets extended edit action.
     *
     * @param name     the name
     * @param listener the listener
     */
    void setExtendedEditAction(String name, ActionListener listener) {
		extendedEditCheckBox.setActionCommand(name);
		extendedEditCheckBox.addActionListener(listener);
	}

    /**
     * Sets generate report action.
     *
     * @param name     the name
     * @param listener the listener
     */
// Generate report button access methods
	void setGenerateReportAction(String name, ActionListener listener) {
		generateReportButton.setActionCommand(name);
		generateReportButton.addActionListener(listener);
	}

    /**
     * Sets generate button enabled.
     *
     * @param value the value
     */
    void setGenerateButtonEnabled(boolean value) {
		generateReportButton.setEnabled(value);
	}

    /**
     * Sets superior unit selector items.
     *
     * @param units the units
     */
// Section selector access methods
	void setSuperiorUnitSelectorItems(Set<String> units) {
		logger.trace("Set section selector items.");
		unitSelector.removeAllItems();
		superiorUnitSelector.removeAllItems();

		for (String item : units) {
			superiorUnitSelector.addItem(item);
		}
	}

    /**
     * Sets superior unit selected action.
     *
     * @param name     the name
     * @param listener the listener
     */
    void setSuperiorUnitSelectedAction(String name, ActionListener listener) {
		superiorUnitSelector.setActionCommand(name);
		superiorUnitSelector.addActionListener(listener);
	}

    /**
     * Gets selected superior unit index.
     *
     * @return the selected superior unit index
     */
    int getSelectedSuperiorUnitIndex() {
		return superiorUnitSelector.getSelectedIndex();
	}

    /**
     * Gets selected superior unit item.
     *
     * @return the selected superior unit item
     */
    String getSelectedSuperiorUnitItem() {
		return (String) superiorUnitSelector.getSelectedItem();
	}

    /**
     * Sets unit selector items.
     *
     * @param slots the slots
     */
// Slot selecor access methods
	void setUnitSelectorItems(Set<String> slots) {
		logger.trace("Set slot selector items.");
		unitSelector.removeAllItems();

		for (String item : slots) {
			unitSelector.addItem(item);
		}
	}

    /**
     * Sets unit selected action.
     *
     * @param name     the name
     * @param listener the listener
     */
    void setUnitSelectedAction(String name, ActionListener listener) {
		unitSelector.setActionCommand(name);
		unitSelector.addActionListener(listener);
	}

    /**
     * Gets selected unit index.
     *
     * @return the selected unit index
     */
    int getSelectedUnitIndex() {
		return unitSelector.getSelectedIndex();
	}

    /**
     * Gets selected unit item.
     *
     * @return the selected unit item
     */
    String getSelectedUnitItem() {
		return (String) unitSelector.getSelectedItem();
	}

    /**
     * Gets selected table row.
     *
     * @return the selected table row
     */
// Inventory table access methods
	public int getSelectedTableRow() {
		return inventoryTable.getSelectedRow();
	}

    /**
     * Sets selected table row.
     *
     * @param rowIndex the row index
     */
    public void setSelectedTableRow(int rowIndex) {
		inventoryTable.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
	}

    /**
     * Sets selected inventory action.
     *
     * @param name     the name
     * @param listener the listener
     */
    public void setSelectedInventoryAction(String name, ListSelectionListener listener) {
		inventoryTable.setName(name);
		ListSelectionModel selectionModel = inventoryTable.getSelectionModel();
		selectionModel.addListSelectionListener(listener);
	}

    /**
     * Gets selected inventory column.
     *
     * @return the selected inventory column
     */
    public int getSelectedInventoryColumn() {
		return inventoryTable.getSelectedColumn();
	}

    /**
     * Sets cell editor.
     *
     * @param editor        the editor
     * @param atColumnIndex the at column index
     */
    public void setCellEditor(TableCellEditor editor, int atColumnIndex) {
		TableColumnModel columnModel = inventoryTable.getColumnModel();
		TableColumn nameColumn = columnModel.getColumn(atColumnIndex);
		
		nameColumn.setCellEditor(editor);
	}

    /**
     * Sets edit button enabled.
     *
     * @param isEnabled the is enabled
     */
//
	public void setEditButtonEnabled(boolean isEnabled) {
		editButton.setEnabled(isEnabled);
	}

    /**
     * Sets edit button action.
     *
     * @param name     the name
     * @param listener the listener
     */
    public void setEditButtonAction(String name, ActionListener listener) {
		editButton.setActionCommand(name);
		editButton.addActionListener(listener);
	}

    /**
     * Sets insert button enabled.
     *
     * @param isEnabled the is enabled
     */
//
	public void setInsertButtonEnabled(boolean isEnabled) {
		insertButton.setEnabled(isEnabled);
	}

    /**
     * Sets insert button action.
     *
     * @param name     the name
     * @param listener the listener
     */
    public void setInsertButtonAction(String name, ActionListener listener) {
		insertButton.setActionCommand(name);
		insertButton.addActionListener(listener);
	}

    /**
     * Sets remove button enabled.
     *
     * @param isEnabled the is enabled
     */
//
	public void setRemoveButtonEnabled(boolean isEnabled) {
		removeButton.setEnabled(isEnabled);
	}

    /**
     * Sets remove button action.
     *
     * @param name     the name
     * @param listener the listener
     */
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
