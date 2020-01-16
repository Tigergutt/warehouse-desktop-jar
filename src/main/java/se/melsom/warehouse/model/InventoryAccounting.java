package se.melsom.warehouse.model;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.database.WarehouseDatabase;
import se.melsom.warehouse.database.application.ItemApplicationDAO;
import se.melsom.warehouse.database.holding.HoldingDAO;
import se.melsom.warehouse.database.inventory.ActualInventoryDAO;
import se.melsom.warehouse.database.inventory.MasterInventoryDAO;
import se.melsom.warehouse.event.EventType;
import se.melsom.warehouse.event.ModelEvent;
import se.melsom.warehouse.event.ModelEventBroker;
import se.melsom.warehouse.model.entity.Holding;
import se.melsom.warehouse.model.entity.Item;
import se.melsom.warehouse.model.entity.ItemApplication;
import se.melsom.warehouse.model.entity.OrganizationalUnit;
import se.melsom.warehouse.model.entity.StockLocation;
import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.model.entity.inventory.MasterInventory;
import se.melsom.warehouse.model.entity.inventory.StockOnHand;

public class InventoryAccounting {
	private static Logger logger = Logger.getLogger(InventoryAccounting.class);
	
	private WarehouseDatabase database;
	private ModelEventBroker eventBroker;
	
	private ItemMasterFile itemMasterFile;
	private LocationMasterFile locationMasterFile;
	private UnitsMasterFile unitsMasterFile;
	private Vector<Holding> holdings = new Vector<>();
	
	public InventoryAccounting(WarehouseDatabase database, ModelEventBroker eventBroker) {
		this.database = database;
		this.eventBroker = eventBroker;
		
		itemMasterFile = new ItemMasterFile(database, eventBroker);
		locationMasterFile = new LocationMasterFile(database, eventBroker);
		unitsMasterFile = new UnitsMasterFile(database, eventBroker);
	}
	
	public int getNextActualInventoryId() {
		return database.getNumberOfActualInventory();
	}

	public int getNextMasterInventoryId() {
		return database.getNumberOfMasterInventory();
	}

	public Collection<String> getPackagings() {
		Set<String> packagings = new TreeSet<>();
		
		for (Packaging packaging : Packaging.values()) {			
			packagings.add(packaging.getName());
		}

		return packagings;
	}

	public void sync() {
		itemMasterFile.retreiveItemList();
		locationMasterFile.retreiveLocationList();
		unitsMasterFile.retreiveUnitList();
		
		holdings.clear();
		
		logger.debug("Reloading holding data.");
		for (HoldingDAO dao : database.selectHoldings(EntityName.NULL_ID, EntityName.NULL_ID)) {
			OrganizationalUnit unit = unitsMasterFile.getUnit(dao.getUnitId());
			
			if (unit == null) {
				logger.warn("Failed to get unit with id=" + dao.getUnitId());
			}
			
			StockLocation location = locationMasterFile.getdLocation(dao.getLocationId());

			if (location == null) {
				logger.warn("Failed to get location with id=" + dao.getLocationId());
			}
			
			Holding holding = new Holding(unit, location);
			
			if (holding.isValid()) {
				logger.trace(holding);
				holdings.addElement(holding);
			} else {
				logger.error("Invalid holding=" + holding);
			}			
		}
	}

	public boolean isUnitReferenced(int unitId) {
		// FIXME:
		return false;
//		return database.selectInventory(null, null, id, null).size() > 0;		
	}

	public boolean isLocationReferenced(int locationId) {
		// FIXME:
//		if (database.selectInventory(null, null, null, id).size() > 0) {
//			return true;
//		}
//		
//		if (database.selectHoldings(null, id).size() > 0) {
//			return true;
//		}
//		
		return false;		
	}

	public ItemMasterFile getItemMasterFile() {
		return itemMasterFile;
	}

	public LocationMasterFile getLocationMasterFile() {
		return locationMasterFile;
	}

	public UnitsMasterFile getUnitsMasterFile() {
		return unitsMasterFile;
	}

	public Vector<StockOnHand> getStockOnHandList() {
		return database.selectStockOnHandItems();
	}
	
