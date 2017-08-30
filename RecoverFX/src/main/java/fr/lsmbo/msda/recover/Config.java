package fr.lsmbo.msda.recover;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import fr.lsmbo.msda.recover.model.settings.UserParams;

public class Config {

	private static String propertiesFileName = "application.conf";
	private static String staticPropertiesFileName = "recoverfx.properties";
	private static File userDirectoryPath = new File(System.getProperty("user.home") + "/.recover");
	private static File userParamsFile = new File(userDirectoryPath.getAbsolutePath() + "/user_params.json");
	private static File defaultParamsFile = new File(Main.class.getClassLoader().getResource("defaultParams.json").getPath());
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
		// create user dir if it does not exist
		if(!userDirectoryPath.exists()) userDirectoryPath.mkdir();
		// read user params if it exist, otherwise read default params file
		loadUserParams(userParamsFile.exists() ? userParamsFile : defaultParamsFile);
	}

	// example: Config.get("max.file.size")
	private static Object _get(String key) {
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
		ArrayList<String> keys = new ArrayList<String>();
		for (String key : properties.stringPropertyNames()) {
			if (key.matches(regex))
				keys.add(key);
		}
		return keys;
	}
	
	public static void resetUserParams() {
		loadUserParams(defaultParamsFile);
	}
	
	public static void loadUserParams(File paramFile) {
		// TODO check version number: user should be aware if the params are from an older version
		// TODO write a diff function to see what's different between older and current param list (so the user only have to verify the problematic params)
		try {
			Gson gson = new Gson();
			JsonReader reader = new JsonReader(new FileReader(paramFile));
			Session.parameters = gson.fromJson(reader, UserParams.class);
			System.out.println("loadUserParams: "+Session.parameters.toString());
		} catch(Exception e) {
			// a possible error case is when param files has been generated with an older version of Recover
			e.printStackTrace();
		}
	}
	
	public static void saveUserParams() {
		saveUserParams(userParamsFile);
	}

	public static void saveUserParams(File paramFile) {
		// update parameters before saving them
		Session.parameters.setUserName(System.getProperty("user.name"));
		Session.parameters.setTimestamp(""+new Timestamp(System.currentTimeMillis()));
		Session.parameters.setRecoverVersion(Session.RECOVER_RELEASE_VERSION);
		System.out.println("saveUserParams: "+Session.parameters.toString());
		// convert params to json and write them to the file
		try (Writer writer = new FileWriter(paramFile)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(Session.parameters, writer);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
