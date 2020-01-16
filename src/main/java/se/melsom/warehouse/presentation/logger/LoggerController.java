package se.melsom.warehouse.presentation.logger;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import se.melsom.logging.TextAreaAppender;
import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowSettings;

public class LoggerController extends ViewController {
	private static Logger logger = Logger.getLogger(LoggerController.class);
	public static final String SET_LOG_LEVEL_ACTION = "SetLogLevel";
	private LoggerView view;
	private ApplicationController controller;
	private Vector<String> logLevels = new Vector<>();

	public LoggerController(ApplicationController controller) {
		this.controller = controller;
		
		logLevels.addElement("OFF");
		logLevels.addElement("WARN");
		logLevels.addElement("INFO");
		logLevels.addElement("DEBUG");
		logLevels.addElement("TRACE");

		WindowSettings settings = PersistentSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowSettings(getWindowName(), 500, 10, 300, 400, false);
			
			PersistentSettings.singleton().addWindowSettings(settings);
		}
		
		view = new LoggerView(this);
		view.setLogLevels(logLevels);
		view.setLogLevelSelectAction(SET_LOG_LEVEL_ACTION);
		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		view.setVisible(settings.isVisible());
		view.addComponentListener(this);

		TextAreaAppender.setTextArea(view.getTextArea());
	}
	
	void setSelectedLogLevel() {
		if (view.getSelectedIndex() < 0) {
			return;
		}
		
		setLogLevel(view.getSelectedLogLevel());
	}
	
	void setLogLevel(String level) {
		AppenderSkeleton appender = (AppenderSkeleton) Logger.getRootLogger().getAppender("swing");
		
		if (appender == null) {
			return;
		}
		
		appender.setThreshold(Level.toLevel(level));
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
		PersistentSettings.singleton().setWindowDimension(getWindowName(), frame.getWidth(), frame.getHeight());	
	}

	@Override
	public void componentMoved(ComponentEvent event) {
		if (event.getSource() instanceof JInternalFrame == false) {
			return;
		}
		
		JInternalFrame frame = (JInternalFrame) event.getSource();
		PersistentSettings.singleton().setWindowLocation(getWindowName(), frame.getX(), frame.getY());	
	}
	
	@Override
	public void componentShown(ComponentEvent e) {
		logger.trace("Shown event=" + e);
		setSelectedLogLevel();
		controller.setLogViewMenuItemChecked(false);
		PersistentSettings.singleton().setWindowVisible(getWindowName(), true);	
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		logger.trace("Hidden event=" + e);
		setLogLevel("OFF");
		view.clearLogView();
		controller.setLogViewMenuItemChecked(true);
		PersistentSettings.singleton().setWindowVisible(getWindowName(), false);	
	}
	
	String getWindowName() {
		return LoggerView.class.getSimpleName();
	}
}
