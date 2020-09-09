package se.melsom.warehouse.database.inventory;

import se.melsom.warehouse.model.entity.inventory.ActualInventory;

/**
 * The Actual inventory data access object.
 */
public class ActualInventoryDAO {
	private int id;
	private int itemId;
	private int locationId;
	private int quantity;
	private String identity;
	private String timestamp;
	private String annotation;

    /**
     * Instantiates a new Actual inventory dao.
     */
    public ActualInventoryDAO() {
	}

    /**
     * Instantiates a new Actual inventory dao.
     *
     * @param inventory the inventory
     */
    public ActualInventoryDAO(ActualInventory inventory) {
		this.id = inventory.getId();
		this.itemId = inventory.getItem().getId();
		this.locationId = inventory.getLocation().getId();
		this.quantity = inventory.getQuantity();
		this.identity = inventory.getIdentity();
		this.timestamp = inventory.getTimestamp().length() > 0 ? inventory.getTimestamp() : null;
		this.annotation = inventory.getAnnotation();
	}

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
		return id;
	}

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
		this.id = id;
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
     * Gets location id.
     *
     * @return the location id
     */
    public int getLocationId() {
		return locationId;
	}

    /**
     * Sets location id.
     *
     * @param locationId the location id
     */
    public void setLocationId(int locationId) {
		this.locationId = locationId;
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
     * @param value the value
     */
    public void setQuantity(int value) {
		quantity = value;
	}

    /**
     * Gets identity.
     *
     * @return the identity
     */
    public String getIdentity() {
		return identity;
	}

    /**
     * Sets identity.
     *
     * @param identity the identity
     */
    public void setIdentity(String identity) {
		this.identity = identity;
	}

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public String getTimestamp() {
		return timestamp;
	}

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

    /**
     * Gets annotation.
     *
     * @return the annotation
     */
    public String getAnnotation() {
		return annotation;
	}

    /**
     * Sets annotation.
     *
     * @param annotation the annotation
     */
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
		text += getLocationId();
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
