package se.melsom.warehouse.command.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.application.search.SearchPresentationModel;
import se.melsom.warehouse.command.GenerateReportCommand;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.report.inventory.SearchViewReport;

import java.util.Vector;

public class GenerateSearchViewReport extends GenerateReportCommand  {
	private static final Logger logger = LoggerFactory.getLogger(GenerateSearchViewReport.class);
	private final ApplicationPresentationModel controller;
	private final SearchPresentationModel searchPresenterModel;

    public GenerateSearchViewReport(ApplicationPresentationModel controller, SearchPresentationModel searchPresenterModel) {
		this.controller = controller;
		this.searchPresenterModel = searchPresenterModel;
	}
	
	@Override
	public void execute() {
		logger.debug("Generate inventory report.");
		String searchKey = searchPresenterModel.getSearchKey();
		Vector<ActualInventoryVO> inventoryList = searchPresenterModel.getInventory();
		String[] columnNames = searchPresenterModel.getTableColumnNames();
		SearchViewReport report = new SearchViewReport(searchKey, inventoryList, columnNames);
		
		save(report, controller.getDesktopView());
	}

}
