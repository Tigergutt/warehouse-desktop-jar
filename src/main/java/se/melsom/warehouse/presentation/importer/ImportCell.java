package se.melsom.warehouse.presentation.importer;

public class ImportCell {
	private ImportStatus status;
	private Object value;

    public ImportCell(ImportStatus status, Object value) {
		this.status = status;
		this.value = value;
	}

    public ImportStatus getStatus() {
		return status;
	}

    public void setStatus(ImportStatus status) {
		this.status = status;
	}

    public Object getValue() {
		return value;
	}

    public void setValue(Object value) {
		this.value = value;
	}

}
