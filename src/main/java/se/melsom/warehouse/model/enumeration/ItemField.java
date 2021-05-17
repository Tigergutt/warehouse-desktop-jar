package se.melsom.warehouse.model.enumeration;

public enum ItemField {
    NUMBER("F-bet/artikel", ""),
    NAME("Benämning", ""),
    PACKAGING("Enhet", "");

	private final String name;
	private final String description;
	
	ItemField(String name, String description) {
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
