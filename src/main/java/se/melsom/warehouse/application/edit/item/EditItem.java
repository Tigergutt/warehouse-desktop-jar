package se.melsom.warehouse.application.edit.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.AbstractPresentationModel;
import se.melsom.warehouse.application.common.MessageView;
import se.melsom.warehouse.application.edit.actual.EditActualInventoryView;
import se.melsom.warehouse.data.service.ItemService;
import se.melsom.warehouse.data.vo.ItemVO;
import se.melsom.warehouse.model.enumeration.Packaging;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowBean;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;

@Component
public class EditItem extends AbstractPresentationModel {
	private static final Logger logger = LoggerFactory.getLogger(EditItem.class);
	
	private static final String ITEM_NUMBER_ACTION_COMMAND = "ItemNumberAction";
	private static final String ITEM_NAME_ACTION_COMMAND = "ItemNameAction";
	private static final String ITEM_PACKAGING_ACTION_COMMAND = "ItemPAction";
	
	private static final String SAVE_ACTION_COMMAND = "SaveAction";
	private static final String CANCEL_ACTION_COMMAND = "CancelAction";
	
	private ItemVO currentItem;
	private boolean isInitializingSelectors = false;

	@Autowired private EditItemView view;
	@Autowired private ItemService itemService;
	@Autowired private PersistentSettings persistentSettings;

	public EditItem() {}

	@Override
	public void showView() {
	}

	@Override
	public void initialize() {
		logger.debug("Initialize.");
		WindowBean settings = persistentSettings.getWindowSettings(getWindowName());

		if (settings == null) {
			settings = new WindowBean(201, 193, 425, 227, false);

			persistentSettings.addWindowSettings(getWindowName(), settings);
		}

		view.initialize(null);
//		view = new EditItemView(null /*parent*/);
//		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
//		view.addComponentListener(this);
//		view.setItemNumberAction(ITEM_NUMBER_ACTION_COMMAND, this, this);
//		view.setItemNameAction(ITEM_NAME_ACTION_COMMAND, this, this);
//		view.setPackagingSelectorAction(ITEM_PACKAGING_ACTION_COMMAND, this);
//		view.setCancelButtonListener(CANCEL_ACTION_COMMAND, this);
//		view.setSaveButtonListener(SAVE_ACTION_COMMAND, this);
//		view.setSaveButtonEnabled(false);

		isInitializingSelectors = true;
//		view.setPackagingSelectorItems(inventoryAccounting.getPackagings());
		isInitializingSelectors = false;
	}

	public ItemVO editItem(ItemVO anItem) {
		currentItem = anItem;
		
		logger.debug("Edit item=" + anItem);
		
		isInitializingSelectors = true;
		view.setItemNumber(anItem.getNumber());
		view.setItemName(anItem.getName());
		view.setPackaging(anItem.getPackaging().length() == 0 ? Packaging.PIECE.getName() : anItem.getPackaging());
		isInitializingSelectors = false;
		
		view.setVisible(true);
		
		return currentItem;
	}

	private void updateItemNumber(String itemNumber, ItemVO item) {
		logger.trace("Update item=" + item);
		ItemVO otherItem = itemService.getItem(itemNumber);
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

			messageView.show(null);
			item.setNumber("");
			view.setFocusOnItemNumber();
		}
		logger.trace("Updated item=" + item);
	}

	private void updateItemName(String itemName, ItemVO item) {
		logger.trace("Update item=" + item);
		item.setName(itemName);
		logger.trace("Updated item=" + item);
	}

	private void updateItemPackaging(String packaging, ItemVO item) {
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

		view.setSaveButtonEnabled(currentItem != null && currentItem.isValid());
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

		view.setSaveButtonEnabled(currentItem.isValid());
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
