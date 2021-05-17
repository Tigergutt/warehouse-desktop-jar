package se.melsom.warehouse.data.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import se.melsom.warehouse.data.entity.ItemEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql({"classpath:schema.sql", "classpath:test-data.sql"})
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository repository;

    @Test
    public void testAutowired() {
        assertNotNull(repository);
    }

    @Test
    public void testCreate() {
        ItemEntity entity = new ItemEntity();
        entity.setNumber("666");
        entity.setName("Legion");
        entity.setPackaging("Fire");
        entity.setDescription("What?");
        repository.save(entity);
        entity = repository.findByNumber("666");
        assertEquals("Legion", entity.getName());
    }

    @Test
    public void testRead()
    {
        Optional<ItemEntity> result = repository.findById(0);
        assertTrue(result.isPresent());
        ItemEntity entity = result.get();
        assertEquals("F1449-000174", entity.getNumber());
    }

    @Test
    public void testUpdate() {
        Optional<ItemEntity> result = repository.findById(0);
        assertTrue(result.isPresent());
        ItemEntity entity = result.get();
        entity.setNumber("ZZZ-000");
        entity.setDescription("New description");
        repository.save(entity);
        result = repository.findById(0);
        assertTrue(result.isPresent());
        entity = result.get();
        assertEquals("ZZZ-000", entity.getNumber());
        assertEquals(new Integer(0), entity.getId());
    }

    @Test
    public void testDelete() {
        assertTrue(repository.existsById(1));
        repository.deleteById(1);
        assertFalse(repository.existsById(1));
    }
}
