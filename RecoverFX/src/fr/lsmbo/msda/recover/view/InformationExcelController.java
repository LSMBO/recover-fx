package fr.lsmbo.msda.recover.view;

import fr.lsmbo.msda.recover.io.IdentifiedSpectraFromExcel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InformationExcelController {
	
	private static Stage dialogStage;

	@FXML
	private Button btnOk;
	@FXML
	private ChoiceBox<String> sheetList ;
	@FXML
	private TextField columnIndex ;
	
	public static void setDialogStage(Stage _dialogStage) {
		dialogStage = _dialogStage;
	}
	
	@FXML
	private void initialize(){
		sheetList.setItems(IdentifiedSpectraFromExcel.getListSheet());
	}
	
	@FXML
	private void handleClickBtnOk(){
		
	}
}
	
	
