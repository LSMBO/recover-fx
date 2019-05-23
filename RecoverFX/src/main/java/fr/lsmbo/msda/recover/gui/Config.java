/*
 * 
 */
package fr.lsmbo.msda.recover.gui;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import fr.lsmbo.msda.recover.gui.model.settings.UserParams;

/**
 * Builds, initialize and save RecoverFx configurations.
 * 
 * @author Aromdhani
 * @author Alexandre Burel
 *
 */
public class Config {
	private static final Logger logger = LogManager.getLogger(Config.class);

	private static String propertiesFileName = "application.conf";
	private static String recoverPropertiesFileName = "recoverfx.properties";
	private static File userDirectoryPath = new File(System.getProperty("user.home") + "/.recover");
	private static File userParamsFile = new File(userDirectoryPath.getAbsolutePath() + "/user_params.json");
	private static File defaultParamsFile = new File(
			Main.class.getClassLoader().getResource("defaultParams.json").getPath());
	private static Properties properties = null;

	/**
	 * Return object value. Example: Config.get("max.file.size")
	 * 
	 * @param the
	 *            specified key to retrieve the property value as an object.
	 */
	private static Object _get(String key) {
		return properties.getProperty(key);
	}

	/**
	 * Return a property value as String
	 * 
	 * @param key
	 *            the specified key to get the property value
	 */
	public static String get(String key) {
		Object value = _get(key);
		if (value == null)
			return null;
		return value.toString();
	}

	/**
	 * Return a property value as an Integer.
	 * 
	 * @param key
	 *            the specified key to retrieve the property value.
	 */
	public static Integer getInteger(String key) {
		Object value = _get(key);
		if (value == null)
			return null;
		return new Integer(value.toString());
	}

	/**
	 * Return an array list of properties values of matched keys with regex.
	 * 
	 * @param regex
	 *            the regex used to matches with the keys.
	 */
	public static ArrayList<String> getPropertyKeys(String regex) {
		ArrayList<String> keys = new ArrayList<String>();
		for (String key : properties.stringPropertyNames()) {
			if (key.matches(regex))
				keys.add(key);
		}
		return keys;
	}

	/** Load and initialize user and Recover parameters */
	public static void initialize() {
		// Read recoverfx.properties file
		try (InputStream input = Main.class.getClassLoader().getResourceAsStream(recoverPropertiesFileName)) {
			if (input == null) {
				System.err.println(
						"Error - Recover properties file: '" + recoverPropertiesFileName + "' does not exist!");
				logger.error("Recover properties file: '" + recoverPropertiesFileName + "' does not exist!");
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
		// Read application.conf file
		properties = new Properties();
		try (InputStream input = Main.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
			if (input == null) {
				System.err.println("Error - Properties file: '" + propertiesFileName + "' does not exist!");
				logger.error("Properties file: '" + propertiesFileName + "' does not exist!");
			}
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Create user directory if it does not exist
		if (!userDirectoryPath.exists())
			userDirectoryPath.mkdir();
		// Read user parameters if it exist otherwise read default parameters
		// file
		loadUserParams(userParamsFile.exists() ? userParamsFile : defaultParamsFile);
	}

	/**
	 * Load user parameters. Read and parse parameters file.
	 * 
	 * @param paramFile
	 *            the file used to load user parameters
	 */
	public static void loadUserParams(File paramFile) {
		// TODO Check version number: user should be aware if the params are
		// from an older version
		// TODO write a diff function to see what's different between older and
		// current param list (so the user only have to verify the problematic
		// params)
		try {
			Gson gson = new Gson();
			JsonReader reader = new JsonReader(new FileReader(paramFile));
			Session.parameters = gson.fromJson(reader, UserParams.class);
			logger.info("The user parameters: {} ", Session.parameters.toString());
		} catch (Exception e) {
			// a possible error case is when param files has been generated with
			// an older version of Recover
			e.printStackTrace();
		}
	}

	/**
	 * Reset user parameters. Load default parameters instead of user
	 * parameters.
	 */
	public static void resetUserParams() {
		loadUserParams(defaultParamsFile);
	}

	/** Save user parameters */
	public static void saveUserParams() {
		saveUserParams(userParamsFile);
	}

	/**
	 * Save user parameters.
	 * 
	 * @param paramFile
	 *            the file to save user parameters.
	 */
	public static void saveUserParams(File paramFile) {
		// Update parameters before saving them.
		Session.parameters.setUserName(System.getProperty("user.name"));
		Session.parameters.setTimestamp("" + new Timestamp(System.currentTimeMillis()));
		Session.parameters.setRecoverVersion(Session.RECOVER_RELEASE_VERSION);
		System.out.println("INFO - The user parameters: " + Session.parameters.toString());
		logger.info("The user parameters : {} ", Session.parameters.toString());
		// Convert parameters to JSON and write them to the file
		try (Writer writer = new FileWriter(paramFile)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(Session.parameters, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
