package se.melsom.warehouse.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.database.WarehouseDatabase;
import se.melsom.warehouse.database.unit.OrganizationalUnitDAO;
import se.melsom.warehouse.event.EventType;
import se.melsom.warehouse.event.ModelEvent;
import se.melsom.warehouse.event.ModelEventBroker;
import se.melsom.warehouse.model.entity.OrganizationalUnit;

public class UnitsMasterFile {
	private static Logger logger = Logger.getLogger(UnitsMasterFile.class);
	
	private WarehouseDatabase database;
	private ModelEventBroker eventBroker;
	private Map<Integer, OrganizationalUnit> unitList = new HashMap<>();

	public UnitsMasterFile(WarehouseDatabase database, ModelEventBroker eventBroker) {
		this.database = database;
		this.eventBroker = eventBroker;
	}

	public int getNextUnitId() {
		return unitList.size();
	}
	
	public Vector<OrganizationalUnit> getUnits() {
		Vector<OrganizationalUnit> units = new Vector<>();
		
		int level = 0;
		for (OrganizationalUnit aUnit : unitList.values()) {
			level = Math.max(level, aUnit.getLevel());
			units.addElement(aUnit);
		}
		
		final int maxLevel = level;
		
		Collections.sort(units, new Comparator<OrganizationalUnit>(){

			@Override
			public int compare(OrganizationalUnit left, OrganizationalUnit right) {
				int result = (maxLevel - left.getLevel()) - (maxLevel - right.getLevel());
				
				if (result == 0) {
					result = left.getCallsign().compareTo(right.getCallsign());
				}
				
				return result;
			}
		});
		
		return units;
	}
	
	public Vector<OrganizationalUnit> getTopLevelUnits() {
		logger.debug("Get top level units");
		Vector<OrganizationalUnit> units = new Vector<>();
		OrganizationalUnit topUnit = null;
		
		for (OrganizationalUnit aUnit : unitList.values()) {
			logger.trace(aUnit);
			if (aUnit.getSuperior() == null) {
				topUnit = aUnit;
				logger.trace("Top unit=" + aUnit);
				units.addElement(aUnit);
				break;
			}
		}
		
		if (topUnit != null) {
			for (OrganizationalUnit aUnit : unitList.values()) {
				if (aUnit.getSuperior() != null && aUnit.getSuperior().equals(topUnit)) {
					units.addElement(aUnit);
				}
			}
		}
		
		logger.debug("Unit count=" + units.size());
		return units;
	}
	
	public Vector<OrganizationalUnit> getSubUnits(String superiorCallSign) {
		Vector<OrganizationalUnit> units = new Vector<>();

		for (OrganizationalUnit aUnit : unitList.values()) {
			if (aUnit.getSuperior() != null && aUnit.getSuperior().getCallsign().equals(superiorCallSign)) {
				units.addElement(aUnit);
			}
		}

		return units;
	}
	
	public OrganizationalUnit getUnit(int withId) {
		OrganizationalUnit unit = unitList.get(withId);
		
		if (unit == null) {
			logger.warn("No unit for id=" + withId);
		}
		
		return unit;
	}
	
	public OrganizationalUnit getUnit(String withCallsign) {
		for (OrganizationalUnit unit : unitList.values()) {
			if (unit.getCallsign().equals(withCallsign)) {
				return unit;
			}
		}
		
		return null;
	}

	public boolean addUnit(OrganizationalUnit newUnit) {
		logger.trace("Add unit=" + newUnit);
		if (getUnit(newUnit.getCallsign()) != null) {
			logger.error("Could not add duplicate unit=" + newUnit);
			return false;
		}

		OrganizationalUnit superior = null;

		if (newUnit.getSuperior() != null) {
			superior = unitList.get(newUnit.getSuperior().getId());
			
			if (superior == null) {
				for (OrganizationalUnit unit : unitList.values()) {
					if (unit.getCallsign().equals(newUnit.getSuperior().getCallsign())) {
						superior = unit;
						break;
					}
				}
			}
		}
		
		newUnit.setSuperior(superior);

		unitList.put(newUnit.getId(), newUnit);
		database.insertOrganizationalUnit(newUnit);
		return true;
	}
	
	public void updateUnit(OrganizationalUnit aUnit) {
		logger.trace("Update unit=" + aUnit);
		unitList.put(aUnit.getId(), aUnit);
		database.updateOrganizationalUnit(aUnit);
	}

	public void removeUnit(OrganizationalUnit aUnit) {
		logger.trace("Remove unit=" + aUnit);
		
		unitList.remove(aUnit.getId());
		database.deleteOrganizationalUnit(aUnit);
	}
	
	void retreiveUnitList() {
		unitList.clear();
		
		logger.debug("Retreiving unit list.");
		Vector<OrganizationalUnitDAO> unitDataAcessObjects = database.selectOrganizationalUnits(null, null);
		
		for (OrganizationalUnitDAO dao : unitDataAcessObjects) {
			logger.trace(dao);
			int unitId = dao.getId();
			String callsign = dao.getCallsign();
			String name = dao.getName();
			OrganizationalUnit unit = new OrganizationalUnit(unitId, callsign, name, null);
			
			unit.setLevel(dao.getLevel());
			
			unitList.put(unit.getId(), unit);
		}
		
		for (OrganizationalUnitDAO dao : unitDataAcessObjects) {
			if (dao.getSuperiorId() < 0) {
				continue;
			}
			
			OrganizationalUnit unit = unitList.get(dao.getId());
			
			if (unit != null) {
				OrganizationalUnit superior = unitList.get(dao.getSuperiorId());

				unit.setSuperior(superior);
				superior.addSubordinate(unit);
			}
		}

		notifyObservers(new ModelEvent(EventType.ORGANIZATIONAL_UNITS_RELOADED));
	}
	
	private void notifyObservers(ModelEvent event) {
		if (eventBroker == null) {
			logger.error("Event broker is null.");
			return;
		}
		
		eventBroker.send(event);
	}
	
}
