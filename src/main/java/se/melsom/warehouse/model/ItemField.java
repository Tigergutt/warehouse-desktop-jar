package se.melsom.warehouse.model;

/**
 * The enum Item field.
 */
public enum ItemField {
    /**
     * Number item field.
     */
    NUMBER("F-bet/artikel", ""),
    /**
     * Name item field.
     */
    NAME("Benämning", ""),
    /**
     * Packaging item field.
     */
    PACKAGING("Enhet", "");

	private String name;
	private String description;
	
	private ItemField(String name, String description) {	
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
