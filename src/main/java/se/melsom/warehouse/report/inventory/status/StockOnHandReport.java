package se.melsom.warehouse.report.inventory.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.data.vo.StockOnHandVO;
import se.melsom.warehouse.report.Report;

import java.util.Vector;

public class StockOnHandReport extends Report {
	private static final Logger logger = LoggerFactory.getLogger(StockOnHandReport.class);
	private static final String REPORT_NAME = "Inventeringsrutin";

    public StockOnHandReport(Vector<String> heading, Vector<StockOnHandVO> data) {
		logger.debug("Generating report.");
		for (int itemIndex = 0, rowIndex = 0; itemIndex < data.size(); itemIndex++) {
			StockOnHandVO item = data.get(itemIndex);
			
			if (!item.getNumber().startsWith("M") && !item.getNumber().startsWith("F")) {
				logger.debug("Ignoring item with number=" + item.getNumber());
				continue;
			}
			
			if (pages.isEmpty() || rowIndex >= ((StockOnHandPage) pages.lastElement()).getRowCapacity()) {
				logger.trace("Add page.");
				StockOnHandPage page = new StockOnHandPage(REPORT_NAME, getDate());

				page.setStockOnHandRow(0, heading); 
				pages.addElement(page);
				rowIndex = 1;
			}
			
			((StockOnHandPage) pages.lastElement()).setStockOnHandRow(rowIndex++, item);
		}
	}
	
	public String getReportName() {
		return REPORT_NAME + " " + getDate();
	}
}
