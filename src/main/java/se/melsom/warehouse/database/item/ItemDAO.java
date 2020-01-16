package se.melsom.warehouse.database.item;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.Item;

public class ItemDAO {
	private int id;
	private String number;
	private String name;
	private String packaging;
	private String description;

	public ItemDAO(Item anItem) {
		this.id = anItem.getId();
		this.number = anItem.getNumber();
		this.name = anItem.getName();
		this.packaging = anItem.getPackaging();
		this.description = anItem.getDescription();
	}
	
	public ItemDAO() {
		this.id = EntityName.NULL_ID;
		this.number = "";
		this.name = "";
		this.packaging = "";
	}

	public Item createItem() {
		return new Item(id, number, name, packaging);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}
	
	public void setNumber(String value) {
		number = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String value) {
		name = value;
	}
	
	public String getPackaging() {
		return packaging;
	}
	
	public void setPackaging(String value) {
		packaging = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		String text = "";
		
		text += "[";
		text += getId();
		text += "|";
		text += getNumber();
		text += "|";
		text += getName();
		text += "|";
		text += getPackaging();
		text += "|";
		text += getDescription();
		text += "]";
		
		return text;
	}
}
