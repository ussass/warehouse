package ru.trofimov.warehouse.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.trofimov.warehouse.model.ItemPosition;

@Repository
public interface ItemPositionRepository extends CrudRepository<ItemPosition, Long> {
}
