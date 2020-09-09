package se.melsom.warehouse.importer;

import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.presentation.importer.ImportCell;
import se.melsom.warehouse.presentation.importer.ImportStatus;
import se.melsom.warehouse.presentation.importer.InputTableModel;

/**
 * The type Importer.
 */
public abstract class Importer {
	private InputTableModel tableModel;

    /**
     * Instantiates a new Importer.
     *
     * @param tableModel the table model
     */
    public Importer(InputTableModel tableModel) {
		this.tableModel = tableModel;
	}

    /**
     * Gets table model.
     *
     * @return the table model
     */
    public InputTableModel getTableModel() {
		return tableModel;
	}

    /**
     * Evaluate column indices boolean.
     *
     * @return the boolean
     */
    public abstract boolean evaluateColumnIndices();

    /**
     * Check validity.
     *
     * @param inventoryAccounting the inventory accounting
     */
    public abstract void checkValidity(InventoryAccounting inventoryAccounting);

    /**
     * Check consitency.
     *
     * @param inventoryAccounting the inventory accounting
     */
    public abstract void checkConsitency(InventoryAccounting inventoryAccounting);

    /**
     * Anything to store boolean.
     *
     * @return the boolean
     */
    public abstract boolean anythingToStore();

    /**
     * Store data.
     *
     * @param inventoryAccounting the inventory accounting
     */
    public abstract void storeData(InventoryAccounting inventoryAccounting);

    /**
     * Validate string value boolean.
     *
     * @param cell the cell
     * @return the boolean
     */
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

    /**
     * Validate integer value boolean.
     *
     * @param cell the cell
     * @return the boolean
     */
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

    /**
     * Gets importer.
     *
     * @param type       the type
     * @param tableModel the table model
     * @return the importer
     */
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
