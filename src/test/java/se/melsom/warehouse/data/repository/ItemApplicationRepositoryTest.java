package se.melsom.warehouse.data.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import se.melsom.warehouse.data.entity.ItemApplicationEntity;
import se.melsom.warehouse.data.entity.ItemApplicationId;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql({"classpath:schema.sql", "classpath:test-data.sql"})
public class ItemApplicationRepositoryTest {
    @Autowired
    private ItemApplicationRepository repository;

    @Test
    public void testAutowired() {
        assertNotNull(repository);
    }

    @Test
    public void testCreate() {
        ItemApplicationEntity entity = new ItemApplicationEntity();
        entity.setUnit(17);
        entity.setItem(11);
        entity.setCategory("Q");
        entity.setQuantity(47);
        repository.save(entity);
        Optional<ItemApplicationEntity> optional = repository.findById(new ItemApplicationId(17, 11, "Q"));
        assertTrue(optional.isPresent());
    }

    @Test
    public void testRead() {
        Optional<ItemApplicationEntity> optional = repository.findById(new ItemApplicationId(0,1,"B"));
        assertTrue(optional.isPresent());
    }

    @Test
    public void testUpdate() {
        Optional<ItemApplicationEntity> optional = repository.findById(new ItemApplicationId(0,1,"A"));
        assertTrue(optional.isPresent());
        ItemApplicationEntity entity = optional.get();
        assertEquals(new Integer(3), entity.getQuantity());
        entity.setQuantity(666);
        repository.save(entity);
        optional = repository.findById(new ItemApplicationId(0,1,"A"));
        entity = optional.get();
        assertEquals(new Integer(666), entity.getQuantity());
    }

    @Test
    public void testDelete() {
        Optional<ItemApplicationEntity> optional = repository.findById(new ItemApplicationId(0,1,"C"));
        assertTrue(optional.isPresent());
        repository.deleteById(new ItemApplicationId(0,1,"C"));
        optional = repository.findById(new ItemApplicationId(0,1,"C"));
        assertFalse(optional.isPresent());
    }
}
