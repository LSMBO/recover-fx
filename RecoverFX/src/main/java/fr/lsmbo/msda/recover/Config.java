package fr.lsmbo.msda.recover;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Config {

	private static String propertiesFileName = "application.conf";
	private static String staticPropertiesFileName = "recoverfx.properties";
	private static Properties properties = null;

	public static void initialize() {
		// read recoverfx.properties
		try (InputStream input = Main.class.getClassLoader().getResourceAsStream(staticPropertiesFileName)) {
			if (input == null) {
				System.err.println("Properties file '"+staticPropertiesFileName+"' does not exist");
			} else {
				Properties recoverProperties = new Properties();
				recoverProperties.load(input);
				Session.RECOVER_RELEASE_NAME = recoverProperties.getProperty("name");
				Session.RECOVER_RELEASE_DESCRIPTION = recoverProperties.getProperty("description");
				Session.RECOVER_RELEASE_VERSION = recoverProperties.getProperty("version");
				Session.RECOVER_RELEASE_DATE = recoverProperties.getProperty("build-date");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// read application.conf
		properties = new Properties();
		try (InputStream input = Main.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
			if (input == null) {
				System.out.println("Properties file '"+propertiesFileName+"' does not exist");
			}
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	private static void initializeIfNeeded() {
//		if (properties == null) {
//			// lazy loading
//			properties = new Properties();
//			try (InputStream input = Main.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
//				if (input == null) {
////					System.out.println("Can't find properties");
//					System.out.println("Properties file '"+propertiesFileName+"' does not exist");
//				}
//				properties.load(input);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	// example: Config.get("max.file.size")
	private static Object _get(String key) {
//		initializeIfNeeded();
		return properties.getProperty(key);
	}

	public static Integer getInteger(String key) {
		Object value = _get(key);
		if (value == null)
			return null;
		return new Integer(value.toString());
	}

	public static String get(String key) {
		Object value = _get(key);
		if (value == null)
			return null;
		return value.toString();
	}

	public static ArrayList<String> getPropertyKeys(String regex) {
//		initializeIfNeeded();
		ArrayList<String> keys = new ArrayList<String>();
		for (String key : properties.stringPropertyNames()) {
			if (key.matches(regex))
				keys.add(key);
		}
		return keys;
	}
}
