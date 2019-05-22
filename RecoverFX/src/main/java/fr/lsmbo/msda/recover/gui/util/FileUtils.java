/*
 * 
 */
package fr.lsmbo.msda.recover.gui.util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.lsmbo.msda.recover.gui.Session;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * 
 * @author Aromdhani
 *
 */
public class FileUtils {
	private static final Logger logger = LogManager.getLogger("FileUtils");

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

	/**
	 * @param peakListConsumer
	 *            consumer accept the peak list file to save .
	 * 
	 * @param primaryStage
	 *            the parent stage of the file chooser.
	 * 
	 */

	public static void exportPeakListAs(Consumer<File> peakListConsumer, Stage primaryStage) {
		FileChooser fileChooser = new FileChooser();
		// Set extension filter
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Mgf files (*.mgf)", "*.mgf"),
				new FileChooser.ExtensionFilter("PeakList files (*.pkl)", "*.pkl"));
		// Set Title
		fileChooser.setTitle("Export file");
		// Show save file dialog
		File file = fileChooser.showSaveDialog(primaryStage);
		if (file != null) {
			peakListConsumer.accept(file);
		}
	}

	/**
	 * @param excelConsumer
	 *            consumer accept the excel file to load .
	 * 
	 * @param window
	 *            the parent stage of the file chooser.
	 * 
	 */

	public static void loadExcelFile(Consumer<File> excelConsumer, Window window) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load spectrum titles file");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("File XLS", "*.xlsx"));
		// Set Title
		fileChooser.setTitle("Load spetrum titles file");
		// Show and open dialog
		File file = fileChooser.showOpenDialog(window);
		if (file != null) {
			excelConsumer.accept(file);
		}
	}

	/**
	 * @param openFile
	 *            consumer accept the JSON file to save .
	 * 
	 * @param primaryStage
	 *            the parent stage of the file chooser
	 * 
	 */
	public static void loadFiltersFrmJSON(Consumer<File> openFile, Stage stage) {
		FileChooser fileChooser = new FileChooser();
		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
		fileChooser.getExtensionFilters().add(extFilter);
		// Show save file dialog
		File file = fileChooser.showOpenDialog(stage);
		fileChooser.setTitle("Load filters");
		if (file != null) {
			openFile.accept(file);
		}
	}

	/**
	 * Open file to desktop
	 * 
	 * @param file
	 *            the file to open
	 * @throws Exception
	 */
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
	 * Open peak list file.
	 * 
	 * @param peakListConsumer
	 *            a Consumer of peak list file when the file is loaded.
	 * @param fileChooserTitle
	 *            the parent stage of this file chooser.
	 */
	public static void openPeakListFile(Consumer<File> peakListConsumer, Stage stage) {
		// Default folder is 'Documents'
		File initialDirectory = new File(
				System.getProperty("user.home") + System.getProperty("file.separator") + "Documents");
		// Otherwise it's home folder
		if (!initialDirectory.exists())
			initialDirectory = new File(System.getProperty("user.home"));
		// if a file is already loaded then it's the same folder
		if (Session.CURRENT_FILE != null)
			initialDirectory = Session.CURRENT_FILE.getParentFile();
		FileChooser fileChooser = new FileChooser();
		// Set extension filter
		fileChooser.setInitialDirectory(initialDirectory);
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Mgf files (*.mgf)", "*.mgf"),
				new FileChooser.ExtensionFilter("PeakList files (*.pkl)", "*.pkl"));
		fileChooser.setTitle("Open file");
		// Show open file dialog
		File file = fileChooser.showOpenDialog(stage);
		if (file != null) {
			peakListConsumer.accept(file);
		}
	}

	/**
	 * @param fileConsumer
	 *            consumer accept the JSON file to save .
	 * 
	 * @param primaryStage
	 *            the parent stage of the file chooser
	 * 
	 */
	public static void saveFilterAs(Consumer<File> fileConsumer, Stage stage) {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle("Save filters");
		File file = fileChooser.showSaveDialog(stage);
		if (file != null) {
			fileConsumer.accept(file);
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
			}
		}
	}

}