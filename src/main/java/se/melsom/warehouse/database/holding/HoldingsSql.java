package se.melsom.warehouse.database.holding;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.DeleteQuery;
import com.healthmarketscience.sqlbuilder.InsertQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.UpdateQuery;

import se.melsom.warehouse.database.WarehouseSchema;
import se.melsom.warehouse.model.EntityName;

/**
 * The Holdings sql commands.
 */
public class HoldingsSql {
    /**
     * Select string.
     *
     * @param byHoldingUnitId the by holding unit id
     * @param atLocationId    the at location id
     * @return the string
     */
    public static String select(int byHoldingUnitId, int atLocationId) {
		SelectQuery query = new SelectQuery();

		query.addAllTableColumns(WarehouseSchema.holdings);
		
		if (byHoldingUnitId != EntityName.NULL_ID) {
			query.addCondition(BinaryCondition.equalTo(WarehouseSchema.holdings_unit_id, byHoldingUnitId));
		}
		
		if (atLocationId != EntityName.NULL_ID) {
			query.addCondition(BinaryCondition.equalTo(WarehouseSchema.holdings_stock_location_id, atLocationId));
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
    public static String insert(HoldingDAO dao) {
		InsertQuery query = new InsertQuery(WarehouseSchema.holdings);		
		
		query.addColumn(WarehouseSchema.holdings_unit_id, dao.getUnitId());
		query.addColumn(WarehouseSchema.holdings_stock_location_id, dao.getLocationId());
		
		query.validate();

		return query.toString();
	}

    /**
     * Update string.
     *
     * @param dao the dao
     * @return the string
     */
    public static String update(HoldingDAO dao) {
		UpdateQuery query = new UpdateQuery(WarehouseSchema.holdings);		
		
		query.addSetClause(WarehouseSchema.holdings_unit_id, dao.getUnitId());
		query.addSetClause(WarehouseSchema.holdings_stock_location_id, dao.getLocationId());
		
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.holdings_unit_id, dao.getUnitId()));
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.holdings_stock_location_id, dao.getLocationId()));

		query.validate();

		return query.toString();
	}

    /**
     * Delete string.
     *
     * @param dao the dao
     * @return the string
     */
    public static String delete(HoldingDAO dao) {
		DeleteQuery query = new DeleteQuery(WarehouseSchema.holdings);		
		
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.holdings_unit_id, dao.getUnitId()));
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.holdings_stock_location_id, dao.getLocationId()));

		query.validate();

		return query.toString();
	}
}
