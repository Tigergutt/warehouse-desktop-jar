package se.melsom.warehouse.data.service;

import org.springframework.stereotype.Component;
import se.melsom.warehouse.data.vo.UnitVO;

import java.util.Vector;

@Component
public interface OrganizationService {
    Vector<UnitVO> getSuperiorUnits();

    Vector<UnitVO> getSubordinateUnits(String callSign);

    UnitVO getUnit(String callSign);
}
