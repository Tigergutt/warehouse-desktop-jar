package se.melsom.warehouse.application.edit.actual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowBean;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.util.Set;
import java.util.TreeSet;

public class EditActualInventory extends ViewController {
	private static final Logger logger = LoggerFactory.getLogger(EditActualInventory.class);
	
	private static final String SELECT_ITEM_ACTION_COMMAND = "ItemAction";
	private static final String LOCATION_SECTION_ACTION_COMMAND = "LocSecAction";
	private static final String LOCATION_SLOT_ACTION_COMMAND = "LocSlAction";
	private static final String QUANTITY_ACTION_COMMAND = "QAction";
	private static final String IDENTITY_ACTION_COMMAND = "IAction";
	private static final String SAVE_ACTION_COMMAND = "SaveAction";
	private static final String CANCEL_ACTION_COMMAND = "CancelAction";
	
	private final EditActualInventoryView view;
	private ActualInventoryVO theInventory;
	private boolean isInitializingSelectors = false;

	@Autowired
	private PersistentSettings persistentSettings;

	public EditActualInventory() {
		WindowBean settings = persistentSettings.getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowBean(201, 193, 425, 227, false);

			persistentSettings.addWindowSettings(getWindowName(), settings);
		}
		
		view = new EditActualInventoryView(null /*parent*/);
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
		
//		for (StockLocation location : locationMasterFile.getLocations()) {
//			locationSections.add(location.getSection());
//		}

		isInitializingSelectors = true;
		view.setSectionSelectorItems(locationSections);	
		isInitializingSelectors = false;
	}

    public ActualInventoryVO editInventory(ActualInventoryVO anInventory) {
		theInventory = anInventory;
		
		if (theInventory.getItem() != null) {
			view.setItemNumber(theInventory.getItem().getNumber());
			view.setItemName(theInventory.getItem().getName());
		}
		
		if (theInventory.getStockLocation() != null) {
			view.setSelectedLocationSection(theInventory.getStockLocation().getSection());
			view.setSelectedLocationSlot(theInventory.getStockLocation().getSlot());
		}
		
		view.setQuantity(theInventory.getQuantity());
		view.setIdentity(theInventory.getIdentity());
		
		view.setVisible(true);
		
		return theInventory;
	}
	
	private void handleSelectedSection(String section) {
		Set<String> locationSlots = new TreeSet<>();
		
//		for (StockLocation location : locationMasterFile.getLocations()) {
//			if (location.getSection().equals(section)) {
//				locationSlots.add(location.getSlot());
//			}
//		}
		
		isInitializingSelectors = true;
		view.setSlotSelectorItems(locationSlots);
		isInitializingSelectors = false;
	}
	
	private void handleSelectedSlot(ActualInventoryVO anInventory, String section, String slot) {
//		StockLocationVO location = locationMasterFile.getLocation(section, slot);
//
//		if (location != null) {
//			anInventory.setStockLocation(location);
//		}
	}
	
	private void updateInventory(ActualInventoryVO anInventory) {
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
//			SelectItemController selector = new SelectItemController(inventoryAccounting, parent);
//			ItemVO selectedItem = selector.selectItem(itemMasterFile.getItems());
//
//			logger.trace("Selected item=" + selectedItem);
//
//			if (selectedItem != null) {
//				logger.debug("Selected item=" + selectedItem);
//				theInventory.setItem(selectedItem);
//				view.setItemNumber(theInventory.getItem().getNumber());
//				view.setItemName(theInventory.getItem().getName());
//			}
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

		view.setSaveButtonEnabled(theInventory != null && theInventory.isValid());
	}

	@Override
	public void focusLost(FocusEvent e) {
		updateInventory(theInventory);

		view.setSaveButtonEnabled(theInventory.isValid());
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
		persistentSettings.setWindowDimension(getWindowName(), frame.getWidth(), frame.getHeight());
	}

	@Override
	public void componentMoved(ComponentEvent event) {
		if (event.getSource() instanceof JDialog == false) {
			return;
		}
		
		JDialog frame = (JDialog) event.getSource();
		persistentSettings.setWindowLocation(getWindowName(), frame.getX(), frame.getY());
	}

    String getWindowName() {
		return EditActualInventoryView.class.getSimpleName();
	}
}
