package se.melsom.warehouse.presentation.importer;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class InputTableModel extends AbstractTableModel {
	private final Vector<String> columnNames = new Vector<>();
	private final Vector<Vector<ImportCell>> content = new Vector<>();

    public InputTableModel() {
	}
    public void addColumnName(String columnName) {
		columnNames.addElement(columnName);
	}

    public Vector<ImportCell> getRow(int rowIndex) {
		return content.get(rowIndex);
	}

    public void addRow(Vector<ImportCell> row) {
		content.addElement(row);
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames.get(col);
	}

	@Override
	public int getRowCount() {
		return content.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex < content.size()) {
			Vector<ImportCell> row = content.get(rowIndex);
			
			if (columnIndex < row.size()) {
				return row.get(columnIndex).getValue();
			}
		}

		return "";
	}
}
