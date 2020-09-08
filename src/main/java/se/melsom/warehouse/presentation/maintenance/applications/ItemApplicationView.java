package se.melsom.warehouse.presentation.maintenance.applications;

import java.awt.BorderLayout;
 import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import se.melsom.warehouse.presentation.common.table.SortedTable;
import se.melsom.warehouse.presentation.common.table.SortedTableModel;
import javax.swing.JSplitPane;

/**
 * The type Item application view.
 */
@SuppressWarnings("serial")
public class ItemApplicationView  extends JDialog  {
	private JTree tree;
	private JCheckBox accumulateCheckBox;
	private JButton btnNewButton;
	private SortedTable table;
	private JButton removeButton;
	private JButton editButton;
	private JButton insertButton;
	private JPanel controlPanel;
	private JSplitPane splitPane;

    /**
     * Instantiates a new Item application view.
     *
     * @param parent            the parent
     * @param componentListener the component listener
     * @param treeModel         the tree model
     * @param tableModel        the table model
     */
    public ItemApplicationView(JFrame parent, ComponentListener componentListener, TreeModel treeModel, SortedTableModel tableModel) {		
		super(parent, true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		controlPanel = new JPanel();
		FlowLayout controlPanelLayout = (FlowLayout) controlPanel.getLayout();
		controlPanelLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(controlPanel, BorderLayout.NORTH);
		
		accumulateCheckBox = new JCheckBox("Ackumulera");
		controlPanel.add(accumulateCheckBox);
		
		btnNewButton = new JButton("Ta ut lista...");
		controlPanel.add(btnNewButton);

		tree = new JTree(treeModel);
		table = new SortedTable(tableModel);
				
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,new JScrollPane(tree), new JScrollPane(table));
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		FlowLayout buttonPanelLayout = (FlowLayout) buttonPanel.getLayout();
		buttonPanelLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		removeButton = new JButton("Ta bort");
		buttonPanel.add(removeButton);
		
		editButton = new JButton("Redigera...");
		buttonPanel.add(editButton);
		
		insertButton = new JButton("Lägg till...");
		buttonPanel.add(insertButton);
	}

    /**
     * Gets selected tree node.
     *
     * @return the selected tree node
     */
// Tree
	TreeNode getSelectedTreeNode() {
		return (TreeNode) tree.getLastSelectedPathComponent();
	}

    /**
     * Sets selected tree node.
     *
     * @param aNode the a node
     */
    void setSelectedTreeNode(TreeNode aNode) {
	}

    /**
     * Sets tree action.
     *
     * @param name     the name
     * @param listener the listener
     */
    void setTreeAction(String name, TreeSelectionListener listener) {
		tree.setName(name);
		tree.getSelectionModel().addTreeSelectionListener(listener);
	}

    /**
     * Gets selected table row.
     *
     * @return the selected table row
     */
// Table
	int getSelectedTableRow() {
		return table.getSelectedRow();
	}

    /**
     * Sets selected table row.
     *
     * @param rowIndex the row index
     */
    void setSelectedTableRow(int rowIndex) {
		table.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
		table.scrollRectToVisible(new Rectangle(table.getCellRect(rowIndex, 0, true)));
	}

    /**
     * Sets table action.
     *
     * @param name     the name
     * @param listener the listener
     */
    void setTableAction(String name, ListSelectionListener listener) {
		table.setName(name);
		table.getSelectionModel().addListSelectionListener(listener);
	}

    /**
     * Is accumulated boolean.
     *
     * @return the boolean
     */
// Accumulate check box
	public boolean isAccumulated() {
		return accumulateCheckBox.isSelected();
	}

    /**
     * Sets accumulate action.
     *
     * @param name     the name
     * @param listener the listener
     */
    public void setAccumulateAction(String name, ActionListener listener) {
		accumulateCheckBox.setActionCommand(name);
		accumulateCheckBox.addActionListener(listener);
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
}
