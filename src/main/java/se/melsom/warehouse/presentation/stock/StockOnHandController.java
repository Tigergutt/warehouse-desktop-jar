package se.melsom.warehouse.presentation.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.data.service.InventoryService;
import se.melsom.warehouse.data.vo.StockOnHandVO;
import se.melsom.warehouse.event.ModelEvent;
import se.melsom.warehouse.event.ModelEventListener;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowBean;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Lagersaldo (Stock on hand) består av en sammanställning över aktuellt lagersaldo.
 * I saldot ingår visning av faktiskt inventerat antal artiklar tillsammans med nominellt antal artiklar.
 * På så vis kan vi få en översikt över eventuella brister/behov.
 *
 * Denna klass (Controller) hanterar kopplingen mellan visning/presentation (View) och datamodell.
 * Datat presenteras i en tabell (StockOnHandTableModel) saldot (antal artiklar) kan ha tre olika förger.
 * Röd för brist/behov, svart för balans (mot nominellt antal) och blå för övertal (fler än nominellt antal).
 *
 * StockOnHandController använder InventoryService för hämtning av saldouppgifterna.
 * Datat kan inte ändras, men det kan filtreras utifrån brist, balans och övertal i syfte att ge bättre
 * översikt utifrån behov, som till exempel enbart visa de artiklar där det finns brist/behov/undertal.
 */
@Deprecated
public class StockOnHandController extends ViewController implements ModelEventListener, ActionListener {
	private static final Logger logger = LoggerFactory.getLogger(StockOnHandController.class);
	private static final String SHORTFALL_ACTION_COMMAND = "SAC";
	private static final String BALANCES_ACTION_COMMAND = "BAC";
	private static final String OVERPLUS_ACTION_COMMAND = "OAC";
	private ApplicationPresentationModel controller;
	private final Map<String, Command> actionCommands = new HashMap<>();
	private StockOnHandView view;
	private StockOnHandTableModel tableModel;

	@Autowired
	private PersistentSettings persistentSettings;

	@Autowired
	InventoryService inventoryService;

	public StockOnHandController() {}

    public void initialize(ApplicationPresentationModel controller) {
		logger.debug("Initializing StockOnHandController.");
		this.controller = controller;

		tableModel = new StockOnHandTableModel();
		view = new StockOnHandView(this, tableModel);
		view.setCellRenderer(4, new QuantityCellRenderer(tableModel));
		view.setFilterShortfallAction(SHORTFALL_ACTION_COMMAND, this);
		view.setFilterBalancesAction(BALANCES_ACTION_COMMAND, this);
		view.setFilterOverplusAction(OVERPLUS_ACTION_COMMAND, this);
		view.addComponentListener(this);
	}

    public JInternalFrame getInternalFrame() {
		return view;
	}

	@Override
	public JComponent getView() {
		return view;
	}

    public void showView() {
		logger.debug("showView()");
		WindowBean settings = persistentSettings.getWindowSettings(getWindowName());

		if (settings == null) {
			settings = new WindowBean(500, 10, 300, 400, true);

			persistentSettings.addWindowSettings(getWindowName(), settings);
		}
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

			logger.debug("Bounds=" + settings);
			view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
			view.setVisible(settings.isVisible());
			view.setVisible(true);
		}
	}

    public void compileStockOnHand(Vector<StockOnHandVO> stockOnHand) {
		String title = String.format("Lagersaldo : antal artikelrader %d.", stockOnHand.size());
		view.setTitle(title);
		
		tableModel.setStockOnHand(stockOnHand);
	}

	@Override
	public void handleEvent(ModelEvent event) {
		logger.debug("Received model event [{}].", event);
		switch (event.getType()) {
		case STOCK_LOCATIONS_RELOADED:
		case STOCK_LOCATIONS_MODIFIED:
		case INVENTORY_UPDATED:
			compileStockOnHand(inventoryService.getStockOnHand());
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
		logger.trace("Component resized [{}].", event);
		if (event.getSource() instanceof JInternalFrame == false) {
			return;
		}

		if (view != null && view.isVisible()) {
			JInternalFrame frame = (JInternalFrame) event.getSource();
			logger.trace("[{}] width={}, height={}.", getWindowName(), frame.getWidth(), frame.getHeight());
			persistentSettings.setWindowDimension(getWindowName(), frame.getWidth(), frame.getHeight());
		}
	}

	@Override
	public void componentMoved(ComponentEvent event) {
		logger.trace("Component moved [{}].", event);
		if (event.getSource() instanceof JInternalFrame == false) {
			return;
		}

		if (view != null && view.isVisible()) {
			JInternalFrame frame = (JInternalFrame) event.getSource();
			logger.trace("[{}] x={}, y={}.", getWindowName(), frame.getX(), frame.getY());
			persistentSettings.setWindowLocation(getWindowName(), frame.getX(), frame.getY());
		}
	}
	
	@Override
	public void componentShown(ComponentEvent e) {
		persistentSettings.setWindowVisible(getWindowName(), true);
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		persistentSettings.setWindowVisible(getWindowName(), false);
	}

    String getWindowName() {
		return StockOnHandView.class.getSimpleName();
	}

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
			logger.warn("Unknown action event={}.", e);
			break;
		}
	}

}
