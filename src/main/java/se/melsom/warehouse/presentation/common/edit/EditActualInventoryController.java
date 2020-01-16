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
import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.presentation.common.select.SelectItemController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowSettings;

public class EditActualInventoryController extends ViewController {
	private static Logger logger = Logger.getLogger(EditActualInventoryController.class);
	
	private static final String SELECT_ITEM_ACTION_COMMAND = "ItemAction";
	private static final String LOCATION_SECTION_ACTION_COMMAND = "LocSecAction";
	private static final String LOCATION_SLOT_ACTION_COMMAND = "LocSlAction";
	private static final String QUANTITY_ACTION_COMMAND = "QAction";
	private static final String IDENTITY_ACTION_COMMAND = "IAction";
	private static final String SAVE_ACTION_COMMAND = "SaveAction";
	private static final String CANCEL_ACTION_COMMAND = "CancelAction";
	
	private InventoryAccounting inventoryAccounting;
	private JFrame parent;
	private ItemMasterFile itemMasterFile;
	private LocationMasterFile locationMasterFile;
	private EditActualInventoryView view;
	private ActualInventory theInventory;
	private boolean isInitializingSelectors = false;

	public EditActualInventoryController(InventoryAccounting inventoryAccounting, JFrame parent) {
		this.inventoryAccounting = inventoryAccounting;
		this.parent = parent;
		this.itemMasterFile = inventoryAccounting.getItemMasterFile();
		this.locationMasterFile = inventoryAccounting.getLocationMasterFile();
		
		WindowSettings settings = PersistentSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowSettings(getWindowName(), 201, 193, 425, 227, false);
			
			PersistentSettings.singleton().addWindowSettings(settings);
		}
		
		view = new EditActualInventoryView(parent);
		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		view.addComponentListener(this);
		
		view.setItemSelectorListener(SELECT_ITEM_ACTION_COMMAND, this);
		view.setLocationSectionFieldListener(LOCATION_SECTION_ACTION_COMMAND, this, this);
		view.setLocationSlotFieldListener(LOCATION_SLOT_ACTION_COMMAND, this, this);

		view.setQuantityFieldListener(QUANTITY_ACTION_COMMAND, this, this);
		view.setIdentityFieldListener(IDENTITY_ACTION_COMMAND, this, this);

		view.setCancelButtonListener(CANCEL_ACTION_COMMAND, this);
		view.setSaveButtonListener(SAVE_ACTION_COMMAND, this);
		view.setSaveButtonEnabled(false);
		
		Set<String> locationSections = new TreeSet<>();
		
		for (StockLocation location : locationMasterFile.getLocations()) {
			locationSections.add(location.getSection());
		}

		isInitializingSelectors = true;
		view.setSectionSelectorItems(locationSections);	
		isInitializingSelectors = false;
	}
	
	public ActualInventory editInventory(ActualInventory anInventory) {
		theInventory = anInventory;
		
		if (theInventory.getItem() != null) {
			view.setItemNumber(theInventory.getItem().getNumber());
			view.setItemName(theInventory.getItem().getName());
		}
		
		if (theInventory.getLocation() != null) {
			view.setSelectedLocationSection(theInventory.getLocation().getSection());
			view.setSelectedLocationSlot(theInventory.getLocation().getSlot());
		}
		
		view.setQuantity(theInventory.getQuantity());
		view.setIdentity(theInventory.getIdentity());
		
		view.setVisible(true);
		
		return theInventory;
	}
	
	private void handleSelectedSection(String section) {
		Set<String> locationSlots = new TreeSet<>();
		
		for (StockLocation location : locationMasterFile.getLocations()) {
			if (location.getSection().equals(section)) {
				locationSlots.add(location.getSlot());
			}
		}
		
		isInitializingSelectors = true;
		view.setSlotSelectorItems(locationSlots);
		isInitializingSelectors = false;
	}
	
	private void handleSelectedSlot(ActualInventory anInventory, String section, String slot) {
		StockLocation location = locationMasterFile.getLocation(section, slot);	
		
		if (location != null) {
			anInventory.setLocation(location);
		}
	}
	
	private void updateInventory(ActualInventory anInventory) {
		logger.trace("Update inventory=" + anInventory);
		anInventory.setIdentity(view.getIdentity());
		anInventory.setQuantity(view.getQuantity());
		logger.trace("Updated inventory=" + anInventory);
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
				theInventory.setItem(selectedItem);
				view.setItemNumber(theInventory.getItem().getNumber());
				view.setItemName(theInventory.getItem().getName());
			}
			break;

		case LOCATION_SECTION_ACTION_COMMAND:
			handleSelectedSection(view.getLocationSection());
			break;
			
		case LOCATION_SLOT_ACTION_COMMAND:
			handleSelectedSlot(theInventory, view.getLocationSection(), view.getLocationSlot());
			break;
			
		case QUANTITY_ACTION_COMMAND:
		case IDENTITY_ACTION_COMMAND:
			updateInventory(theInventory);
			break;
			
		case SAVE_ACTION_COMMAND:
			view.setVisible(false);
			break;
			
		case CANCEL_ACTION_COMMAND:
			theInventory = null;
			view.setVisible(false);
  			break;
		
		default:
			logger.warn("Not implemented action=" + actionEvent);
			break;
		}
		
		if (theInventory != null && theInventory.isValid()) {
			view.setSaveButtonEnabled(true);
		} else {
			view.setSaveButtonEnabled(false);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		updateInventory(theInventory);
		
		if (theInventory.isValid()) {
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
