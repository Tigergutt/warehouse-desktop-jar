package se.melsom.warehouse.data.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import se.melsom.warehouse.data.entity.ActualInventoryEntity;
import se.melsom.warehouse.data.entity.ItemEntity;
import se.melsom.warehouse.data.entity.StockLocationEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql({"classpath:schema.sql", "classpath:test-data.sql"})
public class ActualInventoryRepositoryTest {
    @Autowired
    ActualInventoryRepository repository;

    @Test
    public void testAutowired() {
        assertNotNull(repository);
    }

    @Test
    public void testCreate() {
        Optional<ActualInventoryEntity> optional = repository.findById(3);
        assertFalse(optional.isPresent());
        ActualInventoryEntity entity = new ActualInventoryEntity();
        entity.setItem(new ItemEntity(7, "", "", "", ""));
        entity.setStockLocation(new StockLocationEntity(47, "", "", ""));
        entity.setIdentity("007");
        entity.setQuantity(1);
        entity.setAnnotation("Annotation");
        repository.save(entity);
        optional = repository.findById(3);
        assertTrue(optional.isPresent());
        entity = optional.get();
        assertEquals(new Integer(7), entity.getItem().getId());
        assertEquals(new Integer(47), entity.getStockLocation().getId());
        assertEquals("007", entity.getIdentity());
        assertEquals(new Integer(1), entity.getQuantity());
        assertEquals("Annotation", entity.getAnnotation());
    }

    @Test
    public void testRead() {
        Optional<ActualInventoryEntity> optional = repository.findById(0);
        assertTrue(optional.isPresent());
        ActualInventoryEntity entity = optional.get();
        assertEquals("F1449-000174", entity.getItem().getNumber());
        assertEquals("HJÄLPRAM", entity.getItem().getName());
        assertEquals("123456", entity.getIdentity());
        assertEquals(new Integer(1), entity.getQuantity());
        assertEquals("Kommentar", entity.getAnnotation());
    }

    @Test
    public void testUpdate() {
        ActualInventoryEntity entity = new ActualInventoryEntity();
        entity.setId(1);
        entity.setItem(new ItemEntity(4, "", "", "", ""));
        entity.setStockLocation(new StockLocationEntity(2, "", "", ""));
        entity.setIdentity("");
        entity.setQuantity(0);
        entity.setAnnotation("Updated");
        repository.save(entity);
        Optional<ActualInventoryEntity> optional = repository.findById(1);
        assertTrue(optional.isPresent());
        entity = optional.get();
        assertEquals(new Integer(4), entity.getItem().getId());
        assertEquals("F5944-000388", entity.getItem().getNumber());
        assertEquals("HÖGTALARE", entity.getItem().getName());
        assertEquals("A", entity.getStockLocation().getSection());
        assertEquals("3", entity.getStockLocation().getSlot());
        assertEquals("", entity.getIdentity());
        assertEquals(new Integer(0), entity.getQuantity());
        assertEquals("Updated", entity.getAnnotation());
    }

    @Test
    public void testDelete() {
        Optional<ActualInventoryEntity> optional = repository.findById(2);
        assertTrue(optional.isPresent());
        repository.deleteById(2);
        optional = repository.findById(2);
        assertFalse(optional.isPresent());
    }
}
