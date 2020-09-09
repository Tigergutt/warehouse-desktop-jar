package se.melsom.warehouse.command;

import se.melsom.warehouse.presentation.inventory.ActualInventoryController;

/**
 * The Show inventory view command.
 */
public class ShowInventoryView extends Command {
    /**
     * The Inventory controller.
     */
    ActualInventoryController inventoryController;

    /**
     * Instantiates a new Show inventory view.
     *
     * @param inventoryController the inventory controller
     */
    public ShowInventoryView(ActualInventoryController inventoryController) {
		this.inventoryController = inventoryController;
	}

	@Override
	public void execute() {
		inventoryController.showView();
	}

}
