package se.melsom.warehouse.importer;

import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.model.ItemMasterFile;
import se.melsom.warehouse.model.LocationMasterFile;
import se.melsom.warehouse.model.entity.Item;
import se.melsom.warehouse.model.entity.StockLocation;
import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.presentation.importer.ImportCell;
import se.melsom.warehouse.presentation.importer.ImportStatus;
import se.melsom.warehouse.presentation.importer.InputTableModel;

/**
 * The type Actual inventory importer.
 */
public class ActualInventoryImporter extends Importer {
	private static Logger logger = Logger.getLogger(ActualInventoryImporter.class);
	private Vector<Item> importedItems = new Vector<>();
	private Vector<StockLocation> importedLocations = new Vector<>();
	private Vector<ActualInventory> importedInventoryList = new Vector<>();
	private int locationIndex = -1;
	private int itemNumberIndex = -1;
	private int itemNameIndex = -1;
	private int itemActualQuantityIndex = -1;
	private int itemAnnotationIndex = -1;
	private int timestampIndex = -1;


    /**
     * Instantiates a new Actual inventory importer.
     *
     * @param tableModel the table model
     */
    public ActualInventoryImporter(InputTableModel tableModel) {
		super(tableModel);
	}
	
	@Override
	public boolean evaluateColumnIndices() {
		for (int columnIndex = 0; columnIndex < getTableModel().getColumnCount(); columnIndex++) {
			switch (getTableModel().getColumnName(columnIndex)) {
			case EntityName.STOCK_LOCATION_DESIGNATION:
				locationIndex = columnIndex;
				break;
				
			case EntityName.ITEM_NUMBER:
				itemNumberIndex = columnIndex;
				break;
				
			case EntityName.ITEM_NAME:
				itemNameIndex = columnIndex;
				break;
				
			case EntityName.INVENTORY_ACTUAL_QUANTITY:
				itemActualQuantityIndex = columnIndex;
				break;
				
			case EntityName.INVENTORY_ANNOTATION:
				itemAnnotationIndex = columnIndex;
				break;
				
			case EntityName.INVENTORY_LAST_UPDATED_TIMESTAMP:
				timestampIndex = columnIndex;
				break;

			default:
				logger.warn("Unhandled column: #" + columnIndex + " : " + getTableModel().getColumnName(columnIndex));
				break;
			}
		}
		
		if (locationIndex < 0) {
			return false;
		}
		
		if (itemNumberIndex < 0) {
			return false;
		}
		
		if (itemNameIndex < 0) {
			return false;
		}
		
		if (itemActualQuantityIndex < 0) {
			return false;
		}
		
		if (itemAnnotationIndex < 0) {
			return false;
		}
		
		if (timestampIndex < 0) {
			return false;
		}
		
		return true;
	}

	@Override
	public void checkValidity(InventoryAccounting inventoryAccounting) {
		logger.debug("Validating inventory data.");
		evaluateColumnIndices();		
		
		for (int rowIndex = 0; rowIndex < getTableModel().getRowCount(); rowIndex++) {
			validateStringValue(getTableModel().getRow(rowIndex).get(itemNumberIndex));
			validateStringValue(getTableModel().getRow(rowIndex).get(itemNameIndex));
			
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
			
			ImportCell quantityCell = getTableModel().getRow(rowIndex).get(itemActualQuantityIndex);
			if (!validateIntegerValue(quantityCell)) {
				validateStringValue(quantityCell);
			}

			ImportCell annotationCell = getTableModel().getRow(rowIndex).get(itemAnnotationIndex);
			if (!validateStringValue(annotationCell)) {
				annotationCell.setValue("");
				annotationCell.setStatus(ImportStatus.VALID);
			}
			
			ImportCell timestampCell = getTableModel().getRow(rowIndex).get(timestampIndex);
			if (!validateStringValue(timestampCell)) {
				timestampCell.setValue("");
				timestampCell.setStatus(ImportStatus.VALID);
			}
		}		
	}

