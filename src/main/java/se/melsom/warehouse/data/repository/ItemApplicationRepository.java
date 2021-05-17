package se.melsom.warehouse.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.melsom.warehouse.data.entity.ItemApplicationEntity;
import se.melsom.warehouse.data.entity.ItemApplicationId;

@Repository
public interface ItemApplicationRepository extends CrudRepository<ItemApplicationEntity, ItemApplicationId> {
}
