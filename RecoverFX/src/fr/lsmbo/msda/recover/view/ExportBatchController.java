package fr.lsmbo.msda.recover.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jfree.ui.ExtensionFileFilter;
import org.junit.experimental.theories.Theories;

import com.fasterxml.jackson.core.JsonParseException;

import fr.lsmbo.msda.recover.Session;
import fr.lsmbo.msda.recover.Views;
import fr.lsmbo.msda.recover.io.ExportBatch;
import fr.lsmbo.msda.recover.io.FilterReaderJson;
import fr.lsmbo.msda.recover.io.IdentifiedSpectraFromExcel;
import fr.lsmbo.msda.recover.io.PeaklistReader;
import fr.lsmbo.msda.recover.lists.Filters;
import fr.lsmbo.msda.recover.lists.IdentifiedSpectra;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;

public class ExportBatchController {

	@FXML
	private Button btnTest;

	@FXML
	private ListView<File> listViewFilesToProcess;
	@FXML
	private Button btnAddFiles;
	@FXML
	private Button btnResetFiles;

	@FXML
	private ListView<String> listViewTitles;
	@FXML
	private RadioButton rbtnIdenficationTextBox;
	@FXML
	private Button btnOpenTextBox;
	@FXML
	private RadioButton rbtnIdenficationExcelFile;
	@FXML
	private Button btnOpenExcelFile;
	@FXML
	private Button btnResetIdentification;

	@FXML
	private RadioButton rbtnFilterFromRecover;
	@FXML
	private RadioButton rbtnFilterFromFile;
	@FXML
	private TextField fileFilter;
	@FXML
	private Button btnAddFilter;
	@FXML
	private Button btnResetFilter;

	@FXML
	private ListView<File> listViewFilesProcessed;
	@FXML
	private Button btnOutputFolder;
	@FXML
	private TextField outputFolder;
	@FXML
	private Button btnProcessFile;
	
	@FXML
	private ToggleGroup identification;
	@FXML
	private ToggleGroup filter;

	@FXML
	private ProgressBar progressBarProcessing;

	private Stage dialogStage;
	private ExportBatch exportBatch = new ExportBatch();

	// Object identifiedSpectra common for the TextBox(class
	// IdentifiedSpectraForBatchController) and the excel file (class
	// IdentifiedSpectraFromExcel)
	private IdentifiedSpectra identifiedSpectra = new IdentifiedSpectra();

	public static Boolean specificIdentification = false;
	private IdentifiedSpectraForBatchController identifiedSpectraForBatchController;

