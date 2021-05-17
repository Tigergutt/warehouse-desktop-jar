package se.melsom.warehouse.model.entity.inventory;

@Deprecated
public class MasterInventory extends Inventory {
	private String source = "";
	private String stockPoint = "";

    public String getSource() {
		return source;
	}

    public void setSource(String source) {
		this.source = source;
	}

    public String getStockPoint() {
		return stockPoint;
	}

    public void setStockPoint(String stockPoint) {
		this.stockPoint = stockPoint;
	}

	public boolean isValid() {
		if (getItem() == null) {
			return false;
		}

		return getSource() != null;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof MasterInventory) {
			if (!super.equals(object)) {
				return false;
			}

			MasterInventory other = (MasterInventory) object;

			if (!getSource().equals(other.getSource())) {
				return false;
			}

			return getStockPoint().equals(other.getStockPoint());
		}

		return false;
	}

	@Override
	public String toString() {
		return "(" + getId() + "," + getItem() + "," + getSource() + "," + getQuantity() + "," + getIdentity() + ")";
	}
}
