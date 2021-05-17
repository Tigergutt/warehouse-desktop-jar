package se.melsom.warehouse.model.enumeration;

public enum LocationField {
    STOCK_LOCATION("Lagerplats", ""),
    SECTION("Sektion", ""),
    SLOT("Fack", "");

	private final String name;
	private final String description;
	
	LocationField(String name, String description) {
		this.name = name;
		this.description = description;
	}

    public String getName() {
		return name;
	}

    public String getDescription() {
		return description;
	}
}
