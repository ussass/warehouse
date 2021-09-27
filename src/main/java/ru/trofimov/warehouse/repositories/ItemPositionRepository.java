package ru.trofimov.warehouse.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.trofimov.warehouse.model.ItemPosition;

import java.util.List;

@Repository
public interface ItemPositionRepository extends CrudRepository<ItemPosition, Long> {
    List<ItemPosition> findByGoodsId(Long goodsId);
}
