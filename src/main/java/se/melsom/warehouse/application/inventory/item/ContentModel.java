package se.melsom.warehouse.application.inventory.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.application.common.table.SortableTableModel;
import se.melsom.warehouse.data.vo.ItemVO;
import se.melsom.warehouse.model.EntityName;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class ContentModel extends SortableTableModel {
	private static final Logger logger = LoggerFactory.getLogger(ContentModel.class);

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

	private final Vector<ItemVO> itemList = new Vector<>();

    public ContentModel() {
		if (columnNames.length != columnWidts.length || columnNames.length != columnClass.length) {
			logger.warn("Array dimension mismatch.");
		}
	}

    public ItemVO getItem(int itemIndex) {
		if (itemIndex >= 0 && itemIndex < itemList.size()) {
			return itemList.get(itemIndex);
		}
		
		return null;
	}

   public Vector<ItemVO> getItems() {
		return itemList;
	}

    public int insertItem(ItemVO anItem) {
		itemList.addElement(anItem);
		sortItemList();
		fireTableDataChanged();
		
		return getIndex(anItem);
	}

    public int updateItem(ItemVO anItem, int atIndex) {
		itemList.remove(atIndex);
		itemList.addElement(anItem);
		sortItemList();
		fireTableDataChanged();
		
		return getIndex(anItem);
	}

    public ItemVO removeItem(int atIndex) {
		ItemVO item = itemList.remove(atIndex);

		fireTableRowsDeleted(atIndex, atIndex);

		return item;
	}

    public void setItems(Collection<ItemVO> collection) {
		logger.debug("Product list updated");
		itemList.clear();
		itemList.addAll(collection);
		sortItemList();
		fireTableDataChanged();
	}
	
	private void sortItemList() {
		Collections.sort(itemList, new Comparator<ItemVO>() {

			@Override
			public int compare(ItemVO left, ItemVO right) {
				int result = left.getNumber().compareTo(right.getNumber());
				
				if (result == 0) {
					result = left.getName().compareTo(right.getName());
				}
				
				return result;
			}
			
		});
	}
	
	private int getIndex(ItemVO ofItem) {
		for (int index = 0; index < itemList.size(); index++) {
			ItemVO item = itemList.get(index);
			
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
		ItemVO element = itemList.get(rowIndex);
		
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
		Collections.sort(itemList, new Comparator<ItemVO>() {

			@Override
			public int compare(ItemVO left, ItemVO right) {
				int result = left.getNumber().compareTo(right.getNumber());
				
				if (result == 0) {
					result = left.getName().compareTo(right.getName());
				}
				
				return result;
			}
		});
	}

	private void orderByName() {		
		Collections.sort(itemList, new Comparator<ItemVO>() {

			@Override
			public int compare(ItemVO left, ItemVO right) {
				int result = left.getName().compareTo(right.getName());
				
				if (result == 0) {
					result = left.getNumber().compareTo(right.getNumber());
				}
				
				return result;
			}
		});
	}
}
