package se.melsom.warehouse.presentation.stock;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.inventory.StockOnHand;
import se.melsom.warehouse.presentation.common.table.SortedTableModel;

/**
 * The type Stock on hand table model.
 */
@SuppressWarnings("serial")
public class StockOnHandTableModel extends SortedTableModel  {
	private static Logger logger = Logger.getLogger(StockOnHandTableModel.class);

    /**
     * The constant columnNames.
     */
    public static final String[] columnNames = { 
			EntityName.ITEM_NUMBER, 
			EntityName.ITEM_NAME,
			EntityName.ITEM_PACKAGING,
			EntityName.INVENTORY_NOMINAL_QUANTIY,
			EntityName.INVENTORY_ACTUAL_QUANTITY,
			EntityName.INVENTORY_IDENTITY,
			EntityName.INVENTORY_ANNOTATION 
	};

    /**
     * The constant columnWidts.
     */
    public static final int[] columnWidts = { 
			120, 
			230, 
			50,
			50, 
			50, 
			100, 
			300
	};

    /**
     * The constant isSortableArray.
     */
    public static final boolean[] isSortableArray = { 
			true, 
			true, 
			false, 
			false, 
			false,
			true,
			false};

    /**
     * The constant columnClass.
     */
    public static final Class<?>[] columnClass = {
			String.class, 
			String.class, 
			String.class, 
			Integer.class, 
			Integer.class, 
			String.class,
			String.class
	};

    /**
     * Instantiates a new Stock on hand table model.
     */
    public StockOnHandTableModel() {
		if (columnNames.length != columnWidts.length || 
				columnNames.length != columnClass.length ||
				columnNames.length != isSortableArray.length) {
			logger.warn("Array dimension mismatch.");
		}
	}
	
	private Vector<StockOnHand> filteredList = new Vector<>();
	private Vector<StockOnHand> originalList = new Vector<>();
	private boolean shouldFilterShortfall = false;
	private boolean shouldFilterBalances = false;
	private boolean shouldFilterOverplus = false;

    /**
     * Gets stock on hand.
     *
     * @return the stock on hand
     */
    public Vector<StockOnHand> getStockOnHand() {
		return filteredList;
	}

    /**
     * Sets stock on hand.
     *
     * @param value the value
     */
    public void setStockOnHand(Vector<StockOnHand> value) {
		originalList = value;
		filterStockOnHand(originalList, filteredList);
		orderByColumn(getActiveColumn());
		fireTableDataChanged();
		logger.debug("Inventory updated.");
	}

    /**
     * Sets filter shortfall.
     *
     * @param shouldFilterShortfall the should filter shortfall
     */
    public void setFilterShortfall(boolean shouldFilterShortfall) {
		this.shouldFilterShortfall = shouldFilterShortfall;
		filterStockOnHand(originalList, filteredList);
		orderByColumn(getActiveColumn());
		fireTableDataChanged();
	}

    /**
     * Sets filter balances.
     *
     * @param shouldFilterBalances the should filter balances
     */
    public void setFilterBalances(boolean shouldFilterBalances) {
		this.shouldFilterBalances = shouldFilterBalances;
		filterStockOnHand(originalList, filteredList);
		orderByColumn(getActiveColumn());
		fireTableDataChanged();
	}

    /**
     * Sets filter overplus.
     *
     * @param shouldFilterOverplus the should filter overplus
     */
    public void setFilterOverplus(boolean shouldFilterOverplus) {
		this.shouldFilterOverplus = shouldFilterOverplus;
		filterStockOnHand(originalList, filteredList);
		orderByColumn(getActiveColumn());
		fireTableDataChanged();
	}
	
	private void filterStockOnHand(Vector<StockOnHand> original, Vector<StockOnHand> filtered) {
		filtered.clear();
		
		for (StockOnHand anElement : original) {
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
		StockOnHand element = filteredList.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
			return element.getItemNumber();
			
		case 1:
			return element.getItemName();
			
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
	
	private void orderByNumber(Vector<StockOnHand> elements) {
		Collections.sort(elements, new Comparator<StockOnHand>() {

			@Override
			public int compare(StockOnHand left, StockOnHand right) {
				int result = left.compareByItemNumber(right);
				
				if (result == 0) {
					result = left.compareByItemName(right);
					
					if (result == 0) {
						return left.compareByIdentity(right);
					}
				}
				
				return result;
			}
		});
	}

	private void orderByName(Vector<StockOnHand> elements) {		
		Collections.sort(elements, new Comparator<StockOnHand>() {

			@Override
			public int compare(StockOnHand left, StockOnHand right) {
				int result = left.compareByItemName(right);
				
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
	
	private void orderByIdentity(Vector<StockOnHand> elements) {		
		Collections.sort(elements, new Comparator<StockOnHand>() {

			@Override
			public int compare(StockOnHand left, StockOnHand right) {
				int result = left.compareByIdentity(right);
				
//				if (result == 0) {
//					result = left.compareByItemNumber(right);
//					
//					if (result == 0) {
//						result = left.compareByItemName(right);
//					}
//				}
				
				return result;
			}
		});
	}
}
