package se.melsom.warehouse.presentation.common.select;

import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.Item;
import se.melsom.warehouse.presentation.common.table.SortedTableModel;

/**
 * The type Select item table model.
 */
@SuppressWarnings("serial")
public class SelectItemTableModel extends SortedTableModel {
	private static Logger logger = Logger.getLogger(SelectItemTableModel.class);

    /**
     * The constant columnNames.
     */
    public static final String[] columnNames = { EntityName.ITEM_NUMBER, EntityName.ITEM_NAME, };

    /**
     * The constant columnWidts.
     */
    public static final int[] columnWidts = { 120, 230 };

    /**
     * The constant isSortableArray.
     */
    public static final boolean[] isSortableArray = { true, true };

    /**
     * The constant columnClass.
     */
    public static final Class<?>[] columnClass = { String.class, String.class};
	
	private Vector<Item> itemList = new Vector<>();
	private Vector<Item> originalItemList = new Vector<>();

    /**
     * Instantiates a new Select item table model.
     */
    public SelectItemTableModel() {
		if (columnNames.length != columnWidts.length || 
				columnNames.length != columnClass.length ||
				columnNames.length != isSortableArray.length) {
			logger.warn("Array dimension mismatch.");
		}
	}

    /**
     * Get item.
     *
     * @param index the index
     * @return the item
     */
    public Item get(int index) {
		if (index >= 0 && index < itemList.size()) {
			return itemList.get(index);
		}
		
		return null;
	}

    /**
     * Sets item list.
     *
     * @param items the items
     */
    public void setItemList(Vector<Item> items) {
		itemList.clear();
		itemList.addAll(items);
		fireTableDataChanged();
		
		originalItemList.clear();
		originalItemList.addAll(items);
	}


    /**
     * Search for.
     *
     * @param searchKey the search key
     */
    public void searchFor(String searchKey) {
		itemList.clear();
		
		for (Item item : originalItemList) {
			if (item.getNumber().toUpperCase().contains(searchKey) || item.getName().toUpperCase().contains(searchKey)) {
				itemList.add(item);
			}
		}
		
		fireTableDataChanged();
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
		return itemList.size();
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
				orderByNumber(itemList);
				break;
				
			case 1:
				orderByName(itemList);
				break;
				
			default:
				logger.warn("Sorting is not implemented for column index=" + columnIndex);
				break;
			}
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Item item = itemList.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
			return item.getNumber();
			
		case 1:
			return item.getName();
			
		default:
			logger.warn("Unknown column= " + columnIndex);
		}

		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return false;
			
		case 1:
			return false;
			
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
			break;
		}
	}
	
	private void orderByNumber(Vector<Item> items) {
//		Collections.sort(itemList, new Comparator<Item>() {
//
//			@Override
//			public int compare(Item left, Item right) {
//				int result = left.compareByItemNumber(right);
//				
//				if (result == 0) {
//					result = left.compareByItemName(right);
//					
//					if (result == 0) {
//						result = left.compareByLocation(right);
//						
////						if (result == 0) {
////							return left.compareByInstance(right);
////						}
//					}
//				}
//				
//				return result;
//			}
//		});
	}

	private void orderByName(Vector<Item> items) {		
//		Collections.sort(itemList, new Comparator<Item>() {
//
//			@Override
//			public int compare(Item left, Item right) {
//				int result = left.compareByItemName(right);
//				
//				if (result == 0) {
//					result = left.compareByItemNumber(right);
//					
//					if (result == 0) {
//						result = left.compareByLocation(right);
////						
////						if (result == 0) {
////							return left.compareByInstance(right);
////						}
//					}
//				}
//				
//				return result;
//			}
//		});
	}
}
