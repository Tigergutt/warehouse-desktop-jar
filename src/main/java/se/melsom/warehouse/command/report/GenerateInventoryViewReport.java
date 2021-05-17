package se.melsom.warehouse.command.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.application.inventory.actual.ActualInventoryController;
import se.melsom.warehouse.command.GenerateReportCommand;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.report.inventory.InventoryViewReport;

import java.util.Vector;

public class GenerateInventoryViewReport extends GenerateReportCommand {
	private static final Logger logger = LoggerFactory.getLogger(GenerateInventoryViewReport.class);
	private final ApplicationPresentationModel controller;
	private final ActualInventoryController inventoryController;

    public GenerateInventoryViewReport(ApplicationPresentationModel controller, ActualInventoryController inventoryController) {
		this.controller = controller;
		this.inventoryController = inventoryController;
	}
	
	@Override
	public void execute() {
		logger.debug("Generate inventory report.");
		String section = inventoryController.getSelectedSection();
		String slot = inventoryController.getSelectedSlot();
		String location = section + slot;
		Vector<ActualInventoryVO> inventoryList = inventoryController.getInventory();
		String[] columnNames = inventoryController.getTableColumnNames();
		InventoryViewReport report = new InventoryViewReport(location, inventoryList, columnNames);
		
		save(report, controller.getDesktopView());
	}

}
