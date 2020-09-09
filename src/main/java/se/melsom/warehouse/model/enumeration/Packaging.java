package se.melsom.warehouse.model.enumeration;

/**
 * The enum Packaging.
 */
public enum Packaging {
    /**
     * Piece packaging.
     */
    PIECE("ST"),
    /**
     * Packaging packaging.
     */
    PACKAGING("FÖRP"),
    /**
     * Roll packaging.
     */
    ROLL("RUL"),
    /**
     * Pair packaging.
     */
    PAIR("PAR"),
    /**
     * Set packaging.
     */
    SET("SATS"),
    /**
     * Bag packaging.
     */
    BAG("VÄSKA");
	
	private String name;
	
	Packaging(String name) {
		this.name = name;
	}

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return name;
	}
}
