package se.melsom.warehouse.event;

public interface ModelEventListener {
    void handleEvent(ModelEvent event);

	void finalize() throws Throwable;
}
