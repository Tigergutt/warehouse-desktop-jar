package se.melsom.warehouse.settings;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class PersistentSettings {
	private static final Logger logger = LoggerFactory.getLogger(PersistentSettings.class);
	
	private final SettingsBean settings = new SettingsBean();

	public PersistentSettings() {
		logger.debug("Execute constructor.");
	}

	public void loadData(String path) {
		logger.debug("Load application settings from: {}", path);
		try {
			InputStream input = new FileInputStream(path);

			loadData(input);
		} catch (FileNotFoundException e) {
			logger.warn("Failed to load application settings: {}.", e.getMessage());
		}
	}

	void loadData(InputStream input) {
		InputStreamReader reader = new InputStreamReader(input);
	}

    public String getProperty(String name) {
		return settings.properties.get(name);
	}

	public String getProperty(String name, String defaultValue) {
		String value = settings.properties.get(name);

		if (value != null) {
			return value;
		}

		settings.properties.add(name, defaultValue);

		return defaultValue;
	}

	public void setProperty(String name, String value) {
		settings.properties.add(name, value);
	}

    public WindowBean getWindowSettings(String name) {
		return settings.windows.get(name);
	}

    public void addWindowSettings(String name, WindowBean window) {
		settings.windows.add(name, window);
	}

    public void setWindowLocation(String name, int x, int y) {
		getWindow(name).setX(x);
		getWindow(name).setY(y);
	}

    public void setWindowDimension(String name, int width, int height) {
		getWindow(name).setWidth(width);
		getWindow(name).setHeight(height);
	}

    public void setWindowVisible(String name, boolean isVisible) {
		getWindow(name).setVisible(isVisible);
	}

	private WindowBean getWindow(String name) {
		WindowBean window = settings.windows.get(name);
		if (window == null) {
			window = new WindowBean();
			settings.windows.add(name, window);
		}
		return window;
	}

	public void saveData(OutputStream output) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writerFor(SettingsBean.class);
		JsonGenerator generator = writer.createGenerator(output);
		generator.setPrettyPrinter(new DefaultPrettyPrinter());
		generator.writeObject(settings);
	}

	public void saveData(String path) throws IOException {
		logger.debug("Save application settings to: " + path);		
        OutputStream output = new FileOutputStream(path);
        saveData(output);
	}
}
