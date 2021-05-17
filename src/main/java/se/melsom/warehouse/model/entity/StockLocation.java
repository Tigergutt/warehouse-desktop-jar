package se.melsom.warehouse.model.entity;

@Deprecated
public class StockLocation implements Comparable<StockLocation> {
	private int id;
	private String section;
	private String slot;
	private String description;

    public StockLocation(int id, String section, String slot, String description) {
		this.id = id;
		this.section = section;
		this.slot = slot;
		this.description = description;
	}

    public int getId() {
		return id;
	}

    public void setId(int value) {
		id = value;
	}

    public String getLocationLabel() {
		return getSection() + getSlot();
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

	@Override
	public int compareTo(StockLocation other) {
		if (section.compareTo(other.section) == 0) {
			return slot.compareTo(other.slot);
		}
		
		return section.compareTo(other.section);
	}

	@Override
	public String toString() {
		return "(" + getId() + "," + getSection() + "," + getSlot() + "," + getDescription() + ")"; 
	}
}
