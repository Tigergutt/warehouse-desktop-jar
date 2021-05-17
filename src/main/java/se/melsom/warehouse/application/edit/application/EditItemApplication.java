package se.melsom.warehouse.application.edit.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.AbstractPresentationModel;
import se.melsom.warehouse.application.edit.actual.EditActualInventoryView;
import se.melsom.warehouse.model.entity.ItemApplication;
import se.melsom.warehouse.model.enumeration.ApplicationCategory;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowBean;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.util.Vector;

@Component
public class EditItemApplication extends AbstractPresentationModel {
	private static final Logger logger = LoggerFactory.getLogger(EditItemApplication.class);
	
	private static final String SELECT_ITEM_ACTION_COMMAND = "ItemAction";
	private static final String QUANTITY_ACTION_COMMAND = "QAction";
	private static final String CATEGORY_ACTION_COMMAND = "CAction";
	private static final String SAVE_ACTION_COMMAND = "SaveAction";
	private static final String CANCEL_ACTION_COMMAND = "CancelAction";
	
	private EditItemApplicationView view;
	private ItemApplication theInventory;
	private boolean isInitializingSelectors = false;

	@Autowired private PersistentSettings persistentSettings;

	public EditItemApplication() {
	}

	@Override
	public void showView() {
	}

	@Override
	public void initialize() {
		WindowBean settings = persistentSettings.getWindowSettings(getWindowName());

		if (settings == null) {
			settings = new WindowBean(201, 193, 425, 227, false);

			persistentSettings.addWindowSettings(getWindowName(), settings);
		}

		view = new EditItemApplicationView(null /* parent */);
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

		view.setSaveButtonEnabled(theInventory != null && theInventory.isValid());
	}

	@Override
	public void focusLost(FocusEvent e) {
		updateInventory(theInventory);

		view.setSaveButtonEnabled(theInventory.isValid());
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
