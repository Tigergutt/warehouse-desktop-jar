package se.melsom.warehouse.event;

public class ModelEvent {
	private EventType type;
	
	public ModelEvent(EventType type) {
		this.type = type;
	}
	
	public EventType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return  "ModelEvent(type=" + getType() + ")";
	}
}
