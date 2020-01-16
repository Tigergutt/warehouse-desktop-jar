package se.melsom.warehouse.command;

import se.melsom.warehouse.presentation.holding.InventoryHoldingController;

public class ShowInventoryHoldingView extends Command {
	InventoryHoldingController inventoryController;

	public ShowInventoryHoldingView(InventoryHoldingController inventoryController) {
		this.inventoryController = inventoryController;
	}

	@Override
	public void execute() {
		inventoryController.showView();
	}

}
