package se.melsom.warehouse.data.vo;

import se.melsom.warehouse.data.entity.ActualInventoryEntity;

import java.time.LocalDateTime;

public class ActualInventoryVO {
    private int id;
    private ItemVO item;
    private StockLocationVO stockLocation;
    private int quantity;
    private String identity;
    private String annotation;
    private LocalDateTime lastModified;

    public ActualInventoryVO() {}

    public ActualInventoryVO(ItemVO item, StockLocationVO stockLocation, ActualInventoryEntity entity) {
        this.id = entity.getId();
        this.item = item;
        this.stockLocation = stockLocation;
        this.quantity = entity.getQuantity();
        this.identity = entity.getIdentity();
        this.annotation = entity.getAnnotation();
        this.lastModified = entity.getLastModified();
    }

    @Override
    public String toString() {
        return String.format("%d|%s|%s|%d|%s|%s|%s", id, item, stockLocation, quantity, identity, annotation, lastModified);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemVO getItem() {
        return item;
    }

    public void setItem(ItemVO item) {
        this.item = item;
    }

    public StockLocationVO getStockLocation() {
        return stockLocation;
    }

    public void setStockLocation(StockLocationVO stockLocation) {
        this.stockLocation = stockLocation;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public boolean isValid() {
        return true;
    }
}
