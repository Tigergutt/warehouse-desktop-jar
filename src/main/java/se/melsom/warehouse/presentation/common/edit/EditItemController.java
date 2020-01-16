package se.melsom.warehouse.presentation.common.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.model.ItemMasterFile;
import se.melsom.warehouse.model.Packaging;
import se.melsom.warehouse.model.entity.Item;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.presentation.common.MessageView;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowSettings;

public class EditItemController extends ViewController {
	private static Logger logger = Logger.getLogger(EditItemController.class);
	
	private static final String ITEM_NUMBER_ACTION_COMMAND = "ItemNumberAction";
	private static final String ITEM_NAME_ACTION_COMMAND = "ItemNameAction";
	private static final String ITEM_PACKAGING_ACTION_COMMAND = "ItemPAction";
	
	private static final String SAVE_ACTION_COMMAND = "SaveAction";
	private static final String CANCEL_ACTION_COMMAND = "CancelAction";
	
	private EditItemView view;
	private Item currentItem;
	private ItemMasterFile itemMasterFile;
	private JFrame parentView;
	private boolean isInitializingSelectors = false;

	public EditItemController(InventoryAccounting inventoryAccounting, JFrame parent) {
		this.itemMasterFile = inventoryAccounting.getItemMasterFile();
		this.parentView = parent;
		
		WindowSettings settings = PersistentSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowSettings(getWindowName(), 201, 193, 425, 227, false);
			
			PersistentSettings.singleton().addWindowSettings(settings);
		}
		
		view = new EditItemView(parent);
		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		view.addComponentListener(this);
		view.setItemNumberAction(ITEM_NUMBER_ACTION_COMMAND, this, this);
		view.setItemNameAction(ITEM_NAME_ACTION_COMMAND, this, this);
		view.setPackagingSelectorAction(ITEM_PACKAGING_ACTION_COMMAND, this);
		view.setCancelButtonListener(CANCEL_ACTION_COMMAND, this);
		view.setSaveButtonListener(SAVE_ACTION_COMMAND, this);
		view.setSaveButtonEnabled(false);
		
		isInitializingSelectors = true;
		view.setPackagingSelectorItems(inventoryAccounting.getPackagings());
		isInitializingSelectors = false;
	}
	
	public Item editItem(Item anItem) {
		currentItem = anItem;
		
		logger.trace("Edit item=" + anItem);
		
		isInitializingSelectors = true;
		view.setItemNumber(anItem.getNumber());
		view.setItemName(anItem.getName());
		view.setPackaging(anItem.getPackaging().length() == 0 ? Packaging.PIECE.getName() : anItem.getPackaging());
		isInitializingSelectors = false;
		
		view.setVisible(true);
		
		return currentItem;
	}

	private void updateItemNumber(String itemNumber, Item item) {
		logger.trace("Update item=" + item);
		Item otherItem = itemMasterFile.getItem(itemNumber);
		boolean canSetItemnumber = true;
		
		if (otherItem != null) {
			if (otherItem.getNumber().equals(view.getItemNumber())) {
				if (otherItem.getId() != item.getId()) {
					logger.warn("Duplicate item number=" + itemNumber);
					canSetItemnumber = false;
				}
			}			
		}
		
		if (canSetItemnumber) {
			item.setNumber(view.getItemNumber());
		} else {
			MessageView messageView = new MessageView("Artikel", "Artikelnummer '" + itemNumber + "' är redan använt.");

			messageView.show(parentView);
			item.setNumber("");
			view.setFocusOnItemNumber();
		}
		logger.trace("Updated item=" + item);
	}

	private void updateItemName(String itemName, Item item) {
		logger.trace("Update item=" + item);
		item.setName(itemName);
		logger.trace("Updated item=" + item);
	}

	private void updateItemPackaging(String packaging, Item item) {
		logger.trace("Update item=" + item);
		item.setPackaging(packaging);
		logger.trace("Updated item=" + item);
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (isInitializingSelectors) {
			logger.trace("Ignoring event=" + actionEvent);
			return;
		}

		switch (actionEvent.getActionCommand()) {
		case ITEM_NUMBER_ACTION_COMMAND:
			updateItemNumber(view.getItemNumber(), currentItem);
			break;
			
		case ITEM_NAME_ACTION_COMMAND:
			updateItemName(view.getItemName(), currentItem);
			break;
			
		case ITEM_PACKAGING_ACTION_COMMAND:
			updateItemPackaging(view.getPackaging(), currentItem);
			break;
			
		case SAVE_ACTION_COMMAND:
			view.setVisible(false);
			break;
			
		case CANCEL_ACTION_COMMAND:
			currentItem = null;
			view.setVisible(false);
  			break;
		
		default:
			logger.warn("Not implemented action=" + actionEvent);
			break;
		}
		
		if (currentItem != null && currentItem.isValid()) {
			view.setSaveButtonEnabled(true);
		} else {
			view.setSaveButtonEnabled(false);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() instanceof JTextField) {
			JTextField field = (JTextField) e.getSource();
			
			
			
			switch (field.getName()) {
			case ITEM_NUMBER_ACTION_COMMAND:
				updateItemNumber(view.getItemNumber(), currentItem);
				break;
				
			case ITEM_NAME_ACTION_COMMAND:
				updateItemName(view.getItemName(), currentItem);
				break;
				
			default:	
				break;
			}
		}
		
		if (currentItem.isValid()) {
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
