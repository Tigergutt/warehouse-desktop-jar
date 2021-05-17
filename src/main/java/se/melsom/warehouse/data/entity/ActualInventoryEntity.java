package se.melsom.warehouse.data.entity;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="actual_inventory")
public class ActualInventoryEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;
    @OneToOne
    @JoinColumn(name = "stock_location_id")
    private StockLocationEntity stockLocation;
    @Column
    private Integer quantity;
    @Column
    private String identity;
    @Column
    private String annotation;
    @UpdateTimestamp
    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    public ActualInventoryEntity() {}

    public ActualInventoryEntity(Integer id, ItemEntity item, StockLocationEntity stockLocation, Integer quantity, String identity, String annotation) {
        this.id = id;
        this.item = item;
        this.stockLocation = stockLocation;
        this.quantity = quantity;
        this.identity = identity;
        this.annotation = annotation;
        this.lastModified = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public StockLocationEntity getStockLocation() {
        return stockLocation;
    }

    public void setStockLocation(StockLocationEntity stockLocation) {
        this.stockLocation = stockLocation;
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
