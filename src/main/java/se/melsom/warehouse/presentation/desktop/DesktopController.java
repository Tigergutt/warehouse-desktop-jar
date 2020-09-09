package se.melsom.warehouse.presentation.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import org.apache.log4j.Logger;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.command.Command;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowSettings;

/**
 * The type Desktop controller.
 */
public class DesktopController implements ActionListener, ComponentListener {
	private static Logger logger = Logger.getLogger(DesktopController.class);

    /**
     * The constant SHOW_INVENTORY_VIEW.
     */
    public static final String SHOW_INVENTORY_VIEW = "ShowInventoryView";
    /**
     * The constant SHOW_INVENTORY_HOLDING_VIEW.
     */
    public static final String SHOW_INVENTORY_HOLDING_VIEW = "ShowInventoryHoldingView";
    /**
     * The constant SHOW_SEARCH_INVENTORY_VIEW.
     */
    public static final String SHOW_SEARCH_INVENTORY_VIEW = "ShowSearchEquipmentView";
    /**
     * The constant SHOW_STOCK_ON_HAND_VIEW.
     */
    public static final String SHOW_STOCK_ON_HAND_VIEW = "ShowStockOnHandView";
    /**
     * The constant IMPORT_INVENTORY.
     */
    public static final String IMPORT_INVENTORY = "ImportInventory";
    /**
     * The constant IMPORT_MASTER_INVENTORY.
     */
    public static final String IMPORT_MASTER_INVENTORY = "ImportItems";
    /**
     * The constant IMPORT_STOCK_LOCATIONS.
     */
    public static final String IMPORT_STOCK_LOCATIONS = "ImportLocations";
    /**
     * The constant IMPORT_ORGANIZATIONAL_UNITS.
     */
    public static final String IMPORT_ORGANIZATIONAL_UNITS = "ImportUnits";
    /**
     * The constant EXPORT_DATABASE.
     */
    public static final String EXPORT_DATABASE = "ExportDatabase";
    /**
     * The constant GENERATE_LOCATION_INVENTORY_REPORT.
     */
    public static final String GENERATE_LOCATION_INVENTORY_REPORT = "GenLocInvRep";
    /**
     * The constant GENERATE_STOCK_ON_HAND_REPORT.
     */
    public static final String GENERATE_STOCK_ON_HAND_REPORT = "GenSOHRep";
    /**
     * The constant EDIT_ITEMS.
     */
    public static final String EDIT_ITEMS = "EditItems";
    /**
     * The constant EDIT_INSTANCES.
     */
    public static final String EDIT_INSTANCES = "EditInstances";
    /**
     * The constant EDIT_APPLICATIONS.
     */
    public static final String EDIT_APPLICATIONS = "EditApplications";
    /**
     * The constant SHOW_LOGGER_VIEW.
     */
    public static final String SHOW_LOGGER_VIEW = "ShowLogWindow";

	private DesktopView desktopView;
	private Map<String, Command> actionCommands = new HashMap<>();
	private ApplicationController applicationController;

    /**
     * Create view.
     *
     * @param applicationController the application controller
     */
    public void createView(ApplicationController applicationController) {
		this.applicationController = applicationController;
		
		WindowSettings settings = PersistentSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings == null) {
			logger.debug("Using default settings");
			settings = new WindowSettings(getWindowName(), 100, 100, 1024, 520, true);
			
			PersistentSettings.singleton().addWindowSettings(settings);
		}
		
