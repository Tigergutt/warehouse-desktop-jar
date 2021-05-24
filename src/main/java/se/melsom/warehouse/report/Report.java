package se.melsom.warehouse.report;

import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.report.component.Page;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

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

	public static void save(Report report, JFrame parent) {
		PdfReportRenderer renderer = new PdfReportRenderer();

		for (Page page : report.getPages()) {
			renderer.render(page);
		}

//		String directory = persistentSettings.getProperty("reportDirectory", ".");
		String directory = ".";

		JFileChooser chooser = new JFileChooser(directory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF file.", "pdf");

		chooser.setFileFilter(filter);
		chooser.setSelectedFile(new File(directory, report.getReportName() + ".pdf"));

		if (chooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) {
			return;
		}

//		persistentSettings.setProperty("reportDirectory", directory);

		String path = chooser.getSelectedFile().getPath();

		if (!path.endsWith(".pdf")) {
			path += ".pdf";
		}

		try {
			renderer.save(path);
		} catch (IOException e) {
//			logger.error("Could not save report.", e);
		}
	}

}
