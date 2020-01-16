package se.melsom.warehouse.report;

import java.util.Vector;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.report.component.Page;

public abstract class Report {
	private String date = EntityName.getCurrentDate();
	protected Vector<Page> pages = new Vector<>();

	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public abstract String getReportName();
	
	public Vector<Page> getPages() {
		return pages;
	}
}
