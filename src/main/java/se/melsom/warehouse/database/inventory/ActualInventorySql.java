package se.melsom.warehouse.database.inventory;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.ComboCondition;
import com.healthmarketscience.sqlbuilder.ComboCondition.Op;
import com.healthmarketscience.sqlbuilder.DeleteQuery;
import com.healthmarketscience.sqlbuilder.InsertQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.UpdateQuery;

import se.melsom.warehouse.database.WarehouseSchema;
import se.melsom.warehouse.model.EntityName;

public class ActualInventorySql {
	public static String select(int ofItemId, int atLocationId, String withIdentity) {
		SelectQuery query = createInventorySelect();
		
		if (ofItemId != EntityName.NULL_ID) {
			query.addCondition(BinaryCondition.equalTo(WarehouseSchema.actual_inventory_item_id, ofItemId));
		}
		
		if (atLocationId != EntityName.NULL_ID) {
			query.addCondition(BinaryCondition.equalTo(WarehouseSchema.actual_inventory_stock_location_id, atLocationId));
		}

		if (withIdentity != null  && withIdentity.length() > 0) {
			query.addCondition(BinaryCondition.equalTo(WarehouseSchema.actual_inventory_identity, withIdentity));
		}

		query.validate();
		
		return query.toString();
	}
	
	public static String select(String itemNumber, String itemName, String locationSection, String locationSlot) {
		SelectQuery query = createInventorySelect();
		
		boolean isItemNumberDefined = itemNumber != null && itemNumber.length() > 0;
		boolean isItemNameDefined = itemName != null  && itemName.length() > 0;
		
		if (isItemNumberDefined || isItemNameDefined)  {
			if (isItemNumberDefined) {
				query.addCondition(BinaryCondition.equalTo(WarehouseSchema.items_number, itemNumber));
			}
			
			if (isItemNameDefined) {
				query.addCondition(BinaryCondition.equalTo(WarehouseSchema.items_name, itemName));
			}
		}
				
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.items_id, WarehouseSchema.actual_inventory_item_id));

		boolean isLocationSectionDefined = locationSection != null  && locationSection.length() > 0;
		boolean isLocationSlotDefined = locationSlot != null  && locationSlot.length() > 0;
		
		if (isLocationSectionDefined || isLocationSlotDefined) {
			if (isLocationSectionDefined) {
				query.addCondition(BinaryCondition.equalTo(WarehouseSchema.stock_locations_section, locationSection));
			}
			
			if (isLocationSlotDefined) {
				query.addCondition(BinaryCondition.equalTo(WarehouseSchema.stock_locations_slot, locationSlot));
			}
		}
				
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.stock_locations_id, WarehouseSchema.actual_inventory_stock_location_id));

		query.validate();
		
		return query.toString();
	}

	public static String select(String wildcardKey) {
		String sqlWildcardKey = wildcardKey.replace("*", "%");

		SelectQuery query = createInventorySelect();
		
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.actual_inventory_stock_location_id, WarehouseSchema.stock_locations_id));
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.actual_inventory_item_id, WarehouseSchema.items_id));
		query.addCondition(new ComboCondition(Op.OR, 
				BinaryCondition.like(WarehouseSchema.items_number, sqlWildcardKey),
				BinaryCondition.like(WarehouseSchema.items_name, sqlWildcardKey),
				BinaryCondition.like(WarehouseSchema.actual_inventory_identity, sqlWildcardKey),
				BinaryCondition.like(WarehouseSchema.actual_inventory_annotation, sqlWildcardKey)));		

		query.validate();
		
		return query.toString();
	}

	public static String insert(ActualInventoryDAO dao) {
		InsertQuery query = new InsertQuery(WarehouseSchema.actual_inventory);		
		
		query.addColumn(WarehouseSchema.actual_inventory_id, dao.getId());
		query.addColumn(WarehouseSchema.actual_inventory_item_id, dao.getItemId());
		query.addColumn(WarehouseSchema.actual_inventory_stock_location_id, dao.getLocationId());
		query.addColumn(WarehouseSchema.actual_inventory_quantity, dao.getQuantity());
		query.addColumn(WarehouseSchema.actual_inventory_identity, dao.getIdentity());
		query.addColumn(WarehouseSchema.actual_inventory_annotation, dao.getAnnotation());
		
		if (dao.getTimestamp() != null && dao.getTimestamp().length() > 0) {
			query.addColumn(WarehouseSchema.actual_inventory_last_modified, dao.getTimestamp());
		}

		query.validate();

		return query.toString();
	}
	
	public static String update(ActualInventoryDAO dao) {
		UpdateQuery query = null;
		
		query = new UpdateQuery(WarehouseSchema.actual_inventory);
		
		query.addSetClause(WarehouseSchema.actual_inventory_item_id, dao.getItemId());
		query.addSetClause(WarehouseSchema.actual_inventory_stock_location_id, dao.getLocationId());
		query.addSetClause(WarehouseSchema.actual_inventory_quantity, dao.getQuantity());
		query.addSetClause(WarehouseSchema.actual_inventory_identity, dao.getIdentity());
		query.addSetClause(WarehouseSchema.actual_inventory_annotation, dao.getAnnotation());
		
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.actual_inventory_id, dao.getId()));
		
		query.validate();
		
		return query.toString();
	}

	public static String delete(ActualInventoryDAO dao) {
		DeleteQuery query = null;

		query = new DeleteQuery(WarehouseSchema.actual_inventory);	
		
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.actual_inventory_id, dao.getId()));

		query.validate();

		return query.toString();
	}

	private static SelectQuery createInventorySelect() {
		SelectQuery query = new SelectQuery();
		
		query.addCustomColumns(
				WarehouseSchema.actual_inventory_id,
				WarehouseSchema.actual_inventory_item_id,
				WarehouseSchema.actual_inventory_stock_location_id,
				WarehouseSchema.actual_inventory_quantity,
				WarehouseSchema.actual_inventory_identity,
				WarehouseSchema.actual_inventory_last_modified,
				WarehouseSchema.actual_inventory_annotation);
		
		return query;
	}
}
