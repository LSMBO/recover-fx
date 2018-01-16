package fr.lsmbo.msda.recover.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listFiles;
	}

	/**
	 * @param extensions
	 *            list of extensions to search
	 * 
	 */
	public static void getExtension(String[] extensions) {

	}
}
