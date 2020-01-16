package se.melsom.warehouse.presentation.importer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class InputTableCellRenderer extends JLabel implements TableCellRenderer {
	private InputTableModel tableModel;
	private Color pendingBackground = new Color(242, 242, 242);
	private Color pendingForeground = new Color(117, 113, 113);
	private Color ignoredBackground = new Color(242, 242, 242);
	private Color ignoredForeground = new Color(1, 0, 0);
	private Color validBackground = new Color(242, 242, 242);
	private Color validForeground = new Color(76, 165, 49);
	private Color okBackground = new Color(102, 206, 114);
	private Color okForeground = new Color(1, 0, 0);
	private Color warningBackground = new Color(235, 233, 102);
	private Color warningForeground = new Color(1, 0, 0);
	private Color errorBackground = new Color(235, 83, 43);
	private Color errorForeground = new Color(1, 0, 0);
	
	public InputTableCellRenderer(InputTableModel tableModel) {
		this.tableModel = tableModel;
		super.setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.setText(value == null ? "" : value.toString());
		
		switch (tableModel.getRow(row).get(column).getStatus()) {
		case PENDING:
			super.setBackground(pendingBackground);
			super.setForeground(pendingForeground);
			break;
			
		case IGNORED:
			super.setBackground(ignoredBackground);
			super.setForeground(ignoredForeground);
			break;
			
		case VALID:
			super.setBackground(validBackground);
			super.setForeground(validForeground);
			break;
			
		case OK:
			super.setBackground(okBackground);
			super.setForeground(okForeground);
			break;
			
		case WARNING:
			super.setBackground(warningBackground);
			super.setForeground(warningForeground);
			break;

		case ERROR:
			super.setBackground(errorBackground);
			super.setForeground(errorForeground);
			break;
		}
		
		return this;
	}

}
