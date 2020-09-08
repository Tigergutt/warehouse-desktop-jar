package se.melsom.warehouse.database.inventory;

/**
 * The type Stock on hand item.
 */
@Deprecated
public class StockOnHandItem {
	private String number;
	private String name;
	private String packaging;
	private int nominalQuantity;
	private int actualQuantity;
	private String identity;
	private String annotation;

    /**
     * Gets number.
     *
     * @return the number
     */
    public String getNumber() {
		return number;
	}

    /**
     * Sets number.
     *
     * @param number the number
     */
    public void setNumber(String number) {
		this.number = number;
	}

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return name;
	}

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
		this.name = name;
	}

    /**
     * Gets packaging.
     *
     * @return the packaging
     */
    public String getPackaging() {
		return packaging;
	}

    /**
     * Sets packaging.
     *
     * @param packaging the packaging
     */
    public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

    /**
     * Gets nominal quantity.
     *
     * @return the nominal quantity
     */
    public int getNominalQuantity() {
		return nominalQuantity;
	}

    /**
     * Sets nominal quantity.
     *
     * @param nominalQuantity the nominal quantity
     */
    public void setNominalQuantity(int nominalQuantity) {
		this.nominalQuantity = nominalQuantity;
	}

    /**
     * Gets actual quantity.
     *
     * @return the actual quantity
     */
    public int getActualQuantity() {
		return actualQuantity;
	}

    /**
     * Sets actual quantity.
     *
     * @param actualQuantity the actual quantity
     */
    public void setActualQuantity(int actualQuantity) {
		this.actualQuantity = actualQuantity;
	}

    /**
     * Gets identity.
     *
     * @return the identity
     */
    public String getIdentity() {
		return identity;
	}

    /**
     * Sets identity.
     *
     * @param identity the identity
     */
    public void setIdentity(String identity) {
		this.identity = identity;
	}

    /**
     * Gets annotation.
     *
     * @return the annotation
     */
    public String getAnnotation() {
		return annotation;
	}

    /**
     * Sets annotation.
     *
     * @param annotation the annotation
     */
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
