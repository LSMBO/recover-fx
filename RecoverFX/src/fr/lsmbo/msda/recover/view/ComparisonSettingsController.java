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
	private Integer deltaRTValue = 0;
	private Integer nbPeaksValue = 0;
	private Integer nbPeaksMinValue = 0;
	private Integer thetaMinValue = 0;
	private Float deltaMozValue = 0F;

	@FXML
	// Initialize differents field with the default value in the algorithm
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
	//
	private void handleClickBtnApply() {
		try {

			// convert string in float and check if the values isn't negative. If yes, display an
			// alert window
			deltaMozValue = TextFieldConvertor.changeTextFieldToFloat(deltaMoz);
			if (deltaMozValue < 0) {
				alertNegativeValue();
			}

			// Check if the field deltaRT doesn't contains point (means it's a float). If not
			// convert the value in integer
			// and check if the values isn't negative. if yes, display an alert window
			if (!deltaRT.getText().contains(".")) {
				deltaRTValue = TextFieldConvertor.changeTextFieldToInteger(deltaRT);
				if (deltaRTValue < 0) {
					alertNegativeValue();
				}
			} else {
				displayAlertIntError(deltaRT);
			}

			// Same as deltaRT
			if (!nbPeaks.getText().contains(".")) {
				nbPeaksValue = TextFieldConvertor.changeTextFieldToInteger(nbPeaks);
				if (nbPeaksValue < 0) {
					alertNegativeValue();
				}
			} else {
				displayAlertIntError(nbPeaks);
			}

			// Same as deltaRT
			if (!nbPeaksMin.getText().contains(".")) {
				nbPeaksMinValue = TextFieldConvertor.changeTextFieldToInteger(nbPeaksMin);
				if (nbPeaksMinValue < 0) {
					alertNegativeValue();
				}
			} else {
				displayAlertIntError(nbPeaksMin);
			}

			// Same as deltaRT
			if (!thetaMin.getText().contains(".")) {
				thetaMinValue = TextFieldConvertor.changeTextFieldToInteger(thetaMin);
				if (thetaMinValue < 0) {
					alertNegativeValue();
				}
			} else {
				displayAlertIntError(thetaMin);
			}

		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.WARNING);
			arrayAlert.add(alert);
			alert.setTitle("Error parameters settings");
			alert.setHeaderText("You should enter numeric values !");
			alert.showAndWait();
		}
		if (arrayAlert.size() == 0) {
			ConstantComparisonSpectra.setDeltamoz(deltaMozValue);
			ConstantComparisonSpectra.setDeltaRT(deltaRTValue);
			ConstantComparisonSpectra.setNbPeaks(nbPeaksValue);
			ConstantComparisonSpectra.setNbPeaksMin(nbPeaksMinValue);
			ConstantComparisonSpectra.setThetaMin(thetaMinValue);
			dialogComparisonSettingsStage.close();
		} else
			arrayAlert.clear();
	}

	@FXML
	// re-initialize values of the algorithm
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

	private void alertNegativeValue() {
		Alert alert = new Alert(AlertType.WARNING);
		arrayAlert.add(alert);
		alert.setTitle("Negative Value");
		alert.setHeaderText("Please enter a positive value !");
		alert.showAndWait();
	}

}
