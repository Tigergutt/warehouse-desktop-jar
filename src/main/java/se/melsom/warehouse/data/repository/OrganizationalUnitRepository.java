package se.melsom.warehouse.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.melsom.warehouse.data.entity.OrganizationalUnitEntity;

@Repository
public interface OrganizationalUnitRepository extends CrudRepository<OrganizationalUnitEntity, Integer> {
    OrganizationalUnitEntity findByCallSign(String callSign);

    @Query("SELECT ou FROM OrganizationalUnitEntity ou WHERE superior = :superior")
    Iterable<OrganizationalUnitEntity> findBySuperiorId(@Param("superior") Integer id);
}
