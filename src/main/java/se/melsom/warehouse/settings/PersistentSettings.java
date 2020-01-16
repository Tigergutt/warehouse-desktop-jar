package se.melsom.warehouse.settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

import org.apache.log4j.Logger;

public class PersistentSettings {
	private Logger logger = Logger.getLogger(this.getClass());
	
	private static PersistentSettings singleton = new PersistentSettings();
	
	private Map<String, Property> propertyMap = new HashMap<>();
	private Map<String, WindowSettings> windowSettingsMap = new HashMap<>();
	
	private boolean isDirty = false;
	
	private PersistentSettings() {		
	}
	
	public static PersistentSettings singleton() {
		return singleton;
	}
	
	public void loadData(String path) {
		logger.debug("Load application settings from: " + path);
		InputStream input = null;
		
		try {
			input = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			logger.warn("Failed to load application settings.", e);
			return;
		}
		
		InputStreamReader reader = new InputStreamReader(input);
		JsonReader jsonReader = Json.createReader(reader);
		
		JsonObject root = jsonReader.readObject();
		JsonObject propertiesObject = root.getJsonObject("properties");
		
		if (propertiesObject != null) {
			for (String key : propertiesObject.keySet()) {
				JsonValue value = propertiesObject.get(key);
				
				switch (value.getValueType()) {
				case NUMBER: 
					logger.debug(key + "=" + propertiesObject.getInt(key));					
					propertyMap.put(key, new Property("INTEGER", key, "" + propertiesObject.getInt(key)));
					break;
					
				case STRING: 
					logger.debug(key + "=" + propertiesObject.getString(key, "default"));					
					propertyMap.put(key, new Property("STRING", key, propertiesObject.getString(key)));
					break;
				
				default: 
					logger.warn("Unknown JSON type: " + value.getValueType());
					break;
				}				
			}
		}
		
		JsonObject windowsObject = root.getJsonObject("windows");
		
		if (windowsObject != null) {
			for (String key : windowsObject.keySet()) {
				JsonObject windowObject = windowsObject.getJsonObject(key);
				
				logger.debug(key + "=" + windowObject);
				int x = windowObject.getInt("x", 0);
				int y = windowObject.getInt("y", 0);
				int width = windowObject.getInt("w", 400);
				int height = windowObject.getInt("h", 400);
				boolean isVisible = windowObject.getBoolean("v", false);
				
				windowSettingsMap.put(key, new WindowSettings(key, x, y, width, height, isVisible));
			}
		}

		setDirty(false);
	}
	
	public Property getProperty(String name) {
		return propertyMap.get(name);
	}
	

	public Property getProperty(String name, String defaultValue) {
		Property property = getProperty(name);
		
		if (property == null) {
			property = new Property("STRING", name, defaultValue);
			
			addProperty(property);
		}
		
		return property;
	}

	public Property getProperty(String name, int defaultValue) {
		Property property = getProperty(name);
		
		if (property == null) {
			property = new Property("INTEGER", name, "" + defaultValue);
			
			addProperty(property);
		}
		
		return property;
	}

	public void addProperty(Property property) {
		propertyMap.put(property.getName(), property);
		property.setParent(this);
		setDirty(true);
	}
	
	public WindowSettings getWindowSettings(String name) {
		return windowSettingsMap.get(name);
	}
	
	public void addWindowSettings(WindowSettings windowSettings) {
		windowSettingsMap.put(windowSettings.getName(), windowSettings);
		windowSettings.setParent(this);
		setDirty(true);
	}
	
	public void setWindowLocation(String name, int x, int y) {
		if (windowSettingsMap.containsKey(name)) {
			windowSettingsMap.get(name).setX(x);
			windowSettingsMap.get(name).setY(y);
		}
	}
	
	public void setWindowDimension(String name, int width, int height) {
		if (windowSettingsMap.containsKey(name)) {
			windowSettingsMap.get(name).setWidth(width);
			windowSettingsMap.get(name).setHeight(height);
		}
	}
	
	public void setWindowVisible(String name, boolean isVisible) {
		if (windowSettingsMap.containsKey(name)) {
			windowSettingsMap.get(name).setVisible(isVisible);
		}
	}

	public boolean isDirty() {
		return isDirty;
	}
	
	public void setDirty(boolean dirty) {
		this.isDirty = dirty;
	}

	public void saveData(String path) throws FileNotFoundException {
		logger.debug("Save application settings to: " + path);		
		Map<String, Object> configs = new HashMap<>();
        configs.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonBuilderFactory factory = Json.createBuilderFactory(configs);
		JsonObjectBuilder objectBuilder = factory.createObjectBuilder();
		
		for (String name : propertyMap.keySet()) {
			Property property = propertyMap.get(name);

			objectBuilder.add(property.getName(), property.getValue());
		}
		
        JsonObject propertiesObject = objectBuilder.build();

		for (String name : windowSettingsMap.keySet()) {
			WindowSettings window = windowSettingsMap.get(name);
			JsonObjectBuilder windowObjectBuilder = factory.createObjectBuilder();

			windowObjectBuilder.add("x", window.getX());
			windowObjectBuilder.add("y", window.getY());
			windowObjectBuilder.add("w", window.getWidth());
			windowObjectBuilder.add("h", window.getHeight());
			windowObjectBuilder.add("v", window.isVisible());
	        objectBuilder.add(name, windowObjectBuilder.build());
		}
		
        JsonObject windowsObject = objectBuilder.build();

        objectBuilder.add("properties", propertiesObject);
        objectBuilder.add("windows", windowsObject);
        
        OutputStream output = new FileOutputStream(path);
        JsonWriterFactory writerFactory = Json.createWriterFactory(configs);
        JsonWriter outWriter = writerFactory.createWriter(output);

        outWriter.writeObject(objectBuilder.build());
        
		isDirty = false;
	}
}
