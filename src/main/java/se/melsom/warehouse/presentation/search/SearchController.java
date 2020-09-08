package se.melsom.warehouse.presentation.search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;

import org.apache.log4j.Logger;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.command.Command;
import se.melsom.warehouse.event.ModelEvent;
import se.melsom.warehouse.event.ModelEventListener;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowSettings;

/**
 * The type Search controller.
 */
public class SearchController extends ViewController implements ModelEventListener, ActionListener {
	private static Logger logger = Logger.getLogger(SearchController.class);
    /**
     * The constant SEARCH_ACTION.
     */
    public static final String SEARCH_ACTION = "SearchAction";
    /**
     * The constant GENERATE_REPORT_ACTION.
     */
    public static final String GENERATE_REPORT_ACTION = "GenerateReport";
	private ApplicationController controller;
	private InventoryAccounting inventoryAccounting;
	private SearchView view;
	private SearchResultTableModel tableModel;
	private String searchKey = "";
	private Map<String, Command> actionCommands = new HashMap<>();

    /**
     * Instantiates a new Search controller.
     *
     * @param controller the controller
     */
    public SearchController(ApplicationController controller) {
		this.controller = controller;

		inventoryAccounting = controller.getInventoryAccounting();

		WindowSettings settings = PersistentSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings == null) {
			logger.debug("Apply default window settings.");
			settings = new WindowSettings(getWindowName(), 500, 10, 300, 400, false);
			
			PersistentSettings.singleton().addWindowSettings(settings);
		}
		
		tableModel = new SearchResultTableModel();
		view = new SearchView(this, tableModel);
		view.setSearchAction(SEARCH_ACTION);
		view.setGenerateReportAction(GENERATE_REPORT_ACTION);
		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		view.setVisible(settings.isVisible());
		view.setGenerateButtonEnabled(false);
		view.addComponentListener(this);

		controller.setSearchViewMenuItemChecked(settings.isVisible());
	}


    /**
     * Gets search key.
     *
     * @return the search key
     */
    public String getSearchKey() {
		return searchKey;
	}

    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public Vector<ActualInventory> getInventory() {
		return tableModel.getInventory();
	}

    /**
     * Get table column names string [ ].
     *
     * @return the string [ ]
     */
    public String[] getTableColumnNames() {
		return SearchResultTableModel.columnNames;
	}

    /**
     * Gets internal frame.
     *
     * @return the internal frame
     */
    public JInternalFrame getInternalFrame() {
		return view;
	}

	@Override
	public JComponent getView() {
		return view;
	}

    /**
     * Show view.
     */
    public void showView() {
		logger.trace("showView()");
		if (view.isVisible()) {
			logger.trace("View is visible.");
			if (view.isIcon()) {
				try {
					view.setIcon(false);
				} catch (PropertyVetoException e) {
					logger.error("showView()", e);
				}
			} else {
				logger.trace("Hide view.");
				view.setVisible(false);
			}
		} else {
			logger.trace("Show view.");
			view.setVisible(true);
		}
	}
	

	private void searchEquipment(String key) {
		searchKey = view.getsearchKey();
		Vector<ActualInventory> inventory = inventoryAccounting.getActualInventory(searchKey);
		
		view.setGenerateButtonEnabled(inventory.size() > 0);
		tableModel.setInventory(inventory);
	}

    /**
     * Generate report.
     */
    public void generateReport() {
	}

	@Override
	public void handleEvent(ModelEvent event) {
		logger.debug("Receved model event=" + event);
		switch (event.getType()) {
		case STOCK_LOCATIONS_RELOADED:
		case STOCK_LOCATIONS_MODIFIED:
			if (searchKey.length() == 0) {
				return;
			}
			searchEquipment(searchKey);
			break;
			
		default:
			break;
		}
	}
	
	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}


	@Override
	public void componentResized(ComponentEvent event) {
		if (event.getSource() instanceof JInternalFrame == false) {
			return;
		}
		
		JInternalFrame frame = (JInternalFrame) event.getSource();
		PersistentSettings.singleton().setWindowDimension(getWindowName(), frame.getWidth(), frame.getHeight());	
	}

	@Override
	public void componentMoved(ComponentEvent event) {
		if (event.getSource() instanceof JInternalFrame == false) {
			return;
		}
		
		JInternalFrame frame = (JInternalFrame) event.getSource();
		PersistentSettings.singleton().setWindowLocation(getWindowName(), frame.getX(), frame.getY());	
	}
	
	@Override
	public void componentShown(ComponentEvent e) {
		logger.trace("Shown event=" + e);
		PersistentSettings.singleton().setWindowVisible(getWindowName(), true);	
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		logger.trace("Hidden event=" + e);
		controller.setSearchViewMenuItemChecked(false);
		PersistentSettings.singleton().setWindowVisible(getWindowName(), false);	
	}

    /**
     * Gets window name.
     *
     * @return the window name
     */
    String getWindowName() {
		return SearchView.class.getSimpleName();
	}

    /**
     * Add action command.
     *
     * @param action  the action
     * @param command the command
     */
    public void addActionCommand(String action, Command command) {
		actionCommands.put(action, command);
	} 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case SEARCH_ACTION: {
			searchEquipment(view.getsearchKey());
			break;
		}
		
		case GENERATE_REPORT_ACTION:
			Command command = actionCommands.get(GENERATE_REPORT_ACTION);
			if (command == null) {
				logger.warn("Action command for " + GENERATE_REPORT_ACTION);
				return;
			}
			
			Vector<String> header = new Vector<>();
			
			for (String label : SearchResultTableModel.columnNames) {
				header.addElement(label);
			}
			
			String reportName = "Materielsökning (" + searchKey + ")";
			
			command.setParameter("reportNameString", reportName);
			command.setParameter("headerVector", header);
			command.setParameter("inventoryVector", tableModel.getInventory());
			
			command.execute();
			break;
		
		default:
			logger.warn("Unknown action event=" + e);
			break;
		}
	}
}
