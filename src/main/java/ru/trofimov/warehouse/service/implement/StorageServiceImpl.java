package ru.trofimov.warehouse.service.implement;

import org.springframework.stereotype.Service;
import ru.trofimov.warehouse.model.Goods;
import ru.trofimov.warehouse.model.Storage;
import ru.trofimov.warehouse.repositories.StorageRepository;
import ru.trofimov.warehouse.service.GoodsService;
import ru.trofimov.warehouse.service.StorageService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StorageServiceImpl implements StorageService {

    private final StorageRepository storageRepository;
    private final GoodsService goodsService;

    public StorageServiceImpl(StorageRepository storageRepository, GoodsService goodsService) {
        this.storageRepository = storageRepository;
        this.goodsService = goodsService;
    }

    @Override
    public Storage findById(Long id) {
        return storageRepository.findById(id).get();
    }

    @Override
    public List<Storage> findAll() {
        return StreamSupport.stream(storageRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Storage save(Storage itemPosition) {
        return storageRepository.save(itemPosition);
    }

    @Override
    public void delete(Long id) {
        storageRepository.deleteById(id);
    }

    @Override
    public List<Storage> findByGoodsId(Long id) {
        return storageRepository.findByGoodsId(id);
    }

    @Override
    public List<Storage> findByCategoryIdAndGoodsId(Long categoryId, Long goodsId) {
        List<Goods> goodsList = goodsService.findByCategoryId(categoryId);
        List<Storage> storageList = storageRepository.findByGoodsId(goodsId);
        List<Storage> result = new ArrayList<>();

        for (Goods goods : goodsList) {
            for (Storage storage : storageList) {
                if (goods.getId() == storage.getGoodsId()) {
                    result.add(storage);
                }
            }
        }

        return result;
    }
}
