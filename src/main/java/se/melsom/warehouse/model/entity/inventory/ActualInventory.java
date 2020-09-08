package se.melsom.warehouse.model.entity.inventory;

import se.melsom.warehouse.model.entity.StockLocation;

/**
 * The type Actual inventory.
 */
public class ActualInventory extends Inventory {
	private StockLocation location;

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
     * @param location the location
     */
    public void setLocation(StockLocation location) {
		this.location = location;
	}

    /**
     * Compare by location int.
     *
     * @param other the other
     * @return the int
     */
    public int compareByLocation(ActualInventory other) {
		return getLocation().getLocationLabel().compareTo(other.getLocation().getLocationLabel());
	}
	
	public boolean isValid() {
		if (getItem() == null) {
			return false;
		}
		
		if (getLocation() == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ActualInventory) {
			if (!super.equals(obj)) {
				return false;
			}

			ActualInventory other = (ActualInventory) obj;
			
			if (!getLocation().equals(other.getLocation())) {
				return false;
			}
			
			return true;
		}
		
		return false;
	}

	@Override
	public String toString() {
		return "(" + getId() + "," + getItem() + "," + getLocation() + "," + getQuantity() + "," + getIdentity() + ")";
	}
}
