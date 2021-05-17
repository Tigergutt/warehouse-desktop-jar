package se.melsom.warehouse.report.component.property;

public class Line {
	private float width;
	private Point from;
	private Point to;

    public Line(float width, Point from, Point to) {
		this.width = width;
		this.from = from;
		this.to = to;
	}

    public float getWidth() {
		return width;
	}

    public void setWidth(float width) {
		this.width = width;
	}

    public Point getFrom() {
		return from;
	}

    public void setFrom(Point from) {
		this.from = from;
	}

    public Point getTo() {
		return to;
	}

    public void setTo(Point to) {
		this.to = to;
	}
}
