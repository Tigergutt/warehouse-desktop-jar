package se.melsom.warehouse.maintenance.applications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.common.table.SortableTableModel;
import se.melsom.warehouse.data.vo.ItemApplicationVO;
import se.melsom.warehouse.model.EntityName;

import java.util.Vector;

public class ContentModel extends SortableTableModel {
	private static final Logger logger = LoggerFactory.getLogger(ContentModel.class);
	private final Vector<ItemApplicationVO> instances = new Vector<>();

    public static final String[] columnNames = {
			"Kategori",
			EntityName.ITEM_NUMBER, 
			EntityName.ITEM_NAME,
			EntityName.INVENTORY_NOMINAL_QUANTITY
	};

    public static final int[] columnWidts = {
			70,
			120, 
			230, 
			75
	};

    public static final Class<?>[] columnClass = {
			String.class, 
			String.class, 
			String.class, 
			Integer.class
	};

    public static final boolean[] isSortableArray = { true, true, true, false };

   	public ContentModel() {
		if (columnNames.length != columnWidts.length || columnNames.length != columnClass.length) {
			logger.warn("Array dimension mismatch.");
		}
	}

    public Vector<ItemApplicationVO> getItems() {
		return instances;
	}

    public void insertItem(ItemApplicationVO itemApplication, int atIndex) {
		instances.insertElementAt(itemApplication, atIndex);
		fireTableRowsInserted(atIndex, atIndex);
	}

    public ItemApplicationVO removeItem(int atIndex) {
		ItemApplicationVO item = instances.remove(atIndex);

		fireTableRowsDeleted(atIndex, atIndex);

		return item;
	}

    public void setItems(Vector<ItemApplicationVO> collection) {
		logger.debug("Product list updated");
		instances.clear();
		instances.addAll(collection);
		fireTableDataChanged();
	}

    public int getIndex(ItemApplicationVO ofApplication) {
		for (int index = 0; index < instances.size(); index++) {
			ItemApplicationVO application = instances.get(index);
			
			if (application.getItem().equals(ofApplication.getItem())) {
				return index;
			}
		}
		
		return -1;
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
		logger.warn("sort by column index=" + columnIndex);
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
		return instances.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ItemApplicationVO element = instances.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
			return element.getCategory();
			
		case 1:
			return element.getItem().getNumber();
			
		case 2:
			return element.getItem().getName();
			
		case 3: 
			return element.getQuantity();

		}

		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
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
		}

		fireTableCellUpdated(rowIndex, columnIndex);
	}
}
