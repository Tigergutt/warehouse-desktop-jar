package se.melsom.warehouse.application.inventory.actual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.application.common.table.SortableTableModel;
import se.melsom.warehouse.data.vo.ActualInventoryVO;
import se.melsom.warehouse.model.EntityName;

import java.util.Vector;

public class ContentModel extends SortableTableModel {
	private static final Logger logger = LoggerFactory.getLogger(ContentModel.class);

    public static final String[] columnNames = {
			EntityName.ITEM_NUMBER, 
			EntityName.ITEM_NAME, 
			EntityName.INVENTORY_ACTUAL_QUANTITY,
			EntityName.INVENTORY_IDENTITY,
			EntityName.INVENTORY_LAST_UPDATED_TIMESTAMP,
			EntityName.INVENTORY_ANNOTATION 
	};

    public static final int[] columnWidths = { 120, 300, 50, 150, 100, 250};

    public static final boolean[] isSortableArray = { true, true, false, false, false, false };

    public static final Class<?>[] columnClass = {
			String.class, 
			String.class, 
			Integer.class, 
			String.class, 
			String.class, 
			String.class};

    public ContentModel() {
		if (columnNames.length != columnWidths.length ||
				columnNames.length != columnClass.length ||
				columnNames.length != isSortableArray.length) {
			logger.warn("Array dimension mismatch.");
		}
	}
	
	private Vector<ActualInventoryVO> inventoryList = new Vector<>();

    public ActualInventoryVO getInventory(int atIndex) {
		if (atIndex >= inventoryList.size()) {
			return null;
		}
		
		return inventoryList.get(atIndex);
	}

    public Vector<ActualInventoryVO> getInventory() {
		return inventoryList;
	}

    public int insert(ActualInventoryVO anInventory) {
		inventoryList.addElement(anInventory);
		orderByColumn(getActiveColumn());
		fireTableDataChanged();
		
		return getIndex(anInventory);
	}

    public int update(ActualInventoryVO anInventory, int atIndex) {
		inventoryList.remove(atIndex);
		inventoryList.addElement(anInventory);
		orderByColumn(getActiveColumn());
		fireTableDataChanged();
		
		return getIndex(anInventory);
	}

    public ActualInventoryVO remove(int atIndex) {
		ActualInventoryVO anInventory = inventoryList.remove(atIndex);

		orderByColumn(getActiveColumn());
		fireTableDataChanged();

		return anInventory;
	}

	public void clearInventory() {
		logger.debug("Clear inventory");
		inventoryList.clear();
		fireTableDataChanged();
	}

    public void setInventory(Vector<ActualInventoryVO> value) {
		logger.debug("Inventory updated");
		inventoryList = value;
		orderByColumn(getActiveColumn());
		fireTableDataChanged();
	}
	
	private int getIndex(ActualInventoryVO ofInventory) {
		for (int index = 0; index < inventoryList.size(); index++) {
			ActualInventoryVO inventory = inventoryList.get(index);
			
			if (inventory.getStockLocation().getId() == ofInventory.getStockLocation().getId() &&
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
				
			default:
				logger.warn("Sorting is not implemented for column index=" + columnIndex);
				break;
			}
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ActualInventoryVO inventory = inventoryList.get(rowIndex);
		
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
			return inventory.getLastModified();
			
		case 5: 
			return inventory.getAnnotation();

		default:
			logger.warn("Unknown column= " + columnIndex);
		}

		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		ActualInventoryVO inventory = inventoryList.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
		case 1:
			return false;
			
		case 2: 
			return inventory.getIdentity().length() == 0;

		case 3: 
		case 5:
			return true;

		default:
			logger.warn("Unknown column= " + columnIndex);
		}
		
		return false;
	}
	
	@Override	
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		ActualInventoryVO element = inventoryList.get(rowIndex);
		
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
//		Collections.sort(inventoryList, new Comparator<ActualInventory>() {
//
//			@Override
//			public int compare(ActualInventoryVO left, ActualInventoryVO right) {
//				int result = left.compareByItemNumber(right);
//
//				if (result == 0) {
//					result = left.compareByIdentity(right);
//				}
//
//				return result;
//			}
//		});
	}

	private void orderByName() {		
//		Collections.sort(inventoryList, new Comparator<ActualInventory>() {
//
//			@Override
//			public int compare(ActualInventoryVO left, ActualInventoryVO right) {
//				int result = left.compareByItemName(right);
//
//				if (result == 0) {
//					result = left.compareByIdentity(right);
//				}
//
//				return result;
//			}
//		});
	}
	
}
