package se.melsom.warehouse.maintenance.nominal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.AbstractPresentationModel;
import se.melsom.warehouse.application.desktop.AbstractDesktopView;
import se.melsom.warehouse.data.service.ItemService;
import se.melsom.warehouse.application.desktop.DesktopPresentationModel;
import se.melsom.warehouse.data.service.NominalInventoryService;
import se.melsom.warehouse.data.vo.NominalInventoryVO;
import se.melsom.warehouse.edit.nominal.EditNominalInventoryListener;
import se.melsom.warehouse.edit.nominal.EditNominalInventory;

import javax.annotation.PostConstruct;
import java.awt.*;

@Component
public class NominalInventory extends AbstractPresentationModel implements EditNominalInventoryListener {
    private static final Logger logger = LoggerFactory.getLogger(NominalInventory.class);

    @Autowired private AbstractNominalInventoryView nominalInventoryView;
    @Autowired private ItemService itemService;
    @Autowired private NominalInventoryService nominalInventoryService;
    @Autowired private DesktopPresentationModel desktopPresentationModel;
    @Autowired private AbstractDesktopView desktopView;

    private final ContentModel contentModel = new ContentModel();
    private final ViewState viewState = new ViewState();

    @PostConstruct
    @Override
    public void initialize() {
        contentModel.setInstances(nominalInventoryService.getNominalInventory());
        nominalInventoryView.initialize(contentModel);
        nominalInventoryView.updateState(viewState);
        desktopPresentationModel.addInternalFrame(nominalInventoryView.getInternalFrame());
    }

    @Override
    public void showView() {
        nominalInventoryView.showView();
    }

    public void updateExtendedEdit(boolean isEditEnabled) {
        viewState.setExtendedEditSelected(isEditEnabled);
        updateButtonStates();
    }

    public void editSelectedNominalInventory() {
        EditNominalInventory instanceEditor =  new EditNominalInventory(this, itemService, desktopView.getFrame());;
        int rowIndex = viewState.getSelectedRowIndex();
        NominalInventoryVO instance = contentModel.getInstance(rowIndex);
        instanceEditor.editInstance(instance);
    }

    public void insertNominalInventory() {
        EditNominalInventory instanceEditor = new EditNominalInventory(this, itemService, desktopView.getFrame());
        NominalInventoryVO instance = new NominalInventoryVO();
        instanceEditor.editInstance(instance);
    }

    private void updateButtonStates() {
        boolean isRowSelected = false;
        boolean isEditEnabled = viewState.isExtendedEditSelected();

        int rowIndex = viewState.getSelectedRowIndex();

        if (rowIndex >= 0) {
            isRowSelected = true;
        }

        viewState.setInsertButtonEnabled(isEditEnabled);
        viewState.setRemoveButtonEnabled(isRowSelected && isEditEnabled);
        viewState.setEditButtonEnabled(isRowSelected && isEditEnabled);

        nominalInventoryView.updateState(viewState);
    }

    public void insert() {
//        EditNominalInventory instanceEditor = null;
//        NominalInventoryVO instance = new NominalInventoryVO();
////			instance.setId(inventoryAccounting.getNextMasterInventoryId());
//        NominalInventoryVO editedInstance = instanceEditor.editInstance(instance);
//
//        if (editedInstance != null) {
//            int rowIndex = contentModel.insert(editedInstance);
//
//            viewState.setSelectedRowIndex(rowIndex);
//            nominalInventoryService.addInventory(editedInstance);
//            nominalInventoryView.updateState(viewState);
//        }
    }

    public void edit() {
//        EditNominalInventory instanceEditor = null;
//        int rowIndex = viewState.getSelectedRowIndex();
//        NominalInventoryVO instance = contentModel.getInstance(rowIndex);
//        instanceEditor.editInstance(instance, this);
//
//        if (editedInstance != null) {
//            rowIndex = contentModel.update(editedInstance, rowIndex);
//            viewState.setSelectedRowIndex(rowIndex);
//            nominalInventoryService.updateInventory(editedInstance);
//            nominalInventoryView.updateState(viewState);
//        }
    }

    public void remove() {
        int selectedIndex = viewState.getSelectedRowIndex();
        NominalInventoryVO removedInstance = contentModel.remove(selectedIndex);

        nominalInventoryService.removeInventory(removedInstance);
    }

    @Override
    public void save(NominalInventoryVO editedInstance) {
//        rowIndex = contentModel.update(editedInstance, rowIndex);
//        viewState.setSelectedRowIndex(rowIndex);
//        nominalInventoryService.updateInventory(editedInstance);
//        nominalInventoryView.updateState(viewState);
    }
}
