package se.melsom.warehouse.presentation.common.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.enumeration.ApplicationCategory;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.model.ItemMasterFile;
import se.melsom.warehouse.model.entity.Item;
import se.melsom.warehouse.model.entity.ItemApplication;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.presentation.common.select.SelectItemController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowSettings;

public class EditItemApplicationController extends ViewController {
	private static Logger logger = Logger.getLogger(EditItemApplicationController.class);
	
	private static final String SELECT_ITEM_ACTION_COMMAND = "ItemAction";
	private static final String QUANTITY_ACTION_COMMAND = "QAction";
	private static final String CATEGORY_ACTION_COMMAND = "CAction";
	private static final String SAVE_ACTION_COMMAND = "SaveAction";
	private static final String CANCEL_ACTION_COMMAND = "CancelAction";
	
	private InventoryAccounting inventoryAccounting;
	private JFrame parent;
	private ItemMasterFile itemMasterFile;
	private EditItemApplicationView view;
	private ItemApplication theInventory;
	private boolean isInitializingSelectors = false;

	public EditItemApplicationController(InventoryAccounting inventoryAccounting, JFrame parent) {
		this.inventoryAccounting = inventoryAccounting;
		this.parent = parent;
		this.itemMasterFile = inventoryAccounting.getItemMasterFile();
		
		WindowSettings settings = PersistentSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowSettings(getWindowName(), 201, 193, 425, 227, false);
			
			PersistentSettings.singleton().addWindowSettings(settings);
		}
		
		view = new EditItemApplicationView(parent);
		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		view.addComponentListener(this);
		
		view.setItemSelectorListener(SELECT_ITEM_ACTION_COMMAND, this);
		view.setQuantityFieldListener(QUANTITY_ACTION_COMMAND, this, this);
		view.setCategoryFieldListener(CATEGORY_ACTION_COMMAND, this, this);

		view.setCancelButtonListener(CANCEL_ACTION_COMMAND, this);
		view.setSaveButtonListener(SAVE_ACTION_COMMAND, this);
		view.setSaveButtonEnabled(false);
		
		isInitializingSelectors = true;
		Vector<String> categories = new Vector<>();
		for (ApplicationCategory category : ApplicationCategory.values()) {
			categories.addElement(category.getName());
		}
		view.setCategoryItems(categories);
		isInitializingSelectors = false;
	}
	
	public ItemApplication editApplication(ItemApplication anInventory) {
		theInventory = anInventory;
		
		if (theInventory.getItem() != null) {
			view.setItemNumber(theInventory.getItem().getNumber());
			view.setItemName(theInventory.getItem().getName());
		}
		
		view.setQuantity(theInventory.getQuantity());
		view.setSelectedCategory(theInventory.getCategory());
		
		view.setVisible(true);
		
		return theInventory;
	}
	
	private void updateInventory(ItemApplication anInventory) {
		logger.trace("Update inventory=" + anInventory);
		anInventory.setCategory(view.getSelectedCategory());
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

		case QUANTITY_ACTION_COMMAND:
		case CATEGORY_ACTION_COMMAND:
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
