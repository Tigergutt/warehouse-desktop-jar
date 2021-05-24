package se.melsom.warehouse.common.table;

import se.melsom.warehouse.model.EntityName;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SortableJTable extends JTable {
	private SortableTableRenderer headerRenderer;
	private final Map<Integer, TableCellRenderer> cellRenders = new HashMap<>();

    public SortableJTable() {
	}

    public SortableJTable(SortableTableModel tableModel) {
		super(tableModel);
		headerRenderer = new SortableTableRenderer(getFont(), tableModel.isSortable());
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
			Color bg = (row % 2 == 0 ? EntityName.TABLE_EVEN_ROW_COLOR : EntityName.TABLE_ODD_ROW_COLOR);
			returnComp.setBackground(bg);
			bg = null;
		}
		return returnComp;
	}
}
