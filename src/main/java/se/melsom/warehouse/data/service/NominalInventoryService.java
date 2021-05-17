package se.melsom.warehouse.data.service;

import org.springframework.stereotype.Component;
import se.melsom.warehouse.data.vo.NominalInventoryVO;

import java.util.Vector;

@Component
public interface NominalInventoryService {
    Vector<NominalInventoryVO> getNominalInventory();

    void addInventory(NominalInventoryVO inventoryVO);

    void updateInventory(NominalInventoryVO inventoryVO);

    void removeInventory(NominalInventoryVO inventoryVO);
}
