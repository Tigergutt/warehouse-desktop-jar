package se.melsom.warehouse.common.selection;

import se.melsom.warehouse.data.vo.ItemVO;

public interface SelectItemListener {
    void handleSelectedItem(ItemVO selectedItem);
}
