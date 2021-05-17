package se.melsom.warehouse.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.data.service.InventoryService;
import se.melsom.warehouse.event.ModelEvent;
import se.melsom.warehouse.event.ModelEventBroker;
import se.melsom.warehouse.model.entity.Item;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@Deprecated
public class ItemMasterFile {
	private static final Logger logger = LoggerFactory.getLogger(ItemMasterFile.class);
	
	private final InventoryService database;
	private final ModelEventBroker eventBroker;
	private final Map<Integer, Item> itemList = new HashMap<>();

    public ItemMasterFile(InventoryService database, ModelEventBroker eventBroker) {
		this.database = database;
		this.eventBroker = eventBroker;
	}

    public int getNextItemId() {
		return itemList.size();
	}

    public Vector<Item> getItems() {
		Vector<Item> items = new Vector<>();
		
		items.addAll(itemList.values());
		
		Collections.sort(items);
		
		return items;
	}

    public Item getdItem(int withId) {
		Item item = itemList.get(withId);
		
		if (item == null) {
			logger.warn("No item for id=" + withId);
		}
		
		return item;
	}

    public Vector<Item> getItems(String thatMatches) {
		Vector<Item> result = new Vector<>();
		
		for (Item item : itemList.values()) {
			if (item.getNumber().contains(thatMatches)) {
				result.addElement(item);
				continue;
			}
			
			if (item.getName().contains(thatMatches)) {
				result.addElement(item);
				continue;
			}
		}
		
		return result;
	}

    public Item getItem(String withNumber) {
		for (Item item : itemList.values()) {
			if (item.getNumber().equals(withNumber)) {
				return item;
			}
		}
		
		return null;
	}

    public boolean addItem(Item newItem) {
		logger.trace("Add item=" + newItem);
		if (getItem(newItem.getNumber()) != null) {
			logger.error("Could not att duplicate Item=" + newItem);
			return false;
		}
		itemList.put(newItem.getId(), newItem);
		throw new IllegalArgumentException("Fix create item");
//		notifyObservers(new ModelEvent(EventType.ITEM_LIST_MODIFIED));
//		return true;
	}

    public void updateItem(Item anItem) {
		logger.trace("Update article=" + anItem);
		itemList.put(anItem.getId(), anItem);
		throw new IllegalArgumentException("Fix update item");
//		notifyObservers(new ModelEvent(EventType.ITEM_LIST_MODIFIED));
	}

    public void removeItem(Item anItem) {
		logger.trace("Remove article=" + anItem);
		
		itemList.remove(anItem.getId());
		throw new IllegalArgumentException("Fix delete item");
//		notifyObservers(new ModelEvent(EventType.ITEM_LIST_MODIFIED));
	}

    void retrievedItemList() {
		itemList.clear();
		
		logger.debug("Retrieving item list.");
		throw new IllegalArgumentException("Fix read items");
//		for (Item item : database.selectItems(null, null)) {
//			logger.trace(item);
//			itemList.put(item.getId(), item);
//		}
//
//		notifyObservers(new ModelEvent(EventType.ITEM_LIST_RELOADED));
	}
	
	private void notifyObservers(ModelEvent event) {
		if (eventBroker == null) {
			logger.error("Event broker is null.");
			return;
		}
		
		eventBroker.send(event);
	}
}
