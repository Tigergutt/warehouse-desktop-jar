package se.melsom.warehouse.application.inventory.actual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.AbstractPresentationModel;
import se.melsom.warehouse.application.desktop.DesktopPresentationModel;
import se.melsom.warehouse.application.desktop.DesktopView;
import se.melsom.warehouse.data.service.ActualInventoryService;
import se.melsom.warehouse.data.service.HoldingService;
import se.melsom.warehouse.data.service.ItemService;
import se.melsom.warehouse.data.service.StockLocationService;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.data.vo.HoldingVO;
import se.melsom.warehouse.data.vo.StockLocationVO;
import se.melsom.warehouse.edit.actual.EditActualInventoryListener;
import se.melsom.warehouse.edit.actual.EditActualInventory;
import se.melsom.warehouse.report.PdfReportRenderer;
import se.melsom.warehouse.report.Report;
import se.melsom.warehouse.report.command.GenerateActualInventoryReport;
import se.melsom.warehouse.report.command.GenerateStockLocationInventoryReport;
import se.melsom.warehouse.report.component.Page;
import se.melsom.warehouse.report.inventory.location.StockLocationReport;
import se.melsom.warehouse.settings.PersistentSettings;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

@Component
public class ActualInventory extends AbstractPresentationModel implements EditActualInventoryListener {
    private static final Logger logger = LoggerFactory.getLogger(ActualInventory.class);

    @Autowired private AbstractActualInventoryView actualInventoryView;
    @Autowired private HoldingService holdingService;
    @Autowired private StockLocationService stockLocationService;
    @Autowired private ItemService itemService;
    @Autowired private ActualInventoryService actualInventoryService;
    @Autowired private DesktopPresentationModel desktopPresentationModel;
    @Autowired private DesktopView desktopView;
    @Autowired private PersistentSettings persistentSettings;

    private final ContentModel contentModel = new ContentModel();
    private ViewState viewState = new ViewState();

    @PostConstruct
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

    public StockLocationVO getStockLocation() {
        return stockLocationService.getStockLocation(viewState.getCurrentSection(), viewState.getCurrentSlot());
    }

    public Vector<ActualInventoryVO> getInventory() {
        return contentModel.getInventory();
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

    public void generateReport() {
        StockLocationVO stockLocation = getStockLocation();
        HoldingVO holding = holdingService.findByStockLocation(stockLocation);
        Vector<ActualInventoryVO> inventory = getInventory();
        StockLocationReport report = new StockLocationReport(holding, inventory);

        // TODO: improve parent view handling.
        save(report, null);
    }

    public void itemSelected(int selectedTableRow) {
        viewState.setCurrentItemIndex(selectedTableRow);
        checkEditButtons();
        actualInventoryView.updateState(viewState);
    }

    public void handleEdit(int rowIndex) {
        EditActualInventory instanceEditor = new EditActualInventory(this, stockLocationService,itemService, desktopView.getFrame());
        ActualInventoryVO instance = contentModel.getInventory(rowIndex);
        logger.debug("Show edit dialog.");
        instanceEditor.editInventory(instance);
//
//        if (editedInstance != null) {
//            rowIndex = contentModel.update(editedInstance, rowIndex);
//            viewState.setCurrentItemIndex(rowIndex);
//            // TODO: implement update value
//            // inventoryAccounting.updateInventory(editedInstance);
//            actualInventoryView.updateState(viewState);
//        }
    }

    @Override
    public void save(ActualInventoryVO actualInventoryVO) {
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

    protected void save(Report report, JFrame parent) {
        PdfReportRenderer renderer = new PdfReportRenderer();

        for (Page page : report.getPages()) {
            renderer.render(page);
        }

        String directory = persistentSettings.getProperty("reportDirectory", ".");

        JFileChooser chooser = new JFileChooser(directory);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF file.", "pdf");

        chooser.setFileFilter(filter);
        chooser.setSelectedFile(new File(directory, report.getReportName() + ".pdf"));

        if (chooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        persistentSettings.setProperty("reportDirectory", directory);

        String path = chooser.getSelectedFile().getPath();

        if (!path.endsWith(".pdf")) {
            path += ".pdf";
        }

        try {
            renderer.save(path);
        } catch (IOException e) {
            logger.error("Could not save report.", e);
        }
    }
}
