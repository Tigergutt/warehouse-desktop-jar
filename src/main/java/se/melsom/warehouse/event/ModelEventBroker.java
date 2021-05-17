package se.melsom.warehouse.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.settings.PersistentSettings;

import java.util.Vector;

public class ModelEventBroker {
	private static final Logger logger = LoggerFactory.getLogger(PersistentSettings.class);
	private final Vector<ModelEventListener> listeners = new Vector<>();

    public ModelEventBroker() {
	}

    public void addListener(ModelEventListener listener) {
		logger.debug("Add listener: " + listener);
		listeners.addElement(listener);
	}

    public void send(ModelEvent event) {
		logger.debug("Distribute event: " + event);
		Vector<ModelEventListener> listerVector = new Vector<>(listeners);
		
		for (ModelEventListener listener : listerVector) {
			if (listener != null) {
				listener.handleEvent(event);
			}
		}
	}

    public void removeListener(ModelEventListener listener) {
		listeners.removeElement(listener);
	}

    public void clear() {
		listeners.clear();
	}
}
