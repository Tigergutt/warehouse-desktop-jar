package se.melsom.warehouse.presentation.stock;

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
import se.melsom.warehouse.model.entity.inventory.StockOnHand;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowSettings;

/**
 * The type Stock on hand controller.
 */
public class StockOnHandController extends ViewController implements ModelEventListener, ActionListener {
	private static Logger logger = Logger.getLogger(StockOnHandController.class);
	private static final String SHORTFALL_ACTION_COMMAND = "SAC";
	private static final String BALANCES_ACTION_COMMAND = "BAC";
	private static final String OVERPLUS_ACTION_COMMAND = "OAC";
	private ApplicationController controller;
	private InventoryAccounting inventoryAccounting;
	private Map<String, Command> actionCommands = new HashMap<>();
	private StockOnHandView view;
	private StockOnHandTableModel tableModel;

    /**
     * Instantiates a new Stock on hand controller.
     *
     * @param controller the controller
     */
    public StockOnHandController(ApplicationController controller) {
		this.controller = controller;

		inventoryAccounting = controller.getInventoryAccounting();

		WindowSettings settings = PersistentSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowSettings(getWindowName(), 500, 10, 300, 400, false);
			
			PersistentSettings.singleton().addWindowSettings(settings);
		}
		
		tableModel = new StockOnHandTableModel();
		view = new StockOnHandView(this, tableModel);
		logger.debug("Bounds=" + settings);
		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		view.setVisible(settings.isVisible());
		view.addComponentListener(this);
		view.setCellRenderer(4, new QuantityCellRenderer(tableModel));
		view.setFilterShortfallAction(SHORTFALL_ACTION_COMMAND, this);
		view.setFilterBalancesAction(BALANCES_ACTION_COMMAND, this);
		view.setFilterOverplusAction(OVERPLUS_ACTION_COMMAND, this);

		controller.setStockOnHandViewMenuItemChecked(settings.isVisible());
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
		logger.debug("showView()");
		if (view.isVisible()) {
			if (view.isIcon()) {
				try {
					view.setIcon(false);
				} catch (PropertyVetoException e) {
					logger.error("showView()", e);
				}
			} else {
				logger.debug("showView(false)");
				view.setVisible(false);
			}
		} else {
			logger.debug("showView(true)");
			view.setVisible(true);
		}
	}

    /**
     * Compile stock on hand.
     */
    public void compileStockOnHand() {
		Vector<StockOnHand> inventory = inventoryAccounting.getStockOnHandList();
		
		view.setTitle("Lagersaldo : antal artikelrader " + inventory.size() + ".");
		
		tableModel.setStockOnHand(inventory);
	}

	@Override
	public void handleEvent(ModelEvent event) {
		logger.debug("Receved model event=" + event);
		switch (event.getType()) {
		case STOCK_LOCATIONS_RELOADED:
		case STOCK_LOCATIONS_MODIFIED:
		case INVENTORY_UPDATED:
			compileStockOnHand();
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
		PersistentSettings.singleton().setWindowVisible(getWindowName(), true);	
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		controller.setStockOnHandViewMenuItemChecked(false);
		PersistentSettings.singleton().setWindowVisible(getWindowName(), false);	
	}

    /**
     * Gets window name.
     *
     * @return the window name
     */
    String getWindowName() {
		return StockOnHandView.class.getSimpleName();
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
		case SHORTFALL_ACTION_COMMAND:
			tableModel.setFilterShortfall(view.getFilterShortfallChecked());
			break;
			
		case BALANCES_ACTION_COMMAND:
			tableModel.setFilterBalances(view.getFilterBalancesChecked());
			break;
			
		case OVERPLUS_ACTION_COMMAND:
			tableModel.setFilterOverplus(view.getFilterOverplusChecked());
			break;
			
		default:
			logger.warn("Unknown action event=" + e);
			break;
		}
	}

}
