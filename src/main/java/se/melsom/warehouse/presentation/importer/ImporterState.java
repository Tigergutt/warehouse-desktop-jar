package se.melsom.warehouse.presentation.importer;

/**
 * The enum Importer state.
 */
public enum ImporterState {
    /**
     * Pending importer state.
     */
    PENDING,
    /**
     * Ready importer state.
     */
    READY,
    /**
     * Validity checked importer state.
     */
    VALIDITY_CHECKED,
    /**
     * Consistency checked importer state.
     */
    CONSISTENCY_CHECKED,
    /**
     * Stored importer state.
     */
    STORED
}
