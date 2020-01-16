package se.melsom.warehouse.presentation.maintenance.items;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.Item;
import se.melsom.warehouse.presentation.common.table.SortedTableModel;

@SuppressWarnings("serial")
public class ItemMaintenanceTableModel extends SortedTableModel  {
	private static Logger logger = Logger.getLogger(ItemMaintenanceTableModel.class);
	
	public static final String[] columnNames = { 
			EntityName.ITEM_NUMBER, 
			EntityName.ITEM_NAME,
			EntityName.ITEM_PACKAGING
	};
	
	public static final int[] columnWidts = { 
			120, 
			230, 
			60
	};
	
	public static final boolean[] isSortableArray = { true, true, false };
	

	public static final Class<?>[] columnClass = {
			String.class, 
			String.class, 
			String.class
	};

	private Vector<Item> itemList = new Vector<>();
	
	public ItemMaintenanceTableModel() {
		if (columnNames.length != columnWidts.length || columnNames.length != columnClass.length) {
			logger.warn("Array dimension mismatch.");
		}
	}
	
	public Item getItem(int itemIndex) {
		if (itemIndex >= 0 && itemIndex < itemList.size()) {
			return itemList.get(itemIndex);
		}
		
		return null;
	}
	
	public Vector<Item> getItems() {
		return itemList;
	}
	
	public int insertItem(Item anItem) {
		itemList.addElement(anItem);
		sortItemList();
		fireTableDataChanged();
		
		return getIndex(anItem);
	}
	
	public int updateItem(Item anItem, int atIndex) {
		itemList.remove(atIndex);
		itemList.addElement(anItem);
		sortItemList();
		fireTableDataChanged();
		
		return getIndex(anItem);
	}
	
	public Item removeItem(int atIndex) {
		Item item = itemList.remove(atIndex);

		fireTableRowsDeleted(atIndex, atIndex);

		return item;
	}
	
	public void setItems(Collection<Item> collection) {
		logger.debug("Product list updated");
		itemList.clear();
		itemList.addAll(collection);
		sortItemList();
		fireTableDataChanged();
	}
	
	private void sortItemList() {
		Collections.sort(itemList, new Comparator<Item>() {

			@Override
			public int compare(Item left, Item right) {
				int result = left.compareByItemNumber(right);
				
				if (result == 0) {
					result = left.compareByItemName(right);
				}
				
				return result;
			}
			
		});
	}
	
	private int getIndex(Item ofItem) {
		for (int index = 0; index < itemList.size(); index++) {
			Item item = itemList.get(index);
			
			if (item.getId() == ofItem.getId()) {
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
		Item element = itemList.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
			return element.getNumber();
			
		case 1:
			return element.getName();
			
		case 2: 
			return element.getPackaging();

		}

		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
		case 1:
		case 2: 
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
			return;
		}
	}
	
	private void orderByNumber() {
		Collections.sort(itemList, new Comparator<Item>() {

			@Override
			public int compare(Item left, Item right) {
				int result = left.compareByItemNumber(right);
				
				if (result == 0) {
					result = left.compareByItemName(right);
				}
				
				return result;
			}
		});
	}

	private void orderByName() {		
		Collections.sort(itemList, new Comparator<Item>() {

			@Override
			public int compare(Item left, Item right) {
				int result = left.compareByItemName(right);
				
				if (result == 0) {
					result = left.compareByItemNumber(right);
				}
				
				return result;
			}
		});
	}
}
