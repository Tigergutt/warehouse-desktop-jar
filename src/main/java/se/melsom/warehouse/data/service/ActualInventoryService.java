package se.melsom.warehouse.data.service;

import org.springframework.stereotype.Component;
import se.melsom.warehouse.data.vo.ActualInventoryVO;

import java.util.Vector;

@Component
public interface ActualInventoryService {
    Vector<ActualInventoryVO> getActualInventory();
    Vector<ActualInventoryVO> getActualInventory(String section, String slot);
    Vector<ActualInventoryVO> search(String searchKey);
}
