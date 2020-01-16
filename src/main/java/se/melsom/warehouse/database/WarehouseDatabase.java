package se.melsom.warehouse.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.ComboCondition;
import com.healthmarketscience.sqlbuilder.ComboCondition.Op;
import com.healthmarketscience.sqlbuilder.FunctionCall;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery.JoinType;
import com.healthmarketscience.sqlbuilder.UnaryCondition;

import se.melsom.warehouse.database.application.ItemApplicationDAO;
import se.melsom.warehouse.database.application.ItemApplicationsSql;
import se.melsom.warehouse.database.holding.HoldingDAO;
import se.melsom.warehouse.database.holding.HoldingsSql;
import se.melsom.warehouse.database.inventory.ActualInventoryDAO;
import se.melsom.warehouse.database.inventory.ActualInventorySql;
import se.melsom.warehouse.database.inventory.MasterInventoryDAO;
import se.melsom.warehouse.database.inventory.MasterInventorySql;
import se.melsom.warehouse.database.inventory.StockLocationItem;
import se.melsom.warehouse.database.item.ItemDAO;
import se.melsom.warehouse.database.item.ItemSql;
import se.melsom.warehouse.database.location.StockLocationDAO;
import se.melsom.warehouse.database.location.StockLocationsSql;
import se.melsom.warehouse.database.unit.OrganizationalUnitDAO;
import se.melsom.warehouse.database.unit.OrganizationalUnitsSql;
import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.entity.Holding;
import se.melsom.warehouse.model.entity.Item;
import se.melsom.warehouse.model.entity.ItemApplication;
import se.melsom.warehouse.model.entity.OrganizationalUnit;
import se.melsom.warehouse.model.entity.StockLocation;
import se.melsom.warehouse.model.entity.inventory.ActualInventory;
import se.melsom.warehouse.model.entity.inventory.MasterInventory;
import se.melsom.warehouse.model.entity.inventory.StockOnHand;

/**
 * This class ...
 * 
 * @author bernard
 *
 */
public class WarehouseDatabase {
	private static Logger logger = Logger.getLogger(WarehouseDatabase.class);
	private static final String DATABASE_NAME = "warehouse";
	private static final String DATABASE_USERNAME = "warhorse";
	private static final String DATABASE_PASSWORD = "peacekeeper";

	private static WarehouseDatabase singletonInstance = new WarehouseDatabase();
	
	private Connection connection = null;
	
	/**
	 * 
	 */
	private WarehouseDatabase() {
	}

