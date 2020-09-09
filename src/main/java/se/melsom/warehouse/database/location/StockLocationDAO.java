package se.melsom.warehouse.database.location;

import se.melsom.warehouse.model.entity.StockLocation;

/**
 * The Stock location data access object.
 */
public class StockLocationDAO {
	private int id;
	private String section;
	private String slot;
	private String description;

    /**
     * Instantiates a new Stock location dao.
     *
     * @param id          the id
     * @param section     the section
     * @param slot        the slot
     * @param description the description
     */
    public StockLocationDAO(int id, String section, String slot, String description) {
		this.id = id;
		this.section = section;
		this.slot = slot;
		this.description = description;
	}

    /**
     * Instantiates a new Stock location dao.
     *
     * @param location the location
     */
    public StockLocationDAO(StockLocation location) {
		this.id = location.getId();
		this.section = location.getSection();
		this.slot = location.getSlot();
		this.description = location.getDescription();
	}

    /**
     * Create location stock location.
     *
     * @return the stock location
     */
    public StockLocation createLocation() {
		return new StockLocation(id, section, slot, description);
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
     * Gets id.
     *
     * @param value the value
     */
    public void getId(int value) {
		id = value;
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
	public String toString() {
		String text = "";
		
		text += "[";
		text += getId();
		text += "|";
		text += getSection();
		text += "|";
		text += getSlot();
		text += "|";
		text += getDescription();
		text += "]";
		
		return text;
	}
}
