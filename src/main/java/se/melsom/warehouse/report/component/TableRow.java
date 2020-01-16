package se.melsom.warehouse.report.component;

public class TableRow extends Frame {
	public TableRow(float x, float y) {
		super(x, y, 0, 0);
	}
	
	@Override
	public float getWidth() {
		float width = 0;
		
		for (Component cell : getComponents()) {
			width += cell.getWidth();
		}
		
		return width;
	}

	@Override
	public float getHeight() {
		float height = 0;
		
		for (Component cell : getComponents()) {
			height = Math.max(height, cell.getHeight());
		}
		
		return height;
	}
}
