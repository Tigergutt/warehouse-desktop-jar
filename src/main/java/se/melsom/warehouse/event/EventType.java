package se.melsom.warehouse.event;

/**
 * The enum Event type.
 */
public enum EventType {
    /**
     * Item list reloaded event type.
     */
    ITEM_LIST_RELOADED,
    /**
     * Item list modified event type.
     */
    ITEM_LIST_MODIFIED,
    /**
     * Stock locations reloaded event type.
     */
    STOCK_LOCATIONS_RELOADED,
    /**
     * Stock locations modified event type.
     */
    STOCK_LOCATIONS_MODIFIED,
    /**
     * Inventory updated event type.
     */
    INVENTORY_UPDATED,
    /**
     * Organizational units reloaded event type.
     */
    ORGANIZATIONAL_UNITS_RELOADED,
    /**
     * Organizational units updated event type.
     */
    ORGANIZATIONAL_UNITS_UPDATED,
    /**
     * Holdings reloaded event type.
     */
    HOLDINGS_RELOADED,
    /**
     * Oldings updated event type.
     */
    OLDINGS_UPDATED
}
