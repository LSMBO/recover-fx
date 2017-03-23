package fr.lsmbo.msda.recover.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FiltersReader {

	public static void load(File loadFile) {

		try (BufferedReader reader = new BufferedReader(new FileReader(loadFile))) {
	
			String line = reader.readLine();
			int firstIndex = line.indexOf("{");
			int lastIndex = line.indexOf("}");
			
			String newLine = line.substring(firstIndex +1, lastIndex);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}