package se.melsom.warehouse.database.item;

import java.sql.Connection;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.DeleteQuery;
import com.healthmarketscience.sqlbuilder.InsertQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.UpdateQuery;
import com.healthmarketscience.sqlbuilder.OrderObject.Dir;

import se.melsom.warehouse.database.WarehouseSchema;

public class ItemSql {
	public static String select(Connection connection, String designatedAs, String withName) {
		SelectQuery query = new SelectQuery();

		query.addAllTableColumns(WarehouseSchema.items);
		
		if (designatedAs != null && designatedAs.length() > 0) {
			query.addCondition(BinaryCondition.equalTo(WarehouseSchema.items_number, designatedAs));
		}
		
		if (withName != null && withName.length() > 0) {
			query.addCondition(BinaryCondition.equalTo(WarehouseSchema.items_name, withName));
		}
		
		query.addOrdering(WarehouseSchema.items_number, Dir.ASCENDING);
		query.addOrdering(WarehouseSchema.items_name, Dir.ASCENDING);
		
		query.validate();

		return query.toString();
	}

	public static String insert(Connection connection, ItemDAO dao) {
		InsertQuery query = new InsertQuery(WarehouseSchema.items);		
		
		query.addColumn(WarehouseSchema.items_id, dao.getId());
		query.addColumn(WarehouseSchema.items_number, dao.getNumber());
		query.addColumn(WarehouseSchema.items_name, dao.getName());
		query.addColumn(WarehouseSchema.items_packaging, dao.getPackaging());
		query.addColumn(WarehouseSchema.items_description, dao.getDescription());
		
		query.validate();

		return query.toString();
	}

	public static String update(Connection connection, ItemDAO dao) {
		UpdateQuery query = new UpdateQuery(WarehouseSchema.items);
		
		query.addSetClause(WarehouseSchema.items_number, dao.getNumber());
		query.addSetClause(WarehouseSchema.items_name, dao.getName());
		query.addSetClause(WarehouseSchema.items_packaging, dao.getPackaging());
		query.addSetClause(WarehouseSchema.items_description, dao.getDescription());

		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.items_id, dao.getId()));

		query.validate();
		
		return query.toString();
	}

	public static String delete(Connection connection, ItemDAO dao) {
		DeleteQuery query = new DeleteQuery(WarehouseSchema.items);
		
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.items_id, dao.getId()));

		query.validate();
		
		return query.toString();
	}
}
