package se.melsom.warehouse.model.enumeration;

public enum ApplicationCategory {
	RU("RU", "Reglementerad utrustning");
	
	private String name;
	private String description;
	
	private ApplicationCategory(String name, String description) {	
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
