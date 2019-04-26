/*
 * 
 */
package fr.lsmbo.msda.recover.gui.view.dialog;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import fr.lsmbo.msda.recover.gui.IconResource;
import fr.lsmbo.msda.recover.gui.IconResource.ICON;
import fr.lsmbo.msda.recover.gui.lists.IdentifiedSpectra;
import fr.lsmbo.msda.recover.gui.util.JavaFxUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Creates and displays a selector dialog to choose a sheet and column from an
 * excel file.
 * 
 * @author Aromdhani
 *
 */
public class TitlesSelectorExcelDialog extends Dialog<HashMap<String, Object>> {

	private static ObservableList<String> sheets = FXCollections.observableArrayList();
	private int index = 0;
	private String column = "";
	private HashMap<String, Object> selectionProperties = new HashMap<String, Object>();

	public TitlesSelectorExcelDialog() {

		// Create excel selector dialog components

		// Create notifications pane
		VBox warningPane = new VBox(2);
		Label excelWarningLabel = new Label(
				"Select a valid excel file. You should select a sheet and enter a column to extract titles!");
		excelWarningLabel.setGraphic(new ImageView(IconResource.getImage(ICON.WARNING)));
		excelWarningLabel.setStyle(JavaFxUtils.RED_ITALIC);
		warningPane.getChildren().addAll(excelWarningLabel);

		// Create excel selector pane
		Label sheetLabel = new Label("Select sheet: ");
		ChoiceBox<String> sheetCmBox = new ChoiceBox<String>();
		sheetCmBox.minWidth(100);
		sheetCmBox.setItems(sheets);
		sheetCmBox.getSelectionModel().selectFirst();

		Label columnLabel = new Label("Enter column: ");
		TextField columnTF = new TextField();
		columnTF.setPromptText("Example : A3");
		columnTF.setTooltip(new Tooltip("Enter the column to extract titles. Exmaple: \"A3\""));
		// Layout
		GridPane IdentifiedSpectraDialog = new GridPane();
		IdentifiedSpectraDialog.setAlignment(Pos.CENTER);
		IdentifiedSpectraDialog.setPadding(new Insets(10));
		IdentifiedSpectraDialog.setHgap(25);
		IdentifiedSpectraDialog.setVgap(25);
		IdentifiedSpectraDialog.add(warningPane, 0, 0, 5, 2);
		IdentifiedSpectraDialog.add(sheetLabel, 0, 2, 1, 1);
		IdentifiedSpectraDialog.add(sheetCmBox, 1, 2, 1, 1);
		IdentifiedSpectraDialog.add(columnLabel, 0, 3, 1, 1);
		IdentifiedSpectraDialog.add(columnTF, 1, 3, 1, 1);

		/********************
		 * Main dialog pane *
		 ********************/
		// Create and display the main dialog pane
		DialogPane dialogPane = new DialogPane();
		dialogPane.setContent(IdentifiedSpectraDialog);
		dialogPane.setHeaderText("Get identified spectra");
		dialogPane.setGraphic(new ImageView(IconResource.getImage(ICON.IDENTIFIEDSPECTRA)));
		dialogPane.setPrefHeight(300);
		Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new ImageView(IconResource.getImage(ICON.IDENTIFIEDSPECTRA)).getImage());
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Button buttonOk = (Button) dialogPane.lookupButton(ButtonType.OK);
		this.setTitle("Identified Spectra");
		this.setDialogPane(dialogPane);

		// Show notification and control input values
		SimpleBooleanProperty isValidColumn = new SimpleBooleanProperty(false);
		columnTF.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue.matches("[a-zA-Z0-9]+")) {
				isValidColumn.setValue(true);
			} else {
				isValidColumn.setValue(false);
			}
		});
		excelWarningLabel.visibleProperty().bind(columnTF.textProperty().isEmpty()
				.or(sheetCmBox.getSelectionModel().selectedItemProperty().isNull()).or(isValidColumn.not()));
		buttonOk.disableProperty().bind(excelWarningLabel.visibleProperty());
		// On apply
		this.setResultConverter(buttonType -> {
			if ((buttonType == ButtonType.OK)) {
				index = Integer.parseInt(columnTF.getText().replaceAll("\\D+", ""));
				column = columnTF.getText().replaceAll("\\d+", "");
				selectionProperties.put("column", column);
				selectionProperties.put("rowNumber", index);
				selectionProperties.put("currentSheetName", sheetCmBox.getValue());
				return selectionProperties;
			}
			return selectionProperties;
		});
	}

	public static void setSheets(ObservableList<String> list) {
		sheets = list;
	}
}