package se.melsom.warehouse.presentation.stock;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import se.melsom.warehouse.model.entity.inventory.StockOnHand;
 
@SuppressWarnings("serial")
public class QuantityCellRenderer extends JLabel implements TableCellRenderer {
	private StockOnHandTableModel tableModel;
	
    public QuantityCellRenderer(StockOnHandTableModel tableModel) {
    	this.tableModel = tableModel;
    	super.setOpaque(true);
    	super.setHorizontalAlignment(SwingConstants.RIGHT);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	StockOnHand stockOnHand = tableModel.getStockOnHand().get(row);
    	
    	if (stockOnHand.getActualQuantity() < stockOnHand.getNominalQuantity()) {
    		super.setForeground(Color.RED);
    	} else if (stockOnHand.getActualQuantity() > stockOnHand.getNominalQuantity()) {
    		super.setForeground(Color.BLUE);
    	} else {
    		super.setForeground(Color.BLACK);
    	}
    	setText(value.toString());

        return this;
    }
 
}