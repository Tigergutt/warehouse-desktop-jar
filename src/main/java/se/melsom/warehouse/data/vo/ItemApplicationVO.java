package se.melsom.warehouse.data.vo;

import se.melsom.warehouse.data.entity.ItemApplicationId;

import javax.persistence.*;

public class ItemApplicationVO {
	private UnitVO unit;
	private ItemVO item;
	private String category;
	private Integer quantity;

	public ItemApplicationVO() {
	}

	public ItemApplicationVO(UnitVO unit) {
		this.unit = unit;
	}

	public ItemApplicationVO(ItemApplicationVO other) {
		this.unit = other.unit;
		this.item = other.item;
		this.category = other.category;
		this.quantity = other.quantity;
	}

	public UnitVO getUnit() {
		return unit;
	}

    public void setUnit(UnitVO unit) {
		this.unit = unit;
	}

    public ItemVO getItem() {
		return item;
	}

    public void setItem(ItemVO item) {
		this.item = item;
	}

    public String getCategory() {
		return category;
	}

    public void setCategory(String category) {
		this.category = category;
	}

    public Integer getQuantity() {
		return quantity;
	}

    public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public boolean isValid() {
		if (getUnit() == null) {
			return false;
		}

		if (getItem() == null) {
			return false;
		}

		if (category == null || category.length() == 0) {
			return false;
		}

		return quantity >= 0;
	}

}
