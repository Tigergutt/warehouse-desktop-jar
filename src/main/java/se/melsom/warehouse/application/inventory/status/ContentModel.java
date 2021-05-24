package se.melsom.warehouse.application.inventory.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.common.table.SortableTableModel;
import se.melsom.warehouse.data.vo.StockOnHandVO;
import se.melsom.warehouse.model.EntityName;

import java.util.Vector;

/**
 * Tabellen med lager saldo kan sorteras utifrån ett av begreppen:
 * - Item number (M-nummer).
 * - Item namn (FM-log förrådsbeteckning).
 * - Item ID.
 */
public class ContentModel extends SortableTableModel {
	private static final Logger logger = LoggerFactory.getLogger(ContentModel.class);

	// Kolumnnamn/rubrik.
    public static final String[] COLUMN_NAMES = {
			EntityName.ITEM_NUMBER,
			EntityName.ITEM_NAME,
			EntityName.ITEM_PACKAGING,
			EntityName.INVENTORY_NOMINAL_QUANTITY,
			EntityName.INVENTORY_ACTUAL_QUANTITY,
			EntityName.INVENTORY_IDENTITY,
			EntityName.INVENTORY_ANNOTATION
	};

    // Defaultvärden för kolumnbredd.
    public static final int[] COLUMN_WIDTHS = {
			120,
			230,
			50,
			50,
			50,
			100,
			300
	};

    // Anger vilka kolumner som är sorterbara.
    public static final boolean[] IS_COLUMN_SORTABLE = {
			true,
			true,
			false,
			false,
			false,
			true,
			false};

    // Anger kolumners typ av värde.
    public static final Class<?>[] COLUMN_CLASS = {
			String.class,
			String.class,
			String.class,
			Integer.class,
			Integer.class,
			String.class,
			String.class
	};

	// Diverse parametrar som styr vad och hur artiklar ska visas.
	private ItemViewOrder itemViewOrder = ItemViewOrder.NONE;
	private boolean isShowingShortfall = true;
	private boolean isShowingBalances = true;
	private boolean isShowingOverplus = true;

	// Denna lista innehåller de artiklar som faktiskt visas i fönstret.
	private final Vector<StockOnHandVO> filteredStockOnHandVOVector = new Vector<>();
	// Denna lista innehåller samtliga artiklar.
	private final Vector<StockOnHandVO> stockOnHandVOVector = new Vector<>();

	public ContentModel() {
		logger.debug("Execute constructor.");
		if (COLUMN_NAMES.length != COLUMN_WIDTHS.length ||
				COLUMN_NAMES.length != COLUMN_CLASS.length ||
				COLUMN_NAMES.length != IS_COLUMN_SORTABLE.length) {
			logger.warn("Array dimension mismatch.");
		}
	}

	public Vector<StockOnHandVO> getStockOnHand() {
		return filteredStockOnHandVOVector;
	}

	public void setStockOnHand(Vector<StockOnHandVO> stockOnHand) {
		stockOnHandVOVector.clear();
		stockOnHandVOVector.addAll(stockOnHand);
		updateView();
		logger.debug("Inventory updated.");
	}

	protected void updateView() {
		filteredStockOnHandVOVector.clear();
		filterStockOnHand(stockOnHandVOVector, filteredStockOnHandVOVector);
		sortStockOnHand(filteredStockOnHandVOVector);
		fireTableDataChanged();
	}

	// Applicerar aktuellt filter och framställer en filtrerad lista.
	void filterStockOnHand(Vector<StockOnHandVO> original, Vector<StockOnHandVO> filtered) {
		logger.debug("Filter stock on hand shortfall={}, balances={}, overplus={}.", isShowingShortfall, isShowingBalances, isShowingOverplus);
		for (StockOnHandVO anElement : original) {
			if (anElement.getActualQuantity() < anElement.getNominalQuantity()) {
				if (isShowingShortfall) {
					filtered.addElement(anElement);
				}
			} else if (anElement.getActualQuantity() == anElement.getNominalQuantity()) {
				if (isShowingBalances) {
					filtered.addElement(anElement);
				}
			} else {
				if (isShowingOverplus) {
					filtered.addElement(anElement);
				}
			}
		}
	}

	// Sortering kan göras utifrån artikelnummer, benämning eller serienummer
	void sortStockOnHand(Vector<StockOnHandVO> elements) {
		switch (itemViewOrder) {
			case BY_NUMBER:
				elements.sort((StockOnHandVO left, StockOnHandVO right) -> {
					if (left.getNumber().equals(right.getNumber())) {
						if (left.getName().equals(right.getName())) {
							return left.compareByIdentity(right);
						}

						return left.getName().compareTo(right.getName());
					}

					return left.getNumber().compareTo(right.getNumber());
				});
				break;

			case BY_NAME:
				elements.sort((StockOnHandVO left, StockOnHandVO right) -> {
					if (left.getName().equals(right.getName())) {
						if (left.getNumber().equals(right.getNumber())) {
							return left.compareByIdentity(right);
						}

						return left.getNumber().compareTo(right.getNumber());
					}

					return left.getName().compareTo(right.getName());
				});
				break;

			case BY_IDENTITY:
				elements.sort((StockOnHandVO left, StockOnHandVO right) -> {
					if (left.compareByIdentity(right) == 0) {
						if (left.getNumber().equals(right.getNumber())) {
							return left.getName().compareTo(right.getName());
						}

						return left.getNumber().compareTo(right.getNumber());
					}

					return left.compareByIdentity(right);
				});
				break;

			default:
				break;
		}
	}

	public ItemViewOrder getItemViewOrder() {
		return itemViewOrder;
	}

	public void setItemViewOrder(ItemViewOrder itemViewOrder) {
		this.itemViewOrder = itemViewOrder;
		updateView();
	}

	public boolean isShowingShortfall() {
		return isShowingShortfall;
	}

	public void setShowingShortfall(boolean showingShortfall) {
		isShowingShortfall = showingShortfall;
		updateView();
	}

	public boolean isShowingBalances() {
		return isShowingBalances;
	}

	public void setShowingBalances(boolean showingBalances) {
		isShowingBalances = showingBalances;
		updateView();
	}

	public boolean isShowingOverplus() {
		return isShowingOverplus;
	}

	public void setShowingOverPlus(boolean showingOverplus) {
		isShowingOverplus = showingOverplus;
		updateView();
	}

	/*
	 Nedanstående metoder implementerar gränssnitten:
	 - SortedTableModel (och indirekt AbstractTableModel).
	 */
	@Override
	public Class<?> getColumnClass(int col) {
		return COLUMN_CLASS[col];
	}
	
	@Override
	public String getColumnName(int col) {
		return COLUMN_NAMES[col];
	}

	@Override
	public int getRowCount() {
		return filteredStockOnHandVOVector.size();
	}
 
	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		StockOnHandVO element = filteredStockOnHandVOVector.get(rowIndex);
		
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

	@Override
	public int getColumnWidth(int columnIndex) {
		return COLUMN_WIDTHS[columnIndex];
	}

	@Override
	public boolean[] isSortable() {
		return IS_COLUMN_SORTABLE;
	}

	@Override
	public void orderByColumn(int columnIndex) {
		switch (columnIndex) {
			case 0:
				itemViewOrder = ItemViewOrder.BY_NUMBER;
				break;

			case 1:
				itemViewOrder = ItemViewOrder.BY_NAME;
				break;

			case 5:
				itemViewOrder = ItemViewOrder.BY_IDENTITY;
				break;

			default:
				logger.warn("Sorting is not implemented for column index [{}].", columnIndex);
				break;
		}

		updateView();
	}
}
