package se.melsom.warehouse.application.inventory.holding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.desktop.DesktopPresentationModel;
import se.melsom.warehouse.application.desktop.DesktopView;

@Component
public class ShowInventoryHoldingCommand extends Command {
	private static final Logger logger = LoggerFactory.getLogger(ContentModel.class);

    InventoryHolding presentationModel;

    @Autowired
    public ShowInventoryHoldingCommand(InventoryHolding presentationModel, DesktopPresentationModel desktopPresentationModel) {
		logger.debug("Execute constructor.");
		this.presentationModel = presentationModel;
		desktopPresentationModel.addActionCommand(DesktopView.SHOW_INVENTORY_HOLDING_VIEW, this);
	}

	@Override
	public void execute() {
		logger.debug("Executing command.");
		presentationModel.showView();
	}

}
