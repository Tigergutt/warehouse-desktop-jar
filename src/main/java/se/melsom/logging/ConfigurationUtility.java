package se.melsom.logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ConfigurationUtility  {
	public static String properyFile = "/log4j.properties";
	
	public static void loadConfiguration() {
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.INFO);

		Logger logger = Logger.getLogger(ConfigurationUtility.class);		
		Properties props = new Properties();
		InputStream input = ConfigurationUtility.class.getResourceAsStream(properyFile);
		
		try {
			if (input == null) {
				logger.warn("Could not open file=" + properyFile);
				return;
			}
			
			props.load(input);
			
			PropertyConfigurator.configure(props);
		} catch (IOException e) {
			logger.warn("Failed to load properties from file=" + properyFile, e);
		}
	}
}
