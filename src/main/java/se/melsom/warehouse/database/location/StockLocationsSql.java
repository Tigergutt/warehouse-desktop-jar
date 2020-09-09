package se.melsom.warehouse.database.location;

import java.sql.Connection;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.DeleteQuery;
import com.healthmarketscience.sqlbuilder.InsertQuery;
import com.healthmarketscience.sqlbuilder.OrderObject.Dir;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.UpdateQuery;

import se.melsom.warehouse.database.WarehouseSchema;

/**
 * The Stock locations sql (select, insert, update, delete).
 */
public class StockLocationsSql {
    /**
     * Select string.
     *
     * @param connection the connection
     * @param atSection  the at section
     * @param inSlot     the in slot
     * @return the string
     */
    public static String select(Connection connection, String atSection, String inSlot) {
		SelectQuery query = new SelectQuery();
		
		query.addAllTableColumns(WarehouseSchema.stock_locations);
		
		if (atSection != null && atSection.length() > 0) {
			query.addCondition(BinaryCondition.equalTo(WarehouseSchema.stock_locations_section, atSection));
		}

		if (inSlot != null && inSlot.length() > 0) {
			query.addCondition(BinaryCondition.equalTo(WarehouseSchema.stock_locations_slot, inSlot));
		}
		
		query.addOrdering(WarehouseSchema.stock_locations_section, Dir.ASCENDING);
		query.addOrdering(WarehouseSchema.stock_locations_slot, Dir.ASCENDING);

		query.validate();
		
		return query.toString();
	}

    /**
     * Insert string.
     *
     * @param connection the connection
     * @param dao        the dao
     * @return the string
     */
    public static String insert(Connection connection, StockLocationDAO dao) {
		InsertQuery query = new InsertQuery(WarehouseSchema.stock_locations);		
		
		query.addColumn(WarehouseSchema.stock_locations_id, dao.getId());
		query.addColumn(WarehouseSchema.stock_locations_section, dao.getSection());
		query.addColumn(WarehouseSchema.stock_locations_slot, dao.getSlot());
		query.addColumn(WarehouseSchema.stock_locations_description, dao.getDescription());
		
		query.validate();

		return query.toString();
	}

    /**
     * Update string.
     *
     * @param connection the connection
     * @param dao        the dao
     * @return the string
     */
    public static String update(Connection connection, StockLocationDAO dao) {
		UpdateQuery query = new UpdateQuery(WarehouseSchema.stock_locations);	
		
		query.addSetClause(WarehouseSchema.stock_locations_section, dao.getSection());
		query.addSetClause(WarehouseSchema.stock_locations_slot, dao.getSlot());
		query.addSetClause(WarehouseSchema.stock_locations_description, dao.getDescription());
		
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.stock_locations_id, dao.getId()));
		
		query.validate();

		return query.toString();
	}

    /**
     * Delete string.
     *
     * @param connection the connection
     * @param dao        the dao
     * @return the string
     */
    public static String delete(Connection connection, StockLocationDAO dao) {
		DeleteQuery query = new DeleteQuery(WarehouseSchema.stock_locations);	

		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.stock_locations_id, dao.getId()));
		
		query.validate();
		
		return query.toString();
	}
}
