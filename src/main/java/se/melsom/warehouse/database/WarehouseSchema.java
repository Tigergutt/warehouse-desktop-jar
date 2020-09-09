package se.melsom.warehouse.database;

import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;

/**
 * This class define database schema constants.
 *
 * @author bernard
 */
public class WarehouseSchema {
    /**
     * The Spec.
     */
    static DbSpec spec = new DbSpec();
    /**
     * The Schema.
     */
    static DbSchema schema = spec.addDefaultSchema();

    /**
     * The Units table name.
     */
    static final String UNITS_TABLE_NAME = "organizational_units";
    /**
     * The Id field name.
     */
    static final String ID_FIELD_NAME = "id";
    /**
     * The Callsign field name.
     */
    static final String CALLSIGN_FIELD_NAME = "callsign";
    /**
     * The Name field name.
     */
    static final String NAME_FIELD_NAME = "name";
    /**
     * The Level field name.
     */
    static final String LEVEL_FIELD_NAME = "level";
    /**
     * The Superior unit key field name.
     */
    static final String SUPERIOR_UNIT_KEY_FIELD_NAME = "superior_unit_id";

    /**
     * The constant units.
     */
    public static DbTable units = schema.addTable(UNITS_TABLE_NAME);
    /**
     * The constant units_id.
     */
    public static DbColumn units_id = units.addColumn(ID_FIELD_NAME, "INT", null);
    /**
     * The constant units_callsign.
     */
    public static DbColumn units_callsign = units.addColumn(CALLSIGN_FIELD_NAME, "CHAR", 2);
    /**
     * The constant units_name.
     */
    public static DbColumn units_name = units.addColumn(NAME_FIELD_NAME, "CHAR", 30);
    /**
     * The constant units_level.
     */
    public static DbColumn units_level = units.addColumn(LEVEL_FIELD_NAME, "INT", null);
    /**
     * The constant units_superior_unit_id.
     */
    public static DbColumn units_superior_unit_id = units.addColumn(SUPERIOR_UNIT_KEY_FIELD_NAME, "INT", null);

    /**
     * The Items table name.
     */
    static final String ITEMS_TABLE_NAME = "items";
    /**
     * The Number field name.
     */
    static final String NUMBER_FIELD_NAME = "number";
    /**
     * The Packaging field name.
     */
    static final String PACKAGING_FIELD_NAME = "packaging";
    /**
     * The Description field name.
     */
    static final String DESCRIPTION_FIELD_NAME = "description";

    /**
     * The constant items.
     */
    public static DbTable items = schema.addTable(ITEMS_TABLE_NAME);
    /**
     * The constant items_id.
     */
    public static DbColumn items_id = items.addColumn(ID_FIELD_NAME, "INT", null);
    /**
     * The constant items_number.
     */
    public static DbColumn items_number = items.addColumn(NUMBER_FIELD_NAME, "CHAR", 16);
    /**
     * The constant items_name.
     */
    public static DbColumn items_name = items.addColumn(NAME_FIELD_NAME, "CHAR", 50);
    /**
     * The constant items_packaging.
     */
    public static DbColumn items_packaging = items.addColumn(PACKAGING_FIELD_NAME, "CHAR", 16);
    /**
     * The constant items_description.
     */
    public static DbColumn items_description = items.addColumn(DESCRIPTION_FIELD_NAME, "CHAR", 128);

    /**
     * The Stock locations table name.
     */
    static final String STOCK_LOCATIONS_TABLE_NAME = "stock_locations";
    /**
     * The Section field name.
     */
    static final String SECTION_FIELD_NAME = "section";
    /**
     * The Slot field name.
     */
    static final String SLOT_FIELD_NAME = "slot";

    /**
     * The constant stock_locations.
     */
    public static DbTable stock_locations = schema.addTable(STOCK_LOCATIONS_TABLE_NAME);
    /**
     * The constant stock_locations_id.
     */
    public static DbColumn stock_locations_id = stock_locations.addColumn(ID_FIELD_NAME, "INT", null);
    /**
     * The constant stock_locations_section.
     */
    public static DbColumn stock_locations_section = stock_locations.addColumn(SECTION_FIELD_NAME, "CHAR", 10);
    /**
     * The constant stock_locations_slot.
     */
    public static DbColumn stock_locations_slot = stock_locations.addColumn(SLOT_FIELD_NAME, "CHAR", 10);
    /**
     * The constant stock_locations_description.
     */
    public static DbColumn stock_locations_description = stock_locations.addColumn(DESCRIPTION_FIELD_NAME, "CHAR", 128);

    /**
     * The Actual inventory table name.
     */
    static final String ACTUAL_INVENTORY_TABLE_NAME = "actual_inventory";
    /**
     * The Item id field name.
     */
    static final String ITEM_ID_FIELD_NAME = "item_id";
    /**
     * The Stock location id field name.
     */
    static final String STOCK_LOCATION_ID_FIELD_NAME = "stock_location_id";
    /**
     * The Quantity field name.
     */
    static final String QUANTITY_FIELD_NAME = "quantity";
    /**
     * The Last modified name.
     */
    static final String LAST_MODIFIED_NAME = "last_modified";
    /**
     * The Identity field name.
     */
    static final String IDENTITY_FIELD_NAME = "identity";
    /**
     * The Annotation field name.
     */
    static final String ANNOTATION_FIELD_NAME = "annotation";

