package se.melsom.warehouse.application.desktop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.settings.PersistentSettings;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class DesktopPresentationModel {
	private static final Logger logger = LoggerFactory.getLogger(DesktopPresentationModel.class);

	@Autowired private AbstractDesktopView desktopView;
    @Autowired private PersistentSettings persistentSettings;
	@Autowired private ApplicationPresentationModel applicationPresentationModel;

    private final ViewState viewState = new ViewState();
	private final Map<String, Command> actionCommands = new HashMap<>();

	public DesktopPresentationModel() {
		logger.debug("Execute constructor.");
	}

	@PostConstruct
	public void initialize() {
		logger.debug("Execute initialize().");
		desktopView.initialize();
		desktopView.showView();
		desktopView.updateState(viewState);
	}

    public void addInternalFrame(JInternalFrame internalFrame) {
		logger.debug("Adding internal frame.");
		desktopView.addView(internalFrame);
	}

    public void addActionCommand(String action, Command command) {
		logger.debug("Add action command [{}].", action);
		switch (action) {
			case DesktopView.SHOW_STOCK_ON_HAND_VIEW:
				viewState.setShowStockOnHandViewEnabled(true);
				break;

			case DesktopView.SHOW_INVENTORY_VIEW:
				viewState.setShowInventoryViewEnabled(true);
				break;

			case DesktopView.SHOW_SEARCH_INVENTORY_VIEW:
				viewState.setShowSearchViewEnabled(true);
				break;

			case DesktopView.SHOW_INVENTORY_HOLDING_VIEW:
				viewState.setShowInventoryHoldingViewEnabled(true);
				break;

			case DesktopView.GENERATE_STOCK_ON_HAND_REPORT:
				viewState.setGenerateStockOnHandReportEnabled(true);
				break;

			case DesktopView.GENERATE_LOCATION_INVENTORY_REPORT:
				viewState.setGenerateStockLocationReportEnabled(true);
				break;

			case DesktopView.EDIT_INSTANCES:
				viewState.setEditInstancesEnabled(true);
				break;

			case DesktopView.EDIT_ITEMS:
				viewState.setEditItemsEnabled(true);
				break;

			case DesktopView.EDIT_APPLICATIONS:
				viewState.setEditApplicationsEnabled(true);
				break;

			case DesktopView.EDIT_STOCK_LOCATIONS:
				viewState.setEditHoldingsEnabled(true);
				break;

			case DesktopView.EDIT_ORGANIZATION:
				viewState.setEditOrganizationEnabled(true);
				break;

			default:
				logger.warn("Attempt to add unknown action [{}].", action);
				return;
		}

		desktopView.updateState(viewState);
		actionCommands.put(action, command);
	}

    public void quitApplication() {
		logger.debug("Quit application.");
		applicationPresentationModel.applicationExit();
	}

	public void actionPerformed(String action) {
		logger.trace("Received action [{}].", action );
		Command command = actionCommands.get(action);
		
		if (command != null) {
			logger.trace("Executing command [{}].", command);
			command.execute();
		} else {
			logger.warn("Unhandled action command [{}].", action);
		}

		desktopView.updateState(viewState);
	}
}
