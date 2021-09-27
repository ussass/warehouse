package ru.trofimov.warehouse.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trofimov.warehouse.model.ItemPosition;
import ru.trofimov.warehouse.service.ItemPositionService;

import java.util.List;

@RestController
@RequestMapping("/api/item-position")
public class ItemPositionRestController {
    private final ItemPositionService itemPositionService;

    public ItemPositionRestController(ItemPositionService itemPositionService) {
        this.itemPositionService = itemPositionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPosition> getItemPosition(@PathVariable Long id){
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ItemPosition itemPosition = itemPositionService.findById(id);

        if (itemPosition == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(itemPosition, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemPosition> saveItemPosition(@RequestBody ItemPosition itemPosition){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(itemPosition == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        itemPositionService.save(itemPosition);

        return new ResponseEntity<>(itemPosition, httpHeaders, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<ItemPosition> updateItemPosition(@RequestBody ItemPosition itemPosition){
        HttpHeaders httpHeaders = new HttpHeaders();

        if(itemPosition == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        itemPositionService.save(itemPosition);

        return new ResponseEntity<>(itemPosition, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ItemPosition> deleteItemPosition(@PathVariable Long id){
        ItemPosition itemPosition = itemPositionService.findById(id);
        if (itemPosition == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        itemPositionService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("")
    public ResponseEntity<List<ItemPosition>> getAllItemPositions(){
        List<ItemPosition> itemPositions = itemPositionService.findAll();

        if (itemPositions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(itemPositions, HttpStatus.OK);
    }

}
