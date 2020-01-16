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

	// Tree
	TreeNode getSelectedTreeNode() {
		return (TreeNode) tree.getLastSelectedPathComponent();
	}

	void setSelectedTreeNode(TreeNode aNode) {
	}
	
	void setTreeAction(String name, TreeSelectionListener listener) {
		tree.setName(name);
		tree.getSelectionModel().addTreeSelectionListener(listener);
	}

	// Table
	int getSelectedTableRow() {
		return table.getSelectedRow();
	}

	void setSelectedTableRow(int rowIndex) {
		table.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
		table.scrollRectToVisible(new Rectangle(table.getCellRect(rowIndex, 0, true)));
	}
	
	void setTableAction(String name, ListSelectionListener listener) {
		table.setName(name);
		table.getSelectionModel().addListSelectionListener(listener);
	}

	// Accumulate check box
	public boolean isAccumulated() {
		return accumulateCheckBox.isSelected();
	}
	
	public void setAccumulateAction(String name, ActionListener listener) {
		accumulateCheckBox.setActionCommand(name);
		accumulateCheckBox.addActionListener(listener);
	}

	// Remove button
	public void setRemoveButtonEnabled(boolean isEnabled) {
		removeButton.setEnabled(isEnabled);
	}
	
	public void setRemoveButtonAction(String name, ActionListener listener) {
		removeButton.setActionCommand(name);
		removeButton.addActionListener(listener);
	}

	// Edit button
	void setEditButtonEnabled(boolean isEnabled) {
		editButton.setEnabled(isEnabled);
	}
	
	void setEditButtonAction(String name, ActionListener listener) {
		editButton.setActionCommand(name);
		editButton.addActionListener(listener);
	}

	// Insert button
	void setInsertButtonEnabled(boolean isEnabled) {
		insertButton.setEnabled(isEnabled);
	}
	
	void setInsertButtonAction(String name, ActionListener listener) {
		insertButton.setActionCommand(name);
		insertButton.addActionListener(listener);
	}
}
