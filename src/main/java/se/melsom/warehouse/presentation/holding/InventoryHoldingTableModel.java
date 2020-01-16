package se.melsom.warehouse.presentation.holding;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.presentation.common.table.SortedTableModel;

@SuppressWarnings("serial")
public class InventoryHoldingTableModel extends SortedTableModel  {
	private static Logger logger = Logger.getLogger(InventoryHoldingTableModel.class);
	
	public static final String[] columnNames = {
			EntityName.STOCK_LOCATION_DESIGNATION,
			EntityName.ITEM_NUMBER, 
			EntityName.ITEM_NAME, 
			EntityName.INVENTORY_ACTUAL_QUANTITY,
			EntityName.INVENTORY_IDENTITY,
			EntityName.INVENTORY_LAST_UPDATED_TIMESTAMP,
			EntityName.INVENTORY_ANNOTATION 
	};
	
	public static final int[] columnWidts = { 70, 120, 300, 50, 150, 100, 250};
	
	public static final boolean[] isSortableArray = { true, true, true, false, false, false, false };
	
	public static final Class<?>[] columnClass = {
			String.class, 
			String.class, 
			String.class, 
			Integer.class, 
			String.class, 
			String.class, 
			String.class};
	
	public InventoryHoldingTableModel() {
		if (columnNames.length != columnWidts.length || 
				columnNames.length != columnClass.length ||
				columnNames.length != isSortableArray.length) {
			logger.warn("Array dimension mismatch.");
		}
	}
	
	private Vector<ActualInventory> inventoryList = new Vector<>();


	public ActualInventory getInventory(int atIndex) {
		if (atIndex >= inventoryList.size()) {
			return null;
		}
		
		return inventoryList.get(atIndex);
	}

	public Vector<ActualInventory> getInventory() {
		return inventoryList;
	}
	
	public int insert(ActualInventory anInventory) {
		inventoryList.addElement(anInventory);
		orderByColumn(getActiveColumn());
		fireTableDataChanged();
		
		return getIndex(anInventory);
	}
	
	public int update(ActualInventory anInventory, int atIndex) {
		inventoryList.remove(atIndex);
		inventoryList.addElement(anInventory);
		orderByColumn(getActiveColumn());
		fireTableDataChanged();
		
		return getIndex(anInventory);
	}
	
	public ActualInventory remove(int atIndex) {
		ActualInventory anInventory = inventoryList.remove(atIndex);

		orderByColumn(getActiveColumn());
		fireTableDataChanged();

		return anInventory;
	}
	
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
				orderByLocation();
				break;
				
			case 1:
				orderByNumber();
				break;
				
			case 2:
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
			return inventory.getLocation() == null ? "" : inventory.getLocation().getLocationLabel();
			
		case 1:
			return inventory.getItem() == null ? "" : inventory.getItem().getNumber();
			
		case 2:
			return inventory.getItem() == null ? "" : inventory.getItem().getName();
			
		case 3: 
			return inventory.getQuantity();
			
		case 4: 
			return inventory.getIdentity();
			
		case 5: 
			return inventory.getTimestamp();
			
		case 6: 
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
			return false;
			
		case 3: 
			return inventory.getIdentity().length() == 0;

		case 4: 
			return false;
			
		case 5: 
			return false;
			
		case 6: 
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
			
		case 3: 
			element.setQuantity((Integer) value);
			fireTableCellUpdated(rowIndex, columnIndex);
			return;
			
		case 6: 
			element.setAnnotation((String) value);
			fireTableCellUpdated(rowIndex, columnIndex);
			return;

		default:
			logger.warn("Can not edit column= " + columnIndex);
			break;
		}
	}
	
	private void orderByLocation() {
		Collections.sort(inventoryList, new Comparator<ActualInventory>() {

			@Override
			public int compare(ActualInventory left, ActualInventory right) {
				int result = left.compareByLocation(right);
				
				if (result == 0) {
					result = left.compareByItemNumber(right);
					
					if (result == 0) {
						result = left.compareByIdentity(right);
					}
				}
				
				return result;
			}
		});
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
