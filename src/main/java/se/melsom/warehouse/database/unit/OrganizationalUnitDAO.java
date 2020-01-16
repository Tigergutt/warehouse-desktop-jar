package se.melsom.warehouse.database.unit;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.OrganizationalUnit;

public class OrganizationalUnitDAO {
	private int id = EntityName.NULL_ID;
	private String callSign = "";
	private String name = "";
	private int level = 0;
	private int superiorId = EntityName.NULL_ID;
	
	public OrganizationalUnitDAO(OrganizationalUnit unit) {
		this.id = unit.getId();
		this.callSign = unit.getCallsign();
		this.name = unit.getName();
		this.level = unit.getLevel();
		
		if (unit.getSuperior() != null && unit.getSuperior().getId() != EntityName.NULL_ID) {
			this.superiorId = unit.getSuperior().getId();
		} else {
			this.superiorId = EntityName.NULL_ID;
		}
	}

	public OrganizationalUnitDAO() {
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getCallsign() {
		return callSign;
	}
	
	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getSuperiorId() {
		return superiorId;
	}
	
	public void setSuperiorId(int superiorId) {
		this.superiorId = superiorId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "(" + getId() + "," + getCallsign() + "," + getName() + "," + getLevel() + "," + getSuperiorId() + ")";
	}
}
