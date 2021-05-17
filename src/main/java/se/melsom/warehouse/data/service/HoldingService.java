package se.melsom.warehouse.data.service;

import org.springframework.stereotype.Component;
import se.melsom.warehouse.data.vo.HoldingVO;

import java.util.List;

@Component
public interface HoldingService {
    List<HoldingVO> findByUnitId(int unit);
}
