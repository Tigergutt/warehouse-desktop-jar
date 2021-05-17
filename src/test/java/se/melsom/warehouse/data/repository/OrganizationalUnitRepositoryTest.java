package se.melsom.warehouse.data.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import se.melsom.warehouse.data.entity.OrganizationalUnitEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql({"classpath:schema.sql", "classpath:test-data.sql"})
public class OrganizationalUnitRepositoryTest {
    @Autowired
    OrganizationalUnitRepository repository;

    @Test
    public void testAutowired() {
        assertNotNull(repository);
    }

    @Test
    public void testCreate() {
        OrganizationalUnitEntity entity = new OrganizationalUnitEntity();
        entity.setName("BlackOps");
        entity.setCallSign("BO");
        entity.setLevel(0);
        entity.setSuperior(-1);
        repository.save(entity);
        Optional<OrganizationalUnitEntity> optional = repository.findById(6);
        assertTrue(optional.isPresent());
        entity = optional.get();
        assertEquals("BlackOps", entity.getName());
    }

    @Test
    public void testRead()
    {
        Optional<OrganizationalUnitEntity> optional = repository.findById(0);
        assertTrue(optional.isPresent());
        OrganizationalUnitEntity entity = optional.get();
        assertEquals("RG", entity.getCallSign());
    }

    @Test
    public void testUpdate() {
        Optional<OrganizationalUnitEntity> optional = repository.findById(5);
        assertTrue(optional.isPresent());
        OrganizationalUnitEntity entity = optional.get();
        entity.setCallSign("QO");
        repository.save(entity);
        optional = repository.findById(5);
        entity = optional.get();
        assertEquals("QO", entity.getCallSign());
    }

    @Test
    public void testDelete() {
        Optional<OrganizationalUnitEntity> optional = repository.findById(1);
        assertTrue(optional.isPresent());
        repository.delete(optional.get());
        optional = repository.findById(1);
        assertFalse(optional.isPresent());
    }
}
