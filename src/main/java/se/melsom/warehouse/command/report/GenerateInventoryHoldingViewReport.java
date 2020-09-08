package se.melsom.warehouse.command.report;

import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.command.GenerateReportCommand;
import se.melsom.warehouse.model.entity.OrganizationalUnit;
import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.presentation.holding.InventoryHoldingController;
import se.melsom.warehouse.report.component.Page;
import se.melsom.warehouse.report.inventory.InventoryHoldingViewReport;
import se.melsom.warehouse.report.inventory.InventoryPage;

/**
 * The Generate inventory holding view report command.
 */
public class GenerateInventoryHoldingViewReport extends GenerateReportCommand {
	private static Logger logger = Logger.getLogger(GenerateInventoryHoldingViewReport.class);
	private ApplicationController controller;
	private InventoryHoldingController inventoryController;

    /**
     * Instantiates a new Generate inventory holding view report.
     *
     * @param controller          the controller
     * @param inventoryController the inventory controller
     */
    public GenerateInventoryHoldingViewReport(ApplicationController controller, InventoryHoldingController inventoryController) {
		this.controller = controller;
		this.inventoryController = inventoryController;
	}
	
	@Override
	public void execute() {
		logger.debug("Generate inventory report.");
		OrganizationalUnit unit = inventoryController.getSelectedUnit();
		Vector<ActualInventory> inventoryList = inventoryController.getInventory();
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
