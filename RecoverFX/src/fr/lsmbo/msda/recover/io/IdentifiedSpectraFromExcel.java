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

import fr.lsmbo.msda.recover.Views;
import fr.lsmbo.msda.recover.lists.IdentifiedSpectra;
import fr.lsmbo.msda.recover.view.IdentifiedSpectraController;
import fr.lsmbo.msda.recover.view.IdentifiedSpectraForBatchController;
import fr.lsmbo.msda.recover.view.InformationExcelController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class IdentifiedSpectraFromExcel {
	private static String title = "";
	private static ObservableList<String> sheetList = FXCollections.observableArrayList();

	public static void load(File file) {
		try {
			title = file.getName();
			FileInputStream fileExcel = new FileInputStream(new File(file.getAbsolutePath()));

			XSSFWorkbook workbook = new XSSFWorkbook(fileExcel);
			int nbSheet = workbook.getNumberOfSheets();
			
			for(int i =0; i < nbSheet; i++){
				XSSFSheet sheet = workbook.getSheetAt(i);
				String sheetName = sheet.getSheetName();
				sheetList.add(sheetName);
			}

			openInformationExcel();
			} catch(IOException e){
				
			}
	}

	public static String getTitle() {
		return title;
	}
	
	private static void openInformationExcel(){
		try {
		FXMLLoader loader = new FXMLLoader() ;
		loader.setLocation(Views.INFORMATION_EXCEL);
		BorderPane page = (BorderPane) loader.load();
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Information excel file");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
		InformationExcelController controller = loader.getController();
		InformationExcelController.setDialogStage(dialogStage);
		dialogStage.showAndWait();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ObservableList<String> getListSheet(){
		return sheetList;
	}

}
