package ru.trofimov.warehouse.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.warehouse.model.Category;
import ru.trofimov.warehouse.model.Goods;
import ru.trofimov.warehouse.service.CategoryService;
import ru.trofimov.warehouse.service.GoodsService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories/")
public class CategoryRestController {

    private final CategoryService categoryService;

    private final GoodsService goodsService;

    public CategoryRestController(CategoryService categoryService, GoodsService goodsService) {
        this.categoryService = categoryService;
        this.goodsService = goodsService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategory(@RequestParam(defaultValue = "0") long offset,
                                                         @RequestParam(defaultValue = "" + Long.MAX_VALUE) long limit){

        List<Category> categories = categoryService.findAll();
        categories = categories.stream().skip(offset).limit(limit).collect(Collectors.toList());


        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Category> getCategory(@PathVariable long id){

        Category category = categoryService.findById(id);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> saveCategory(@RequestBody Category category){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(category == null){
            throw new IllegalArgumentException("invalid request body");
        }
        categoryService.save(category);

        return new ResponseEntity<>(category, httpHeaders, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable long id, @RequestBody Category category){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(category == null){
            throw new IllegalArgumentException("invalid request body");
        }
        category.setId(id);
        categoryService.save(category);

        return new ResponseEntity<>(category, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable long id){

        List<Goods> goodsList = goodsService.findByCategoryId(id);
        goodsList.forEach(goods -> goods.setCategoryId(null));

        categoryService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
