package se.melsom.warehouse.data.entity;

import java.io.Serializable;

public class HoldingId implements Serializable {
    private Integer unit;
    private Integer stockLocation;

    public HoldingId() {}

    public HoldingId(int unit, int stockLocation) {
        this.unit = unit;
        this.stockLocation = stockLocation;
    }

    public Integer getUnit() {
        return unit;
    }

    public Integer getStockLocation() {
        return stockLocation;
    }

    @Override
    public int hashCode() {
        return unit.hashCode() * stockLocation.hashCode();
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof HoldingId)) {
            return false;
        }

        HoldingId other = (HoldingId) object;

        if (!unit.equals(other.unit)) {
            return false;
        }

        return stockLocation.equals(other.stockLocation);
    }
}
