package se.melsom.warehouse.edit.item;

import se.melsom.warehouse.data.vo.ItemVO;

public interface EditItemListener {
    void save(ItemVO item);
}
