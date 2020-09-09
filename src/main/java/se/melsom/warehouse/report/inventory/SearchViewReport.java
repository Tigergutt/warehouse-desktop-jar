package se.melsom.warehouse.report.inventory;

import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.report.Report;

/**
 * The type Search view report.
 */
public class SearchViewReport extends Report {
	private static Logger logger = Logger.getLogger(InventoryViewReport.class);
	private static final String REPORT_NAME = "Materiellista";

    /**
     * Instantiates a new Search view report.
     *
     * @param searchKey     the search key
     * @param inventoryList the inventory list
     * @param columnHeaders the column headers
     */
    public SearchViewReport(String searchKey, Vector<ActualInventory> inventoryList, String... columnHeaders) {
		logger.debug("Generating report.");
	}
	
	public String getReportName() {
		return REPORT_NAME + " " + getDate();
	}
}
