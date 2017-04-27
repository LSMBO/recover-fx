package fr.lsmbo.msda.recover.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fr.lsmbo.msda.recover.lists.IdentifiedSpectra;
import fr.lsmbo.msda.recover.view.IdentifiedSpectraController;
import javafx.scene.control.TextInputDialog;

public class IdentifiedSpectraFromExcel {
	private static String title = "";


	
	@SuppressWarnings("deprecation")
	public static void load(File file) {
		try {
			title = file.getName();
			FileInputStream fileExcel = new FileInputStream(new File(file.getAbsolutePath()));

			XSSFWorkbook workbook = new XSSFWorkbook(fileExcel);
			XSSFSheet sheet = null ;
			int nbSheetInWorkbook = workbook.getNumberOfSheets();
			
			Boolean titlesFoundInSheet = false;
			Boolean titlesFoundInColumn = false;
			Boolean changeContentTextSheet = false;
			Boolean changeContentTextColumn= false ;
			int nbTitle = 0;
			

		while(!titlesFoundInSheet){
			
			
			TextInputDialog dialogSheet = new TextInputDialog();
			dialogSheet.setTitle("Information about the sheet used");
			
			
			if(!changeContentTextSheet){
				dialogSheet.setContentText("Please enter here the index of the sheet which contains titles. \nIndex must be between 1 and " + nbSheetInWorkbook +".");
			} else{
				dialogSheet.setContentText("No titles was found, please enter a good index of your sheet. \nIndex must be between 1 and " + nbSheetInWorkbook +".");
			}
			dialogSheet.showAndWait();
			
			int indexSheet = Integer.parseInt(dialogSheet.getResult()) - 1;
			
			sheet = workbook.getSheetAt(indexSheet);
			Iterator<Row> rowIteratorCount = sheet.iterator();
			
			
			while(rowIteratorCount.hasNext()){
				rowIteratorCount.next();
				nbTitle++;
			}
			
			if(nbTitle !=0){
				titlesFoundInSheet = true;
			} else {
				changeContentTextSheet = true;
			}
		}
		
		String[] titles = new String[nbTitle];
		
		while(!titlesFoundInColumn){
			TextInputDialog dialogColumn = new TextInputDialog("Ex: For \"A\" enter \"1\"");
			dialogColumn.setTitle("Information about the column used");
			if(!changeContentTextColumn){
				dialogColumn.setContentText("Please enter the column of your title");
			} else {
				dialogColumn.setContentText("No titles was found, make sure the column index was good");
			}
			dialogColumn.showAndWait();
			
			int indexColumn = Integer.parseInt(dialogColumn.getResult()) - 1;
			Iterator<Row> rowIterator =  sheet.iterator();
			
			
			try{
				int i = 0;
				while(rowIterator.hasNext()){
				Row row = rowIterator.next();
				Cell cell = row.getCell(indexColumn);
				if(cell.getCellTypeEnum() == CellType.FORMULA){
					FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
					CellValue cellValue = evaluator.evaluate(cell);
					if(cellValue.getCellTypeEnum() == CellType.STRING){
						titles[i] = cellValue.getStringValue();
						i++;
					}
				} else if (cell.getCellTypeEnum() == CellType.STRING){
					titles[i] = cell.getStringCellValue();
					i++;
				}
					titlesFoundInColumn = true;
				}
			} catch(NullPointerException e){
				changeContentTextColumn = true;
			}
			
			
		}
		
		workbook.close();
		
		IdentifiedSpectra identifiedSpectra = IdentifiedSpectraController.getIdentifiedSpectra();
		identifiedSpectra.setArrayTitles(titles);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getTitle() {
		return title;
	}
	

}
