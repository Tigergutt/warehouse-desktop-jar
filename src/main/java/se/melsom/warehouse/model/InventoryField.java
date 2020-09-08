package se.melsom.warehouse.model;

/**
 * The enum Inventory field.
 */
public enum InventoryField {
    /**
     * Source inventory field.
     */
    SOURCE("Källa", ""),
    /**
     * Nominal quantity inventory field.
     */
    NOMINAL_QUANTITY("Antal", ""),
    /**
     * Actual quantity inventory field.
     */
    ACTUAL_QUANTITY("Invent.", ""),
    /**
     * Identity inventory field.
     */
    IDENTITY("Individ", ""),
    /**
     * Annotation inventory field.
     */
    ANNOTATION("Anteckning", ""),
    /**
     * Last updated timestamp inventory field.
     */
    LAST_UPDATED_TIMESTAMP("Senast ändrad", ""),
    /**
     * Stock point inventory field.
     */
    STOCK_POINT("Lagerställe", ""),
    /**
     * Stock on hand inventory field.
     */
    STOCK_ON_HAND("Lagersaldo", "");

	private String name;
	private String description;
	
	private InventoryField(String name, String description) {	
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
