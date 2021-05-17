package se.melsom.warehouse.data.vo;

import se.melsom.warehouse.data.entity.StockLocationEntity;

public class StockLocationVO {
    private int id;
    private String section;
    private String slot;
    private String description;

    public StockLocationVO() {}

    public StockLocationVO(StockLocationEntity entity) {
        this.id = entity.getId();
        this.section = entity.getSection();
        this.slot = entity.getSlot();
        this.description = entity.getSection();
    }

    @Override
    public String toString() {
        return String.format("%d|%s|%s|%s", id, section, slot, description);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String value) {
        section = value;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String value) {
        slot = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        description = value;
    }

    public String getLocationLabel() {
        return String.format("%s:%s", getSection(), getSlot());
    }
}
