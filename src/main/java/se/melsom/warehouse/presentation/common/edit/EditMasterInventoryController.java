package se.melsom.warehouse.presentation.common.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.model.ItemMasterFile;
import se.melsom.warehouse.model.LocationMasterFile;
import se.melsom.warehouse.model.entity.Item;
import se.melsom.warehouse.model.entity.StockLocation;
import se.melsom.warehouse.model.entity.inventory.MasterInventory;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.presentation.common.select.SelectItemController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowSettings;

public class EditMasterInventoryController extends ViewController {
	private static Logger logger = Logger.getLogger(EditMasterInventoryController.class);
	
	private static final String SELECT_ITEM_ACTION_COMMAND = "ItemAction";
	private static final String QUANTITY_ACTION_COMMAND = "QAction";
	private static final String IDENTITY_ACTION_COMMAND = "IAction";
	private static final String SOURCE_ACTION_COMMAND = "SAction";
	private static final String SAVE_ACTION_COMMAND = "SaveAction";
	private static final String CANCEL_ACTION_COMMAND = "CancelAction";
	
	private InventoryAccounting inventoryAccounting;
	private JFrame parent;
	private ItemMasterFile itemMasterFile;
	private LocationMasterFile locationMasterFile;
	private EditMasterInventoryView view;
	private MasterInventory currentInstance;
	private boolean isInitializingSelectors = false;

	public EditMasterInventoryController(InventoryAccounting inventoryAccounting, JFrame parent) {
		this.inventoryAccounting = inventoryAccounting;
		this.parent = parent;
		this.itemMasterFile = inventoryAccounting.getItemMasterFile();
		this.locationMasterFile = inventoryAccounting.getLocationMasterFile();
		
		WindowSettings settings = PersistentSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowSettings(getWindowName(), 201, 193, 425, 227, false);
			
			PersistentSettings.singleton().addWindowSettings(settings);
		}
		
		view = new EditMasterInventoryView(parent);
		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		view.addComponentListener(this);
		
		view.setItemSelectorListener(SELECT_ITEM_ACTION_COMMAND, this);

		view.setIdentityFieldListener(IDENTITY_ACTION_COMMAND, this, this);

		view.setCancelButtonListener(CANCEL_ACTION_COMMAND, this);
		view.setSaveButtonListener(SAVE_ACTION_COMMAND, this);
		view.setSaveButtonEnabled(false);
		
		Set<String> locationSections = new TreeSet<>();
		
		for (StockLocation location : locationMasterFile.getLocations()) {
			locationSections.add(location.getSection());
		}
	}
	
	public MasterInventory editInstance(MasterInventory instance) {
		currentInstance = instance;
		
		if (currentInstance.getItem() != null) {
			view.setItemNumber(currentInstance.getItem().getNumber());
			view.setItemName(currentInstance.getItem().getName());
		}
		
		view.setQuantity(currentInstance.getQuantity());
		view.setIdentity(currentInstance.getIdentity());
		view.setSource(currentInstance.getSource());
		
		view.setVisible(true);
		
		return currentInstance;
	}
	
	private void updateInstance(MasterInventory instance) {
		logger.trace("Update instance=" + instance);
		instance.setQuantity(view.getQuantity());
		instance.setIdentity(view.getIdentity());
		instance.setSource(view.getSource());

		logger.trace("Updated instance=" + instance);
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (isInitializingSelectors) {
			logger.trace("Ignoring event=" + actionEvent);
			return;
		}

		switch (actionEvent.getActionCommand()) {
		
		case SELECT_ITEM_ACTION_COMMAND:
			SelectItemController selector = new SelectItemController(inventoryAccounting, parent);
			Item selectedItem = selector.selectItem(itemMasterFile.getItems());

			logger.trace("Selected item=" + selectedItem);
			
			if (selectedItem != null) {
				logger.debug("Selected item=" + selectedItem);
				currentInstance.setItem(selectedItem);
				view.setItemNumber(currentInstance.getItem().getNumber());
				view.setItemName(currentInstance.getItem().getName());
			}
			break;

		case QUANTITY_ACTION_COMMAND:
		case IDENTITY_ACTION_COMMAND:
		case SOURCE_ACTION_COMMAND:
			updateInstance(currentInstance);
			break;
			
		case SAVE_ACTION_COMMAND:
			view.setVisible(false);
			break;
			
		case CANCEL_ACTION_COMMAND:
			currentInstance = null;
			view.setVisible(false);
  			break;
		
		default:
			logger.warn("Not implemented action=" + actionEvent);
			break;
		}
		
		if (currentInstance != null && currentInstance.isValid()) {
			view.setSaveButtonEnabled(true);
		} else {
			view.setSaveButtonEnabled(false);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		updateInstance(currentInstance);
		
		if (currentInstance.isValid()) {
			view.setSaveButtonEnabled(true);
		} else {
			view.setSaveButtonEnabled(false);
		}
	}

	@Override
	public JComponent getView() {
		return null;
	}

	@Override
	public void componentResized(ComponentEvent event) {
		if (event.getSource() instanceof JDialog == false) {
			return;
		}
		
		JDialog frame = (JDialog) event.getSource();
		PersistentSettings.singleton().setWindowDimension(getWindowName(), frame.getWidth(), frame.getHeight());	
	}

	@Override
	public void componentMoved(ComponentEvent event) {
		if (event.getSource() instanceof JDialog == false) {
			return;
		}
		
		JDialog frame = (JDialog) event.getSource();
		PersistentSettings.singleton().setWindowLocation(getWindowName(), frame.getX(), frame.getY());	
	}

	String getWindowName() {
		return EditActualInventoryView.class.getSimpleName();
	}
}
