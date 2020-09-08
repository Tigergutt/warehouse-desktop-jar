package se.melsom.warehouse.presentation.maintenance.items;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import se.melsom.warehouse.presentation.common.table.SortedTable;
import se.melsom.warehouse.presentation.common.table.SortedTableModel;

/**
 * The type Item maintenance view.
 */
@SuppressWarnings("serial")
public class ItemMaintenanceView extends JInternalFrame {
	private static Logger logger = Logger.getLogger(ItemMaintenanceView.class);
	private SortedTable itemsTable;
	private JButton generateReportButton;
	private JCheckBox extendedEditCheckBox;
	private JPanel editPanel;
	private JButton editButton;
	private JButton insertButton;
	private JButton removeButton;

    /**
     * Instantiates a new Item maintenance view.
     *
     * @param controller the controller
     * @param tableModel the table model
     */
    public ItemMaintenanceView(ItemMaintenanceController controller, SortedTableModel tableModel) {
		setIconifiable(true);
		addComponentListener(controller);
		setClosable(true);
		setResizable(true);
		setTitle("Registervård: Artikellista");
		
		initializeView(tableModel);
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
     * Sets extended edit checked.
     *
     * @param isEnabled the is enabled
     */
    public void setExtendedEditChecked(boolean isEnabled) {
		extendedEditCheckBox.setSelected(isEnabled);
	}

    /**
     * Sets extended edit button action.
     *
     * @param name     the name
     * @param listener the listener
     */
    public void setExtendedEditButtonAction(String name, ActionListener listener) {
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
		logger.trace("Set generate report button enabled=" + value);
		generateReportButton.setEnabled(value);
	}

    /**
     * Sets selected item action.
     *
     * @param name     the name
     * @param listener the listener
     */
// Inventory table access methods
	void setSelectedItemAction(String name, ListSelectionListener listener) {
		itemsTable.setName(name);
		itemsTable.getSelectionModel().addListSelectionListener(listener);;		
	}

    /**
     * Gets selected product table row.
     *
     * @return the selected product table row
     */
    public int getSelectedProductTableRow() {
		return itemsTable.getSelectedRow();
	}

    /**
     * Gets selected article table column.
     *
     * @return the selected article table column
     */
    public int getSelectedArticleTableColumn() {
		return itemsTable.getSelectedColumn();
	}

    /**
     * Gets selected item row.
     *
     * @return the selected item row
     */
// Product table access methods
	public int getSelectedItemRow() {
		return itemsTable.getSelectedRow();
	}

    /**
     * Sets selected item row.
     *
     * @param rowIndex the row index
     */
    public void setSelectedItemRow(int rowIndex) {
		itemsTable.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
		itemsTable.scrollRectToVisible(new Rectangle(itemsTable.getCellRect(rowIndex, 0, true)));
	}

    /**
     * Gets selected article column.
     *
     * @return the selected article column
     */
    public int getSelectedArticleColumn() {
		return itemsTable.getSelectedColumn();
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

	private void initializeView(SortedTableModel tableModel) {
		JPanel controlPanel = new JPanel();
		getContentPane().add(controlPanel, BorderLayout.NORTH);
		GridBagLayout gbl_controlPanel = new GridBagLayout();
		gbl_controlPanel.columnWidths = new int[]{0, 0, 119, 0};
		gbl_controlPanel.rowHeights = new int[]{29, 0};
		gbl_controlPanel.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_controlPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		controlPanel.setLayout(gbl_controlPanel);
		
		generateReportButton = new JButton("Ta ut lista...");

		extendedEditCheckBox = new JCheckBox("Redigering");
		String toolTipText = "";
		
		toolTipText += "<html>";
		toolTipText += "Utökad redigering innebär möjlighet att redigera F-beteckning,<br/>";
		toolTipText += "F-benämning, och även lägga till och ta bort rader.";
		toolTipText += "</html>";
		
		extendedEditCheckBox.setToolTipText(toolTipText);
		GridBagConstraints gbc_extendedEditCheckBox = new GridBagConstraints();
		gbc_extendedEditCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_extendedEditCheckBox.anchor = GridBagConstraints.EAST;
		gbc_extendedEditCheckBox.gridx = 1;
		gbc_extendedEditCheckBox.gridy = 0;
		controlPanel.add(extendedEditCheckBox, gbc_extendedEditCheckBox);

		GridBagConstraints gbc_generateReportButton = new GridBagConstraints();
		gbc_generateReportButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_generateReportButton.gridx = 2;
		gbc_generateReportButton.gridy = 0;
		controlPanel.add(generateReportButton, gbc_generateReportButton);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		itemsTable = new SortedTable(tableModel);
		
		scrollPane.setViewportView(itemsTable);

		editPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) editPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(editPanel, BorderLayout.SOUTH);
		
		removeButton = new JButton("Ta bort");
		editPanel.add(removeButton);

		editButton = new JButton("Redigera...");
		editPanel.add(editButton);
		
		insertButton = new JButton("Lägg till ny...");
		editPanel.add(insertButton);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		logger.debug("Dispose");
	}

}
