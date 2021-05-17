package se.melsom.warehouse.importer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.model.LocationMasterFile;
import se.melsom.warehouse.model.entity.Holding;
import se.melsom.warehouse.model.entity.OrganizationalUnit;
import se.melsom.warehouse.model.entity.StockLocation;
import se.melsom.warehouse.presentation.importer.ImportCell;
import se.melsom.warehouse.presentation.importer.ImportStatus;
import se.melsom.warehouse.presentation.importer.InputTableModel;

import java.util.Vector;

public class HoldingsImporter extends Importer {
	private static final Logger logger = LoggerFactory.getLogger(HoldingsImporter.class);
	private final Vector<StockLocation> importedLocations = new Vector<>();
	private final Vector<Holding> importedHoldings = new Vector<>();
	private int locationIndex = -1;
	private int unitIndex = -1;

    public HoldingsImporter(InputTableModel tableModel) {
		super(tableModel);
	}
	
	@Override
	public boolean evaluateColumnIndices() {
		for (int columnIndex = 0; columnIndex < getTableModel().getColumnCount(); columnIndex++) {
			switch (getTableModel().getColumnName(columnIndex)) {
			case EntityName.STOCK_LOCATION_DESIGNATION:
				locationIndex = columnIndex;
				break;
				
			case EntityName.HOLDING_UNIT:
				unitIndex = columnIndex;
				break;
				
			default:
				logger.warn("Unhandled column: #" + columnIndex + " : " + getTableModel().getColumnName(columnIndex));
				break;
			}
		}
		
		if (locationIndex < 0) {
			return false;
		}

        return unitIndex >= 0;
    }

	@Override
	public void checkValidity(InventoryAccounting inventoryAccounting) {
		logger.debug("Validating holding data.");
		evaluateColumnIndices();		
		
		for (int rowIndex = 0; rowIndex < getTableModel().getRowCount(); rowIndex++) {
			validateStringValue(getTableModel().getRow(rowIndex).get(unitIndex));
			ImportCell locationCell = getTableModel().getRow(rowIndex).get(locationIndex);
			
			if (validateStringValue(locationCell)) {
				String location = locationCell.getValue().toString();			
				String section = location.substring(0, location.indexOf('.'));
				String slot = location.substring(1 + location.indexOf('.'));
				
				if (section.length() == 0 || slot.length() == 0) {
					logger.trace("Stock location designator is not defined at row=" + rowIndex);
					locationCell.setStatus(ImportStatus.ERROR);
				}
			}
		}		
	}

	@Override
	public void checkConsitency(InventoryAccounting inventoryAccounting) {
		for (int rowIndex = 0; rowIndex < getTableModel().getRowCount(); rowIndex++) {
			ImportCell locationCell = getTableModel().getRow(rowIndex).get(locationIndex);
			String location = locationCell.getValue().toString();			
			String section = location.substring(0, location.indexOf('.'));
			String slot = location.substring(1 + location.indexOf('.'));			
			StockLocation stockLocation = inventoryAccounting.getLocationMasterFile().getLocation(section, slot);
			
			if (stockLocation != null) {
				locationCell.setStatus(ImportStatus.IGNORED);
			} else {
				locationCell.setStatus(ImportStatus.OK);
				stockLocation = new StockLocation(EntityName.NULL_ID, section, slot, "");
				importedLocations.addElement(stockLocation);
			}
			
			ImportCell unitCell = getTableModel().getRow(rowIndex).get(unitIndex);
			String unitCallsign = unitCell.getValue().toString();
			OrganizationalUnit unit = inventoryAccounting.getUnitsMasterFile().getUnit(unitCallsign);
			
			if (unit != null) {
				boolean doesExist = false;
				
				for (Holding aHolding : inventoryAccounting.getHoldings(unit.getId())) {
					if (aHolding.getLocation().compareTo(stockLocation) == 0) {
						doesExist = true;
						break;
					}
				}
				
				if (doesExist) {
					unitCell.setStatus(ImportStatus.IGNORED);					
				} else {
					unitCell.setStatus(ImportStatus.OK);					
					importedHoldings.addElement(new Holding(unit, stockLocation));
				}				
			} else {
				unitCell.setStatus(ImportStatus.ERROR);
			}
		}		
	}

	@Override
	public boolean anythingToStore() {
		if (importedLocations.size() > 0) {
			return true;
		}

        return importedHoldings.size() > 0;
    }

	@Override
	public void storeData(InventoryAccounting inventoryAccounting) {
		logger.debug("Storing inventory data.");
		LocationMasterFile locationMasterFile = inventoryAccounting.getLocationMasterFile();
		int nextLocationId = locationMasterFile.getNextLocationId();
		
		for (StockLocation aLocation : importedLocations) {
			aLocation.setId(nextLocationId++);
			logger.trace("{}", aLocation);
			locationMasterFile.addLocation(aLocation);
		}

		for (Holding aHolding : importedHoldings) {
			inventoryAccounting.addHolding(aHolding);
		}
	}
}
