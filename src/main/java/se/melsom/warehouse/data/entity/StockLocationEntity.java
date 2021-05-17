package se.melsom.warehouse.data.entity;

import javax.persistence.*;

@Entity
@Table(name="stock_locations")
public class StockLocationEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String section;
	@Column(nullable = false)
	private String slot;
	@Column(nullable = false)
	private String description;

	public StockLocationEntity() {
	}

	public StockLocationEntity(Integer id, String section, String slot, String description) {
		this.id = id;
		this.section = section;
		this.slot = slot;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

    public void setId(Integer value) {
		id = value;
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
}
