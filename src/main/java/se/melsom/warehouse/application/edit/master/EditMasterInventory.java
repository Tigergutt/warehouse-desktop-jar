package se.melsom.warehouse.application.edit.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.melsom.warehouse.application.AbstractPresentationModel;
import se.melsom.warehouse.application.edit.actual.EditActualInventoryView;
import se.melsom.warehouse.data.vo.NominalInventoryVO;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.model.ItemMasterFile;
import se.melsom.warehouse.model.LocationMasterFile;
import se.melsom.warehouse.model.entity.StockLocation;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowBean;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.util.Set;
import java.util.TreeSet;

public class EditMasterInventory extends AbstractPresentationModel {
	private static final Logger logger = LoggerFactory.getLogger(EditMasterInventory.class);
	
	private static final String SELECT_ITEM_ACTION_COMMAND = "ItemAction";
	private static final String QUANTITY_ACTION_COMMAND = "QAction";
	private static final String IDENTITY_ACTION_COMMAND = "IAction";
	private static final String SOURCE_ACTION_COMMAND = "SAction";
	private static final String SAVE_ACTION_COMMAND = "SaveAction";
	private static final String CANCEL_ACTION_COMMAND = "CancelAction";
	
	private final InventoryAccounting inventoryAccounting;
	private final JFrame parent;
	private final ItemMasterFile itemMasterFile;
	private final LocationMasterFile locationMasterFile;
	private final EditMasterInventoryView view;
	private NominalInventoryVO currentInstance;
	private final boolean isInitializingSelectors = false;

	@Autowired
	private PersistentSettings persistentSettings;

    public EditMasterInventory(InventoryAccounting inventoryAccounting, JFrame parent) {
		this.inventoryAccounting = inventoryAccounting;
		this.parent = parent;
		this.itemMasterFile = inventoryAccounting.getItemMasterFile();
		this.locationMasterFile = inventoryAccounting.getLocationMasterFile();
		
		WindowBean settings = persistentSettings.getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowBean(201, 193, 425, 227, false);

			persistentSettings.addWindowSettings(getWindowName(), settings);
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

	@Override
	public void showView() {
	}

	@Override
	public void initialize() {
	}

	public NominalInventoryVO editInstance(NominalInventoryVO instance) {
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
	
	private void updateInstance(NominalInventoryVO instance) {
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
//			SelectItemController selector = new SelectItemController(inventoryAccounting, parent);
//			Item selectedItem = selector.selectItem(itemMasterFile.getItems());
//
//			logger.trace("Selected item=" + selectedItem);
//
//			if (selectedItem != null) {
//				logger.debug("Selected item=" + selectedItem);
//				currentInstance.setItem(selectedItem);
//				view.setItemNumber(currentInstance.getItem().getNumber());
//				view.setItemName(currentInstance.getItem().getName());
//			}
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

		view.setSaveButtonEnabled(currentInstance != null && currentInstance.isValid());
	}

	@Override
	public void focusLost(FocusEvent e) {
		updateInstance(currentInstance);

		view.setSaveButtonEnabled(currentInstance.isValid());
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
