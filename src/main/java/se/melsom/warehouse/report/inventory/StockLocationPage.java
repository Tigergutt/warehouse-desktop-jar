package se.melsom.warehouse.report.inventory;

import se.melsom.warehouse.report.component.Page;
import se.melsom.warehouse.report.component.TextBox;
import se.melsom.warehouse.report.part.InventoryLandscapeTable;

/**
 * The type Stock location page.
 */
public abstract class StockLocationPage extends Page {
	private static final float HEADER_FIELD_X = 130;
	private static final float HEADER_FIELD_Y = 10;	
	private static final float HEADER_FIELD_WIDTH = 120;
	private static final float HEADER_TEXT_HEIGHT = 10;
	private static final float DATE_TEXT_HEIGHT = 7;


    /**
     * Instantiates a new Stock location page.
     *
     * @param reportName the report name
     * @param date       the date
     */
    public StockLocationPage(String reportName, String date) {
		super(Orientation.LANDSCAPE);		
		
		TextBox reportTitle = new TextBox(HEADER_FIELD_X, HEADER_FIELD_Y, 
				HEADER_FIELD_WIDTH, HEADER_TEXT_HEIGHT);
		TextBox reportDate = new TextBox(HEADER_FIELD_X, HEADER_FIELD_Y + HEADER_TEXT_HEIGHT, 
				HEADER_FIELD_WIDTH, DATE_TEXT_HEIGHT);
		
		reportTitle.setText(reportName);
		reportDate.setText(date);

		super.addComponent(reportTitle);
		super.addComponent(reportDate);
	}

    /**
     * Sets location.
     *
     * @param designator the designator
     */
    public abstract void setLocation(String designator);

    /**
     * Sets cycle counting date.
     *
     * @param date the date
     */
    public abstract void setCycleCountingDate(String date);

    /**
     * Gets table.
     *
     * @return the table
     */
    public abstract InventoryLandscapeTable getTable();

    /**
     * Gets row capacity.
     *
     * @return the row capacity
     */
    public abstract int getRowCapacity();

    /**
     * Sets holding unit.
     *
     * @param holdingUnit the holding unit
     */
    public abstract void setHoldingUnit(String holdingUnit);
}
