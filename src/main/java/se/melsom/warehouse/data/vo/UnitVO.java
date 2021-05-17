package se.melsom.warehouse.data.vo;

import se.melsom.warehouse.data.entity.OrganizationalUnitEntity;

public class UnitVO {
    private int id;
    private String callSign;
    private String name;
    private int level;
    private int superior;

    public UnitVO() {}

    public UnitVO(OrganizationalUnitEntity entity) {
        this.id = entity.getId();
        this.callSign = entity.getCallSign();
        this.name = entity.getName();
        this.level = entity.getLevel();
        this.superior = entity.getSuperior();
    }

    @Override
    public String toString() {
        return String.format("%d|%s|%s|%d|%d", id, callSign, name, level, superior);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCallSign() {
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSuperior() {
        return superior;
    }

    public void setSuperior(int superior) {
        this.superior = superior;
    }
}
