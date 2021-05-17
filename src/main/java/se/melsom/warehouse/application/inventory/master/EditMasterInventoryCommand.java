package se.melsom.warehouse.application.inventory.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.main.DesktopPresentationModel;
import se.melsom.warehouse.application.main.DesktopView;

@Component
public class EditMasterInventoryCommand extends Command {
	private static final Logger logger = LoggerFactory.getLogger(EditMasterInventoryCommand.class);

	MasterInventory presentationModel;
	DesktopPresentationModel desktopPresentationModel;

	@Autowired
    public EditMasterInventoryCommand(MasterInventory presentationModel, DesktopPresentationModel desktopPresentationModel) {
		logger.debug("Execute constructor.");
		this.presentationModel = presentationModel;
		this.desktopPresentationModel = desktopPresentationModel;
		desktopPresentationModel.addActionCommand(DesktopView.EDIT_INSTANCES, this);
	}

	@Override
	public void execute() {
		logger.debug("Executing command.");
		presentationModel.showView();
	}
}
