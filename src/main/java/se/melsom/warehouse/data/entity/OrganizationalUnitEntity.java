package se.melsom.warehouse.data.entity;

import javax.persistence.*;

@Entity
@Table(name="organizational_units")
public class OrganizationalUnitEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "callsign", nullable = false)
	private String callSign;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private Integer level;
	@Column(name = "superior_unit_id", nullable = false)
	private Integer superior;

	public OrganizationalUnitEntity() {}

	public OrganizationalUnitEntity(Integer id, String callSign, String name, Integer level, Integer superior) {
		this.id = id;
		this.callSign = callSign;
		this.name = name;
		this.level = level;
		this.superior = superior;
	}

    public Integer getId() {
		return id;
	}

    public void setId(Integer id) {
		this.id = id;
	}

    public String getCallSign() {
		return callSign;
	}

	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public Integer getLevel() {
		return level;
	}

    public void setLevel(Integer level) {
		this.level = level;
	}

    public Integer getSuperior() {
		return superior;
	}

    public void setSuperior(Integer superior) {
		this.superior = superior;
	}
}
