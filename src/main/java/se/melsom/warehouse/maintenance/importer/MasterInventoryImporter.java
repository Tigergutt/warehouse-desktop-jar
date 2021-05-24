package se.melsom.warehouse.maintenance.importer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.model.ItemMasterFile;
import se.melsom.warehouse.model.entity.Item;
import se.melsom.warehouse.model.entity.inventory.MasterInventory;
import se.melsom.warehouse.presentation.importer.ImportCell;
import se.melsom.warehouse.presentation.importer.ImportStatus;
import se.melsom.warehouse.presentation.importer.InputTableModel;

import java.util.Vector;

public class MasterInventoryImporter extends Importer {
	private static final Logger logger = LoggerFactory.getLogger(MasterInventoryImporter.class);
	private final Vector<Item> importedItems = new Vector<>();
	private final Vector<MasterInventory> importedInventoryList = new Vector<>();
	private int itemNumberIndex = -1;
	private int itemNameIndex = -1;
	private int itemPackagingIndex = -1;
	private int inventorySourceIndex = -1;
	private int inventoryStockPointIndex = -1;
	private int inventoryQuantityIndex = -1;
	private int inventoryIdentityIndex = -1;
	private int inventoryAnnotationIndex = -1;


    public MasterInventoryImporter(InputTableModel tableModel) {
		super(tableModel);
	}

	@Override
	public boolean evaluateColumnIndices() {
		for (int columnIndex = 0; columnIndex < getTableModel().getColumnCount(); columnIndex++) {
			switch (getTableModel().getColumnName(columnIndex)) {
			case EntityName.INVENTORY_SOURCE:
				inventorySourceIndex = columnIndex;
				break;
				
			case EntityName.ITEM_NUMBER:
				itemNumberIndex = columnIndex;
				break;
				
			case EntityName.ITEM_NAME:
				itemNameIndex = columnIndex;
				break;
				
			case EntityName.ITEM_PACKAGING:
				itemPackagingIndex = columnIndex;
				break;
				
			case EntityName.INVENTORY_NOMINAL_QUANTITY:
				inventoryQuantityIndex = columnIndex;
				break;
				
			case EntityName.INVENTORY_IDENTITY:
				inventoryIdentityIndex = columnIndex;
				break;
				
			case EntityName.INVENTORY_ANNOTATION:
				inventoryAnnotationIndex = columnIndex;
				break;
				
			case EntityName.STOCK_POINT_DESIGNATION:
				inventoryStockPointIndex = columnIndex;
				break;

			default:
				logger.warn("Unhandled column: #" + columnIndex + " : " + getTableModel().getColumnName(columnIndex));
				break;
			}
		}
		
		if (inventorySourceIndex < 0) {
			return false;
		}
		
		if (inventoryStockPointIndex < 0) {
			return false;
		}
		
		if (itemNumberIndex < 0) {
			return false;
		}
		
		if (itemNameIndex < 0) {
			return false;
		}
		
		if (itemPackagingIndex < 0) {
			return false;
		}
		
		if (inventoryQuantityIndex < 0) {
			return false;
		}
		
		if (inventoryIdentityIndex < 0) {
			return false;
		}

		return inventoryAnnotationIndex >= 0;
	}

	@Override
	public void checkValidity(InventoryAccounting inventoryAccounting) {
		logger.debug("Validating inventory data.");
		evaluateColumnIndices();		
				
		for (int rowIndex = 0; rowIndex < getTableModel().getRowCount(); rowIndex++) {
			validateStringValue(getTableModel().getRow(rowIndex).get(itemNumberIndex));
			validateStringValue(getTableModel().getRow(rowIndex).get(itemNameIndex));
			
			ImportCell packagingCell = getTableModel().getRow(rowIndex).get(itemPackagingIndex);
			if (validateStringValue(packagingCell)) {
				if (packagingCell.getValue().toString().equals("?")) {
					packagingCell.setValue("");
				}
			}
			
			ImportCell sourceCell = getTableModel().getRow(rowIndex).get(inventorySourceIndex);
			if (validateStringValue(sourceCell)) {
				if (sourceCell.getValue().toString().equals("?")) {
					sourceCell.setValue("");
				}
			}

			ImportCell stockPointCell = getTableModel().getRow(rowIndex).get(inventoryStockPointIndex);
			if (validateStringValue(stockPointCell)) {
				if (stockPointCell.getValue().toString().equals("?")) {
					stockPointCell.setValue("");
				}
			}

			ImportCell quantityCell = getTableModel().getRow(rowIndex).get(inventoryQuantityIndex);
			if (!validateIntegerValue(quantityCell)) {
				validateStringValue(quantityCell);
			}

			ImportCell identityCell = getTableModel().getRow(rowIndex).get(inventoryIdentityIndex);
			if (!validateStringValue(identityCell)) {
				identityCell.setValue("");
				identityCell.setStatus(ImportStatus.VALID);
			} else {
				if (identityCell.getValue().toString().equals("Nej")) {
					identityCell.setValue("");
				}
			}

			ImportCell annotationCell = getTableModel().getRow(rowIndex).get(inventoryAnnotationIndex);
			if (!validateStringValue(annotationCell)) {
				annotationCell.setValue("");
				annotationCell.setStatus(ImportStatus.VALID);
			}
		}
	}

