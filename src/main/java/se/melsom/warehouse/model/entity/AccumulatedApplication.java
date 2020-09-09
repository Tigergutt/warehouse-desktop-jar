package se.melsom.warehouse.model.entity;

import java.util.Vector;

/**
 * Accumulated applications for a unit and subordinate units.
 */
public class AccumulatedApplication extends ItemApplication {
	private Vector<ItemApplication> applications = new Vector<>();

    /**
     * Instantiates a new Accumulated application.
     *
     * @param item     the item
     * @param category the category
     */
    public AccumulatedApplication(Item item, String category) {
		super(null, item, category, 0);
	}

    /**
     * Add application.
     *
     * @param application the application
     */
    public void addApplication(ItemApplication application) {
		applications.addElement(application);
	}

	public int getQuantity() {
		int quantity = 0;

		for (ItemApplication anApplication : applications) {
			quantity += anApplication.getQuantity();
		}

		return quantity;
	}

}
