package se.melsom.warehouse.presentation.stock;

import se.melsom.warehouse.application.common.table.SortableJTable;
import se.melsom.warehouse.application.common.table.SortableTableModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;

@Deprecated
public class StockOnHandView extends JInternalFrame {
	private final JCheckBox shortfallCheckBox;
	private final JCheckBox balancesCheckBox;
	private final JCheckBox overplusCheckBox;
	private final SortableJTable stockOnHandtTable;

    public StockOnHandView(StockOnHandController controller, SortableTableModel tableModel) {
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
		
		stockOnHandtTable = new SortableJTable(tableModel);
		scrollPane.setViewportView(stockOnHandtTable);
	}

     public void setCellRenderer(int columnIndex, TableCellRenderer renderer) {
		stockOnHandtTable.addCellRenderer(columnIndex, renderer);
	}

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
