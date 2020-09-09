package se.melsom.warehouse.presentation.common.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * The type Table header renderer.
 */
@SuppressWarnings("serial")
public class TableHeaderRenderer extends JLabel implements TableCellRenderer {
	private Font defaultFont;
	private Font enabledFont;
	private Font activeFont;
	private boolean[] columnStatus;
	private int activeColumn = 0;

    /**
     * Instantiates a new Table header renderer.
     *
     * @param font         the font
     * @param columnStatus the column status
     */
    public TableHeaderRenderer(Font font, boolean[] columnStatus) {
    	this.columnStatus = columnStatus;
    	defaultFont = font;
    	enabledFont = new Font(font.getFontName(), Font.PLAIN, font.getSize());
    	activeFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
    	setOpaque(true);
    }

    /**
     * Gets active column.
     *
     * @return the active column
     */
    public int getActiveColumn() {
    	return activeColumn;
    }

    /**
     * Sets active column.
     *
     * @param columnIndex the column index
     */
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