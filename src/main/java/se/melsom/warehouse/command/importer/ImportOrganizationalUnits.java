package se.melsom.warehouse.command.importer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.settings.PersistentSettings;

public class ImportOrganizationalUnits extends Command {
	private static final Logger logger = LoggerFactory.getLogger(ImportOrganizationalUnits.class);
	private final ApplicationPresentationModel controller;

	@Autowired
	PersistentSettings persistentSettings;

    public ImportOrganizationalUnits(ApplicationPresentationModel controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
    	throw new IllegalArgumentException("Implement this motherfucker!");
//		logger.debug("Executing import command.");
//		DesktopView mainView = controller.getDesktopView();
//		String directory = persistentSettings.getProperty("currentDirectory", ".");
//		JFileChooser chooser = new JFileChooser(directory);
//		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Workbook", "xlsx");
//
//		chooser.setFileFilter(filter);
//
//		if (chooser.showOpenDialog(mainView) != JFileChooser.APPROVE_OPTION) {
//			return;
//		}
//
//		persistentSettings.setProperty("currentDirectory", chooser.getSelectedFile().getParent());
//
//		ImportWizardController importer = new ImportWizardController(null /*controller.getInventoryAccounting()*/);
//
//		if (importer.importExcelFile(chooser.getSelectedFile().getPath())) {
//			importer.showWizardView(ImportType.ORGANIZATIONAL_UNITS, controller.getDesktopView());
//		}
	}

}
