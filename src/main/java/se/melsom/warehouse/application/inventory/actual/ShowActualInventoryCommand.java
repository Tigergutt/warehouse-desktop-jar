package se.melsom.warehouse.application.inventory.actual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.main.DesktopPresentationModel;
import se.melsom.warehouse.application.main.DesktopView;

@Component
public class ShowActualInventoryCommand extends Command {
	private static final Logger logger = LoggerFactory.getLogger(ShowActualInventoryCommand.class);

    ActualInventory presentationModel;

    @Autowired
    public ShowActualInventoryCommand(ActualInventory presentationModel, DesktopPresentationModel desktopPresentationModel) {
		logger.debug("Execute constructor.");
		this.presentationModel = presentationModel;
		desktopPresentationModel.addActionCommand(DesktopView.SHOW_INVENTORY_VIEW, this);
	}

	@Override
	public void execute() {
		logger.debug("Executing command.");
		presentationModel.showView();
	}

}
