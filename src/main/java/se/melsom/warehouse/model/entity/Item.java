package se.melsom.warehouse.model.entity;

public class Item implements Comparable<Item> {
	private int id;
	private String number;
	private String name;
	private String packaging;
	private String description;

	public Item(int id) {
		this.id = id;
		this.number = "";
		this.name = "";
		this.packaging = "";
		this.description = "";
	}

	public Item(int id, String number, String name, String packaging) {
		this.id = id;
		this.number = number;
		this.name = name;
		this.packaging = packaging;
		this.description = "";
	}

	public boolean isValid() {
		if (number == null || number.length() == 0) {
			return false;
		}

		if (name == null || name.length() == 0) {
			return false;
		}
		
		if (packaging == null || packaging.length() == 0) {
			return false;
		}

		return true;
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
	public int compareTo(Item other) {
		if (number.compareTo(other.number) == 0) {
			return name.compareTo(other.name);
		}
		
		return number.compareTo(other.number);
	}
	
	public int compareByItemNumber(Item other) {
		return number.compareTo(other.number);
	}

	public int compareByItemName(Item other) {
		return name.compareTo(other.name);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Item)) {
			return false;
		}
		
		Item other = (Item) object;
		
		if (this.id != other.id) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		String text = "(";
		
		text += getId();
		text += ",";
		text += getNumber();
		text += ",";
		text += getName();
		text += ",";
		text += getPackaging();
		text += ",";
		text += getDescription();
		text += ")";
		
		return text;
	}
}
