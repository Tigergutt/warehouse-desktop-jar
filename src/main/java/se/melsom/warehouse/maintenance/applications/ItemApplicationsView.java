package se.melsom.warehouse.maintenance.applications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.common.table.SortableJTable;
import se.melsom.warehouse.common.table.SortableTableModel;
import se.melsom.warehouse.settings.PersistentSettings;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

@Component
public class ItemApplicationsView extends JDialog implements AbstractItemApplicationsView {
	@Autowired private PersistentSettings persistentSettings;
	@Autowired private ItemApplications itemApplications;

	private JTree tree;
	private JCheckBox accumulateCheckBox;
	private JButton packingSlipButton;
	private SortableJTable table;
	private JButton removeButton;
	private JButton editButton;
	private JButton insertButton;
	private JPanel controlPanel;
	private JSplitPane splitPane;

    public void initializeView(JFrame parent, ComponentListener componentListener, TreeModel treeModel, SortableTableModel tableModel) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		controlPanel = new JPanel();
		FlowLayout controlPanelLayout = (FlowLayout) controlPanel.getLayout();
		controlPanelLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(controlPanel, BorderLayout.NORTH);

		accumulateCheckBox = new JCheckBox("Ackumulera");
		controlPanel.add(accumulateCheckBox);

		packingSlipButton = new JButton("Ta ut lista...");
		controlPanel.add(packingSlipButton);

		tree = new JTree(treeModel);
		table = new SortableJTable(tableModel);

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

	TreeNode getSelectedTreeNode() {
		return (TreeNode) tree.getLastSelectedPathComponent();
	}

    void setSelectedTreeNode(TreeNode aNode) {
	}

    void setTreeAction(String name, TreeSelectionListener listener) {
		tree.setName(name);
		tree.getSelectionModel().addTreeSelectionListener(listener);
	}

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

	public boolean isAccumulated() {
		return accumulateCheckBox.isSelected();
	}

    public void setAccumulateAction(String name, ActionListener listener) {
		accumulateCheckBox.setActionCommand(name);
		accumulateCheckBox.addActionListener(listener);
	}

	public void setRemoveButtonEnabled(boolean isEnabled) {
		removeButton.setEnabled(isEnabled);
	}

    public void setRemoveButtonAction(String name, ActionListener listener) {
		removeButton.setActionCommand(name);
		removeButton.addActionListener(listener);
	}

	void setEditButtonEnabled(boolean isEnabled) {
		editButton.setEnabled(isEnabled);
	}

    void setEditButtonAction(String name, ActionListener listener) {
		editButton.setActionCommand(name);
		editButton.addActionListener(listener);
	}

	void setInsertButtonEnabled(boolean isEnabled) {
		insertButton.setEnabled(isEnabled);
	}

    void setInsertButtonAction(String name, ActionListener listener) {
		insertButton.setActionCommand(name);
		insertButton.addActionListener(listener);
	}
}
