package se.melsom.warehouse.model.entity;

/**
 * The type Item application.
 */
public class ItemApplication {
	private OrganizationalUnit unit;
	private Item item = null;
	private String category = "";
	private int quantity = 0;

    /**
     * Instantiates a new Item application.
     *
     * @param unit the unit
     */
    public ItemApplication(OrganizationalUnit unit) {
		this.unit = unit;
	}

    /**
     * Instantiates a new Item application.
     *
     * @param unit     the unit
     * @param item     the item
     * @param category the category
     * @param quantity the quantity
     */
    public ItemApplication(OrganizationalUnit unit, Item item, String category, int quantity) {
		this.unit = unit;
		this.item = item;
		this.category = category;
		this.quantity = quantity;
	}

    /**
     * Instantiates a new Item application.
     *
     * @param other the other
     */
    public ItemApplication(ItemApplication other) {
		this.unit = other.unit;
		this.item = other.item;
		this.category = other.category;
		this.quantity = other.quantity;
	}

    /**
     * Gets unit.
     *
     * @return the unit
     */
    public OrganizationalUnit getUnit() {
		return unit;
	}

    /**
     * Sets unit.
     *
     * @param unit the unit
     */
    public void setUnit(OrganizationalUnit unit) {
		this.unit = unit;
	}

    /**
     * Gets item.
     *
     * @return the item
     */
    public Item getItem() {
		return item;
	}

    /**
     * Sets item.
     *
     * @param item the item
     */
    public void setItem(Item item) {
		this.item = item;
	}

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
		return category;
	}

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(String category) {
		this.category = category;
	}

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
		return quantity;
	}

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

    /**
     * Is valid boolean.
     *
     * @return the boolean
     */
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

    /**
     * Is equal boolean.
     *
     * @param other the other
     * @return the boolean
     */
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
