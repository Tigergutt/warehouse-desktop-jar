package se.melsom.warehouse.model;

public enum ItemField {
	NUMBER("F-bet/artikel", ""),
	NAME("Benämning", ""),
	PACKAGING("Enhet", "");

	private String name;
	private String description;
	
	private ItemField(String name, String description) {	
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
