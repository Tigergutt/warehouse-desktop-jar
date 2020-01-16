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

public class ItemMasterFile {
	private static Logger logger = Logger.getLogger(ItemMasterFile.class);
	
	private WarehouseDatabase database;
	private ModelEventBroker eventBroker;
	private Map<Integer, Item> itemList = new HashMap<>();

	public ItemMasterFile(WarehouseDatabase database, ModelEventBroker eventBroker) {
		this.database = database;
		this.eventBroker = eventBroker;
	}
	
	public int getNextItemId() {
		return itemList.size();
	}
	
	/**
	 * 
	 * @return
	 */
	public Vector<Item> getItems() {
		Vector<Item> items = new Vector<>();
		
		items.addAll(itemList.values());
		
		Collections.sort(items);
		
		return items;
	}

	/**
	 * 
	 * @param withId
	 * @return
	 */
	public Item getdItem(int withId) {
		Item item = itemList.get(withId);
		
		if (item == null) {
			logger.warn("No item for id=" + withId);
		}
		
		return item;
	}
	
	/**
	 * 
	 * @param bySearchKey
	 * @return
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

	public Item getItem(String withNumber) {
		for (Item item : itemList.values()) {
			if (item.getNumber().equals(withNumber)) {
				return item;
			}
		}
		
		return null;
	}

	/**
	 * 
	 * @param newItem
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
	 * 
	 * @param anItem
	 */
	public void updateItem(Item anItem) {
		logger.trace("Update article=" + anItem);
		itemList.put(anItem.getId(), anItem);
		database.updateItem(anItem);
		notifyObservers(new ModelEvent(EventType.ITEM_LIST_MODIFIED));
	}

	/**
	 * 
	 * @param anItem
	 */
	public void removeItem(Item anItem) {
		logger.trace("Remove article=" + anItem);
		
		itemList.remove(anItem.getId());
		database.deleteItem(anItem);
		notifyObservers(new ModelEvent(EventType.ITEM_LIST_MODIFIED));
	}
	
	/**
	 * 
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
