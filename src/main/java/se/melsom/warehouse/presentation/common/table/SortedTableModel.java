package se.melsom.warehouse.presentation.common.table;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;

/**
 * The type Sorted table model.
 */
@SuppressWarnings("serial")
public abstract class SortedTableModel extends AbstractTableModel implements MouseListener {
	private static Logger logger = Logger.getLogger(SortedTable.class);
	private TableHeaderRenderer headerRenderer = null;
//	private int activeColumnIndex = 0;

    /**
     * Sets header renderer.
     *
     * @param headerRenderer the header renderer
     */
    public void setHeaderRenderer(TableHeaderRenderer headerRenderer) {
		this.headerRenderer = headerRenderer;
	}

    /**
     * Gets header renderer.
     *
     * @return the header renderer
     */
    public TableHeaderRenderer getHeaderRenderer() {
		return headerRenderer;
	}

    /**
     * Gets column width.
     *
     * @param columnIndex the column index
     * @return the column width
     */
    public abstract int getColumnWidth(int columnIndex);

    /**
     * Is sortable boolean [ ].
     *
     * @return the boolean [ ]
     */
    public abstract boolean[] isSortable();

    /**
     * Gets active column.
     *
     * @return the active column
     */
    public int getActiveColumn() {
		if (headerRenderer == null ) {
			return 0;
		}
		
		return headerRenderer.getActiveColumn();
	}

    /**
     * Order by column.
     *
     * @param columnIndex the column index
     */
    public abstract void orderByColumn(int columnIndex);
	
	@Override
	public void mouseClicked(MouseEvent event) {
		logger.trace(event);
		if (event.getSource() instanceof JTableHeader) {
			JTableHeader tableHeader = (JTableHeader) event.getSource();
			JTable table = tableHeader.getTable();
			TableColumnModel colModel = table.getColumnModel();
			
			int index = colModel.getColumnIndexAtX(event.getX());
			
			if (index == -1) {
				return;
			}
			
//			Rectangle headerRect = table.getTableHeader().getHeaderRect(index);
			
			logger.trace("Clicked index=" + index);
			if (isSortable()[index]) {
				logger.debug("Sort column index=" + index);
				orderByColumn(index);
				headerRenderer.setActiveColumn(index);
			}
//
//			if (index == 0) {
//				headerRect.width -= 10;
//			} else {
//				headerRect.grow(-10, 0);
//			}
//			
//			if (!headerRect.contains(event.getX(), event.getY())) {
//				int vLeftColIndex = index;
//				
//				if (event.getX() < headerRect.x) {
//					vLeftColIndex--;
//				}
//				
//				logger.debug("Clicked column=" + vLeftColIndex);
//			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
