package se.melsom.warehouse.model;

/**
 * The enum Stock point.
 */
public enum StockPoint {
    /**
     * External stock point.
     */
    EXTERNAL("VNG"),
    /**
     * Internal stock point.
     */
    INTERNAL("Kompaniförråd"),
    /**
     * Vehicle rental stock point.
     */
    VEHICLE_RENTAL("Biluthyrning");
	
	private String name;
	
	private StockPoint(String name) {
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
