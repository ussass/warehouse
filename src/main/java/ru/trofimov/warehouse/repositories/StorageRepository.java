package ru.trofimov.warehouse.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.trofimov.warehouse.model.Storage;

@Repository
public interface StorageRepository extends CrudRepository<Storage, Long> {
}
