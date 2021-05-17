package se.melsom.warehouse.presentation.importer;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class InputTableCellRenderer extends JLabel implements TableCellRenderer {
	private final InputTableModel tableModel;
	private final Color pendingBackground = new Color(242, 242, 242);
	private final Color pendingForeground = new Color(117, 113, 113);
	private final Color ignoredBackground = new Color(242, 242, 242);
	private final Color ignoredForeground = new Color(1, 0, 0);
	private final Color validBackground = new Color(242, 242, 242);
	private final Color validForeground = new Color(76, 165, 49);
	private final Color okBackground = new Color(102, 206, 114);
	private final Color okForeground = new Color(1, 0, 0);
	private final Color warningBackground = new Color(235, 233, 102);
	private final Color warningForeground = new Color(1, 0, 0);
	private final Color errorBackground = new Color(235, 83, 43);
	private final Color errorForeground = new Color(1, 0, 0);

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
