package se.melsom.warehouse.model.entity.inventory;

@Deprecated
public class StockOnHand {
	private String itemNumber;
	private String itemName;
	private String packaging;
	private int nominalQuantity;
	private int actualQuantity;
	private String identity;
	private String annotation;

    public String getItemNumber() {
		return itemNumber;
	}

    public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

    public String getItemName() {
		return itemName;
	}

    public void setItemName(String itemName) {
		this.itemName = itemName;
	}

    public String getPackaging() {
		return packaging;
	}

    public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

    public int getNominalQuantity() {
		return nominalQuantity;
	}

    public void setNominalQuantity(int nomnalQuantity) {
		this.nominalQuantity = nomnalQuantity;
	}

    public int getActualQuantity() {
		return actualQuantity;
	}

    public void setActualQuantity(int actualQuantity) {
		this.actualQuantity = actualQuantity;
	}

    public String getIdentity() {
		return identity;
	}

    public void setIdentity(String identity) {
		this.identity = identity;
	}

    public String getAnnotation() {
		return annotation;
	}

    public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	@Override
	public String toString() {
		String text = "(";
		text += getItemNumber();
		text += ",";
		text += getItemName();
		text += ",";
		text += getNominalQuantity();
		text += ",";
		text += getActualQuantity();
		text += ",";
		text += getPackaging();
		text += ",";
		text += getIdentity();
		text += ",";
		text += getAnnotation();
		text += ")";

		return text;
	}

    public int compareByItemNumber(StockOnHand other) {
		return getItemNumber().compareTo(other.getItemNumber());
	}

    public int compareByItemName(StockOnHand other) {
		return getItemName().compareTo(other.getItemName());
	}

    public int compareByIdentity(StockOnHand other) {
		if (getIdentity() != null && other.getIdentity() != null) {
			if (getIdentity().length() > 0 && other.getIdentity().length() > 0) {
				return getIdentity().compareTo(other.getIdentity());
			} else if (getIdentity().length() > 0 && other.getIdentity().length() == 0) {
				return -1;
			} else if (getIdentity().length() == 0 && other.getIdentity().length() > 0) {
				return 1;
			}
		} else if (getIdentity() == null && other.getIdentity() != null) {
			return 1;
		} else if (getIdentity() != null && other.getIdentity() == null) {
			return -1;
		}

		return 0;
	}
}
