package se.melsom.warehouse.command;

import se.melsom.warehouse.presentation.stock.StockOnHandController;

public class ShowStockOnHandView extends Command {
	StockOnHandController stockOnHandController;

	public ShowStockOnHandView(StockOnHandController stockOnHandController) {
		this.stockOnHandController = stockOnHandController;
	}

	@Override
	public void execute() {
		stockOnHandController.showView();
	}

}
