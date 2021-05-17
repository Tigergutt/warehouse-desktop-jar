package se.melsom.warehouse.application.inventory.holding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.AbstractPresentationModel;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.edit.actual.EditActualInventory;
import se.melsom.warehouse.application.main.DesktopPresentationModel;
import se.melsom.warehouse.data.service.ActualInventoryService;
import se.melsom.warehouse.data.service.HoldingService;
import se.melsom.warehouse.data.service.OrganizationService;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.data.vo.HoldingVO;
import se.melsom.warehouse.data.vo.UnitVO;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.model.UnitsMasterFile;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import java.util.*;

@Component
public class InventoryHolding extends AbstractPresentationModel {
	private static final Logger logger = LoggerFactory.getLogger(InventoryHolding.class);

	@Autowired private AbstractInventoryHoldingView inventoryHoldingView;
	@Autowired private OrganizationService organizationService;
	@Autowired private HoldingService holdingService;
	@Autowired private ActualInventoryService actualInventoryService;
	@Autowired private DesktopPresentationModel desktopPresentationModel;

    public static final String SUPERIOR_UNIT_SELECTED_ACTION = "SupUSel";
    public static final String UNIT_SELECTED_ACTION = "USel";
    public static final String GENERATE_REPORT_ACTION = "GenerateReport";
    public static final String EXTENDED_EDIT_ACTION = "ExtendedEdit";
    public static final String TABLE_ROW_ACTION = "RowSelected";
    public static final String EDIT_INVENTORY_ACTION = "EditInventory";
    public static final String INSERT_INVENTORY_ACTION = "AddInventory";
    public static final String REMOVE_INVENTORY_ACTION = "DeleteInventory";

	private ApplicationPresentationModel controller;
	
	private InventoryAccounting inventoryAccounting;
	private UnitsMasterFile unitsMasterFile;
	
	private final ContentModel tableModel = new ContentModel();
	private final Map<String, Command> actionCommands = new HashMap<>();
	private UnitVO selectedUnit = null;

	private final ViewState viewState = new ViewState();

	public InventoryHolding() {}

	@Override
	public void initialize() {
		logger.debug("Execute initialize().");
		inventoryHoldingView.initialize(tableModel);
		desktopPresentationModel.addInternalFrame(inventoryHoldingView.getInternalFrame());
		setSuperiorUnits();
		inventoryHoldingView.updateState(viewState);
	}

	@Override
	public void showView() {
		logger.debug("showView()");
		inventoryHoldingView.showView();
	}

	private void setSuperiorUnits() {
		logger.debug("Set superior units");
		Set<String> superiorUnits = new TreeSet<>();

		for (UnitVO aUnit : organizationService.getSuperiorUnits()) {
			superiorUnits.add(aUnit.getCallSign());
		}

		viewState.setSuperiors(superiorUnits);
		inventoryHoldingView.updateState(viewState);
	}

	public void selectSuperiorUnit(int index, String callSign) {
		if (viewState.isUpdating()) {
			return;
		}
		logger.debug("Selected unit call sign={}.", callSign);
		Set<String> units = new TreeSet<>();

		for (UnitVO aUnit : organizationService.getSubordinateUnits(callSign)) {
			units.add(aUnit.getCallSign());
		}

		viewState.setUnits(units);
		viewState.setSelectedUnitIndex(-1);
		viewState.setSelectedSuperiorUnitIndex(index);
		inventoryHoldingView.updateState(viewState);
	}

	public void selectSubordinateUnit(String superior, int index, String callSign) {
		if (viewState.isUpdating()) {
			return;
		}
		selectedUnit = organizationService.getUnit(callSign);
		logger.debug("Selected unit={}.", selectedUnit);
		Vector<ActualInventoryVO> inventory = new Vector<>();

		for (HoldingVO holding : holdingService.findByUnitId(selectedUnit.getId())) {
			logger.debug("Holding {}.", holding);
			String section = holding.getLocation().getSection();
			String slot = holding.getLocation().getSlot();

			inventory.addAll(actualInventoryService.getActualInventory(section, slot));
		}

		for (UnitVO subordinate : organizationService.getSubordinateUnits(selectedUnit.getCallSign())) {
			logger.debug("Subordinate unit={}.", subordinate);
			for (HoldingVO holding : holdingService.findByUnitId(subordinate.getId())) {
				logger.debug("Holding {}.", holding);
				String section = holding.getLocation().getSection();
				String slot = holding.getLocation().getSlot();

				inventory.addAll(actualInventoryService.getActualInventory(section, slot));
			}
		}

		tableModel.setInventory(inventory);
	}

