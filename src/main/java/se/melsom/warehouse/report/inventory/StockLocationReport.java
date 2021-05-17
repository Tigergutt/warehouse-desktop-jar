package se.melsom.warehouse.report.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.data.vo.StockLocationVO;
import se.melsom.warehouse.model.entity.Holding;
import se.melsom.warehouse.report.Report;
import se.melsom.warehouse.report.part.InventoryLandscapeTable;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class StockLocationReport extends Report {
	private static final Logger logger = LoggerFactory.getLogger(StockLocationReport.class);
	private static final String REPORT_NAME = "Materiellista";

    public StockLocationReport(Vector<String> tableHeader, Vector<StockLocationVO> data, Vector<Holding> holdings) {
		logger.debug("Generating report.");
		Map<String, LinkedList<StockLocationVO>> locationInventory = new TreeMap<>();
		LinkedList<StockLocationVO> currentLocationInventory = null;
		String currentStockLocationLabel = "";

		logger.debug("Split data into locations.");
		for (StockLocationVO dao : data) {
			String stockLocationLabel = dao.getSection() + dao.getSlot();
			
			if (!currentStockLocationLabel.equals(stockLocationLabel)) {
				currentStockLocationLabel = stockLocationLabel;
				currentLocationInventory = new LinkedList<>();
				locationInventory.put(currentStockLocationLabel, currentLocationInventory);
				logger.trace("Creating list for location=" + currentStockLocationLabel);
			}
			
			logger.trace("Adding dao=" + dao);
			currentLocationInventory.add(dao);
		}
		
		logger.debug("Create report pages.");
		for (String location : locationInventory.keySet()) {
			logger.trace("location=" + location);
			String holdingUnit = "AA";
			
			for (Holding holding : holdings) {
				if (location.equals(holding.getLocation().getLocationLabel())) {
					holdingUnit = "";
					holdingUnit += holding.getUnit().getCallsign();
//					holdingUnit += " (" + holding.getUnit().getName() + ")";
				}
			}
			
			LinkedList<StockLocationVO> inventory = locationInventory.get(location);
			Vector<StockLocationPage> locationPages = new Vector<>();
			int rowIndex = 0;
			
			StockLocationPage currentPage = new StockLocationFrontPage(REPORT_NAME, getDate());
			InventoryLandscapeTable table = currentPage.getTable();

			logger.trace("table header=" + tableHeader.get(0) + "|" + tableHeader.get(1) + "|" + tableHeader.get(2));
			table.setRowValues(rowIndex++, tableHeader.get(0), tableHeader.get(1), tableHeader.get(2));
			
			locationPages.addElement(currentPage);
			
			String oldestModificationDate = "9999-99-99";
			
			logger.debug("Create page(s) for location=" + location);
			do {
				if (rowIndex >= currentPage.getRowCapacity()) {
					currentPage = new StockLocationTrailerPage(REPORT_NAME, getDate());
					rowIndex = 0;
					logger.trace("table header=" + tableHeader.get(0) + "|" + tableHeader.get(1) + "|" + tableHeader.get(2));
					currentPage.getTable().setRowValues(rowIndex++, tableHeader.get(0), tableHeader.get(1), tableHeader.get(2));
					locationPages.addElement(currentPage);
				}
				
//				StockLocationItem dao = inventory.removeFirst();
//				String col0 = dao.getNumber();
//				String col1 = dao.getName();
//				String col2 = dao.getIdentity().length() == 0 ? "" + dao.getQuantity() : "#" + dao.getIdentity();
//
//				if (oldestModificationDate.compareTo(dao.getDate()) > 0) {
//					oldestModificationDate = dao.getDate();
//				}
				
//				logger.trace("table row=" + col0 + "|" + col1 + "|" + col2);
//				currentPage.getTable().setRowValues(rowIndex++, col0, col1, col2);
			} while (!inventory.isEmpty());	
			
			logger.debug("Setting page header data.");
			for (int pageIndex = 0; pageIndex < locationPages.size(); pageIndex++) {
				StockLocationPage page = locationPages.get(pageIndex);

				logger.trace("pagee #" + pageIndex + "," + oldestModificationDate + "," + holdingUnit);				
				page.setLocation(location);
				page.setPageNumber(pageIndex + 1);
				page.setPageCount(locationPages.size());
				page.setCycleCountingDate(oldestModificationDate);
				page.setHoldingUnit(holdingUnit);
				pages.add(page);
			}
		}
	}
	
	public String getReportName() {
		return REPORT_NAME + " " + getDate();
	}
}
