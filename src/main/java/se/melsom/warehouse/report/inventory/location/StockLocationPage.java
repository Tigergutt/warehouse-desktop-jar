package se.melsom.warehouse.report.inventory.location;

import se.melsom.warehouse.report.component.Page;
import se.melsom.warehouse.report.component.TextBox;
import se.melsom.warehouse.report.inventory.InventoryLandscapeTable;

public abstract class StockLocationPage extends Page {
	private static final float HEADER_FIELD_X = 130;
	private static final float HEADER_FIELD_Y = 10;	
	private static final float HEADER_FIELD_WIDTH = 120;
	private static final float HEADER_TEXT_HEIGHT = 10;
	private static final float DATE_TEXT_HEIGHT = 7;


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

    public abstract void setLocation(String designator);

    public abstract void setCycleCountingDate(String date);

    public abstract InventoryLandscapeTable getTable();

    public abstract int getRowCapacity();

    public abstract void setHoldingUnit(String holdingUnit);
}
