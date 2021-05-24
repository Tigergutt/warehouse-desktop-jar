package se.melsom.warehouse.common.table;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class SortableTableRenderer extends JLabel implements TableCellRenderer {
	private final Font defaultFont;
	private final Font enabledFont;
	private final Font activeFont;
	private final boolean[] columnStatus;
	private int activeColumn = 0;

    public SortableTableRenderer(Font font, boolean[] columnStatus) {
    	this.columnStatus = columnStatus;
    	defaultFont = font;
    	enabledFont = new Font(font.getFontName(), Font.PLAIN, font.getSize());
    	activeFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
    	setOpaque(true);
    }

    public int getActiveColumn() {
    	return activeColumn;
    }

    public void setActiveColumn(int columnIndex) {
    	if (columnStatus[columnIndex]) {
    		activeColumn = columnIndex;
    	}
    }
     
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	if (column == activeColumn) {
            setFont(activeFont);
            setForeground(Color.BLUE);
            setBorder(BorderFactory.createEtchedBorder());
    	} else if (columnStatus[column]) {
            setFont(enabledFont);
            setForeground(Color.BLUE);
            setBorder(BorderFactory.createEtchedBorder());
    	} else {
            setFont(defaultFont);
            setForeground(Color.BLACK);
            setBorder(BorderFactory.createEmptyBorder());
    	}
    	
    	setText(value.toString());

        return this;
    }
}