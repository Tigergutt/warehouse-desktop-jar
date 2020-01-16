package se.melsom.warehouse.command;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.database.WarehouseDatabase;
import se.melsom.warehouse.database.application.ItemApplicationDAO;
import se.melsom.warehouse.database.holding.HoldingDAO;
import se.melsom.warehouse.database.inventory.ActualInventoryDAO;
import se.melsom.warehouse.database.inventory.MasterInventoryDAO;
import se.melsom.warehouse.database.unit.OrganizationalUnitDAO;
import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.Item;
import se.melsom.warehouse.model.entity.StockLocation;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.Property;

public class ExportDatabase extends Command {
	private static Logger logger = Logger.getLogger(ExportDatabase.class);
	private ApplicationController controller;
	
	public ExportDatabase(ApplicationController controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        addItems(workbook);
        addStockLocations(workbook);
        
        addMasterInventory(workbook);
        addActualInventory(workbook);
        addOrganizationalUnits(workbook);
        addItemApplication(workbook);
        addHoldings(workbook);
        
        Iterator<Sheet> iterator = workbook.sheetIterator();
        while (iterator.hasNext()) {
        	Sheet sheet = iterator.next();
        	
    	    for (int index = 0; index < sheet.getRow(0).getPhysicalNumberOfCells(); index++) {
    	    	sheet.autoSizeColumn(index);
    	    }
        }
        
		Property property = PersistentSettings.singleton().getProperty("exportDirectory", ".");

		JFileChooser chooser = new JFileChooser(property.getValue());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel file.", "xlsx");
		String filename = "Databasexport " + EntityName.getCurrentDate() + ".xlsx";
		
		chooser.setFileFilter(filter);
		chooser.setSelectedFile(new File(property.getValue(), filename));

		if (chooser.showSaveDialog(controller.getDesktopView()) != JFileChooser.APPROVE_OPTION) {
			return;
		}
			
        try {
            FileOutputStream outputStream = new FileOutputStream(chooser.getSelectedFile());
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
        	logger.error("Could not save workbook.", e);
        }

        property.setValue(chooser.getSelectedFile().getParent());
	}
	
	private void addItems(XSSFWorkbook workbook) {
		WarehouseDatabase database = WarehouseDatabase.singleton();
		Vector<Item> items = database.selectItems(null, null);
		Sheet sheet = workbook.createSheet("items");
		Row row = sheet.createRow(0);
		int columnIndex = 0;
		
		row.createCell(columnIndex++).setCellValue("id");
		row.createCell(columnIndex++).setCellValue("number");
		row.createCell(columnIndex++).setCellValue("name");
		row.createCell(columnIndex++).setCellValue("packaging");
		row.createCell(columnIndex++).setCellValue("description");
		
		for (int index = 0; index < items.size(); index++) {
			Item anItem = items.get(index);
			
			row = sheet.createRow(index + 1);
			columnIndex = 0;
			
			row.createCell(columnIndex++).setCellValue(anItem.getId());
			row.createCell(columnIndex++).setCellValue(anItem.getNumber());
			row.createCell(columnIndex++).setCellValue(anItem.getName());
			row.createCell(columnIndex++).setCellValue(anItem.getPackaging());
			row.createCell(columnIndex++).setCellValue(anItem.getDescription());
		}
	}
		
	private void addStockLocations(XSSFWorkbook workbook) {
		WarehouseDatabase database = WarehouseDatabase.singleton();
		Vector<StockLocation> locations = database.selectLocations(null, null);
		Sheet sheet = workbook.createSheet("stock_locations");
		Row row = sheet.createRow(0);
		int columnIndex = 0;
		
		row.createCell(columnIndex++).setCellValue("id");
		row.createCell(columnIndex++).setCellValue("section");
		row.createCell(columnIndex++).setCellValue("slot");
		row.createCell(columnIndex++).setCellValue("description");
		
		for (int index = 0; index < locations.size(); index++) {
			StockLocation location = locations.get(index);
			
			row = sheet.createRow(index + 1);
			columnIndex = 0;
			
			row.createCell(columnIndex++).setCellValue(location.getId());
			row.createCell(columnIndex++).setCellValue(location.getSection());
			row.createCell(columnIndex++).setCellValue(location.getSlot());
			row.createCell(columnIndex++).setCellValue(location.getDescription());
		}
	}

	private void addMasterInventory(XSSFWorkbook workbook) {
		WarehouseDatabase database = WarehouseDatabase.singleton();
		Vector<MasterInventoryDAO> inventory = database.selectMasterInventory(EntityName.NULL_ID, null);
		Sheet sheet = workbook.createSheet("master_inventory");
		Row row = sheet.createRow(0);
		int columnIndex = 0;
		
		row.createCell(columnIndex++).setCellValue("id");
		row.createCell(columnIndex++).setCellValue("item_id");
		row.createCell(columnIndex++).setCellValue("source");
		row.createCell(columnIndex++).setCellValue("stock_point");
		row.createCell(columnIndex++).setCellValue("quantity");
		row.createCell(columnIndex++).setCellValue("identity");
		row.createCell(columnIndex++).setCellValue("annotation");
		row.createCell(columnIndex++).setCellValue("last_modified");
		
		for (int index = 0; index < inventory.size(); index++) {
			MasterInventoryDAO dao = inventory.get(index);
			
			row = sheet.createRow(index + 1);
			columnIndex = 0;
			
			row.createCell(columnIndex++).setCellValue(dao.getId());
			row.createCell(columnIndex++).setCellValue(dao.getItemId());
			row.createCell(columnIndex++).setCellValue(dao.getSource());
			row.createCell(columnIndex++).setCellValue(dao.getStockPoint());
			row.createCell(columnIndex++).setCellValue(dao.getQuantity());
			row.createCell(columnIndex++).setCellValue(dao.getIdentity());
			row.createCell(columnIndex++).setCellValue(dao.getAnnotation());
			row.createCell(columnIndex++).setCellValue(dao.getTimestamp());
		}
	}

