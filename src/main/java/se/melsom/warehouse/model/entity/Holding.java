package se.melsom.warehouse.model.entity;

/**
 * The type Holding.
 */
public class Holding {
	private OrganizationalUnit unit;
	private StockLocation location;

    /**
     * Instantiates a new Holding.
     *
     * @param unit     the unit
     * @param location the location
     */
    public Holding(OrganizationalUnit unit, StockLocation location) {
		this.unit = unit;
		this.location = location;
	}

    /**
     * Gets unit.
     *
     * @return the unit
     */
    public OrganizationalUnit getUnit() {
		return unit;
	}

    /**
     * Sets unit.
     *
     * @param value the value
     */
    public void setUnit(OrganizationalUnit value) {
		unit = value;
	}

    /**
     * Gets location.
     *
     * @return the location
     */
    public StockLocation getLocation() {
		return location;
	}

    /**
     * Sets location.
     *
     * @param value the value
     */
    public void setLocation(StockLocation value) {
		location = value;
	}

    /**
     * Is valid boolean.
     *
     * @return the boolean
     */
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
