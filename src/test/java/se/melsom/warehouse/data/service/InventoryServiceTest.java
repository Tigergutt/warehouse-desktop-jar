package se.melsom.warehouse.data.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.melsom.warehouse.data.entity.ActualInventoryEntity;
import se.melsom.warehouse.data.entity.ItemEntity;
import se.melsom.warehouse.data.entity.NominalInventoryEntity;
import se.melsom.warehouse.data.entity.StockLocationEntity;
import se.melsom.warehouse.data.repository.ActualInventoryRepository;
import se.melsom.warehouse.data.repository.ItemRepository;
import se.melsom.warehouse.data.repository.NominalInventoryRepository;
import se.melsom.warehouse.data.repository.StockLocationRepository;
import se.melsom.warehouse.data.vo.StockOnHandVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {InventoryService.class, InventoryServiceImpl.class})
public class InventoryServiceTest {
    @Autowired
    InventoryService service;

    @MockBean
    private ActualInventoryRepository actualInventoryRepository;

    @MockBean
    private NominalInventoryRepository nominalInventoryRepository;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private StockLocationRepository stockLocationRepository;

    @BeforeEach
    public void setUp() {
        List<NominalInventoryEntity> nominal = new ArrayList<NominalInventoryEntity>();
        nominal.add(new NominalInventoryEntity(0, "T",
                new ItemEntity(0, "111", "XXX", "Q", ""),
                "L", 10, "", ""));
        nominal.add(new NominalInventoryEntity(1, "T",
                new ItemEntity(1, "222", "YYY", "W", ""),
                "L", 20, "", ""));
        nominal.add(new NominalInventoryEntity(2, "T",
                new ItemEntity(2, "333", "ZZZ", "R", ""),
                "L", 30, "17", ""));

        List<ActualInventoryEntity> actual = new ArrayList<ActualInventoryEntity>();
        actual.add(new ActualInventoryEntity(0,
                new ItemEntity(0, "111", "XXX", "Q", ""),
                new StockLocationEntity(11, "H", "K", ""),
                100, "", ""));
        actual.add(new ActualInventoryEntity(1,
                new ItemEntity(1, "222", "YYY", "W", ""),
                new StockLocationEntity(13, "I", "L", ""),
                200, "", ""));
        actual.add(new ActualInventoryEntity(2,
                new ItemEntity(2, "333", "ZZZ", "R", ""),
                new StockLocationEntity(17, "J", "M", ""),
                300, "17", ""));


        Mockito.when(actualInventoryRepository.findAll())
                .thenReturn(actual);

        Mockito.when(nominalInventoryRepository.findAll())
                .thenReturn(nominal);
    }

    @Test
    public void testingAutowired() {
        assertNotNull(service);
    }

    @Test
    public void testingGetStockOnHand() {
        Vector<StockOnHandVO> stockAtHand = service.getStockOnHand();
        assertEquals(3, stockAtHand.size());

        StockOnHandVO item = stockAtHand.get(0);
        assertNotNull(item);
        assertEquals("111", item.getNumber());
        assertEquals("XXX", item.getName());
        assertEquals("Q", item.getPackaging());
        assertEquals(10, item.getNominalQuantity());
        assertEquals(100, item.getActualQuantity());
        assertEquals("", item.getIdentity());

        item = stockAtHand.get(1);
        assertNotNull(item);
        assertEquals("222", item.getNumber());
        assertEquals("YYY", item.getName());
        assertEquals("W", item.getPackaging());
        assertEquals(20, item.getNominalQuantity());
        assertEquals(200, item.getActualQuantity());
        assertEquals("", item.getIdentity());

        item = stockAtHand.get(2);
        assertNotNull(item);
        assertEquals("333", item.getNumber());
        assertEquals("ZZZ", item.getName());
        assertEquals("R", item.getPackaging());
        assertEquals(30, item.getNominalQuantity());
        assertEquals(300, item.getActualQuantity());
        assertEquals("17", item.getIdentity());
    }
}
