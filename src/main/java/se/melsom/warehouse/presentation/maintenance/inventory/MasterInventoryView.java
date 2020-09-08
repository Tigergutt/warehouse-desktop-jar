package se.melsom.warehouse.presentation.maintenance.inventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.EntityName;

/**
 * The type Master inventory view.
 */
@SuppressWarnings("serial")
public class MasterInventoryView extends JDialog {
	private static Logger logger = Logger.getLogger(MasterInventoryView.class);
	private MasterInventoryController controller;
	private JPanel controlPanel;
	private JTable instancesTable;
	private JCheckBox extendedEditCheckBox;
	private JPanel editPanel;
	private JButton insertButton;
	private JButton removeButton;
	private JButton editButton;

    /**
     * Instantiates a new Master inventory view.
     *
     * @param parent     the parent
     * @param controller the controller
     * @param tableModel the table model
     */
    public MasterInventoryView(JFrame parent, MasterInventoryController controller, TableModel tableModel) {
		super(parent, true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		this.controller = controller;

		addComponentListener(controller);
		setTitle("Registervård: Materiel enligt centrallager");
		
		initializeView(tableModel);
	}

    /**
     * Is extended edit enabled boolean.
     *
     * @return the boolean
     */
// Extended edit checkbox access methods
	boolean isExtendedEditEnabled() {
		return extendedEditCheckBox.isSelected();
	}

    /**
     * Sets extended edit enabled.
     *
     * @param isEnabled the is enabled
     */
    void setExtendedEditEnabled(boolean isEnabled) {
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
     * Gets selected table row.
     *
     * @return the selected table row
     */
// Instance table
	int getSelectedTableRow() {
		return instancesTable.getSelectedRow();
	}

    /**
     * Sets selected table row.
     *
     * @param rowIndex the row index
     */
    void setSelectedTableRow(int rowIndex) {
		instancesTable.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
		instancesTable.scrollRectToVisible(new Rectangle(instancesTable.getCellRect(rowIndex, 0, true)));
	}

    /**
     * Sets instance table action.
     *
     * @param name     the name
     * @param listener the listener
     */
    void setInstanceTableAction(String name, ListSelectionListener listener) {
		instancesTable.setName(name);
		instancesTable.getSelectionModel().addListSelectionListener(listener);
	}

    /**
     * Sets edit button enabled.
     *
     * @param isEnabled the is enabled
     */
// Edit button
	void setEditButtonEnabled(boolean isEnabled) {
		editButton.setEnabled(isEnabled);
	}

    /**
     * Sets edit button action.
     *
     * @param name     the name
     * @param listener the listener
     */
    void setEditButtonAction(String name, ActionListener listener) {
		editButton.setActionCommand(name);
		editButton.addActionListener(listener);
	}

    /**
     * Sets insert button enabled.
     *
     * @param isEnabled the is enabled
     */
// Insert button
	void setInsertButtonEnabled(boolean isEnabled) {
		insertButton.setEnabled(isEnabled);
	}

    /**
     * Sets insert button action.
     *
     * @param name     the name
     * @param listener the listener
     */
    void setInsertButtonAction(String name, ActionListener listener) {
		insertButton.setActionCommand(name);
		insertButton.addActionListener(listener);
	}

    /**
     * Sets remove button enabled.
     *
     * @param isEnabled the is enabled
     */
// Remove button
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

	// ---
	private void initializeView(TableModel tableModel) {
		controlPanel = new JPanel();
		getContentPane().add(controlPanel, BorderLayout.NORTH);

		extendedEditCheckBox = new JCheckBox("Redigera");
		String toolTipText = "";
		
		toolTipText += "<html>";
		toolTipText += "Utökad redigering innebär möjlighet att redigera F-beteckning,<br/>";
		toolTipText += "F-benämning, och även lägga till och ta bort rader.";
		toolTipText += "</html>";
		
		extendedEditCheckBox.setToolTipText(toolTipText);
		extendedEditCheckBox.addActionListener(controller);
		controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		controlPanel.add(extendedEditCheckBox);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		instancesTable = new JTable(tableModel) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component returnComp = super.prepareRenderer(renderer, row, column);
				if (!returnComp.getBackground().equals(getSelectionBackground())) {
					Color bg = (row % 2 == 0 ? EntityName.tableEvenRowColor : EntityName.tableOddRowColor);
					returnComp.setBackground(bg);
					bg = null;
				}
				return returnComp;
			}
		};
		
		scrollPane.setViewportView(instancesTable);

		TableColumnModel columnModel = instancesTable.getColumnModel();
		columnModel.setColumnMargin(5);
		instancesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		for (int columnIndex = 0; columnIndex < columnModel.getColumnCount(); columnIndex++) {
			columnModel.getColumn(columnIndex).setPreferredWidth(MasterInventoryTableModel.columnWidts[columnIndex]);
		}
		
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
	
	@Override
	public void dispose() {
		super.dispose();
		logger.debug("Dispose");
	}
}
