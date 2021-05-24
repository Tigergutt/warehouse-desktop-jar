package se.melsom.warehouse.report.inventory.holding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.data.vo.UnitVO;
import se.melsom.warehouse.report.Report;
import se.melsom.warehouse.report.inventory.InventoryPage;

import java.util.Vector;

public class InventoryHoldingViewReport extends Report {
	private static final Logger logger = LoggerFactory.getLogger(InventoryHoldingViewReport.class);
	private static final String REPORT_NAME = "Materiellista";
	private final UnitVO unit;
	private final String date;

    public InventoryHoldingViewReport(UnitVO unit, Vector<ActualInventoryVO> inventory) {
		logger.debug("Generating report.");
		this.unit = unit;
		this.date = getDate();

		InventoryPage currentPage = new InventoryPage(REPORT_NAME, unit.getCallSign(), date);
		pages.addElement(currentPage);

		for (int itemIndex = 0, rowIndex = 1; itemIndex < inventory.size(); itemIndex++, rowIndex++) {
			if (rowIndex >= currentPage.getRowCapacity()) {
				logger.trace("Add page.");
				currentPage = new InventoryPage(REPORT_NAME, unit.getCallSign(), getDate());
				pages.addElement(currentPage);
				rowIndex = 1;
			}

			currentPage.setInventoryRow(rowIndex, inventory.get(itemIndex));
		}
	}
	
	public String getReportName() {
		return String.format("%s %s %s", unit.getCallSign(), REPORT_NAME, getDate());
	}
}
