package se.melsom.warehouse.presentation.importer;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
public class InputTableModel extends AbstractTableModel {
	private Vector<String> columnNames = new Vector<>();
	private Vector<Vector<ImportCell>> content = new Vector<>();

	public InputTableModel() {
	}
	
//	@Override
//	public Class<?> getColumnClass(int col) {
//		return content.get(0).get(col).getClass();
//	}
	
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
