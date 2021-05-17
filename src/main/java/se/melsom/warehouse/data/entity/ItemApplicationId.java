package se.melsom.warehouse.data.entity;

import java.io.Serializable;

public class ItemApplicationId implements Serializable {
    private Integer unit;
    private Integer item;
    private String category;

    public ItemApplicationId() {}

    public ItemApplicationId(int unit, int item, String category) {
        this.unit = unit;
        this.item = item;
        this.category = category;
    }

    public Integer getUnit() {
        return unit;
    }

    public Integer getItem() {
        return item;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public int hashCode() {
        return unit.hashCode() * item.hashCode() * category.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ItemApplicationId)) {
            return false;
        }

        ItemApplicationId other = (ItemApplicationId) object;

        if (!unit.equals(other.unit)) {
            return false;
        }

        if (!item.equals(other.item)) {
            return false;
        }

        return category.equals(other.category);
    }
}