package ru.trofimov.warehouse.service;

import ru.trofimov.warehouse.model.ItemPosition;

import java.util.List;

public interface ItemPositionService {

    ItemPosition findById(Long id);

    List<ItemPosition> findAll();

    ItemPosition save(ItemPosition itemPosition);

    void delete(Long id);

    List<ItemPosition> findByGoodsId(Long id);
}
