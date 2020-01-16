package se.melsom.warehouse.database.inventory;

@Deprecated
public class StockOnHandDAO {
	private String itemId;
	private int nominalQuantity;
	private int actualQuantity;
	private String identity;
	private String annotation;

	public String getItemId() {
		return itemId;
	}

	public void setItemtId(String itemId) {
		this.itemId = itemId;
	}

	public int getNominalQuantity() {
		return nominalQuantity;
	}

	public void setNominalQuantity(int quantity) {
		this.nominalQuantity = quantity;
	}
	
	public int getActualQuantity() {
		return actualQuantity;
	}

	public void setActualQuantity(int quantity) {
		this.actualQuantity = quantity;
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
		String text = "[";
		
		text += getItemId();
		text += "|";
		text += getNominalQuantity();
		text += "|";
		text += getActualQuantity();
		text += "|";
		text += getIdentity();
		text += "|";
		text += getAnnotation();
		text += "]";
		
		return text;
	}
}
