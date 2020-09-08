package se.melsom.warehouse.event;

/**
 * The interface Model event listener.
 */
public interface ModelEventListener {
    /**
     * Handle event.
     *
     * @param event the event
     */
    public void handleEvent(ModelEvent event);

	public void finalize() throws Throwable;
}
