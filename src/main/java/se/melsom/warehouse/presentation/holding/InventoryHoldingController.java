package se.melsom.warehouse.presentation.holding;

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
import se.melsom.warehouse.model.UnitsMasterFile;
import se.melsom.warehouse.model.entity.Holding;
import se.melsom.warehouse.model.entity.OrganizationalUnit;
import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.presentation.common.edit.EditActualInventoryController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowSettings;

/**
 * The type Inventory holding controller.
 */
public class InventoryHoldingController extends ViewController implements ModelEventListener, TableModelListener, ActionListener, ListSelectionListener {
	private static Logger logger = Logger.getLogger(InventoryHoldingController.class);

    /**
     * The constant SUPERIOR_UNIT_SELECTED_ACTION.
     */
    public static final String SUPERIOR_UNIT_SELECTED_ACTION = "SupUSel";
    /**
     * The constant UNIT_SELECTED_ACTION.
     */
    public static final String UNIT_SELECTED_ACTION = "USel";
    /**
     * The constant GENERATE_REPORT_ACTION.
     */
    public static final String GENERATE_REPORT_ACTION = "GenerateReport";
    /**
     * The constant EXTENDED_EDIT_ACTION.
     */
    public static final String EXTENDED_EDIT_ACTION = "ExtendedEdit";
    /**
     * The constant TABLE_ROW_ACTION.
     */
    public static final String TABLE_ROW_ACTION = "RowSelected";
    /**
     * The constant EDIT_INVENTORY_ACTION.
     */
    public static final String EDIT_INVENTORY_ACTION = "EditInventory";
    /**
     * The constant INSERT_INVENTORY_ACTION.
     */
    public static final String INSERT_INVENTORY_ACTION = "AddInventory";
    /**
     * The constant REMOVE_INVENTORY_ACTION.
     */
    public static final String REMOVE_INVENTORY_ACTION = "DeleteInventory";

	private ApplicationController controller;
	
	private InventoryAccounting inventoryAccounting;
	private UnitsMasterFile unitsMasterFile;
	
	private InventoryHoldingTableModel tableModel;
	private InventoryHoldingView view;
	private Map<String, Command> actionCommands = new HashMap<>();	
	private OrganizationalUnit selectedUnit = null;

    /**
     * Instantiates a new Inventory holding controller.
     *
     * @param controller the controller
     */
    public InventoryHoldingController(ApplicationController controller) {
		this.controller = controller;

		inventoryAccounting = controller.getInventoryAccounting();
		unitsMasterFile = inventoryAccounting.getUnitsMasterFile();

		WindowSettings settings = PersistentSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowSettings(getWindowName(), 21, 33, 778, 264, false);
			
			PersistentSettings.singleton().addWindowSettings(settings);
		}
		
		tableModel = new InventoryHoldingTableModel();
		tableModel.addTableModelListener(this);
		view = new InventoryHoldingView(this, tableModel);
		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		view.setVisible(settings.isVisible());
		view.addComponentListener(this);
		view.setGenerateButtonEnabled(false);		
		view.setSuperiorUnitSelectedAction(SUPERIOR_UNIT_SELECTED_ACTION, this);
		view.setUnitSelectedAction(UNIT_SELECTED_ACTION, this);
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

