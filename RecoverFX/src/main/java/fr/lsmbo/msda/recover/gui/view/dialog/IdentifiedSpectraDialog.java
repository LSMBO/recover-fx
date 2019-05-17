/*
 * 
 */
package fr.lsmbo.msda.recover.gui.view.dialog;

import java.util.ArrayList;
import java.util.Arrays;

import fr.lsmbo.msda.recover.gui.IconResource;
import fr.lsmbo.msda.recover.gui.IconResource.ICON;
import fr.lsmbo.msda.recover.gui.filters.IdentifiedSpectraFilter;
import fr.lsmbo.msda.recover.gui.io.IdentifiedSpectraFromExcel;
import fr.lsmbo.msda.recover.gui.lists.IdentifiedSpectra;
import fr.lsmbo.msda.recover.gui.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.gui.util.FileUtils;
import fr.lsmbo.msda.recover.gui.util.JavaFxUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Creates and displays identified spectra dialog.
 * 
 * @author Aromdhani
 *
 */
public class IdentifiedSpectraDialog extends Dialog<Object> {
	private IdentifiedSpectra identifiedSpectra = new IdentifiedSpectra();
	private IdentifiedSpectraFilter identifiedSpectraFilter = new IdentifiedSpectraFilter();
	private IdentifiedSpectraFromExcel identifiedSpectraFromExcel = null;
	private static Boolean isExcelFileImported = false;
	// Components
	TextField filePathTF = null;

	public IdentifiedSpectraDialog() {
		// Create notifications pane
		VBox warningPane = new VBox(2);
		Label warningLabel = new Label(
				"Enter below the title of your spectrum that you want to identify please. One title per line!");
		warningLabel.setGraphic(new ImageView(IconResource.getImage(ICON.WARNING)));
		warningLabel.setStyle(JavaFxUtils.RED_ITALIC);
		Label emptyTitlesLabel = new Label(
				"No titles were imported from your excel file. Please select a valid file, check the sheet and the selected column!");
		emptyTitlesLabel.setGraphic(new ImageView(IconResource.getImage(ICON.WARNING)));
		emptyTitlesLabel.setStyle(JavaFxUtils.RED_ITALIC);
		warningPane.getChildren().addAll(warningLabel, emptyTitlesLabel);

		// Create identified spectra pane
		TextArea titlesTextArea = new TextArea();
		Separator separator = new Separator();
		Label filePathLabel = new Label("File title: ");
		filePathTF = new TextField();
		filePathTF.setText(IdentifiedSpectraFromExcel.getTitle());
		CheckBox loadTitlesChBx = new CheckBox("Load titles from excel file");
		Button loadTitlesFromExcelBtn = new Button("Load file");
		loadTitlesFromExcelBtn.setGraphic(new ImageView(IconResource.getImage(ICON.LOAD)));
		loadTitlesFromExcelBtn.setOnAction((e) -> {
			loadExcelFile();
		});

		// Layout
		GridPane IdentifiedSpectraDialog = new GridPane();
		IdentifiedSpectraDialog.setAlignment(Pos.CENTER);
		IdentifiedSpectraDialog.setPadding(new Insets(10));
		IdentifiedSpectraDialog.setHgap(25);
		IdentifiedSpectraDialog.setVgap(25);
		IdentifiedSpectraDialog.add(warningPane, 0, 0, 6, 1);
		IdentifiedSpectraDialog.add(titlesTextArea, 0, 1, 6, 3);
		IdentifiedSpectraDialog.add(separator, 0, 4, 6, 1);
		IdentifiedSpectraDialog.add(loadTitlesChBx, 0, 5, 2, 1);
		IdentifiedSpectraDialog.add(loadTitlesFromExcelBtn, 7, 6, 4, 1);
		IdentifiedSpectraDialog.add(filePathLabel, 0, 6, 1, 1);
		IdentifiedSpectraDialog.add(filePathTF, 3, 6, 4, 1);

		// Show notification and control input values
		loadTitlesFromExcelBtn.disableProperty().bind(loadTitlesChBx.selectedProperty().not());
		filePathLabel.disableProperty().bind(loadTitlesChBx.selectedProperty().not());
		filePathTF.disableProperty().bind(loadTitlesChBx.selectedProperty().not());
		emptyTitlesLabel.visibleProperty()
				.bind(filePathTF.textProperty().isEmpty().or(loadTitlesChBx.selectedProperty().not()));
		warningLabel.visibleProperty().bind(titlesTextArea.textProperty().isEmpty());
		/********************
		 * Main dialog pane *
		 ********************/
		// Create and display the main dialog pane
		DialogPane dialogPane = new DialogPane();
		dialogPane.setContent(IdentifiedSpectraDialog);
		dialogPane.setHeaderText("Get identified spectra");
		dialogPane.setGraphic(new ImageView(IconResource.getImage(ICON.IDENTIFIEDSPECTRA)));
		dialogPane.setPrefSize(800, 500);
		Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new ImageView(IconResource.getImage(ICON.IDENTIFIEDSPECTRA)).getImage());
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Button buttonOk = (Button) dialogPane.lookupButton(ButtonType.OK);
		this.setTitle("Get Identified Spectra");
		this.setDialogPane(dialogPane);
		buttonOk.disableProperty().bind(warningLabel.visibleProperty().and(emptyTitlesLabel.visibleProperty()));
		this.setResultConverter(buttonType -> {
			if (buttonType == ButtonType.OK) {
				// ObservableList<Object> listIS =
				// FXCollections.observableArrayList();
				// listIS.add(identifiedSpectraFilter);
				// Filters.add("IS", listIS);
				// Reset Spectra identified
				if (ListOfSpectra.getFirstSpectra().getNbIdentified() != 0) {
					ListOfSpectra.getFirstSpectra().resetIdentified();
				}
				// Convert textContent to an ArrayList of titles
				String[] arrayTitles = titlesTextArea.getText().split("\n");
				ArrayList<String> arrayListTitles = new ArrayList<>(Arrays.asList(arrayTitles));
				if (!isExcelFileImported) {
					identifiedSpectra.setArrayTitles(arrayListTitles);
				} else {
					identifiedSpectra.addAllTitles(arrayListTitles);
				}
				return identifiedSpectra;
			} else {
				return null;
			}
		});
	}

	/**
	 * Load spectrum titles from an excel file.
	 * 
	 */
	private void loadExcelFile() {
		FileUtils.loadExcelFile(file -> {
			identifiedSpectraFromExcel = new IdentifiedSpectraFromExcel();
			identifiedSpectraFromExcel.setIdentifiedSpectra(identifiedSpectra);
			identifiedSpectraFromExcel.load(file);
			if (identifiedSpectraFromExcel.getTitles().size() != 0) {
				isExcelFileImported = true;
				filePathTF.setText(file.getPath());
				identifiedSpectraFilter.setFileParams(identifiedSpectraFromExcel.getFileParams());
			}
		}, this.getDialogPane().getScene().getWindow());
	}

}