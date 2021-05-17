package se.melsom.warehouse.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.data.service.InventoryService;
import se.melsom.warehouse.event.EventType;
import se.melsom.warehouse.event.ModelEvent;
import se.melsom.warehouse.event.ModelEventBroker;
import se.melsom.warehouse.model.entity.StockLocation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@Deprecated
public class LocationMasterFile {
	private static final Logger logger = LoggerFactory.getLogger(LocationMasterFile.class);
	
	private final InventoryService database;
	private final ModelEventBroker eventBroker;
	private final Map<Integer, StockLocation> locationList = new HashMap<>();

    public LocationMasterFile(InventoryService database, ModelEventBroker eventBroker) {
		this.database = database;
		this.eventBroker = eventBroker;
	}

    public int getNextLocationId() {
		return locationList.size();
	}

    public Vector<StockLocation> getLocations() {
		Vector<StockLocation> locations = new Vector<>();
		
		locations.addAll(locationList.values());
		
		Collections.sort(locations);
		
		return locations;
	}

    public StockLocation getdLocation(int withId) {
		StockLocation location = locationList.get(withId);
		
		if (location == null) {
			logger.warn("No item for id=" + withId);
		}
		
		return location;
	}

    public StockLocation getLocation(String withinSection, String atSlot) {
		for (StockLocation location : locationList.values()) {
			if (location.getSection().equals(withinSection) && location.getSlot().equals(atSlot)) {
				return location;
			}
		}
		
		return null;
	}

    public boolean addLocation(StockLocation newLocation) {
		logger.trace("Add location=" + newLocation);
		if (getLocation(newLocation.getSection(), newLocation.getSlot()) != null) {
			logger.error("Could not add duplicate StockLocation=" + newLocation);
			return false;
		}
		locationList.put(newLocation.getId(), newLocation);
		//database.insertStockLocation(newLocation);
		return true;
	}

    public void updateLocation(StockLocation aLocation) {
		logger.trace("Update location=" + aLocation);
		locationList.put(aLocation.getId(), aLocation);
		//database.updateStockLocation(aLocation);
	}

    public void removeLocation(StockLocation aLocation) {
		logger.trace("Remove location=" + aLocation);
		
		locationList.remove(aLocation.getId());
		//database.deleteStockLocation(aLocation);
	}

    void retrieveLocationList() {
		locationList.clear();
		
		logger.debug("Retriving location list.");
//		for (StockLocation location : database.selectLocations(null, null)) {
//			logger.trace(location);
//			locationList.put(location.getId(), location);
//		}
		
		notifyObservers(new ModelEvent(EventType.STOCK_LOCATIONS_RELOADED));
	}
	
	private void notifyObservers(ModelEvent event) {
		if (eventBroker == null) {
			logger.error("Event broker is null.");
			return;
		}
		
		eventBroker.send(event);
	}
}