		controller.setInventoryHoldingViewMenuItemChecked(settings.isVisible());
	}

    /**
     * Gets selected unit.
     *
     * @return the selected unit
     */
    public OrganizationalUnit getSelectedUnit() {
		return selectedUnit;
	}

    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public Vector<ActualInventory> getInventory() {
		return tableModel.getInventory();
	}

    /**
     * Get table column names string [ ].
     *
     * @return the string [ ]
     */
    public String[] getTableColumnNames() {
		return InventoryHoldingTableModel.columnNames;
	}

	private void updateUnits() {
		logger.debug("Update stock locstions");
		Set<String> superiorUnits = new TreeSet<>();
		
		for (OrganizationalUnit aUnit : unitsMasterFile.getTopLevelUnits()) {
			superiorUnits.add(aUnit.getCallsign());
		}
		
		view.setSuperiorUnitSelectorItems(superiorUnits);	
	}

    /**
     * Remove inventory.
     *
     * @param atIndex the at index
     */
    public void removeInventory(int atIndex) {
		if (atIndex < 0) {
			return;
		}
		
		logger.debug("Delete row=" + view.getSelectedTableRow());

		ActualInventory inventory = tableModel.remove(atIndex);
		inventoryAccounting.removeInventory(inventory);
	}

	private void handleSelectedSuperiorUnit(String callsign) {
		Set<String> units = new TreeSet<>();
		
		for (OrganizationalUnit aUnit : unitsMasterFile.getSubUnits(callsign)) {
			units.add(aUnit.getCallsign());
		}
		
		view.setUnitSelectorItems(units);
	}
	
	private void handleSelectedUnit(String callsign) {
		selectedUnit = unitsMasterFile.getUnit(callsign);
		logger.debug("Selected unit=" + selectedUnit);
		Vector<ActualInventory> inventory = new Vector<>();
		
		for (Holding aHolding : inventoryAccounting.getHoldings(selectedUnit.getId())) {
			logger.trace(aHolding);
			String section = aHolding.getLocation().getSection();
			String slot = aHolding.getLocation().getSlot();
			
			inventory.addAll(inventoryAccounting.getActualInventory(section, slot));
		}
		
		for (OrganizationalUnit subUnit : inventoryAccounting.getUnitsMasterFile().getSubUnits(selectedUnit.getCallsign())) {
			for (Holding aHolding : inventoryAccounting.getHoldings(subUnit.getId())) {
				logger.trace(aHolding);
				String section = aHolding.getLocation().getSection();
				String slot = aHolding.getLocation().getSlot();
				
				inventory.addAll(inventoryAccounting.getActualInventory(section, slot));
			}
		}
		
		view.setGenerateButtonEnabled(inventory.size() > 0);		
		tableModel.setInventory(inventory);
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
		case ORGANIZATIONAL_UNITS_RELOADED:
		case ORGANIZATIONAL_UNITS_UPDATED:
			updateUnits();
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
		controller.setInventoryHoldingViewMenuItemChecked(false);
		PersistentSettings.singleton().setWindowVisible(getWindowName(), false);	
	}

	private String getWindowName() {
		return InventoryHoldingView.class.getSimpleName();
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
		
		logger.debug("List section first index=" + e.getFirstIndex() + ",last index=" + e.getLastIndex());
		logger.debug(view.getSelectedTableRow());
		
		checkEditButtons();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		logger.debug("Received action=" + e.getActionCommand());

		switch (e.getActionCommand()) {
		case SUPERIOR_UNIT_SELECTED_ACTION: {
			if (view.getSelectedSuperiorUnitIndex() < 0) {
				return;
			}
			
			String section = view.getSelectedSuperiorUnitItem();

			logger.debug("Superior unit selected=" + section);
			handleSelectedSuperiorUnit(section);
			checkEditButtons();
			return;
		}

		case UNIT_SELECTED_ACTION: {
			if (view.getSelectedUnitIndex() < 0) {
				return;
			}
			
			String callsign = view.getSelectedUnitItem();

			logger.debug("Unit selected=" + callsign);
			handleSelectedUnit(callsign);
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
			ActualInventory editedInstance = instanceEditor.editInventory(instance);
			
			logger.debug("Show edit dialog.");
			
			if (editedInstance != null) {
				int rowIndex = tableModel.insert(editedInstance);
				
				view.setSelectedTableRow(rowIndex);
				inventoryAccounting.addInventory(editedInstance);
				inventoryAccounting.addHolding(new Holding(selectedUnit, editedInstance.getLocation()));
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
