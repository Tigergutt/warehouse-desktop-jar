package se.melsom.warehouse.model.enumeration;

public enum Packaging {
    PIECE("ST"),
    PACKAGING("FÖRP"),
    ROLL("RUL"),
    PAIR("PAR"),
    SET("SATS"),
    BAG("VÄSKA");
	
	private final String name;
	
	Packaging(String name) {
		this.name = name;
	}

    public String getName() {
		return name;
	}
}
