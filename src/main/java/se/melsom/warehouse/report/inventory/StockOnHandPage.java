package se.melsom.warehouse.report.inventory;

import java.util.Vector;

import se.melsom.warehouse.model.entity.inventory.StockOnHand;
import se.melsom.warehouse.report.component.Page;
import se.melsom.warehouse.report.component.TextBox;
import se.melsom.warehouse.report.component.property.Alignment;
import se.melsom.warehouse.report.part.StockOnHandTable;

/**
 * The type Stock on hand page.
 */
public class StockOnHandPage extends Page {
	private static final float HEADER_FIELD_X = 10;
	private static final float HEADER_FIELD_Y = 10;	
	private static final float HEADER_FIELD_WIDTH = 120;
	private static final float HEADER_TEXT_HEIGHT = 5;
	private static final float DATE_TEXT_HEIGHT = 5;
	private static final float PAGE_NUMBER_FIELD_X = 227;
	private static final float PAGE_NUMBER_FIELD_Y = 10;	
	private static final float PAGE_NUMBER_FIELD_WIDTH = 60;
	private static final float PAGE_NUMBER_TEXT_HEIGHT = 5;
	private static final int STOCK_ON_HAND_TABLE_ROW_COUNT = 28;
	private static final float STOCK_ON_HAND_X = 10;
	private static final float STOCK_ON_HAND_Y = 32;
	
	private TextBox pageNumberField;
	private StockOnHandTable table;

    /**
     * Instantiates a new Stock on hand page.
     *
     * @param reportName the report name
     * @param date       the date
     */
    public StockOnHandPage(String reportName, String date) {
		super(Orientation.LANDSCAPE);
		
		TextBox reportTitle = new TextBox(HEADER_FIELD_X, HEADER_FIELD_Y, 
				HEADER_FIELD_WIDTH, HEADER_TEXT_HEIGHT);
		reportTitle.setText(reportName + " " + date);
		addComponent(reportTitle);

		TextBox reportDate = new TextBox(HEADER_FIELD_X, HEADER_FIELD_Y + HEADER_TEXT_HEIGHT, 
				HEADER_FIELD_WIDTH, DATE_TEXT_HEIGHT);		

		addComponent(reportDate);
		
		pageNumberField = new TextBox(PAGE_NUMBER_FIELD_X, PAGE_NUMBER_FIELD_Y, 
				PAGE_NUMBER_FIELD_WIDTH, PAGE_NUMBER_TEXT_HEIGHT);
		pageNumberField.setAlignment(Alignment.RIGHT);
		addComponent(pageNumberField);

		table = new StockOnHandTable(STOCK_ON_HAND_X, STOCK_ON_HAND_Y, STOCK_ON_HAND_TABLE_ROW_COUNT);		
		addComponent(table);
	}

    /**
     * Gets row capacity.
     *
     * @return the row capacity
     */
    public int getRowCapacity() {
		return STOCK_ON_HAND_TABLE_ROW_COUNT;
	}

    /**
     * Sets stock on hand row.
     *
     * @param rowIndex the row index
     * @param item     the item
     */
    public void setStockOnHandRow(int rowIndex, StockOnHand item) {
		table.setRowValues(rowIndex, item);
	}

    /**
     * Sets stock on hand row.
     *
     * @param rowIndex the row index
     * @param values   the values
     */
    public void setStockOnHandRow(int rowIndex, Vector<String> values) {
		table.setRowValues(rowIndex, values);
	}

    /**
     * Update page number field.
     */
    public void updatePageNumberField() {
		pageNumberField.setText("Sida " + getPageNumber() + " (" + getPageCount() + ")");
	}
}
