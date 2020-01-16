package se.melsom.warehouse.database.inventory;

import se.melsom.warehouse.model.entity.inventory.MasterInventory;

public class MasterInventoryDAO {
	private int id;
	private int itemId;
	private String source;
	private String stockPoint;
	private int quantity;
	private String identity;
	private String timestamp;
	private String annotation;
	
	public MasterInventoryDAO() {
	}
	
	public MasterInventoryDAO(MasterInventory inventory) {
		this.id = inventory.getId();
		this.itemId = inventory.getItem().getId();
		this.stockPoint = inventory.getStockPoint();
		this.source = inventory.getSource();
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStockPoint() {
		return stockPoint;
	}

	public void setStockPoint(String stockPoint) {
		this.stockPoint = stockPoint;
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
		text += getSource();
		text += "|";
		text += getStockPoint();
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
