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
	private static String title = "";
	private static Boolean titleFoundInSheet = false;
	private static int nbTitle = 0;

	
	public static void load(File file) {
		try {
			title = file.getName();
			FileInputStream fileExcel = new FileInputStream(new File(file.getAbsolutePath()));

			XSSFWorkbook workbook = new XSSFWorkbook(fileExcel);
			Boolean changeContentText = false;
		while(!titleFoundInSheet){
			
			
			TextInputDialog dialogSheet = new TextInputDialog();
			dialogSheet.setTitle("Information about the sheet used");
			
			if(!changeContentText){
				dialogSheet.setContentText("Please enter here the index of the sheet which contains titles.");
			} else{
				dialogSheet.setContentText("No titles was found, please enter a good index of your sheet");
			}
			dialogSheet.showAndWait();
			
			int indexSheet = Integer.parseInt(dialogSheet.getResult()) - 1;
			
			XSSFSheet sheet = workbook.getSheetAt(indexSheet);
			Iterator<Row> rowIteratorCount = sheet.iterator();
			
			
			while(rowIteratorCount.hasNext()){
				rowIteratorCount.next();
				nbTitle++;
			}
			
			if(nbTitle !=0){
				titleFoundInSheet = true;
			} else {
				changeContentText = true;
			}
		}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getTitle() {
		return title;
	}
	
	public static String rewriteGoodIndex(){
		TextInputDialog dialogSheet = new TextInputDialog();
		dialogSheet.setTitle("Information about the sheet used");
		dialogSheet.setContentText("No titles was found. Please verify and enter the good index of your sheet");
		dialogSheet.showAndWait();
		
		return dialogSheet.getResult();
	}
}
