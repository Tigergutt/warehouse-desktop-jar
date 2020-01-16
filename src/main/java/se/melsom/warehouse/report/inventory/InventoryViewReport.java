package se.melsom.warehouse.report.inventory;

import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.report.Report;

public class InventoryViewReport extends Report {
	private static Logger logger = Logger.getLogger(InventoryViewReport.class);
	private static final String REPORT_NAME = "Materiellista";
	private String location;

	public InventoryViewReport(String location, Vector<ActualInventory> inventoryList, String... columnHeaders) {
		logger.debug("Generating report.");
		this.location = location;
	}
	
	public String getReportName() {
		return REPORT_NAME + " " + location + " " + getDate();
	}
}
