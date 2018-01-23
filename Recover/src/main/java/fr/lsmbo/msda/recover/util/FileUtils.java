package fr.lsmbo.msda.recover.util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.media.jfxmedia.logging.Logger;

import javafx.stage.FileChooser;

/**
 * 
 * @author aromdhani
 *
 */
public class FileUtils {
	/**
	 * 
	 * @param file
	 * @return list of files in a directory
	 */
	public static List<File> get(String file) {
		final List<File> listFiles = new ArrayList();
		try {
			if (new File(file).exists()) {
				File f = new File(file);
				if (f.isDirectory()) {
					File[] arrayFiles = f.listFiles();
					if ((arrayFiles != null) && (arrayFiles.length > 0)) {
						for (int i = 0; i <= arrayFiles.length; i++) {
							listFiles.add(arrayFiles[i]);
						}
					}
				} else {
					System.out.println("this is not a directory!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listFiles;
	}

	/**
	 * 
	 * @param fileChooser
	 *            create file chooser
	 * @param fileChooserTitle
	 *            the title of filechooser
	 */
	public static void cofigureFileChooser(final FileChooser fileChooser, String fileChooserTitle) {
		fileChooser.setTitle(fileChooserTitle);
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".pkl files", "*.pkl"),
				new FileChooser.ExtensionFilter(".mgf files", "*.mgf"));
	}

	public static void open(File file) {
		try {
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(file);
			}
		} catch (Exception e) {
			Logger.logMsg(4, "Error while trying to open file!");
		}
	}

	/**
	 * 
	 * @param path
	 *            open file
	 */
	public static void showFile(String path) {
		if (java.awt.Desktop.isDesktopSupported()) {
			try {
				java.awt.Desktop.getDesktop().browse(new File(path).toURI());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
