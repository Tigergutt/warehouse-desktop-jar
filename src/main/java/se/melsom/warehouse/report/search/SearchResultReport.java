package se.melsom.warehouse.report.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.report.Report;
import se.melsom.warehouse.report.inventory.InventoryPage;
import se.melsom.warehouse.report.inventory.actual.ActualInventoryReport;

import java.util.Vector;

public class SearchResultReport extends Report {
	private static final Logger logger = LoggerFactory.getLogger(ActualInventoryReport.class);
	private static final String REPORT_NAME = "Materiellista (utsökning)";
	private final String date;

    public SearchResultReport(String searchKey, Vector<ActualInventoryVO> inventory) {
		logger.debug("Generating report.");
		this.date = getDate();

		InventoryPage currentPage = new InventoryPage(REPORT_NAME, "", date);
		pages.addElement(currentPage);

		for (int itemIndex = 0, rowIndex = 1; itemIndex < inventory.size(); itemIndex++, rowIndex++) {
			if (rowIndex >= currentPage.getRowCapacity()) {
				logger.trace("Add page.");
				currentPage = new InventoryPage(REPORT_NAME, "", date);
				pages.addElement(currentPage);
			}

			currentPage.setInventoryRow(rowIndex, inventory.get(itemIndex));
		}
	}
	
	public String getReportName() {
		return REPORT_NAME + " " + getDate();
	}
}
