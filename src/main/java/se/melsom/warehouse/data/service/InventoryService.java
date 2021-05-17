package se.melsom.warehouse.data.service;

import org.springframework.stereotype.Component;
import se.melsom.warehouse.data.vo.StockOnHandVO;

import java.util.Vector;

@Component
public interface InventoryService {
    Vector<StockOnHandVO> getStockOnHand();
}
