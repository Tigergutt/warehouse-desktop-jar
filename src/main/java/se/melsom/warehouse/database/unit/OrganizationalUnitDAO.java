package se.melsom.warehouse.database.unit;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.OrganizationalUnit;

/**
 * The Organizational unit data access object.
 */
public class OrganizationalUnitDAO {
	private int id = EntityName.NULL_ID;
	private String callSign = "";
	private String name = "";
	private int level = 0;
	private int superiorId = EntityName.NULL_ID;

    /**
     * Instantiates a new Organizational unit dao.
     *
     * @param unit the unit
     */
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

    /**
     * Instantiates a new Organizational unit dao.
     */
    public OrganizationalUnitDAO() {
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
		return callSign;
	}

    /**
     * Sets call sign.
     *
     * @param callSign the call sign
     */
    public void setCallSign(String callSign) {
		this.callSign = callSign;
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
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
		this.name = name;
	}

    /**
     * Gets superior id.
     *
     * @return the superior id
     */
    public int getSuperiorId() {
		return superiorId;
	}

    /**
     * Sets superior id.
     *
     * @param superiorId the superior id
     */
    public void setSuperiorId(int superiorId) {
		this.superiorId = superiorId;
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

	@Override
	public String toString() {
		return "(" + getId() + "," + getCallsign() + "," + getName() + "," + getLevel() + "," + getSuperiorId() + ")";
	}
}
