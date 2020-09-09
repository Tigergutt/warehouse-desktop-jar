package se.melsom.warehouse.command;

import se.melsom.warehouse.presentation.stock.StockOnHandController;

/**
 * The Show stock on hand view command.
 */
public class ShowStockOnHandView extends Command {
    /**
     * The Stock on hand controller.
     */
    StockOnHandController stockOnHandController;

    /**
     * Instantiates a new Show stock on hand view.
     *
     * @param stockOnHandController the stock on hand controller
     */
    public ShowStockOnHandView(StockOnHandController stockOnHandController) {
		this.stockOnHandController = stockOnHandController;
	}

	@Override
	public void execute() {
		stockOnHandController.showView();
	}

}
