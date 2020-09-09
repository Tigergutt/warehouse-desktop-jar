package se.melsom.warehouse.report.inventory;

import java.util.Vector;

import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.report.component.Page;
import se.melsom.warehouse.report.component.TextBox;
import se.melsom.warehouse.report.component.property.Alignment;
import se.melsom.warehouse.report.component.property.TrueTypeFont;
import se.melsom.warehouse.report.part.InventoryPortraitTable;

/**
 * The type Inventory page.
 */
public class InventoryPage extends Page {
	private static final float CALLSIGN_FIELD_X = 20;
	private static final float CALLSIGN_FIELD_Y = 7;	
	private static final float CALLSIGN_FIELD_WIDTH = 40;	
	private static final float CALLSIGN_FIELD_HEIGHT = 15;	
	private static final float HEADER_FIELD_X = 90;
	private static final float HEADER_FIELD_Y = 10;	
	private static final float HEADER_FIELD_WIDTH = 90;
	private static final float HEADER_TEXT_HEIGHT = 5;
	private static final float DATE_TEXT_HEIGHT = 5;
	private static final float PAGE_NUMBER_FIELD_X = 165;
	private static final float PAGE_NUMBER_FIELD_Y = 10;	
	private static final float PAGE_NUMBER_FIELD_WIDTH = 30;
	private static final float PAGE_NUMBER_TEXT_HEIGHT = 4;
	private static final int INVENTORY_TABLE_ROW_COUNT = 50;
	private static final float INVENTORY_TABLE_HAND_X = 20;
	private static final float INVENTORY_TABLE_HAND_Y = 25;
	
	private TextBox pageNumberField;
	private InventoryPortraitTable table;

    /**
     * Instantiates a new Inventory page.
     *
     * @param reportName the report name
     * @param callsign   the callsign
     * @param date       the date
     */
    public InventoryPage(String reportName, String callsign, String date) {
		super(Orientation.PORTRAIT);
		
		TextBox callsignBox = new TextBox(CALLSIGN_FIELD_X, CALLSIGN_FIELD_Y, 
				CALLSIGN_FIELD_WIDTH, CALLSIGN_FIELD_HEIGHT);
		callsignBox.setText(callsign);
		callsignBox.setFont(TrueTypeFont.FM_SANS_STENCIL);
		addComponent(callsignBox);
		
		TextBox reportTitle = new TextBox(HEADER_FIELD_X, HEADER_FIELD_Y, 
				HEADER_FIELD_WIDTH, HEADER_TEXT_HEIGHT);
		reportTitle.setText(reportName);
		reportTitle.setFont(TrueTypeFont.FM_SANS_BOLD);
		addComponent(reportTitle);

		TextBox reportDate = new TextBox(HEADER_FIELD_X, HEADER_FIELD_Y + HEADER_TEXT_HEIGHT, 
				HEADER_FIELD_WIDTH, DATE_TEXT_HEIGHT);		

		reportDate.setText(date);
		addComponent(reportDate);
		
		pageNumberField = new TextBox(PAGE_NUMBER_FIELD_X, PAGE_NUMBER_FIELD_Y, 
				PAGE_NUMBER_FIELD_WIDTH, PAGE_NUMBER_TEXT_HEIGHT);
		pageNumberField.setAlignment(Alignment.RIGHT);
		addComponent(pageNumberField);

		table = new InventoryPortraitTable(INVENTORY_TABLE_HAND_X, INVENTORY_TABLE_HAND_Y, INVENTORY_TABLE_ROW_COUNT);		
		addComponent(table);
	}

    /**
     * Gets row capacity.
     *
     * @return the row capacity
     */
    public int getRowCapacity() {
		return INVENTORY_TABLE_ROW_COUNT;
	}

    /**
     * Sets inventory row.
     *
     * @param rowIndex the row index
     * @param item     the item
     */
    public void setInventoryRow(int rowIndex, ActualInventory item) {
		table.setRowValues(rowIndex, item);
	}

    /**
     * Sets inventory row.
     *
     * @param rowIndex the row index
     * @param values   the values
     */
    public void setInventoryRow(int rowIndex, Vector<String> values) {
		table.setRowValues(rowIndex, values);
	}

    /**
     * Sets inventory row.
     *
     * @param rowIndex the row index
     * @param values   the values
     */
    public void setInventoryRow(int rowIndex, String... values) {
		table.setRowValues(rowIndex, values);
	}

    /**
     * Update page number field.
     */
    public void updatePageNumberField() {
		pageNumberField.setText("Sida " + getPageNumber() + " (" + getPageCount() + ")");
	}
}
