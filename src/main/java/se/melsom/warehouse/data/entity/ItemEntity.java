package se.melsom.warehouse.data.entity;

import javax.persistence.*;

@Entity
@Table(name="items")
public class ItemEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String number;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String packaging;
	@Column(nullable = false)
	private String description;

	public ItemEntity() {}

	public ItemEntity(String number, String name, String packaging, String description) {
		this.number = number;
		this.name = name;
		this.packaging = packaging;
		this.description = description;
	}

	public ItemEntity(Integer id, String number, String name, String packaging, String description) {
		this.id = id;
		this.number = number;
		this.name = name;
		this.packaging = packaging;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

    public void setId(Integer id) {
		this.id = id;
	}

    public String getNumber() {
		return number;
	}

    public void setNumber(String value) {
		number = value;
	}

    public String getName() {
		return name;
	}

    public void setName(String value) {
		name = value;
	}

    public String getPackaging() {
		return packaging;
	}

    public void setPackaging(String value) {
		packaging = value;
	}

    public String getDescription() {
		return description;
	}

    public void setDescription(String description) {
		this.description = description;
	}
}
