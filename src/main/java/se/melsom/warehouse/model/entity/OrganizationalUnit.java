package se.melsom.warehouse.model.entity;

import java.util.Vector;

public class OrganizationalUnit {
	private int id;
	private String callsign;
	private String name;
	private int level;
	private OrganizationalUnit superior;
	private Vector<OrganizationalUnit> subordinates = new Vector<>();
	
	public OrganizationalUnit(int id, String callsign, String name, OrganizationalUnit superior) {
		this.id = id;
		this.callsign = callsign;
		this.name = name;
		this.superior = superior;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCallsign() {
		return callsign;
	}
	
	public String getName() {
		return name;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public OrganizationalUnit getSuperior() {
		return superior;
	}
	
	public void setSuperior(OrganizationalUnit superior) {
		this.superior = superior;
	}
	
	public Vector<OrganizationalUnit> getSubordinates() {
		return subordinates;
	}
	
	public void addSubordinate(OrganizationalUnit subordinate) {
		subordinates.addElement(subordinate);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof OrganizationalUnit)) {
			return false;
		}
		
		OrganizationalUnit other = (OrganizationalUnit) object;
		
		if (this.id != other.id) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
