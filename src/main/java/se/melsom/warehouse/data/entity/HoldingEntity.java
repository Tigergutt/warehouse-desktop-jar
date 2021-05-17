package se.melsom.warehouse.data.entity;

import javax.persistence.*;

@Entity
@Table(name="holdings")
@IdClass(HoldingId.class)
public class HoldingEntity {
	@Id
	@Column(name = "unit_id")
	private Integer unit;
	@Id
	@Column(name = "stock_location_id")
	private Integer stockLocation;

    public HoldingEntity() {
	}

	public HoldingEntity(Integer unit, Integer stockLocation) {
    	this.unit = unit;
    	this.stockLocation = stockLocation;
	}

	public Integer getUnit() {
		return unit;
	}

    public void setUnit(Integer value) {
		unit = value;
	}

    public Integer getStockLocation() {
		return stockLocation;
	}

    public void setStockLocation(Integer value) {
		stockLocation = value;
	}
}
