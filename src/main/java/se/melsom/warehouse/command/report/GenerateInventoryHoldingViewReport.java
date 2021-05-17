package se.melsom.warehouse.command.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.application.inventory.holding.InventoryHolding;
import se.melsom.warehouse.command.GenerateReportCommand;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.data.vo.UnitVO;
import se.melsom.warehouse.report.component.Page;
import se.melsom.warehouse.report.inventory.InventoryHoldingViewReport;
import se.melsom.warehouse.report.inventory.InventoryPage;

import java.util.Vector;

public class GenerateInventoryHoldingViewReport extends GenerateReportCommand {
	private static final Logger logger = LoggerFactory.getLogger(GenerateInventoryHoldingViewReport.class);
	private final ApplicationPresentationModel controller;
	private final InventoryHolding inventoryController;

    public GenerateInventoryHoldingViewReport(ApplicationPresentationModel controller, InventoryHolding inventoryController) {
		this.controller = controller;
		this.inventoryController = inventoryController;
	}
	
	@Override
	public void execute() {
		logger.debug("Generate inventory report.");
		UnitVO unit = inventoryController.getSelectedUnit();
		Vector<ActualInventoryVO> inventoryList = inventoryController.getInventory();
		String[] columnNames = inventoryController.getTableColumnNames();
		InventoryHoldingViewReport report = new InventoryHoldingViewReport(unit, inventoryList, columnNames);
		
		for (int pageIndex = 0; pageIndex < report.getPages().size(); pageIndex++) {
			Page page = report.getPages().get(pageIndex);
			
			page.setPageNumber(pageIndex + 1);
			page.setPageCount(report.getPages().size());
		}

		for (Page page : report.getPages()) {
			((InventoryPage) page).updatePageNumberField();
		}

		save(report, controller.getDesktopView());
	}

}
