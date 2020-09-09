package se.melsom.warehouse.database.unit;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.DeleteQuery;
import com.healthmarketscience.sqlbuilder.InsertQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.UpdateQuery;

import se.melsom.warehouse.database.WarehouseSchema;
import se.melsom.warehouse.model.EntityName;

/**
 * The Organizational units sql (select, insert, update, delete).
 */
public class OrganizationalUnitsSql {
    /**
     * Generates SQL-query for finding Organizational Units by unit id, call sign, unit name, and/or superior unit id.
     *
     * @param byUnitId         unit id or null
     * @param byCallSign       call sign or null
     * @param byName           unit name or null
     * @param bySuperiorUnitId superior unit id or null
     * @return SQL-query
     */
    public static String select(int byUnitId, String byCallSign, String byName, int bySuperiorUnitId) {
		SelectQuery query = new SelectQuery();
		
		query.addAllTableColumns(WarehouseSchema.units);

		if (byUnitId != EntityName.NULL_ID) {
			query.addCondition(BinaryCondition.equalTo(WarehouseSchema.units_id, byUnitId));
		}
		
		if (byCallSign != null && byCallSign.length() > 0) {
			query.addCondition(BinaryCondition.equalTo(WarehouseSchema.units_callsign, byCallSign));
		}

		if (byName != null && byName.length() > 0) {
			query.addCondition(BinaryCondition.equalTo(WarehouseSchema.units_name, byName));
		}

		if (bySuperiorUnitId != EntityName.NULL_ID) {
			query.addCondition(BinaryCondition.equalTo(WarehouseSchema.units_superior_unit_id, bySuperiorUnitId));
		}
		
		query.validate();
		
		return query.toString();
	}

    /**
     * Generates SQL for inserting an Organizational Unit into database.
     *
     * @param dao an Organizational Unit
     * @return SQL-query
     */
    public static String insert(OrganizationalUnitDAO dao) {
		InsertQuery query = new InsertQuery(WarehouseSchema.units);		
		
		query.addColumn(WarehouseSchema.units_id, dao.getId());
		query.addColumn(WarehouseSchema.units_callsign, dao.getCallsign());
		query.addColumn(WarehouseSchema.units_name, dao.getName());
		query.addColumn(WarehouseSchema.units_level, dao.getLevel());
		query.addColumn(WarehouseSchema.units_superior_unit_id, dao.getSuperiorId());
		
		query.validate();

		return query.toString();
	}

    /**
     * UGenerates SQL for updating an Organizational Unit into database.
     *
     * @param dao an Organizational Unit
     * @return SQL-query
     */
    public static String update(OrganizationalUnitDAO dao) {
		UpdateQuery query = new UpdateQuery(WarehouseSchema.units);	
		
		query.addSetClause(WarehouseSchema.units_callsign, dao.getCallsign());
		query.addSetClause(WarehouseSchema.units_name, dao.getName());
		query.addSetClause(WarehouseSchema.units_level, dao.getLevel());
		query.addSetClause(WarehouseSchema.units_superior_unit_id, dao.getSuperiorId());
		
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.units_id, dao.getId()));
		
		query.validate();

		return query.toString();
	}

    /**
     * Generates SQL for removing an Organizational Unit from database.
     *
     * @param dao an Organizational Unit
     * @return SQL-query
     */
    public static String delete(OrganizationalUnitDAO dao) {
		DeleteQuery query = new DeleteQuery(WarehouseSchema.units);	
		
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.units_id, dao.getId()));
		
		query.validate();

		return query.toString();
	}
}
