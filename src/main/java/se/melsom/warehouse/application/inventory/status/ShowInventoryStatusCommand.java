package se.melsom.warehouse.application.inventory.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.desktop.DesktopPresentationModel;
import se.melsom.warehouse.application.desktop.DesktopView;

@Component
public class ShowInventoryStatusCommand extends Command {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationPresentationModel.class);

	private final InventoryStatus presentationModel;

	@Autowired
    public ShowInventoryStatusCommand(InventoryStatus presentationModel, DesktopPresentationModel desktopPresentationModel) {
    	logger.debug("Execute constructor.");
		this.presentationModel = presentationModel;
		desktopPresentationModel.addActionCommand(DesktopView.SHOW_STOCK_ON_HAND_VIEW, this);
	}

	@Override
	public void execute() {
		logger.debug("Executing command.");
		presentationModel.showView();
	}
}
