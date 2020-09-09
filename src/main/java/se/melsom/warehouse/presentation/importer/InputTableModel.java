package se.melsom.warehouse.presentation.importer;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;


/**
 * The type Input table model.
 */
@SuppressWarnings("serial")
public class InputTableModel extends AbstractTableModel {
	private Vector<String> columnNames = new Vector<>();
	private Vector<Vector<ImportCell>> content = new Vector<>();

    /**
     * Instantiates a new Input table model.
     */
    public InputTableModel() {
	}
	
//	@Override
//	public Class<?> getColumnClass(int col) {
//		return content.get(0).get(col).getClass();
//	}

    /**
     * Add column name.
     *
     * @param columnName the column name
     */
    public void addColumnName(String columnName) {
		columnNames.addElement(columnName);
	}

    /**
     * Gets row.
     *
     * @param rowIndex the row index
     * @return the row
     */
    public Vector<ImportCell> getRow(int rowIndex) {
		return content.get(rowIndex);
	}

    /**
     * Add row.
     *
     * @param row the row
     */
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
