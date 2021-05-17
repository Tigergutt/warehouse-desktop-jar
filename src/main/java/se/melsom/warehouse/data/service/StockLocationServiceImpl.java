package se.melsom.warehouse.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.melsom.warehouse.data.repository.StockLocationRepository;
import se.melsom.warehouse.data.vo.StockLocationVO;

import java.util.Vector;

@Service
public class StockLocationServiceImpl implements  StockLocationService {
    @Autowired StockLocationRepository repository;

    @Override
    public Vector<StockLocationVO> getStockLocations() {
        Vector<StockLocationVO> stockLocationVector = new Vector<>();

        repository.findAll().forEach((stockLocationEntity) -> {
            StockLocationVO stockLocationVO = new StockLocationVO(stockLocationEntity);

            stockLocationVector.add(stockLocationVO);
        });

        return stockLocationVector;
    }
}
