package ru.trofimov.warehouse.rest.implement;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.warehouse.exception.InvalidRequestBodyException;
import ru.trofimov.warehouse.model.Category;
import ru.trofimov.warehouse.model.Goods;
import ru.trofimov.warehouse.model.Info;
import ru.trofimov.warehouse.rest.CategoryRestController;
import ru.trofimov.warehouse.service.CategoryService;
import ru.trofimov.warehouse.service.GoodsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories/")
public class CategoryRestControllerImpl implements CategoryRestController {

    private final long DEFAULT_LIMIT = 50;

    private final CategoryService categoryService;

    private final GoodsService goodsService;

    public CategoryRestControllerImpl(CategoryService categoryService, GoodsService goodsService) {
        this.categoryService = categoryService;
        this.goodsService = goodsService;
    }

    @Override
    @GetMapping
    public ResponseEntity<Info<Category>> getAllCategory(HttpServletRequest request,
                                                         @RequestParam(defaultValue = "0") long offset,
                                                         @RequestParam(defaultValue = "" + DEFAULT_LIMIT) long limit) {

        List<Category> categories = categoryService.findAll();
        long fullSize = categories.size();
        categories = categories.stream().skip(offset).limit(limit).collect(Collectors.toList());

        Info<Category> info = new Info<>(offset, limit, categories, fullSize, request.getRequestURL().toString());

        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<Category> getCategory(@PathVariable long id) {

        Category category = categoryService.findById(id);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<Category> saveCategory(HttpServletRequest request, @RequestBody Category category) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (category.getName() == null) {
            throw new InvalidRequestBodyException("invalid request body");
        }
        categoryService.save(category);
        httpHeaders.add("Location", request.getRequestURL().toString() + category.getId());

        return new ResponseEntity<>(category, httpHeaders, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable long id, @RequestBody Category category) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (category.getName() == null) {
            throw new InvalidRequestBodyException("invalid request body");
        }
        category.setId(id);
        categoryService.save(category);

        return new ResponseEntity<>(category, httpHeaders, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable long id) {

        List<Goods> goodsList = goodsService.findByCategoryId(id);
        goodsList.forEach(goods -> goods.setCategoryId(null));

        categoryService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
