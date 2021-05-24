package se.melsom.warehouse.data.vo;

import se.melsom.warehouse.data.entity.HoldingEntity;

public class HoldingVO {
    private UnitVO unit;
    private StockLocationVO location;

    public HoldingVO() {}

    public HoldingVO(UnitVO unit, StockLocationVO location) {
        this.unit = unit;
        this.location = location;
    }

    @Override
    public String toString() {
        return String.format("%s|%s", unit, location);
    }

    public UnitVO getUnit() {
        return unit;
    }

    public void setUnit(UnitVO unit) {
        this.unit = unit;
    }

    public StockLocationVO getLocation() {
        return location;
    }

    public void setLocation(StockLocationVO location) {
        this.location = location;
    }
}
