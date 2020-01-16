package se.melsom.warehouse.database.inventory;

public class StockLocationItem {
	private String section;
	private String slot;
	private String number;
	private String name;
	int quantity;
	private String identity;
	private String date;

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		String text = "";
		
		text += "[";
		text += getSection();
		text += "|";
		text += getSlot();
		text += "|";
		text += getNumber();
		text += "|";
		text += getName();
		text += "|";
		text += getQuantity();
		text += "|";
		text += getIdentity();
		text += "]";
		
		return text;
	}
}
