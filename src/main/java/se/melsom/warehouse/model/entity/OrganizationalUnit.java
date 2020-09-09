package se.melsom.warehouse.model.entity;

import java.util.Vector;

/**
 * The type Organizational unit.
 */
public class OrganizationalUnit {
	private int id;
	private String callsign;
	private String name;
	private int level;
	private OrganizationalUnit superior;
	private Vector<OrganizationalUnit> subordinates = new Vector<>();

    /**
     * Instantiates a new Organizational unit.
     *
     * @param id       the id
     * @param callsign the callsign
     * @param name     the name
     * @param superior the superior
     */
    public OrganizationalUnit(int id, String callsign, String name, OrganizationalUnit superior) {
		this.id = id;
		this.callsign = callsign;
		this.name = name;
		this.superior = superior;
	}

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
		return id;
	}

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
		this.id = id;
	}

    /**
     * Gets callsign.
     *
     * @return the callsign
     */
    public String getCallsign() {
		return callsign;
	}

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return name;
	}

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
		return level;
	}

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
		this.level = level;
	}

    /**
     * Gets superior.
     *
     * @return the superior
     */
    public OrganizationalUnit getSuperior() {
		return superior;
	}

    /**
     * Sets superior.
     *
     * @param superior the superior
     */
    public void setSuperior(OrganizationalUnit superior) {
		this.superior = superior;
	}

    /**
     * Gets subordinates.
     *
     * @return the subordinates
     */
    public Vector<OrganizationalUnit> getSubordinates() {
		return subordinates;
	}

    /**
     * Add subordinate.
     *
     * @param subordinate the subordinate
     */
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
