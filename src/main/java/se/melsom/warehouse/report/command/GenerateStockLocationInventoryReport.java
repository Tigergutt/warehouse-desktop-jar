package se.melsom.warehouse.report.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.desktop.DesktopPresentationModel;
import se.melsom.warehouse.application.desktop.DesktopView;
import se.melsom.warehouse.application.inventory.actual.ActualInventory;
import se.melsom.warehouse.data.service.ActualInventoryService;
import se.melsom.warehouse.data.service.HoldingService;
import se.melsom.warehouse.data.service.StockLocationService;
import se.melsom.warehouse.report.Report;
import se.melsom.warehouse.report.inventory.location.StockLocationReport;

@Component
public class GenerateStockLocationInventoryReport extends Command {
	private static final Logger logger = LoggerFactory.getLogger(GenerateStockLocationInventoryReport.class);

	@Autowired HoldingService holdingService;
	@Autowired StockLocationService stockLocationService;
	@Autowired ActualInventoryService actualInventoryService;

	private final ActualInventory actualInventory;
	
	@Autowired
    public GenerateStockLocationInventoryReport(ActualInventory actualInventory, DesktopPresentationModel desktopPresentationModel) {
		this.actualInventory = actualInventory;
		desktopPresentationModel.addActionCommand(DesktopView.GENERATE_LOCATION_INVENTORY_REPORT, this);
	}
	
	@Override
	public void execute() {
		logger.debug("Generate stock location inventory report.");
		StockLocationReport report = new StockLocationReport(holdingService, stockLocationService, actualInventoryService);

		// TODO: improve parent view handling.
		Report.save(report, null);
	}

}
