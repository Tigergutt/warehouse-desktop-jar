package se.melsom.warehouse.report;

import java.util.Vector;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.report.component.Page;

/**
 * The type Report.
 */
public abstract class Report {
	private String date = EntityName.getCurrentDate();
    /**
     * The Pages.
     */
    protected Vector<Page> pages = new Vector<>();


    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
		return date;
	}

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
		this.date = date;
	}

    /**
     * Gets report name.
     *
     * @return the report name
     */
    public abstract String getReportName();

    /**
     * Gets pages.
     *
     * @return the pages
     */
    public Vector<Page> getPages() {
		return pages;
	}
}
