package se.melsom.warehouse.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.melsom.warehouse.data.entity.ItemEntity;

@Repository
public interface ItemRepository extends CrudRepository<ItemEntity, Integer> {
    ItemEntity findByNumber(String number);

    @Query("SELECT i FROM ItemEntity i WHERE name LIKE %:key% OR number LIKE %:key%")
    Iterable<ItemEntity> findByWildcard(@Param("key") String wildcard);
}