	@Override
	public void checkConsitency(InventoryAccounting inventoryAccounting) {
		for (int rowIndex = 0; rowIndex < getTableModel().getRowCount(); rowIndex++) {
			ImportCell itemNumberCell = getTableModel().getRow(rowIndex).get(itemNumberIndex);
			ImportCell itemNameCell = getTableModel().getRow(rowIndex).get(itemNameIndex);
			
			if (itemNumberCell.getStatus() != ImportStatus.VALID || itemNameCell.getStatus() != ImportStatus.VALID) {
				continue;
			}
			
			ImportCell locationCell = getTableModel().getRow(rowIndex).get(locationIndex);
			
			if (locationCell.getStatus() != ImportStatus.VALID) {
				continue;
			}

			String itemNumber = itemNumberCell.getValue().toString();
			String itemName = itemNameCell.getValue().toString();						
			Item importedItem = null;
			
			for (Item anItem : inventoryAccounting.getItemMasterFile().getItems()) {
				logger.trace(itemNumber + "?=" + anItem.getNumber());
				if (anItem.getNumber().equals(itemNumber)) {
					itemNumberCell.setStatus(ImportStatus.IGNORED);
					itemNameCell.setStatus(ImportStatus.IGNORED);
					
					if (!anItem.getName().equals(itemName)) {
						itemNameCell.setStatus(ImportStatus.WARNING);
					}
					
					logger.trace(anItem);
					importedItem = anItem;
					break;					
				}
			}
			
			if (importedItem == null) {
				for (Item anItem : importedItems) {
					if (anItem.getNumber().equals(itemNumber)) {
						importedItem = anItem;
						break;
					}
				}
			}
			
			if (importedItem == null) {
				itemNumberCell.setStatus(ImportStatus.OK);
				itemNameCell.setStatus(ImportStatus.OK);
				importedItem = new Item(EntityName.NULL_ID, itemNumber, itemName, "");
				logger.trace(importedItem);
				importedItems.addElement(importedItem);
			}
			
			
			String location = locationCell.getValue().toString();			
			String section = location.substring(0, location.indexOf('.'));
			String slot = location.substring(1 + location.indexOf('.'));			
			StockLocation stockLocation = inventoryAccounting.getLocationMasterFile().getLocation(section, slot);
			
			if (stockLocation != null) {
				locationCell.setStatus(ImportStatus.IGNORED);
			} else {
				// TODO: check already imported!
				locationCell.setStatus(ImportStatus.OK);
				stockLocation = new StockLocation(EntityName.NULL_ID, section, slot, "");
				importedLocations.addElement(stockLocation);
			}
			
			ActualInventory importedInventory = new ActualInventory();
			
			importedInventory.setId(EntityName.NULL_ID);
			importedInventory.setItem(importedItem);
			importedInventory.setLocation(stockLocation);
			
			ImportCell quantityCell = getTableModel().getRow(rowIndex).get(itemActualQuantityIndex);
			int quantity = 1;
			String identity = "";
			boolean isEqual = true;
			Vector<ActualInventory> currentInventoryList = inventoryAccounting.getActualInventory(section, slot);

			if (quantityCell.getValue() instanceof String) {
				identity = quantityCell.getValue().toString().substring(1);
				boolean isDefined = false;

				for (ActualInventory anInventory : currentInventoryList) {
					if (anInventory.getIdentity().equals(identity)) {
						isDefined = true;
						break;
					}
				}
				
				quantityCell.setStatus(isDefined ? ImportStatus.IGNORED : ImportStatus.OK);
				isEqual = isDefined ? isEqual : false;
			} else if (quantityCell.getValue() instanceof Integer) {
				quantity = (Integer) quantityCell.getValue();
				boolean isDefined = false;

				for (ActualInventory anInventory : currentInventoryList) {
					if (anInventory.getQuantity() == quantity) {
						isDefined = true;
						break;
					}
				}
				quantityCell.setStatus(isDefined ? ImportStatus.IGNORED : ImportStatus.OK);
				isEqual = isDefined ? isEqual : false;
			} else {				
				quantityCell.setStatus(ImportStatus.ERROR);
			}
			
			importedInventory.setQuantity(quantity);
			importedInventory.setIdentity(identity);
			
			ImportCell annotationCell = getTableModel().getRow(rowIndex).get(itemAnnotationIndex);
			String annotation = annotationCell.getValue().toString();
			
			boolean isDefined = false;
			for (ActualInventory anInventory : currentInventoryList) {
				if (anInventory.getAnnotation().equals(annotation)) {
					isDefined = true;
					break;
				}
			}
			
			annotationCell.setStatus(isDefined ? ImportStatus.IGNORED : ImportStatus.OK);
			isEqual = isDefined ? isEqual : false;

			importedInventory.setAnnotation(annotation);	
			
			ImportCell timestampCell = getTableModel().getRow(rowIndex).get(timestampIndex);
			String timestamp = timestampCell.getValue() == null ? "" : timestampCell.getValue().toString();

			isDefined = false;
			for (ActualInventory anInventory : currentInventoryList) {
				if (anInventory.getTimestamp().equals(timestamp)) {
					isDefined = true;
					break;
				}
			}
			timestampCell.setStatus(isDefined ? ImportStatus.IGNORED : ImportStatus.OK);
			isEqual = isDefined ? isEqual : false;
			
			importedInventory.setTimestamp(timestamp);				

			if (!isEqual) {
				importedInventoryList.addElement(importedInventory);
			}
		}		
	}

	@Override
	public boolean anythingToStore() {
		if (importedItems.size() > 0) {
			return true;
		}
		
		if (importedLocations.size() > 0) {
			return true;
		}

		if (importedInventoryList.size() > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public void storeData(InventoryAccounting inventoryAccounting) {
		logger.debug("Storing inventory data.");
		ItemMasterFile itemMasterFile = inventoryAccounting.getItemMasterFile();
		int nextItemId = itemMasterFile.getNextItemId();
		
		for (Item anItem : importedItems) {
			anItem.setId(nextItemId++);
			logger.trace(anItem);
			itemMasterFile.addItem(anItem);
		}
		
		LocationMasterFile locationMasterFile = inventoryAccounting.getLocationMasterFile();
		int nextLocationId = locationMasterFile.getNextLocationId();
		
		for (StockLocation aLocation : importedLocations) {
			aLocation.setId(nextLocationId++);
			logger.trace(aLocation);
			locationMasterFile.addLocation(aLocation);
		}
		
		int nextInventoryId = inventoryAccounting.getNextActualInventoryId();

		for (ActualInventory anInventory : importedInventoryList) {
			anInventory.setId(nextInventoryId++);
			logger.trace(anInventory);
			inventoryAccounting.addInventory(anInventory);
		}
	}
}
