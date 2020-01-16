package se.melsom.warehouse.importer;

import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.presentation.importer.ImportCell;
import se.melsom.warehouse.presentation.importer.ImportStatus;
import se.melsom.warehouse.presentation.importer.InputTableModel;

public abstract class Importer {
	private InputTableModel tableModel;

	public Importer(InputTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public InputTableModel getTableModel() {
		return tableModel;
	}

	public abstract boolean evaluateColumnIndices();
	
	public abstract void checkValidity(InventoryAccounting inventoryAccounting);

	public abstract void checkConsitency(InventoryAccounting inventoryAccounting);
	
	public abstract boolean anythingToStore();

	public abstract void storeData(InventoryAccounting inventoryAccounting);

	boolean validateStringValue(ImportCell cell) {
		if (cell.getValue() == null) {
			cell.setStatus(ImportStatus.ERROR);
			return false;
		}

		String value = cell.getValue().toString();
		
		if (value.length() == 0) {
			cell.setStatus(ImportStatus.ERROR);

			return false;
		}

		cell.setStatus(ImportStatus.VALID);
		
		return true;
	}

	boolean validateIntegerValue(ImportCell cell) {
		if (cell.getValue() == null) {
			cell.setStatus(ImportStatus.ERROR);
			return false;
		}

		if (cell.getValue() instanceof Double) {
			cell.setValue(new Integer(((Double) cell.getValue()).intValue()));
		}

		if (cell.getValue() instanceof Integer) {
			cell.setStatus(ImportStatus.VALID);
			return true;
		}
		
		cell.setStatus(ImportStatus.ERROR);

		return false;
	}

	public static Importer getImporter(ImportType type, InputTableModel tableModel) {
		Importer importer = null;
		
		switch (type) {	
		case MASTER_INVENTORY:
			importer = new MasterInventoryImporter(tableModel);
			break;
			
		case INVENTORY:
			importer = new ActualInventoryImporter(tableModel);
			break;
			
		case STOCK_LOCATIONS_AND_HOLDINGS:
			importer = new HoldingsImporter(tableModel);
			break;
			
		case ORGANIZATIONAL_UNITS:
			importer = new OrganizationalUnitImporter(tableModel);
			break;
		}
		
		return importer;
	}
}
