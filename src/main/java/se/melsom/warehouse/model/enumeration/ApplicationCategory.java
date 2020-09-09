package se.melsom.warehouse.model.enumeration;

/**
 * The enum Application category.
 */
public enum ApplicationCategory {
    /**
     * The Ru.
     */
    RU("RU", "Reglementerad utrustning");
	
	private String name;
	private String description;
	
	private ApplicationCategory(String name, String description) {	
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
