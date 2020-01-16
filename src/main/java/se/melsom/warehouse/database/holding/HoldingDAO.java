package se.melsom.warehouse.database.holding;

import se.melsom.warehouse.model.entity.Holding;

public class HoldingDAO {
	private int unitId;
	private int locationId;
	
	public HoldingDAO(int unitId, int locationId) {
		this.unitId = unitId;
		this.locationId = locationId;
	}
	
	public HoldingDAO(Holding holding) {
		this.unitId = holding.getUnit().getId();
		this.locationId = holding.getLocation().getId();
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public int getLocationId() {
		return locationId;
	}

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
