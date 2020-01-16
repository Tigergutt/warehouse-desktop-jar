package se.melsom.warehouse.command.report;

import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.command.GenerateReportCommand;
import se.melsom.warehouse.database.WarehouseDatabase;
import se.melsom.warehouse.database.inventory.StockLocationItem;
import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.Holding;
import se.melsom.warehouse.report.inventory.StockLocationReport;

public class GenerateStockLocationInventoryReport  extends GenerateReportCommand  {
	private static Logger logger = Logger.getLogger(GenerateStockLocationInventoryReport.class);
	private ApplicationController controller;
	
	private static String[] tableColumnNames = {
		EntityName.ITEM_NUMBER, EntityName.ITEM_NAME, EntityName.INVENTORY_ACTUAL_QUANTITY
	};
	
	public GenerateStockLocationInventoryReport(ApplicationController controller) {
		this.controller = controller;
	}
	
	@Override
	public void execute() {
		logger.debug("Generate stock location inventory report.");
		Vector<String> tableHeader = new Vector<>();
		
		for (String columnName : tableColumnNames) {
			tableHeader.addElement(columnName);
		}
		
		Vector<StockLocationItem> data = WarehouseDatabase.singleton().selectStockLocationInventoryList();
		Vector<Holding> holdings = controller.getInventoryAccounting().getHoldings();
		logger.debug("Generate stock on hand report total row count=" + data.size());
		StockLocationReport report = new StockLocationReport(tableHeader, data, holdings);
		
		save(report, controller.getDesktopView());
	}

}
