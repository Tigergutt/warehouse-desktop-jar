package se.melsom.warehouse.application.inventory.actual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.desktop.DesktopPresentationModel;
import se.melsom.warehouse.application.desktop.DesktopView;

@Component
public class ShowActualInventoryCommand extends Command {
	private static final Logger logger = LoggerFactory.getLogger(ShowActualInventoryCommand.class);

    ActualInventory actualInventory;

    @Autowired
    public ShowActualInventoryCommand(ActualInventory actualInventory, DesktopPresentationModel desktopPresentationModel) {
		logger.debug("Execute constructor.");
		this.actualInventory = actualInventory;
		desktopPresentationModel.addActionCommand(DesktopView.SHOW_INVENTORY_VIEW, this);
	}

	@Override
	public void execute() {
		logger.debug("Executing command.");
		actualInventory.showView();
	}
}
