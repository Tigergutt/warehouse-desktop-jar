package se.melsom.warehouse.application.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.AbstractPresentationModel;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.main.DesktopPresentationModel;
import se.melsom.warehouse.data.service.ActualInventoryService;
import se.melsom.warehouse.data.vo.ActualInventoryVO;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@Component
public class SearchPresentationModel extends AbstractPresentationModel {
	private static final Logger logger = LoggerFactory.getLogger(SearchPresentationModel.class);

	@Autowired private SearchView view;
	@Autowired private DesktopPresentationModel desktopPresentationModel;
	@Autowired private ActualInventoryService actualInventoryService;

//	public static final String SEARCH_ACTION = "SearchAction";
    public static final String GENERATE_REPORT_ACTION = "GenerateReport";
//	private ApplicationPresentationModel controller;
//	private InventoryAccounting inventoryAccounting;
	private final ContentModel contentModel = new ContentModel();
	private final String searchKey = "";
	private final Map<String, Command> actionCommands = new HashMap<>();

    public SearchPresentationModel() {
		logger.debug("Execute constructor.");
	}

    public String getSearchKey() {
		return searchKey;
	}

    public Vector<ActualInventoryVO> getInventory() {
		return contentModel.getInventory();
	}

    public String[] getTableColumnNames() {
		return ContentModel.columnNames;
	}

    public JInternalFrame getInternalFrame() {
		return view;
	}

	@Override
	public void initialize() {
    	view.initialize(contentModel);
    	desktopPresentationModel.addInternalFrame(view.getInternalFrame());
	}

	@Override
    public void showView() {
		view.showView();
	}

	void searchEquipment(String searchKey) {
		Vector<ActualInventoryVO> inventory = actualInventoryService.search(searchKey);

		view.setGenerateButtonEnabled(inventory.size() > 0);
		contentModel.setInventory(inventory);
	}

    public void generateReport() {
		Command command = actionCommands.get(GENERATE_REPORT_ACTION);
		if (command == null) {
			logger.warn("Action command for " + GENERATE_REPORT_ACTION);
			return;
		}

		Vector<String> header = new Vector<>();

		for (String label : ContentModel.columnNames) {
			header.addElement(label);
		}

		String reportName = "Materielsökning (" + searchKey + ")";

		command.setParameter("reportNameString", reportName);
		command.setParameter("headerVector", header);
		command.setParameter("inventoryVector", contentModel.getInventory());

		command.execute();
	}
}
