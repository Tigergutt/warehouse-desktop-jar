package se.melsom.warehouse.application;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

import se.melsom.warehouse.command.EditApplications;
import se.melsom.warehouse.command.EditInstances;
import se.melsom.warehouse.command.EditItems;
import se.melsom.warehouse.command.ExportDatabase;
import se.melsom.warehouse.command.ShowArticleSearchView;
import se.melsom.warehouse.command.ShowInventoryHoldingView;
import se.melsom.warehouse.command.ShowInventoryView;
import se.melsom.warehouse.command.ShowLoggerView;
import se.melsom.warehouse.command.ShowStockOnHandView;
import se.melsom.warehouse.command.importer.ImportInventory;
import se.melsom.warehouse.command.importer.ImportMasterInventory;
import se.melsom.warehouse.command.importer.ImportOrganizationalUnits;
import se.melsom.warehouse.command.importer.ImportStockLocations;
import se.melsom.warehouse.command.report.GenerateInventoryHoldingViewReport;
import se.melsom.warehouse.command.report.GenerateInventoryViewReport;
import se.melsom.warehouse.command.report.GenerateSearchViewReport;
import se.melsom.warehouse.command.report.GenerateStockLocationInventoryReport;
import se.melsom.warehouse.command.report.GenerateStockOnHandReport;
import se.melsom.warehouse.database.WarehouseDatabase;
import se.melsom.warehouse.event.ModelEventBroker;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.presentation.desktop.DesktopController;
import se.melsom.warehouse.presentation.desktop.DesktopView;
import se.melsom.warehouse.presentation.holding.InventoryHoldingController;
import se.melsom.warehouse.presentation.inventory.ActualInventoryController;
import se.melsom.warehouse.presentation.logger.LoggerController;
import se.melsom.warehouse.presentation.search.SearchController;
import se.melsom.warehouse.presentation.stock.StockOnHandController;
import se.melsom.warehouse.settings.PersistentSettings;

// This class acts as a main prensenter.

public class ApplicationController {
	private static Logger logger = Logger.getLogger(ApplicationController.class);
	private static final String APPLICATION_SETTINGS_FILENAME = "ApplicationSettings.json";
	private ModelEventBroker eventBroker = null;

	private PersistentSettings persistentSettings = PersistentSettings.singleton();

	private InventoryAccounting inventoryAccounting;
	
	private DesktopController desktopController = new DesktopController();
	private DesktopView desktopView = null;

	private ActualInventoryController inventoryController = null;
	private InventoryHoldingController inventoryHoldingController = null;
	private SearchController searchController = null;
	private StockOnHandController stockOnHandController = null;
	private LoggerController loggerController = null;

	public ApplicationController(ModelEventBroker eventBroker) {
		this.eventBroker = eventBroker;
	}

	public DesktopView getDesktopView() {
		return desktopView;
	}

	public DesktopController getDesktopController() {
		return desktopController;
	}
	
	public InventoryAccounting getInventoryAccounting() {
		return inventoryAccounting;
	}

