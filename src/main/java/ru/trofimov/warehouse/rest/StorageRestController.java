package ru.trofimov.warehouse.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.warehouse.model.Storage;
import ru.trofimov.warehouse.service.StorageService;

import java.util.List;

@RestController
@RequestMapping("/api/storage")
public class StorageRestController {
    private final StorageService storageService;

    public StorageRestController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/{id}")
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

    @PostMapping("")
    public ResponseEntity<Storage> saveStorage(@RequestBody Storage storage){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(storage == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        storageService.save(storage);

        return new ResponseEntity<>(storage, httpHeaders, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Storage> updateStorage(@RequestBody Storage storage){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(storage == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        storageService.save(storage);

        return new ResponseEntity<>(storage, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Storage> deleteStorage(@PathVariable Long id){
        Storage storage = storageService.findById(id);
        if (storage == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        storageService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("")
    public ResponseEntity<List<Storage>> getAllStorage(){
        List<Storage> storage = storageService.findAll();

        if (storage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(storage, HttpStatus.OK);
    }
}
