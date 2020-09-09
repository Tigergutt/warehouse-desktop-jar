package se.melsom.warehouse.presentation.importer;

/**
 * The type Import cell.
 */
public class ImportCell {
	private ImportStatus status;
	private Object value;

    /**
     * Instantiates a new Import cell.
     *
     * @param status the status
     * @param value  the value
     */
    public ImportCell(ImportStatus status, Object value) {
		this.status = status;
		this.value = value;
	}

    /**
     * Gets status.
     *
     * @return the status
     */
    public ImportStatus getStatus() {
		return status;
	}

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(ImportStatus status) {
		this.status = status;
	}

    /**
     * Gets value.
     *
     * @return the value
     */
    public Object getValue() {
		return value;
	}

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(Object value) {
		this.value = value;
	}

}
