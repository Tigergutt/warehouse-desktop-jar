package se.melsom.warehouse.model.enumeration;

public enum ApplicationCategory {
    RU("RU", "Reglementerad utrustning");
	
	private final String name;
	private final String description;
	
	ApplicationCategory(String name, String description) {
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
