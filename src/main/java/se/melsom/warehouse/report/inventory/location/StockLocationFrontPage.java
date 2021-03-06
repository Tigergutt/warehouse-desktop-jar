package se.melsom.warehouse.report.inventory.location;

import se.melsom.warehouse.report.component.FormField;
import se.melsom.warehouse.report.component.property.Alignment;
import se.melsom.warehouse.report.component.property.TrueTypeFont;
import se.melsom.warehouse.report.inventory.InventoryLandscapeTable;

public class StockLocationFrontPage extends StockLocationPage {
    public static final int INVENTORY_TABLE_ROW_COUNT = 12;
	
	private static final float LOCATION_FIELD_X = 10;
	private static final float LOCATION_FIELD_Y = 10;
	private static final float LOCATION_FIELD_WIDTH = 92;
	private static final float LOCATION_CAPTION_HEIGHT = 10;
	private static final float LOCATION_VALUE_HEIGHT = 40;

	private static final float DATE_FIELD_X = 225;
	private static final float DATE_FIELD_Y = 10;	
	private static final float DATE_FIELD_WIDTH = 38;
	private static final float DATE_CAPTION_HEIGHT = 5;
	private static final float DATE_VALUE_HEIGHT = 10;

	private static final float PAGE_FIELD_X = DATE_FIELD_X + DATE_FIELD_WIDTH;
	private static final float PAGE_FIELD_Y = DATE_FIELD_Y;	
	private static final float PAGE_FIELD_WIDTH = 17;
	private static final float PAGE_CAPTION_HEIGHT = 5;
	private static final float PAGE_VALUE_HEIGHT = 10;

	private static final float HOLDING_FIELD_X = DATE_FIELD_X;
	private static final float HOLDING_FIELD_Y = DATE_FIELD_Y + DATE_CAPTION_HEIGHT + DATE_VALUE_HEIGHT;	
	private static final float HOLDING_FIELD_WIDTH = DATE_FIELD_WIDTH + PAGE_FIELD_WIDTH;
	private static final float HOLDING_CAPTION_HEIGHT = 5;
	private static final float HOLDING_VALUE_HEIGHT = 10;

	private static final float INVENTORY_TABLE_X = 10;
	private static final float INVENTORY_Y = 70;
	
	private final FormField locationField;
	private final FormField pageNumberField;
	private final FormField cycleCountingDateField;
	private final FormField holdingField;
	private final InventoryLandscapeTable table;

    public StockLocationFrontPage(String reportName, String date) {
		super(reportName, date);
		locationField = new FormField(LOCATION_FIELD_X, LOCATION_FIELD_Y, LOCATION_FIELD_WIDTH, 
				LOCATION_CAPTION_HEIGHT, LOCATION_VALUE_HEIGHT);

		locationField.setCaptionText("Lagerplats");
		locationField.setValueAlignment(Alignment.CENTER);
		locationField.setValueText("XX:00");
		locationField.getValue().setFont(TrueTypeFont.FM_SANS_STENCIL);

		super.addComponent(locationField);
		
		cycleCountingDateField = new FormField(DATE_FIELD_X, DATE_FIELD_Y, DATE_FIELD_WIDTH, 
				DATE_CAPTION_HEIGHT, DATE_VALUE_HEIGHT);
		
		cycleCountingDateField.setCaptionText("Inventerad");
		cycleCountingDateField.setValueText("9999-99-99");

		super.addComponent(cycleCountingDateField);

		pageNumberField = new FormField(PAGE_FIELD_X, PAGE_FIELD_Y, PAGE_FIELD_WIDTH, 
				PAGE_CAPTION_HEIGHT, PAGE_VALUE_HEIGHT);
		
		pageNumberField.setCaptionText("Sida");
		pageNumberField.setValueText("0 (0)");

		
		super.addComponent(pageNumberField);

		holdingField = new FormField(HOLDING_FIELD_X, HOLDING_FIELD_Y, HOLDING_FIELD_WIDTH, 
				HOLDING_CAPTION_HEIGHT, HOLDING_VALUE_HEIGHT);
		
		holdingField.setCaptionText("Ansvar");
		holdingField.setValueAlignment(Alignment.CENTER);
		holdingField.setValueText("XX");
		holdingField.getValue().setFont(TrueTypeFont.FM_SANS_STENCIL);

		
		super.addComponent(holdingField);

		table = new InventoryLandscapeTable(INVENTORY_TABLE_X, INVENTORY_Y, INVENTORY_TABLE_ROW_COUNT);
		
		super.addComponent(table);
	}
	
	@Override
	public int getRowCapacity() {
		return INVENTORY_TABLE_ROW_COUNT;
	}
	
	@Override
	public void setLocation(String designator) {
		locationField.getValue().setText(designator);
	}
	
	@Override
	public void setCycleCountingDate(String date) {
		cycleCountingDateField.setValueText(date);
	}

	@Override
	public void setHoldingUnit(String holdingUnit) {
		holdingField.setValueText(holdingUnit);
	}

	@Override
	public void setPageNumber(int pageNumber) {
		super.setPageNumber(pageNumber);
		
		updatePageNumberField();
	}

	@Override
	public void setPageCount(int pageCount) {
		super.setPageCount(pageCount);
		
		updatePageNumberField();
	}
	
	
	@Override
	public InventoryLandscapeTable getTable() {
		return table;
	}
	
	private void updatePageNumberField() {
		pageNumberField.getValue().setText(getPageNumber() + " (" + getPageCount() + ")");
	}
}
