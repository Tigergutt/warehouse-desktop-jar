package se.melsom.warehouse.data.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import se.melsom.warehouse.data.entity.StockLocationEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql({"classpath:schema.sql", "classpath:test-data.sql"})
public class StockLocationRepositoryTest {
    @Autowired
    StockLocationRepository repository;

    @Test
    public void testAutowired() {
        assertNotNull(repository);
    }

    @Test
    public void testCreate() {
        StockLocationEntity entity = new StockLocationEntity();
        entity.setSection("A1");
        entity.setSlot("1");
        entity.setDescription("Pallplats");
        repository.save(entity);
        Optional<StockLocationEntity> optional = repository.findById(3);
        assertTrue(optional.isPresent());
        entity = optional.get();
        assertEquals("Pallplats", entity.getDescription());
    }

    @Test
    public void testRead() {
        Optional<StockLocationEntity> optional = repository.findById(0);
        assertTrue(optional.isPresent());
        StockLocationEntity entity = optional.get();
        assertEquals("A", entity.getSection());
    }

    @Test
    public void testUpdate() {
        Optional<StockLocationEntity> optional = repository.findById(1);
        assertTrue(optional.isPresent());
        StockLocationEntity entity = optional.get();
        assertEquals("A", entity.getSection());
        entity.setSection("B");
        repository.save(entity);
        optional = repository.findById(1);
        entity = optional.get();
        assertEquals("B", entity.getSection());
    }

    @Test
    public void testDelete() {
        Optional<StockLocationEntity> optional = repository.findById(2);
        assertTrue(optional.isPresent());
        repository.deleteById(2);
        optional = repository.findById(2);
        assertFalse(optional.isPresent());
   }
}
