package se.melsom.warehouse.presentation.stock;

import se.melsom.warehouse.data.vo.StockOnHandVO;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

@Deprecated
public class QuantityCellRenderer extends JLabel implements TableCellRenderer {
	private final StockOnHandTableModel tableModel;

    public QuantityCellRenderer(StockOnHandTableModel tableModel) {
    	this.tableModel = tableModel;
    	super.setOpaque(true);
    	super.setHorizontalAlignment(SwingConstants.RIGHT);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	StockOnHandVO stockOnHand = tableModel.getStockOnHand().get(row);
    	
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