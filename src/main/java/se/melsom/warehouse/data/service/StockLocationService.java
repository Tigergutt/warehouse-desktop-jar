package se.melsom.warehouse.data.service;

import org.springframework.stereotype.Component;
import se.melsom.warehouse.data.vo.StockLocationVO;

import java.util.Vector;

@Component
public interface StockLocationService {
    Vector<StockLocationVO> getStockLocations();
}
