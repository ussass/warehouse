package ru.trofimov.warehouse.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.trofimov.warehouse.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
