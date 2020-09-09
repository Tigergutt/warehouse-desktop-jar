package se.melsom.warehouse.presentation.maintenance.items;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;

import org.apache.log4j.Logger;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.command.Command;
import se.melsom.warehouse.event.ModelEvent;
import se.melsom.warehouse.event.ModelEventListener;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.model.ItemMasterFile;
import se.melsom.warehouse.model.entity.Item;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.presentation.common.MessageView;
import se.melsom.warehouse.presentation.common.edit.EditItemController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowSettings;

/**
 * The type Item maintenance controller.
 */
public class ItemMaintenanceController extends ViewController implements ModelEventListener {
	private static Logger logger = Logger.getLogger(ItemMaintenanceController.class);
    /**
     * The constant GENERATE_REPORT_ACTION.
     */
    public static final String GENERATE_REPORT_ACTION = "GenerateReport";
    /**
     * The constant EXTENDED_EDIT_ACTION.
     */
    public static final String EXTENDED_EDIT_ACTION = "ExtendedEdit";
    /**
     * The constant SELECT_ITEM_ACTION.
     */
    public static final String SELECT_ITEM_ACTION = "SelectAction";
    /**
     * The constant EDIT_ITEM_ACTION.
     */
    public static final String EDIT_ITEM_ACTION = "EditItem";
    /**
     * The constant INSERT_ITEM_ACTION.
     */
    public static final String INSERT_ITEM_ACTION = "AddItem";
    /**
     * The constant REMOVE_ITEM_ACTION.
     */
    public static final String REMOVE_ITEM_ACTION = "DeleteItem";
	
	private ApplicationController controller;
	private InventoryAccounting inventoryAccounting;
	private ItemMasterFile itemMasterFile;
	private ItemMaintenanceTableModel tableModel;
	private ItemMaintenanceView view;
	private Map<String, Command> actionCommands = new HashMap<>();

    /**
     * Instantiates a new Item maintenance controller.
     *
     * @param controller the controller
     */
    public ItemMaintenanceController(ApplicationController controller) {
		logger.debug("Executing constructor.");
		this.controller = controller;
		this.inventoryAccounting = controller.getInventoryAccounting();
		this.itemMasterFile = inventoryAccounting.getItemMasterFile();

		WindowSettings settings = PersistentSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowSettings(getWindowName(), 154, 124, 879, 498, false);
			
			PersistentSettings.singleton().addWindowSettings(settings);
		}
		
		tableModel = new ItemMaintenanceTableModel();
		tableModel.addTableModelListener(this);

