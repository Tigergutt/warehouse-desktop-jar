package se.melsom.warehouse.command.report;

import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.command.GenerateReportCommand;
import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.presentation.search.SearchController;
import se.melsom.warehouse.report.inventory.SearchViewReport;

public class GenerateSearchViewReport extends GenerateReportCommand  {
	private static Logger logger = Logger.getLogger(GenerateSearchViewReport.class);
	private ApplicationController controller;
	private SearchController searchController;
	
	public GenerateSearchViewReport(ApplicationController controller, SearchController searchController) {
		this.controller = controller;
		this.searchController = searchController;
	}
	
	@Override
	public void execute() {
		logger.debug("Generate inventory report.");
		String searchKey = searchController.getSearchKey();
		Vector<ActualInventory> inventoryList = searchController.getInventory();
		String[] columnNames = searchController.getTableColumnNames();
		SearchViewReport report = new SearchViewReport(searchKey, inventoryList, columnNames);
		
		save(report, controller.getDesktopView());
	}

}
