package se.melsom.warehouse.database;

import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;

/**
 * This class ..
 * 
 * @author bernard
 *
 */
public class WarehouseSchema {
	static DbSpec spec = new DbSpec();
    static DbSchema schema = spec.addDefaultSchema();

	static final String UNITS_TABLE_NAME = "organizational_units";
	static final String ID_FIELD_NAME = "id";
	static final String CALLSIGN_FIELD_NAME = "callsign";
	static final String NAME_FIELD_NAME = "name";
	static final String LEVEL_FIELD_NAME = "level";
	static final String SUPERIOR_UNIT_KEY_FIELD_NAME = "superior_unit_id";

	public static DbTable units = schema.addTable(UNITS_TABLE_NAME);
	public static DbColumn units_id = units.addColumn(ID_FIELD_NAME, "INT", null);
	public static DbColumn units_callsign = units.addColumn(CALLSIGN_FIELD_NAME, "CHAR", 2);
	public static DbColumn units_name = units.addColumn(NAME_FIELD_NAME, "CHAR", 30);
	public static DbColumn units_level = units.addColumn(LEVEL_FIELD_NAME, "INT", null);
	public static DbColumn units_superior_unit_id = units.addColumn(SUPERIOR_UNIT_KEY_FIELD_NAME, "INT", null);
    
	static final String ITEMS_TABLE_NAME = "items";
	static final String NUMBER_FIELD_NAME = "number";
	static final String PACKAGING_FIELD_NAME = "packaging";
	static final String DESCRIPTION_FIELD_NAME = "description";

	public static DbTable items = schema.addTable(ITEMS_TABLE_NAME);
	public static DbColumn items_id = items.addColumn(ID_FIELD_NAME, "INT", null);
	public static DbColumn items_number = items.addColumn(NUMBER_FIELD_NAME, "CHAR", 16);
	public static DbColumn items_name = items.addColumn(NAME_FIELD_NAME, "CHAR", 50);
	public static DbColumn items_packaging = items.addColumn(PACKAGING_FIELD_NAME, "CHAR", 16);
	public static DbColumn items_description = items.addColumn(DESCRIPTION_FIELD_NAME, "CHAR", 128);
    
	static final String STOCK_LOCATIONS_TABLE_NAME = "stock_locations";
	static final String SECTION_FIELD_NAME = "section";
	static final String SLOT_FIELD_NAME = "slot";

	public static DbTable stock_locations = schema.addTable(STOCK_LOCATIONS_TABLE_NAME);
	public static DbColumn stock_locations_id = stock_locations.addColumn(ID_FIELD_NAME, "INT", null);
	public static DbColumn stock_locations_section = stock_locations.addColumn(SECTION_FIELD_NAME, "CHAR", 10);
	public static DbColumn stock_locations_slot = stock_locations.addColumn(SLOT_FIELD_NAME, "CHAR", 10);
	public static DbColumn stock_locations_description = stock_locations.addColumn(DESCRIPTION_FIELD_NAME, "CHAR", 128);

	static final String ACTUAL_INVENTORY_TABLE_NAME = "actual_inventory";
	static final String ITEM_ID_FIELD_NAME = "item_id";
	static final String STOCK_LOCATION_ID_FIELD_NAME = "stock_location_id";
	static final String QUANTITY_FIELD_NAME = "quantity";
	static final String LAST_MODIFIED_NAME = "last_modified";
	static final String IDENTITY_FIELD_NAME = "identity";
	static final String ANNOTATION_FIELD_NAME = "annotation";

	public static DbTable actual_inventory = schema.addTable(ACTUAL_INVENTORY_TABLE_NAME);
	public static DbColumn actual_inventory_id = actual_inventory.addColumn(ID_FIELD_NAME, "INT", null);
	public static DbColumn actual_inventory_item_id = actual_inventory.addColumn(ITEM_ID_FIELD_NAME, "INT", null);
	public static DbColumn actual_inventory_stock_location_id = actual_inventory.addColumn(STOCK_LOCATION_ID_FIELD_NAME, "INT", null);
	public static DbColumn actual_inventory_quantity = actual_inventory.addColumn(QUANTITY_FIELD_NAME, "INT", null);
	public static DbColumn actual_inventory_identity = actual_inventory.addColumn(IDENTITY_FIELD_NAME, "CHAR", 16);
	public static DbColumn actual_inventory_annotation = actual_inventory.addColumn(ANNOTATION_FIELD_NAME, "CHAR", 50);
	public static DbColumn actual_inventory_last_modified = actual_inventory.addColumn(LAST_MODIFIED_NAME, "TIMESTAMP", null);

	static final String MASTER_INVENTORY_TABLE_NAME = "master_inventory";
	static final String SOURCE_FIELD_NAME = "source";
	static final String STOCK_POINT_FIELD_NAME = "stock_point";

	public static DbTable master_inventory = schema.addTable(MASTER_INVENTORY_TABLE_NAME);
	public static DbColumn master_inventory_id = master_inventory.addColumn(ID_FIELD_NAME, "INT", null);
	public static DbColumn master_inventory_item_id = master_inventory.addColumn(ITEM_ID_FIELD_NAME, "INT", null);
	public static DbColumn master_inventory_source = master_inventory.addColumn(SOURCE_FIELD_NAME, "CHAR", 16);
	public static DbColumn master_inventory_stock_point = master_inventory.addColumn(STOCK_POINT_FIELD_NAME, "CHAR", 16);
	public static DbColumn master_inventory_quantity = master_inventory.addColumn(QUANTITY_FIELD_NAME, "INT", null);
	public static DbColumn master_inventory_identity = master_inventory.addColumn(IDENTITY_FIELD_NAME, "CHAR", 16);
	public static DbColumn master_inventory_annotation = master_inventory.addColumn(ANNOTATION_FIELD_NAME, "CHAR", 50);
	public static DbColumn master_inventory_last_modified = master_inventory.addColumn(LAST_MODIFIED_NAME, "TIMESTAMP", null);

	static final String HOLDINGS_TABLE_NAME = "holdings";
	static final String UNIT_ID_FIELD_NAME = "unit_id";

	public static DbTable holdings = schema.addTable(HOLDINGS_TABLE_NAME);
	public static DbColumn holdings_unit_id = holdings.addColumn(UNIT_ID_FIELD_NAME, "INT", null);
	public static DbColumn holdings_stock_location_id = holdings.addColumn(STOCK_LOCATION_ID_FIELD_NAME, "INT", null);
    
	static final String ITEM_APPLICATION_TABLE_NAME = "item_application";
	static final String CATEGORY_FIELD_NAME = "category";
		
	public static DbTable item_application = schema.addTable(ITEM_APPLICATION_TABLE_NAME);
	public static DbColumn item_application_unit_id = item_application.addColumn(UNIT_ID_FIELD_NAME, "INT", null);
	public static DbColumn item_application_item_id = item_application.addColumn(ITEM_ID_FIELD_NAME, "INT", null);
	public static DbColumn item_application_category = item_application.addColumn(CATEGORY_FIELD_NAME, "CHAR", 50);
	public static DbColumn item_application_quantity = item_application.addColumn(QUANTITY_FIELD_NAME, "INT", null);

	static final String INSTANCES_TABLE_NAME = "instances";
	static final String LOCAL_IDENTITY_FIELD_NAME = "local_identity";
	static final String METER_COUNT_FIELD_NAME = "meter_count";
}
