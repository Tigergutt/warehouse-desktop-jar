package se.melsom.warehouse.presentation.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class LoggerView extends JInternalFrame {
	private static final Logger logger = LoggerFactory.getLogger(LoggerView.class);
	private final JTextArea textArea;
	private final JComboBox<String> logLevelSelector;

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

    JTextArea getTextArea() {
		return textArea;
	}

    String getSelectedLogLevel() {
		return (String) logLevelSelector.getSelectedItem();
	}

    void setSelectedLogLevel(int atIndex) {
		logLevelSelector.setSelectedIndex(atIndex);
	}

    int getSelectedIndex() {
		return logLevelSelector.getSelectedIndex();
	}

    void setLogLevels(Vector<String> logLevels) {
		logLevelSelector.removeAllItems();
		
		for (String logLevel : logLevels) {
			logLevelSelector.addItem(logLevel);
		}
	}

    void setLogLevelSelectAction(String name) {
		logLevelSelector.setActionCommand(name);
	}

    String getWindowName() {
		return LoggerView.class.getSimpleName();
	}

    public void clearLogView() {
		textArea.setText("");
	}
}
