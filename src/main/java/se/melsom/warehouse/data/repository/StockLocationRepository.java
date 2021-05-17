package se.melsom.warehouse.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.melsom.warehouse.data.entity.StockLocationEntity;

@Repository
public interface StockLocationRepository extends CrudRepository<StockLocationEntity, Integer> {
}
