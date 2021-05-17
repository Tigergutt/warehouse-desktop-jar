package se.melsom.warehouse.application.inventory.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.AbstractPresentationModel;
import se.melsom.warehouse.application.edit.master.EditMasterInventory;
import se.melsom.warehouse.application.main.DesktopPresentationModel;
import se.melsom.warehouse.data.service.NominalInventoryService;
import se.melsom.warehouse.data.vo.NominalInventoryVO;

@Component
public class MasterInventory extends AbstractPresentationModel {
    private static final Logger logger = LoggerFactory.getLogger(MasterInventory.class);

    @Autowired private AbstractMasterInventoryView view;
    @Autowired private NominalInventoryService nominalInventoryService;
    @Autowired private DesktopPresentationModel desktopPresentationModel;

    private final ContentModel contentModel = new ContentModel();
    private final ViewState viewState = new ViewState();

    @Override
    public void showView() {
        view.showView();
    }

    @Override
    public void initialize() {
        contentModel.setInstances(nominalInventoryService.getNominalInventory());
        view.initialize(contentModel);
    }

    public void insert() {
        EditMasterInventory instanceEditor = null;
        NominalInventoryVO instance = new NominalInventoryVO();
//			instance.setId(inventoryAccounting.getNextMasterInventoryId());
        NominalInventoryVO editedInstance = instanceEditor.editInstance(instance);

        if (editedInstance != null) {
            int rowIndex = contentModel.insert(editedInstance);

            viewState.setSelectedRowIndex(rowIndex);
            nominalInventoryService.addInventory(editedInstance);
            view.updateState(viewState);
        }
    }

    public void edit() {
        EditMasterInventory instanceEditor = null;
        int rowIndex = viewState.getSelectedRowIndex();
        NominalInventoryVO instance = contentModel.getInstance(rowIndex);
        NominalInventoryVO editedInstance = instanceEditor.editInstance(instance);

        if (editedInstance != null) {
            rowIndex = contentModel.update(editedInstance, rowIndex);
            viewState.setSelectedRowIndex(rowIndex);
            nominalInventoryService.updateInventory(editedInstance);
            view.updateState(viewState);
        }
    }

    public void remove() {
        int selectedIndex = viewState.getSelectedRowIndex();
        NominalInventoryVO removedInstance = contentModel.remove(selectedIndex);

        nominalInventoryService.removeInventory(removedInstance);
    }

    private void checkEditButtons() {
        boolean isRowSelected = false;
        boolean isEditEnabled = viewState.isExtendedEditSelected();

        int rowIndex = viewState.getSelectedRowIndex();

        if (rowIndex >= 0) {
            isRowSelected = true;
        }

        viewState.setInsertButtonEnabled(isEditEnabled);
        viewState.setRemoveButtonEnabled(isRowSelected && isEditEnabled);
        viewState.setEditButtonEnabled(isRowSelected && isEditEnabled);
    }
}
