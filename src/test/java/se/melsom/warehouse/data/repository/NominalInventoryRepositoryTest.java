package se.melsom.warehouse.data.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import se.melsom.warehouse.data.entity.ItemEntity;
import se.melsom.warehouse.data.entity.NominalInventoryEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql({"classpath:schema.sql", "classpath:test-data.sql"})
public class NominalInventoryRepositoryTest {
    @Autowired
    NominalInventoryRepository repository;

    @Test
    public void testAutowired() {
        assertNotNull(repository);
    }

    @Test
    public void testCreate() {
        Optional<NominalInventoryEntity> optional = repository.findById(3);
        assertFalse(optional.isPresent());
        NominalInventoryEntity entity = new NominalInventoryEntity();
        entity.setItem(new ItemEntity(7, "A", "X", "", ""));
        entity.setSource("AAA");
        entity.setStockPoint("Birsta");
        entity.setIdentity("007");
        entity.setQuantity(1);
        entity.setAnnotation("Annotation");
        repository.save(entity);
        entity = null;
        optional = repository.findById(3);
        assertTrue(optional.isPresent());
        entity = optional.get();
        assertEquals(new Integer(7), entity.getItem().getId());
        assertEquals("A", entity.getItem().getNumber());
        assertEquals("X", entity.getItem().getName());
        assertEquals("AAA", entity.getSource());
        assertEquals("Birsta", entity.getStockPoint());
        assertEquals("007", entity.getIdentity());
        assertEquals(new Integer(1), entity.getQuantity());
        assertEquals("Annotation", entity.getAnnotation());
    }

    @Test
    public void testRead() {
        Optional<NominalInventoryEntity> optional = repository.findById(0);
        assertTrue(optional.isPresent());
        NominalInventoryEntity entity = optional.get();
        assertEquals("F1449-000174", entity.getItem().getNumber());
        assertEquals("HJÄLPRAM", entity.getItem().getName());
        assertEquals("A0JG", entity.getSource());
        assertEquals("Kompaniförråd", entity.getStockPoint());
        assertEquals("ZXY123", entity.getIdentity());
        assertEquals(new Integer(1), entity.getQuantity());
        assertEquals("Utlånad", entity.getAnnotation());
    }

    @Test
    public void testUpdate() {
        NominalInventoryEntity entity = new NominalInventoryEntity();
        entity.setId(1);
        entity.setItem(new ItemEntity(4, "123", "PRYL", "ST", "TEST"));
        entity.setSource("BBB");
        entity.setStockPoint("TEST");
        entity.setIdentity("");
        entity.setQuantity(0);
        entity.setAnnotation("Updated");
        repository.save(entity);
        Optional<NominalInventoryEntity> optional = repository.findById(1);
        assertTrue(optional.isPresent());
        entity = optional.get();
        assertEquals(new Integer(4), entity.getItem().getId());
        assertEquals("F5944-000388", entity.getItem().getNumber());
        assertEquals("HÖGTALARE", entity.getItem().getName());
        assertEquals("BBB", entity.getSource());
        assertEquals("TEST", entity.getStockPoint());
        assertEquals("", entity.getIdentity());
        assertEquals(new Integer(0), entity.getQuantity());
        assertEquals("Updated", entity.getAnnotation());
    }

    @Test
    public void testDelete() {
        Optional<NominalInventoryEntity> optional = repository.findById(2);
        assertTrue(optional.isPresent());
        repository.deleteById(2);
        optional = repository.findById(2);
        assertFalse(optional.isPresent());
    }
}