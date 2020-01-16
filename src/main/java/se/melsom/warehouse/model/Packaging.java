package se.melsom.warehouse.model;

public enum Packaging {
	PIECE("ST"),
	PACKAGING("FÖRP"),
	ROLL("RUL"),
	PAIR("PAR"),
	SET("SATS"),
	BAG("VÄSKA");
	
	private String name;
	
	Packaging(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