	/**
	 * 
	 * @return
	 */
	public static WarehouseDatabase singleton() {
		return singletonInstance;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return connection != null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean connect() {
		String connectionString = "";
		
		connectionString += "jdbc:mysql://localhost/";
		connectionString += DATABASE_NAME;
		connectionString += "?user=" + DATABASE_USERNAME + "&password=" + DATABASE_PASSWORD;
		
		try {
			connection = DriverManager.getConnection(connectionString);
		} catch (SQLException exception) {
			logger.error("Failed to connect to DB", exception);
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * @return Stock On Hand Vector
	 */	
	public Vector<StockOnHand> selectStockOnHandItems() {
		logger.debug("Select stock on hand data.");
		SelectQuery query = new SelectQuery();
/*
SELECT I.number,I.name,MI.quantity,SUM(AI.quantity),I.packaging,MI.identity,MI.annotation
FROM master_inventory MI
LEFT JOIN actual_inventory AI
ON AI.item_id = MI.item_id
AND AI.identity = MI.identity
INNER JOIN items I
ON I.id = MI.item_id
GROUP BY I.number,I.name,I.packaging,MI.quantity,MI.identity,MI.annotation,MI.id
ORDER BY I.number,I.name,MI.identity		
*/

//		query.addCustomColumns(
//				WarehouseSchema.items_number,
//				WarehouseSchema.items_name,
//				WarehouseSchema.master_inventory_quantity,
//				FunctionCall.sum().addColumnParams(WarehouseSchema.actual_inventory_quantity),
//				WarehouseSchema.items_packaging,
//				WarehouseSchema.master_inventory_identity,
//				WarehouseSchema.master_inventory_annotation);
//		
//		
//		query.addJoin(JoinType.LEFT_OUTER, 
//				WarehouseSchema.master_inventory, WarehouseSchema.actual_inventory, 
//				new ComboCondition(Op.AND, 
//						BinaryCondition.equalTo(WarehouseSchema.master_inventory_item_id, WarehouseSchema.actual_inventory_item_id),
//						BinaryCondition.equalTo(WarehouseSchema.master_inventory_identity, WarehouseSchema.actual_inventory_identity)));
//		
//		query.addJoin(JoinType.INNER, 
//				WarehouseSchema.master_inventory, WarehouseSchema.items, 
//				WarehouseSchema.items_id, WarehouseSchema.master_inventory_item_id);
//
//		query.addGroupings(
//				WarehouseSchema.items_number,
//				WarehouseSchema.items_name,
//				WarehouseSchema.items_packaging,
//				WarehouseSchema.master_inventory_quantity,
//				WarehouseSchema.master_inventory_identity,
//				WarehouseSchema.master_inventory_annotation,
//				WarehouseSchema.master_inventory_id);
//
//		query.addOrderings(
//				WarehouseSchema.items_number,
//				WarehouseSchema.items_name,
//				WarehouseSchema.master_inventory_identity);

/*
 * Second try
SELECT I.number,I.name,MI.quantity,SUM(AI.quantity),I.packaging,MI.identity,MI.annotation
FROM items I
LEFT JOIN master_inventory MI
ON I.id = MI.item_id
LEFT JOIN actual_inventory AI
ON I.id = AI.item_id
AND (MI.identity is null
OR AI.identity is null
OR MI.identity = AI.identity)
GROUP BY I.number,I.name,I.packaging,MI.quantity,MI.identity,MI.annotation,MI.id
ORDER BY I.number,I.name,MI.identity		
;
*/
		query.addCustomColumns(
				WarehouseSchema.items_number,
				WarehouseSchema.items_name,
				WarehouseSchema.master_inventory_quantity,
				FunctionCall.sum().addColumnParams(WarehouseSchema.actual_inventory_quantity),
				WarehouseSchema.items_packaging,
				WarehouseSchema.master_inventory_identity,
				WarehouseSchema.master_inventory_annotation);
		
		query.addJoin(JoinType.LEFT_OUTER, 
				WarehouseSchema.items, WarehouseSchema.master_inventory, 
				WarehouseSchema.items_id, WarehouseSchema.master_inventory_item_id);
		
		query.addJoin(JoinType.LEFT_OUTER, 
				WarehouseSchema.master_inventory, WarehouseSchema.actual_inventory, 
				new ComboCondition(Op.AND, 
						BinaryCondition.equalTo(WarehouseSchema.items_id, WarehouseSchema.actual_inventory_item_id),
						new ComboCondition(Op.OR,
								BinaryCondition.equalTo(WarehouseSchema.master_inventory_identity, WarehouseSchema.actual_inventory_identity),
								UnaryCondition.isNull(WarehouseSchema.master_inventory_identity),
								UnaryCondition.isNull(WarehouseSchema.actual_inventory_identity))));
		
		query.addGroupings(
				WarehouseSchema.items_number,
				WarehouseSchema.items_name,
				WarehouseSchema.items_packaging,
				WarehouseSchema.master_inventory_quantity,
				WarehouseSchema.master_inventory_identity,
				WarehouseSchema.master_inventory_annotation,
				WarehouseSchema.master_inventory_id);

		query.addOrderings(
				WarehouseSchema.items_number,
				WarehouseSchema.items_name,
				WarehouseSchema.master_inventory_identity);
		
		query.validate();

		String sql = query.toString();
		
		logger.debug("SQL=" + sql);

		Vector<StockOnHand> stockOnHandItems = new Vector<>();
		
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			if (resultSet.first()) {
				do {
					int index = 1;
					StockOnHand stockOnHandItem = new StockOnHand();
					
					stockOnHandItem.setItemNumber(resultSet.getString(index++));
					stockOnHandItem.setItemName(resultSet.getString(index++));
					stockOnHandItem.setNominalQuantity(resultSet.getInt(index++));
					stockOnHandItem.setActualQuantity(resultSet.getInt(index++));
					stockOnHandItem.setPackaging(resultSet.getString(index++));
					stockOnHandItem.setIdentity(resultSet.getString(index++));
					stockOnHandItem.setAnnotation(resultSet.getString(index++));

					logger.trace(stockOnHandItem);

					stockOnHandItems.addElement(stockOnHandItem);
				} while (resultSet.next());
			}
		} catch (SQLException exception) {
			logger.error("Caught exception for SQL=" + sql, exception);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		logger.debug("Selected count=" + stockOnHandItems.size());
		return stockOnHandItems;
	}

//	@Deprecated
//	public Vector<StockOnHandItem> selectStockOnHandList() {
//		logger.debug("Select stock on hand list.");
//		SelectQuery query = new SelectQuery();
//		
//		query.addCustomColumns(
//				WarehouseSchema.items_number,
//				WarehouseSchema.items_name,
//				WarehouseSchema.items_packaging,
//				WarehouseSchema.actual_inventory_quantity,
//				WarehouseSchema.actual_inventory_identity);
//		
//		query.addJoin(JoinType.LEFT_OUTER, WarehouseSchema.items, WarehouseSchema.actual_inventory, 
//				BinaryCondition.equalTo(WarehouseSchema.items_id, WarehouseSchema.actual_inventory_item_id));
//
//		query.validate();
//
//		String sql = query.toString();
//		
//		logger.debug("SQL=" + sql);
//
//		Vector<StockOnHandItem> itemList = new Vector<>();
//		
//		Statement statement = null;
//		ResultSet resultSet = null;
//
//		try {
//			statement = connection.createStatement();
//			resultSet = statement.executeQuery(sql);
//			
//			if (resultSet.first()) {
//				do {
//					int index = 1;
//					StockOnHandItem item = new StockOnHandItem();
//
//					item.setNumber(resultSet.getString(index++));
//					item.setName(resultSet.getString(index++));
//					item.setPackaging(resultSet.getString(index++));
//					item.setNominalQuantity(resultSet.getInt(index++));
//					item.setActualQuantity(resultSet.getInt(index++));
//					item.setIdentity(resultSet.getString(index++));
//					item.setAnnotation(resultSet.getString(index++));
//
//					logger.trace(item);
//					
//					itemList.addElement(item);
//				} while (resultSet.next());
//			}
//		} catch (SQLException exception) {
//			logger.error("Caught exception for SQL=" + sql, exception);
//		} finally {
//			if (resultSet != null) {
//				try {
//					resultSet.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		logger.debug("Sorting data.");
//		Collections.sort(itemList, new Comparator<StockOnHandItem>() {
//
//			@Override
//			public int compare(StockOnHandItem left, StockOnHandItem right) {
//				int result = left.getNumber().compareTo(right.getNumber());
//				
//				if (result == 0) {
//					result = left.getName().compareTo(right.getName());
//
//					if (result == 0 && left.getIdentity() != null && right.getIdentity() != null) {
//						result = left.getIdentity().compareTo(right.getIdentity());					
//					}
//				}
//				
//				return result;
//			}
//			
//		});
//
//		logger.debug("Selected count=" + itemList.size());
//		return itemList;
//	}

	public Vector<StockLocationItem> selectStockLocationInventoryList() {
		logger.debug("Select stock location inventory list.");
		SelectQuery query = new SelectQuery();
		
		query.addCustomColumns(
				WarehouseSchema.stock_locations_section,
				WarehouseSchema.stock_locations_slot,
				WarehouseSchema.items_number,
				WarehouseSchema.items_name,
				WarehouseSchema.actual_inventory_quantity,
				WarehouseSchema.actual_inventory_identity,
				WarehouseSchema.actual_inventory_last_modified);

		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.actual_inventory_item_id, WarehouseSchema.items_id));
		query.addCondition(BinaryCondition.equalTo(WarehouseSchema.actual_inventory_stock_location_id, WarehouseSchema.stock_locations_id));

		query.validate();

		String sql = query.toString();
		
		logger.debug("SQL=" + sql);

		Vector<StockLocationItem> itemList = new Vector<>();
		
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			if (resultSet.first()) {
				do {
					int index = 1;
					StockLocationItem item = new StockLocationItem();

					item.setSection(resultSet.getString(index++));
					item.setSlot(resultSet.getString(index++));
					item.setNumber(resultSet.getString(index++));
					item.setName(resultSet.getString(index++));
					item.setQuantity(resultSet.getInt(index++));
					item.setIdentity(resultSet.getString(index++));
					item.setDate(resultSet.getTimestamp(index++).toString().substring(0, 10));

					logger.trace(item);
					
					itemList.addElement(item);
				} while (resultSet.next());
			}
		} catch (SQLException exception) {
			logger.error("Caught exception for SQL=" + sql, exception);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		logger.debug("Sorting data.");
		Collections.sort(itemList, new Comparator<StockLocationItem>() {

			@Override
			public int compare(StockLocationItem left, StockLocationItem right) {
				int result = left.getSection().compareTo(right.getSection());
				
				if (result == 0) {
					result =  left.getSlot().compareTo(right.getSlot());
					
					if (result == 0) {
						result = left.getNumber().compareTo(right.getNumber());
						
						if (result == 0) {
							return left.getName().compareTo(right.getName());
						}
					}
				}
				
				return result;
			}
			
		});

		logger.debug("Selected count=" + itemList.size());
		return itemList;
	}
	
	public int getNumberOfMasterInventory() {
		SelectQuery query = new SelectQuery();

		query.addCustomColumns(FunctionCall.count().addColumnParams(WarehouseSchema.master_inventory_id));
		query.validate();

		String sql = query.toString();
		
		logger.debug("SQL=" + sql);

		int result = 0;
		
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			if (resultSet.first()) {
				result = resultSet.getInt(1);
			}
		} catch (SQLException exception) {
			logger.error("Caught exception for SQL=" + sql, exception);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public Vector<MasterInventoryDAO> selectMasterInventory(int itemId, String identity) {
		String sql = MasterInventorySql.select(itemId, identity);
		
		return executeSelectMasterInventory(sql);
	}
	

	public Vector<ActualInventoryDAO> selectActualInventory(int itemId, int locationId, String identity) {
		String sql = ActualInventorySql.select(itemId, locationId, identity);
		
		return executeSelectActualInventory(sql);
	}
	
	public int getNumberOfActualInventory() {
		SelectQuery query = new SelectQuery();

		query.addCustomColumns(FunctionCall.count().addColumnParams(WarehouseSchema.actual_inventory_id));
		query.validate();

		String sql = query.toString();
		
		logger.debug("SQL=" + sql);

		int result = 0;
		
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			if (resultSet.first()) {
				result = resultSet.getInt(1);
			}
		} catch (SQLException exception) {
			logger.error("Caught exception for SQL=" + sql, exception);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public Vector<ActualInventoryDAO> selectActualInventory(String wildcardSearchKey) {
		logger.debug("Select inventory matching='" + wildcardSearchKey + "'");
		String sql = ActualInventorySql.select(wildcardSearchKey);
		
		return executeSelectActualInventory(sql);
	}

	public Vector<ActualInventoryDAO> selectActualInventory(String itemNumber, String itemName, String locationSection, String locationSlot) {
		logger.debug("Select inventory number=" + itemNumber + ",name=" + itemName + ",section=" + locationSection + ",slot=" + locationSlot);
		String sql = ActualInventorySql.select(itemNumber, itemName, locationSection, locationSlot);
		
		return executeSelectActualInventory(sql);
	}
	
	private Vector<MasterInventoryDAO> executeSelectMasterInventory(String sql) {
		logger.debug("SQL=" + sql);

		Vector<MasterInventoryDAO> dataAccessObjects = new Vector<>();
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			if (resultSet.first()) {
				do {
					MasterInventoryDAO dao = new MasterInventoryDAO();

					int index = 1;
					dao.setId(resultSet.getInt(index++));					
					dao.setItemId(resultSet.getInt(index++));
					dao.setSource(resultSet.getString(index++));
					dao.setStockPoint(resultSet.getString(index++));
					dao.setQuantity(resultSet.getInt(index++));
					dao.setIdentity(resultSet.getString(index++));
					dao.setTimestamp(resultSet.getTimestamp(index++).toString().substring(0, 10));
					dao.setAnnotation(resultSet.getString(index++));
				
					logger.trace("" + dao);

					dataAccessObjects.addElement(dao);
				} while (resultSet.next());
			}
		} catch (SQLException exception) {
			logger.error("Caught exception for SQL=" + sql, exception);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				resultSet = null;
			}
		}

		logger.debug("Selected count=" + dataAccessObjects.size());
		return dataAccessObjects;
	}

	private Vector<ActualInventoryDAO> executeSelectActualInventory(String sql) {
		logger.debug("SQL=" + sql);

		Vector<ActualInventoryDAO> dataAccessObjects = new Vector<>();
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			if (resultSet.first()) {
				do {
					ActualInventoryDAO dao = new ActualInventoryDAO();
					
					int index = 1;
					dao.setId(resultSet.getInt(index++));					
					dao.setItemId(resultSet.getInt(index++));
					dao.setLocationId(resultSet.getInt(index++));
					dao.setQuantity(resultSet.getInt(index++));
					dao.setIdentity(resultSet.getString(index++));
					dao.setTimestamp(resultSet.getTimestamp(index++).toString().substring(0, 10));
					dao.setAnnotation(resultSet.getString(index++));
				
					logger.trace("" + dao);

					dataAccessObjects.addElement(dao);
				} while (resultSet.next());
			}
		} catch (SQLException exception) {
			logger.error("Caught exception for SQL=" + sql, exception);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				resultSet = null;
			}
		}

