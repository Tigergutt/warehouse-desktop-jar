package se.melsom.warehouse.data.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import se.melsom.warehouse.data.entity.HoldingEntity;
import se.melsom.warehouse.data.entity.HoldingId;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql({"classpath:schema.sql", "classpath:test-data.sql"})
public class HoldingRepositoryTest {
    @Autowired
    private HoldingRepository repository;

    @Test
    public void testAutowired() {
        assertNotNull(repository);
    }

    @Test
    public void testCreate() {
        HoldingEntity entity = new HoldingEntity();
        entity.setUnit(47);
        entity.setStockLocation(17);
        repository.save(entity);
        HoldingId id = new HoldingId(47, 17);
        Optional<HoldingEntity> optional = repository.findById(id);
        assertTrue(optional.isPresent());
        entity = optional.get();
        assertEquals(id.getUnit(), entity.getUnit());
        assertEquals(id.getStockLocation(), entity.getStockLocation());
    }

    @Test
    public void testRead()
    {
        HoldingId id = new HoldingId(0,94);
        Optional<HoldingEntity> optional = repository.findById(id);
        assertTrue(optional.isPresent());
        List<HoldingEntity> holdings = repository.findByUnit(1);
        assertEquals(2, holdings.size());
        HoldingEntity a = holdings.get(0);
        HoldingEntity b = holdings.get(1);
        int sum = a.getStockLocation() + b.getStockLocation();
        assertEquals(203, sum);
    }

    @Test
    public void testDelete() {
        Optional<HoldingEntity> optional = repository.findById(new HoldingId(0,100));
        assertTrue(optional.isPresent());
        repository.deleteById(new HoldingId(0,100));
        optional = repository.findById(new HoldingId(0,100));
        assertFalse(optional.isPresent());
    }
}
