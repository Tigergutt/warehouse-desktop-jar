package se.melsom.warehouse.command;

import se.melsom.warehouse.presentation.inventory.ActualInventoryController;

public class ShowInventoryView extends Command {
	ActualInventoryController inventoryController;

	public ShowInventoryView(ActualInventoryController inventoryController) {
		this.inventoryController = inventoryController;
	}

	@Override
	public void execute() {
		inventoryController.showView();
	}

}
