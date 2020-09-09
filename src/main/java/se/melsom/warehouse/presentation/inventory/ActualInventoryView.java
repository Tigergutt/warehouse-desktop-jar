package se.melsom.warehouse.presentation.inventory;

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
 * The type Actual inventory view.
 */
@SuppressWarnings("serial")
public class ActualInventoryView extends JInternalFrame {
	private static Logger logger = Logger.getLogger(ActualInventoryView.class);
	private JCheckBox extendedEditCheckBox;
	private JComboBox<String> sectionSelector;
	private JComboBox<String> slotSelector;
	private JLabel rackingLabel;
	private JLabel palletLabel;
	private JButton generateReportButton;
	private SortedTable inventoryTable;
	private JPanel editPanel;
	private JButton insertButton;
	private JButton removeButton;
	private JButton editButton;

    /**
     * Instantiates a new Actual inventory view.
     *
     * @param controller the controller
     * @param tableModel the table model
     */
    public ActualInventoryView(ActualInventoryController controller, SortedTableModel tableModel) {
		addComponentListener(controller);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(true);
		setTitle("Materiellista för vald lagerplats");
		
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
     * Sets section selector items.
     *
     * @param sections the sections
     */
// Section selector access methods
	void setSectionSelectorItems(Set<String> sections) {
		logger.trace("Set section selector items.");
		slotSelector.removeAllItems();
		sectionSelector.removeAllItems();

		for (String item : sections) {
			sectionSelector.addItem(item);
		}
	}

    /**
     * Sets section selected action.
     *
     * @param name     the name
     * @param listener the listener
     */
    void setSectionSelectedAction(String name, ActionListener listener) {
		sectionSelector.setActionCommand(name);
		sectionSelector.addActionListener(listener);
	}

    /**
     * Gets selected section index.
     *
     * @return the selected section index
     */
    int getSelectedSectionIndex() {
		return sectionSelector.getSelectedIndex();
	}

    /**
     * Gets selected section item.
     *
     * @return the selected section item
     */
    String getSelectedSectionItem() {
		return (String) sectionSelector.getSelectedItem();
	}

    /**
     * Sets slot selector items.
     *
     * @param slots the slots
     */
// Slot selecor access methods
	void setSlotSelectorItems(Set<String> slots) {
		logger.trace("Set slot selector items.");
		slotSelector.removeAllItems();

		for (String item : slots) {
			slotSelector.addItem(item);
		}
	}

    /**
     * Sets slot selected action.
     *
     * @param name     the name
     * @param listener the listener
     */
    void setSlotSelectedAction(String name, ActionListener listener) {
		slotSelector.setActionCommand(name);
		slotSelector.addActionListener(listener);
	}

    /**
     * Gets selected slot index.
     *
     * @return the selected slot index
     */
    int getSelectedSlotIndex() {
		return slotSelector.getSelectedIndex();
	}

    /**
     * Gets selected slot item.
     *
     * @return the selected slot item
     */
    String getSelectedSlotItem() {
		return (String) slotSelector.getSelectedItem();
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

	private void initializeView(ActualInventoryController controller, SortedTableModel tableModel) {
		JPanel controlPanel = new JPanel();
		getContentPane().add(controlPanel, BorderLayout.NORTH);
		GridBagLayout gbl_controller = new GridBagLayout();
		gbl_controller.columnWidths = new int[] { 0, 52, 0, 52, 0, 0, 0, 0 };
		gbl_controller.rowHeights = new int[] { 29, 0 };
		gbl_controller.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_controller.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		controlPanel.setLayout(gbl_controller);

		slotSelector = new JComboBox<>();

		rackingLabel = new JLabel(EntityName.STOCK_LOCATION_DESIGNATION_SECTION);
		GridBagConstraints gbc_rackingLabel = new GridBagConstraints();
		gbc_rackingLabel.anchor = GridBagConstraints.WEST;
		gbc_rackingLabel.insets = new Insets(0, 5, 0, 5);
		gbc_rackingLabel.gridx = 0;
		gbc_rackingLabel.gridy = 0;
		controlPanel.add(rackingLabel, gbc_rackingLabel);

		sectionSelector = new JComboBox<>();

		GridBagConstraints gbc_rackingSelector = new GridBagConstraints();
		gbc_rackingSelector.anchor = GridBagConstraints.WEST;
		gbc_rackingSelector.insets = new Insets(0, 0, 0, 5);
		gbc_rackingSelector.gridx = 1;
		gbc_rackingSelector.gridy = 0;
		controlPanel.add(sectionSelector, gbc_rackingSelector);

		palletLabel = new JLabel(EntityName.STOCK_LOCATION_DESIGNATION_SLOT);
		GridBagConstraints gbc_palletLabel = new GridBagConstraints();
		gbc_palletLabel.anchor = GridBagConstraints.WEST;
		gbc_palletLabel.insets = new Insets(0, 0, 0, 5);
		gbc_palletLabel.gridx = 2;
		gbc_palletLabel.gridy = 0;
		controlPanel.add(palletLabel, gbc_palletLabel);

		GridBagConstraints gbc_palletSelector = new GridBagConstraints();
		gbc_palletSelector.anchor = GridBagConstraints.WEST;
		gbc_palletSelector.insets = new Insets(0, 0, 0, 5);
		gbc_palletSelector.gridx = 3;
		gbc_palletSelector.gridy = 0;
		controlPanel.add(slotSelector, gbc_palletSelector);

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
