package se.melsom.warehouse.event;

/**
 * The type Model event.
 */
public class ModelEvent {
	private EventType type;

    /**
     * Instantiates a new Model event.
     *
     * @param type the type
     */
    public ModelEvent(EventType type) {
		this.type = type;
	}

    /**
     * Gets type.
     *
     * @return the type
     */
    public EventType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return  "ModelEvent(type=" + getType() + ")";
	}
}
