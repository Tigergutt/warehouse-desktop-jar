package se.melsom.warehouse.application.applications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.main.DesktopPresentationModel;
import se.melsom.warehouse.application.main.DesktopView;

@Component
public class EditApplicationsCommand extends Command {
	private static final Logger logger = LoggerFactory.getLogger(EditApplicationsCommand.class);

	ItemApplications itemApplications;

    @Autowired
    public EditApplicationsCommand(ItemApplications itemApplications, DesktopPresentationModel desktopPresentationModel) {
		this.itemApplications = itemApplications;
		desktopPresentationModel.addActionCommand(DesktopView.EDIT_APPLICATIONS, this);
	}

	@Override
	public void execute() {
		logger.warn("Not yet implemented!");
	}

}
