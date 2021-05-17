package se.melsom.warehouse.command.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.command.GenerateReportCommand;
import se.melsom.warehouse.data.vo.StockLocationVO;
import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.Holding;
import se.melsom.warehouse.report.inventory.StockLocationReport;

import java.util.Vector;

public class GenerateStockLocationInventoryReport  extends GenerateReportCommand  {
	private static final Logger logger = LoggerFactory.getLogger(GenerateStockLocationInventoryReport.class);
	private final ApplicationPresentationModel controller;
	
	private static final String[] tableColumnNames = {
		EntityName.ITEM_NUMBER, EntityName.ITEM_NAME, EntityName.INVENTORY_ACTUAL_QUANTITY
	};

    public GenerateStockLocationInventoryReport(ApplicationPresentationModel controller) {
		this.controller = controller;
	}
	
	@Override
	public void execute() {
		logger.debug("Generate stock location inventory report.");
		Vector<String> tableHeader = new Vector<>();
		
		for (String columnName : tableColumnNames) {
			tableHeader.addElement(columnName);
		}

		// TODO: fixa till här!
//		Vector<StockLocationVO> data = WarehouseDatabase.singleton().selectStockLocationInventoryList();
		Vector<StockLocationVO> data = new Vector<>();
		Vector<Holding> holdings = null; // controller.getInventoryAccounting().getHoldings();
		logger.debug("Generate stock on hand report total row count=" + data.size());
		StockLocationReport report = new StockLocationReport(tableHeader, null, holdings);
		
		save(report, controller.getDesktopView());
	}

}
