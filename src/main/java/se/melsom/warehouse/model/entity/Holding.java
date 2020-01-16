package se.melsom.warehouse.model.entity;

public class Holding {
	private OrganizationalUnit unit;
	private StockLocation location;
	
	public Holding(OrganizationalUnit unit, StockLocation location) {
		this.unit = unit;
		this.location = location;
	}
	
	public OrganizationalUnit getUnit() {
		return unit;
	}
	
	public void setUnit(OrganizationalUnit value) {
		unit = value;
	}

	public StockLocation getLocation() {
		return location;
	}
	
	public void setLocation(StockLocation value) {
		location = value;
	}
	
	public boolean isValid() {
		if (unit == null) {
			return false;
		}
		
		if (location == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		return "(" + getUnit() + "," + getLocation() + ")";
	}
}
