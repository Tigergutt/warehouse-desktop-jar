package se.melsom.warehouse.importer;

public enum ImportType {
    INVENTORY("Materiellista"),
    ORGANIZATIONAL_UNITS("Organisation"),
    MASTER_INVENTORY("Materiellista VNG"),
    STOCK_LOCATIONS_AND_HOLDINGS("Lagerplatser, och ansvar");
	
	private final String name;
	
	ImportType(String name) {
		this.name = name;
	}

    public String getName() {
		return name;
	}
}
