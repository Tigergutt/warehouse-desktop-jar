package se.melsom.warehouse.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.melsom.warehouse.data.entity.HoldingEntity;
import se.melsom.warehouse.data.entity.HoldingId;

import java.util.List;

@Repository
public interface HoldingRepository extends CrudRepository<HoldingEntity, HoldingId> {
    List<HoldingEntity> findByUnit(Integer unit);
}
