package se.melsom.warehouse.report.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.data.vo.UnitVO;
import se.melsom.warehouse.report.Report;

import java.util.Vector;

public class InventoryHoldingViewReport extends Report {
	private static final Logger logger = LoggerFactory.getLogger(InventoryHoldingViewReport.class);
	private static final String REPORT_NAME = "Materiellista";
	private final UnitVO unit;

    public InventoryHoldingViewReport(UnitVO unit, Vector<ActualInventoryVO> inventoryList, String... columnHeaders) {
		logger.debug("Generating report.");
		this.unit = unit;
		for (int itemIndex = 0, rowIndex = 0; itemIndex < inventoryList.size(); itemIndex++) {
			ActualInventoryVO item = inventoryList.get(itemIndex);
			
			if (pages.isEmpty() || rowIndex >= ((InventoryPage) pages.lastElement()).getRowCapacity()) {
				logger.trace("Add page.");
				InventoryPage page = new InventoryPage(REPORT_NAME, unit.getCallSign(), getDate());

				page.setInventoryRow(0, columnHeaders); 
				pages.addElement(page);
				rowIndex = 1;
			}
			
			((InventoryPage) pages.lastElement()).setInventoryRow(rowIndex++, item);
		}
	}
	
	public String getReportName() {
		return unit.getCallSign() + " " + REPORT_NAME + " " + getDate();
	}
}
