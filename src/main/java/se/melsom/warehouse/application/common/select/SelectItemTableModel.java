package se.melsom.warehouse.application.common.select;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.application.common.table.SortableTableModel;
import se.melsom.warehouse.data.vo.ItemVO;
import se.melsom.warehouse.model.EntityName;

import java.util.Vector;

public class SelectItemTableModel extends SortableTableModel {
	private static final Logger logger = LoggerFactory.getLogger(SelectItemTableModel.class);

    public static final String[] columnNames = { EntityName.ITEM_NUMBER, EntityName.ITEM_NAME, };
    public static final int[] columnWidts = { 120, 230 };
    public static final boolean[] isSortableArray = { true, true };
    public static final Class<?>[] columnClass = { String.class, String.class};
	
	private final Vector<ItemVO> itemList = new Vector<>();
	private final Vector<ItemVO> originalItemList = new Vector<>();

    public SelectItemTableModel() {
		if (columnNames.length != columnWidts.length || 
				columnNames.length != columnClass.length ||
				columnNames.length != isSortableArray.length) {
			logger.warn("Array dimension mismatch.");
		}
	}

    public ItemVO get(int index) {
		if (index >= 0 && index < itemList.size()) {
			return itemList.get(index);
		}
		
		return null;
	}

    public void setItemList(Vector<ItemVO> items) {
		itemList.clear();
		itemList.addAll(items);
		fireTableDataChanged();
		
		originalItemList.clear();
		originalItemList.addAll(items);
	}


    public void searchFor(String searchKey) {
		itemList.clear();
		
		for (ItemVO item : originalItemList) {
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
		ItemVO item = itemList.get(rowIndex);
		
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
	
	private void orderByNumber(Vector<ItemVO> items) {
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

	private void orderByName(Vector<ItemVO> items) {
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
