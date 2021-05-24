package se.melsom.warehouse.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.melsom.warehouse.data.entity.HoldingEntity;
import se.melsom.warehouse.data.entity.OrganizationalUnitEntity;
import se.melsom.warehouse.data.entity.StockLocationEntity;
import se.melsom.warehouse.data.repository.HoldingRepository;
import se.melsom.warehouse.data.repository.OrganizationalUnitRepository;
import se.melsom.warehouse.data.repository.StockLocationRepository;
import se.melsom.warehouse.data.vo.HoldingVO;
import se.melsom.warehouse.data.vo.StockLocationVO;
import se.melsom.warehouse.data.vo.UnitVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HoldingServiceImpl implements HoldingService {
    @Autowired HoldingRepository holdingRepository;
    @Autowired OrganizationalUnitRepository unitRepository;
    @Autowired StockLocationRepository stockLocationRepository;

    @Override
    public List<HoldingVO> findByUnitId(int unit) {
        List<HoldingVO> holdings = new ArrayList<>();

        for (HoldingEntity entity : holdingRepository.findByUnit(unit)) {
            Optional<OrganizationalUnitEntity> optionalUnit = unitRepository.findById(entity.getUnit());
            assert optionalUnit.isPresent();
            Optional<StockLocationEntity> optionalLocation = stockLocationRepository.findById(entity.getStockLocation());
            assert optionalLocation.isPresent();
            holdings.add(new HoldingVO(new UnitVO(optionalUnit.get()), new StockLocationVO(optionalLocation.get())));
        }

        return holdings;
    }

    @Override
    public HoldingVO findByStockLocation(StockLocationVO stockLocation) {
        HoldingEntity entity = holdingRepository.findByStockLocation(stockLocation.getId());

        if (entity != null) {
            Optional<OrganizationalUnitEntity> optionalUnit = unitRepository.findById(entity.getUnit());
            assert optionalUnit.isPresent();
            Optional<StockLocationEntity> optionalLocation = stockLocationRepository.findById(entity.getStockLocation());
            assert optionalLocation.isPresent();
            return new HoldingVO(new UnitVO(optionalUnit.get()), new StockLocationVO(optionalLocation.get()));
        }

        return null;
    }
}
