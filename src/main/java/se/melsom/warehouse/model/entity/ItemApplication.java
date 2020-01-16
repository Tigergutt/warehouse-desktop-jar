package se.melsom.warehouse.model.entity;

public class ItemApplication {
	private OrganizationalUnit unit;
	private Item item = null;
	private String category = "";
	private int quantity = 0; 
	
	public ItemApplication(OrganizationalUnit unit) {
		this.unit = unit;
	}

	public ItemApplication(OrganizationalUnit unit, Item item, String category, int quantity) {
		this.unit = unit;
		this.item = item;
		this.category = category;
		this.quantity = quantity;
	}

	public ItemApplication(ItemApplication other) {
		this.unit = other.unit;
		this.item = other.item;
		this.category = other.category;
		this.quantity = other.quantity;
	}

	public OrganizationalUnit getUnit() {
		return unit;
	}

	public void setUnit(OrganizationalUnit unit) {
		this.unit = unit;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
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

	public boolean isValid() {
		if (getUnit() == null) {
			return false;
		}
		
		if (getItem() == null) {
			return false;
		}
		
		if (category == null || category.length() == 0) {
			return false;
		}
		
		if (quantity < 0) {
			return false;
		}
		
		return true;
	}
	
	public boolean isEqual(ItemApplication other) {
		if (!this.unit.equals(other.unit)) {
			return false;
		}
		
		if (!this.item.equals(other.item)) {
			return false;
		}
		
		if (!this.category.equals(other.category)) {
			return false;
		}
		
		return true;
	}
}