	private void addActualInventory(XSSFWorkbook workbook) {
		WarehouseDatabase database = WarehouseDatabase.singleton();
		Vector<ActualInventoryDAO> inventory = database.selectActualInventory(EntityName.NULL_ID, EntityName.NULL_ID, null);
		Sheet sheet = workbook.createSheet("actual_inventory");
		Row row = sheet.createRow(0);
		int columnIndex = 0;
		
		row.createCell(columnIndex++).setCellValue("id");
		row.createCell(columnIndex++).setCellValue("item_id");
		row.createCell(columnIndex++).setCellValue("stock_location_id");
		row.createCell(columnIndex++).setCellValue("quantity");
		row.createCell(columnIndex++).setCellValue("identity");
		row.createCell(columnIndex++).setCellValue("annotation");
		row.createCell(columnIndex++).setCellValue("last:modified");

		for (int index = 0; index < inventory.size(); index++) {
			ActualInventoryDAO dao = inventory.get(index);
			
			row = sheet.createRow(index + 1);
			columnIndex = 0;
			
			row.createCell(columnIndex++).setCellValue(dao.getId());
			row.createCell(columnIndex++).setCellValue(dao.getItemId());
			row.createCell(columnIndex++).setCellValue(dao.getLocationId());
			row.createCell(columnIndex++).setCellValue(dao.getQuantity());
			row.createCell(columnIndex++).setCellValue(dao.getIdentity());
			row.createCell(columnIndex++).setCellValue(dao.getAnnotation());
			row.createCell(columnIndex++).setCellValue(dao.getTimestamp());
		}
	}

	private void addOrganizationalUnits(XSSFWorkbook workbook) {
		WarehouseDatabase database = WarehouseDatabase.singleton();
		Vector<OrganizationalUnitDAO> units = database.selectOrganizationalUnits(null, null);
		Sheet sheet = workbook.createSheet("organizational_units");
		Row row = sheet.createRow(0);
		int columnIndex = 0;
		
		row.createCell(columnIndex++).setCellValue("id");
		row.createCell(columnIndex++).setCellValue("callsign");
		row.createCell(columnIndex++).setCellValue("name");
		row.createCell(columnIndex++).setCellValue("level");
		row.createCell(columnIndex++).setCellValue("superior_unit_id");
		
		for (int index = 0; index < units.size(); index++) {
			OrganizationalUnitDAO dao = units.get(index);
			
			row = sheet.createRow(index + 1);
			columnIndex = 0;
			
			row.createCell(columnIndex++).setCellValue(dao.getId());
			row.createCell(columnIndex++).setCellValue(dao.getCallsign());
			row.createCell(columnIndex++).setCellValue(dao.getName());
			row.createCell(columnIndex++).setCellValue(dao.getLevel());
			row.createCell(columnIndex++).setCellValue(dao.getSuperiorId());
		}
	}

	private void addItemApplication(XSSFWorkbook workbook) {
		WarehouseDatabase database = WarehouseDatabase.singleton();
		Vector<ItemApplicationDAO> applications = database.selectItemApplications(EntityName.NULL_ID);
		Sheet sheet = workbook.createSheet("item_application");
		Row row = sheet.createRow(0);
		int columnIndex = 0;
		
		row.createCell(columnIndex++).setCellValue("unit_id");
		row.createCell(columnIndex++).setCellValue("item_id");
		row.createCell(columnIndex++).setCellValue("category");
		row.createCell(columnIndex++).setCellValue("quantity");
		
		for (int index = 0; index < applications.size(); index++) {
			ItemApplicationDAO dao = applications.get(index);
			
			row = sheet.createRow(index + 1);
			columnIndex = 0;
			
			row.createCell(columnIndex++).setCellValue(dao.getUnitId());
			row.createCell(columnIndex++).setCellValue(dao.getItemId());
			row.createCell(columnIndex++).setCellValue(dao.getCategory());
			row.createCell(columnIndex++).setCellValue(dao.getQuantity());
		}
	}
	
	private void addHoldings(XSSFWorkbook workbook) {
		WarehouseDatabase database = WarehouseDatabase.singleton();
		Vector<HoldingDAO> holdings = database.selectHoldings(EntityName.NULL_ID, EntityName.NULL_ID);
		Sheet sheet = workbook.createSheet("holdings");
		Row row = sheet.createRow(0);
		int columnIndex = 0;
		
		row.createCell(columnIndex++).setCellValue("unit_id");
		row.createCell(columnIndex++).setCellValue("stock_location_id");
		
		for (int index = 0; index < holdings.size(); index++) {
			HoldingDAO dao = holdings.get(index);
			
			row = sheet.createRow(index + 1);
			columnIndex = 0;
			
			row.createCell(columnIndex++).setCellValue(dao.getUnitId());
			row.createCell(columnIndex++).setCellValue(dao.getLocationId());
		}
	}
}
