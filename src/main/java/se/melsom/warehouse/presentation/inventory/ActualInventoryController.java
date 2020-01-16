package se.melsom.warehouse.presentation.inventory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.log4j.Logger;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.command.Command;
import se.melsom.warehouse.event.ModelEvent;
import se.melsom.warehouse.event.ModelEventListener;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.model.LocationMasterFile;
import se.melsom.warehouse.model.entity.StockLocation;
import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.presentation.common.edit.EditActualInventoryController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowSettings;

public class ActualInventoryController extends ViewController implements ModelEventListener, TableModelListener, ActionListener, ListSelectionListener {
	private static Logger logger = Logger.getLogger(ActualInventoryController.class);

	public static final String SECTION_SELECTED_ACTION = "SectionSelected";
	public static final String SLOT_SELECTED_ACTION = "SlotSelected";
	public static final String GENERATE_REPORT_ACTION = "GenerateReport";
	public static final String EXTENDED_EDIT_ACTION = "ExtendedEdit";
	public static final String TABLE_ROW_ACTION = "RowSelected";
	public static final String EDIT_INVENTORY_ACTION = "EditInventory";
	public static final String INSERT_INVENTORY_ACTION = "AddInventory";
	public static final String REMOVE_INVENTORY_ACTION = "DeleteInventory";

	private ApplicationController controller;
	
	private InventoryAccounting inventoryAccounting;
	private LocationMasterFile locationMasterFile;
	
	private ActualInventoryTableModel tableModel;
	private ActualInventoryView view;
	private Map<String, Command> actionCommands = new HashMap<>();	
	private StockLocation currentLocation = null;
	
	public ActualInventoryController(ApplicationController controller) {
		this.controller = controller;

		inventoryAccounting = controller.getInventoryAccounting();
		locationMasterFile = inventoryAccounting.getLocationMasterFile();

		WindowSettings settings = PersistentSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowSettings(getWindowName(), 21, 33, 778, 264, false);
			
			PersistentSettings.singleton().addWindowSettings(settings);
		}
		
		tableModel = new ActualInventoryTableModel();
		tableModel.addTableModelListener(this);
		view = new ActualInventoryView(this, tableModel);
		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		view.setVisible(settings.isVisible());
		view.addComponentListener(this);
		view.setGenerateButtonEnabled(false);		
		view.setSectionSelectedAction(SECTION_SELECTED_ACTION, this);
		view.setSlotSelectedAction(SLOT_SELECTED_ACTION, this);
		view.setGenerateReportAction(GENERATE_REPORT_ACTION, this);
		view.setExtendedEditAction(EXTENDED_EDIT_ACTION, this);
		view.setExtendedEditEnabled(false);
		view.setEditButtonEnabled(false);
		view.setEditButtonAction(EDIT_INVENTORY_ACTION, this);
		view.setInsertButtonEnabled(false);
		view.setInsertButtonAction(INSERT_INVENTORY_ACTION, this);
		view.setRemoveButtonEnabled(false);
		view.setRemoveButtonAction(REMOVE_INVENTORY_ACTION, this);
		view.setSelectedInventoryAction(TABLE_ROW_ACTION, this);

