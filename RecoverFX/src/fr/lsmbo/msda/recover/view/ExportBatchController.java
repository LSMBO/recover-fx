package fr.lsmbo.msda.recover.view;

import java.io.File;
import java.io.IOException;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class ExportBatchController {

	@FXML
	private ListView<File> listFiles;
	@FXML
	private Button btnAddFiles;
	@FXML
	private Button btnResetFiles;

	@FXML
	private ListView<String> listIdentification;
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
	private ListView<File> listFilesProcessed;
	@FXML
	private Button btnOutputFolder;
	@FXML
	private TextField outputFolder;

	@FXML
	private ProgressBar progressBarProcessing;

	private Stage dialogStage;

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
		ExportBatch.initialize();

		listFiles.setItems(ExportBatch.getListFile());
		listIdentification.setItems(ExportBatch.getListIdentification());
		listFilesProcessed.setItems(ExportBatch.getListFileProcess());

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
	private void handleClickBtnAddFiles() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import your files");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("MGF", "*.mgf"),
				new ExtensionFilter("PKL", "*.pkl"));
		List<File> files = fileChooser.showOpenMultipleDialog(this.dialogStage);

		if (files != null) {
			ExportBatch.addListFile(files);
			btnResetFiles.setDisable(false);
		}
	}

	@FXML
	private void handleClickBtnResetFiles() {
		ExportBatch.resetListFile();
		btnResetFiles.setDisable(true);
	}

	@FXML
	private void handleClickBtnOpenTextBox() {
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
			IdentifiedSpectraForBatchController identifiedSpectraForBatchController = loader.getController();
			identifiedSpectraForBatchController.setDialogStage(dialogStage);
			dialogStage.showAndWait();

			IdentifiedSpectra identifiedSpectra = IdentifiedSpectraForBatchController.getIdentifiedSpectra();
			if (identifiedSpectra.getArrayTitles() != null) {
				ExportBatch.addListIdentification(identifiedSpectra.getArrayTitles());

				btnResetIdentification.setDisable(false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void handleClickBtnExcelFile() {
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Import your excel file");
		filechooser.getExtensionFilters().addAll(new ExtensionFilter("File XLS","*.xlsx"));
		File excelFile = filechooser.showOpenDialog(this.dialogStage);
		IdentifiedSpectraFromExcel.load(excelFile);
	}

	@FXML
	private void handleClickBtnResetIdentification() {
		ExportBatch.resetListIdentification();
		btnResetIdentification.setDisable(true);
	}

	@FXML
	private void handleClickBtnAddFilter() throws JsonParseException, IOException {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select a filter settings file");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON", "*.json"));
			File initialDirectory = Session.DIRECTORY_FILTER_FILE;
			if (initialDirectory != null) {
				fileChooser.setInitialDirectory(initialDirectory);
			}

			File loadFile = fileChooser.showOpenDialog(this.dialogStage);
			
			if (loadFile != null) {
				Session.DIRECTORY_FILTER_FILE = loadFile.getParentFile();
				FilterReaderJson.load(loadFile);
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
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Choose a destination folder");
		File directory = directoryChooser.showDialog(this.dialogStage);
		if (directory != null) {
			outputFolder.setText(directory.getAbsolutePath());
			ExportBatch.setDirectoryFolder(directory.getAbsolutePath());
		}
	}

	@FXML
	private void handleClickBtnProcessFile() {

		if (listFiles.getItems().size() == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Files missing");
			alert.setHeaderText("Files to be processed are missing, please import files");
			alert.showAndWait();
			handleClickBtnAddFiles();
		}

		else if (listIdentification.getItems().size() == 0 && Filters.nbFilterUsed() == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Identification and filter missing");
			alert.setHeaderText(
					"There are no title to identified spectra and no filters used, please use at least one of two");
			alert.showAndWait();
		}

		// Verify if a folder was selected, if not open the dialog to select a
		// folder
		else if (outputFolder.getText().length() == 0) {
			handleClickBtnOutputFolder();
		}

		else{
			long startTime = System.currentTimeMillis();
			ExportBatch.Main();
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
