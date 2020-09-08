package se.melsom.warehouse.database.inventory;

import se.melsom.warehouse.model.entity.inventory.MasterInventory;

/**
 * The Master inventory data access object.
 */
public class MasterInventoryDAO {
	private int id;
	private int itemId;
	private String source;
	private String stockPoint;
	private int quantity;
	private String identity;
	private String timestamp;
	private String annotation;

    /**
     * Instantiates a new Master inventory dao.
     */
    public MasterInventoryDAO() {
	}

    /**
     * Instantiates a new Master inventory dao.
     *
     * @param inventory the inventory
     */
    public MasterInventoryDAO(MasterInventory inventory) {
		this.id = inventory.getId();
		this.itemId = inventory.getItem().getId();
		this.stockPoint = inventory.getStockPoint();
		this.source = inventory.getSource();
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
     * Gets source.
     *
     * @return the source
     */
    public String getSource() {
		return source;
	}

    /**
     * Sets source.
     *
     * @param source the source
     */
    public void setSource(String source) {
		this.source = source;
	}

    /**
     * Gets stock point.
     *
     * @return the stock point
     */
    public String getStockPoint() {
		return stockPoint;
	}

    /**
     * Sets stock point.
     *
     * @param stockPoint the stock point
     */
    public void setStockPoint(String stockPoint) {
		this.stockPoint = stockPoint;
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
		text += getSource();
		text += "|";
		text += getStockPoint();
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
