package se.melsom.warehouse.application.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.application.common.table.SortableTableModel;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.data.vo.StockLocationVO;
import se.melsom.warehouse.model.EntityName;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class ContentModel extends SortableTableModel {
	private static final Logger logger = LoggerFactory.getLogger(ContentModel.class);

    public static final String[] columnNames = {
			EntityName.ITEM_NUMBER, 
			EntityName.ITEM_NAME, 
			EntityName.STOCK_LOCATION_DESIGNATION,
			EntityName.INVENTORY_ACTUAL_QUANTITY, 
			EntityName.INVENTORY_IDENTITY,
			EntityName.INVENTORY_LAST_UPDATED_TIMESTAMP,
			EntityName.INVENTORY_ANNOTATION,
	};

    public static final int[] columnWidths = { 120, 230, 70, 60, 60, 100, 300};
    public static final boolean[] isSortableArray = { true, true, true, false, false, true, false };

    public static final Class<?>[] columnClass = {
			String.class, 
			String.class, 
			String.class, 
			Integer.class, 
			String.class, 
			String.class,			
			String.class};
	
	private Vector<ActualInventoryVO> inventoryList = new Vector<>();

    public ContentModel() {
		if (columnNames.length != columnWidths.length ||
				columnNames.length != columnClass.length ||
				columnNames.length != isSortableArray.length) {
			logger.warn("Array dimension mismatch.");
		}
	}

    public Vector<ActualInventoryVO> getInventory() {
		return inventoryList;
	}

    public void setInventory(Vector<ActualInventoryVO> value) {
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
		return columnWidths[columnIndex];
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
		ActualInventoryVO element = inventoryList.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
			return element.getItem().getNumber();
			
		case 1:
			return element.getItem().getName();
			
		case 2:
			StockLocationVO location = element.getStockLocation();
			return location == null ? "" : location.getLocationLabel();

		case 3: 
			return element.getQuantity();
			
		case 4: 
			return element.getIdentity();
			
		case 5: 
			return element.getLastModified();
			
		case 6: 
			return element.getAnnotation();
		}

		return null;
	}
	
	private void orderByNumber() {
		Collections.sort(inventoryList, new Comparator<ActualInventoryVO>() {
			@Override
			public int compare(ActualInventoryVO left, ActualInventoryVO right) {
				int result = left.getItem().getNumber().compareTo(right.getItem().getNumber());
				
				if (result == 0) {
					result = left.getItem().getName().compareTo(right.getItem().getName());
					
					if (result == 0) {
						result = left.getStockLocation().getLocationLabel().compareTo(right.getStockLocation().getLocationLabel());
					}
				}
				
				return result;
			}
		});
	}

	private void orderByName() {		
		Collections.sort(inventoryList, new Comparator<ActualInventoryVO>() {

			@Override
			public int compare(ActualInventoryVO left, ActualInventoryVO right) {
				int result = left.getItem().getName().compareTo(right.getItem().getName());
				
				if (result == 0) {
					result = left.getItem().getNumber().compareTo(right.getItem().getNumber());
					
					if (result == 0) {
						result = left.getStockLocation().getLocationLabel().compareTo(right.getStockLocation().getLocationLabel());
					}
				}
				
				return result;
			}
		});
	}
	
	private void orderByLocation() {
		Collections.sort(inventoryList, new Comparator<ActualInventoryVO>() {

			@Override
			public int compare(ActualInventoryVO left, ActualInventoryVO right) {
				int result = left.getStockLocation().getLocationLabel().compareTo(right.getStockLocation().getLocationLabel());
				
				if (result == 0) {
					result = left.getItem().getNumber().compareTo(right.getItem().getNumber());
					
					if (result == 0) {
						result = left.getItem().getName().compareTo(right.getItem().getName());
					}
				}
				
				return result;
			}
		});
	}
	
	private void orderByTimestamp() {
		Collections.sort(inventoryList, new Comparator<ActualInventoryVO>() {

			@Override
			public int compare(ActualInventoryVO left, ActualInventoryVO right) {
				int result = left.getLastModified().compareTo(right.getLastModified());
				
				if (result == 0) {
					result = left.getItem().getNumber().compareTo(right.getItem().getNumber());
					
					if (result == 0) {
						result = left.getItem().getName().compareTo(right.getItem().getName());
						
						if (result == 0) {
							result = left.getStockLocation().getLocationLabel().compareTo(right.getStockLocation().getLocationLabel());
						}
					}
				}
				
				return result;
			}
		});
	}
}
