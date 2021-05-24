package se.melsom.warehouse.maintenance.items;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.desktop.DesktopPresentationModel;
import se.melsom.warehouse.application.desktop.DesktopView;

@Component
public class EditItemsCommand extends Command {
	private static final Logger logger = LoggerFactory.getLogger(ContentModel.class);

	private final ItemMaintenance presentationModel;

	@Autowired
    public EditItemsCommand(ItemMaintenance presentationModel, DesktopPresentationModel desktopPresentationModel) {
		logger.debug("Execute constructor.");
		this.presentationModel = presentationModel;
		desktopPresentationModel.addActionCommand(DesktopView.EDIT_ITEMS, this);
	}

	@Override
	public void execute() {
		logger.debug("Executing command.");
		presentationModel.showView();
	}
}
