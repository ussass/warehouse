package ru.trofimov.warehouse.rest.implement;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.warehouse.exception.InvalidRequestBodyException;
import ru.trofimov.warehouse.model.Goods;
import ru.trofimov.warehouse.model.Info;
import ru.trofimov.warehouse.model.Storage;
import ru.trofimov.warehouse.rest.GoodsRestController;
import ru.trofimov.warehouse.service.GoodsService;
import ru.trofimov.warehouse.service.StorageService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories/{categoryId}/goods/")
public class GoodsRestControllerImpl implements GoodsRestController {

    private final long DEFAULT_LIMIT = 50;

    private final GoodsService goodsService;

    private final StorageService storageService;

    public GoodsRestControllerImpl(GoodsService goodsService, StorageService storageService) {
        this.goodsService = goodsService;
        this.storageService = storageService;
    }

    @Override
    @GetMapping
    public ResponseEntity<Info<Goods>> getAllGoods(HttpServletRequest request,
                                                   @RequestParam(defaultValue = "0") long offset,
                                                   @RequestParam(defaultValue = "" + DEFAULT_LIMIT) long limit,
                                                   @PathVariable long categoryId) {

        List<Goods> goodsList = goodsService.findByCategoryId(categoryId);
        long fullSize = goodsList.size();
        goodsList = goodsList.stream().skip(offset).limit(limit).collect(Collectors.toList());

        Info<Goods> info = new Info<>(offset, limit, goodsList, fullSize, request.getRequestURL().toString());

        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<Goods> getGoods(@PathVariable long id) {

        Goods goods = goodsService.findById(id);

        return new ResponseEntity<>(goods, HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<Goods> saveGoods(HttpServletRequest request, @RequestBody Goods goods) {
        HttpHeaders httpHeaders = new HttpHeaders();
        System.out.println("goods.toString() = " + goods.toString());

        if (goods.getName() == null || goods.getDescription() == null || goods.getCategoryId() == null) {
            throw new InvalidRequestBodyException("invalid request body");
        }
        goodsService.save(goods);
        httpHeaders.add("Location", request.getRequestURL().toString() + goods.getId());

        return new ResponseEntity<>(goods, httpHeaders, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("{id}")
    public ResponseEntity<Goods> updateGoods(@PathVariable long id, @RequestBody Goods goods) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (goods.getName() == null || goods.getDescription() == null || goods.getCategoryId() == null) {
            throw new InvalidRequestBodyException("invalid request body");
        }
        goods.setId(id);
        goodsService.save(goods);

        return new ResponseEntity<>(goods, httpHeaders, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<Goods> deleteGoods(@PathVariable long id) {

        List<Storage> storages = storageService.findByGoodsId(id);
        storages.forEach(storage -> storage.setGoodsId(null));

        goodsService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
