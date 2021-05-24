package se.melsom.warehouse.report.inventory.location;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.data.service.ActualInventoryService;
import se.melsom.warehouse.data.service.HoldingService;
import se.melsom.warehouse.data.service.StockLocationService;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.data.vo.HoldingVO;
import se.melsom.warehouse.data.vo.StockLocationVO;
import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.report.Report;
import se.melsom.warehouse.report.inventory.InventoryLandscapeTable;

import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class StockLocationReport extends Report {
	private static final Logger logger = LoggerFactory.getLogger(StockLocationReport.class);

	private static final String REPORT_NAME = "Materiellista";

	private final String reportName;
	private static final String[] tableHeader = {
			EntityName.ITEM_NUMBER, EntityName.ITEM_NAME, EntityName.INVENTORY_ACTUAL_QUANTITY
	};

	public StockLocationReport(HoldingVO holding, Vector<ActualInventoryVO> inventory) {
		logger.debug("Generating report.");
		String location = holding.getLocation().getLocationLabel();
		String callSign = holding.getUnit().getCallSign();
		String date = getDate();

		this.reportName = String.format("%s %s %s", REPORT_NAME, location.replace(":", ""), date);

		String oldestModificationDate = "9999-99-99";
		StockLocationPage currentPage = new StockLocationFrontPage(REPORT_NAME, date);
		InventoryLandscapeTable table = currentPage.getTable();
		table.setRowValues(0, tableHeader);
		pages.addElement(currentPage);

		for (int itemIndex = 0, rowIndex = 1; itemIndex < inventory.size(); itemIndex++) {
			if (rowIndex >= currentPage.getRowCapacity()) {
				logger.trace("Add page.");
				currentPage = new StockLocationTrailerPage(REPORT_NAME, date);
				table = currentPage.getTable();
				table.setRowValues(0, tableHeader);
				pages.addElement(currentPage);
			}

			ActualInventoryVO item = inventory.get(itemIndex);
			String[] values = {
				item.getItem().getNumber(),
				item.getItem().getName(),
				item.getIdentity().length() == 0 ? String.format("%d", item.getQuantity()) : String.format("#%d", item.getIdentity())
			};

			table.setRowValues(rowIndex++, values);
			DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
			String modificationDate = item.getLastModified().format(formatter);

			if (oldestModificationDate.compareTo(modificationDate) > 0) {
				oldestModificationDate = modificationDate;
			}
		}

		logger.debug("Setting page header data.");
		for (int pageIndex = 0; pageIndex < pages.size(); pageIndex++) {
			StockLocationPage page = (StockLocationPage) pages.get(pageIndex);

			logger.trace("pagee #" + pageIndex + "," + oldestModificationDate + "," + callSign);
			page.setLocation(location);
			page.setPageNumber(pageIndex + 1);
			page.setPageCount(pages.size());
			page.setCycleCountingDate(oldestModificationDate);
			page.setHoldingUnit(callSign);
		}
	}

	public StockLocationReport(HoldingService holdingService, StockLocationService stockLocationService, ActualInventoryService actualInventoryService) {
		String date = getDate();

		this.reportName = String.format("%s %s", REPORT_NAME, date);

		for (StockLocationVO location : stockLocationService.getStockLocations()) {
			Vector<ActualInventoryVO> inventory = actualInventoryService.getActualInventory(location);
			Vector<StockLocationPage> locationPages = new Vector<>();
			HoldingVO holding = holdingService.findByStockLocation(location);
			String locationLabel = holding.getLocation().getLocationLabel();
			String callSign = holding.getUnit().getCallSign();

			String oldestModificationDate = "9999-99-99";
			StockLocationPage currentPage = new StockLocationFrontPage(REPORT_NAME, date);
			InventoryLandscapeTable table = currentPage.getTable();
			table.setRowValues(0, tableHeader);
			locationPages.add(currentPage);

			for (int itemIndex = 0, rowIndex = 1; itemIndex < inventory.size(); itemIndex++) {
				if (rowIndex >= currentPage.getRowCapacity()) {
					logger.trace("Add page.");
					currentPage = new StockLocationTrailerPage(REPORT_NAME, date);
					table = currentPage.getTable();
					table.setRowValues(0, tableHeader);
					locationPages.addElement(currentPage);
				}

				ActualInventoryVO item = inventory.get(itemIndex);
				String[] values = {
						item.getItem().getNumber(),
						item.getItem().getName(),
						item.getIdentity().length() == 0 ? String.format("%d", item.getQuantity()) : String.format("#%s", item.getIdentity())
				};

				table.setRowValues(rowIndex++, values);
				DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
				String modificationDate = item.getLastModified().format(formatter);

				if (oldestModificationDate.compareTo(modificationDate) > 0) {
					oldestModificationDate = modificationDate;
				}
			}

			logger.debug("Setting page header data.");
			for (int pageIndex = 0; pageIndex < locationPages.size(); pageIndex++) {
				StockLocationPage page = (StockLocationPage) locationPages.get(pageIndex);

				logger.trace("pagee #" + pageIndex + "," + oldestModificationDate + "," + callSign);
				page.setLocation(locationLabel);
				page.setPageNumber(pageIndex + 1);
				page.setPageCount(locationPages.size());
				page.setCycleCountingDate(oldestModificationDate);
				page.setHoldingUnit(callSign);
				pages.add(page);
			}
		}
	}
	
	public String getReportName() {
		return reportName;
	}
}
