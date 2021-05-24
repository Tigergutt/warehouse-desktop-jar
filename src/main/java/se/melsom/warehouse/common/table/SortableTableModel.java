package se.melsom.warehouse.common.table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Detta är en basklass för att hantera sortering i tabeller.
 * Sortering kan göras på en konfigurerbar/valfri kolumn.
 */
public abstract class SortableTableModel extends AbstractTableModel implements MouseListener {
	private static final Logger logger = LoggerFactory.getLogger(SortableTableModel.class);
	private SortableTableRenderer headerRenderer = null;

    public void setHeaderRenderer(SortableTableRenderer headerRenderer) {
		this.headerRenderer = headerRenderer;
	}

    public SortableTableRenderer getHeaderRenderer() {
		return headerRenderer;
	}

    public abstract int getColumnWidth(int columnIndex);

    public abstract boolean[] isSortable();

    public int getActiveColumn() {
		if (headerRenderer == null ) {
			return 0;
		}
		
		return headerRenderer.getActiveColumn();
	}

    public abstract void orderByColumn(int columnIndex);
	
	@Override
	public void mouseClicked(MouseEvent event) {
		logger.trace("{}", event);
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
