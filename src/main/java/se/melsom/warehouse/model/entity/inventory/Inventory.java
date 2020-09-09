package se.melsom.warehouse.model.entity.inventory;

import se.melsom.warehouse.model.entity.Item;

/**
 * Base class fpr various inventory classes.
 */
public abstract class Inventory {
	private int id;
	private Item item;
	private String identity = "";
	private int quantity = 0;
	private String annotation = "";
	private String timestamp = "";

    /**
     * Instantiates a new Inventory.
     */
    public Inventory() {
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
     * Compare by item number int.
     *
     * @param other the other
     * @return the int
     */
    public int compareByItemNumber(Inventory other) {
		return getItem().getNumber().compareTo(other.getItem().getNumber());
	}

    /**
     * Compare by item name int.
     *
     * @param other the other
     * @return the int
     */
    public int compareByItemName(Inventory other) {
		return getItem().getName().compareTo(other.getItem().getName());
	}

    /**
     * Compare by identity int.
     *
     * @param other the other
     * @return the int
     */
    public int compareByIdentity(Inventory other) {
		return getIdentity().compareTo(other.getIdentity());
	}

    /**
     * Compare by timestamp int.
     *
     * @param other the other
     * @return the int
     */
    public int compareByTimestamp(Inventory other) {
		return getTimestamp().compareTo(other.getTimestamp());
	}

    /**
     * Is valid boolean.
     *
     * @return the boolean
     */
    public abstract boolean isValid();

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Inventory) {
			Inventory other = (Inventory) obj;

			if (getItem().getId() != other.getItem().getId()) {
				return false;
			}

			if (!getIdentity().equals(other.getIdentity())) {
				return false;
			}

			if (getQuantity() != other.getQuantity()) {
				return false;
			}

			if (!getAnnotation().equals(other.getAnnotation())) {
				return false;
			}

			return true;
		}

		return false;
	}

}
