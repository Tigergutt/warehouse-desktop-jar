package se.melsom.warehouse.model.entity;

/**
 * The type Item.
 */
public class Item implements Comparable<Item> {
	private int id;
	private String number;
	private String name;
	private String packaging;
	private String description;

    /**
     * Instantiates a new Item.
     *
     * @param id the id
     */
    public Item(int id) {
		this.id = id;
		this.number = "";
		this.name = "";
		this.packaging = "";
		this.description = "";
	}

    /**
     * Instantiates a new Item.
     *
     * @param id        the id
     * @param number    the number
     * @param name      the name
     * @param packaging the packaging
     */
    public Item(int id, String number, String name, String packaging) {
		this.id = id;
		this.number = number;
		this.name = name;
		this.packaging = packaging;
		this.description = "";
	}

    /**
     * Is valid boolean.
     *
     * @return the boolean
     */
    public boolean isValid() {
		if (number == null || number.length() == 0) {
			return false;
		}

		if (name == null || name.length() == 0) {
			return false;
		}
		
		if (packaging == null || packaging.length() == 0) {
			return false;
		}

		return true;
	}

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
		return id;
	}

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
		this.id = id;
	}

    /**
     * Gets number.
     *
     * @return the number
     */
    public String getNumber() {
		return number;
	}

    /**
     * Sets number.
     *
     * @param value the value
     */
    public void setNumber(String value) {
		number = value;
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
     * Sets name.
     *
     * @param value the value
     */
    public void setName(String value) {
		name = value;
	}

    /**
     * Gets packaging.
     *
     * @return the packaging
     */
    public String getPackaging() {
		return packaging;
	}

    /**
     * Sets packaging.
     *
     * @param value the value
     */
    public void setPackaging(String value) {
		packaging = value;
	}

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
		return description;
	}

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int compareTo(Item other) {
		if (number.compareTo(other.number) == 0) {
			return name.compareTo(other.name);
		}
		
		return number.compareTo(other.number);
	}

    /**
     * Compare by item number int.
     *
     * @param other the other
     * @return the int
     */
    public int compareByItemNumber(Item other) {
		return number.compareTo(other.number);
	}

    /**
     * Compare by item name int.
     *
     * @param other the other
     * @return the int
     */
    public int compareByItemName(Item other) {
		return name.compareTo(other.name);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Item)) {
			return false;
		}
		
		Item other = (Item) object;
		
		if (this.id != other.id) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		String text = "(";
		
		text += getId();
		text += ",";
		text += getNumber();
		text += ",";
		text += getName();
		text += ",";
		text += getPackaging();
		text += ",";
		text += getDescription();
		text += ")";
		
		return text;
	}
}
