package se.melsom.warehouse.data.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.melsom.warehouse.data.repository.ActualInventoryRepository;
import se.melsom.warehouse.data.repository.ItemRepository;
import se.melsom.warehouse.data.repository.NominalInventoryRepository;
import se.melsom.warehouse.data.repository.StockLocationRepository;
import se.melsom.warehouse.data.vo.InventoryId;
import se.melsom.warehouse.data.vo.StockOnHandVO;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@Service
public class InventoryServiceImpl implements InventoryService {
    private static final Logger logger = LoggerFactory.getLogger(ActualInventoryServiceImpl.class);

    @Autowired
    ActualInventoryRepository actualInventoryRepository;

    @Autowired
    NominalInventoryRepository nominalInventoryRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    StockLocationRepository stockLocationRepository;

    public InventoryServiceImpl() {
        logger.debug("Execute constructor.");
    }

    @Override
    public Vector<StockOnHandVO> getStockOnHand() {
        Map<InventoryId, StockOnHandVO> stockOnHandEntityMap = new HashMap<>();

        nominalInventoryRepository.findAll().forEach((inventory) -> {
            InventoryId id = new InventoryId(inventory.getItem().getNumber(), inventory.getIdentity());
            StockOnHandVO stockOnHand = new StockOnHandVO();
            stockOnHand.setNumber(inventory.getItem().getNumber());
            stockOnHand.setName(inventory.getItem().getName());
            stockOnHand.setIdentity(inventory.getIdentity());
            stockOnHand.setPackaging(inventory.getItem().getPackaging());
            stockOnHand.setNominalQuantity(inventory.getQuantity());
            stockOnHandEntityMap.put(id, stockOnHand);
        });

        actualInventoryRepository.findAll().forEach((inventory) -> {
            InventoryId id = new InventoryId(inventory.getItem().getNumber(), inventory.getIdentity());
            StockOnHandVO stockOnHand = stockOnHandEntityMap.get(id);

            if (stockOnHand == null) {
                stockOnHand = new StockOnHandVO();
                stockOnHand.setNumber(inventory.getItem().getNumber());
                stockOnHand.setName(inventory.getItem().getName());
                stockOnHand.setIdentity(inventory.getIdentity());
                stockOnHand.setPackaging(inventory.getItem().getPackaging());
                stockOnHandEntityMap.put(id, stockOnHand);
            }

            stockOnHand.setActualQuantity(inventory.getQuantity());
        });

        return new Vector<>(stockOnHandEntityMap.values());
    }
}
