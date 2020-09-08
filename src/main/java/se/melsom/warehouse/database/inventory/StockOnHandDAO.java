package se.melsom.warehouse.database.inventory;

/**
 * The type Stock on hand dao.
 */
@Deprecated
public class StockOnHandDAO {
	private String itemId;
	private int nominalQuantity;
	private int actualQuantity;
	private String identity;
	private String annotation;

    /**
     * Gets item id.
     *
     * @return the item id
     */
    public String getItemId() {
		return itemId;
	}

    /**
     * Sets itemt id.
     *
     * @param itemId the item id
     */
    public void setItemtId(String itemId) {
		this.itemId = itemId;
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
     * @param quantity the quantity
     */
    public void setNominalQuantity(int quantity) {
		this.nominalQuantity = quantity;
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
     * @param quantity the quantity
     */
    public void setActualQuantity(int quantity) {
		this.actualQuantity = quantity;
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