	@FXML
	private void initialize() {
		btnResetFiles.setDisable(true);
		btnOpenExcelFile.setDisable(true);
		fileFilter.setDisable(true);
		btnAddFilter.setDisable(true);
		btnResetFilter.setDisable(true);
		btnResetIdentification.setDisable(true);

		rbtnIdenficationTextBox.setSelected(true);
		rbtnFilterFromRecover.setSelected(true);

		// Different listView linked with variable of ExportBatch
		listViewFilesToProcess.setItems(exportBatch.getListFileToProcess());
		listViewTitles.setItems(exportBatch.getListTitles());
		listViewFilesProcessed.setItems(exportBatch.getListFileProcessed());

		// add specific identification excel file for a file
		ContextMenu contextMenu = new ContextMenu();
		MenuItem addExcelFile = new MenuItem("Add excel file ...");
		contextMenu.getItems().add(addExcelFile);
		listViewFilesToProcess.setContextMenu(contextMenu);

		addExcelFile.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
			private Window dialogStage;

			@Override
			public void handle(ActionEvent event) {
				// Open an excel file for the selected file and create new
				// object of identified spectra. Set the object identified
				// spectra and get titles for this excel file.
				// Return this titles in an arrayList and add this file with
				// titles in HashMap.
				File file = listViewFilesToProcess.getSelectionModel().getSelectedItem();

				if (file != null) {
					FileChooser filechooser = new FileChooser();
					filechooser.setTitle("Import your excel file");
					filechooser.getExtensionFilters().addAll(new ExtensionFilter("File XLS", "*.xlsx"));
					File excelFile = filechooser.showOpenDialog(this.dialogStage);
					specificIdentification = true;
					
					IdentifiedSpectra specificIdentifiedSpectra = new IdentifiedSpectra();
					IdentifiedSpectraFromExcel specificIdentifiedSpectraFromExcel = new IdentifiedSpectraFromExcel();
					
					//call method load from identifiedSpectraFromExcel to find titles in the excel file
					specificIdentifiedSpectraFromExcel.setIdentifiedSpectra(specificIdentifiedSpectra);
					specificIdentifiedSpectraFromExcel.load(excelFile);
					
					//array list contains titles found in excel file
					ArrayList<String> specificListTitles = new ArrayList<>(specificIdentifiedSpectraFromExcel.getTitles());
					
					//add in the hashmap the corresponding file with its titles
					exportBatch.addListTitlesWithCorrespondingFile(file, specificListTitles);
				}
			}
		});

	}

	// @FXML
	// private void doTest() {
	// exportBatch.makeSomeTest();
	// }

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
	private void handleClickBtnAddFiles() {
		// Open dialog to select multiple files and add them in an arrayList in
		// ExportBatch
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import your files");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("MGF", "*.mgf"), new ExtensionFilter("PKL", "*.pkl"));
		List<File> files = fileChooser.showOpenMultipleDialog(this.dialogStage);

		if (files != null) {
			exportBatch.addFilesInObservableList(files);
			
			//allow to reset with enable the button reset
			btnResetFiles.setDisable(false);
		}

	}

	@FXML
	private void handleClickBtnResetFiles() {
		//reset the list and the hashmap in exportBatch and disable button reset
		exportBatch.resetListFileToProcess();
		btnResetFiles.setDisable(true);
	}

	@FXML
	private void handleClickBtnOpenTextBox() {
		// Open a TextBox, set the object identifiedSpectra for the class
		// identifiedSpectraForBatchController and use this class to get titles
		// enter by the user
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Views.IDENTIFIED_SPECTRA_FOR_BATCH);
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Text box to identify spectra");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.dialogStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			identifiedSpectraForBatchController = loader.getController();
			identifiedSpectraForBatchController.setDialogStage(dialogStage);
			identifiedSpectraForBatchController.setidentifiedSpectra(identifiedSpectra);

			// Reset titles present in identifiedSpectra. if the text box is
			// used more than once, identifiedSpectra is the same object. need
			// to get only this title
			// (and not their present in previous TextBox) to add in the
			// ArrayList in ExportBatch. If not, display a warning duplicate
			// identification.
			identifiedSpectra.resetArrayTitles();
			dialogStage.showAndWait();

			if (!identifiedSpectra.getArrayTitles().isEmpty()) {
				exportBatch.addTitlesInObservableList(identifiedSpectra.getArrayTitles());
				btnResetIdentification.setDisable(false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void handleClickBtnExcelFile() {
		// Open dialog to select an excel file. Set identifiedSpectra in the
		// class IdentifiedSpectraFromExcel (common with
		// identifiedSpectraForBatchController)
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Import your excel file");
		filechooser.getExtensionFilters().addAll(new ExtensionFilter("File XLS", "*.xlsx"));
		File excelFile = filechooser.showOpenDialog(this.dialogStage);
		if (excelFile != null) {

			IdentifiedSpectraFromExcel identifiedSpectraFromExcel = new IdentifiedSpectraFromExcel();
			identifiedSpectraFromExcel.setIdentifiedSpectra(identifiedSpectra);

			// Same to handleClickBtnOpenTextBox()
			identifiedSpectra.resetArrayTitles();
			
			identifiedSpectraFromExcel.load(excelFile);

			if (identifiedSpectra.getArrayTitles() != null) {
				exportBatch.addTitlesInObservableList(identifiedSpectra.getArrayTitles());
				btnResetIdentification.setDisable(false);
			}
		}
	}

	@FXML
	private void handleClickBtnResetIdentification() {
		//reset the list which contains titles and disable button reset 
		exportBatch.resetListTitles();
		btnResetIdentification.setDisable(true);
	}

	@FXML
	private void handleClickBtnAddFilter() throws JsonParseException, IOException {
		// open a dialog to load json file (contains parameters of filters)
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select a filter settings file");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON", "*.json"));

			// define the directory to find the file
			File initialDirectory = Session.DIRECTORY_FILTER_FILE;
			if (initialDirectory != null) {
				fileChooser.setInitialDirectory(initialDirectory);
			}

			File loadFile = fileChooser.showOpenDialog(this.dialogStage);

			// set parameters of filter (in the main recover)
			if (loadFile != null) {
				Session.DIRECTORY_FILTER_FILE = loadFile.getParentFile();
				
				//Import parameter of filter
				FilterReaderJson.load(loadFile);
				
				//Put the name of the file in the textField
				fileFilter.setText(loadFile.getName());
				btnResetFilter.setDisable(false);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleClickBtnResetFilter() {
		fileFilter.setText(null);
		Filters.resetHashMap();
		btnResetFilter.setDisable(true);
	}

	@FXML
	private void handleClickBtnOutputFolder() {

		// open dialog to choose the folder where save files exported
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Choose a destination folder");
		File directory = directoryChooser.showDialog(this.dialogStage);
		if (directory != null) {
			outputFolder.setText(directory.getAbsolutePath());
			exportBatch.setNameDirectoryFolder(directory.getAbsolutePath());
		}
	}

	@FXML
	private void handleClickBtnProcessFile() {

		if (listViewFilesToProcess.getItems().size() == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Files missing");
			alert.setHeaderText("Files to be processed are missing, please import files");
			alert.showAndWait();
			handleClickBtnAddFiles();
		}

		else if (listViewTitles.getItems().size() == 0 && Filters.nbFilterUsed() == 0 && !specificIdentification) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Identification and filter missing");
			alert.setHeaderText("There are no title to identified spectra and no filters used, please use at least one of two");
			alert.showAndWait();
		}

		// Verify if a folder was selected, if not open the dialog to select a
		// folder
		else if (outputFolder.getText().length() == 0) {
			handleClickBtnOutputFolder();
		}

		else {
			long startTime = System.currentTimeMillis();
			progressBarProcessing.setProgress(-1);
			exportBatch.Main();
			progressBarProcessing.setProgress(1);
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("Time export batch: " + (double) totalTime / 1000 + " sec");

			ExportBatch.useBatchSpectra = false;
		}
	}

	@FXML
	private void checkRBtnIdentificationTextBox() {
		if (rbtnIdenficationTextBox.isSelected()) {
			btnOpenExcelFile.setDisable(true);
			btnOpenTextBox.setDisable(false);
		}
	}

	@FXML
	private void checkRBtnIdentificationExcelFile() {
		if (rbtnIdenficationExcelFile.isSelected()) {
			btnOpenTextBox.setDisable(true);
			btnOpenExcelFile.setDisable(false);
		}
	}

	@FXML
	private void checkRbtnFilterFromRecover() {
		if (rbtnFilterFromRecover.isSelected()) {
			fileFilter.setDisable(true);
			btnAddFilter.setDisable(true);
			btnResetFilter.setDisable(true);
		}
	}

	@FXML
	private void checkRbtnFilterFromFile() {
		if (rbtnFilterFromFile.isSelected()) {
			fileFilter.setDisable(false);
			btnAddFilter.setDisable(false);
		}
	}

}
