package se.melsom.warehouse.data.vo;

import se.melsom.warehouse.data.entity.NominalInventoryEntity;

import java.time.LocalDateTime;

public class NominalInventoryVO {
    private int id;
    private String source;
    private ItemVO item;
    private String stockPoint;
    private int quantity;
    private String identity;
    private String annotation;
    private LocalDateTime lastModified;

    public NominalInventoryVO() {}

    public NominalInventoryVO(ItemVO item, NominalInventoryEntity entity) {
        this.id = entity.getId();
        this.source = entity.getSource();
        this.item = item;
        this.stockPoint = entity.getStockPoint();
        this.identity = entity.getIdentity();
        this.quantity = entity.getQuantity();
        this.annotation = entity.getAnnotation();
        this.lastModified = entity.getLastModified();
    }

    @Override
    public String toString() {
        return String.format("%d|%s|%s|%s|%d|%s|%s|%s", id, source, item, stockPoint, quantity, identity, annotation, lastModified);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ItemVO getItem() {
        return item;
    }

    public void setItem(ItemVO item) {
        this.item = item;
    }

    public String getStockPoint() {
        return stockPoint;
    }

    public void setStockPoint(String stockPoint) {
        this.stockPoint = stockPoint;
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
        if (getItem() == null) {
            return false;
        }

        return getSource() != null;
    }
}
