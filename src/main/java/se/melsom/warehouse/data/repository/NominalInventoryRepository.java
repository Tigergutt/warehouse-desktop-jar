package se.melsom.warehouse.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.melsom.warehouse.data.entity.NominalInventoryEntity;

@Repository
public interface NominalInventoryRepository extends CrudRepository<NominalInventoryEntity, Integer> {
}
