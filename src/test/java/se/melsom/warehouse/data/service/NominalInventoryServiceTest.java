package se.melsom.warehouse.data.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.melsom.warehouse.data.entity.ItemEntity;
import se.melsom.warehouse.data.entity.NominalInventoryEntity;
import se.melsom.warehouse.data.repository.ActualInventoryRepository;
import se.melsom.warehouse.data.repository.ItemRepository;
import se.melsom.warehouse.data.repository.NominalInventoryRepository;
import se.melsom.warehouse.data.vo.NominalInventoryVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {NominalInventoryService.class, NominalInventoryServiceImpl.class})
public class NominalInventoryServiceTest {
    @Autowired
    NominalInventoryService service;

    @MockBean
    private ActualInventoryRepository actualInventoryRepository;

    @MockBean
    private NominalInventoryRepository nominalInventoryRepository;

    @MockBean
    private ItemRepository itemRepository;

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

        Mockito.when(nominalInventoryRepository.findAll())
                .thenReturn(nominal);
    }

    @Test
    public void testingAutowired() {
        assertNotNull(service);
    }

    @Test
    public void testingGetNominalInventory() {
        Vector<NominalInventoryVO> inventory = service.getNominalInventory();
        assertEquals(3, inventory.size());
    }
}