		view = new ItemMaintenanceView(this, tableModel);
		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		view.setVisible(settings.isVisible());
		view.addComponentListener(this);
		view.setExtendedEditChecked(false);
		view.setExtendedEditButtonAction(EXTENDED_EDIT_ACTION, this);
		view.setGenerateButtonEnabled(false);
		view.setSelectedItemAction(SELECT_ITEM_ACTION, this);
		view.setGenerateReportAction(GENERATE_REPORT_ACTION, this);
		view.setEditButtonEnabled(false);
		view.setEditButtonAction(EDIT_ITEM_ACTION, this);
		view.setInsertButtonEnabled(false);
		view.setInsertButtonAction(INSERT_ITEM_ACTION, this);
		view.setRemoveButtonEnabled(false);
		view.setRemoveButtonAction(REMOVE_ITEM_ACTION, this);
	}

    /**
     * Gets items.
     *
     * @return the items
     */
    public Vector<Item> getItems() {
		return tableModel.getItems();
	}

    /**
     * Sets items.
     *
     * @param collection the collection
     */
    public void setItems(Collection<Item> collection) {
		tableModel.setItems(collection);
	}

    /**
     * Gets internal frame.
     *
     * @return the internal frame
     */
    public JInternalFrame getInternalFrame() {
		return view;
	}

	@Override
	public JComponent getView() {
		return view;
	}

    /**
     * Show view.
     */
    public void showView() {
		logger.debug("showView()");
		if (view.isVisible()) {
			if (view.isIcon()) {
				try {
					view.setIcon(false);
				} catch (PropertyVetoException e) {
					logger.error("showView()", e);
				}
			}
		} else {
			view.setVisible(true);
		}
	}
	
	@Override
	public void handleEvent(ModelEvent event) {
		logger.debug("Receved model event=" + event);
		switch (event.getType()) {
		case STOCK_LOCATIONS_RELOADED:
		case STOCK_LOCATIONS_MODIFIED:
			break;
			
		default:
			break;
		}
		
		logger.debug("Ignored the event.");
	}
	
	@Override
	public void finalize() throws Throwable {
		super.finalize();
		logger.debug("Finalize.");
	}


	@Override
	public void componentResized(ComponentEvent event) {
		if (event.getSource() instanceof JInternalFrame == false) {
			return;
		}
		
		JInternalFrame frame = (JInternalFrame) event.getSource();
		PersistentSettings.singleton().setWindowDimension(getWindowName(), frame.getWidth(), frame.getHeight());	
	}

	@Override
	public void componentMoved(ComponentEvent event) {
		if (event.getSource() instanceof JInternalFrame == false) {
			return;
		}
		
		JInternalFrame frame = (JInternalFrame) event.getSource();
		PersistentSettings.singleton().setWindowLocation(getWindowName(), frame.getX(), frame.getY());	
	}
	
	@Override
	public void componentShown(ComponentEvent e) {
		setItems(itemMasterFile.getItems());

		PersistentSettings.singleton().setWindowVisible(getWindowName(), true);	
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		view = null;
		tableModel = null;
		PersistentSettings.singleton().setWindowVisible(getWindowName(), false);	
	}

    /**
     * Add action command.
     *
     * @param action  the action
     * @param command the command
     */
    public void addActionCommand(String action, Command command) {
		actionCommands.put(action, command);
	} 
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}
		
		checkEditButtons();
	}
	
	@Override
	public void tableChanged(TableModelEvent e) {
		int rowIndex = e.getFirstRow();
		int columnIndex = e.getColumn();
		
		if (rowIndex < 0 || columnIndex < 0) {
			return;
		}
		
		switch (e.getType()) {
		case TableModelEvent.UPDATE:
			Item updated = tableModel.getItems().get(rowIndex);
			itemMasterFile.updateItem(updated);
			return;
			
		case TableModelEvent.INSERT:
		case TableModelEvent.DELETE:
			return;
			
		default:
			break;
		}
		
		logger.warn("Unhandled event at row=" + e.getFirstRow() + ",column=" + e.getColumn());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		logger.trace("Received action=" + e.getActionCommand() + ",source=" + e.getSource() + ",event=" + e);
		
		switch (e.getActionCommand()) {
		case EXTENDED_EDIT_ACTION:
			checkEditButtons();
			return;
		
		case GENERATE_REPORT_ACTION:
			Command command = actionCommands.get(GENERATE_REPORT_ACTION);
			if (command == null) {
				logger.warn("Action command for " + GENERATE_REPORT_ACTION);
				return;
			}
			
			logger.warn("Generate report is not implemened.");
			return;
			
		case EDIT_ITEM_ACTION: {
			EditItemController itemEditor = new EditItemController(inventoryAccounting, controller.getDesktopView());
			
			logger.debug("Show edit dialog.");
			int selectedItemIndex = view.getSelectedItemRow();
			Item selectedItem = tableModel.getItem(selectedItemIndex);
			Item editedItem = itemEditor.editItem(selectedItem);
			
			if (editedItem != null) {
				logger.debug("Edited existing item=" + editedItem);
				if (!selectedItem.getNumber().equals(editedItem.getNumber())) {
					if (itemMasterFile.getItem(editedItem.getNumber()) != null) {
						
					}
				}
				
				int rowIndex = tableModel.updateItem(editedItem, selectedItemIndex);
				view.setSelectedItemRow(rowIndex);
				itemMasterFile.updateItem(editedItem);
			}
			return;
		}
			
		case INSERT_ITEM_ACTION: {
			EditItemController itemEditor = new EditItemController(inventoryAccounting, controller.getDesktopView());
			
			logger.debug("Show edit dialog.");
			Item newItem = new Item(itemMasterFile.getNextItemId(), "Y0000-000000", "Ny artikel", "ST");
			Item editedItem = itemEditor.editItem(newItem);
			
			if (editedItem != null) {
				logger.debug("Edited new item=" + editedItem);
				int rowIndex = tableModel.insertItem(editedItem);
				view.setSelectedItemRow(rowIndex);
				itemMasterFile.addItem(newItem);
			}
			return;
		}
			
		case REMOVE_ITEM_ACTION: {
			int selectedItemIndex = view.getSelectedItemRow();
			logger.debug("Delete row=" + selectedItemIndex);
			Item item = tableModel.getItem(selectedItemIndex);
			if (inventoryAccounting.isUnitReferenced(item.getId())) {
				String title = item.getNumber() + ":" + item.getName();
				String message = "<html>";
				
				message += "<b>Kan inte ta bort denna artikel</b><br/>";
				message += "Den används. Du måste se till att den<br/>";
				message += "inte används innan du kan ta bort den.";
				message += "</html>";
				
				MessageView messageView = new MessageView(title, message);
				
				messageView.show(controller.getDesktopView());
			} else {
				Item removedItem = tableModel.removeItem(view.getSelectedItemRow());
				
				itemMasterFile.removeItem(removedItem);
			}
			return;
		}
		
		default:
			break;
		}
		
		logger.warn("Unknown action event=" + e);
	}

    /**
     * Gets window name.
     *
     * @return the window name
     */
    String getWindowName() {
		return ItemMaintenanceView.class.getSimpleName();
	}

	private void checkEditButtons() {
		boolean isRowSelected = false;
		boolean isEditEnabled = view.isExtendedEditEnabled();
		
		int rowIndex = view.getSelectedItemRow();

		if (rowIndex >= 0) {
			isRowSelected = true;
		}
		
		view.setInsertButtonEnabled(isEditEnabled);
		view.setRemoveButtonEnabled(isRowSelected && isEditEnabled);
		view.setEditButtonEnabled(isRowSelected && isEditEnabled);
	}

}
