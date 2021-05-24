package se.melsom.warehouse.application.inventory.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.application.desktop.DesktopPresentationModel;
import se.melsom.warehouse.application.desktop.AbstractDesktopView;
import se.melsom.warehouse.application.desktop.ViewState;
import se.melsom.warehouse.data.service.InventoryService;
import se.melsom.warehouse.data.vo.StockOnHandVO;
import se.melsom.warehouse.settings.PersistentSettings;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(classes = {
        InventoryStatus.class,
        ApplicationPresentationModel.class,
        DesktopPresentationModel.class,
        PersistentSettings.class,
        ViewState.class})
public class InventoryStatusTest {
    @MockBean
    AbstractDesktopView desktopView;

    @MockBean
    AbstractInventoryStatusView inventoryHoldingView;

    @Autowired
    InventoryStatus presentationModel;

    @MockBean
    InventoryService inventoryService;

    @BeforeEach
    public void setUp() {
        Vector<StockOnHandVO> stockOnHandVOVector = new Vector<>();

        stockOnHandVOVector.add(createStockOnHand("222", "BBB", "007", 2, 3));
        stockOnHandVOVector.add(createStockOnHand("111", "CCC", "", 1, 1));
        stockOnHandVOVector.add(createStockOnHand("333", "AAA", "002", 3, 2));
        stockOnHandVOVector.add(createStockOnHand("555", "AAA", "", 5, 0));
        stockOnHandVOVector.add(createStockOnHand("444", "BBB", "", 4, 4));

        Mockito.when(inventoryService.getStockOnHand())
                .thenReturn(stockOnHandVOVector);
        presentationModel.initialize();
    }

    @Test
    public void testingFilter() {
        Vector<StockOnHandVO> stockOnHandVOVector;
        ContentModel contentModel = presentationModel.getContentModel();
        stockOnHandVOVector = contentModel.getStockOnHand();
        assertEquals(5, stockOnHandVOVector.size());
        presentationModel.setShowingBalances(false);
        presentationModel.setShowingOverplus(false);
        presentationModel.setShowingShortfall(false);
        stockOnHandVOVector = contentModel.getStockOnHand();
        assertEquals(0, stockOnHandVOVector.size());

        presentationModel.setShowingBalances(true);
        contentModel = presentationModel.getContentModel();
        stockOnHandVOVector = contentModel.getStockOnHand();
        assertEquals(2, stockOnHandVOVector.size());
        assertEquals("111", stockOnHandVOVector.get(0).getNumber());
        assertEquals("444", stockOnHandVOVector.get(1).getNumber());
        presentationModel.setShowingBalances(false);

        presentationModel.setShowingOverplus(true);
        stockOnHandVOVector = contentModel.getStockOnHand();
        assertEquals(1, stockOnHandVOVector.size());
        assertEquals("222", stockOnHandVOVector.get(0).getNumber());
        presentationModel.setShowingOverplus(false);

        presentationModel.setShowingShortfall(true);
        stockOnHandVOVector = contentModel.getStockOnHand();
        assertEquals(2, stockOnHandVOVector.size());
        assertEquals("333", stockOnHandVOVector.get(0).getNumber());
        assertEquals("555", stockOnHandVOVector.get(1).getNumber());
        presentationModel.setShowingShortfall(false);

        assertEquals(0, contentModel.getStockOnHand().size());

        presentationModel.setShowingBalances(true);
        stockOnHandVOVector = contentModel.getStockOnHand();
        assertEquals(2, stockOnHandVOVector.size());
        assertEquals("111", stockOnHandVOVector.get(0).getNumber());
        assertEquals("444", stockOnHandVOVector.get(1).getNumber());
        presentationModel.setShowingOverplus(true);
        stockOnHandVOVector = contentModel.getStockOnHand();
        assertEquals(3, stockOnHandVOVector.size());
        assertEquals("222", stockOnHandVOVector.get(0).getNumber());
        assertEquals("111", stockOnHandVOVector.get(1).getNumber());
        assertEquals("444", stockOnHandVOVector.get(2).getNumber());
        presentationModel.setShowingShortfall(true);
        stockOnHandVOVector = contentModel.getStockOnHand();
        assertEquals(5, stockOnHandVOVector.size());
        assertEquals("222", stockOnHandVOVector.get(0).getNumber());
        assertEquals("111", stockOnHandVOVector.get(1).getNumber());
        assertEquals("333", stockOnHandVOVector.get(2).getNumber());
        assertEquals("555", stockOnHandVOVector.get(3).getNumber());
        assertEquals("444", stockOnHandVOVector.get(4).getNumber());

        // Återställer till default för att inte störa andra tester.
        presentationModel.setShowingBalances(true);
        presentationModel.setShowingOverplus(true);
        presentationModel.setShowingShortfall(true);
    }

    @Test
    public void testSortByNumber() {
        presentationModel.setItemViewOrder(ItemViewOrder.BY_NUMBER);
        ContentModel contentModel = presentationModel.getContentModel();
        Vector<StockOnHandVO> stockOnHandVOVector = contentModel.getStockOnHand();
        assertEquals(5, stockOnHandVOVector.size());
        assertEquals("111", stockOnHandVOVector.get(0).getNumber());
        assertEquals("222", stockOnHandVOVector.get(1).getNumber());
        assertEquals("333", stockOnHandVOVector.get(2).getNumber());
        assertEquals("444", stockOnHandVOVector.get(3).getNumber());
        assertEquals("555", stockOnHandVOVector.get(4).getNumber());

        // Återställer till default för att inte störa andra tester.
        presentationModel.setItemViewOrder(ItemViewOrder.NONE);
    }

    @Test
    public void testSortByName() {
        presentationModel.setItemViewOrder(ItemViewOrder.BY_NAME);
        ContentModel contentModel = presentationModel.getContentModel();
        Vector<StockOnHandVO> stockOnHandVOVector = contentModel.getStockOnHand();
        assertEquals(5, stockOnHandVOVector.size());
        assertEquals("333", stockOnHandVOVector.get(0).getNumber());
        assertEquals("555", stockOnHandVOVector.get(1).getNumber());
        assertEquals("222", stockOnHandVOVector.get(2).getNumber());
        assertEquals("444", stockOnHandVOVector.get(3).getNumber());
        assertEquals("111", stockOnHandVOVector.get(4).getNumber());

        // Återställer till default för att inte störa andra tester.
        presentationModel.setItemViewOrder(ItemViewOrder.NONE);
    }

    @Test
    public void testSortByIdentity() {
        presentationModel.setItemViewOrder(ItemViewOrder.BY_IDENTITY);
        ContentModel contentModel = presentationModel.getContentModel();
        Vector<StockOnHandVO> stockOnHandVOVector = contentModel.getStockOnHand();
        assertEquals(5, stockOnHandVOVector.size());
        assertEquals("333", stockOnHandVOVector.get(0).getNumber());
        assertEquals("222", stockOnHandVOVector.get(1).getNumber());
        assertEquals("111", stockOnHandVOVector.get(2).getNumber());
        assertEquals("444", stockOnHandVOVector.get(3).getNumber());
        assertEquals("555", stockOnHandVOVector.get(4).getNumber());

        // Återställer till default för att inte störa andra tester.
        presentationModel.setItemViewOrder(ItemViewOrder.NONE);
    }

    StockOnHandVO createStockOnHand(String number, String name, String identity, int nominal, int actual) {
        StockOnHandVO element = new StockOnHandVO();

        element.setNumber(number);
        element.setName(name);
        element.setIdentity(identity);
        element.setNominalQuantity(nominal);
        element.setActualQuantity(actual);

        return element;
    }
}
