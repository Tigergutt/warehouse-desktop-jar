package se.melsom.warehouse.application.inventory.actual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.AbstractPresentationModel;
import se.melsom.warehouse.application.edit.actual.EditActualInventory;
import se.melsom.warehouse.application.main.DesktopPresentationModel;
import se.melsom.warehouse.data.service.ActualInventoryService;
import se.melsom.warehouse.data.service.StockLocationService;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.data.vo.StockLocationVO;

import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

@Component
public class ActualInventory extends AbstractPresentationModel {
    private static final Logger logger = LoggerFactory.getLogger(ActualInventory.class);

    @Autowired private AbstractActualInventoryView actualInventoryView;
    @Autowired private StockLocationService stockLocationService;
    @Autowired private ActualInventoryService actualInventoryService;
    @Autowired private DesktopPresentationModel desktopPresentationModel;

    private final ContentModel contentModel = new ContentModel();
    private ViewState viewState = new ViewState();

    @Override
    public void initialize() {
        logger.debug("Initialize.");
        viewState = new ViewState();
        actualInventoryView.initialize(contentModel);
        desktopPresentationModel.addInternalFrame(actualInventoryView.getInternalFrame());
        setStockLocations();
        actualInventoryView.updateState(viewState);
    }

    @Override
    public void showView() {
        logger.debug("Show view.");
        actualInventoryView.showView();
    }

    private void setStockLocations() {
        logger.debug("Set stock locations");
        Set<String> locationSections = new TreeSet<>();

        for (StockLocationVO location : stockLocationService.getStockLocations()) {
            locationSections.add(location.getSection());
        }

        viewState.setStockLocationSections(locationSections);
    }

    public void handleSectionSelected(int sectionIndex, String section) {
        if (viewState.isUpdating()) {
            return;
        }
        Set<String> locationSlots = new TreeSet<>();

        for (StockLocationVO location : stockLocationService.getStockLocations()) {
            if (location.getSection().equals(section)) {
                locationSlots.add(location.getSlot());
            }
        }

        contentModel.clearInventory();
        viewState.setCurrentSectionIndex(sectionIndex);
        viewState.setCurrentSlotIndex(-1);
        viewState.setStockLocationSlots(locationSlots);
        actualInventoryView.updateState(viewState);
    }

    public void handleSlotSelected(String section, int slotIndex, String slot) {
        if (viewState.isUpdating()) {
            return;
        }
        Vector<ActualInventoryVO> inventoryList = actualInventoryService.getActualInventory(section, slot);
        contentModel.setInventory(inventoryList);
        viewState.setCurrentSlotIndex(slotIndex);
		viewState.setGenerateReportButtonEnabled(inventoryList.size() > 0);
		actualInventoryView.updateState(viewState);
    }

    public void extendedEdit(boolean isEnabled) {
        viewState.setExtendedEditSelected(isEnabled);
        checkEditButtons();
        actualInventoryView.updateState(viewState);
    }


    public void itemSelected(int selectedTableRow) {
        viewState.setCurrentItemIndex(selectedTableRow);
        checkEditButtons();
        actualInventoryView.updateState(viewState);
    }

    public void handleEdit(int rowIndex) {
        EditActualInventory instanceEditor = new EditActualInventory();
        ActualInventoryVO instance = contentModel.getInventory(rowIndex);
        logger.debug("Show edit dialog.");
        ActualInventoryVO editedInstance = instanceEditor.editInventory(instance);

        if (editedInstance != null) {
            rowIndex = contentModel.update(editedInstance, rowIndex);
            viewState.setCurrentItemIndex(rowIndex);
            // TODO: implement update value
            // inventoryAccounting.updateInventory(editedInstance);
            actualInventoryView.updateState(viewState);
        }
    }

    public void handleInsert() {
    }

    public void handleRemove(int rowIndex) {
    }

    private void checkEditButtons() {
		boolean isRowSelected = false;
		boolean isEditEnabled = viewState.isExtendedEditSelected();

		int rowIndex = viewState.getCurrentItemIndex();

		if (rowIndex >= 0) {
			isRowSelected = true;
		}

        viewState.setInsertButtonEnabled(isEditEnabled);
        viewState.setRemoveButtonEnabled(isRowSelected && isEditEnabled);
        viewState.setEditButtonEnabled(isRowSelected && isEditEnabled);
    }
}
