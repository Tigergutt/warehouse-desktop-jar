package se.melsom.warehouse.application.inventory.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import se.melsom.warehouse.data.vo.StockOnHandVO;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContentModelTest {
    static Vector<StockOnHandVO> stockOnHandVOVector = new Vector<>();

    @BeforeAll
    public static void initialize() {
        stockOnHandVOVector.clear();
        stockOnHandVOVector.add(createElement("222", "BBB", "007", 2, 3));
        stockOnHandVOVector.add(createElement("111", "CCC", "", 1, 1));
        stockOnHandVOVector.add(createElement("333", "AAA", "002", 3, 2));
        stockOnHandVOVector.add(createElement("555", "AAA", "", 5, 0));
        stockOnHandVOVector.add(createElement("444", "BBB", "", 4, 4));

    }

    @Test
    public void testingInitialize() {
        ContentModel contentModel = new ContentModel();

        contentModel.setStockOnHand(stockOnHandVOVector);
        assertEquals(5, contentModel.getStockOnHand().size());
    }

    @Test
    public void testingShowingBalances() {
        ContentModel contentModel = new ContentModel();

        contentModel.setStockOnHand(stockOnHandVOVector);
        contentModel.setShowingOverPlus(false);
        contentModel.setShowingBalances(true);
        contentModel.setShowingShortfall(false);
        assertEquals(2, contentModel.getStockOnHand().size());
        assertEquals("111", contentModel.getStockOnHand().get(0).getNumber());
        assertEquals("444", contentModel.getStockOnHand().get(1).getNumber());
        contentModel.setItemViewOrder(ItemViewOrder.BY_NAME);
        assertEquals("444", contentModel.getStockOnHand().get(0).getNumber());
        assertEquals("111", contentModel.getStockOnHand().get(1).getNumber());
        contentModel.setItemViewOrder(ItemViewOrder.BY_NUMBER);
        assertEquals("111", contentModel.getStockOnHand().get(0).getNumber());
        assertEquals("444", contentModel.getStockOnHand().get(1).getNumber());
    }

    @Test
    public void testingShowingOverplus() {
        ContentModel contentModel = new ContentModel();

        contentModel.setStockOnHand(stockOnHandVOVector);
        contentModel.setShowingOverPlus(true);
        contentModel.setShowingBalances(false);
        contentModel.setShowingShortfall(false);
        assertEquals(1, contentModel.getStockOnHand().size());
        assertEquals("222", contentModel.getStockOnHand().get(0).getNumber());
    }

    @Test
    public void testingShowingShortfall() {
        ContentModel contentModel = new ContentModel();

        contentModel.setStockOnHand(stockOnHandVOVector);
        contentModel.setShowingOverPlus(false);
        contentModel.setShowingBalances(false);
        contentModel.setShowingShortfall(true);
        assertEquals(2, contentModel.getStockOnHand().size());
        assertEquals("333", contentModel.getStockOnHand().get(0).getNumber());
        assertEquals("555", contentModel.getStockOnHand().get(1).getNumber());
    }

    static StockOnHandVO createElement(String number, String name, String identity, int nominal, int actual) {
        StockOnHandVO element = new StockOnHandVO();

        element.setNumber(number);
        element.setName(name);
        element.setIdentity(identity);
        element.setNominalQuantity(nominal);
        element.setActualQuantity(actual);

        return element;
    }
}
