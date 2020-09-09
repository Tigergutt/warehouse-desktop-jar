package se.melsom.warehouse.command;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.presentation.maintenance.items.ItemMaintenanceController;

/**
 * The Edit items command.
 */
public class EditItems extends Command {
    /**
     * The Controller.
     */
    ApplicationController controller;

    /**
     * Instantiates a new Edit items.
     *
     * @param applicationController the application controller
     */
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
