package se.melsom.warehouse.model.enumeration;

/**
 * The enum Location field.
 */
public enum LocationField {
    /**
     * Stock location location field.
     */
    STOCK_LOCATION("Lagerplats", ""),
    /**
     * Section location field.
     */
    SECTION("Sektion", ""),
    /**
     * Slot location field.
     */
    SLOT("Fack", "");

	private String name;
	private String description;
	
	private LocationField(String name, String description) {	
		this.name = name;
		this.description = description;
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
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
		return description;
	}
}
