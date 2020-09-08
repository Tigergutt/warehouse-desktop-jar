package se.melsom.warehouse.command;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.presentation.maintenance.inventory.MasterInventoryController;

/**
 * The Edit instances command.
 */
public class EditInstances extends Command {
    /**
     * The Controller.
     */
    ApplicationController controller;

    /**
     * Instantiates a new Edit instances.
     *
     * @param applicationController the application controller
     */
    public EditInstances(ApplicationController applicationController) {
		this.controller = applicationController;
	}

	@Override
	public void execute() {
		new MasterInventoryController(controller);
	}

}
