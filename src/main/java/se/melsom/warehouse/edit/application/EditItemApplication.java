package se.melsom.warehouse.edit.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.melsom.warehouse.data.vo.ItemApplicationVO;
import se.melsom.warehouse.model.enumeration.ApplicationCategory;
import se.melsom.warehouse.settings.PersistentSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class EditItemApplication {
	private static final Logger logger = LoggerFactory.getLogger(EditItemApplication.class);

	@Autowired private PersistentSettings persistentSettings;

	private static final String SELECT_ITEM_ACTION_COMMAND = "ItemAction";
	private static final String QUANTITY_ACTION_COMMAND = "QAction";
	private static final String CATEGORY_ACTION_COMMAND = "CAction";
	private static final String SAVE_ACTION_COMMAND = "SaveAction";
	private static final String CANCEL_ACTION_COMMAND = "CancelAction";
	
	private ItemApplicationVO theInventory;
	private boolean isInitializingSelectors = false;

	private EditItemApplicationView view;

	public EditItemApplication(JFrame parentFrame) {
		this.view = new EditItemApplicationView(parentFrame);
	}

	@Deprecated
	public void initialize() {
//		view.setItemSelectorListener(SELECT_ITEM_ACTION_COMMAND, this);
//		view.setQuantityFieldListener(QUANTITY_ACTION_COMMAND, this, this);
//		view.setCategoryFieldListener(CATEGORY_ACTION_COMMAND, this, this);
//
//		view.setCancelButtonListener(CANCEL_ACTION_COMMAND, this);
//		view.setSaveButtonListener(SAVE_ACTION_COMMAND, this);
		view.setSaveButtonEnabled(false);

		isInitializingSelectors = true;
		Vector<String> categories = new Vector<>();
		for (ApplicationCategory category : ApplicationCategory.values()) {
			categories.addElement(category.getName());
		}
		view.setCategoryItems(categories);
		isInitializingSelectors = false;
	}

	public ItemApplicationVO editApplication(ItemApplicationVO anInventory, EditItemApplicationListener listener) {
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
	
	private void updateInventory(ItemApplicationVO anInventory, EditItemApplicationListener listener) {
		logger.trace("Update inventory=" + anInventory);
		anInventory.setCategory(view.getSelectedCategory());
		anInventory.setQuantity(view.getQuantity());
		logger.trace("Updated inventory=" + anInventory);
	}

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
//				theInventory.setItem(selectedItem);
//				view.setItemNumber(theInventory.getItem().getNumber());
//				view.setItemName(theInventory.getItem().getName());
//			}
			break;

//		case QUANTITY_ACTION_COMMAND:
//		case CATEGORY_ACTION_COMMAND:
//			updateInventory(theInventory);
//			break;
			
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

//	public void focusLost(FocusEvent e) {
//		updateInventory(theInventory);
//
//		view.setSaveButtonEnabled(theInventory.isValid());
//	}
}
