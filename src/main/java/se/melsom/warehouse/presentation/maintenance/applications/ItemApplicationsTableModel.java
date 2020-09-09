package se.melsom.warehouse.presentation.maintenance.applications;

import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.ItemApplication;
import se.melsom.warehouse.presentation.common.table.SortedTableModel;

/**
 * The type Item applications table model.
 */
@SuppressWarnings("serial")
public class ItemApplicationsTableModel extends SortedTableModel {
	private static Logger logger = Logger.getLogger(ItemApplicationsTableModel.class);
	private Vector<ItemApplication> instances = new Vector<>();

    /**
     * The constant columnNames.
     */
    public static final String[] columnNames = { 
			"Kategori",
			EntityName.ITEM_NUMBER, 
			EntityName.ITEM_NAME,
			EntityName.INVENTORY_NOMINAL_QUANTIY
	};

    /**
     * The constant columnWidts.
     */
    public static final int[] columnWidts = { 
			70,
			120, 
			230, 
			75
	};

    /**
     * The constant columnClass.
     */
    public static final Class<?>[] columnClass = {
			String.class, 
			String.class, 
			String.class, 
			Integer.class
	};

    /**
     * The constant isSortableArray.
     */
    public static final boolean[] isSortableArray = { true, true, true, false };


    /**
     * Instantiates a new Item applications table model.
     */
    public ItemApplicationsTableModel() {
		if (columnNames.length != columnWidts.length || columnNames.length != columnClass.length) {
			logger.warn("Array dimension mismatch.");
		}
	}

    /**
     * Gets items.
     *
     * @return the items
     */
    public Vector<ItemApplication> getItems() {
		return instances;
	}

    /**
     * Insert item.
     *
     * @param itemApplication the item application
     * @param atIndex         the at index
     */
    public void insertItem(ItemApplication itemApplication, int atIndex) {
		instances.insertElementAt(itemApplication, atIndex);
		fireTableRowsInserted(atIndex, atIndex);
	}

    /**
     * Remove item item application.
     *
     * @param atIndex the at index
     * @return the item application
     */
    public ItemApplication removeItem(int atIndex) {
		ItemApplication item = instances.remove(atIndex);

		fireTableRowsDeleted(atIndex, atIndex);

		return item;
	}

    /**
     * Sets items.
     *
     * @param collection the collection
     */
    public void setItems(Vector<ItemApplication> collection) {
		logger.debug("Product list updated");
		instances.clear();
		instances.addAll(collection);
		fireTableDataChanged();
	}


    /**
     * Gets index.
     *
     * @param ofApplication the of application
     * @return the index
     */
    public int getIndex(ItemApplication ofApplication) {
		for (int index = 0; index < instances.size(); index++) {
			ItemApplication application = instances.get(index);
			
			if (application.getItem().equals(ofApplication.getItem())) {
				return index;
			}
		}
		
		return -1;
	}
	
	@Override
	public int getColumnWidth(int columnIndex) {
		return columnWidts[columnIndex];
	}

	@Override
	public boolean[] isSortable() {
		return isSortableArray;
	}

	@Override
	public void orderByColumn(int columnIndex) {
		logger.warn("sort by column index=" + columnIndex);
	}

	@Override
	public Class<?> getColumnClass(int col) {
		return columnClass[col];
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public int getRowCount() {
		return instances.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ItemApplication element = instances.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
			return element.getCategory();
			
		case 1:
			return element.getItem().getNumber();
			
		case 2:
			return element.getItem().getName();
			
		case 3: 
			return element.getQuantity();

		}

		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		default:
			logger.warn("Unknown column= " + columnIndex);
		}
		
		return false;
	}
	
	@Override	
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		switch (columnIndex) {
		default:
			logger.warn("Can not edit column= " + columnIndex);
		}

		fireTableCellUpdated(rowIndex, columnIndex);
	}
}
