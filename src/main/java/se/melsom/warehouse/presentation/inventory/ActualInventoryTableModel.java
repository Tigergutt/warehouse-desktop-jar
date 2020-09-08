package se.melsom.warehouse.presentation.inventory;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.presentation.common.table.SortedTableModel;

/**
 * The type Actual inventory table model.
 */
@SuppressWarnings("serial")
public class ActualInventoryTableModel extends SortedTableModel  {
	private static Logger logger = Logger.getLogger(ActualInventoryTableModel.class);

    /**
     * The constant columnNames.
     */
    public static final String[] columnNames = { 
			EntityName.ITEM_NUMBER, 
			EntityName.ITEM_NAME, 
			EntityName.INVENTORY_ACTUAL_QUANTITY,
			EntityName.INVENTORY_IDENTITY,
			EntityName.INVENTORY_LAST_UPDATED_TIMESTAMP,
			EntityName.INVENTORY_ANNOTATION 
	};

    /**
     * The constant columnWidts.
     */
    public static final int[] columnWidts = { 120, 300, 50, 150, 100, 250};

    /**
     * The constant isSortableArray.
     */
    public static final boolean[] isSortableArray = { true, true, false, false, false, false };

    /**
     * The constant columnClass.
     */
    public static final Class<?>[] columnClass = {
			String.class, 
			String.class, 
			Integer.class, 
			String.class, 
			String.class, 
			String.class};

    /**
     * Instantiates a new Actual inventory table model.
     */
    public ActualInventoryTableModel() {
		if (columnNames.length != columnWidts.length || 
				columnNames.length != columnClass.length ||
				columnNames.length != isSortableArray.length) {
			logger.warn("Array dimension mismatch.");
		}
	}
	
	private Vector<ActualInventory> inventoryList = new Vector<>();


    /**
     * Gets inventory.
     *
     * @param atIndex the at index
     * @return the inventory
     */
    public ActualInventory getInventory(int atIndex) {
		if (atIndex >= inventoryList.size()) {
			return null;
		}
		
		return inventoryList.get(atIndex);
	}

    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public Vector<ActualInventory> getInventory() {
		return inventoryList;
	}

    /**
     * Insert int.
     *
     * @param anInventory the an inventory
     * @return the int
     */
    public int insert(ActualInventory anInventory) {
		inventoryList.addElement(anInventory);
		orderByColumn(getActiveColumn());
		fireTableDataChanged();
		
		return getIndex(anInventory);
	}

    /**
     * Update int.
     *
     * @param anInventory the an inventory
     * @param atIndex     the at index
     * @return the int
     */
    public int update(ActualInventory anInventory, int atIndex) {
		inventoryList.remove(atIndex);
		inventoryList.addElement(anInventory);
		orderByColumn(getActiveColumn());
		fireTableDataChanged();
		
		return getIndex(anInventory);
	}

    /**
     * Remove actual inventory.
     *
     * @param atIndex the at index
     * @return the actual inventory
     */
    public ActualInventory remove(int atIndex) {
		ActualInventory anInventory = inventoryList.remove(atIndex);

		orderByColumn(getActiveColumn());
		fireTableDataChanged();

		return anInventory;
	}

    /**
     * Sets inventory.
     *
     * @param value the value
     */
    public void setInventory(Vector<ActualInventory> value) {
		logger.debug("Inventory updated");
		inventoryList = value;
		orderByColumn(getActiveColumn());
		fireTableDataChanged();
	}
	
	private int getIndex(ActualInventory ofInventory) {
		for (int index = 0; index < inventoryList.size(); index++) {
			ActualInventory inventory = inventoryList.get(index);
			
			if (inventory.getLocation().getId() == ofInventory.getLocation().getId() &&
				inventory.getItem().getId() == ofInventory.getItem().getId()) {
				return index;
			}
		}
		
		return -1;
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
		return inventoryList.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
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
		if (isSortable()[columnIndex]) {
			switch (columnIndex) {
			case 0:
				orderByNumber();
				break;
				
			case 1:
				orderByName();
				break;
				
			default:
				logger.warn("Sorting is not implemented for column index=" + columnIndex);
				break;
			}
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ActualInventory inventory = inventoryList.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
			return inventory.getItem() == null ? "" : inventory.getItem().getNumber();
			
		case 1:
			return inventory.getItem() == null ? "" : inventory.getItem().getName();
			
		case 2: 
			return inventory.getQuantity();
			
		case 3: 
			return inventory.getIdentity();
			
		case 4: 
			return inventory.getTimestamp();
			
		case 5: 
			return inventory.getAnnotation();

		default:
			logger.warn("Unknown column= " + columnIndex);
		}

		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		ActualInventory inventory = inventoryList.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
			return false;
			
		case 1:
			return false;
			
		case 2: 
			return inventory.getIdentity().length() == 0;

		case 3: 
			return false;
			
		case 4: 
			return false;
			
		case 5: 
			return true;

		default:
			logger.warn("Unknown column= " + columnIndex);
		}
		
		return false;
	}
	
	@Override	
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		ActualInventory element = inventoryList.get(rowIndex);
		
		switch (columnIndex) {
			
		case 2: 
			element.setQuantity((Integer) value);
			fireTableCellUpdated(rowIndex, columnIndex);
			return;
			
		case 5: 
			element.setAnnotation((String) value);
			fireTableCellUpdated(rowIndex, columnIndex);
			return;

		default:
			logger.warn("Can not edit column= " + columnIndex);
			break;
		}
	}
	
	private void orderByNumber() {
		Collections.sort(inventoryList, new Comparator<ActualInventory>() {

			@Override
			public int compare(ActualInventory left, ActualInventory right) {
				int result = left.compareByItemNumber(right);
				
				if (result == 0) {
					result = left.compareByIdentity(right);
				}
				
				return result;
			}
		});
	}

	private void orderByName() {		
		Collections.sort(inventoryList, new Comparator<ActualInventory>() {

			@Override
			public int compare(ActualInventory left, ActualInventory right) {
				int result = left.compareByItemName(right);
				
				if (result == 0) {
					result = left.compareByIdentity(right);
				}
				
				return result;
			}
		});
	}
	
}
