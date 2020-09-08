package se.melsom.warehouse.database.holding;

import se.melsom.warehouse.model.entity.Holding;

/**
 * The Holding data access object.
 */
public class HoldingDAO {
	private int unitId;
	private int locationId;

    /**
     * Instantiates a new Holding dao.
     *
     * @param unitId     the unit id
     * @param locationId the location id
     */
    public HoldingDAO(int unitId, int locationId) {
		this.unitId = unitId;
		this.locationId = locationId;
	}

    /**
     * Instantiates a new Holding dao.
     *
     * @param holding the holding
     */
    public HoldingDAO(Holding holding) {
		this.unitId = holding.getUnit().getId();
		this.locationId = holding.getLocation().getId();
	}

    /**
     * Gets unit id.
     *
     * @return the unit id
     */
    public int getUnitId() {
		return unitId;
	}

    /**
     * Sets unit id.
     *
     * @param unitId the unit id
     */
    public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

    /**
     * Gets location id.
     *
     * @return the location id
     */
    public int getLocationId() {
		return locationId;
	}

    /**
     * Sets location id.
     *
     * @param locationId the location id
     */
    public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	
	@Override
	public String toString() {
		String text = "";
		
		text += "[";
		text += getUnitId();
		text += "|";
		text += getLocationId();
		text += "]";
		
		return text;
	}
}
