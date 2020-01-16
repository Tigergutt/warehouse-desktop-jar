package se.melsom.warehouse.database.location;

import se.melsom.warehouse.model.entity.StockLocation;

public class StockLocationDAO {
	private int id;
	private String section;
	private String slot;
	private String description;

	public StockLocationDAO(int id, String section, String slot, String description) {
		this.id = id;
		this.section = section;
		this.slot = slot;
		this.description = description;
	}
	
	public StockLocationDAO(StockLocation location) {
		this.id = location.getId();
		this.section = location.getSection();
		this.slot = location.getSlot();
		this.description = location.getDescription();
	}
	
	public StockLocation createLocation() {
		return new StockLocation(id, section, slot, description);
	}
	
	public int getId() {
		return id;
	}

	public void getId(int value) {
		id = value;
	}

	public String getSection() {
		return section;
	}
	
	public void setSection(String value) {
		section = value;
	}
	
	public String getSlot() {
		return slot;
	}
	
	public void setSlot(String value) {
		slot = value;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String value) {
		description = value;
	}

	@Override
	public String toString() {
		String text = "";
		
		text += "[";
		text += getId();
		text += "|";
		text += getSection();
		text += "|";
		text += getSlot();
		text += "|";
		text += getDescription();
		text += "]";
		
		return text;
	}
}