		logger.debug("Selected count=" + dataAccessObjects.size());
		return dataAccessObjects;
	}

	/**
	 * 
	 * @param inventory
	 */
	public void insertInventory(ActualInventory inventory) { 
		ActualInventoryDAO dao = new ActualInventoryDAO(inventory);
		
		String sql = ActualInventorySql.insert(dao);

		execute(sql);
	}	

	public void insertInventory(MasterInventory inventory) { 
		MasterInventoryDAO dao = new MasterInventoryDAO(inventory);
		
		String sql = MasterInventorySql.insert(dao);

		execute(sql);
	}	

	/**
	 * 
	 * @param inventory
	 */
	public void updateInventory(ActualInventory inventory) {
		ActualInventoryDAO dao = new ActualInventoryDAO(inventory);
		
		String sql = ActualInventorySql.update(dao);

		execute(sql);
	}	

	public void updateInventory(MasterInventory inventory) {
		MasterInventoryDAO dao = new MasterInventoryDAO(inventory);
		
		String sql = MasterInventorySql.update(dao);

		execute(sql);
	}	
	/**
	 * 
	 * @param inventory
	 */
	public void deleteInventory(ActualInventory inventory) {
		ActualInventoryDAO dao = new ActualInventoryDAO(inventory);
		
		String sql = ActualInventorySql.delete(dao);

		execute(sql);
	}	

	public void deleteInventory(MasterInventory inventory) {
		MasterInventoryDAO dao = new MasterInventoryDAO(inventory);
		
		String sql = MasterInventorySql.delete(dao);

		execute(sql);
	}	
	public Vector<HoldingDAO> selectHoldings(int byHoldingUnitId, int atLocationId) {
		String sql = HoldingsSql.select(byHoldingUnitId, atLocationId);

		logger.debug("SQL=" + sql);

		Vector<HoldingDAO> dataAccessObjects = new Vector<>();
		
		Statement statement = null;
		ResultSet result = null;

		try {
			statement = connection.createStatement();
			result = statement.executeQuery(sql);

			if (result.first()) {
				do {
					int unitId = result.getInt(WarehouseSchema.holdings_unit_id.getName());
					int locationId = result.getInt(WarehouseSchema.holdings_stock_location_id.getName());
				
					HoldingDAO dao = new HoldingDAO(unitId, locationId);
					
					logger.trace("" + dao);

					dataAccessObjects.addElement(dao);
				} while (result.next());
			}
		} catch (SQLException exception) {
			logger.error("Caught exception for SQL=" + sql, exception);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				result = null;
			}
		}

		logger.debug("Selected count=" + dataAccessObjects.size());
		return dataAccessObjects;
	}

	public void insertHolding(Holding newHolding) {
		HoldingDAO dao = new HoldingDAO(newHolding);
		
		String sql = HoldingsSql.insert(dao);
		
		execute(sql);
	}

	public void updateHolding(Holding aHolding) {
		HoldingDAO dao = new HoldingDAO(aHolding);
		
		String sql = HoldingsSql.update(dao);
		
		execute(sql);
	}

	public void deleteHolding(Holding aHolding) {
		HoldingDAO dao = new HoldingDAO(aHolding);
		
		String sql = HoldingsSql.delete(dao);
		
		execute(sql);
	}
	
	/**
	 * 
	 * @param withNumber
	 * @param withName
	 * @return
	 */
	public Vector<Item> selectItems(String withNumber, String withName) {
		String sql = ItemSql.select(connection, withNumber, withName);

		logger.debug("SQL=" + sql);

		Vector<Item> items = new Vector<>();
		
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			if (resultSet.first()) {
				do {
					ItemDAO dao = new ItemDAO();

					dao.setId(resultSet.getInt(WarehouseSchema.items_id.getName()));
					dao.setNumber(resultSet.getString(WarehouseSchema.items_number.getName()));
					dao.setName(resultSet.getString(WarehouseSchema.items_name.getName()));
					dao.setPackaging(resultSet.getString(WarehouseSchema.items_packaging.getName()));
					dao.setDescription(resultSet.getString(WarehouseSchema.items_description.getName()));
				
					logger.trace("" + dao);

					items.addElement(dao.createItem());
				} while (resultSet.next());
			}
		} catch (SQLException exception) {
			logger.error("Caught exception for SQL=" + sql, exception);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				resultSet = null;
			}
		}

		logger.debug("Selected count=" + items.size());
		return items;
	}
	/**
	 * 
	 * @param newItem
	 */
	public void insertItem(Item newItem) {
		logger.trace("Insert item=" + newItem);
		ItemDAO dao = new ItemDAO(newItem);
		
		String sql = ItemSql.insert(connection, dao);
		execute(sql);
	}
	
	/**
	 * 
	 * @param anItem
	 */
	public void updateItem(Item anItem) {
		logger.trace("Update iteme=" + anItem);
		ItemDAO dao = new ItemDAO(anItem);
		
		String sql = ItemSql.update(connection, dao);

		execute(sql);
	}

	/**
	 * 
	 * @param anItem
	 */
	public void deleteItem(Item anItem) {
		logger.trace("Remove item=" + anItem);
		ItemDAO dao = new ItemDAO(anItem);
		
		String sql = ItemSql.delete(connection, dao);
		execute(sql);
	}

	/*
	 * ************************************
	 */
	
	/**
	 * 
	 * @param withinSection
	 * @param atSlot
	 * @return
	 */
	public Vector<StockLocation> selectLocations(String withinSection, String atSlot) {
		Vector<StockLocation> locations = new Vector<>();
		
		String sql = StockLocationsSql.select(connection, withinSection, atSlot);
		
		logger.debug("SQL=" + sql);

		Statement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			if (resultSet.first()) {
				do {
					int id = resultSet.getInt(1);
					String section = resultSet.getString(2);
					String slot = resultSet.getString(3);
					String description = resultSet.getString(4);

					StockLocationDAO dao = new StockLocationDAO(id, section, slot, description);
					
					logger.trace("" + dao);

					locations.addElement(dao.createLocation());
				} while (resultSet.next());
			}
		} catch (SQLException exception) {
			logger.error("Caught exception for SQL=" + sql, exception);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		logger.debug("Selected count=" + locations.size());
		return locations;
	}

	/**
	 * 
	 * @param newLocation
	 */
	public void insertStockLocation(StockLocation newLocation) {
		logger.trace("Insert stock location=" + newLocation);
		
		StockLocationDAO dao = new StockLocationDAO(newLocation);
		
		String sql = StockLocationsSql.insert(connection, dao);
		
		execute(sql);
	}
	
	/**
	 * 
	 * @param aLocation
	 */
	public void updateStockLocation(StockLocation aLocation) {
		logger.trace("Update stock location=" + aLocation);
		
		StockLocationDAO dao = new StockLocationDAO(aLocation);
		
		String sql = StockLocationsSql.update(connection, dao);
		
		execute(sql);
	}
	
	/**
	 * 
	 * @param aLocation
	 */
	public void deleteStockLocation(StockLocation aLocation) {
		logger.trace("Update stock location=" + aLocation);
		StockLocationDAO dao = new StockLocationDAO(aLocation);

		String sql = StockLocationsSql.delete(connection, dao);
		
		execute(sql);
	}
	

	public Vector<OrganizationalUnitDAO> selectOrganizationalUnits(String byCallsign, String andName) {
		String sql = OrganizationalUnitsSql.select(EntityName.NULL_ID, byCallsign, andName, EntityName.NULL_ID);
		logger.debug("SQL=" + sql);

		Vector<OrganizationalUnitDAO> dataAccessObjects = new Vector<>();
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			if (resultSet.first()) {
				do {
					OrganizationalUnitDAO dao = new OrganizationalUnitDAO();
					
					dao.setId(resultSet.getInt(WarehouseSchema.units_id.getName()));
					dao.setName(resultSet.getString(WarehouseSchema.units_name.getName()));
					dao.setCallSign(resultSet.getString(WarehouseSchema.units_callsign.getName()));
					dao.setLevel(resultSet.getInt(WarehouseSchema.units_level.getName()));
					dao.setSuperiorId(resultSet.getInt(WarehouseSchema.units_superior_unit_id.getName()));
				
					logger.trace("" + dao);

					dataAccessObjects.addElement(dao);
				} while (resultSet.next());
			}
		} catch (SQLException exception) {
			logger.error("Caught exception for SQL=" + sql, exception);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				resultSet = null;
			}
		}

		logger.debug("Selected count=" + dataAccessObjects.size());
		return dataAccessObjects;
	}

	public void insertOrganizationalUnit(OrganizationalUnit newUnit) {
		OrganizationalUnitDAO dao = new OrganizationalUnitDAO(newUnit);
		
		String sql = OrganizationalUnitsSql.insert(dao);
		
		execute(sql);
	}

	public void updateOrganizationalUnit(OrganizationalUnit aUnit) {
		OrganizationalUnitDAO dao = new OrganizationalUnitDAO(aUnit);
		
		String sql = OrganizationalUnitsSql.update(dao);
		
		execute(sql);
	}

	public void deleteOrganizationalUnit(OrganizationalUnit aUnit) {
		OrganizationalUnitDAO dao = new OrganizationalUnitDAO(aUnit);
		
		String sql = OrganizationalUnitsSql.delete(dao);
		
		execute(sql);
	}


	public Vector<ItemApplicationDAO> selectItemApplications(int forUnitId) {
		String sql = ItemApplicationsSql.select(forUnitId, null);
		
		logger.debug("SQL=" + sql);

		Vector<ItemApplicationDAO> result = new Vector<>();		
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			if (resultSet.first()) {
				do {
					int unitId = resultSet.getInt(WarehouseSchema.item_application_unit_id.getName());
					int articleId = resultSet.getInt(WarehouseSchema.item_application_item_id.getName());
					String category = resultSet.getString(WarehouseSchema.item_application_category.getName());
					int quantity = resultSet.getInt(WarehouseSchema.item_application_quantity.getName());
				
					ItemApplicationDAO dao = new ItemApplicationDAO(unitId, articleId, category, quantity);
					
					logger.trace("" + dao);

					result.addElement(dao);
				} while (resultSet.next());
			}
		} catch (SQLException exception) {
			logger.error("Caught exception for SQL=" + sql, exception);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				resultSet = null;
			}
		}

		logger.debug("Selected count=" + result.size());
		return result;
	}
	
	public void insertItemApplication(ItemApplication newUnit) {
		ItemApplicationDAO dao = new ItemApplicationDAO(newUnit);
		
		String sql = ItemApplicationsSql.insert(dao);
		
		execute(sql);
	}

	public void updateItemApplication(ItemApplication aUnit) {
		ItemApplicationDAO dao = new ItemApplicationDAO(aUnit);
		
		String sql = ItemApplicationsSql.update(dao);
		
		execute(sql);
	}

	public void deleteItemApplication(ItemApplication aUnit) {
		ItemApplicationDAO dao = new ItemApplicationDAO(aUnit);
		
		String sql = ItemApplicationsSql.delete(dao);
		
		execute(sql);
	}
	
	private void execute(String sql) {
		logger.debug("SQL=" + sql);
		
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			statement.execute(sql);
		} catch (SQLException exception) {
			logger.error("Caught exception for SQL=" + sql, exception);
		}
		
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean disconnect() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException exception) {
				logger.error("Failed to disconnect from DB", exception);
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 
	 */
	@Override
	protected void finalize() throws Throwable {
		if (connection != null) {
			connection.close();
		}
		
		super.finalize();
	}
}

