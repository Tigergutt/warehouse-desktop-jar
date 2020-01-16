package se.melsom.warehouse.database.inventory;

import se.melsom.warehouse.model.entity.inventory.ActualInventory;

public class ActualInventoryDAO {
	private int id;
	private int itemId;
	private int locationId;
	private int quantity;
	private String identity;
	private String timestamp;
	private String annotation;
	
	public ActualInventoryDAO() {
	}
	
	public ActualInventoryDAO(ActualInventory inventory) {
		this.id = inventory.getId();
		this.itemId = inventory.getItem().getId();
		this.locationId = inventory.getLocation().getId();
		this.quantity = inventory.getQuantity();
		this.identity = inventory.getIdentity();
		this.timestamp = inventory.getTimestamp().length() > 0 ? inventory.getTimestamp() : null;
		this.annotation = inventory.getAnnotation();
	}
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getLocationId() {
		return locationId;
	}
	
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int value) {
		quantity = value;
	}
	
	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	@Override
	public String toString() {
		String text = "";
		
		text += "[";
		text += getId();
		text += "|";
		text += getItemId();
		text += "|";
		text += getLocationId();
		text += "|";
		text += getQuantity();
		text += "|";
		text += getIdentity();
		text += "|";
		text += getTimestamp();
		text += "|";
		text += getAnnotation();
		text += "]";
		
		return text;
	}
}
