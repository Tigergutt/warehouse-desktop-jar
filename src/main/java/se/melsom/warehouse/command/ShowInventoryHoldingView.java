package se.melsom.warehouse.command;

import se.melsom.warehouse.presentation.holding.InventoryHoldingController;

/**
 * The Show inventory holding view command.
 */
public class ShowInventoryHoldingView extends Command {
    /**
     * The Inventory controller.
     */
    InventoryHoldingController inventoryController;

    /**
     * Instantiates a new Show inventory holding view.
     *
     * @param inventoryController the inventory controller
     */
    public ShowInventoryHoldingView(InventoryHoldingController inventoryController) {
		this.inventoryController = inventoryController;
	}

	@Override
	public void execute() {
		inventoryController.showView();
	}

}
