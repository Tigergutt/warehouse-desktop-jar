package se.melsom.warehouse.presentation.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowBean;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;
import java.util.Vector;

public class LoggerController extends ViewController {
	private static final Logger logger = LoggerFactory.getLogger(LoggerController.class);
    public static final String SET_LOG_LEVEL_ACTION = "SetLogLevel";
	private final LoggerView view;
	private final ApplicationPresentationModel controller;
	private final Vector<String> logLevels = new Vector<>();

	@Autowired
	private PersistentSettings persistentSettings;

    public LoggerController(ApplicationPresentationModel controller) {
		this.controller = controller;
		
		logLevels.addElement("OFF");
		logLevels.addElement("WARN");
		logLevels.addElement("INFO");
		logLevels.addElement("DEBUG");
		logLevels.addElement("TRACE");

		WindowBean settings = persistentSettings.getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowBean(500, 10, 300, 400, false);

			persistentSettings.addWindowSettings(getWindowName(), settings);
		}
		
		view = new LoggerView(this);
		view.setLogLevels(logLevels);
		view.setLogLevelSelectAction(SET_LOG_LEVEL_ACTION);
		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		view.setVisible(settings.isVisible());
		view.addComponentListener(this);

//		TextAreaAppender.setTextArea(view.getTextArea());
	}

    void setSelectedLogLevel() {
		if (view.getSelectedIndex() < 0) {
			return;
		}
		
		setLogLevel(view.getSelectedLogLevel());
	}

    void setLogLevel(String level) {
//		AppenderSkeleton appender = (AppenderSkeleton) Logger.getRootLogger().getAppender("swing");
//
//		if (appender == null) {
//			return;
//		}
//
//		appender.setThreshold(Level.toLevel(level));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case SET_LOG_LEVEL_ACTION:
			setSelectedLogLevel();
			break;
			
		default:
			break;
		}
	}

    public JInternalFrame getInternalFrame() {
		return view;
	}

	@Override
	public JComponent getView() {
		return view;
	}

    public void showView() {
		logger.debug("showView()");
		if (view.isVisible()) {
			if (view.isIcon()) {
				try {
					view.setIcon(false);
				} catch (PropertyVetoException e) {
					logger.error("showView()", e);
				}
			} else {
				view.setVisible(false);
			}
		} else {
			view.setVisible(true);
		}
	}
	
	@Override
	public void finalize() throws Throwable {
		logger.info("Finalize.");
		super.finalize();
	}


	@Override
	public void componentResized(ComponentEvent event) {
		if (event.getSource() instanceof JInternalFrame == false) {
			return;
		}
		
		JInternalFrame frame = (JInternalFrame) event.getSource();
		persistentSettings.setWindowDimension(getWindowName(), frame.getWidth(), frame.getHeight());
	}

	@Override
	public void componentMoved(ComponentEvent event) {
		if (event.getSource() instanceof JInternalFrame == false) {
			return;
		}
		
		JInternalFrame frame = (JInternalFrame) event.getSource();
		persistentSettings.setWindowLocation(getWindowName(), frame.getX(), frame.getY());
	}
	
	@Override
	public void componentShown(ComponentEvent e) {
		logger.trace("Shown event=" + e);
		setSelectedLogLevel();
		persistentSettings.setWindowVisible(getWindowName(), true);
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		logger.trace("Hidden event=" + e);
		setLogLevel("OFF");
		view.clearLogView();
		persistentSettings.setWindowVisible(getWindowName(), false);
	}

    String getWindowName() {
		return LoggerView.class.getSimpleName();
	}
}
