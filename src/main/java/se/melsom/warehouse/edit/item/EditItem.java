package se.melsom.warehouse.edit.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.common.view.MessageView;
import se.melsom.warehouse.data.service.ItemService;
import se.melsom.warehouse.data.vo.ItemVO;
import se.melsom.warehouse.model.enumeration.Packaging;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;

public class EditItem  {
	private static final Logger logger = LoggerFactory.getLogger(EditItem.class);

	private static final String ITEM_NUMBER_ACTION_COMMAND = "ItemNumberAction";
	private static final String ITEM_NAME_ACTION_COMMAND = "ItemNameAction";
	private static final String ITEM_PACKAGING_ACTION_COMMAND = "ItemPAction";
	
	private static final String SAVE_ACTION_COMMAND = "SaveAction";
	private static final String CANCEL_ACTION_COMMAND = "CancelAction";

	private final ItemService itemService;
	private final JFrame parentFrame;
	private final EditItemView view;
	private ItemVO currentItem;
	private boolean isInitializingSelectors = false;

	public EditItem(ItemService itemService, JFrame parentFrame) {
		this.itemService = itemService;
		this.parentFrame = parentFrame;
		this.view = new EditItemView(parentFrame);
	}

	public void editItem(ItemVO anItem, EditItemListener editItemListener) {
		currentItem = anItem;
		
		logger.debug("Edit item=" + anItem);
		
		isInitializingSelectors = true;
		view.setPackagingSelectorItems(itemService.getPackaging());
		view.setItemNumber(anItem.getNumber());
		view.setItemName(anItem.getName());
		view.setPackaging(anItem.getPackaging().length() == 0 ? Packaging.PIECE.getName() : anItem.getPackaging());
		isInitializingSelectors = false;
		
		view.setVisible(true);
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
}
