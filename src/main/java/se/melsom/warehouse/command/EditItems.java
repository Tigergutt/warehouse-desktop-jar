package se.melsom.warehouse.command;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.presentation.maintenance.items.ItemMaintenanceController;

public class EditItems extends Command {
	ApplicationController controller;
	
	public EditItems(ApplicationController applicationController) {
		this.controller = applicationController;
	}

	@Override
	public void execute() {
		ItemMaintenanceController productsController = new ItemMaintenanceController(controller);

		controller.getDesktopController().addInternalFrame(productsController.getInternalFrame());
		
		productsController.showView();
	}

}
