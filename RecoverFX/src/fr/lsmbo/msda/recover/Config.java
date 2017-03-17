package fr.lsmbo.msda.recover;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Config {

	private static String propertiesFileName = "application.conf";
	private static Properties properties = null;

	private static void initializeIfNeeded() {
		if (properties == null) {
			// lazy loading
			properties = new Properties();
			try (InputStream input = Main.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
				if (input == null) {
					System.out.println("Can't find properties");
				}
				properties.load(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// example: Config.get("max.file.size")
	private static Object _get(String key) {
		initializeIfNeeded();
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
		initializeIfNeeded();
		ArrayList<String> keys = new ArrayList<String>();
		for (String key : properties.stringPropertyNames()) {
			if (key.matches(regex))
				keys.add(key);
		}
		return keys;
	}
}
