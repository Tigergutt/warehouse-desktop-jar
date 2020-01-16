package se.melsom.warehouse.report.component;

import se.melsom.warehouse.report.component.property.Line;
import se.melsom.warehouse.report.component.property.Point;
import se.melsom.warehouse.report.component.property.Position;

public class Frame extends Component {
	Line[] lines = {null, null, null, null};
	
	public Frame(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public Line[] getLines() {
		return lines;
	}
	
	public Line getLine(Position position) {
		return lines[position.ordinal()];
	}
	
	public void setLines(float width) {
		setLines(width, Position.TOP, Position.RIGHT, Position.BOTTOM, Position.LEFT);
	}
	
	public void setLines(float width, Position... positions) {
		for (Position position : positions) {
			Point from = null;
			Point to = null;
			
			switch (position) {
			case TOP:
				from = new Point(getX(), getY() + getHeight());
				to = new Point(getX() + getWidth(), getY() + getHeight());
				break;
				
			case LEFT:
				from = new Point(getX() + getWidth(), getY());
				to = new Point(getX() + getWidth(), getY() + getHeight());
				break;

			case BOTTOM:
				from = new Point(getX(), getY());
				to = new Point(getX() + getWidth(), getY());
				break;
				
			case RIGHT:
				from = new Point(getX(), getY());
				to = new Point(getX(), getY() + getHeight());
				break;
			}
			
			lines[position.ordinal()] = new Line(width, from, to);
		}
	}
}
