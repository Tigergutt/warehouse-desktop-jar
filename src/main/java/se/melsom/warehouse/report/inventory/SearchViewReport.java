package se.melsom.warehouse.report.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.report.Report;

import java.util.Vector;

public class SearchViewReport extends Report {
	private static final Logger logger = LoggerFactory.getLogger(InventoryViewReport.class);
	private static final String REPORT_NAME = "Materiellista";

    public SearchViewReport(String searchKey, Vector<ActualInventoryVO> inventoryList, String... columnHeaders) {
		logger.debug("Generating report.");
	}
	
	public String getReportName() {
		return REPORT_NAME + " " + getDate();
	}
}
