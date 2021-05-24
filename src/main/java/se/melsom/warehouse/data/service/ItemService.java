package se.melsom.warehouse.data.service;

import org.springframework.stereotype.Component;
import se.melsom.warehouse.data.vo.ItemVO;

import java.util.Collection;
import java.util.Vector;

@Component
public interface ItemService {
    Vector<ItemVO> getItems();

    void updateItem(ItemVO item);

    void addItem(ItemVO item);

    void removeItem(ItemVO item);

    ItemVO getItem(String number);

    Collection<String> getPackaging();
}
