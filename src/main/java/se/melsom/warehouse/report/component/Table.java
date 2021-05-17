package se.melsom.warehouse.report.component;


public class Table extends Frame {
    public Table(float x, float y) {
		super(x, y, 0, 0);
	}
	
	@Override
	public float getWidth() {
		float width = 0;
		
		for (Component row : getComponents()) {
			width = Math.max(width, row.getWidth());
		}
		
		return width;
	}

	@Override
	public float getHeight() {
		float height = 0;
		
		for (Component row : getComponents()) {
			height += row.getHeight();
		}
		
		return height;
	}
}
