package se.melsom.warehouse.report.inventory;

import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.report.component.Page;
import se.melsom.warehouse.report.component.TextBox;
import se.melsom.warehouse.report.component.property.Alignment;
import se.melsom.warehouse.report.component.property.TrueTypeFont;

import java.util.Vector;

public class InventoryPage extends Page {
	private static final float CALL_SIGN_FIELD_X = 20;
	private static final float CALL_SIGN_FIELD_Y = 7;
	private static final float CALL_SIGN_FIELD_WIDTH = 40;
	private static final float CALL_SIGN_FIELD_HEIGHT = 15;
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
	
	private final TextBox pageNumberField;
	private final InventoryPortraitTable table;

    public InventoryPage(String reportName, String callSign, String date) {
		super(Orientation.PORTRAIT);
		
		TextBox callSignBox = new TextBox(CALL_SIGN_FIELD_X, CALL_SIGN_FIELD_Y, CALL_SIGN_FIELD_WIDTH, CALL_SIGN_FIELD_HEIGHT);
		callSignBox.setText(callSign);
		callSignBox.setFont(TrueTypeFont.FM_SANS_STENCIL);
		addComponent(callSignBox);
		
		TextBox reportTitle = new TextBox(HEADER_FIELD_X, HEADER_FIELD_Y, HEADER_FIELD_WIDTH, HEADER_TEXT_HEIGHT);
		reportTitle.setText(reportName);
		reportTitle.setFont(TrueTypeFont.FM_SANS_BOLD);
		addComponent(reportTitle);

		TextBox reportDate = new TextBox(HEADER_FIELD_X, HEADER_FIELD_Y + HEADER_TEXT_HEIGHT, HEADER_FIELD_WIDTH, DATE_TEXT_HEIGHT);
		reportDate.setText(date);
		addComponent(reportDate);
		
		pageNumberField = new TextBox(PAGE_NUMBER_FIELD_X, PAGE_NUMBER_FIELD_Y, PAGE_NUMBER_FIELD_WIDTH, PAGE_NUMBER_TEXT_HEIGHT);
		pageNumberField.setAlignment(Alignment.RIGHT);
		addComponent(pageNumberField);

		if (getOrientation() == Orientation.PORTRAIT) {
			table = new InventoryPortraitTable(INVENTORY_TABLE_HAND_X, INVENTORY_TABLE_HAND_Y, INVENTORY_TABLE_ROW_COUNT);
			addComponent(table);

			String[] header = {
					EntityName.STOCK_LOCATION_DESIGNATION,
					EntityName.ITEM_NUMBER,
					EntityName.ITEM_NAME,
					EntityName.INVENTORY_ACTUAL_QUANTITY,
					EntityName.INVENTORY_IDENTITY
			};

			table.setRowValues(0, header);
		} else {
			table = new InventoryPortraitTable(INVENTORY_TABLE_HAND_X, INVENTORY_TABLE_HAND_Y, INVENTORY_TABLE_ROW_COUNT);
			addComponent(table);

			String[] header = {
					EntityName.ITEM_NUMBER,
					EntityName.ITEM_NAME,
					EntityName.INVENTORY_ACTUAL_QUANTITY
			};

			table.setRowValues(0, header);
		}
	}

    public int getRowCapacity() {
		return INVENTORY_TABLE_ROW_COUNT;
	}

	public void setInventoryRow(int rowIndex, ActualInventoryVO item) {
		if (getOrientation() == Orientation.PORTRAIT) {
			String[] values = {
					item.getStockLocation().getLocationLabel(),
					item.getItem().getNumber(),
					item.getItem().getName(),
					String.format("%d", item.getQuantity()),
					item.getIdentity()
			};

			table.setRowValues(rowIndex, values);
		} else {
			String[] values = {
					item.getItem().getNumber(),
					item.getItem().getName(),
					String.format("%d", item.getQuantity())
			};

			table.setRowValues(rowIndex, values);
		}
	}


	public void setInventoryRow(int rowIndex, Vector<String> values) {
		table.setRowValues(rowIndex, values);
	}

    public void setInventoryRow(int rowIndex, String... values) {
		table.setRowValues(rowIndex, values);
	}

    public void updatePageNumberField() {
		pageNumberField.setText("Sida " + getPageNumber() + " (" + getPageCount() + ")");
	}
}
