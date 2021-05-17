package se.melsom.warehouse.report.component;

import se.melsom.warehouse.report.component.property.Alignment;
import se.melsom.warehouse.report.component.property.Inset;
import se.melsom.warehouse.report.component.property.Position;

import java.util.Vector;

public abstract class Component {
	private final Vector<Component> components = new Vector<>();
	private final Inset[] insets = { null, null, null, null};
	private Alignment alignment = Alignment.LEFT;
	private float x;
	private float y;
	private float width;
	private float height;

    public Component(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

    public Vector<Component> getComponents() {
		return components;
	}

    public void addComponent(Component component) {
//		component.setX(this.getX() + component.getX());
//		component.setY(this.getY() + component.getY());
		components.addElement(component);
	}

    public void setInsets(float width) {
		setInsets(width, Position.TOP, Position.RIGHT, Position.BOTTOM, Position.LEFT);
	}

    public Inset[] getInsets() {
		return insets;
	}

    public Inset getInset(Position position) {
		return insets[position.ordinal()];
	}

    public void setInsets(float width, Position... positions) {
		for (Position position : positions) {
			insets[position.ordinal()] = new Inset(width, position);
		}
	}

    public Alignment getAlignment() {
		return alignment;
	}

    public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

    public float getX() {
		return x;
	}

    public void setX(float x) {
		this.x = x;
	}

    public float getY() {
		return y;
	}

    public void setY(float y) {
		this.y = y;
	}

    public float getWidth() {
		return width;
	}

    public void setWidth(float width) {
		this.width = width;
	}

    public float getHeight() {
		return height;
	}

    public void setHeight(float height) {
		this.height = height;
	}
}
