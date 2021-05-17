package se.melsom.warehouse.command.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.command.GenerateReportCommand;
import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.inventory.StockOnHand;
import se.melsom.warehouse.report.component.Page;
import se.melsom.warehouse.report.inventory.StockOnHandPage;
import se.melsom.warehouse.report.inventory.StockOnHandReport;

import java.util.Vector;

public class GenerateStockOnHandReport  extends GenerateReportCommand  {
	private static final Logger logger = LoggerFactory.getLogger(GenerateStockOnHandReport.class);
	private final ApplicationPresentationModel controller;
	
	private static final String[] tableColumnNames = {
			EntityName.ITEM_NUMBER, EntityName.ITEM_NAME, 			
			EntityName.INVENTORY_NOMINAL_QUANTITY, EntityName.INVENTORY_ACTUAL_QUANTITY, EntityName.ITEM_PACKAGING,
			EntityName.INVENTORY_IDENTITY, EntityName.INVENTORY_ANNOTATION
		};

    public GenerateStockOnHandReport(ApplicationPresentationModel controller) {
		this.controller = controller;
	}
	
	@Override
	public void execute() {
		logger.debug("Generate stock on hand report.");
		Vector<String> tableHeader = new Vector<>();
		
		for (String columnName : tableColumnNames) {
			tableHeader.addElement(columnName);
		}
		
		Vector<StockOnHand> data = null; // controller.getInventoryAccounting().getStockOnHandList();
		StockOnHandReport report = new StockOnHandReport(tableHeader, data);
		
		for (int pageIndex = 0; pageIndex < report.getPages().size(); pageIndex++) {
			StockOnHandPage page = (StockOnHandPage) report.getPages().get(pageIndex);
			
			page.setPageNumber(pageIndex + 1);
			page.setPageCount(report.getPages().size());
		}

		for (Page page : report.getPages()) {
			((StockOnHandPage) page).updatePageNumberField();
		}

		
		save(report, controller.getDesktopView());
	}
}
