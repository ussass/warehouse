package ru.trofimov.warehouse.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.trofimov.warehouse.model.Goods;

import java.util.List;

@Repository
public interface GoodsRepository extends CrudRepository<Goods, Long> {
    List<Goods> findByCategoryId(Long categoryId);
}
