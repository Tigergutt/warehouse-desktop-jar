package se.melsom.warehouse.model.enumeration;

public enum StockPoint {
	EXTERNAL("VNG"),
	INTERNAL("Kompaniförråd"),
	VEHICLE_RENTAL("Biluthyrning");
	
	private String name;
	
	private StockPoint(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
