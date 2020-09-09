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
import se.melsom.warehouse.model.entity.Item;

/**
 * The type Item master file.
 */
public class ItemMasterFile {
	private static Logger logger = Logger.getLogger(ItemMasterFile.class);
	
	private WarehouseDatabase database;
	private ModelEventBroker eventBroker;
	private Map<Integer, Item> itemList = new HashMap<>();

    /**
     * Instantiates a new Item master file.
     *
     * @param database    the database
     * @param eventBroker the event broker
     */
    public ItemMasterFile(WarehouseDatabase database, ModelEventBroker eventBroker) {
		this.database = database;
		this.eventBroker = eventBroker;
	}

    /**
     * Gets next item id.
     *
     * @return the next item id
     */
    public int getNextItemId() {
		return itemList.size();
	}

    /**
     * Gets items.
     *
     * @return items
     */
    public Vector<Item> getItems() {
		Vector<Item> items = new Vector<>();
		
		items.addAll(itemList.values());
		
		Collections.sort(items);
		
		return items;
	}

    /**
     * Gets item.
     *
     * @param withId the with id
     * @return item
     */
    public Item getdItem(int withId) {
		Item item = itemList.get(withId);
		
		if (item == null) {
			logger.warn("No item for id=" + withId);
		}
		
		return item;
	}

    /**
     * Gets items.
     *
     * @param thatMatches the that matches
     * @return items
     */
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

    /**
     * Gets item.
     *
     * @param withNumber the with number
     * @return the item
     */
    public Item getItem(String withNumber) {
		for (Item item : itemList.values()) {
			if (item.getNumber().equals(withNumber)) {
				return item;
			}
		}
		
		return null;
	}

    /**
     * Add item boolean.
     *
     * @param newItem the new item
     * @return the boolean
     */
    public boolean addItem(Item newItem) {
		logger.trace("Add item=" + newItem);
		if (getItem(newItem.getNumber()) != null) {
			logger.error("Could not att duplicate Item=" + newItem);
			return false;
		}
		itemList.put(newItem.getId(), newItem);
		database.insertItem(newItem);
		notifyObservers(new ModelEvent(EventType.ITEM_LIST_MODIFIED));
		return true;
	}

    /**
     * Update item.
     *
     * @param anItem the an item
     */
    public void updateItem(Item anItem) {
		logger.trace("Update article=" + anItem);
		itemList.put(anItem.getId(), anItem);
		database.updateItem(anItem);
		notifyObservers(new ModelEvent(EventType.ITEM_LIST_MODIFIED));
	}

    /**
     * Remove item.
     *
     * @param anItem the an item
     */
    public void removeItem(Item anItem) {
		logger.trace("Remove article=" + anItem);
		
		itemList.remove(anItem.getId());
		database.deleteItem(anItem);
		notifyObservers(new ModelEvent(EventType.ITEM_LIST_MODIFIED));
	}

    /**
     * Retreive item list.
     */
    void retreiveItemList() {
		itemList.clear();
		
		logger.debug("Retriving item list.");
		for (Item item : database.selectItems(null, null)) {
			logger.trace(item);
			itemList.put(item.getId(), item);
		}
		
		notifyObservers(new ModelEvent(EventType.ITEM_LIST_RELOADED));
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
