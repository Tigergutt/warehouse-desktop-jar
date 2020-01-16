package se.melsom.warehouse.command;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.presentation.maintenance.applications.ItemApplicationController;

public class EditApplications extends Command {
	ApplicationController controller;
	
	public EditApplications(ApplicationController applicationController) {
		this.controller = applicationController;
	}

	@Override
	public void execute() {
		new ItemApplicationController(controller);
	}

}
