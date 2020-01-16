package se.melsom.warehouse.database.application;

import se.melsom.warehouse.model.entity.ItemApplication;

public class ItemApplicationDAO {
	private int unitId;
	private int itemId;
	private String category;
	private int quantity; 
	
	public ItemApplicationDAO(int unitId, int itemId, String category, int quantity) {
		this.unitId = unitId;
		this.itemId = itemId;
		this.category = category;
		this.quantity = quantity;
	}

	public ItemApplicationDAO(ItemApplication anItemApplication) {
		this.unitId = anItemApplication.getUnit().getId();
		this.itemId = anItemApplication.getItem().getId();
		this.category = anItemApplication.getCategory();
		this.quantity = anItemApplication.getQuantity();
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		String text = "";
		
		text += "[";
		text += getUnitId();
		text += "|";
		text += getItemId();
		text += "|";
		text += getCategory();
		text += "|";
		text += getQuantity();
		text += "]";
		
		return text;
	}
}
