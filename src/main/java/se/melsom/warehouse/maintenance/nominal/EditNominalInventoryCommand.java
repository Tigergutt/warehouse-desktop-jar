package se.melsom.warehouse.maintenance.nominal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.desktop.DesktopPresentationModel;
import se.melsom.warehouse.application.desktop.DesktopView;

@Component
public class EditNominalInventoryCommand extends Command {
	private static final Logger logger = LoggerFactory.getLogger(EditNominalInventoryCommand.class);

	NominalInventory presentationModel;
	DesktopPresentationModel desktopPresentationModel;

	@Autowired
    public EditNominalInventoryCommand(NominalInventory presentationModel, DesktopPresentationModel desktopPresentationModel) {
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
