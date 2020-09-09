package se.melsom.warehouse.database.inventory;

/**
 * The type Stock location item.
 */
public class StockLocationItem {
	private String section;
	private String slot;
	private String number;
	private String name;
    /**
     * The Quantity.
     */
    int quantity;
	private String identity;
	private String date;

    /**
     * Gets section.
     *
     * @return the section
     */
    public String getSection() {
		return section;
	}

    /**
     * Sets section.
     *
     * @param section the section
     */
    public void setSection(String section) {
		this.section = section;
	}

    /**
     * Gets slot.
     *
     * @return the slot
     */
    public String getSlot() {
		return slot;
	}

    /**
     * Sets slot.
     *
     * @param slot the slot
     */
    public void setSlot(String slot) {
		this.slot = slot;
	}

    /**
     * Gets number.
     *
     * @return the number
     */
    public String getNumber() {
		return number;
	}

    /**
     * Sets number.
     *
     * @param number the number
     */
    public void setNumber(String number) {
		this.number = number;
	}

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return name;
	}

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
		this.name = name;
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
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
		return date;
	}

    /**
     * Sets date.
     *
     * @param date the date
     */
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
