package se.melsom.warehouse.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.melsom.warehouse.data.entity.OrganizationalUnitEntity;
import se.melsom.warehouse.data.repository.OrganizationalUnitRepository;
import se.melsom.warehouse.data.vo.UnitVO;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired OrganizationalUnitRepository repository;

    @Override
    public Vector<UnitVO> getSuperiorUnits() {
        Set<Integer> superiors = new TreeSet<>();

        repository.findAll().forEach((unit) -> {
            superiors.add(unit.getSuperior());
        });

        Vector<UnitVO> units = new Vector<>();

        for (int id : superiors) {
            Optional<OrganizationalUnitEntity> superior = repository.findById(id);

            if (superior.isPresent()) {
                units.add(new UnitVO(superior.get()));
            }
        }

        return units;
    }

    @Override
    public Vector<UnitVO> getSubordinateUnits(String callSign) {
        Vector<UnitVO> units = new Vector<>();
        OrganizationalUnitEntity superior = repository.findByCallSign(callSign);

        if (superior != null) {
            repository.findBySuperiorId(superior.getId()).forEach((unit) -> {
                units.add(new UnitVO(unit));
            });
        }

        return units;
    }

    @Override
    public UnitVO getUnit(String callSign) {
        OrganizationalUnitEntity unit = repository.findByCallSign(callSign);

        return new UnitVO(unit);
    }
}
