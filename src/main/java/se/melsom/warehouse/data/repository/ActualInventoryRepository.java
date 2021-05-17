package se.melsom.warehouse.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.melsom.warehouse.data.entity.ActualInventoryEntity;
import se.melsom.warehouse.data.entity.ItemEntity;

@Repository
public interface ActualInventoryRepository extends CrudRepository<ActualInventoryEntity, Integer> {
    Iterable<ActualInventoryEntity> findByItem(ItemEntity item);
}
