package se.melsom.warehouse.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.melsom.warehouse.data.entity.ItemEntity;
import se.melsom.warehouse.data.repository.ItemRepository;
import se.melsom.warehouse.data.vo.ItemVO;
import se.melsom.warehouse.model.enumeration.Packaging;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired ItemRepository itemRepository;

    @Override
    public Vector<ItemVO> getItems() {
        Vector<ItemVO> items = new Vector<>();

        itemRepository.findAll().forEach((item) -> {
            items.add(new ItemVO(item));
        });

        return items;
    }

    @Override
    public void updateItem(ItemVO itemVO) {
        ItemEntity item = new ItemEntity();

        item.setId(itemVO.getId());
        item.setNumber(itemVO.getNumber());
        item.setName(itemVO.getName());
        item.setPackaging(itemVO.getPackaging());
        item.setDescription(itemVO.getDescription());

        itemRepository.save(item);
    }

    @Override
    public void addItem(ItemVO itemVO) {
        ItemEntity item = new ItemEntity();

        item.setNumber(itemVO.getNumber());
        item.setName(itemVO.getName());
        item.setPackaging(itemVO.getPackaging());
        item.setDescription(itemVO.getDescription());

        itemRepository.save(item);
    }

    @Override
    public void removeItem(ItemVO itemVO) {
        ItemEntity item = new ItemEntity();

        item.setId(itemVO.getId());
        item.setNumber(itemVO.getNumber());
        item.setName(itemVO.getName());
        item.setPackaging(itemVO.getPackaging());
        item.setDescription(itemVO.getDescription());

        itemRepository.delete(item);
    }

    @Override
    public ItemVO getItem(String number) {
        ItemEntity entity = itemRepository.findByNumber(number);

        if (entity == null) {
            return null;
        }

        return new ItemVO(entity);
    }

    @Override
    public Collection<String> getPackaging() {
        Set<String> packagings = new TreeSet<>();

        for (Packaging packaging : Packaging.values()) {
            packagings.add(packaging.getName());
        }

        return packagings;
    }
}
