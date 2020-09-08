package se.melsom.warehouse.importer;

/**
 * The enum Import type.
 */
public enum ImportType {
    /**
     * Inventory import type.
     */
    INVENTORY("Materiellista"),
    /**
     * Organizational units import type.
     */
    ORGANIZATIONAL_UNITS("Organisation"),
    /**
     * The Master inventory.
     */
    MASTER_INVENTORY("Materiellista VNG"),
    /**
     * The Stock locations and holdings.
     */
    STOCK_LOCATIONS_AND_HOLDINGS("Lagerplatser, och ansvar");
	
	private String name;
	
	ImportType(String name) {
		this.name = name;
	}

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return name;
	}
}
