package se.melsom.warehouse.presentation.logger;

import javax.swing.JInternalFrame;

import org.apache.log4j.Logger;

import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 * The type Logger view.
 */
@SuppressWarnings("serial")
public class LoggerView extends JInternalFrame {
	private static Logger logger = Logger.getLogger(LoggerView.class);
	private JTextArea textArea;
	private JComboBox<String> logLevelSelector = null;

    /**
     * Instantiates a new Logger view.
     *
     * @param controller the controller
     */
    public LoggerView(LoggerController controller) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		logger.debug("Create logger view.");
		setClosable(true);
		setResizable(true);
		setTitle("Loggfönster");
		
		JPanel controlPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) controlPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(controlPanel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Loggnivå");
		controlPanel.add(lblNewLabel);
		
		logLevelSelector = new JComboBox<>();
		logLevelSelector.addActionListener(controller);
		controlPanel.add(logLevelSelector);
		
		textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
	}
	
	@Override
	public void dispose() {
		logger.debug("Dispose logger view.");
		super.dispose();
	}
	
	@Override
	protected void finalize() throws Throwable {
		logger.debug("Finalize logger view.");
		super.finalize();
	}

    /**
     * Gets text area.
     *
     * @return the text area
     */
    JTextArea getTextArea() {
		return textArea;
	}

    /**
     * Gets selected log level.
     *
     * @return the selected log level
     */
    String getSelectedLogLevel() {
		return (String) logLevelSelector.getSelectedItem();
	}

    /**
     * Sets selected log level.
     *
     * @param atIndex the at index
     */
    void setSelectedLogLevel(int atIndex) {
		logLevelSelector.setSelectedIndex(atIndex);;
	}

    /**
     * Gets selected index.
     *
     * @return the selected index
     */
    int getSelectedIndex() {
		return logLevelSelector.getSelectedIndex();
	}

    /**
     * Sets log levels.
     *
     * @param logLevels the log levels
     */
    void setLogLevels(Vector<String> logLevels) {
		logLevelSelector.removeAllItems();
		
		for (String logLevel : logLevels) {
			logLevelSelector.addItem(logLevel);
		}
	}

    /**
     * Sets log level select action.
     *
     * @param name the name
     */
    void setLogLevelSelectAction(String name) {
		logLevelSelector.setActionCommand(name);
	}

    /**
     * Gets window name.
     *
     * @return the window name
     */
    String getWindowName() {
		return LoggerView.class.getSimpleName();
	}

    /**
     * Clear log view.
     */
    public void clearLogView() {
		textArea.setText("");
	}
}
