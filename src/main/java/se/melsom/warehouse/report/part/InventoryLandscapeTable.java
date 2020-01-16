package se.melsom.warehouse.report.part;

import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.report.component.Component;
import se.melsom.warehouse.report.component.Table;
import se.melsom.warehouse.report.component.TableRow;
import se.melsom.warehouse.report.component.TextBox;
import se.melsom.warehouse.report.component.property.Alignment;
import se.melsom.warehouse.report.component.property.Position;
import se.melsom.warehouse.report.component.property.TrueTypeFont;

public class InventoryLandscapeTable extends Table {
	private static Logger logger = Logger.getLogger(InventoryLandscapeTable.class);
	
	private static final float FRAME_LINE_WIDTH = 0.5f;
	private static final float INNER_FRAME_LINE_WIDTH = 0.1f;
	private static final float TABLE_ROW_HEIGHT = 10;
	private static final float INSET_WIDTH = 2;
	private static final float[] CELL_WIDTH = { 60, 150, 60 };
	private static final Alignment[] CELL_ALIGNMENT = { Alignment.LEFT, Alignment.LEFT, Alignment.RIGHT };

	public InventoryLandscapeTable(float x, float y, int rowCount) {
		super(x, y);
		float rowY = y;
		
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			float rowX = x;
			TableRow row = new TableRow(rowX, rowY);
			
			for (int columnIndex = 0; columnIndex < CELL_WIDTH.length; columnIndex++) {
				float columnWidth = CELL_WIDTH[columnIndex];
				Alignment alignment = CELL_ALIGNMENT[columnIndex];
				
				TextBox cell = new TextBox(rowX, rowY, columnWidth, TABLE_ROW_HEIGHT);

				cell.setLines(INNER_FRAME_LINE_WIDTH);
				cell.setInsets(INSET_WIDTH);

				if (rowIndex == 0) {
					cell.setFont(TrueTypeFont.FM_SANS_BOLD);
					cell.setLines(FRAME_LINE_WIDTH);
				} else {
					cell.setAlignment(alignment);

					switch (alignment) {
					case RIGHT:
						cell.setInsets(5, Position.RIGHT);
						break;
						
					default:
						break;
					}
				}
				
				row.addComponent(cell);
				rowX += columnWidth;
			}
			
			rowY += TABLE_ROW_HEIGHT;
			
			addComponent(row);
		}
		
		setLines(FRAME_LINE_WIDTH);
	}
	
	public void setRowValues(int rowIndex, ActualInventory item) {
		setRowValues(rowIndex, 
				item.getItem().getNumber(), 
				item.getItem().getName(), 
				"" + item.getQuantity());
	}
	
	public void setRowValues(int rowIndex, String... values) {
		Component row = getComponents().get(rowIndex);

		for (int columnIndex = 0; columnIndex < values.length; columnIndex++) {
			if (columnIndex >= row.getComponents().size()) {
				logger.warn("Column index=" + columnIndex + " is out of range=" + row.getComponents().size());
				return;
			}
			
			TextBox textBox = (TextBox) row.getComponents().get(columnIndex);
			
			textBox.setText(values[columnIndex] == null ? "" : values[columnIndex]);
		}
	}
	
	public void setRowValues(int rowIndex, Vector<String> values) {
		Component row = getComponents().get(rowIndex);

		for (int columnIndex = 0; columnIndex < values.size(); columnIndex++) {
			if (columnIndex >= row.getComponents().size()) {
				logger.warn("Column index=" + columnIndex + " is out of range=" + row.getComponents().size());
				return;
			}
			
			TextBox textBox = (TextBox) row.getComponents().get(columnIndex);
			
			textBox.setText(values.get(columnIndex));
		}
	}
}
