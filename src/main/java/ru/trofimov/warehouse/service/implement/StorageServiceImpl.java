package ru.trofimov.warehouse.service.implement;

import org.springframework.stereotype.Service;
import ru.trofimov.warehouse.model.Storage;
import ru.trofimov.warehouse.repositories.StorageRepository;
import ru.trofimov.warehouse.service.StorageService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StorageServiceImpl implements StorageService {

    private final StorageRepository itemPositionRepository;

    public StorageServiceImpl(StorageRepository itemPositionRepository) {
        this.itemPositionRepository = itemPositionRepository;
    }

    @Override
    public Storage findById(Long id) {
        return itemPositionRepository.findById(id).get();
    }

    @Override
    public List<Storage> findAll() {
        return StreamSupport.stream(itemPositionRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Storage save(Storage itemPosition) {
        return itemPositionRepository.save(itemPosition);
    }

    @Override
    public void delete(Long id) {
        itemPositionRepository.deleteById(id);
    }

    @Override
    public List<Storage> findByGoodsId(Long id) {
        return itemPositionRepository.findByGoodsId(id);
    }
}
