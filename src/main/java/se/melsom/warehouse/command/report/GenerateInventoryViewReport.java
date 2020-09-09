package se.melsom.warehouse.command.report;

import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.command.GenerateReportCommand;
import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.presentation.inventory.ActualInventoryController;
import se.melsom.warehouse.report.inventory.InventoryViewReport;

/**
 * The Generate inventory view report command.
 */
public class GenerateInventoryViewReport extends GenerateReportCommand {
	private static Logger logger = Logger.getLogger(GenerateInventoryViewReport.class);
	private ApplicationController controller;
	private ActualInventoryController inventoryController;

    /**
     * Instantiates a new Generate inventory view report.
     *
     * @param controller          the controller
     * @param inventoryController the inventory controller
     */
    public GenerateInventoryViewReport(ApplicationController controller, ActualInventoryController inventoryController) {
		this.controller = controller;
		this.inventoryController = inventoryController;
	}
	
	@Override
	public void execute() {
		logger.debug("Generate inventory report.");
		String section = inventoryController.getSelectedSection();
		String slot = inventoryController.getSelectedSlot();
		String location = section + slot;
		Vector<ActualInventory> inventoryList = inventoryController.getInventory();
		String[] columnNames = inventoryController.getTableColumnNames();
		InventoryViewReport report = new InventoryViewReport(location, inventoryList, columnNames);
		
		save(report, controller.getDesktopView());
	}

}
