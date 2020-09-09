package se.melsom.warehouse.event;

import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * The type Model event broker.
 */
public class ModelEventBroker {
	private Logger logger = Logger.getLogger(this.getClass());
	private Vector<ModelEventListener> listeners = new Vector<>();

    /**
     * Instantiates a new Model event broker.
     */
    public ModelEventBroker() {
	}

    /**
     * Add listener.
     *
     * @param listener the listener
     */
    public void addListener(ModelEventListener listener) {	
		logger.debug("Add listener: " + listener);
		listeners.addElement(listener);
	}

    /**
     * Send.
     *
     * @param event the event
     */
    public void send(ModelEvent event) {
		logger.debug("Distribute event: " + event);
		Vector<ModelEventListener> listerVector = new Vector<>(listeners);
		
		for (ModelEventListener listener : listerVector) {
			if (listener != null) {
				listener.handleEvent(event);
			}
		}
	}

    /**
     * Remove listener.
     *
     * @param listener the listener
     */
    public void removeListener(ModelEventListener listener) {		
		listeners.removeElement(listener);
	}

    /**
     * Clear.
     */
    public void clear() {
		listeners.clear();
	}
}