	@Override
	public void checkConsitency(InventoryAccounting inventoryAccounting) {
		for (int rowIndex = 0; rowIndex < getTableModel().getRowCount(); rowIndex++) {
			ImportCell itemNumberCell = getTableModel().getRow(rowIndex).get(itemNumberIndex);
			ImportCell itemNameCell = getTableModel().getRow(rowIndex).get(itemNameIndex);
			ImportCell packagingCell = getTableModel().getRow(rowIndex).get(itemPackagingIndex);
			String itemNumber = itemNumberCell.getValue().toString();
			String itemName = itemNameCell.getValue().toString();						
			String packaging = packagingCell.getValue().toString();
			Item importedItem = inventoryAccounting.getItemMasterFile().getItem(itemNumber);
			
			if (importedItem != null) {
				logger.trace("Item designator already exists at row=" + rowIndex);
				itemNumberCell.setStatus(ImportStatus.IGNORED);
				itemNameCell.setStatus(ImportStatus.IGNORED);
				packagingCell.setStatus(ImportStatus.IGNORED);
			} else {
				for (Item anItem : importedItems) {
					if (anItem.getNumber().equals(itemNumber)) {
						importedItem = anItem;
						break;
					}
				}
			}
			
			if (importedItem == null) {
				logger.trace("New item designator at row=" + rowIndex);
				itemNumberCell.setStatus(ImportStatus.OK);
				itemNameCell.setStatus(ImportStatus.OK);
				packagingCell.setStatus(ImportStatus.OK);
				importedItem = new Item(EntityName.NULL_ID, itemNumber, itemName, packaging);
				importedItems.addElement(importedItem);
			}
			
			ImportCell identityCell = getTableModel().getRow(rowIndex).get(inventoryIdentityIndex);
			String identity = identityCell.getValue().toString();
			Vector<MasterInventory> inventoryList = inventoryAccounting.getMasterInventory(importedItem.getId(), identity);		

			ImportCell sourceCell = getTableModel().getRow(rowIndex).get(inventorySourceIndex);
			ImportCell stockPointCell = getTableModel().getRow(rowIndex).get(inventoryStockPointIndex);
			ImportCell quantityCell = getTableModel().getRow(rowIndex).get(inventoryQuantityIndex);
			ImportCell annotationCell = getTableModel().getRow(rowIndex).get(inventoryAnnotationIndex);

			MasterInventory importedInventory = new MasterInventory();
			
			importedInventory.setItem(importedItem);

			importedInventory.setSource(sourceCell.getValue().toString());
			importedInventory.setStockPoint(stockPointCell.getValue().toString());
			importedInventory.setQuantity(((Integer) quantityCell.getValue()));			
			importedInventory.setIdentity(identityCell.getValue().toString());		
			importedInventory.setAnnotation(annotationCell.getValue().toString());
			
			MasterInventory existingInventory = null;
			boolean isEqual = false;
			
			if (inventoryList.size() == 1) {
				existingInventory = inventoryList.get(0);
				logger.trace("Single match for imported inventory=" + importedInventory);
				logger.trace("                 matching inventory=" + existingInventory);
				isEqual = true;
			} else if (inventoryList.size() > 1) {
				logger.trace("Multiple (" + inventoryList.size() + ") match for item=" + importedItem + ",identity='" + identity + "'");
				for (MasterInventory anInventory : inventoryList) {
					if (anInventory.equals(importedInventory)) {
						existingInventory = anInventory;
						logger.trace("Found matching inventory=" + existingInventory);
						isEqual = true;
						break;
					}
				}
				
				if (!isEqual) {
					logger.debug("Could not find match, comparing item numbers.");
					for (MasterInventory anInventory : inventoryList) {
						if (!anInventory.getItem().getNumber().equals(importedInventory.getItem().getNumber())) {
							continue;
						}

						existingInventory = anInventory;
						logger.debug("Found matching inventory=" + existingInventory);
						isEqual = true;
						break;
					}
					
					if (isEqual) {
						logger.debug("Ignoring inventory=" + importedInventory);
						sourceCell.setStatus(ImportStatus.IGNORED);
						stockPointCell.setStatus(ImportStatus.IGNORED);
						identityCell.setStatus(ImportStatus.IGNORED);
						quantityCell.setStatus(ImportStatus.IGNORED);
						annotationCell.setStatus(ImportStatus.IGNORED);
						continue;
					}
				}
			} else {
				logger.trace("No match at all, assuming new inventory=" + importedInventory);				
			}
						
			if (existingInventory != null) {
				importedInventory.setId(existingInventory.getId());
				
				if (importedInventory.getSource().length() == 0) {
					sourceCell.setStatus(ImportStatus.IGNORED);
					importedInventory.setSource(existingInventory.getSource());
				} else if (!existingInventory.getSource().equals(importedInventory.getSource())) {
					sourceCell.setStatus(ImportStatus.OK);
					isEqual = false;
				} else {
					sourceCell.setStatus(ImportStatus.IGNORED);
				}
				
				if (importedInventory.getStockPoint().length() == 0) {
					stockPointCell.setStatus(ImportStatus.IGNORED);
					importedInventory.setStockPoint(existingInventory.getStockPoint());
				} else if (!existingInventory.getStockPoint().equals(importedInventory.getStockPoint())) {
					stockPointCell.setStatus(ImportStatus.OK);
					isEqual = false;
				} else {
					stockPointCell.setStatus(ImportStatus.IGNORED);
				}
				
				if (importedInventory.getIdentity().length() == 0) {
					identityCell.setStatus(ImportStatus.IGNORED);
					importedInventory.setIdentity(existingInventory.getIdentity());
				} else if (!existingInventory.getIdentity().equals(importedInventory.getIdentity())) {
					identityCell.setStatus(ImportStatus.OK);
					isEqual = false;
				} else {
					identityCell.setStatus(ImportStatus.IGNORED);
				}

				if (importedInventory.getQuantity() == 0) {
					quantityCell.setStatus(ImportStatus.IGNORED);
					importedInventory.setQuantity(existingInventory.getQuantity());
				} else if (existingInventory.getQuantity() != importedInventory.getQuantity()) {
					quantityCell.setStatus(ImportStatus.OK);
					isEqual = false;
				} else {
					quantityCell.setStatus(ImportStatus.IGNORED);
				}
				
				if (existingInventory.getAnnotation().equals(importedInventory.getAnnotation())) {
					annotationCell.setStatus(ImportStatus.IGNORED);
				} else {
					annotationCell.setStatus(ImportStatus.OK);
					isEqual = false;
				}				
			} else {
				importedInventory.setId(EntityName.NULL_ID);
				sourceCell.setStatus(ImportStatus.OK);
				stockPointCell.setStatus(ImportStatus.OK);
				identityCell.setStatus(ImportStatus.OK);
				quantityCell.setStatus(ImportStatus.OK);
				annotationCell.setStatus(ImportStatus.OK);
				isEqual = false;
			}
			
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

		return importedInventoryList.size() > 0;
	}

	@Override
	public void storeData(InventoryAccounting inventoryAccounting) {
		logger.debug("Storing inventory data.");
		ItemMasterFile itemMasterFile = inventoryAccounting.getItemMasterFile();
		int nextItemId = itemMasterFile.getNextItemId();
		
		for (Item anItem : importedItems) {
			anItem.setId(nextItemId++);
			logger.trace("{}", anItem);
			itemMasterFile.addItem(anItem);
		}
				
		int nextInventoryId = -1;//inventoryAccounting.getNextMasterInventoryId();
		
		for (MasterInventory anInventory : importedInventoryList) {
			if (anInventory.getId() == EntityName.NULL_ID) {
				logger.trace("Adding inventory=" + anInventory);				
				anInventory.setId(nextInventoryId++);
				inventoryAccounting.addInventory(anInventory);
			} else {
				logger.trace("Updating inventory=" + anInventory);				
				inventoryAccounting.updateInventory(anInventory);
			}
		}
	}
}
