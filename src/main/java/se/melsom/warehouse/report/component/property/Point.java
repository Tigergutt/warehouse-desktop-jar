package se.melsom.warehouse.report.component.property;

/**
 * The type Point.
 */
public class Point {
	private float x;
	private float y;

    /**
     * Instantiates a new Point.
     *
     * @param x the x
     * @param y the y
     */
    public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

    /**
     * Gets x.
     *
     * @return the x
     */
    public float getX() {
		return x;
	}

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(float x) {
		this.x = x;
	}

    /**
     * Gets y.
     *
     * @return the y
     */
    public float getY() {
		return y;
	}

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(float y) {
		this.y = y;
	}
}
