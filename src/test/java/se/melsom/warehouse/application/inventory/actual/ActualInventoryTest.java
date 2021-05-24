package se.melsom.warehouse.application.inventory.actual;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.application.desktop.AbstractDesktopView;
import se.melsom.warehouse.application.desktop.DesktopPresentationModel;
import se.melsom.warehouse.data.service.ActualInventoryService;
import se.melsom.warehouse.data.service.StockLocationService;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.data.vo.ItemVO;
import se.melsom.warehouse.data.vo.StockLocationVO;
import se.melsom.warehouse.settings.PersistentSettings;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {
        ActualInventory.class,
        ApplicationPresentationModel.class,
        DesktopPresentationModel.class,
        PersistentSettings.class})
public class ActualInventoryTest {
    @MockBean
    AbstractDesktopView desktopView;

    @MockBean
    AbstractActualInventoryView actualInventoryView;

    @Autowired
    ActualInventory presentationModel;

    @MockBean
    ActualInventoryService actualInventoryService;

    @MockBean
    StockLocationService stockLocationService;

    @Captor
    ArgumentCaptor<ViewState> viewStateArgumentCaptor;

    @BeforeEach
    public void setUp() {
        Vector<StockLocationVO> stockLocations = new Vector<>();

        stockLocations.add(createStockLocation(0, "A", "1", "A:1"));
        stockLocations.add(createStockLocation(1, "A", "2", "A:2"));
        stockLocations.add(createStockLocation(0, "B", "1", "B:1"));
        stockLocations.add(createStockLocation(1, "B", "2", "B:2"));

        Mockito.when(stockLocationService.getStockLocations()).thenReturn(stockLocations);

        Vector<ItemVO> items = new Vector<>();

        items.add(createItem(0, "item001", "00001", "pkg", "Item 001"));

        Vector<ActualInventoryVO> actualInventory = new Vector<>();

        actualInventory.add(createActualInventory(0, items.get(0), stockLocations.get(0), 100, null));
        actualInventory.add(createActualInventory(0, items.get(0), stockLocations.get(1), 100, null));

        Mockito.when(actualInventoryService.getActualInventory()).thenReturn(actualInventory);
        Mockito.when(actualInventoryService.getActualInventory("A", "1")).thenReturn(actualInventory);
    }

    @Test
    public void initializeTest() {
        presentationModel.initialize();
        verify(actualInventoryView).updateState(viewStateArgumentCaptor.capture());
        ViewState viewState = viewStateArgumentCaptor.getValue();
        assertEquals(2, viewState.getStockLocationSections().size());
        assertEquals("A", viewState.getStockLocationSections().toArray(new String[0])[0]);
        assertEquals("B", viewState.getStockLocationSections().toArray(new String[0])[1]);
        assertFalse(viewState.isExtendedEditSelected());
        assertFalse(viewState.isGenerateReportButtonEnabled());
        assertFalse(viewState.isEditButtonEnabled());
        assertFalse(viewState.isInsertButtonEnabled());
        assertFalse(viewState.isRemoveButtonEnabled());
    }

    @Test
    public void handleSectionSelectedTest() {
        presentationModel.initialize();
        presentationModel.handleSectionSelected(0, "A");
        verify(actualInventoryView, times(2)).updateState(viewStateArgumentCaptor.capture());
        ViewState viewState = viewStateArgumentCaptor.getValue();
        assertEquals(2, viewState.getStockLocationSlots().size());
        assertEquals("1", viewState.getStockLocationSlots().toArray(new String[0])[0]);
        assertEquals("2", viewState.getStockLocationSlots().toArray(new String[0])[1]);
    }

    @Test
    public void handleSlotSelectedTest() {
        presentationModel.initialize();
        presentationModel.handleSectionSelected(0, "A");
        presentationModel.handleSlotSelected("A", 0, "1");
        verify(actualInventoryView, times(3)).updateState(viewStateArgumentCaptor.capture());
        ViewState viewState = viewStateArgumentCaptor.getValue();
    }

    @Test
    public void handleEditTest() {
    }

    StockLocationVO createStockLocation(int id, String section, String slot, String description) {
        StockLocationVO stockLocation = new StockLocationVO();

        stockLocation.setId(id);
        stockLocation.setSection(section);
        stockLocation.setSlot(slot);
        stockLocation.setDescription(description);

        return stockLocation;
    }

    ItemVO createItem(int id, String name, String number, String packaging, String description) {
        ItemVO item = new ItemVO();

        item.setId(id);
        item.setName(name);
        item.setNumber(number);
        item.setPackaging(packaging);
        item.setDescription(description);

        return item;
    }

    ActualInventoryVO createActualInventory(int id, ItemVO item, StockLocationVO location, int quantity, String identity) {
        ActualInventoryVO actualInventory = new ActualInventoryVO();

        actualInventory.setId(id);
        actualInventory.setItem(item);
        actualInventory.setStockLocation(location);
        if (quantity >= 0) {
            actualInventory.setQuantity(quantity);
        } else if (identity != null) {
            actualInventory.setIdentity(identity);
        }

        return actualInventory;
    }
}
