package se.melsom.warehouse.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.database.WarehouseDatabase;
import se.melsom.warehouse.event.EventType;
import se.melsom.warehouse.event.ModelEvent;
import se.melsom.warehouse.event.ModelEventBroker;
import se.melsom.warehouse.model.entity.StockLocation;

/**
 * 
 * @author bernard
 *
 */
public class LocationMasterFile {
	private static Logger logger = Logger.getLogger(LocationMasterFile.class);
	
	private WarehouseDatabase database;
	private ModelEventBroker eventBroker;
	private Map<Integer, StockLocation> locationList = new HashMap<>();

	public LocationMasterFile(WarehouseDatabase database, ModelEventBroker eventBroker) {
		this.database = database;
		this.eventBroker = eventBroker;
	}

	public int getNextLocationId() {
		return locationList.size();
	}
	
	/**
	 * 
	 * @return
	 */
	public Vector<StockLocation> getLocations() {
		Vector<StockLocation> locations = new Vector<>();
		
		locations.addAll(locationList.values());
		
		Collections.sort(locations);
		
		return locations;
	}

	/**
	 * 
	 * @param withId
	 * @return
	 */
	public StockLocation getdLocation(int withId) {
		StockLocation location = locationList.get(withId);
		
		if (location == null) {
			logger.warn("No item for id=" + withId);
		}
		
		return location;
	}
	
	/**
	 * 
	 * @param withinSection
	 * @param atSlot
	 * @return
	 */
	public StockLocation getLocation(String withinSection, String atSlot) {
		for (StockLocation location : locationList.values()) {
			if (location.getSection().equals(withinSection) && location.getSlot().equals(atSlot)) {
				return location;
			}
		}
		
		return null;
	}

	/**
	 * 
	 * @param newLocation
	 */
	public boolean addLocation(StockLocation newLocation) {
		logger.trace("Add location=" + newLocation);
		if (getLocation(newLocation.getSection(), newLocation.getSlot()) != null) {
			logger.error("Could not add duplicate StockLocation=" + newLocation);
			return false;
		}
		locationList.put(newLocation.getId(), newLocation);
		database.insertStockLocation(newLocation);
		return true;
	}
	
	/**
	 * 
	 * @param aLocation
	 */
	public void updateLocation(StockLocation aLocation) {
		logger.trace("Update location=" + aLocation);
		locationList.put(aLocation.getId(), aLocation);
		database.updateStockLocation(aLocation);
	}

	/**
	 * 
	 * @param aLocation
	 */
	public void removeLocation(StockLocation aLocation) {
		logger.trace("Remove location=" + aLocation);
		
		locationList.remove(aLocation.getId());
		database.deleteStockLocation(aLocation);
	}
	
	/**
	 * 
	 */
	void retreiveLocationList() {
		locationList.clear();
		
		logger.debug("Retriving location list.");
		for (StockLocation location : database.selectLocations(null, null)) {
			logger.trace(location);
			locationList.put(location.getId(), location);
		}
		
		notifyObservers(new ModelEvent(EventType.STOCK_LOCATIONS_RELOADED));
	}
	
	/**
	 * 
	 * @param event
	 */
	private void notifyObservers(ModelEvent event) {
		if (eventBroker == null) {
			logger.error("Event broker is null.");
			return;
		}
		
		eventBroker.send(event);
	}
}
