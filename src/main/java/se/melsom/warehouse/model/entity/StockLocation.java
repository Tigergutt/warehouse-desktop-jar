package se.melsom.warehouse.model.entity;

/**
 * The type Stock location.
 */
public class StockLocation implements Comparable<StockLocation> {
	private int id;
	private String section;
	private String slot;
	private String description;

    /**
     * Instantiates a new Stock location.
     *
     * @param id          the id
     * @param section     the section
     * @param slot        the slot
     * @param description the description
     */
    public StockLocation(int id, String section, String slot, String description) {
		this.id = id;
		this.section = section;
		this.slot = slot;
		this.description = description;
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
     * @param value the value
     */
    public void setId(int value) {
		id = value;
	}

    /**
     * Gets location label.
     *
     * @return the location label
     */
    public String getLocationLabel() {
		return getSection() + getSlot();
	}

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
     * @param value the value
     */
    public void setSection(String value) {
		section = value;
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
     * @param value the value
     */
    public void setSlot(String value) {
		slot = value;
	}

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
		return description;
	}

    /**
     * Sets description.
     *
     * @param value the value
     */
    public void setDescription(String value) {
		description = value;
	}

	@Override
	public int compareTo(StockLocation other) {
		if (section.compareTo(other.section) == 0) {
			return slot.compareTo(other.slot);
		}
		
		return section.compareTo(other.section);
	}

	@Override
	public String toString() {
		return "(" + getId() + "," + getSection() + "," + getSlot() + "," + getDescription() + ")"; 
	}
}