	public void applicationStart() {
		logger.debug("Application start.");
		inventoryAccounting = new InventoryAccounting(WarehouseDatabase.singleton(), eventBroker);
		persistentSettings.loadData(APPLICATION_SETTINGS_FILENAME);		
		desktopController.createView(this);
		
		inventoryController = new ActualInventoryController(this);
		desktopController.addInternalFrame(inventoryController.getInternalFrame());
		eventBroker.addListener(inventoryController);
		GenerateInventoryViewReport generateInventoryReport = new GenerateInventoryViewReport(this, inventoryController);
		inventoryController.addActionCommand(ActualInventoryController.GENERATE_REPORT_ACTION, generateInventoryReport);
		desktopController.addActionCommand(DesktopController.SHOW_INVENTORY_VIEW, new ShowInventoryView(inventoryController));

		inventoryHoldingController = new InventoryHoldingController(this);
		desktopController.addInternalFrame(inventoryHoldingController.getInternalFrame());
		eventBroker.addListener(inventoryHoldingController);
		GenerateInventoryHoldingViewReport generateInventoryHoldingReport = new GenerateInventoryHoldingViewReport(this, inventoryHoldingController);
		inventoryHoldingController.addActionCommand(ActualInventoryController.GENERATE_REPORT_ACTION, generateInventoryHoldingReport);
		desktopController.addActionCommand(DesktopController.SHOW_INVENTORY_HOLDING_VIEW, new ShowInventoryHoldingView(inventoryHoldingController));

		searchController = new SearchController(this);
		desktopController.addInternalFrame(searchController.getInternalFrame());
		GenerateSearchViewReport generateSearchReport = new GenerateSearchViewReport(this, searchController);
		searchController.addActionCommand(SearchController.GENERATE_REPORT_ACTION, generateSearchReport);
		eventBroker.addListener(searchController);
		desktopController.addActionCommand(DesktopController.SHOW_SEARCH_INVENTORY_VIEW, new ShowArticleSearchView(searchController));

		stockOnHandController = new StockOnHandController(this);
		desktopController.addInternalFrame(stockOnHandController.getInternalFrame());
		eventBroker.addListener(stockOnHandController);
		desktopController.addActionCommand(DesktopController.SHOW_STOCK_ON_HAND_VIEW, new ShowStockOnHandView(stockOnHandController));
		
		loggerController = new LoggerController(this);
		desktopController.addInternalFrame(loggerController.getInternalFrame());
		desktopController.addActionCommand(DesktopController.SHOW_LOGGER_VIEW, new ShowLoggerView(loggerController));

		desktopController.addActionCommand(DesktopController.IMPORT_MASTER_INVENTORY, new ImportMasterInventory(this));
		desktopController.addActionCommand(DesktopController.IMPORT_INVENTORY, new ImportInventory(this));
		desktopController.addActionCommand(DesktopController.IMPORT_STOCK_LOCATIONS, new ImportStockLocations(this));
		desktopController.addActionCommand(DesktopController.IMPORT_ORGANIZATIONAL_UNITS, new ImportOrganizationalUnits(this));
		desktopController.addActionCommand(DesktopController.EXPORT_DATABASE, new ExportDatabase(this));
		desktopController.addActionCommand(DesktopController.EDIT_ITEMS, new EditItems(this));
		desktopController.addActionCommand(DesktopController.EDIT_INSTANCES, new EditInstances(this));
		desktopController.addActionCommand(DesktopController.EDIT_APPLICATIONS, new EditApplications(this));
		desktopController.addActionCommand(DesktopController.GENERATE_LOCATION_INVENTORY_REPORT, new GenerateStockLocationInventoryReport(this));
		desktopController.addActionCommand(DesktopController.GENERATE_STOCK_ON_HAND_REPORT, new GenerateStockOnHandReport(this));
		
		inventoryAccounting.sync();
	}

	public void setInventoryViewMenuItemChecked(boolean isChecked) {
		logger.trace("InventoryViewMenuItem isChecked=" + isChecked);
		desktopController.setShowInventoryViewMenuItemChecked(isChecked);
	}

	public void setInventoryHoldingViewMenuItemChecked(boolean isChecked) {
		logger.trace("InventoryHoldingViewMenuItem isChecked=" + isChecked);
		desktopController.setShowInventoryHoldingViewMenuItemChecked(isChecked);
	}

	public void setSearchViewMenuItemChecked(boolean isChecked) {
		logger.trace("SearchViewMenuItem isChecked=" + isChecked);
		desktopController.setShowSearchViewMenuItemChecked(isChecked);
	}

	public void setStockOnHandViewMenuItemChecked(boolean isChecked) {
		logger.trace("ShowStockOnHandViewMenuItem isChecked=" + isChecked);
		desktopController.setShowStockOnHandViewMenuItemChecked(isChecked);
	}

	public void setEditItemsMenuItemChecked(boolean isChecked) {
		logger.trace("EditProductsMenuItem isChecked=" + isChecked);
		desktopController.setEditProductsMenuItemChecked(isChecked);;
	}

	public void setLogViewMenuItemChecked(boolean isChecked) {
		logger.trace("ShowLogViewMenuItem isChecked=" + isChecked);
		desktopController.setShowLogViewMenuItemChecked(isChecked);;
	}

	public void applicationExit() {
		logger.debug("applicationExit()");
		try {
			persistentSettings.saveData(APPLICATION_SETTINGS_FILENAME);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
