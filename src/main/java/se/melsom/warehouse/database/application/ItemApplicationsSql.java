package se.melsom.warehouse.database.application;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.DeleteQuery;
import com.healthmarketscience.sqlbuilder.InsertQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.UpdateQuery;

import se.melsom.warehouse.database.WarehouseSchema;
import se.melsom.warehouse.model.EntityName;

/**
 * The Item applications sql queries and commands.
 */
public class ItemApplicationsSql {
    /**
     * Select string.
     *
     * @param byUnitId   the by unit id
     * @param ofCategory the of category
     * @return the string
     */
    public static String select(int byUnitId, String ofCategory) {
		SelectQuery query = new SelectQuery();
		
		query.addAllTableColumns(WarehouseSchema.item_application);

		if (byUnitId != EntityName.NULL_ID) {
			query.addCondition(BinaryCondition.equalTo(WarehouseSchema.item_application_unit_id, byUnitId));
		}
		
		if (ofCategory != null && ofCategory.length() > 0) {
			query.addCondition(BinaryCondition.equalTo(WarehouseSchema.item_application_category, ofCategory));
		}
		
		query.validate();
		
		return query.toString();
	}

    /**
     * Insert string.
     *
     * @param dao the dao
     * @return the string
     */
    public static String insert(ItemApplicationDAO dao) {
		InsertQuery query = new InsertQuery(WarehouseSchema.item_application);		
		
		query.addColumn(WarehouseSchema.item_application_unit_id, dao.getUnitId());
		query.addColumn(WarehouseSchema.item_application_item_id, dao.getItemId());
		query.addColumn(WarehouseSchema.item_application_category, dao.getCategory());
		query.addColumn(WarehouseSchema.item_application_quantity, dao.getQuantity());
		
		query.validate();

		return query.toString();
	}

    /**
     * Update string.
     *
     * @param dao the dao
     * @return the string
     */
    public static String update(ItemApplicationDAO dao) {
		UpdateQuery query = new UpdateQuery(WarehouseSchema.item_application);		
		
		query.addSetClause(WarehouseSchema.item_application_quantity, dao.getQuantity());
		
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.item_application_unit_id, dao.getUnitId()));
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.item_application_item_id, dao.getItemId()));
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.item_application_category, dao.getCategory()));

		query.validate();

		return query.toString();
	}

    /**
     * Delete string.
     *
     * @param dao the dao
     * @return the string
     */
    public static String delete(ItemApplicationDAO dao) {
		DeleteQuery query = new DeleteQuery(WarehouseSchema.item_application);		
		
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.item_application_unit_id, dao.getUnitId()));
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.item_application_item_id, dao.getItemId()));
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.item_application_category, dao.getCategory()));

		query.validate();

		return query.toString();
	}
}
