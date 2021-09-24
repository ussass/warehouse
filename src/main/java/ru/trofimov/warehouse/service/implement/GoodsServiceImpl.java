package ru.trofimov.warehouse.service.implement;

import org.springframework.stereotype.Service;
import ru.trofimov.warehouse.model.Goods;
import ru.trofimov.warehouse.repositories.GoodsRepository;
import ru.trofimov.warehouse.service.GoodsService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository goodsRepository;

    public GoodsServiceImpl(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @Override
    public Goods findById(Long id) {
        return goodsRepository.findById(id).get();
    }

    @Override
    public List<Goods> findAll() {
        return StreamSupport.stream(goodsRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Goods save(Goods goods) {
        return goodsRepository.save(goods);
    }

    @Override
    public void delete(Long id) {
        goodsRepository.deleteById(id);
    }
}
