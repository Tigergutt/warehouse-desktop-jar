package se.melsom.warehouse.report.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.report.Report;

import java.util.Vector;

public class InventoryViewReport extends Report {
	private static final Logger logger = LoggerFactory.getLogger(InventoryViewReport.class);
	private static final String REPORT_NAME = "Materiellista";
	private final String location;

    public InventoryViewReport(String location, Vector<ActualInventoryVO> inventoryList, String... columnHeaders) {
		logger.debug("Generating report.");
		this.location = location;
	}
	
	public String getReportName() {
		return REPORT_NAME + " " + location + " " + getDate();
	}
}
