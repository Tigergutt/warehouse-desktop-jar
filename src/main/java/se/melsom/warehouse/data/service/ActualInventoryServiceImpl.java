package se.melsom.warehouse.data.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.melsom.warehouse.data.repository.ActualInventoryRepository;
import se.melsom.warehouse.data.repository.ItemRepository;
import se.melsom.warehouse.data.repository.StockLocationRepository;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.data.vo.ItemVO;
import se.melsom.warehouse.data.vo.StockLocationVO;

import java.util.Vector;

@Service
public class ActualInventoryServiceImpl implements ActualInventoryService {
    private static final Logger logger = LoggerFactory.getLogger(ActualInventoryServiceImpl.class);

    @Autowired ActualInventoryRepository actualInventoryRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired StockLocationRepository stockLocationRepository;

    @Override
    public Vector<ActualInventoryVO> getActualInventory() {
        Vector<ActualInventoryVO> inventoryVector = new Vector<>();

        actualInventoryRepository.findAll().forEach((inventory) -> {
            ItemVO item = new ItemVO(inventory.getItem());
            StockLocationVO location = new StockLocationVO(inventory.getStockLocation());
            ActualInventoryVO inventoryVO = new ActualInventoryVO(item, location, inventory);

            inventoryVector.add(inventoryVO);
        });

        return inventoryVector;
    }

    @Override
    public Vector<ActualInventoryVO> getActualInventory(String section, String slot) {
        Vector<ActualInventoryVO> inventoryVector = new Vector<>();

        actualInventoryRepository.findAll().forEach((inventory) -> {
            StockLocationVO location = new StockLocationVO(inventory.getStockLocation());

            if (location.getSection().equals(section) && location.getSlot().equals(slot)) {
                ItemVO item = new ItemVO(inventory.getItem());
                ActualInventoryVO inventoryVO = new ActualInventoryVO(item, location, inventory);

                inventoryVector.add(inventoryVO);
            }
        });

        return inventoryVector;
    }

    @Override
    public Vector<ActualInventoryVO> search(String searchKey) {
        Vector<ActualInventoryVO> inventoryVector = new Vector<>();

        itemRepository.findByWildcard(searchKey).forEach((item) -> {
            actualInventoryRepository.findByItem(item).forEach((inventory) -> {
                StockLocationVO locationVO = new StockLocationVO(inventory.getStockLocation());
                ItemVO itemVO = new ItemVO(item);
                ActualInventoryVO inventoryVO = new ActualInventoryVO(itemVO, locationVO, inventory);

                inventoryVector.add(inventoryVO);
            });
        });

        return inventoryVector;
    }
}
