package se.melsom.warehouse.command.importer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.command.Command;
import se.melsom.warehouse.importer.ImportType;
import se.melsom.warehouse.presentation.desktop.DesktopView;
import se.melsom.warehouse.presentation.importer.ImportWizardController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.Property;

public class ImportInventory extends Command {
	private static Logger logger = Logger.getLogger(ImportInventory.class);
	private ApplicationController controller;
	
	public ImportInventory(ApplicationController controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		logger.debug("Executing import command.");
		DesktopView mainView = controller.getDesktopView();
		Property property = PersistentSettings.singleton().getProperty("currentDirectory", ".");
		JFileChooser chooser = new JFileChooser(property.getValue());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Workbook", "xlsx");

		chooser.setFileFilter(filter);

		if (chooser.showOpenDialog(mainView) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		property.setValue(chooser.getSelectedFile().getParent());

		ImportWizardController importer = new ImportWizardController(controller.getInventoryAccounting());
		
		if (importer.importExcelFile(chooser.getSelectedFile().getPath())) {
			importer.showWizardView(ImportType.INVENTORY, controller.getDesktopView());
		}
	}

}
