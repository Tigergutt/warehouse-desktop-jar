package se.melsom.warehouse.model.entity.inventory;

import se.melsom.warehouse.model.entity.Item;

public abstract class Inventory {
	private int id;
	private Item item;
	private String identity = "";
	private int quantity = 0;
	private String annotation = "";
	private String timestamp = "";

	public Inventory() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public int compareByItemNumber(Inventory other) {
		return getItem().getNumber().compareTo(other.getItem().getNumber());
	}
	
	public int compareByItemName(Inventory other) {
		return getItem().getName().compareTo(other.getItem().getName());
	}
	public int compareByIdentity(Inventory other) {
		return getIdentity().compareTo(other.getIdentity());
	}

	public int compareByTimestamp(Inventory other) {
		return getTimestamp().compareTo(other.getTimestamp());
	}

	public abstract boolean isValid();
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Inventory) {
			Inventory other = (Inventory) obj;
			
			if (getItem().getId() != other.getItem().getId()) {
				return false;
			}
			
			if (!getIdentity().equals(other.getIdentity())) {
				return false;
			}
			
			if (getQuantity() != other.getQuantity()) {
				return false;
			}
			
			if (!getAnnotation().equals(other.getAnnotation())) {
				return false;
			}
			
			return true;
		}
		
		return false;
	}

}
