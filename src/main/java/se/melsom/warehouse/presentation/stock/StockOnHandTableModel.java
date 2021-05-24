package se.melsom.warehouse.presentation.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.common.table.SortableTableModel;
import se.melsom.warehouse.data.vo.StockOnHandVO;
import se.melsom.warehouse.model.EntityName;

import java.util.Vector;

@Deprecated
public class StockOnHandTableModel extends SortableTableModel {
	private static final Logger logger = LoggerFactory.getLogger(StockOnHandTableModel.class);

    public static final String[] columnNames = {
			EntityName.ITEM_NUMBER, 
			EntityName.ITEM_NAME,
			EntityName.ITEM_PACKAGING,
			EntityName.INVENTORY_NOMINAL_QUANTITY,
			EntityName.INVENTORY_ACTUAL_QUANTITY,
			EntityName.INVENTORY_IDENTITY,
			EntityName.INVENTORY_ANNOTATION 
	};

    public static final int[] columnWidts = {
			120, 
			230, 
			50,
			50, 
			50, 
			100, 
			300
	};

    public static final boolean[] isSortableArray = {
			true, 
			true, 
			false, 
			false, 
			false,
			true,
			false};

    public static final Class<?>[] columnClass = {
			String.class, 
			String.class, 
			String.class, 
			Integer.class, 
			Integer.class, 
			String.class,
			String.class
	};

    public StockOnHandTableModel() {
		if (columnNames.length != columnWidts.length || 
				columnNames.length != columnClass.length ||
				columnNames.length != isSortableArray.length) {
			logger.warn("Array dimension mismatch.");
		}
	}
	
	private final Vector<StockOnHandVO> filteredList = new Vector<>();
	private Vector<StockOnHandVO> originalList = new Vector<>();
	private boolean shouldFilterShortfall = false;
	private boolean shouldFilterBalances = false;
	private boolean shouldFilterOverplus = false;

    public Vector<StockOnHandVO> getStockOnHand() {
		return filteredList;
	}

    public void setStockOnHand(Vector<StockOnHandVO> stockOnHand) {
		originalList = stockOnHand;
		filterStockOnHand(originalList, filteredList);
		orderByColumn(getActiveColumn());
		fireTableDataChanged();
		logger.debug("Inventory updated.");
	}

    public void setFilterShortfall(boolean shouldFilterShortfall) {
		this.shouldFilterShortfall = shouldFilterShortfall;
		filterStockOnHand(originalList, filteredList);
		orderByColumn(getActiveColumn());
		fireTableDataChanged();
	}

    public void setFilterBalances(boolean shouldFilterBalances) {
		this.shouldFilterBalances = shouldFilterBalances;
		filterStockOnHand(originalList, filteredList);
		orderByColumn(getActiveColumn());
		fireTableDataChanged();
	}

   public void setFilterOverplus(boolean shouldFilterOverplus) {
		this.shouldFilterOverplus = shouldFilterOverplus;
		filterStockOnHand(originalList, filteredList);
		orderByColumn(getActiveColumn());
		fireTableDataChanged();
	}
	
	private void filterStockOnHand(Vector<StockOnHandVO> original, Vector<StockOnHandVO> filtered) {
		filtered.clear();
		
		for (StockOnHandVO anElement : original) {
			if (anElement.getActualQuantity() < anElement.getNominalQuantity()) {
				if (!shouldFilterShortfall) {
					filtered.addElement(anElement);
				}
			} else if (anElement.getActualQuantity() == anElement.getNominalQuantity()) {
				if (!shouldFilterBalances) {
					filtered.addElement(anElement);
				}
			} else {
				if (!shouldFilterOverplus) {
					filtered.addElement(anElement);
				}
			}
		}
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
		return filteredList.size();
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
				orderByNumber(filteredList);
				break;
				
			case 1:
				orderByName(filteredList);
				break;
				
			case 5:
				orderByIdentity(filteredList);
				break;
				
			default:
				logger.warn("Sorting is not implemented for column index=" + columnIndex);
				break;
			}
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		StockOnHandVO element = filteredList.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
			return element.getNumber();
			
		case 1:
			return element.getName();
			
		case 2: 
			return element.getPackaging();

		case 3: 
			return element.getNominalQuantity();

		case 4: 
			return element.getActualQuantity();

		case 5: 
			return element.getIdentity();

		case 6: 
			return element.getAnnotation();
		}

		return null;
	}
	
	private void orderByNumber(Vector<StockOnHandVO> elements) {
    	throw new IllegalArgumentException("Sluta använda denna!");
//		Collections.sort(elements, new Comparator<StockOnHandVO>() {
//
//			@Override
//			public int compare(StockOnHandVO left, StockOnHandVO right) {
//				int result = left.compareByItemNumber(right);
//
//				if (result == 0) {
//					result = left.compareByItemName(right);
//
//					if (result == 0) {
//						return left.compareByIdentity(right);
//					}
//				}
//
//				return result;
//			}
//		});
	}

	private void orderByName(Vector<StockOnHandVO> elements) {
		throw new IllegalArgumentException("Sluta använda denna!");
//		Collections.sort(elements, new Comparator<StockOnHandVO>() {
//
//			@Override
//			public int compare(StockOnHandVO left, StockOnHandVO right) {
//				int result = left.compareByItemName(right);
//
//				if (result == 0) {
//					result = left.compareByItemNumber(right);
//
//					if (result == 0) {
//						result = left.compareByIdentity(right);
//					}
//				}
//
//				return result;
//			}
//		});
	}
	
	private void orderByIdentity(Vector<StockOnHandVO> elements) {
		throw new IllegalArgumentException("Sluta använda denna!");
//		Collections.sort(elements, new Comparator<StockOnHandVO>() {
//
//			@Override
//			public int compare(StockOnHandVO left, StockOnHandVO right) {
//				int result = left.compareByIdentity(right);
//
////				if (result == 0) {
////					result = left.compareByItemNumber(right);
////
////					if (result == 0) {
////						result = left.compareByItemName(right);
////					}
////				}
//
//				return result;
//			}
//		});
	}
}