    /**
     * The constant actual_inventory.
     */
    public static DbTable actual_inventory = schema.addTable(ACTUAL_INVENTORY_TABLE_NAME);
    /**
     * The constant actual_inventory_id.
     */
    public static DbColumn actual_inventory_id = actual_inventory.addColumn(ID_FIELD_NAME, "INT", null);
    /**
     * The constant actual_inventory_item_id.
     */
    public static DbColumn actual_inventory_item_id = actual_inventory.addColumn(ITEM_ID_FIELD_NAME, "INT", null);
    /**
     * The constant actual_inventory_stock_location_id.
     */
    public static DbColumn actual_inventory_stock_location_id = actual_inventory.addColumn(STOCK_LOCATION_ID_FIELD_NAME, "INT", null);
    /**
     * The constant actual_inventory_quantity.
     */
    public static DbColumn actual_inventory_quantity = actual_inventory.addColumn(QUANTITY_FIELD_NAME, "INT", null);
    /**
     * The constant actual_inventory_identity.
     */
    public static DbColumn actual_inventory_identity = actual_inventory.addColumn(IDENTITY_FIELD_NAME, "CHAR", 16);
    /**
     * The constant actual_inventory_annotation.
     */
    public static DbColumn actual_inventory_annotation = actual_inventory.addColumn(ANNOTATION_FIELD_NAME, "CHAR", 50);
    /**
     * The constant actual_inventory_last_modified.
     */
    public static DbColumn actual_inventory_last_modified = actual_inventory.addColumn(LAST_MODIFIED_NAME, "TIMESTAMP", null);

    /**
     * The Master inventory table name.
     */
    static final String MASTER_INVENTORY_TABLE_NAME = "master_inventory";
    /**
     * The Source field name.
     */
    static final String SOURCE_FIELD_NAME = "source";
    /**
     * The Stock point field name.
     */
    static final String STOCK_POINT_FIELD_NAME = "stock_point";

    /**
     * The constant master_inventory.
     */
    public static DbTable master_inventory = schema.addTable(MASTER_INVENTORY_TABLE_NAME);
    /**
     * The constant master_inventory_id.
     */
    public static DbColumn master_inventory_id = master_inventory.addColumn(ID_FIELD_NAME, "INT", null);
    /**
     * The constant master_inventory_item_id.
     */
    public static DbColumn master_inventory_item_id = master_inventory.addColumn(ITEM_ID_FIELD_NAME, "INT", null);
    /**
     * The constant master_inventory_source.
     */
    public static DbColumn master_inventory_source = master_inventory.addColumn(SOURCE_FIELD_NAME, "CHAR", 16);
    /**
     * The constant master_inventory_stock_point.
     */
    public static DbColumn master_inventory_stock_point = master_inventory.addColumn(STOCK_POINT_FIELD_NAME, "CHAR", 16);
    /**
     * The constant master_inventory_quantity.
     */
    public static DbColumn master_inventory_quantity = master_inventory.addColumn(QUANTITY_FIELD_NAME, "INT", null);
    /**
     * The constant master_inventory_identity.
     */
    public static DbColumn master_inventory_identity = master_inventory.addColumn(IDENTITY_FIELD_NAME, "CHAR", 16);
    /**
     * The constant master_inventory_annotation.
     */
    public static DbColumn master_inventory_annotation = master_inventory.addColumn(ANNOTATION_FIELD_NAME, "CHAR", 50);
    /**
     * The constant master_inventory_last_modified.
     */
    public static DbColumn master_inventory_last_modified = master_inventory.addColumn(LAST_MODIFIED_NAME, "TIMESTAMP", null);

    /**
     * The Holdings table name.
     */
    static final String HOLDINGS_TABLE_NAME = "holdings";
    /**
     * The Unit id field name.
     */
    static final String UNIT_ID_FIELD_NAME = "unit_id";

    /**
     * The constant holdings.
     */
    public static DbTable holdings = schema.addTable(HOLDINGS_TABLE_NAME);
    /**
     * The constant holdings_unit_id.
     */
    public static DbColumn holdings_unit_id = holdings.addColumn(UNIT_ID_FIELD_NAME, "INT", null);
    /**
     * The constant holdings_stock_location_id.
     */
    public static DbColumn holdings_stock_location_id = holdings.addColumn(STOCK_LOCATION_ID_FIELD_NAME, "INT", null);

    /**
     * The Item application table name.
     */
    static final String ITEM_APPLICATION_TABLE_NAME = "item_application";
    /**
     * The Category field name.
     */
    static final String CATEGORY_FIELD_NAME = "category";

    /**
     * The constant item_application.
     */
    public static DbTable item_application = schema.addTable(ITEM_APPLICATION_TABLE_NAME);
    /**
     * The constant item_application_unit_id.
     */
    public static DbColumn item_application_unit_id = item_application.addColumn(UNIT_ID_FIELD_NAME, "INT", null);
    /**
     * The constant item_application_item_id.
     */
    public static DbColumn item_application_item_id = item_application.addColumn(ITEM_ID_FIELD_NAME, "INT", null);
    /**
     * The constant item_application_category.
     */
    public static DbColumn item_application_category = item_application.addColumn(CATEGORY_FIELD_NAME, "CHAR", 50);
    /**
     * The constant item_application_quantity.
     */
    public static DbColumn item_application_quantity = item_application.addColumn(QUANTITY_FIELD_NAME, "INT", null);

    /**
     * The Instances table name.
     */
    static final String INSTANCES_TABLE_NAME = "instances";
    /**
     * The Local identity field name.
     */
    static final String LOCAL_IDENTITY_FIELD_NAME = "local_identity";
    /**
     * The Meter count field name.
     */
    static final String METER_COUNT_FIELD_NAME = "meter_count";
}
