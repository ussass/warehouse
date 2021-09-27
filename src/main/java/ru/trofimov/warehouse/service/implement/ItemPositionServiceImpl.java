package ru.trofimov.warehouse.service.implement;

import org.springframework.stereotype.Service;
import ru.trofimov.warehouse.model.ItemPosition;
import ru.trofimov.warehouse.repositories.ItemPositionRepository;
import ru.trofimov.warehouse.service.ItemPositionService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ItemPositionServiceImpl implements ItemPositionService {

    private final ItemPositionRepository itemPositionRepository;

    public ItemPositionServiceImpl(ItemPositionRepository itemPositionRepository) {
        this.itemPositionRepository = itemPositionRepository;
    }

    @Override
    public ItemPosition findById(Long id) {
        return itemPositionRepository.findById(id).get();
    }

    @Override
    public List<ItemPosition> findAll() {
        return StreamSupport.stream(itemPositionRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public ItemPosition save(ItemPosition itemPosition) {
        return itemPositionRepository.save(itemPosition);
    }

    @Override
    public void delete(Long id) {
        itemPositionRepository.deleteById(id);
    }

    @Override
    public List<ItemPosition> findByGoodsId(Long id) {
        return itemPositionRepository.findByGoodsId(id);
    }
}
