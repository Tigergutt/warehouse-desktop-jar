package se.melsom.warehouse.settings;

/**
 * The type Property.
 */
public class Property {
	private PersistentSettings parent;
	private String type;
	private String name;
	private String value;

    /**
     * Instantiates a new Property.
     *
     * @param type  the type
     * @param name  the name
     * @param value the value
     */
    public Property(String type, String name, String value) {
		this.type = type;
		this.name = name;
		this.value = value;
	}

    /**
     * Sets parent.
     *
     * @param parent the parent
     */
    public void setParent(PersistentSettings parent) {
		this.parent = parent;
	}

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
		return type;
	}

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return name;
	}

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
		return value;
	}

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(String value) {
		this.value = value;
		
		if (parent != null) {
			parent.setDirty(true);
		}
	}
}
