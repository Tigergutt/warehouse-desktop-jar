package se.melsom.warehouse.application.inventory.status;

import se.melsom.warehouse.data.vo.StockOnHandVO;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Används för att förtydliga lagersaldo genom att färga cellen som visar aktuellt saldo för en
 * viss artikel:
 * Svart - balans; aktuellt antal är lika med nominellt antal artiklar.
 * Blått - överskott; aktuellt antal är överstiger nominellt antal artiklar.
 * Rött  - brist/underskott; aktuellt antal är understiger nominellt antal artiklar.
 */
public class QuantityCellRenderer extends JLabel implements TableCellRenderer {
	private final ContentModel tableModel;

    public QuantityCellRenderer(ContentModel tableModel) {
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