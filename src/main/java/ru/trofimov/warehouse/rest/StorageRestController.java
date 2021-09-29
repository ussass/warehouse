package ru.trofimov.warehouse.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.warehouse.model.Storage;
import ru.trofimov.warehouse.service.StorageService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories/{categoryId}/goods/{goodsId}/storage/")
public class StorageRestController {
    private final StorageService storageService;

    public StorageRestController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public ResponseEntity<List<Storage>> getAllStorage(@PathVariable Long categoryId, @PathVariable Long goodsId){
        List<Storage> storage = storageService.findByCategoryIdAndGoodsId(categoryId, goodsId);

        if (storage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(storage, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Storage> getStorage(@PathVariable Long id){
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Storage storage = storageService.findById(id);

        if (storage == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(storage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Storage> saveStorage(@RequestBody Storage storage){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(storage == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        storageService.save(storage);

        return new ResponseEntity<>(storage, httpHeaders, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Storage> updateStorage(@PathVariable Long id, @RequestBody Storage storage){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(storage == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        storage.setId(id);
        storageService.save(storage);

        return new ResponseEntity<>(storage, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Storage> deleteStorage(@PathVariable Long id){
        Storage storage = storageService.findById(id);
        if (storage == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        storageService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
