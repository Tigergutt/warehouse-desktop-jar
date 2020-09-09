package se.melsom.warehouse.importer;

import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.model.UnitsMasterFile;
import se.melsom.warehouse.model.entity.OrganizationalUnit;
import se.melsom.warehouse.presentation.importer.ImportCell;
import se.melsom.warehouse.presentation.importer.ImportStatus;
import se.melsom.warehouse.presentation.importer.InputTableModel;

/**
 * The type Organizational unit importer.
 */
public class OrganizationalUnitImporter extends Importer {
	private static Logger logger = Logger.getLogger(OrganizationalUnitImporter.class);
	private Vector<OrganizationalUnit> importedUnits = new Vector<>();
	private int callsignIndex = -1;
	private int nameIndex = -1;
	private int superiorCallsignIndex = -1;
	private int levelIndex = -1;

    /**
     * Instantiates a new Organizational unit importer.
     *
     * @param tableModel the table model
     */
    public OrganizationalUnitImporter(InputTableModel tableModel) {
		super(tableModel);
	}
	
	
	@Override
	public boolean evaluateColumnIndices() {
		for (int columnIndex = 0; columnIndex < getTableModel().getColumnCount(); columnIndex++) {
			switch (getTableModel().getColumnName(columnIndex)) {
			case "Callsign":
				callsignIndex = columnIndex;
				break;
				
			case "Name":
				nameIndex = columnIndex;
				break;
				
			case "Superior":
				superiorCallsignIndex = columnIndex;
				break;
				
			case "Level":
				levelIndex = columnIndex;
				break;
				
			default:
				logger.warn("Unhandled column: #" + columnIndex + " : " + getTableModel().getColumnName(columnIndex));
				break;
			}
		}
		
		if (callsignIndex < 0) {
			return false;
		}
		
		if (nameIndex < 0) {
			return false;
		}
		
		if (superiorCallsignIndex < 0) {
			return false;
		}
		
		if (levelIndex < 0) {
			return false;
		}
		
		return true;
	}

	@Override
	public void checkValidity(InventoryAccounting inventoryAccounting) {
		logger.debug("Validating holding data.");
		for (int rowIndex = 0; rowIndex < getTableModel().getRowCount(); rowIndex++) {
			ImportCell callsignCell = getTableModel().getRow(rowIndex).get(callsignIndex);
			
			if (validateStringValue(callsignCell)) {
				if (callsignCell.getValue().toString().length() != 2) {
					callsignCell.setStatus(ImportStatus.WARNING);
				}
			}
			validateStringValue(getTableModel().getRow(rowIndex).get(nameIndex));
			validateStringValue(getTableModel().getRow(rowIndex).get(superiorCallsignIndex));
			validateIntegerValue(getTableModel().getRow(rowIndex).get(levelIndex));
		}		
	}

	@Override
	public void checkConsitency(InventoryAccounting inventoryAccounting) {
		UnitsMasterFile unitsMasterFile = inventoryAccounting.getUnitsMasterFile();
		
		for (int rowIndex = 0; rowIndex < getTableModel().getRowCount(); rowIndex++) {
			ImportCell callsignCell = getTableModel().getRow(rowIndex).get(callsignIndex);
			String callsign = callsignCell.getValue().toString();
			
			logger.trace(callsign);

			ImportCell nameCell = getTableModel().getRow(rowIndex).get(nameIndex);

			if (unitsMasterFile.getUnit(callsign) != null) {
				ImportCell superiorCallsignCell = getTableModel().getRow(rowIndex).get(superiorCallsignIndex);
				ImportCell levelCell = getTableModel().getRow(rowIndex).get(levelIndex);
				
				callsignCell.setStatus(ImportStatus.IGNORED);
				superiorCallsignCell.setStatus(ImportStatus.IGNORED);
				nameCell.setStatus(ImportStatus.IGNORED);
				levelCell.setStatus(ImportStatus.IGNORED);
			} else {
				String name = nameCell.getValue().toString();
				
				callsignCell.setStatus(ImportStatus.OK);
				nameCell.setStatus(ImportStatus.OK);
				OrganizationalUnit importedUnit = new OrganizationalUnit(EntityName.NULL_ID, callsign, name, null);
				
				logger.trace(importedUnit);
				
				importedUnits.addElement(importedUnit);
			}
		}	
		
		for (int rowIndex = 0; rowIndex < getTableModel().getRowCount(); rowIndex++) {
			ImportCell callsignCell = getTableModel().getRow(rowIndex).get(callsignIndex);
			String callsign = callsignCell.getValue().toString();
			OrganizationalUnit importedUnit = null;
			
			for (OrganizationalUnit aUnit : importedUnits) {
				if (aUnit.getCallsign().equals(callsign)) {
					importedUnit = aUnit;
					break;
				}
			}
			
			if (importedUnit == null) {
				continue;
			}
			
			ImportCell superiorCallsignCell = getTableModel().getRow(rowIndex).get(superiorCallsignIndex);
			String superiorCallsign = superiorCallsignCell.getValue().toString();
			OrganizationalUnit superiorUnit = unitsMasterFile.getUnit(superiorCallsign);
			
			if (superiorUnit == null) {
				for (OrganizationalUnit aUnit : importedUnits) {
					if (aUnit.getCallsign().equals(superiorCallsign)) {
						superiorUnit= aUnit;
						break;
					}
				}				
			}
			
			importedUnit.setSuperior(superiorUnit);
						
			ImportCell levelCell = getTableModel().getRow(rowIndex).get(levelIndex);
			int level = ((Integer) levelCell.getValue());
			
			importedUnit.setLevel(level);
			
			if (superiorUnit != null && level > 0 || superiorUnit == null && level == 0) {
				superiorCallsignCell.setStatus(ImportStatus.OK);
				levelCell.setStatus(ImportStatus.OK);
				importedUnits.addElement(importedUnit);
			} else {
				superiorCallsignCell.setStatus(ImportStatus.WARNING);
				levelCell.setStatus(ImportStatus.WARNING);
			}
		}		
	}

	@Override
	public boolean anythingToStore() {
		if (importedUnits.size() > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public void storeData(InventoryAccounting inventoryAccounting) {
		logger.debug("Storing unit data.");
		UnitsMasterFile unitsMasterFile = inventoryAccounting.getUnitsMasterFile();
		int nextUnitId = unitsMasterFile.getNextUnitId();
		
		for (OrganizationalUnit aUnit : importedUnits) {
			aUnit.setId(nextUnitId++);
			unitsMasterFile.addUnit(aUnit);
		}
	}
}
