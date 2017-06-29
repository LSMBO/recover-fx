package fr.lsmbo.msda.recover.io;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fr.lsmbo.msda.recover.Views;
import fr.lsmbo.msda.recover.lists.IdentifiedSpectra;

import fr.lsmbo.msda.recover.view.InformationExcelController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class to extract titles in excel file and put them in class identifiedSpectra. Read an excel file, recover sheets, open a new window to get back information
 * about the sheet and the column to extract these titles by iteration.
 * 
 * @author LOMBART.benjamin
 *
 */
public class IdentifiedSpectraFromExcel {
	private static String title = "";
	private ObservableList<String> sheetList = FXCollections.observableArrayList();
	private int rowNumber = 0;
	private String column = "";
	private String currentSheetName = "";
	private ArrayList<String> titles = new ArrayList<>();

	
	private InformationExcelController informationExcelController;

	private IdentifiedSpectra identifiedSpectra;

	public IdentifiedSpectraFromExcel() {
	}

	/**
	 * 
	 * @param file
	 * 		Excel file which contains titles to make identification
	 */ public void load(File file) {
		try {

			initialization();
			title = file.getName();
			FileInputStream fileExcel = new FileInputStream(new File(file.getAbsolutePath()));

			XSSFWorkbook workbook = new XSSFWorkbook(fileExcel);
			
			//recover number of sheet in the workbook and save all the sheets(name) present in list.
			int nbSheet = workbook.getNumberOfSheets();
			for (int i = 0; i < nbSheet; i++) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				String sheetName = sheet.getSheetName();
				sheetList.add(sheetName);
			}

			
			openInformationExcel();

			//transform a string column ("A", "B" ...) in an index
			int columnIndex = CellReference.convertColStringToIndex(column);
			
			XSSFSheet currentSheet = workbook.getSheet(currentSheetName);

			Iterator<Row> rowIterator = currentSheet.iterator();

			//iterate through all row
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();

				//iterate through all cell for a row 
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					//add value contains in the cell only for the cells from specific row and in the good column
					if (row.getRowNum() >= rowNumber) {
						if (cell.getColumnIndex() == columnIndex) {
							titles.add(cell.getStringCellValue());
						}
					}
				}
			}

			//add titles in the object identified spectra, if no titles are present in the object (list), just initialize it
			//or else add in the list.
			if (identifiedSpectra.getArrayTitles() != null) {
				identifiedSpectra.setArrayTitles(titles);
			} else {
				identifiedSpectra.addAllTitles(titles);
			}

			workbook.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Initialize value
	public void initialization() {

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

	//Open a window in order to get information about the excel file imported( sheet to use and column)
	private void openInformationExcel() {
		try {
			//return the list of sheet
			InformationExcelController.setSheets(sheetList);
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Views.INFORMATION_EXCEL);
			BorderPane page = (BorderPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Information excel file");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			informationExcelController = loader.getController();
			informationExcelController.setDialogStage(dialogStage);
			dialogStage.showAndWait();

			//recover informations to use
			rowNumber = informationExcelController.getIndex() - 1;
			column = informationExcelController.getColumn();
			currentSheetName = informationExcelController.getSheetNameSelected();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ObservableList<String> getListSheet() {
		return sheetList;
	}

	public void setIdentifiedSpectra(IdentifiedSpectra identifiedSpectra) {
		this.identifiedSpectra = identifiedSpectra;
	}

	public ArrayList<String> getTitles() {
		return titles;
	}

}
