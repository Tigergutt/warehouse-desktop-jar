package se.melsom.warehouse.settings;

/**
 * The type Window settings.
 */
public class WindowSettings {
	private PersistentSettings parent;
	private String name;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean isVisible;

    /**
     * Instantiates a new Window settings.
     *
     * @param name      the name
     * @param x         the x
     * @param y         the y
     * @param width     the width
     * @param height    the height
     * @param isVisible the is visible
     */
    public WindowSettings(String name, int x, int y, int width, int height, boolean isVisible) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isVisible = isVisible;
	}

    /**
     * Sets parent.
     *
     * @param parent the parent
     */
    public void setParent(PersistentSettings parent) {
		this.parent = parent;
	}

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return name;
	}

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
		return x;
	}

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(int x) {
		this.x = x;
		
		if (parent != null) {
			parent.setDirty(true);
		}
	}

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
		return y;
	}

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(int y) {
		this.y = y;
		
		if (parent != null) {
			parent.setDirty(true);
		}
	}

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
		return width;
	}

    /**
     * Sets width.
     *
     * @param width the width
     */
    public void setWidth(int width) {
		this.width = width;
		
		if (parent != null) {
			parent.setDirty(true);
		}
	}

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
		return height;
	}

    /**
     * Sets height.
     *
     * @param height the height
     */
    public void setHeight(int height) {
		this.height = height;
		
		if (parent != null) {
			parent.setDirty(true);
		}
	}

    /**
     * Is visible boolean.
     *
     * @return the boolean
     */
    public boolean isVisible() {
		return isVisible;
	}

    /**
     * Sets visible.
     *
     * @param isVisible the is visible
     */
    public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
		
		if (parent != null) {
			parent.setDirty(true);
		}
	}

	@Override
	public String toString() {
		return "(" + name + ",x=" + x + ",y=" + y + ",width=" + width + ",height=" + height + ")";
	}
}
