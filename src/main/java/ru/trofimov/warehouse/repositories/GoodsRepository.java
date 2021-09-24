package ru.trofimov.warehouse.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.trofimov.warehouse.model.Goods;

@Repository
public interface GoodsRepository extends CrudRepository<Goods, Long> {
}
