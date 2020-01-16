package se.melsom.warehouse.database.inventory;

@Deprecated
public class StockOnHandItem {
	private String number;
	private String name;
	private String packaging;
	private int nominalQuantity;
	private int actualQuantity;
	private String identity;
	private String annotation;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setNominalQuantity(int nominalQuantity) {
		this.nominalQuantity = nominalQuantity;
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
		String text = "";

		text += "[";
		text += getNumber();
		text += "|";
		text += getName();
		text += "|";
		text += getNominalQuantity();
		text += "|";
		text += getActualQuantity();
		text += "|";
		text += getIdentity();
		text += "]";

		return text;
	}
}
