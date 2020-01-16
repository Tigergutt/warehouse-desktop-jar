package se.melsom.warehouse.report.component.property;

public class Inset {
	private float width;
	private Position position;

	public Inset(float width, Position position) {
		this.width = width;
		this.position = position;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}