	public Vector<ItemApplication> getItemApplications(OrganizationalUnit forUnit, Vector<ApplicationCategory> ofCategories) {
		Vector<ItemApplication> result = new Vector<>();
		
		for (ItemApplicationDAO dao : database.selectItemApplications(forUnit.getId())) {
			boolean shouldAddApplication = true;
			
			if (ofCategories != null) {
				shouldAddApplication = false;
				
				for (ApplicationCategory aCategory : ofCategories) {
					if (aCategory.getName().equals(dao.getCategory())) {
						shouldAddApplication = true;
						break;
					}
				}
			}
			
			if (shouldAddApplication) {
				OrganizationalUnit unit = unitsMasterFile.getUnit(dao.getUnitId());
				Item item = itemMasterFile.getdItem(dao.getItemId());
				ItemApplication application = new ItemApplication(unit, item, dao.getCategory(), dao.getQuantity());
				
				result.addElement(application);
			}
		}
		
		return result;
	}
	
	public Vector<ActualInventory> getActualInventory(String wildcardSearchKey) {
		Vector<ActualInventory> inventoryList = new Vector<>();
		
		for (ActualInventoryDAO dao : database.selectActualInventory(wildcardSearchKey)) {
			Item item = itemMasterFile.getdItem(dao.getItemId());

			if (item == null) {
				logger.warn("Could not get item for dao=" + dao);
				continue;
			}
			
			StockLocation location = locationMasterFile.getdLocation(dao.getLocationId());
			
			if (location == null) {
				logger.warn("Could not get location for dao=" + dao);
				continue;
			}
			
			ActualInventory inventory = new ActualInventory();
			
			inventory.setId(dao.getId());
			inventory.setItem(item);
			inventory.setLocation(location);
			inventory.setQuantity(dao.getQuantity());
			inventory.setIdentity(dao.getIdentity());
			inventory.setAnnotation(dao.getAnnotation());
			inventory.setTimestamp(dao.getTimestamp());

			inventoryList.addElement(inventory);
		}
		
		return inventoryList;
	}
	
	public Vector<ActualInventory> getActualInventory(String section, String slot) {
		Vector<ActualInventory> inventoryList = new Vector<>();
		
		for (ActualInventoryDAO dao : database.selectActualInventory(null, null, section, slot)) {
			Item item = itemMasterFile.getdItem(dao.getItemId());
			StockLocation location = locationMasterFile.getdLocation(dao.getLocationId());
			
			ActualInventory inventory = new ActualInventory();
			
			inventory.setId(dao.getId());
			inventory.setItem(item);
			inventory.setLocation(location);
			inventory.setQuantity(dao.getQuantity());
			inventory.setIdentity(dao.getIdentity());
			inventory.setAnnotation(dao.getAnnotation());
			inventory.setTimestamp(dao.getTimestamp());
			
			inventoryList.addElement(inventory);
		}
		
		return inventoryList;
	}

	public Vector<MasterInventory> getMasterInventory(int itemId, String identity) {
		Vector<MasterInventory> inventoryList = new Vector<>();
		
		for (MasterInventoryDAO dao : database.selectMasterInventory(itemId, identity)) {
			Item item = itemMasterFile.getdItem(dao.getItemId());			
			MasterInventory inventory = new MasterInventory();
			
			inventory.setId(dao.getId());
			inventory.setItem(item);
			inventory.setSource(dao.getSource());
			inventory.setStockPoint(dao.getStockPoint());
			inventory.setQuantity(dao.getQuantity());
			inventory.setIdentity(dao.getIdentity());
			inventory.setAnnotation(dao.getAnnotation());
			inventory.setTimestamp(dao.getTimestamp());
			
			inventoryList.addElement(inventory);
		}
		
		return inventoryList;
	}

	public boolean addInventory(MasterInventory newInventory) {
		logger.debug("Adding inventory=" + newInventory);
		database.insertInventory(newInventory);
		
		notifyObservers(new ModelEvent(EventType.INVENTORY_UPDATED));
		
		return true;
	}

