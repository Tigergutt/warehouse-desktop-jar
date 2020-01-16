package se.melsom.warehouse.presentation.stock;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.table.TableCellRenderer;

import se.melsom.warehouse.presentation.common.table.SortedTable;
import se.melsom.warehouse.presentation.common.table.SortedTableModel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class StockOnHandView extends JInternalFrame {
	private JCheckBox shortfallCheckBox;
	private JCheckBox balancesCheckBox;
	private JCheckBox overplusCheckBox;
	private SortedTable stockOnHandtTable;

	public StockOnHandView(StockOnHandController controller, SortedTableModel tableModel) {
		addComponentListener(controller);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(true);
		setTitle("Lagersaldo");
		
		JPanel controlPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) controlPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(controlPanel, BorderLayout.NORTH);
		
		JLabel filterLabel = new JLabel("Dölj:");
		controlPanel.add(filterLabel);
		
		shortfallCheckBox = new JCheckBox("underskott");
		controlPanel.add(shortfallCheckBox);
		
		balancesCheckBox = new JCheckBox("balanser");
		controlPanel.add(balancesCheckBox);
		
		overplusCheckBox = new JCheckBox("överskott");
		controlPanel.add(overplusCheckBox);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		stockOnHandtTable = new SortedTable(tableModel);
		scrollPane.setViewportView(stockOnHandtTable);
	}
	
	public void setCellRenderer(int columnIndex, TableCellRenderer renderer) {
		stockOnHandtTable.addCellRenderer(columnIndex, renderer);
	}
	
	/*
	 * Shortfall filter check box
	 */
	public boolean getFilterShortfallChecked() {
		return shortfallCheckBox.isSelected();
	}
	
	public void setFilterShortfallChecked(boolean checked) {
		shortfallCheckBox.setSelected(checked);
	}
	
	public void setFilterShortfallAction(String name, ActionListener listener) {
		shortfallCheckBox.setActionCommand(name);
		shortfallCheckBox.addActionListener(listener);
	}

	/*
	 * Balances filter check box
	 */
	public boolean getFilterBalancesChecked() {
		return balancesCheckBox.isSelected();
	}
	
	public void setFilterBalancesChecked(boolean checked) {
		balancesCheckBox.setSelected(checked);
	}

	public void setFilterBalancesAction(String name, ActionListener listener) {
		balancesCheckBox.setActionCommand(name);
		balancesCheckBox.addActionListener(listener);
	}

	/*
	 * Overplus filter check box
	 */
	public boolean getFilterOverplusChecked() {
		return overplusCheckBox.isSelected();
	}
	
	public void setFilterOverplusChecked(boolean checked) {
		overplusCheckBox.setSelected(checked);
	}

	public void setFilterOverplusAction(String name, ActionListener listener) {
		overplusCheckBox.setActionCommand(name);
		overplusCheckBox.addActionListener(listener);
	}
}
