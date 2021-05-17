package se.melsom.warehouse.application.holdings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.main.DesktopPresentationModel;
import se.melsom.warehouse.application.main.DesktopView;

@Component
public class EditHoldingsCommand extends Command {
	private static final Logger logger = LoggerFactory.getLogger(EditHoldingsCommand.class);

    StockLocationHolding locationHolding;

    @Autowired
    public EditHoldingsCommand(StockLocationHolding locationHolding, DesktopPresentationModel desktopPresentationModel) {
		this.locationHolding = locationHolding;
		desktopPresentationModel.addActionCommand(DesktopView.EDIT_STOCK_LOCATIONS, this);
	}

	@Override
	public void execute() {
		logger.warn("Not yet implemented!");
	}

}
