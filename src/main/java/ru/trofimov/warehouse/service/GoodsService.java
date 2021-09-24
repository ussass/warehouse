package ru.trofimov.warehouse.service;

import ru.trofimov.warehouse.model.Goods;

import java.util.List;

public interface GoodsService {

    Goods findById(Long id);

    List<Goods> findAll();

    Goods save(Goods goods);

    void delete(Long id);

}
