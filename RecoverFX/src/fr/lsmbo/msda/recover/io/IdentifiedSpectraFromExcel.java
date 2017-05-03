package fr.lsmbo.msda.recover.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fr.lsmbo.msda.recover.Views;
import fr.lsmbo.msda.recover.lists.IdentifiedSpectra;
import fr.lsmbo.msda.recover.view.ExportBatchController;
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
	private static int rowNumber = 0;
	private static String column = "";
	private static String currentSheetName = "";
	private static ArrayList<String> titles = new ArrayList<>();
	private static IdentifiedSpectra identifiedSpectra = null;

	public static void load(File file) {
		try {
			
			initialize();
			title = file.getName();
			FileInputStream fileExcel = new FileInputStream(new File(file.getAbsolutePath()));

			XSSFWorkbook workbook = new XSSFWorkbook(fileExcel);
			int nbSheet = workbook.getNumberOfSheets();

			for (int i = 0; i < nbSheet; i++) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				String sheetName = sheet.getSheetName();
				sheetList.add(sheetName);
			}

			openInformationExcel();

			int columnIndex = CellReference.convertColStringToIndex(column);
			XSSFSheet currentSheet = workbook.getSheet(currentSheetName);

			Iterator<Row> rowIterator = currentSheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					if (row.getRowNum() >= rowNumber) {
						if (cell.getColumnIndex() == columnIndex) {
							titles.add(cell.getStringCellValue());
						}
					}
				}
			}
			
			if(!ExportBatch.useBatchSpectra && !ExportBatchController.specificIdentification){
				identifiedSpectra = IdentifiedSpectraController.getIdentifiedSpectra();
				identifiedSpectra.setArrayTitles(titles);
			} else if(ExportBatch.useBatchSpectra && !ExportBatchController.specificIdentification){
				 identifiedSpectra = IdentifiedSpectraForBatchController.getIdentifiedSpectra();
				 identifiedSpectra.setArrayTitles(titles);
			}
			
			
			workbook.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialize() {

		if (sheetList != null) {
			sheetList.clear();
		}

		if (titles.size() != 0) {
			titles.clear();
		}

		title = "";
		rowNumber = 0;
		column = "";
		currentSheetName = "";
	}

	public static String getTitle() {
		return title;
	}

	private static void openInformationExcel() {
		try {
			FXMLLoader loader = new FXMLLoader();
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

	public static ObservableList<String> getListSheet() {
		return sheetList;
	}

	public static void setRowNumber(int _rowNumber) {
		rowNumber = _rowNumber;
	}

	public static void setColumn(String _column) {
		column = _column;
	}

	public static void setCurrentSheetName(String _currentSheetName) {
		currentSheetName = _currentSheetName;
	}
	
	public static IdentifiedSpectra getIdentifiedSpectraExcel(){
		return identifiedSpectra;
	}
	
	public static ArrayList<String> getTitles(){
		return titles;
	}
}
