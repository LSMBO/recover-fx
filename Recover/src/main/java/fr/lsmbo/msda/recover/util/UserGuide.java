package fr.lsmbo.msda.recover.util;

import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

/**
 * 
 * @author aromdhani
 *
 */
public class UserGuide {
	/**
	 * 
	 * @param path
	 */
	public static void getFile(String path) {
		if (java.awt.Desktop.isDesktopSupported()) {
			try {
				java.awt.Desktop.getDesktop().browse(new File(path).toURI());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
