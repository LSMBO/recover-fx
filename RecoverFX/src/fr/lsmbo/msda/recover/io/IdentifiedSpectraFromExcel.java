package fr.lsmbo.msda.recover.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fr.lsmbo.msda.recover.lists.IdentifiedSpectra;
import fr.lsmbo.msda.recover.view.IdentifiedSpectraController;
import javafx.scene.control.TextInputDialog;

public class IdentifiedSpectraFromExcel {

	public static void load(File file) {
		try {
			FileInputStream fileExcel = new FileInputStream(new File(file.getAbsolutePath()));

			XSSFWorkbook workbook = new XSSFWorkbook(fileExcel);

			TextInputDialog dialog = new TextInputDialog("Ex: For column \"A\" enter \"1\"");
			dialog.setTitle("Information about your Excel File");
			dialog.setContentText("Please enter the column of your title");
			dialog.showAndWait();

			int indexColum = Integer.parseInt(dialog.getResult());
			int nbTitle = 0;

			XSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIteratorCount = sheet.iterator();
			Iterator<Row> rowIterator = sheet.iterator();

			// count number of title
			while (rowIteratorCount.hasNext()) {
				rowIteratorCount.next();
				nbTitle++;
			}
			
			
			String[] title = new String[nbTitle];

			int i = 0;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				title[i] = row.getCell(indexColum - 1).getStringCellValue();
				i++;
			}

			IdentifiedSpectra identifiedSpectra = IdentifiedSpectraController.getIdentifiedSpectra();
			identifiedSpectra.setArrayTitles(title);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
