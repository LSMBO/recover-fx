/*
 * 
 */
package fr.lsmbo.msda.recover.gui.util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.lsmbo.msda.recover.gui.Session;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 * 
 * @author Aromdhani
 *
 */
public class FileUtils {
	private static final Logger logger = LogManager.getLogger("FileUtils");

	/**
	 * Configure the initial directory of the file chooser .
	 * 
	 * @param fileChooser
	 *            Create file chooser
	 * @param fileChooserTitle
	 *            The title of file chooser
	 */
	public static void configureFileChooser(final FileChooser fileChooser, String fileChooserTitle) {
		// Default folder is 'Documents'
		File initialDirectory = new File(
				System.getProperty("user.home") + System.getProperty("file.separator") + "Documents");
		// Otherwise it's home folder
		if (!initialDirectory.exists())
			initialDirectory = new File(System.getProperty("user.home"));
		// if a file is already loaded then it's the same folder
		if (Session.CURRENT_FILE != null)
			initialDirectory = Session.CURRENT_FILE.getParentFile();
		fileChooser.setTitle(fileChooserTitle);
		fileChooser.setInitialDirectory(initialDirectory);
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".mgf files", "*.mgf"),
				new FileChooser.ExtensionFilter(".pkl files", "*.pkl"));
	}

	/**
	 * Configure the initial directory of the directory chooser .
	 * 
	 * @param chooser
	 *            the directory chooser
	 * @param chooserTitle
	 *            The title of the directory chooser
	 */
	public static void configureDirChooser(final DirectoryChooser chooser, String chooserTitle) {
		File initialDirectory = new File(
				System.getProperty("user.home") + System.getProperty("file.separator") + "Documents");
		if (!initialDirectory.exists())
			initialDirectory = new File(System.getProperty("user.home"));
		if (Session.CURRENT_FILE != null)
			initialDirectory = Session.CURRENT_FILE.getParentFile();
		chooser.setTitle(chooserTitle);
		chooser.setInitialDirectory(initialDirectory);
	}

	public static void open(File file) {
		try {
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(file);
			}
		} catch (Exception ex) {
			logger.error("Error while trying to open file!", ex);
		}
	}

	/**
	 * Browse and show a file
	 * 
	 * @param path
	 *            the path of file to browse and to show.
	 */
	public static void showFile(String path) {
		if (java.awt.Desktop.isDesktopSupported()) {
			try {
				java.awt.Desktop.getDesktop().browse(new File(path).toURI());
			} catch (IOException ex) {
				logger.error("Error while trying to browse file!", ex);
				ex.printStackTrace();
			}
		}
	}
	 public static String getFileNameWithoutExtension(File file) {
	        String fileName = "";
	 
	        try {
	            if (file != null && file.exists()) {
	                String name = file.getName();
	                fileName = name.replaceFirst("[.][^.]+$", "");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            fileName = "";
	        }
	 
	        return fileName;
	 
	    }
}