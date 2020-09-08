package se.melsom.warehouse.report.component.property;

/**
 * The type Inset.
 */
public class Inset {
	private float width;
	private Position position;

    /**
     * Instantiates a new Inset.
     *
     * @param width    the width
     * @param position the position
     */
    public Inset(float width, Position position) {
		this.width = width;
		this.position = position;
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
     * Gets position.
     *
     * @return the position
     */
    public Position getPosition() {
		return position;
	}

    /**
     * Sets position.
     *
     * @param position the position
     */
    public void setPosition(Position position) {
		this.position = position;
	}

}
