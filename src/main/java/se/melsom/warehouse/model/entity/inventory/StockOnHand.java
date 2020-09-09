package se.melsom.warehouse.model.entity.inventory;

/**
 * Stock on hand is a union of actual and master inventory.
 */
public class StockOnHand {
	private String itemNumber;
	private String itemName;
	private String packaging;
	private int nominalQuantity;
	private int actualQuantity;
	private String identity;
	private String annotation;

    /**
     * Gets item number.
     *
     * @return the item number
     */
    public String getItemNumber() {
		return itemNumber;
	}

    /**
     * Sets item number.
     *
     * @param itemNumber the item number
     */
    public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

    /**
     * Gets item name.
     *
     * @return the item name
     */
    public String getItemName() {
		return itemName;
	}

    /**
     * Sets item name.
     *
     * @param itemName the item name
     */
    public void setItemName(String itemName) {
		this.itemName = itemName;
	}

    /**
     * Gets packaging.
     *
     * @return the packaging
     */
    public String getPackaging() {
		return packaging;
	}

    /**
     * Sets packaging.
     *
     * @param packaging the packaging
     */
    public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

    /**
     * Gets nominal quantity.
     *
     * @return the nominal quantity
     */
    public int getNominalQuantity() {
		return nominalQuantity;
	}

    /**
     * Sets nominal quantity.
     *
     * @param nomnalQuantity the nomnal quantity
     */
    public void setNominalQuantity(int nomnalQuantity) {
		this.nominalQuantity = nomnalQuantity;
	}


    /**
     * Gets actual quantity.
     *
     * @return the actual quantity
     */
    public int getActualQuantity() {
		return actualQuantity;
	}


    /**
     * Sets actual quantity.
     *
     * @param actualQuantity the actual quantity
     */
    public void setActualQuantity(int actualQuantity) {
		this.actualQuantity = actualQuantity;
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
		String text = "(";
		text += getItemNumber();
		text += ",";
		text += getItemName();
		text += ",";
		text += getNominalQuantity();
		text += ",";
		text += getActualQuantity();
		text += ",";
		text += getPackaging();
		text += ",";
		text += getIdentity();
		text += ",";
		text += getAnnotation();
		text += ")";

		return text;
	}

    /**
     * Compare by item number int.
     *
     * @param other the other
     * @return the int
     */
    public int compareByItemNumber(StockOnHand other) {
		return getItemNumber().compareTo(other.getItemNumber());
	}

    /**
     * Compare by item name int.
     *
     * @param other the other
     * @return the int
     */
    public int compareByItemName(StockOnHand other) {
		return getItemName().compareTo(other.getItemName());
	}

    /**
     * Compare by identity int.
     *
     * @param other the other
     * @return the int
     */
    public int compareByIdentity(StockOnHand other) {
		if (getIdentity() != null && other.getIdentity() != null) {
			if (getIdentity().length() > 0 && other.getIdentity().length() > 0) {
				return getIdentity().compareTo(other.getIdentity());
			} else if (getIdentity().length() > 0 && other.getIdentity().length() == 0) {
				return -1;
			} else if (getIdentity().length() == 0 && other.getIdentity().length() > 0) {
				return 1;
			}
		} else if (getIdentity() == null && other.getIdentity() != null) {
			return 1;
		} else if (getIdentity() != null && other.getIdentity() == null) {
			return -1;
		}

		return 0;
	}
}
