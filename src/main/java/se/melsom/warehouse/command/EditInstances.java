package se.melsom.warehouse.command;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.presentation.maintenance.inventory.MasterInventoryController;

public class EditInstances extends Command {
	ApplicationController controller;
	
	public EditInstances(ApplicationController applicationController) {
		this.controller = applicationController;
	}

	@Override
	public void execute() {
		new MasterInventoryController(controller);
	}

}
