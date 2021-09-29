package ru.trofimov.warehouse.service;

import ru.trofimov.warehouse.model.Storage;

import java.util.List;

public interface StorageService {

    Storage findById(Long id);

    List<Storage> findAll();

    Storage save(Storage itemPosition);

    void delete(Long id);

    List<Storage> findByGoodsId(Long id);

    List<Storage> findByCategoryIdAndGoodsId(Long categoryId, Long goodsId);
}
