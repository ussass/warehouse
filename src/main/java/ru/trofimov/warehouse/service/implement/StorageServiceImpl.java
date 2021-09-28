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

    private final StorageRepository storageRepository;

    public StorageServiceImpl(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
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
    public Storage save(Storage storage) {
        return storageRepository.save(storage);
    }

    @Override
    public void delete(Long id) {
        storageRepository.deleteById(id);
    }
}
