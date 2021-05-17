package se.melsom.warehouse.application.common.select;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.melsom.warehouse.data.vo.ItemVO;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowBean;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class SelectItemController extends ViewController {
	private static final Logger logger = LoggerFactory.getLogger(SelectItemController.class);

    public static final String SEARCH_FIELD_ACTION_COMMAND = "ItNumAction";
    public static final String SELECTED_ITEM_ACTION_COMMAND = "ItSelAction";
    public static final String ACCEPT_ACTION_COMMAND = "AcceptAction";
    public static final String CANCEL_ACTION_COMMAND = "CancelAction";
	
	private final SelectItemTableModel tableModel;
	private final SelectItemView view;
	private ItemVO selectedItem;

	@Autowired
	private PersistentSettings persistentSettings;

    public SelectItemController(InventoryAccounting inventoryAccounting, JFrame parent) {
		WindowBean settings = persistentSettings.getWindowSettings(getWindowName());
		
		if (settings == null) {
  			settings = new WindowBean(414, 286, 355, 500, false);

			persistentSettings.addWindowSettings(getWindowName(), settings);
		}
		
		tableModel = new SelectItemTableModel();
		tableModel.addTableModelListener(this);
		
		view = new SelectItemView(tableModel, parent);
		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		view.addComponentListener(this);
		
		view.setSearchFieldListener(SEARCH_FIELD_ACTION_COMMAND, this);
		view.setItemTableListener(SELECTED_ITEM_ACTION_COMMAND, this);
		view.setCancelButtonListener(CANCEL_ACTION_COMMAND, this);
		view.setAcceptButtonListener(ACCEPT_ACTION_COMMAND, this);
		view.setAcceptButtonEnabled(false);
	}

    public ItemVO selectItem(Vector<ItemVO> items) {
		selectedItem = null;
		logger.debug("Popping the view.");
		tableModel.setItemList(items);
		view.setVisible(true);
		
		logger.debug("Dropping the view selected item=" + selectedItem);
		return selectedItem;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() instanceof JTextField) {
			JTextField field = (JTextField) e.getSource();
			String searchKey = field.getText().toUpperCase();
			logger.debug("Search key=[" + searchKey + "]");
			
			if (searchKey.length() > 1) {
				tableModel.searchFor(searchKey);
				view.setSelectedItemRowIndex(-1);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		switch (actionEvent.getActionCommand()) {
		case SEARCH_FIELD_ACTION_COMMAND:
			break;

		case SELECTED_ITEM_ACTION_COMMAND:
			if (view.getSelectedTableRowIndex() >= 0) {
				view.setAcceptButtonEnabled(true);
			} else {
				view.setAcceptButtonEnabled(true);
			}
			logger.warn("Selected item=" + selectedItem);
			break;

		case ACCEPT_ACTION_COMMAND:
			view.setVisible(false);
			break;
			
		case CANCEL_ACTION_COMMAND:
			selectedItem = null;
			view.setVisible(false);
  			break;
		
		default:
			logger.warn("Not implemented action=" + actionEvent);
			break;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int selectedRowIndex = view.getSelectedTableRowIndex();
		
		if (selectedRowIndex >= 0) {
			view.setAcceptButtonEnabled(true);
			selectedItem = tableModel.get(selectedRowIndex);
		} else {
			selectedItem = null;
			view.setAcceptButtonEnabled(false);
		}
		
		logger.trace("Selected item=" + selectedItem);
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
		return SelectItemView.class.getSimpleName();
	}
}