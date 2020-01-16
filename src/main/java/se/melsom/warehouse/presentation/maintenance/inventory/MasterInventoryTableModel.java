package se.melsom.warehouse.presentation.maintenance.inventory;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.inventory.MasterInventory;

@SuppressWarnings("serial")
public class MasterInventoryTableModel extends AbstractTableModel  {
	private static Logger logger = Logger.getLogger(MasterInventoryTableModel.class);
	
	public static final String[] columnNames = { 
			EntityName.INVENTORY_SOURCE,
			EntityName.ITEM_NUMBER, 
			EntityName.ITEM_NAME,
			EntityName.INVENTORY_NOMINAL_QUANTIY,
			EntityName.ITEM_PACKAGING,
			EntityName.INVENTORY_IDENTITY,
			EntityName.INVENTORY_LAST_UPDATED_TIMESTAMP,
			EntityName.INVENTORY_ANNOTATION 
	};
	
	public static final int[] columnWidts = { 
			50,
			120, 
			250, 
			70,
			50,
			100, 
			100,
			300
	};
	
	public static final Class<?>[] columnClass = {
			String.class, 
			String.class, 
			String.class, 
			Integer.class,
			String.class,
			String.class,
			String.class,
			String.class
	};

	private Vector<MasterInventory> instanceList = new Vector<>();
	
	public MasterInventoryTableModel() {
		if (columnNames.length != columnWidts.length || columnNames.length != columnClass.length) {
			logger.warn("Array dimension mismatch.");
		}
	}
	
	public Vector<MasterInventory> getInstances() {
		return instanceList;
	}
	
	public MasterInventory getInstance(int instanceIndex) {
		if (instanceIndex >= 0 && instanceIndex < instanceList.size()) {
			return instanceList.get(instanceIndex);
		}
		
		return null;
	}

	public int insert(MasterInventory anInstance) {
		instanceList.addElement(anInstance);
		sortInstanceList();
		fireTableDataChanged();
		
		return getIndex(anInstance);
	}
	
	public int update(MasterInventory anInstance, int atIndex) {
		instanceList.remove(atIndex);
		instanceList.addElement(anInstance);
		sortInstanceList();
		fireTableDataChanged();
		
		return getIndex(anInstance);
	}
	
	public MasterInventory remove(int atIndex) {
		MasterInventory instance = instanceList.remove(atIndex);

		sortInstanceList();
		fireTableDataChanged();

		return instance;
	}
	
	public void setInstances(Vector<MasterInventory> instances) {
		logger.debug("Product list updated");
		instanceList.clear();
		instanceList.addAll(instances);
		sortInstanceList();
		fireTableDataChanged();
	}
	
	private void sortInstanceList() {
		Collections.sort(instanceList, new Comparator<MasterInventory>() {

			@Override
			public int compare(MasterInventory left, MasterInventory right) {
				int result = left.compareByItemNumber(right);
				
				if (result == 0) {
					result = left.compareByItemName(right);
					
					if (result == 0) {
						result = left.compareByIdentity(right);
					}
				}
				
				return result;
			}
			
		});
	}
	
	private int getIndex(MasterInventory ofInstance) {
		for (int index = 0; index < instanceList.size(); index++) {
			MasterInventory instance = instanceList.get(index);
			
			if (instance.getId() == ofInstance.getId()) {
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
		return instanceList.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		MasterInventory element = instanceList.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
			return element.getSource();
			
		case 1:
			return element.getItem().getNumber();
			
		case 2: 
			return element.getItem().getName();

		case 3: 
			return element.getQuantity();

		case 4: 
			return element.getItem().getPackaging();

		case 5: 
			return element.getIdentity();

		case 6: 
			return element.getTimestamp();
			
		case 7: 
			return element.getAnnotation();
		}

		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
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
}
