package se.melsom.warehouse.application.holdings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.AbstractPresentationModel;

@Component
public class StockLocationHolding extends AbstractPresentationModel {
    @Autowired AbstractStockLocationHoldingView view;

    public StockLocationHolding() {
	}

    @Override
    public void initialize() {
    }

    @Override
    public void showView() {
    }
}