		controller.setInventoryViewMenuItemChecked(settings.isVisible());
	}
	
	public String getSelectedSection() {
		return view.getSelectedSectionItem();
	}

	public String getSelectedSlot() {
		return view.getSelectedSlotItem();
	}

	public Vector<ActualInventory> getInventory() {
		return tableModel.getInventory();
	}

	public String[] getTableColumnNames() {
		return ActualInventoryTableModel.columnNames;
	}

	private void updateStockLocations() {
		logger.debug("Update stock locstions");
		Set<String> locationSections = new TreeSet<>();
		
		for (StockLocation location : locationMasterFile.getLocations()) {
			locationSections.add(location.getSection());
		}
		
		view.setSectionSelectorItems(locationSections);	
	}
	
	public void removeInventory(int atIndex) {
		if (atIndex < 0) {
			return;
		}
		
		logger.debug("Delete row=" + view.getSelectedTableRow());

		ActualInventory inventory = tableModel.remove(atIndex);
		inventoryAccounting.removeInventory(inventory);
	}

	private void handleSelectedSection(String section) {
		Set<String> locationSlots = new TreeSet<>();
		
		for (StockLocation location : locationMasterFile.getLocations()) {
			if (location.getSection().equals(section)) {
				locationSlots.add(location.getSlot());
			}
		}
		
		view.setSlotSelectorItems(locationSlots);
	}
	
	private void handleSelectedSlot(String slot) {
		String section = view.getSelectedSectionItem();
		currentLocation = locationMasterFile.getLocation(section, slot);
		
		Vector<ActualInventory> inventoryList = inventoryAccounting.getActualInventory(section, slot);
		
		view.setGenerateButtonEnabled(inventoryList.size() > 0);		
		tableModel.setInventory(inventoryList);
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
			ActualInventory inventory = tableModel.getInventory(rowIndex);
			logger.debug("Updating inventory=" + inventory);
			
			inventoryAccounting.updateInventory(inventory);
			return;
			
		default:
			break;
		}
		
		logger.warn("Unhandled event at row=" + e.getFirstRow() + ",column=" + e.getColumn());
	}

	public JInternalFrame getInternalFrame() {
		return view;
	}

	@Override
	public JComponent getView() {
		return view;
	}

	public void showView() {
		logger.debug("showView()");
		if (view.isVisible()) {
			if (view.isIcon()) {
				try {
					view.setIcon(false);
				} catch (PropertyVetoException e) {
					logger.error("showView()", e);
				}
			} else {
				view.setVisible(false);
			}
		} else {
			view.setVisible(true);
		}
	}
	
	@Override
	public void handleEvent(ModelEvent event) {
		logger.debug("Model event=" + event);

		switch (event.getType()) {
		case ITEM_LIST_RELOADED:
		case ITEM_LIST_MODIFIED:
			break;

		case STOCK_LOCATIONS_RELOADED:
		case STOCK_LOCATIONS_MODIFIED:
			updateStockLocations();
			break;

		default:
			break;
		}
	}
	
	@Override
	public void finalize() throws Throwable {
		super.finalize();
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
		PersistentSettings.singleton().setWindowVisible(getWindowName(), true);	
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		controller.setInventoryViewMenuItemChecked(false);
		PersistentSettings.singleton().setWindowVisible(getWindowName(), false);	
	}

	private String getWindowName() {
		return ActualInventoryView.class.getSimpleName();
	}

	public void addActionCommand(String action, Command command) {
		actionCommands.put(action, command);
	} 
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}
		
		logger.debug("List section first index=" + e.getFirstIndex() + ",last index=" + e.getLastIndex());
		logger.debug(view.getSelectedTableRow());
		
		checkEditButtons();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		logger.debug("Received action=" + e.getActionCommand());

		switch (e.getActionCommand()) {
		case SECTION_SELECTED_ACTION: {
			if (view.getSelectedSectionIndex() < 0) {
				return;
			}
			
			String section = view.getSelectedSectionItem();

			logger.debug("Section selected=" + section);
			handleSelectedSection(section);
			checkEditButtons();
			return;
		}

		case SLOT_SELECTED_ACTION: {
			if (view.getSelectedSlotIndex() < 0) {
				return;
			}
			
			String slot = view.getSelectedSlotItem();

			logger.debug("Slot selected=" + slot);
			handleSelectedSlot(slot);
			checkEditButtons();
			return;
		}
		
		case GENERATE_REPORT_ACTION:
			Command command = actionCommands.get(GENERATE_REPORT_ACTION);
			if (command == null) {
				logger.warn("Action command for " + GENERATE_REPORT_ACTION);
				return;
			}
			
			command.execute();
			return;
			
			
		case EXTENDED_EDIT_ACTION:
			checkEditButtons();
			return;
			
		case EDIT_INVENTORY_ACTION: {
			EditActualInventoryController instanceEditor = new EditActualInventoryController(inventoryAccounting, controller.getDesktopView());
			int rowIndex = view.getSelectedTableRow();
			ActualInventory instance = tableModel.getInventory(rowIndex);
			ActualInventory editedInstance = instanceEditor.editInventory(instance);
			
			logger.debug("Show edit dialog.");
			if (editedInstance != null) {
				rowIndex = tableModel.update(editedInstance, rowIndex);
 				view.setSelectedTableRow(rowIndex);
				inventoryAccounting.updateInventory(editedInstance);
			}
			return;
		}
			
		case INSERT_INVENTORY_ACTION: {
			EditActualInventoryController instanceEditor = new EditActualInventoryController(inventoryAccounting, controller.getDesktopView());
			ActualInventory instance = new ActualInventory();
			instance.setId(inventoryAccounting.getNextActualInventoryId());
			instance.setLocation(currentLocation);
			ActualInventory editedInstance = instanceEditor.editInventory(instance);
			
			logger.debug("Show edit dialog.");
			
			if (editedInstance != null) {
				int rowIndex = tableModel.insert(editedInstance);
				
				view.setSelectedTableRow(rowIndex);
				inventoryAccounting.addInventory(editedInstance);
			}
			return;
		}
		
		case REMOVE_INVENTORY_ACTION:
			if (view.getSelectedTableRow() >= 0) {
				ActualInventory removedInventory = tableModel.remove(view.getSelectedTableRow());
				
				if (removedInventory.getItem() != null && removedInventory.getLocation() != null) {
					inventoryAccounting.removeInventory(removedInventory);
				}
			}
			return;
			
		default:			
			break;
		}
		
		logger.warn("Unhandled action=" + e);
	}
	
	private void checkEditButtons() {
		boolean isRowSelected = false;
		boolean isEditEnabled = view.isExtendedEditEnabled();
		
		int rowIndex = view.getSelectedTableRow();

		if (rowIndex >= 0) {
			isRowSelected = true;
		}
		
		view.setInsertButtonEnabled(isEditEnabled);
		view.setRemoveButtonEnabled(isRowSelected && isEditEnabled);
		view.setEditButtonEnabled(isRowSelected && isEditEnabled);
	}
}
