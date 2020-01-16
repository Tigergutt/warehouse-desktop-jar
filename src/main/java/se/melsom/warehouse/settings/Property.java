package se.melsom.warehouse.settings;

public class Property {
	private PersistentSettings parent;
	private String type;
	private String name;
	private String value;
	
	public Property(String type, String name, String value) {
		this.type = type;
		this.name = name;
		this.value = value;
	}

	public void setParent(PersistentSettings parent) {
		this.parent = parent;
	}
	
	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
		
		if (parent != null) {
			parent.setDirty(true);
		}
	}
}
