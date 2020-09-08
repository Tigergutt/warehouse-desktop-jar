package se.melsom.warehouse.report.component.property;

/**
 * The type Line.
 */
public class Line {
	private float width;
	private Point from;
	private Point to;

    /**
     * Instantiates a new Line.
     *
     * @param width the width
     * @param from  the from
     * @param to    the to
     */
    public Line(float width, Point from, Point to) {
		this.width = width;
		this.from = from;
		this.to = to;
	}

    /**
     * Gets width.
     *
     * @return the width
     */
    public float getWidth() {
		return width;
	}

    /**
     * Sets width.
     *
     * @param width the width
     */
    public void setWidth(float width) {
		this.width = width;
	}

    /**
     * Gets from.
     *
     * @return the from
     */
    public Point getFrom() {
		return from;
	}

    /**
     * Sets from.
     *
     * @param from the from
     */
    public void setFrom(Point from) {
		this.from = from;
	}

    /**
     * Gets to.
     *
     * @return the to
     */
    public Point getTo() {
		return to;
	}

    /**
     * Sets to.
     *
     * @param to the to
     */
    public void setTo(Point to) {
		this.to = to;
	}
}
