package se.melsom.warehouse.data.entity;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="master_inventory")
public class NominalInventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String source;
    @OneToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;
    @Column(name = "stock_point")
    private String stockPoint;
    @Column
    private Integer quantity;
    @Column
    private String identity;
    @Column
    private String annotation;
    @UpdateTimestamp
    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    public NominalInventoryEntity() {}

    public NominalInventoryEntity(String source, ItemEntity item, String stockPoint, Integer quantity, String identity, String annotation) {
        this.source = source;
        this.item = item;
        this.stockPoint = stockPoint;
        this.identity = identity;
        this.quantity = quantity;
        this.annotation = annotation;
        this.lastModified = LocalDateTime.now();
    }

    public NominalInventoryEntity(Integer id, String source, ItemEntity item, String stockPoint, Integer quantity, String identity, String annotation) {
        this.id = id;
        this.source = source;
        this.item = item;
        this.stockPoint = stockPoint;
        this.identity = identity;
        this.quantity = quantity;
        this.annotation = annotation;
        this.lastModified = LocalDateTime.now();
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

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public String getStockPoint() {
        return stockPoint;
    }

    public void setStockPoint(String stockPoint) {
        this.stockPoint = stockPoint;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
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
}
