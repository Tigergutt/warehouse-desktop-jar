package se.melsom.logging;

public class ConfigurationUtility  {
    public static String propertyFile = "/logging.properties";

    public static void loadConfiguration() {
//		BasicConfigurator.configure();
//		Logger.getRootLogger().setLevel(Level.INFO);
//
//		Logger logger = Logger.getLogger(ConfigurationUtility.class);
//		Properties props = new Properties();
//		InputStream input = ConfigurationUtility.class.getResourceAsStream(propertyFile);
//
//		try {
//			if (input == null) {
//				logger.warn("Could not open file=" + propertyFile);
//				return;
//			}
//
//			props.load(input);
//
//			PropertyConfigurator.configure(props);
//		} catch (IOException e) {
//			logger.warn("Failed to load properties from file=" + propertyFile, e);
//		}
	}
}
