package se.melsom.warehouse.presentation.maintenance.inventory;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.inventory.MasterInventory;

/**
 * The type Master inventory table model.
 */
@SuppressWarnings("serial")
public class MasterInventoryTableModel extends AbstractTableModel  {
	private static Logger logger = Logger.getLogger(MasterInventoryTableModel.class);

    /**
     * The constant columnNames.
     */
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

    /**
     * The constant columnWidts.
     */
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

    /**
     * The constant columnClass.
     */
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

    /**
     * Instantiates a new Master inventory table model.
     */
    public MasterInventoryTableModel() {
		if (columnNames.length != columnWidts.length || columnNames.length != columnClass.length) {
			logger.warn("Array dimension mismatch.");
		}
	}

    /**
     * Gets instances.
     *
     * @return the instances
     */
    public Vector<MasterInventory> getInstances() {
		return instanceList;
	}

    /**
     * Gets instance.
     *
     * @param instanceIndex the instance index
     * @return the instance
     */
    public MasterInventory getInstance(int instanceIndex) {
		if (instanceIndex >= 0 && instanceIndex < instanceList.size()) {
			return instanceList.get(instanceIndex);
		}
		
		return null;
	}

    /**
     * Insert int.
     *
     * @param anInstance the an instance
     * @return the int
     */
    public int insert(MasterInventory anInstance) {
		instanceList.addElement(anInstance);
		sortInstanceList();
		fireTableDataChanged();
		
		return getIndex(anInstance);
	}

    /**
     * Update int.
     *
     * @param anInstance the an instance
     * @param atIndex    the at index
     * @return the int
     */
    public int update(MasterInventory anInstance, int atIndex) {
		instanceList.remove(atIndex);
		instanceList.addElement(anInstance);
		sortInstanceList();
		fireTableDataChanged();
		
		return getIndex(anInstance);
	}

    /**
     * Remove master inventory.
     *
     * @param atIndex the at index
     * @return the master inventory
     */
    public MasterInventory remove(int atIndex) {
		MasterInventory instance = instanceList.remove(atIndex);

		sortInstanceList();
		fireTableDataChanged();

		return instance;
	}

    /**
     * Sets instances.
     *
     * @param instances the instances
     */
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