		desktopView = new DesktopView(this);	
		desktopView.setShowInventoryViewAction(SHOW_INVENTORY_VIEW);
		desktopView.setShowInventoryHoldingViewAction(SHOW_INVENTORY_HOLDING_VIEW);
		desktopView.setShowSearchViewAction(SHOW_SEARCH_INVENTORY_VIEW);
		desktopView.setShowStockOnHandViewAction(SHOW_STOCK_ON_HAND_VIEW);
		desktopView.setImportMasterInventoryAction(IMPORT_MASTER_INVENTORY);
		desktopView.setImportInventoryAction(IMPORT_INVENTORY);
		desktopView.setImportStockLocationsAction(IMPORT_STOCK_LOCATIONS);
		desktopView.setImportOrganizationalUtitsAction(IMPORT_ORGANIZATIONAL_UNITS);
		desktopView.setExportDatabaseAction(EXPORT_DATABASE);
		desktopView.setGenerateStockLocationInventorAction(GENERATE_LOCATION_INVENTORY_REPORT);
		desktopView.setGenerateStockOnHandAction(GENERATE_STOCK_ON_HAND_REPORT);
		desktopView.setEditItemsAction(EDIT_ITEMS);
		desktopView.setEditInstancesAction(EDIT_INSTANCES);
		desktopView.setEditApplicationsAction(EDIT_APPLICATIONS);
		desktopView.setShowLogViewAction(SHOW_LOGGER_VIEW);
		logger.debug("Setting bounds=" + settings);
		desktopView.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		desktopView.setVisible(true);
		desktopView.addComponentListener(this);
	}

    /**
     * Add internal frame.
     *
     * @param internalFrame the internal frame
     */
    public void addInternalFrame(JInternalFrame internalFrame) {
		desktopView.getDesktop().add(internalFrame);
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

    /**
     * Quit application.
     */
    public void quitApplication() {
		applicationController.applicationExit();
	}

    /**
     * Sets show inventory view menu item checked.
     *
     * @param isChecked the is checked
     */
    public void setShowInventoryViewMenuItemChecked(boolean isChecked) {
		logger.trace("ShowInventoryViewMenuItem isChecked=" + isChecked);
		desktopView.setShowInventoryViewChecked(isChecked);
	}

    /**
     * Sets show inventory holding view menu item checked.
     *
     * @param isChecked the is checked
     */
    public void setShowInventoryHoldingViewMenuItemChecked(boolean isChecked) {
		logger.trace("ShowInventoryHoldingViewMenuItem isChecked=" + isChecked);
		desktopView.setShowInventoryHoldingViewChecked(isChecked);
	}

    /**
     * Sets show search view menu item checked.
     *
     * @param isChecked the is checked
     */
    public void setShowSearchViewMenuItemChecked(boolean isChecked) {
		logger.trace("ShowSearchViewMenuItem isChecked=" + isChecked);
		desktopView.setShowSearchViewMenuItemChecked(isChecked);
	}

    /**
     * Sets show stock on hand view menu item checked.
     *
     * @param isChecked the is checked
     */
    public void setShowStockOnHandViewMenuItemChecked(boolean isChecked) {
		logger.trace("ShowStockOnHandViewMenuItem isChecked=" + isChecked);
		desktopView.setShowStockOnHandViewChecked(isChecked);
	}

    /**
     * Sets edit products menu item checked.
     *
     * @param isChecked the is checked
     */
    public void setEditProductsMenuItemChecked(boolean isChecked) {
		logger.trace("EditProductsMenuItem isChecked=" + isChecked);
		desktopView.setEditItemsMenuItemChecked(isChecked);
	}

    /**
     * Sets show log view menu item checked.
     *
     * @param isChecked the is checked
     */
    public void setShowLogViewMenuItemChecked(boolean isChecked) {
		logger.trace("ShowLogViewMenuItem isChecked=" + isChecked);
		desktopView.setShowLogViewChecked(isChecked);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		logger.trace("Received action=" + action + " from " + e.getSource());
		Command command = actionCommands.get(action);
		
		if (command == null) {
			logger.warn("Unhandled action command " + action);
			return;
		}
		
		logger.trace("Executing command=" + command);
		
		command.execute();
	}

	@Override
	public void componentResized(ComponentEvent event) {
		if (event.getSource() instanceof JFrame == false) {
			return;
		}
		
		JFrame frame = (JFrame) event.getSource();

		PersistentSettings.singleton().setWindowDimension(getWindowName(), frame.getWidth(), frame.getHeight());	
	}

	@Override
	public void componentMoved(ComponentEvent event) {
		if (event.getSource() instanceof JFrame == false) {
			return;
		}
		
		JFrame frame = (JFrame) event.getSource();

		PersistentSettings.singleton().setWindowLocation(getWindowName(), frame.getX(), frame.getY());	
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

    /**
     * Gets window name.
     *
     * @return the window name
     */
    String getWindowName() {
		return DesktopView.class.getSimpleName();
	}
}
