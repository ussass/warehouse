package ru.trofimov.warehouse.rest.implement;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.warehouse.exception.InvalidRequestBodyException;
import ru.trofimov.warehouse.model.Info;
import ru.trofimov.warehouse.model.Storage;
import ru.trofimov.warehouse.rest.StorageRestController;
import ru.trofimov.warehouse.service.StorageService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories/{categoryId}/goods/{goodsId}/storage/")
public class StorageRestControllerImpl implements StorageRestController {

    private final long DEFAULT_LIMIT = 50;

    private final StorageService storageService;

    public StorageRestControllerImpl(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    @GetMapping
    public ResponseEntity<Info<Storage>> getAllStorage(HttpServletRequest request,
                                                       @RequestParam(defaultValue = "0") long offset,
                                                       @RequestParam(defaultValue = "" + DEFAULT_LIMIT) long limit,
                                                       @PathVariable long categoryId, @PathVariable long goodsId) {

        List<Storage> storages = storageService.findByCategoryIdAndGoodsId(categoryId, goodsId);
        long fullSize = storages.size();
        storages = storages.stream().skip(offset).limit(limit).collect(Collectors.toList());

        Info<Storage> info = new Info<>(offset, limit, storages, fullSize, request.getRequestURL().toString());

        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<Storage> getStorage(@PathVariable long id) {

        Storage storage = storageService.findById(id);

        return new ResponseEntity<>(storage, HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<Storage> saveStorage(HttpServletRequest request, @RequestBody Storage storage) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (storage.getGoodsId() == null) {
            throw new InvalidRequestBodyException("invalid request body");
        }
        storageService.save(storage);
        httpHeaders.add("Location", request.getRequestURL().toString() + storage.getId());

        return new ResponseEntity<>(storage, httpHeaders, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("{id}")
    public ResponseEntity<Storage> updateStorage(@PathVariable long id, @RequestBody Storage storage) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (storage.getGoodsId() == null) {
            throw new InvalidRequestBodyException("invalid request body");
        }
        storage.setId(id);
        storageService.save(storage);

        return new ResponseEntity<>(storage, httpHeaders, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<Storage> deleteStorage(@PathVariable long id) {

        storageService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
