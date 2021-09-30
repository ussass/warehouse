package ru.trofimov.warehouse.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.warehouse.model.Info;
import ru.trofimov.warehouse.model.Storage;
import ru.trofimov.warehouse.service.StorageService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories/{categoryId}/goods/{goodsId}/storage/")
public class StorageRestController {
    private final StorageService storageService;

    public StorageRestController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public ResponseEntity<Info<Storage>> getAllStorage(HttpServletRequest request,
                                                       @RequestParam(defaultValue = "0") long offset,
                                                       @RequestParam(defaultValue = "" + Long.MAX_VALUE) long limit,
                                                       @PathVariable long categoryId, @PathVariable long goodsId){

        List<Storage> storages = storageService.findByCategoryIdAndGoodsId(categoryId, goodsId);
        long fullSize = storages.size();
        storages = storages.stream().skip(offset).limit(limit).collect(Collectors.toList());

        Info<Storage> info = new Info<>(offset, limit, storages, fullSize, request.getRequestURL().toString());

        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Storage> getStorage(@PathVariable long id){

        Storage storage = storageService.findById(id);

        return new ResponseEntity<>(storage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Storage> saveStorage(@RequestBody Storage storage){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(storage == null){
            throw new IllegalArgumentException("invalid request body");
        }
        storageService.save(storage);

        return new ResponseEntity<>(storage, httpHeaders, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Storage> updateStorage(@PathVariable long id, @RequestBody Storage storage){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(storage == null){
            throw new IllegalArgumentException("invalid request body");
        }
        storage.setId(id);
        storageService.save(storage);

        return new ResponseEntity<>(storage, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Storage> deleteStorage(@PathVariable long id){

        storageService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
