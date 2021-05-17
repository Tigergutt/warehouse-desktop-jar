package se.melsom.warehouse.data.vo;

import se.melsom.warehouse.data.entity.ItemEntity;

public class ItemVO {
    private int id = -1;
    private String number = "";
    private String name = "";
    private String packaging = "";
    private String description = "";

    public ItemVO() {}

    public ItemVO(ItemEntity item) {
        this.id = item.getId();
        this.number = item.getNumber();
        this.name = item.getName();
        this.packaging = item.getPackaging();
        this.description = item.getDescription();
    }

    @Override
    public String toString() {
        return String.format("%d|%s|%s|%s|%s", id, number, name, packaging, description);
    }

    public ItemVO(String number, String name, String packaging) {
        this.number = number;
        this.name = name;
        this.packaging = packaging;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isValid() {
        if (number == null || number.length() == 0) {
            return false;
        }

        if (name == null || name.length() == 0) {
            return false;
        }

        return packaging != null && packaging.length() != 0;
    }
}
