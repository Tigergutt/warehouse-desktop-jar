package se.melsom.warehouse.database.application;

import se.melsom.warehouse.model.entity.ItemApplication;

/**
 * The Item application data access object.
 */
public class ItemApplicationDAO {
	private int unitId;
	private int itemId;
	private String category;
	private int quantity;

    /**
     * Instantiates a new Item application dao.
     *
     * @param unitId   the unit id
     * @param itemId   the item id
     * @param category the category
     * @param quantity the quantity
     */
    public ItemApplicationDAO(int unitId, int itemId, String category, int quantity) {
		this.unitId = unitId;
		this.itemId = itemId;
		this.category = category;
		this.quantity = quantity;
	}

    /**
     * Instantiates a new Item application dao.
     *
     * @param anItemApplication the an item application
     */
    public ItemApplicationDAO(ItemApplication anItemApplication) {
		this.unitId = anItemApplication.getUnit().getId();
		this.itemId = anItemApplication.getItem().getId();
		this.category = anItemApplication.getCategory();
		this.quantity = anItemApplication.getQuantity();
	}

    /**
     * Gets unit id.
     *
     * @return the unit id
     */
    public int getUnitId() {
		return unitId;
	}

    /**
     * Sets unit id.
     *
     * @param unitId the unit id
     */
    public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

    /**
     * Gets item id.
     *
     * @return the item id
     */
    public int getItemId() {
		return itemId;
	}

    /**
     * Sets item id.
     *
     * @param itemId the item id
     */
    public void setItemId(int itemId) {
		this.itemId = itemId;
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
