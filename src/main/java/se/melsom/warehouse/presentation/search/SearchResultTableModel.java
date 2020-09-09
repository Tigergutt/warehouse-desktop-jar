package se.melsom.warehouse.presentation.search;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.presentation.common.table.SortedTableModel;

/**
 * The type Search result table model.
 */
@SuppressWarnings("serial")
public class SearchResultTableModel extends SortedTableModel  {
	private static Logger logger = Logger.getLogger(SearchResultTableModel.class);

    /**
     * The constant columnNames.
     */
    public static final String[] columnNames = { 			
			EntityName.ITEM_NUMBER, 
			EntityName.ITEM_NAME, 
			EntityName.STOCK_LOCATION_DESIGNATION,
			EntityName.INVENTORY_ACTUAL_QUANTITY, 
			EntityName.INVENTORY_IDENTITY,
			EntityName.INVENTORY_LAST_UPDATED_TIMESTAMP,
			EntityName.INVENTORY_ANNOTATION,
	};
    /**
     * The constant columnWidts.
     */
    public static final int[] columnWidts = { 120, 230, 70, 60, 60, 100, 300};

    /**
     * The constant isSortableArray.
     */
    public static final boolean[] isSortableArray = { true, true, true, false, false, true, false };

    /**
     * The constant columnClass.
     */
    public static final Class<?>[] columnClass = {
			String.class, 
			String.class, 
			String.class, 
			Integer.class, 
			String.class, 
			String.class,			
			String.class};
	
	private Vector<ActualInventory> inventoryList = new Vector<>();

    /**
     * Instantiates a new Search result table model.
     */
    public SearchResultTableModel() {
		if (columnNames.length != columnWidts.length || 
				columnNames.length != columnClass.length ||
				columnNames.length != isSortableArray.length) {
			logger.warn("Array dimension mismatch.");
		}
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
     * Sets inventory.
     *
     * @param value the value
     */
    public void setInventory(Vector<ActualInventory> value) {
		inventoryList = value;
		orderByColumn(getActiveColumn());
		fireTableDataChanged();
		logger.debug("Inventory updated.");
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
				
			case 2:
				orderByLocation();
				break;
				
			case 4:
				orderByTimestamp();
				break;
				
			default:
				logger.warn("Sorting is not implemented for column index=" + columnIndex);
				break;
			}
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ActualInventory element = inventoryList.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
			return element.getItem().getNumber();
			
		case 1:
			return element.getItem().getName();
			
		case 2: 
			return element.getLocation() == null ? "" : element.getLocation().getLocationLabel();

		case 3: 
			return element.getQuantity();
			
		case 4: 
			return element.getIdentity();
			
		case 5: 
			return element.getTimestamp();
			
		case 6: 
			return element.getAnnotation();

		}

		return null;
	}
	
	private void orderByNumber() {
		Collections.sort(inventoryList, new Comparator<ActualInventory>() {

			@Override
			public int compare(ActualInventory left, ActualInventory right) {
				int result = left.compareByItemNumber(right);
				
				if (result == 0) {
					result = left.compareByItemName(right);
					
					if (result == 0) {
						result = left.compareByLocation(right);
					}
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
					result = left.compareByItemNumber(right);
					
					if (result == 0) {
						result = left.compareByLocation(right);
					}
				}
				
				return result;
			}
		});
	}
	
	private void orderByLocation() {
		Collections.sort(inventoryList, new Comparator<ActualInventory>() {

			@Override
			public int compare(ActualInventory left, ActualInventory right) {
				int result = left.compareByLocation(right);
				
				if (result == 0) {
					result = left.compareByItemNumber(right);
					
					if (result == 0) {
						result = left.compareByItemName(right);
					}
				}
				
				return result;
			}
		});
	}
	
	private void orderByTimestamp() {
		Collections.sort(inventoryList, new Comparator<ActualInventory>() {

			@Override
			public int compare(ActualInventory left, ActualInventory right) {
				int result = left.getTimestamp().compareTo(right.getTimestamp());
				
				if (result == 0) {
					result = left.compareByItemNumber(right);
					
					if (result == 0) {
						result = left.compareByItemName(right);
						
						if (result == 0) {
							result = left.compareByLocation(right);
						}
					}
				}
				
				return result;
			}
		});
	}
}
