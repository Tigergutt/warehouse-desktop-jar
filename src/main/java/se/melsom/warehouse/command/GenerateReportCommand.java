package se.melsom.warehouse.command;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import se.melsom.warehouse.report.PdfReportRenderer;
import se.melsom.warehouse.report.Report;
import se.melsom.warehouse.report.component.Page;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.Property;

public abstract class GenerateReportCommand extends Command {
	private static Logger logger = Logger.getLogger(GenerateReportCommand.class);
	
	protected void save(Report report, JFrame parent) {
		PdfReportRenderer renderer = new PdfReportRenderer();
		
		for (Page page : report.getPages()) {
			renderer.render(page);
		}
		
		Property property = PersistentSettings.singleton().getProperty("reportDirectory", ".");

		JFileChooser chooser = new JFileChooser(property.getValue());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF file.", "pdf");

		chooser.setFileFilter(filter);
		chooser.setSelectedFile(new File(property.getValue(), report.getReportName() + ".pdf"));

		if (chooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) {
			return;
		}
			
		property.setValue(chooser.getSelectedFile().getParent());
		
		String path = chooser.getSelectedFile().getPath();
		
		if (!path.endsWith(".pdf")) {
			path += ".pdf";
		}

		try {
			renderer.save(path);
		} catch (IOException e) {
			logger.error("Could not save report.", e);
		}		
	}
}
