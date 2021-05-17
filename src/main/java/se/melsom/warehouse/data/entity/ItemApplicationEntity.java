package se.melsom.warehouse.data.entity;

import javax.persistence.*;

@Entity
@Table(name="item_application")
@IdClass(ItemApplicationId.class)
public class ItemApplicationEntity {
	@Id
	// TODO: @OneToOne
	@Column(name = "unit_id")
	private Integer unit;
	@Id
	// TODO: @OneToOne
	@Column(name = "item_id")
	private Integer item;
	@Id
	private String category;
	@Column(nullable = false)
	private Integer quantity;

	public ItemApplicationEntity() {
	}

    public Integer getUnit() {
		return unit;
	}

    public void setUnit(Integer unit) {
		this.unit = unit;
	}

    public Integer getItem() {
		return item;
	}

    public void setItem(Integer item) {
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
}
