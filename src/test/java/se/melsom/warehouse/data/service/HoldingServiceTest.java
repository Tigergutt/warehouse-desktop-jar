package se.melsom.warehouse.data.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.melsom.warehouse.data.entity.HoldingEntity;
import se.melsom.warehouse.data.entity.OrganizationalUnitEntity;
import se.melsom.warehouse.data.entity.StockLocationEntity;
import se.melsom.warehouse.data.repository.HoldingRepository;
import se.melsom.warehouse.data.repository.OrganizationalUnitRepository;
import se.melsom.warehouse.data.repository.StockLocationRepository;
import se.melsom.warehouse.data.vo.HoldingVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {HoldingService.class, HoldingServiceImpl.class})
public class HoldingServiceTest {
    @Autowired
    HoldingService service;

    @MockBean
    private HoldingRepository holdingRepository;

    @MockBean
    private OrganizationalUnitRepository unitRepository;

    @MockBean
    private StockLocationRepository stockLocationRepository;

    @BeforeEach
    public void setUp() {
        List<HoldingEntity> holdingsUnit0 = new ArrayList<>();
        holdingsUnit0.add(new HoldingEntity(0, 10));
        holdingsUnit0.add(new HoldingEntity(0, 11));
        holdingsUnit0.add(new HoldingEntity(0, 12));

        Mockito.when(holdingRepository.findByUnit(0))
                .thenReturn(holdingsUnit0);

        List<HoldingEntity> holdingsUnit1 = new ArrayList<>();
        holdingsUnit1.add(new HoldingEntity(1, 15));
        holdingsUnit1.add(new HoldingEntity(1, 16));
        holdingsUnit1.add(new HoldingEntity(1, 17));

        Mockito.when(holdingRepository.findByUnit(1))
                .thenReturn(holdingsUnit1);

        List<HoldingEntity> holdingsUnit2 = new ArrayList<>();
        holdingsUnit2.add(new HoldingEntity(2, 22));
        holdingsUnit2.add(new HoldingEntity(2, 23));
        holdingsUnit2.add(new HoldingEntity(2, 24));

        Mockito.when(holdingRepository.findByUnit(2))
                .thenReturn(holdingsUnit2);

        Mockito.when(unitRepository.findById(0))
                .thenReturn(Optional.of(new OrganizationalUnitEntity(0, "AA", "Unit A", 0, -1)));
        Mockito.when(unitRepository.findById(1))
                .thenReturn(Optional.of(new OrganizationalUnitEntity(1, "BB", "Unit B", 0, -1)));
        Mockito.when(unitRepository.findById(2))
                .thenReturn(Optional.of(new OrganizationalUnitEntity(2, "CC", "Unit C", 0, -1)));

        Mockito.when(stockLocationRepository.findById(10))
                .thenReturn(Optional.of(new StockLocationEntity(10, "A", "1", "")));
        Mockito.when(stockLocationRepository.findById(11))
                .thenReturn(Optional.of(new StockLocationEntity(11, "A", "2", "")));
        Mockito.when(stockLocationRepository.findById(12))
                .thenReturn(Optional.of(new StockLocationEntity(12, "A", "2", "")));
        Mockito.when(stockLocationRepository.findById(15))
                .thenReturn(Optional.of(new StockLocationEntity(15, "B", "1", "")));
        Mockito.when(stockLocationRepository.findById(16))
                .thenReturn(Optional.of(new StockLocationEntity(16, "B", "2", "")));
        Mockito.when(stockLocationRepository.findById(17))
                .thenReturn(Optional.of(new StockLocationEntity(17, "B", "3", "")));
        Mockito.when(stockLocationRepository.findById(22))
                .thenReturn(Optional.of(new StockLocationEntity(22, "C", "1", "")));
        Mockito.when(stockLocationRepository.findById(23))
                .thenReturn(Optional.of(new StockLocationEntity(23, "C", "2", "")));
        Mockito.when(stockLocationRepository.findById(24))
                .thenReturn(Optional.of(new StockLocationEntity(24, "C", "3", "")));
    }

    @Test
    public void testingAutowired() {
        assertNotNull(service);
    }

    @Test
    public void testingFindByUnit() {
        List<HoldingVO> holdings = service.findByUnitId(0);
        assertEquals(3, holdings.size());
        assertEquals("AA", holdings.get(0).getUnit().getCallSign());
        assertEquals("A", holdings.get(0).getLocation().getSection());
        assertEquals("1", holdings.get(0).getLocation().getSlot());

        holdings = service.findByUnitId(1);
        assertEquals(3, holdings.size());
        assertEquals("BB", holdings.get(0).getUnit().getCallSign());
        assertEquals("B", holdings.get(0).getLocation().getSection());
        assertEquals("1", holdings.get(0).getLocation().getSlot());
    }
}
