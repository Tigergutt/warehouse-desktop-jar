package se.melsom.warehouse.model.entity.inventory;

import se.melsom.warehouse.model.entity.StockLocation;

@Deprecated
public class ActualInventory extends Inventory {
	private StockLocation location;

    public StockLocation getLocation() {
		return location;
	}

    public void setLocation(StockLocation location) {
		this.location = location;
	}

    public int compareByLocation(ActualInventory other) {
		return getLocation().getLocationLabel().compareTo(other.getLocation().getLocationLabel());
	}

	public boolean isValid() {
		if (getItem() == null) {
			return false;
		}

		return getLocation() != null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ActualInventory) {
			if (!super.equals(obj)) {
				return false;
			}

			ActualInventory other = (ActualInventory) obj;

			return getLocation().equals(other.getLocation());
		}

		return false;
	}

	@Override
	public String toString() {
		return "(" + getId() + "," + getItem() + "," + getLocation() + "," + getQuantity() + "," + getIdentity() + ")";
	}
}
