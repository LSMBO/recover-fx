
package fr.lsmbo.msda.recover.gui.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fr.lsmbo.msda.recover.gui.lists.IdentifiedSpectra;
import fr.lsmbo.msda.recover.gui.model.settings.FileSelectionParams;
import fr.lsmbo.msda.recover.gui.view.dialog.TitlesSelectorExcelDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class to extract titles in excel file and put them in class
 * identifiedSpectra. Read an excel file, recover sheets, open a new window to
 * get back information about the sheet and the column to extract these titles
 * by iteration.
 * 
 * @author LOMBART.benjamin
 * @author Aromdhani
 *
 */
public class IdentifiedSpectraFromExcel {

	private static final Logger logger = LogManager.getLogger(IdentifiedSpectraFromExcel.class);

	private static String title = "";
	private ObservableList<String> sheetList = FXCollections.observableArrayList();
	private FileSelectionParams fileParams = new FileSelectionParams();
	private ArrayList<String> titles = new ArrayList<>();
	private IdentifiedSpectra identifiedSpectra;
	private HashMap<String, Object> selectedParamsByName = new HashMap<String, Object>();

	/**
	 * Return the the sheet list.
	 * 
	 * @return the sheet list
	 */
	public ObservableList<String> getListSheet() {
		return sheetList;
	}

	/**
	 * Set identified spectra object.
	 * 
	 * @param identifiedSpectra
	 *            the identified spectra object to set
	 */
	public void setIdentifiedSpectra(IdentifiedSpectra identifiedSpectra) {
		this.identifiedSpectra = identifiedSpectra;
	}

	/**
	 * Return the identified titles.
	 * 
	 * @return the titles
	 */
	public ArrayList<String> getTitles() {
		return titles;
	}

	/**
	 * Return file title.
	 */
	public static String getTitle() {
		return title;
	}

	/**
	 * Initialize all values.
	 */
	public void initialize() {
		if (sheetList != null) {
			sheetList.clear();
		}
		if (titles.size() != 0) {
			titles.clear();
		}
		title = "";
		this.fileParams.initialize();
	}

	/**
	 * @return the file parameters
	 */
	public final FileSelectionParams getFileParams() {
		return fileParams;
	}

	/**
	 * @param fileParams
	 *            the file parameters to set
	 */
	public final void setFileParams(FileSelectionParams fileParams) {
		this.fileParams = fileParams;
	}

	/**
	 * 
	 * @param file
	 *            the spectrum titles file to identify (an excel file)
	 */
	public void load(File file) {
		try {
			initialize();
			title = file.getName();
			fileParams.setFilePath(file.getPath());
			FileInputStream fileExcel = new FileInputStream(new File(file.getAbsolutePath()));

			XSSFWorkbook workbook = new XSSFWorkbook(fileExcel);

			// Recover number of sheet in the workbook and save all the
			// sheets(name) present in list.
			int nbSheet = workbook.getNumberOfSheets();
			for (int i = 0; i < nbSheet; i++) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				String sheetName = sheet.getSheetName();
				sheetList.add(sheetName);
			}

			TitlesSelectorExcelDialog.setSheets(sheetList);
			getSpectrumTitlesSelection();

			// Transform a string column ("A", "B" ...) as an index
			int columnIndex = CellReference.convertColStringToIndex(fileParams.getColumn());
			XSSFSheet currentSheet = workbook.getSheet(fileParams.getCurrentSheetName());
			Iterator<Row> rowIterator = currentSheet.iterator();

			// Iterate through all row
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				// Iterate through all cell for a row
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					// Add value contains in the cell only for the cells from
					// specific row and in the good column
					if (row.getRowNum() >= fileParams.getRowNumber()) {
						if (cell.getColumnIndex() == columnIndex) {
							titles.add(cell.getStringCellValue());
						}
					}
				}
			}
			if (identifiedSpectra.getArrayTitles() != null) {
				identifiedSpectra.setArrayTitles(titles);
			} else {
				identifiedSpectra.addAllTitles(titles);
			}
			workbook.close();
		} catch (IOException e) {
			logger.error("Error while trying to load spectrum titles from an excel file!", e);
		}
	}

	/**
	 * Load spectrum title file. Get the spectrum titles from a specific
	 * selection from an excel file.
	 * 
	 * @param file
	 *            the spectrum titles file(excel)
	 * @param sheetName
	 *            the sheet name
	 * @param column
	 *            the column name
	 * @param rowNumber
	 *            the row number
	 */
	public void loadFromSelection(File file, String currentSheetName, String column, int rowNumber) {
		try {
			initialize();
			title = file.getName();
			fileParams.setFilePath(file.getPath());
			FileInputStream fileExcel = new FileInputStream(new File(file.getAbsolutePath()));
			XSSFWorkbook workbook = new XSSFWorkbook(fileExcel);
			// Transform a string column ("A", "B" ...) as an index
			int columnIndex = CellReference.convertColStringToIndex(column);
			XSSFSheet currentSheet = workbook.getSheet(currentSheetName);
			Iterator<Row> rowIterator = currentSheet.iterator();
			// Iterate through all row
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				// Iterate through all cell for a row
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					// Add value contains in the cell only for the cells from
					// specific row and in the good column
					if (row.getRowNum() >= rowNumber) {
						if (cell.getColumnIndex() == columnIndex) {
							titles.add(cell.getStringCellValue());
						}
					}
				}
			}
			if (identifiedSpectra.getArrayTitles() != null) {
				identifiedSpectra.setArrayTitles(titles);
			} else {
				identifiedSpectra.addAllTitles(titles);
			}
			workbook.close();
		} catch (IOException e) {
			logger.error("Error while trying to load a selection from a spectrum titles!", e);
		}
	}

	/**
	 * Return the spectrum titles selection from an excel file.
	 */
	@SuppressWarnings("unchecked")
	private void getSpectrumTitlesSelection() {
		try {
			TitlesSelectorExcelDialog excelSelectorDialog = new TitlesSelectorExcelDialog();
			excelSelectorDialog.showAndWait().ifPresent(selectorProperties -> {
				selectedParamsByName = (HashMap<String, Object>) selectorProperties.clone();
			});
			logger.info("Spectrum titles loaded from excel file: {}", selectedParamsByName);
			System.out.println("INFO - Spectrum titles loaded from excel file: " + selectedParamsByName);
			fileParams.setRowNumber((int) selectedParamsByName.get("rowNumber") - 1);
			fileParams.setColumn((String) selectedParamsByName.get("column"));
			fileParams.setCurrentSheetName((String) selectedParamsByName.get("currentSheetName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