	void setExtendedEditEnabled(boolean isEnabled) {
		viewState.setExtendedEditSelected(isEnabled);
	}

	void generateReport()
	{
		Command command = actionCommands.get(GENERATE_REPORT_ACTION);
		if (command == null) {
			logger.warn("Action command for " + GENERATE_REPORT_ACTION);
			return;
		}

		command.execute();
	}

	public void selectItem(int index) {
		viewState.setSelectedItemIndex(index);
		checkEditButtons();
		inventoryHoldingView.updateState(viewState);
	}

	public void insert() {
		EditActualInventory instanceEditor = new EditActualInventory();
		ActualInventoryVO instance = new ActualInventoryVO();
//			instance.setId(inventoryAccounting.getNextActualInventoryId());
		ActualInventoryVO editedInstance = instanceEditor.editInventory(instance);

		logger.debug("Show edit dialog.");

		if (editedInstance != null) {
			int rowIndex = tableModel.insert(editedInstance);

			viewState.setSelectedItemIndex(rowIndex);
//				inventoryAccounting.addInventory(editedInstance);
//				inventoryAccounting.addHolding(new Holding(selectedUnit, editedInstance.getLocation()));
			checkEditButtons();
			inventoryHoldingView.updateState(viewState);
		}
	}

	public void edit() {
		EditActualInventory instanceEditor = new EditActualInventory();
		int rowIndex = viewState.getSelectedItemIndex();
		ActualInventoryVO instance = tableModel.getInventory(rowIndex);
		ActualInventoryVO editedInstance = instanceEditor.editInventory(instance);

		logger.debug("Show edit dialog.");
		if (editedInstance != null) {
			rowIndex = tableModel.update(editedInstance, rowIndex);
			viewState.setSelectedItemIndex(rowIndex);
//				inventoryAccounting.updateInventory(editedInstance);
			inventoryHoldingView.updateState(viewState);
		}
	}

	public void remove() {
		int atIndex = viewState.getSelectedItemIndex();

		if (atIndex < 0) {
			return;
		}

		logger.debug("Delete row={}.", atIndex);

		ActualInventoryVO inventory = tableModel.remove(atIndex);
//		inventoryAccounting.removeInventory(inventory);
		inventoryHoldingView.updateState(viewState);
	}

    public Vector<ActualInventoryVO> getInventory() {
		return tableModel.getInventory();
	}

    public String[] getTableColumnNames() {
		return ContentModel.columnNames;
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
			ActualInventoryVO inventory = tableModel.getInventory(rowIndex);
			logger.debug("Updating inventory=" + inventory);
			
//			inventoryAccounting.updateInventory(inventory);
			return;
			
		default:
			break;
		}
		
		logger.warn("Unhandled event at row=" + e.getFirstRow() + ",column=" + e.getColumn());
	}

//	@Override
//	public void handleEvent(ModelEvent event) {
//		logger.debug("Model event=" + event);
//
//		switch (event.getType()) {
//		case ORGANIZATIONAL_UNITS_RELOADED:
//		case ORGANIZATIONAL_UNITS_UPDATED:
//			updateUnits();
//			break;
//
//		default:
//			break;
//		}
//	}
	
    public void addActionCommand(String action, Command command) {
		actionCommands.put(action, command);
	} 
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}
		
		logger.debug("List section first index=" + e.getFirstIndex() + ",last index=" + e.getLastIndex());

		checkEditButtons();
	}

	private void checkEditButtons() {
		boolean isRowSelected = false;
		boolean isEditEnabled = viewState.isExtendedEditEnabled();
		
		int rowIndex = viewState.getSelectedItemIndex();

		if (rowIndex >= 0) {
			isRowSelected = true;
		}
		
		viewState.setInsertButtonEnabled(isEditEnabled);
		viewState.setRemoveButtonEnabled(isRowSelected && isEditEnabled);
		viewState.setEditButtonEnabled(isRowSelected && isEditEnabled);
	}

	public UnitVO getSelectedUnit() {
		return selectedUnit;
	}
}
