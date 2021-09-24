package ru.trofimov.warehouse.service;

import ru.trofimov.warehouse.model.Category;

import java.util.List;

public interface CategoryService {

    Category findById(Long id);

    List<Category> findAll();

    Category save(Category category);

    void delete(Long id);
}
