package se.melsom.warehouse.application.organization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.main.DesktopPresentationModel;
import se.melsom.warehouse.application.main.DesktopView;

@Component
public class EditOrganizationCommand extends Command {
	private static final Logger logger = LoggerFactory.getLogger(EditOrganizationCommand.class);

    OrganizationUnits organizationUnits;

    @Autowired
    public EditOrganizationCommand(OrganizationUnits organizationUnits, DesktopPresentationModel desktopPresentationModel) {
		this.organizationUnits = organizationUnits;
		desktopPresentationModel.addActionCommand(DesktopView.EDIT_ORGANIZATION, this);
	}

	@Override
	public void execute() {
    	logger.warn("Not yet implemented!");
	}

}
