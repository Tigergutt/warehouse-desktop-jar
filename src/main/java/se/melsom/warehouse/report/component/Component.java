package se.melsom.warehouse.report.component;

import java.util.Vector;

import se.melsom.warehouse.report.component.property.Alignment;
import se.melsom.warehouse.report.component.property.Inset;
import se.melsom.warehouse.report.component.property.Position;

/**
 * The type Component.
 */
public abstract class Component {
	private Vector<Component> components = new Vector<>();
	private Inset[] insets = { null, null, null, null};
	private Alignment alignment = Alignment.LEFT;
	private float x;
	private float y;
	private float width;
	private float height;

    /**
     * Instantiates a new Component.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     */
    public Component(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

    /**
     * Gets components.
     *
     * @return the components
     */
    public Vector<Component> getComponents() {
		return components;
	}

    /**
     * Add component.
     *
     * @param component the component
     */
    public void addComponent(Component component) {
//		component.setX(this.getX() + component.getX());
//		component.setY(this.getY() + component.getY());
		components.addElement(component);
	}

    /**
     * Sets insets.
     *
     * @param width the width
     */
    public void setInsets(float width) {
		setInsets(width, Position.TOP, Position.RIGHT, Position.BOTTOM, Position.LEFT);
	}

    /**
     * Get insets inset [ ].
     *
     * @return the inset [ ]
     */
    public Inset[] getInsets() {
		return insets;
	}

    /**
     * Gets inset.
     *
     * @param position the position
     * @return the inset
     */
    public Inset getInset(Position position) {
		return insets[position.ordinal()];
	}

    /**
     * Sets insets.
     *
     * @param width     the width
     * @param positions the positions
     */
    public void setInsets(float width, Position... positions) {
		for (Position position : positions) {
			insets[position.ordinal()] = new Inset(width, position);
		}
	}

    /**
     * Gets alignment.
     *
     * @return the alignment
     */
    public Alignment getAlignment() {
		return alignment;
	}

    /**
     * Sets alignment.
     *
     * @param alignment the alignment
     */
    public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
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
     * Gets height.
     *
     * @return the height
     */
    public float getHeight() {
		return height;
	}

    /**
     * Sets height.
     *
     * @param height the height
     */
    public void setHeight(float height) {
		this.height = height;
	}
}
