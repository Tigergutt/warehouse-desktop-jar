package se.melsom.warehouse.report.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.inventory.actual.ActualInventory;
import se.melsom.warehouse.data.service.HoldingService;

@Component
public class GenerateActualInventoryReport extends Command {
	private static final Logger logger = LoggerFactory.getLogger(GenerateActualInventoryReport.class);

	@Autowired HoldingService holdingService;

	private final ActualInventory actualInventory;

	@Autowired
    public GenerateActualInventoryReport(ActualInventory actualInventory) {
    	this.actualInventory = actualInventory;
	}
	
	@Override
	public void execute() {
//		logger.debug("Generate inventory report.");
//		holdingService.findByStockLocation(actualInventory.getStockLocation());
//		String location = String.format("%s%s", actualInventory.getSelectedSection(), actualInventory.getSelectedSlot());
//		Vector<ActualInventoryVO> inventoryList = actualInventory.getInventory();
//		String[] columnNames = actualInventory.getTableColumnNames();
//		ActualInventoryReport report = new ActualInventoryReport("", location, inventoryList, columnNames);
//
//		// TODO: improve dialog parent handling.
//		save(report, null);
	}

}
