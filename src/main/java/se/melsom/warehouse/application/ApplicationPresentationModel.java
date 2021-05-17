package se.melsom.warehouse.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.melsom.warehouse.application.main.DesktopPresentationModel;
import se.melsom.warehouse.settings.PersistentSettings;

import javax.swing.*;
import java.io.IOException;

public class ApplicationPresentationModel {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationPresentationModel.class);
	private static final String APPLICATION_SETTINGS_FILENAME = "ApplicationSettings.json";

	@Autowired private DesktopPresentationModel desktopPresentationModel;
	@Autowired private PersistentSettings persistentSettings;

	public ApplicationPresentationModel() {
		logger.debug("Execute constructor.");
	}

	public void initialize() {
		logger.debug("Application initialize.");
		persistentSettings.loadData(APPLICATION_SETTINGS_FILENAME);

//		inventoryController = new ActualInventoryController(this);
//		desktopController.addInternalFrame(inventoryController.getInternalFrame());
//		eventBroker.addListener(inventoryController);
//		GenerateInventoryViewReport generateInventoryReport = new GenerateInventoryViewReport(this, inventoryController);
//		inventoryController.addActionCommand(ActualInventoryController.GENERATE_REPORT_ACTION, generateInventoryReport);
//		desktopController.addActionCommand(DesktopController.SHOW_INVENTORY_VIEW, new ShowInventoryView(inventoryController));

//		inventoryHoldingController = new InventoryHoldingController(this);
//		desktopController.addInternalFrame(inventoryHoldingController.getInternalFrame());
//		eventBroker.addListener(inventoryHoldingController);
//		GenerateInventoryHoldingViewReport generateInventoryHoldingReport = new GenerateInventoryHoldingViewReport(this, inventoryHoldingController);
//		inventoryHoldingController.addActionCommand(ActualInventoryController.GENERATE_REPORT_ACTION, generateInventoryHoldingReport);
//		desktopController.addActionCommand(DesktopController.SHOW_INVENTORY_HOLDING_VIEW, new ShowInventoryHoldingView(inventoryHoldingController));

//		searchController = new SearchController(this);
//		desktopController.addInternalFrame(searchController.getInternalFrame());
//		GenerateSearchViewReport generateSearchReport = new GenerateSearchViewReport(this, searchController);
//		searchController.addActionCommand(SearchController.GENERATE_REPORT_ACTION, generateSearchReport);
//		eventBroker.addListener(searchController);
//		desktopController.addActionCommand(DesktopController.SHOW_SEARCH_INVENTORY_VIEW, new ShowArticleSearchView(searchController));

//		loggerController = new LoggerController(this);
//		desktopController.addInternalFrame(loggerController.getInternalFrame());
//		desktopController.addActionCommand(DesktopController.SHOW_LOGGER_VIEW, new ShowLoggerView(loggerController));

//		desktopController.addActionCommand(DesktopController.IMPORT_MASTER_INVENTORY, new ImportMasterInventory(this));
//		desktopController.addActionCommand(DesktopController.IMPORT_INVENTORY, new ImportInventory(this));
//		desktopController.addActionCommand(DesktopController.IMPORT_STOCK_LOCATIONS, new ImportStockLocations(this));
//		desktopController.addActionCommand(DesktopController.IMPORT_ORGANIZATIONAL_UNITS, new ImportOrganizationalUnits(this));
//		desktopController.addActionCommand(DesktopController.EXPORT_DATABASE, new ExportDatabase(this));
//		desktopController.addActionCommand(DesktopController.EDIT_ITEMS, new EditItems(this));
//		desktopController.addActionCommand(DesktopController.EDIT_INSTANCES, new EditInstances(this));
//		desktopController.addActionCommand(DesktopController.EDIT_APPLICATIONS, new EditApplications(this));
//		desktopController.addActionCommand(DesktopController.GENERATE_LOCATION_INVENTORY_REPORT, new GenerateStockLocationInventoryReport(this));
//		desktopController.addActionCommand(DesktopController.GENERATE_STOCK_ON_HAND_REPORT, new GenerateStockOnHandReport(this));

		desktopPresentationModel.initialize();

		for (AbstractPresentationModel presentationModel : AbstractPresentationModel.registeredPresentationModels) {
			logger.debug("Initalizing module [{}].", presentationModel.getClass());
			try {
				presentationModel.initialize();
			} catch (Exception e) {
				logger.warn("Failed: {}.", e.getMessage());
			}
		}
	}

	public void applicationExit() {
		logger.debug("applicationExit()");
		try {
			persistentSettings.saveData(APPLICATION_SETTINGS_FILENAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public JFrame getDesktopView() {
		return null;
	}
}
