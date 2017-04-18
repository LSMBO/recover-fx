package fr.lsmbo.msda.recover.view;

import fr.lsmbo.msda.recover.model.ConstantComparisonSpectra;
import fr.lsmbo.msda.recover.model.TextFieldConvertor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ComparisonSettingsController {

	private Stage dialogComparisonSettingsStage;

	@FXML
	private TextField deltaMoz;
	@FXML
	private TextField deltaRT;
	@FXML
	private TextField nbPeaks;
	@FXML
	private TextField nbPeaksMin;
	@FXML
	private TextField thetaMin;
	@FXML
	private Button btnApply;
	@FXML
	private Button btnValuesByDefault;
	@FXML
	private Button btnClose;

	private ObservableList<Alert> arrayAlert = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		deltaMoz.setText(ConstantComparisonSpectra.getDeltaMoz().toString());
		deltaRT.setText(ConstantComparisonSpectra.getDeltaRT().toString());

		nbPeaks.setText(ConstantComparisonSpectra.getNbPeaks().toString());
		nbPeaksMin.setText(ConstantComparisonSpectra.getNbPeaksMin().toString());
		thetaMin.setText(ConstantComparisonSpectra.getThetaMin().toString());
	}

	public void setDialogStage(Stage comparisonSettingsStage) {
		this.dialogComparisonSettingsStage = comparisonSettingsStage;
	}

	@FXML
	private void handleClickBtnApply() {
		try {
			Integer deltaRTValue = 0;
			Integer nbPeaksValue = 0;
			Integer nbPeaksMinValue = 0;
			Integer thetaMinValue = 0;
			
			Float deltaMozValue = TextFieldConvertor.changeTextFieldToFloat(deltaMoz);

			//Condition for deltaRT
			if (!deltaRT.getText().contains(".")) {
				deltaRTValue = TextFieldConvertor.changeTextFieldToInteger(deltaRT);
			} else {
				displayAlertIntError(deltaRT);
			}

			//Condition for nbPeaks
			if (!nbPeaks.getText().contains(".")) {
			nbPeaksValue = TextFieldConvertor.changeTextFieldToInteger(nbPeaks);
			} else {
				displayAlertIntError(nbPeaks);
			}
			
			//Condition for nbPeaksMin
			if (!nbPeaksMin.getText().contains(".")) {
			nbPeaksMinValue = TextFieldConvertor.changeTextFieldToInteger(nbPeaksMin);
			} else{
				displayAlertIntError(nbPeaksMin);
			}
			
			//Condtion for thetamin
			if (!thetaMin.getText().contains(".")) {
			thetaMinValue = TextFieldConvertor.changeTextFieldToInteger(thetaMin);
			} else{
				displayAlertIntError(thetaMin);
			}

			ConstantComparisonSpectra.setDeltamoz(deltaMozValue);
			ConstantComparisonSpectra.setDeltaRT(deltaRTValue);
			ConstantComparisonSpectra.setNbPeaks(nbPeaksValue);
			ConstantComparisonSpectra.setNbPeaksMin(nbPeaksMinValue);
			ConstantComparisonSpectra.setThetaMin(thetaMinValue);
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.WARNING);
			arrayAlert.add(alert);
			alert.setTitle("Error parameters settings");
			alert.setHeaderText("You should enter numeric values !");
			alert.showAndWait();
		}
		if (arrayAlert.size() == 0) {
			dialogComparisonSettingsStage.close();
		} else
			arrayAlert.clear();
	}

	@FXML
	private void handleClickBtnValuesByDefault() {
		ConstantComparisonSpectra.initialValue();
		initialize();
	}

	@FXML
	private void handleClickBtnClose() {
		dialogComparisonSettingsStage.close();
	}

	private void displayAlertIntError(TextField field) {
		String fieldName = "";

		switch (field.getId()) {
		case "deltaRT":
			fieldName = "Delta retention time (in second)";
			break;
		case "nbPeaks":
			fieldName = "Number of peaks";
			break;
		case "nbPeaksMin":
			fieldName = "Number of peaks minimum";
			break;
		case "thetaMin":
			fieldName = "Theta min";
			break;
		}

		Alert alert = new Alert(AlertType.WARNING);
		arrayAlert.add(alert);
		alert.setTitle("Error parameters settings");
		alert.setHeaderText("You should enter integer value for the field : " + fieldName);
		alert.showAndWait();
	}
}
