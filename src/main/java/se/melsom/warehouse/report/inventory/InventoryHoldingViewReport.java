package se.melsom.warehouse.report.inventory;

import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.entity.OrganizationalUnit;
import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.report.Report;

/**
 * The type Inventory holding view report.
 */
public class InventoryHoldingViewReport extends Report {
	private static Logger logger = Logger.getLogger(InventoryHoldingViewReport.class);
	private static final String REPORT_NAME = "Materiellista";
	private OrganizationalUnit unit;

    /**
     * Instantiates a new Inventory holding view report.
     *
     * @param unit          the unit
     * @param inventoryList the inventory list
     * @param columnHeaders the column headers
     */
    public InventoryHoldingViewReport(OrganizationalUnit unit, Vector<ActualInventory> inventoryList, String... columnHeaders) {
		logger.debug("Generating report.");
		this.unit = unit;
		for (int itemIndex = 0, rowIndex = 0; itemIndex < inventoryList.size(); itemIndex++) {
			ActualInventory item = inventoryList.get(itemIndex);
			
			if (pages.isEmpty() || rowIndex >= ((InventoryPage) pages.lastElement()).getRowCapacity()) {
				logger.trace("Add page.");
				InventoryPage page = new InventoryPage(REPORT_NAME, unit.getCallsign(), getDate());

				page.setInventoryRow(0, columnHeaders); 
				pages.addElement(page);
				rowIndex = 1;
			}
			
			((InventoryPage) pages.lastElement()).setInventoryRow(rowIndex++, item);
		}
	}
	
	public String getReportName() {
		return unit.getCallsign() + " " + REPORT_NAME + " " + getDate();
	}
}
