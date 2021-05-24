package se.melsom.warehouse.application.inventory.holding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.AbstractPresentationModel;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.desktop.DesktopPresentationModel;
import se.melsom.warehouse.application.desktop.DesktopView;
import se.melsom.warehouse.data.service.*;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.data.vo.HoldingVO;
import se.melsom.warehouse.data.vo.UnitVO;
import se.melsom.warehouse.edit.actual.EditActualInventory;
import se.melsom.warehouse.edit.actual.EditActualInventoryListener;
import se.melsom.warehouse.report.Report;
import se.melsom.warehouse.report.inventory.holding.InventoryHoldingViewReport;

import javax.annotation.PostConstruct;
import javax.swing.event.TableModelEvent;
import java.util.*;

@Component
public class InventoryHolding extends AbstractPresentationModel implements EditActualInventoryListener {
	private static final Logger logger = LoggerFactory.getLogger(InventoryHolding.class);

	@Autowired private AbstractInventoryHoldingView inventoryHoldingView;
	@Autowired private StockLocationService stockLocationService;
	@Autowired private ItemService itemService;
	@Autowired private OrganizationService organizationService;
	@Autowired private HoldingService holdingService;
	@Autowired private ActualInventoryService actualInventoryService;
	@Autowired private DesktopPresentationModel desktopPresentationModel;
	@Autowired private DesktopView desktopView;

	private final ContentModel tableModel = new ContentModel();
	private final Map<String, Command> actionCommands = new HashMap<>();
	private UnitVO selectedUnit = null;

	private final ViewState viewState = new ViewState();

	public InventoryHolding() {}

	@PostConstruct
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
		checkEditButtons();
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
		checkEditButtons();
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
		checkEditButtons();
		inventoryHoldingView.updateState(viewState);
	}

	void setExtendedEditEnabled(boolean isEnabled) {
		viewState.setExtendedEditSelected(isEnabled);
		checkEditButtons();
		inventoryHoldingView.updateState(viewState);
	}

	void generateReport() {
		InventoryHoldingViewReport report = new InventoryHoldingViewReport(selectedUnit, tableModel.getInventory());

		Report.save(report, desktopView.getFrame());
	}

	public void selectItem(int index) {
		viewState.setSelectedItemIndex(index);
		checkEditButtons();
		inventoryHoldingView.updateState(viewState);
	}

	public void insert() {
		EditActualInventory instanceEditor = new EditActualInventory(this, stockLocationService, itemService, desktopView.getFrame());
		ActualInventoryVO instance = new ActualInventoryVO();
		instanceEditor.editInventory(instance);
	}

	public void edit() {
		EditActualInventory instanceEditor = new EditActualInventory(this, stockLocationService, itemService, desktopView.getFrame());
		int rowIndex = viewState.getSelectedItemIndex();
		ActualInventoryVO instance = tableModel.getInventory(rowIndex);
		instanceEditor.editInventory(instance);

		logger.debug("Show edit dialog.");
	}

	@Override
	public void save(ActualInventoryVO actualInventoryVO) {
		logger.debug("Save actual inventory={}.", actualInventoryVO);
		if (actualInventoryVO == null) {
			return;
		}

		int rowIndex = tableModel.update(actualInventoryVO, -1);

		viewState.setSelectedItemIndex(rowIndex);


		// TODO: Handle added
//		if (editedInstance != null) {
//			int rowIndex = tableModel.insert(editedInstance);
//
//			viewState.setSelectedItemIndex(rowIndex);
////				inventoryAccounting.addInventory(editedInstance);
////				inventoryAccounting.addHolding(new Holding(selectedUnit, editedInstance.getLocation()));
//			checkEditButtons();
//			inventoryHoldingView.updateState(viewState);
//		}
		// TODO: Handle edited
//		if (editedInstance != null) {
//			rowIndex = tableModel.update(editedInstance, rowIndex);
//			viewState.setSelectedItemIndex(rowIndex);
////				inventoryAccounting.updateInventory(editedInstance);
//		}
		checkEditButtons();
		inventoryHoldingView.updateState(viewState);
	}

	public void remove() {
		int atIndex = viewState.getSelectedItemIndex();

		if (atIndex < 0) {
			return;
		}

		logger.debug("Delete row={}.", atIndex);

		ActualInventoryVO inventory = tableModel.remove(atIndex);
		// TODO: Handle removal
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
			// TODO: persist update
//			inventoryAccounting.updateInventory(inventory);
			return;
			
		default:
			break;
		}
		
		logger.warn("Unhandled event at row=" + e.getFirstRow() + ",column=" + e.getColumn());
	}

	private void checkEditButtons() {
		boolean isRowSelected = false;
		boolean isEditEnabled = viewState.isExtendedEditEnabled();
		
		int rowIndex = viewState.getSelectedItemIndex();

		if (rowIndex >= 0) {
			isRowSelected = true;
		}

		viewState.setGenerateReportEnabled(tableModel.getInventory().size() > 0);
		viewState.setInsertButtonEnabled(isEditEnabled);
		viewState.setRemoveButtonEnabled(isRowSelected && isEditEnabled);
		viewState.setEditButtonEnabled(isRowSelected && isEditEnabled);
	}

	public UnitVO getSelectedUnit() {
		return selectedUnit;
	}
}
