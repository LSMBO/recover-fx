package fr.lsmbo.msda.recover.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class InformationExcelController {

	private static Stage dialogStage;

	@FXML
	private Button btnOk;
	@FXML
	private ChoiceBox<String> sheetList;
	@FXML
	private TextField columnIndex;
	
	private static ObservableList<String> sheets = FXCollections.observableArrayList();
	private String sheetNameSelected ="";
	private int index = 0;
	private String column ="";

	public void setDialogStage(Stage _dialogStage) {
		dialogStage = _dialogStage;
	}

	@FXML
	private void initialize() {
		sheetList.setItems(sheets);
		sheetList.getSelectionModel().selectFirst();
		columnIndex.setPromptText("Ex : A3");
	}

	@FXML
	private void handleClickBtnOk() {
		sheetNameSelected = sheetList.getValue();
		try {
			index = Integer.parseInt(columnIndex.getText().replaceAll("\\D+", ""));
			column = columnIndex.getText().replaceAll("\\d+", "");
			if (!column.isEmpty() && !column.contains(" ")) {
				dialogStage.close();
			} else {
				displaySyntaxError();
			}
		} catch (NumberFormatException e) {
			displaySyntaxError();
		}
	}

	private void displaySyntaxError() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Syntax error");
		alert.setContentText("Input expected for column is for example : \"A5\"");
		alert.showAndWait();
	}

	public static void setSheets(ObservableList<String> list){
		sheets = list;
	}
	
	public String getSheetNameSelected(){
		return sheetNameSelected;
	}
	
	public int getIndex(){
		return index;
	}
	
	public String getColumn(){
		return column;
	}
	
}
