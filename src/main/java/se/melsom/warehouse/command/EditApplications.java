package se.melsom.warehouse.command;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.presentation.maintenance.applications.ItemApplicationController;

/**
 * The Edit applications command.
 */
public class EditApplications extends Command {
    /**
     * The Controller.
     */
    ApplicationController controller;

    /**
     * Instantiates a new Edit applications.
     *
     * @param applicationController the application controller
     */
    public EditApplications(ApplicationController applicationController) {
		this.controller = applicationController;
	}

	@Override
	public void execute() {
		new ItemApplicationController(controller);
	}

}
