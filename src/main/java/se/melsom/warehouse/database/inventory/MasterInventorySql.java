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

/**
 * The Master inventory sql (select, insert, update, delete).
 */
public class MasterInventorySql {
    /**
     * Select string.
     *
     * @param ofItemId the of item id
     * @param identity the identity
     * @return the string
     */
    public static String select(int ofItemId, String identity) {
		SelectQuery query = createInventorySelect();
		
		if (ofItemId != EntityName.NULL_ID) {
			query.addCondition(BinaryCondition.equalTo(WarehouseSchema.master_inventory_item_id, ofItemId));
		}
		
		if (identity != null && identity.length() > 0) {
			query.addCondition(BinaryCondition.equalTo(WarehouseSchema.master_inventory_identity, identity));
		}		

		query.validate();
		
		return query.toString();
	}

    /**
     * Select string.
     *
     * @param wildcardKey the wildcard key
     * @return the string
     */
    public static String select(String wildcardKey) {
		String sqlWildcardKey = wildcardKey.replace("*", "%");

		SelectQuery query = createInventorySelect();
		
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.master_inventory_item_id, WarehouseSchema.items_id));
		query.addCondition(new ComboCondition(Op.OR, 
				BinaryCondition.like(WarehouseSchema.items_number, sqlWildcardKey),
				BinaryCondition.like(WarehouseSchema.items_name, sqlWildcardKey),
				BinaryCondition.like(WarehouseSchema.master_inventory_annotation, sqlWildcardKey)));		

		query.validate();
		
		return query.toString();
	}


    /**
     * Insert string.
     *
     * @param dao the dao
     * @return the string
     */
    public static String insert(MasterInventoryDAO dao) {
		InsertQuery query = new InsertQuery(WarehouseSchema.master_inventory);		
		
		query.addColumn(WarehouseSchema.master_inventory_id, dao.getId());
		query.addColumn(WarehouseSchema.master_inventory_item_id, dao.getItemId());
		query.addColumn(WarehouseSchema.master_inventory_source, dao.getSource());
		query.addColumn(WarehouseSchema.master_inventory_stock_point, dao.getStockPoint());
		query.addColumn(WarehouseSchema.master_inventory_quantity, dao.getQuantity());
		query.addColumn(WarehouseSchema.master_inventory_identity, dao.getIdentity());
		query.addColumn(WarehouseSchema.master_inventory_annotation, dao.getAnnotation());

		if (dao.getTimestamp() != null && dao.getTimestamp().length() > 0) {
			query.addColumn(WarehouseSchema.master_inventory_last_modified, dao.getTimestamp());
		}

		query.validate();

		return query.toString();
	}

    /**
     * Update string.
     *
     * @param dao the dao
     * @return the string
     */
    public static String update(MasterInventoryDAO dao) {
		UpdateQuery query = null;
		
		query = new UpdateQuery(WarehouseSchema.master_inventory);
		
		query.addSetClause(WarehouseSchema.master_inventory_item_id, dao.getItemId());
		query.addSetClause(WarehouseSchema.master_inventory_source, dao.getSource());
		query.addSetClause(WarehouseSchema.master_inventory_stock_point, dao.getStockPoint());
		query.addSetClause(WarehouseSchema.master_inventory_quantity, dao.getQuantity());
		query.addSetClause(WarehouseSchema.master_inventory_identity, dao.getIdentity());
		query.addSetClause(WarehouseSchema.master_inventory_annotation, dao.getAnnotation());
		
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.master_inventory_id, dao.getId()));
		
		query.validate();
		
		return query.toString();
	}

    /**
     * Delete string.
     *
     * @param dao the dao
     * @return the string
     */
    public static String delete(MasterInventoryDAO dao) {
		DeleteQuery query = null;

		query = new DeleteQuery(WarehouseSchema.master_inventory);	
		
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.master_inventory_id, dao.getId()));

		query.validate();

		return query.toString();
	}

	private static SelectQuery createInventorySelect() {
		SelectQuery query = new SelectQuery();
		
		query.addCustomColumns(
				WarehouseSchema.master_inventory_id,
				WarehouseSchema.master_inventory_item_id,
				WarehouseSchema.master_inventory_source,
				WarehouseSchema.master_inventory_stock_point,
				WarehouseSchema.master_inventory_quantity,
				WarehouseSchema.master_inventory_identity,
				WarehouseSchema.master_inventory_last_modified,
				WarehouseSchema.master_inventory_annotation);
		
		return query;
	}
}
