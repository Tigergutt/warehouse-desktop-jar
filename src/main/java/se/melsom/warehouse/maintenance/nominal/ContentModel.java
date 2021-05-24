package se.melsom.warehouse.maintenance.nominal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.data.vo.NominalInventoryVO;
import se.melsom.warehouse.model.EntityName;

import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class ContentModel extends AbstractTableModel  {
	private static final Logger logger = LoggerFactory.getLogger(ContentModel.class);

    public static final String[] columnNames = {
			EntityName.INVENTORY_SOURCE,
			EntityName.ITEM_NUMBER, 
			EntityName.ITEM_NAME,
			EntityName.INVENTORY_NOMINAL_QUANTITY,
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

	private final Vector<NominalInventoryVO> instanceList = new Vector<>();

    public ContentModel() {
		if (columnNames.length != columnWidts.length || columnNames.length != columnClass.length) {
			logger.warn("Array dimension mismatch.");
		}
	}

    public Vector<NominalInventoryVO> getInstances() {
		return instanceList;
	}

    public NominalInventoryVO getInstance(int instanceIndex) {
		if (instanceIndex >= 0 && instanceIndex < instanceList.size()) {
			return instanceList.get(instanceIndex);
		}
		
		return null;
	}

    public int insert(NominalInventoryVO anInstance) {
		instanceList.addElement(anInstance);
		sortInstanceList();
		fireTableDataChanged();
		
		return getIndex(anInstance);
	}

    public int update(NominalInventoryVO anInstance, int atIndex) {
		instanceList.remove(atIndex);
		instanceList.addElement(anInstance);
		sortInstanceList();
		fireTableDataChanged();
		
		return getIndex(anInstance);
	}

    public NominalInventoryVO remove(int atIndex) {
		NominalInventoryVO instance = instanceList.remove(atIndex);

		sortInstanceList();
		fireTableDataChanged();

		return instance;
	}

    public void setInstances(Vector<NominalInventoryVO> instances) {
		logger.debug("Product list updated");
		instanceList.clear();
		instanceList.addAll(instances);
		sortInstanceList();
		fireTableDataChanged();
	}
	
	private void sortInstanceList() {
		Collections.sort(instanceList, new Comparator<NominalInventoryVO>() {

			@Override
			public int compare(NominalInventoryVO left, NominalInventoryVO right) {
				int result = left.getItem().getNumber().compareTo(right.getItem().getNumber());
				
				if (result == 0) {
					result = left.getItem().getName().compareTo(right.getItem().getName());
					
					if (result == 0) {
						result = left.getIdentity().compareTo(right.getIdentity());
					}
				}
				
				return result;
			}
			
		});
	}
	
	private int getIndex(NominalInventoryVO ofInstance) {
		for (int index = 0; index < instanceList.size(); index++) {
			NominalInventoryVO instance = instanceList.get(index);
			
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
		NominalInventoryVO element = instanceList.get(rowIndex);
		
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
			return element.getLastModified();
			
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
