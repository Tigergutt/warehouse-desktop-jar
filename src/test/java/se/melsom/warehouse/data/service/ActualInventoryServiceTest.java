package se.melsom.warehouse.data.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.melsom.warehouse.data.entity.ActualInventoryEntity;
import se.melsom.warehouse.data.entity.ItemEntity;
import se.melsom.warehouse.data.entity.StockLocationEntity;
import se.melsom.warehouse.data.repository.ActualInventoryRepository;
import se.melsom.warehouse.data.repository.ItemRepository;
import se.melsom.warehouse.data.repository.StockLocationRepository;
import se.melsom.warehouse.data.vo.ActualInventoryVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {ActualInventoryService.class, ActualInventoryServiceImpl.class})
public class ActualInventoryServiceTest {
    @Autowired
    ActualInventoryService service;

    @MockBean
    private ActualInventoryRepository actualInventoryRepository;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private StockLocationRepository stockLocationRepository;

    @BeforeEach
    public void setUp() {
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
    }

    @Test
    public void testingAutowired() {
        assertNotNull(service);
    }

    @Test
    public void testingGetActualInventory() {
        Vector<ActualInventoryVO> inventory = service.getActualInventory();
        assertEquals(3, inventory.size());
    }
}
