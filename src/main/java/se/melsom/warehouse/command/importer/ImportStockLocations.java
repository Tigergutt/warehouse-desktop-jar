package se.melsom.warehouse.command.importer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.maintenance.importer.ImportType;
import se.melsom.warehouse.presentation.importer.ImportWizardController;
import se.melsom.warehouse.settings.PersistentSettings;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImportStockLocations extends Command {
	private static final Logger logger = LoggerFactory.getLogger(ImportStockLocations.class);
	private final ApplicationPresentationModel controller;

	@Autowired
	private PersistentSettings persistentSettings;

    public ImportStockLocations(ApplicationPresentationModel controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		logger.debug("Executing import command.");
		String directory = persistentSettings.getProperty("currentDirectory", ".");
		JFileChooser chooser = new JFileChooser(directory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Workbook", "xlsx");

		chooser.setFileFilter(filter);

		if (chooser.showOpenDialog(controller.getDesktopView()) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		persistentSettings.setProperty("currentDirectory", chooser.getSelectedFile().getParent());

		ImportWizardController importer = new ImportWizardController(null /*controller.getInventoryAccounting()*/);
		
		if (importer.importExcelFile(chooser.getSelectedFile().getPath())) {
			importer.showWizardView(ImportType.STOCK_LOCATIONS_AND_HOLDINGS, controller.getDesktopView());
		}
	}
}
