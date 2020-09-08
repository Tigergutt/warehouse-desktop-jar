package se.melsom.warehouse.database.item;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.Item;

/**
 * The Item data access object.
 */
public class ItemDAO {
	private int id;
	private String number;
	private String name;
	private String packaging;
	private String description;

    /**
     * Instantiates a new Item dao.
     *
     * @param anItem the an item
     */
    public ItemDAO(Item anItem) {
		this.id = anItem.getId();
		this.number = anItem.getNumber();
		this.name = anItem.getName();
		this.packaging = anItem.getPackaging();
		this.description = anItem.getDescription();
	}

    /**
     * Instantiates a new Item dao.
     */
    public ItemDAO() {
		this.id = EntityName.NULL_ID;
		this.number = "";
		this.name = "";
		this.packaging = "";
	}

    /**
     * Create item item.
     *
     * @return the item
     */
    public Item createItem() {
		return new Item(id, number, name, packaging);
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
	public String toString() {
		String text = "";
		
		text += "[";
		text += getId();
		text += "|";
		text += getNumber();
		text += "|";
		text += getName();
		text += "|";
		text += getPackaging();
		text += "|";
		text += getDescription();
		text += "]";
		
		return text;
	}
}
