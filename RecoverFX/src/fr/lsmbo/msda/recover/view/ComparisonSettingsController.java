package fr.lsmbo.msda.recover.view;

import fr.lsmbo.msda.recover.model.ConstantComparisonSpectra;
import fr.lsmbo.msda.recover.model.TextFieldConvertor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
	
	
	@FXML
	private void initialize(){
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
	private void handleClickBtnApply(){
		try{
		Float deltaMozValue = TextFieldConvertor.changeTextFieldToFloat(deltaMoz);
		Integer deltaRTValue = TextFieldConvertor.changeTextFieldToInteger(deltaRT);
		Integer nbPeaksValue = TextFieldConvertor.changeTextFieldToInteger(nbPeaks);
		Integer nbPeaksMinValue = TextFieldConvertor.changeTextFieldToInteger(nbPeaksMin);
		Integer thetaMinValue = TextFieldConvertor.changeTextFieldToInteger(thetaMin);
		
		ConstantComparisonSpectra.setDeltamoz(deltaMozValue);
		ConstantComparisonSpectra.setDeltaRT(deltaRTValue);
		ConstantComparisonSpectra.setNbPeaks(nbPeaksValue);
		ConstantComparisonSpectra.setNbPeaksMin(nbPeaksMinValue);
		ConstantComparisonSpectra.setThetaMin(thetaMinValue);
		} catch(NumberFormatException e){
			e.printStackTrace();
		} 
		dialogComparisonSettingsStage.close();
	}
	
	@FXML
	private void handleClickBtnValuesByDefault(){
		ConstantComparisonSpectra.initialValue();
		initialize();
	}
	
	@FXML
	private void handleClickBtnClose(){
		dialogComparisonSettingsStage.close();
	}
	
}
