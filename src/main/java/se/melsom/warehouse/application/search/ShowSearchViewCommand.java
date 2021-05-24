package se.melsom.warehouse.application.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.maintenance.items.ContentModel;
import se.melsom.warehouse.application.desktop.DesktopPresentationModel;
import se.melsom.warehouse.application.desktop.DesktopView;

@Component
public class ShowSearchViewCommand extends Command {
	private static final Logger logger = LoggerFactory.getLogger(ContentModel.class);

    SearchPresentationModel presentationModel;

    @Autowired
    public ShowSearchViewCommand(SearchPresentationModel presentationModel, DesktopPresentationModel desktopPresentationModel) {
		logger.debug("Execute constructor.");
		this.presentationModel = presentationModel;
		desktopPresentationModel.addActionCommand(DesktopView.SHOW_SEARCH_INVENTORY_VIEW, this);
	}

	@Override
	public void execute() {
		presentationModel.showView();
	}
}
