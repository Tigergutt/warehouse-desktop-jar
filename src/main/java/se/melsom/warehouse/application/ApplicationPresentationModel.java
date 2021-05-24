package se.melsom.warehouse.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.melsom.warehouse.application.desktop.DesktopPresentationModel;
import se.melsom.warehouse.settings.PersistentSettings;

import javax.swing.*;
import java.io.IOException;

public class ApplicationPresentationModel {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationPresentationModel.class);
	private static final String APPLICATION_SETTINGS_FILENAME = "ApplicationSettings.json";

	@Autowired private DesktopPresentationModel desktopPresentationModel;
	@Autowired private PersistentSettings persistentSettings;

	public ApplicationPresentationModel() {
		logger.debug("Execute constructor.");
	}

	public void initialize() {
		logger.debug("Application initialize.");
		persistentSettings.loadData(APPLICATION_SETTINGS_FILENAME);

//		loggerController = new LoggerController(this);
//		desktopController.addInternalFrame(loggerController.getInternalFrame());
//		desktopController.addActionCommand(DesktopController.SHOW_LOGGER_VIEW, new ShowLoggerView(loggerController));
	}

	public void applicationExit() {
		logger.debug("applicationExit()");
		try {
			persistentSettings.saveData(APPLICATION_SETTINGS_FILENAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public JFrame getDesktopView() {
		return null;
	}
}
