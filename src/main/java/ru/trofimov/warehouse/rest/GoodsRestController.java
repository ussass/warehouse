package ru.trofimov.warehouse.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.warehouse.model.Goods;
import ru.trofimov.warehouse.model.Storage;
import ru.trofimov.warehouse.service.GoodsService;
import ru.trofimov.warehouse.service.StorageService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories/{categoryId}/goods/")
public class GoodsRestController {
    private final GoodsService goodsService;

    private final StorageService storageService;

    public GoodsRestController(GoodsService goodsService, StorageService storageService) {
        this.goodsService = goodsService;
        this.storageService = storageService;
    }

    @GetMapping
    public ResponseEntity<List<Goods>> getAllGoods(@PathVariable long categoryId){

        List<Goods> goodsList = goodsService.findByCategoryId(categoryId);

        return new ResponseEntity<>(goodsList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Goods> getGoods(@PathVariable long id){

        Goods goods = goodsService.findById(id);

        return new ResponseEntity<>(goods, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Goods> saveGoods(@RequestBody Goods goods){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(goods == null){
            throw new IllegalArgumentException("invalid request body");
        }
        goodsService.save(goods);

        return new ResponseEntity<>(goods, httpHeaders, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Goods> updateGoods(@PathVariable long id, @RequestBody Goods goods){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(goods == null){
            throw new IllegalArgumentException("invalid request body");
        }
        goods.setId(id);
        goodsService.save(goods);

        return new ResponseEntity<>(goods, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Goods> deleteGoods(@PathVariable long id){

        List<Storage> storages = storageService.findByGoodsId(id);
        storages.forEach(storage -> storage.setGoodsId(null));

        goodsService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
