package se.melsom.warehouse.maintenance.organization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.inventory.ActualInventory;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class ContentModel extends AbstractTableModel  {
	private static final Logger logger = LoggerFactory.getLogger(ContentModel.class);
	private final Vector<ActualInventory> instances = new Vector<>();

    public static final String[] columnNames = {
			EntityName.ITEM_NUMBER, 
			EntityName.ITEM_NAME,
			EntityName.STOCK_LOCATION_DESIGNATION,
			EntityName.INVENTORY_IDENTITY
	};

    public static final int[] columnWidts = {
			120, 
			230, 
			70,
			120, 
			75
	};

    public static final Class<?>[] columnClass = {
			String.class, 
			String.class, 
			String.class, 
			String.class
	};

	private boolean isExtendedEditActive = false;

    public ContentModel() {
		if (columnNames.length != columnWidts.length || columnNames.length != columnClass.length) {
			logger.warn("Array dimension mismatch.");
		}
	}

    public Vector<ActualInventory> getInstances() {
		return instances;
	}

    public void insertItem(ActualInventory product, int atIndex) {
		instances.insertElementAt(product, atIndex);
		fireTableRowsInserted(atIndex, atIndex);
	}

    public ActualInventory removeItem(int atIndex) {
		ActualInventory product = instances.remove(atIndex);

		fireTableRowsDeleted(atIndex, atIndex);

		return product;
	}

    public void setInstances(Vector<ActualInventory> collection) {
		logger.debug("Product list updated");
		instances.clear();
		instances.addAll(collection);
		fireTableDataChanged();
	}

    public void setExtendedEditActive(boolean value) {
		isExtendedEditActive = value;
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
		ActualInventory element = instances.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
			return element.getItem().getNumber();
			
		case 1:
			return element.getItem().getName();
			
		case 2: 
			return element.getLocation().getSection() + element.getLocation().getSlot();

		case 3: 
			return element.getIdentity();

		}

		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
		case 1:
		case 2: 
			break;
			
		case 3: 
			return isExtendedEditActive;

		case 4: 
			return true;

		default:
			logger.warn("Unknown column= " + columnIndex);
		}
		
		return false;
	}
	
	@Override	
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		ActualInventory element = instances.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
		case 1:
		case 2: 
			break;

		case 3: 
			element.setIdentity((String) value);
			break;

		default:
			logger.warn("Can not edit column= " + columnIndex);
			return;
		}

		fireTableCellUpdated(rowIndex, columnIndex);
	}
}