	public void updateInventory(MasterInventory inventory) {
		database.updateInventory(inventory);
		
		notifyObservers(new ModelEvent(EventType.INVENTORY_UPDATED));
	}

	public void removeInventory(MasterInventory inventory) {
		database.deleteInventory(inventory);
		
		notifyObservers(new ModelEvent(EventType.INVENTORY_UPDATED));
	}

	public boolean addInventory(ActualInventory newInventory) {
		String withNumber = newInventory.getItem().getNumber();
		String withName = newInventory.getItem().getName();
		String withinSection = newInventory.getLocation().getSection();
		String atSlot = newInventory.getLocation().getSlot();
		
		StockLocation location = locationMasterFile.getLocation(withinSection, atSlot);
		
		if (location == null) {
			logger.error("No stock location for inventory=" + newInventory);
			return false;
		}
		
		Item item = itemMasterFile.getItem(withNumber);
		
		if (item == null) {
			logger.debug("No item defined for inventory=" + newInventory);
			if (withNumber.length() > 0 && withName.length() > 0) {
				logger.debug("Adding new item for inventory=" + newInventory);
				item = new Item(itemMasterFile.getNextItemId(), withNumber, withName, "");
				
				if (itemMasterFile.addItem(item)) {
					logger.error("Unable to add item=" + item);
					return false;
				}
			} else {
				logger.error("Unable to create unit for inventory=" + newInventory);
				return false;
			}
		}
			
		newInventory.setItem(item);
		newInventory.setLocation(location);

		logger.debug("Adding inventory=" + newInventory);
		if (database.selectActualInventory(item.getId(), location.getId(), newInventory.getIdentity()).size() == 0) {
			database.insertInventory(newInventory);
		} else {
			return false;
		}
		
		notifyObservers(new ModelEvent(EventType.INVENTORY_UPDATED));
		
		return true;
	}
	
	public void updateInventory(ActualInventory inventory) {
		database.updateInventory(inventory);
		
		notifyObservers(new ModelEvent(EventType.INVENTORY_UPDATED));
	}

	public void removeInventory(ActualInventory inventory) {
		database.deleteInventory(inventory);
		
		notifyObservers(new ModelEvent(EventType.INVENTORY_UPDATED));
	}
	
	public Vector<Holding> getHoldings() {
		return holdings;
	}
	
	public Vector<Holding> getHoldings(int forUnitId) {
		Vector<Holding> result = new Vector<>();
		
		for (Holding holding : holdings) {
			if (holding.getUnit().getId() == forUnitId) {
				result.addElement(holding);
			}
		}
		
		return result;
	}

	public boolean addHolding(Holding newHolding) {
		if (newHolding.getLocation() == null) {
			logger.error("No location defined for new holding=" + newHolding);
			return false;
		}

		String section = newHolding.getLocation().getSection();
		String slot = newHolding.getLocation().getSlot();
		StockLocation location = locationMasterFile.getLocation(section, slot);
		
		if (location == null) {
			logger.error("No such location for new holding=" + newHolding);
			return false;
		}

		if (newHolding.getUnit() == null) {
			logger.error("No unit defined for new holding=" + newHolding);
			return false;
		}
		
		String callsign = newHolding.getUnit().getCallsign();
		OrganizationalUnit unit = getUnitsMasterFile().getUnit(callsign);
		
		if (unit == null) {
			logger.error("No such unit for new holding=" + newHolding);
			return false;
		}
		
		for (Holding holding : holdings) {
			if (holding.getUnit().getId() == unit.getId() && holding.getLocation().getId() == location.getId()) {
				logger.error("Will not add dublicate holding=" + newHolding);
				return false;
			}
		}
		
		newHolding.setUnit(unit);
		newHolding.setLocation(location);
		
		database.insertHolding(newHolding);
		
		return false;
	}
	

	public void addApplication(ItemApplication application) {
		database.insertItemApplication(application);
	}

	public void updateApplication(ItemApplication application) {
		database.updateItemApplication(application);
	}
	
	public void removeApplication(ItemApplication application) {
		database.deleteItemApplication(application);
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
