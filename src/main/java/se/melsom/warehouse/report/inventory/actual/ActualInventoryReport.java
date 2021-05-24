package se.melsom.warehouse.report.inventory.actual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.report.Report;
import se.melsom.warehouse.report.inventory.InventoryPage;

import java.util.Vector;

public class ActualInventoryReport extends Report {
	private static final Logger logger = LoggerFactory.getLogger(ActualInventoryReport.class);
	private static final String REPORT_NAME = "Materiellista";
	private final String location;
	private final String date;

    public ActualInventoryReport(String callSign, String location, Vector<ActualInventoryVO> inventoryList) {
		logger.debug("Generating report.");
		this.location = location;
		this.date = getDate();

		InventoryPage currentPage = new InventoryPage(REPORT_NAME, callSign, date);
		pages.addElement(currentPage);

		for (int itemIndex = 0, rowIndex = 1; itemIndex < inventoryList.size(); itemIndex++, rowIndex++) {
			if (rowIndex >= currentPage.getRowCapacity()) {
				logger.trace("Add page.");
				currentPage = new InventoryPage(REPORT_NAME, callSign, date);
				pages.addElement(currentPage);
			}

			currentPage.setInventoryRow(rowIndex, inventoryList.get(itemIndex));
		}
	}
	
	public String getReportName() {
		return REPORT_NAME + " " + location + " " + date;
	}
}
