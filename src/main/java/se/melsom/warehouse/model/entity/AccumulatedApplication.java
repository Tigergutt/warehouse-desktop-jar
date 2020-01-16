package se.melsom.warehouse.model.entity;

import java.util.Vector;

public class AccumulatedApplication extends ItemApplication {
	private Vector<ItemApplication> applications = new Vector<>();
	
	public AccumulatedApplication(Item item, String category) {
		super(null, item, category, 0);
	}

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
