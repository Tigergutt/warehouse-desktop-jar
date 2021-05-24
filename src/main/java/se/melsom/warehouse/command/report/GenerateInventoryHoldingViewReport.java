package se.melsom.warehouse.command.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.inventory.holding.InventoryHolding;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.data.vo.UnitVO;
import se.melsom.warehouse.report.Report;
import se.melsom.warehouse.report.component.Page;
import se.melsom.warehouse.report.inventory.holding.InventoryHoldingViewReport;
import se.melsom.warehouse.report.inventory.InventoryPage;

import java.util.Vector;

public class GenerateInventoryHoldingViewReport extends Command {
	private static final Logger logger = LoggerFactory.getLogger(GenerateInventoryHoldingViewReport.class);
	private final ApplicationPresentationModel applicationPresentationModel;
	private final InventoryHolding inventoryHolding;

    public GenerateInventoryHoldingViewReport(ApplicationPresentationModel controller, InventoryHolding inventoryController) {
		this.applicationPresentationModel = controller;
		this.inventoryHolding = inventoryController;
	}
	
	@Override
	public void execute() {
		logger.debug("Generate inventory report.");
		UnitVO unit = inventoryHolding.getSelectedUnit();
		Vector<ActualInventoryVO> inventoryList = inventoryHolding.getInventory();
		InventoryHoldingViewReport report = new InventoryHoldingViewReport(unit, inventoryList);
		
		for (int pageIndex = 0; pageIndex < report.getPages().size(); pageIndex++) {
			Page page = report.getPages().get(pageIndex);
			
			page.setPageNumber(pageIndex + 1);
			page.setPageCount(report.getPages().size());
		}

		for (Page page : report.getPages()) {
			((InventoryPage) page).updatePageNumberField();
		}

		Report.save(report, applicationPresentationModel.getDesktopView());
	}

}
