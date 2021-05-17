package se.melsom.warehouse.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.report.PdfReportRenderer;
import se.melsom.warehouse.report.Report;
import se.melsom.warehouse.report.component.Page;
import se.melsom.warehouse.settings.PersistentSettings;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

public abstract class GenerateReportCommand extends Command {
	private static final Logger logger = LoggerFactory.getLogger(GenerateReportCommand.class);

	@Autowired
	private PersistentSettings persistentSettings;

    protected void save(Report report, JFrame parent) {
		PdfReportRenderer renderer = new PdfReportRenderer();
		
		for (Page page : report.getPages()) {
			renderer.render(page);
		}
		
		String directory = persistentSettings.getProperty("reportDirectory", ".");

		JFileChooser chooser = new JFileChooser(directory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF file.", "pdf");

		chooser.setFileFilter(filter);
		chooser.setSelectedFile(new File(directory, report.getReportName() + ".pdf"));

		if (chooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		persistentSettings.setProperty("reportDirectory", directory);

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
