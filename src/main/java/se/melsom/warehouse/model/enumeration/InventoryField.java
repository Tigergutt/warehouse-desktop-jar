package se.melsom.warehouse.model.enumeration;

public enum InventoryField {
    SOURCE("Källa", ""),
    NOMINAL_QUANTITY("Antal", ""),
    ACTUAL_QUANTITY("Invent.", ""),
    IDENTITY("Individ", ""),
    ANNOTATION("Anteckning", ""),
    LAST_UPDATED_TIMESTAMP("Senast ändrad", ""),
    STOCK_POINT("Lagerställe", ""),
    STOCK_ON_HAND("Lagersaldo", "");

	private final String name;
	private final String description;
	
	InventoryField(String name, String description) {
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
