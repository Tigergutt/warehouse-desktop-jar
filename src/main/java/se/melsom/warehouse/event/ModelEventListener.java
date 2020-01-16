package se.melsom.warehouse.event;

public interface ModelEventListener {
	public void handleEvent(ModelEvent event);

	public void finalize() throws Throwable;
}
