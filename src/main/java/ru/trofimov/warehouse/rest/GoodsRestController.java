package ru.trofimov.warehouse.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.warehouse.model.Goods;
import ru.trofimov.warehouse.model.ItemPosition;
import ru.trofimov.warehouse.service.GoodsService;
import ru.trofimov.warehouse.service.ItemPositionService;

import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class GoodsRestController {
    private final GoodsService goodsService;

    private final ItemPositionService itemPositionService;

    public GoodsRestController(GoodsService goodsService, ItemPositionService itemPositionService) {
        this.goodsService = goodsService;
        this.itemPositionService = itemPositionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goods> getGoods(@PathVariable Long id){
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Goods goods = goodsService.findById(id);

        if (goods == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(goods, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Goods> saveGoods(@RequestBody Goods goods){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(goods == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        goodsService.save(goods);

        return new ResponseEntity<>(goods, httpHeaders, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Goods> updateGoods(@RequestBody Goods goods){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(goods == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        goodsService.save(goods);

        return new ResponseEntity<>(goods, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Goods> deleteGoods(@PathVariable Long id){
        Goods goods = goodsService.findById(id);
        if (goods == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<ItemPosition> itemPositions = itemPositionService.findByGoodsId(id);
        itemPositions.forEach(System.out::println);
        itemPositions.forEach(itemPosition -> itemPosition.setGoodsId(null));
        itemPositions.forEach(System.out::println);


        goodsService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("")
    public ResponseEntity<List<Goods>> getAllGoods(){
        List<Goods> goodsList = goodsService.findAll();

        if (goodsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(goodsList, HttpStatus.OK);
    }
}
