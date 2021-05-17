package se.melsom.warehouse.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.melsom.warehouse.data.repository.ItemRepository;
import se.melsom.warehouse.data.repository.NominalInventoryRepository;
import se.melsom.warehouse.data.vo.ItemVO;
import se.melsom.warehouse.data.vo.NominalInventoryVO;

import java.util.Vector;

@Service
public class NominalInventoryServiceImpl implements NominalInventoryService {

    @Autowired NominalInventoryRepository nominalInventoryRepository;
    @Autowired ItemRepository itemRepository;

    @Override
    public Vector<NominalInventoryVO> getNominalInventory() {
        Vector<NominalInventoryVO> inventoryVector = new Vector<>();

        nominalInventoryRepository.findAll().forEach((inventory) -> {
            ItemVO item = new ItemVO(inventory.getItem());
            NominalInventoryVO inventoryVO = new NominalInventoryVO(item, inventory);
            inventoryVector.add(inventoryVO);
        });

        return inventoryVector;
    }

    @Override
    public void addInventory(NominalInventoryVO inventoryVO) {

    }

    @Override
    public void updateInventory(NominalInventoryVO inventoryVO) {

    }

    @Override
    public void removeInventory(NominalInventoryVO inventoryVO) {

    }
}
