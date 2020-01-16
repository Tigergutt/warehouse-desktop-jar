package se.melsom.warehouse.presentation.common.table;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import se.melsom.warehouse.model.EntityName;

@SuppressWarnings("serial")
public class SortedTable extends JTable {
	private TableHeaderRenderer headerRenderer;
	private Map<Integer, TableCellRenderer> cellRenders = new HashMap<>();

	public SortedTable() {
	}

	public SortedTable(SortedTableModel tableModel) {
		super(tableModel);
		headerRenderer = new TableHeaderRenderer(getFont(), tableModel.isSortable());
		tableModel.setHeaderRenderer(headerRenderer);
		getTableHeader().setDefaultRenderer(headerRenderer);
		getTableHeader().addMouseListener(tableModel);

		TableColumnModel columnModel = getColumnModel();

		columnModel.setColumnMargin(5);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		for (int columnIndex = 0; columnIndex < columnModel.getColumnCount(); columnIndex++) {
			columnModel.getColumn(columnIndex).setPreferredWidth(tableModel.getColumnWidth(columnIndex));
		}
	}
	
	public void addCellRenderer(int columnIndex, TableCellRenderer renderer) {
		cellRenders.put(columnIndex, renderer);
	}
		
	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		if (cellRenders.containsKey(column)) {
			return cellRenders.get(column);
		}
		
        return super.getCellRenderer(row, column);
    }
	
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Component returnComp = super.prepareRenderer(renderer, row, column);
		if (!returnComp.getBackground().equals(getSelectionBackground())) {
			Color bg = (row % 2 == 0 ? EntityName.tableEvenRowColor : EntityName.tableOddRowColor);
			returnComp.setBackground(bg);
			bg = null;
		}
		return returnComp;
	}
}
