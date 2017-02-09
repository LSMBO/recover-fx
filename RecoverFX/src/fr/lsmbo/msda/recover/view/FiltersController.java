package fr.lsmbo.msda.recover.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.RowFilter.ComparisonType;

import fr.lsmbo.msda.recover.filters.BasicFilter;
import fr.lsmbo.msda.recover.filters.ChargeStatesFilter;
import fr.lsmbo.msda.recover.filters.FragmentIntensityFilter;
import fr.lsmbo.msda.recover.filters.HighIntensityThreasholdFilter;
import fr.lsmbo.msda.recover.filters.IdentifiedSpectraFilter;
import fr.lsmbo.msda.recover.filters.LowIntensityThreasholdFilter;
import fr.lsmbo.msda.recover.filters.PrecursorIntensityFilter;
import fr.lsmbo.msda.recover.filters.WrongChargeFilter;
import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.ComparisonTypes;
import fr.lsmbo.msda.recover.model.ComputationTypes;
import fr.lsmbo.msda.recover.model.Spectrum;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class FiltersController {
	
	private Stage dialogStage;
	
	private HighIntensityThreasholdFilter filterHIT = new HighIntensityThreasholdFilter() ;
	private LowIntensityThreasholdFilter filterLIT = new LowIntensityThreasholdFilter() ;
	private ChargeStatesFilter filterCS = new ChargeStatesFilter();
	private PrecursorIntensityFilter filterPI = new PrecursorIntensityFilter();
	private FragmentIntensityFilter filterFI = new FragmentIntensityFilter();
	private WrongChargeFilter filterWC = new WrongChargeFilter();
	private IdentifiedSpectraFilter filterIS = new IdentifiedSpectraFilter();
	
	private ObservableList<String> modeBaselineList = FXCollections.observableArrayList("Average of all peaks","Median of all peaks");
	private ObservableList<String> comparatorIntensity = FXCollections.observableArrayList("=","#",">",">=","<","<=");
	
	private ObservableList<Control> controlHIT = FXCollections.observableArrayList();
	private ObservableList<Control> controlLIT = FXCollections.observableArrayList();
	private ObservableList<Control> controlCS = FXCollections.observableArrayList();
	private ObservableList<Control> controlPI = FXCollections.observableArrayList();
	private ObservableList<Control> controlFI = FXCollections.observableArrayList();
	private ObservableList<Control> controlIS = FXCollections.observableArrayList();
	
	private ObservableList<Alert> arrayAlert = FXCollections.observableArrayList();
	
	private Integer nb = Spectra.getSpectraAsObservable().size(); 
	
	
	
	/*******************************************
	 Control for High Intensity Threshold Filter
	 *******************************************/
	@FXML
	private CheckBox checkBoxHighIntensityThresholdFilter;
	@FXML
	private TextField mostIntensePeaksToConsider ;
	@FXML
	private TextField percentageOfTopLine;
	@FXML
	private TextField maxNbPeaks;
	
	/*******************************************
	 Control for low Intensity Threshold Filter
	 *******************************************/
	@FXML
	private CheckBox checkBoxLowIntensityThresholdFilter;
	@FXML
	private ChoiceBox<String> modeBaseline;
	@FXML
	private TextField emergence;
	@FXML
	private TextField minUPN;
	@FXML 
	private TextField maxUPN;
	
	/********************************
	 Control for Charge State Filter
	 ********************************/
	@FXML
	private CheckBox checkBoxChargeStatesFilter;
	@FXML
	private CheckBox charge1;
	@FXML
	private CheckBox charge2;
	@FXML
	private CheckBox charge3;
	@FXML
	private CheckBox charge4;
	@FXML
	private CheckBox charge5;
	@FXML
	private CheckBox chargeOver5;
	@FXML
	private CheckBox chargeUnknown;
	
	/********************************
	 Control for Precursor Intensity Filter
	 ********************************/
	@FXML
	private CheckBox checkBoxPrecursorIntensityFilter;
	@FXML
	private ChoiceBox<String> comparatorPrecursorIntensity ;
	@FXML
	private TextField precursorIntensity;
	
	/********************************
	 Control for Fragment Intensity Filter
	 ********************************/
	@FXML
	private CheckBox checkBoxFragmentIntensityFilter;
	@FXML
	private ChoiceBox<String> comparatorFragmentIntensity ;
	@FXML
	private TextField fragmentIntensity;
	
	/********************************
	 Control for Wrong Charge Filter
	 ********************************/
	@FXML
	private CheckBox checkBoxWrongChargeFilter;
	
	/********************************
	 Control for Identified Spectra Filter
	 ********************************/
	@FXML
	private CheckBox checkBoxIdentifiedSpectraFilter;
	@FXML
	private TextArea titles;
	
	//Buttons
	@FXML 
	private Button btnApply;
	@FXML 
	private Button btnCancel;
	
	@FXML
	private void initialize(){
		
		//add different control in observable list for different filters
		controlHIT.addAll(mostIntensePeaksToConsider, percentageOfTopLine, maxNbPeaks);
		controlLIT.addAll(modeBaseline, emergence, minUPN, maxUPN);
		controlCS.addAll(charge1, charge2, charge3, charge4, charge5, chargeOver5, chargeUnknown);
		controlPI.addAll(comparatorPrecursorIntensity, precursorIntensity);
		controlFI.addAll(comparatorFragmentIntensity, fragmentIntensity);
		controlIS.addAll(titles);
		
		//disable all control
		
		setDisableControl(controlHIT,"disable");
		setDisableControl(controlLIT,"disable");
		setDisableControl(controlCS,"disable");
		setDisableControl(controlPI,"disable");
		setDisableControl(controlFI,"disable");
		setDisableControl(controlIS,"disable");
		
		//Initialize values and set the first value for choice boxes
		modeBaseline.setItems(modeBaselineList);
		modeBaseline.getSelectionModel().selectFirst();
		
		comparatorPrecursorIntensity.setItems(comparatorIntensity);
		comparatorPrecursorIntensity.getSelectionModel().selectFirst();
		
		comparatorFragmentIntensity.setItems(comparatorIntensity);
		comparatorFragmentIntensity.getSelectionModel().selectFirst();
		}
	
	@FXML
	private void handleClickBtnApply(){
		//filterHIT
		if (checkBoxHighIntensityThresholdFilter.isSelected()){
			applyFilterHITToSpectrum();
		}
		//filterLIT
		if (checkBoxLowIntensityThresholdFilter.isSelected()){
			applyFilterLITToSpectrum();
		}
		//filterCS
		if (checkBoxChargeStatesFilter.isSelected()){
			applyFilterCSToSpectrum();
		}
		//filterPI
		if (checkBoxPrecursorIntensityFilter.isSelected()){
			applyFilterPIToSpectrum();
		}
		//filterFI
		if (checkBoxFragmentIntensityFilter.isSelected()){
			applyFilterFIToSpectrum();
		}
		//filterWC
		if (checkBoxWrongChargeFilter.isSelected()){
			applyFilterWCToSpectrum();
		}
		//filterIS
		if (checkBoxIdentifiedSpectraFilter.isSelected()){
			applyFilterISToSpectrum();
		}
		//initialize variable nbRecover after apply filter
		Spectra.checkRecoveredSpectra();
		
		if (arrayAlert.size() == 0)
			dialogStage.close();
		else
			arrayAlert.clear();
}
	
/********************************
High Intensity Threshold Filter
********************************/
	@FXML
	private void checkHighIntensityThresholdFilter(){
		if (checkBoxHighIntensityThresholdFilter.isSelected()){
			setDisableControl(controlHIT, "enable");
		}
		else
			initialize();
	}
	@FXML
	private void applyFilterHITToSpectrum(){
		try{
		Integer mostIntensePeaksToConsiderInt = changeTextFieldToInteger(mostIntensePeaksToConsider);
		Integer maxNbPeaksInt = changeTextFieldToInteger(maxNbPeaks);
		Float percentageOfTopLineFloat = changeTextFieldToFloat(percentageOfTopLine);
		Boolean mostIntensePeaksToConsiderExceedNbFragment = false;
		
		//Verify if percentage is correct (between 0 and 1)
		if(percentageOfTopLineFloat < 0 || percentageOfTopLineFloat > 1){
			Alert alert = new Alert(AlertType.WARNING);
			arrayAlert.add(alert);
			alert.setTitle("Bad percentage");
			alert.setHeaderText("Please enter a value (float) between 0 and 1 for percentage");
			alert.showAndWait();
		}
		
		filterHIT.setParameters(mostIntensePeaksToConsiderInt, percentageOfTopLineFloat, maxNbPeaksInt);
		
		for (int i=0; i < nb; i++){
			Spectrum spectrum = Spectra.getSpectraAsObservable().get(i);
			if (isUsable(mostIntensePeaksToConsiderInt, spectrum.getNbFragments())){
				if (RecoverController.filterUsed){
					spectrum.setIsRecover(recoverIfFilterUsed(spectrum, filterHIT));
				}
				else
					spectrum.setIsRecover(filterHIT.isValid(spectrum));
			}
			else{
				spectrum.setIsRecover(false);
				mostIntensePeaksToConsiderExceedNbFragment = true;
			}	
		}
		
		if (mostIntensePeaksToConsiderExceedNbFragment){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText("Some spectrum have number of fragment lower than the variable most intense peaks to consider."
					+ "\n In this case, the value recover will be false !!!");
			alert.showAndWait();
		}
			
		RecoverController.filterUsed = true;
		} catch (NumberFormatException e){
			Alert alert = new Alert(AlertType.WARNING);
			arrayAlert.add(alert);
			alert.setTitle("No numeric parameters have been chosen");
			alert.setHeaderText("Please enter numeric values for Most intense peaks/Number peaks and float for percentage");
			alert.showAndWait();
		}
	}
	
	//method for high intensity threshold, to verify if the number of peaks we have to consider is lower than the number of fragment
	public Boolean isUsable(Integer nbPeaks, Integer nbFragment){
		if (nbPeaks >= nbFragment){
			return false;
		}
		return true;
	}
/********************************
Low Intensity Threshold Filter
********************************/
	@FXML
	private void checkLowIntensityThresholdFilter(){
		if (checkBoxLowIntensityThresholdFilter.isSelected()){
			setDisableControl(controlLIT,"enable");
		}
		else
			initialize();
	}
	
	@FXML
	private void applyFilterLITToSpectrum(){
		try{
			Integer emergenceInt = changeTextFieldToInteger(emergence);
			Integer minUPNInt = changeTextFieldToInteger(minUPN);
			Integer maxUPNInt = changeTextFieldToInteger(maxUPN);
			
			filterLIT.setParameters(emergenceInt,minUPNInt, maxUPNInt, setChoiceMode(modeBaseline));
			for (int i=0; i < nb; i++){
				Spectrum spectrum = Spectra.getSpectraAsObservable().get(i);
				if (RecoverController.filterUsed){
					spectrum.setIsRecover(recoverIfFilterUsed(spectrum, filterLIT));
				}
				else
					spectrum.setIsRecover(filterLIT.isValid(spectrum));
			}
			RecoverController.filterUsed = true;
		} catch (NumberFormatException e){
			Alert alert = new Alert(AlertType.WARNING);
			arrayAlert.add(alert);
			alert.setTitle("No numeric parameters have been chosen");
			alert.setHeaderText("Please enter a numeric value for Emergence/Min useful peaks/Max useful peaks");
			alert.showAndWait();
		}
	}
	
	public ComputationTypes setChoiceMode(ChoiceBox<String> string){
		if (string.getValue().contains("Average")){
			return ComputationTypes.AVERAGE;
		}
		return ComputationTypes.MEDIAN;
	}
/********************************
Charge State Filter
********************************/
	@FXML
	private void checkChargeStatesFilter(){
		if (checkBoxChargeStatesFilter.isSelected()){
			setDisableControl(controlCS,"enable");
		}
		else
			initialize();
	}
	
	@FXML
	private void applyFilterCSToSpectrum(){
		Boolean keepCharge1 = setBooleanToCharge(charge1);
		Boolean keepCharge2 = setBooleanToCharge(charge2);
		Boolean keepCharge3 = setBooleanToCharge(charge3);
		Boolean keepCharge4 = setBooleanToCharge(charge4);
		Boolean keepCharge5 = setBooleanToCharge(charge5);
		Boolean keepChargeOver5 = setBooleanToCharge(chargeOver5);
		Boolean keepChargeUnknown = setBooleanToCharge(chargeUnknown);
		filterCS.setParameters(keepCharge1,keepCharge2,keepCharge3,keepCharge4,keepCharge5,keepChargeOver5,keepChargeUnknown);
		for (int i=0; i < nb; i++){
			Spectrum spectrum = Spectra.getSpectraAsObservable().get(i);
			if (RecoverController.filterUsed){
				spectrum.setIsRecover(recoverIfFilterUsed(spectrum, filterCS));
			}
			else
				spectrum.setIsRecover(filterCS.isValid(spectrum));
		}
		RecoverController.filterUsed = true;
	}
	
	//method for charge state filter to give a boolean value if the charge is checked or not
	public Boolean setBooleanToCharge(CheckBox charge){
		if (charge.isSelected())
			return false;
		return true;		
	}
	
/********************************
Precursor Intensity Filter
********************************/	
	@FXML
	private void checkPrecursorIntensityFilter(){
		if (checkBoxPrecursorIntensityFilter.isSelected()){
			setDisableControl(controlPI, "enable");
		}
		else
			initialize();
	}
	
	@FXML
	private void applyFilterPIToSpectrum(){
		try{
			Integer intensityInt = changeTextFieldToInteger(precursorIntensity);
			filterPI.setParameters(intensityInt, setChoiceComparator(comparatorPrecursorIntensity));
			for (int i=0; i < nb; i++){
				Spectrum spectrum = Spectra.getSpectraAsObservable().get(i);
				if (RecoverController.filterUsed){
					spectrum.setIsRecover(recoverIfFilterUsed(spectrum, filterPI));
				}
				else
					spectrum.setIsRecover(filterPI.isValid(spectrum));
			}
		} catch (NumberFormatException e){
			Alert alert = new Alert(AlertType.WARNING);
			arrayAlert.add(alert);
			alert.setTitle("No numeric parameters have been chosen");
			alert.setHeaderText("Please enter a numeric value for intensity");
			alert.showAndWait();
		}
		RecoverController.filterUsed = true;
	}

/********************************
Fragment Intensity Filter
********************************/
	@FXML
	private void checkFragmentIntensityFilter(){
		if (checkBoxFragmentIntensityFilter.isSelected()){
			setDisableControl(controlFI, "enable");
		}
		else
			initialize();
	}
	
	@FXML
	private void applyFilterFIToSpectrum(){
		try{
			Integer intensityInt = changeTextFieldToInteger(fragmentIntensity);
			filterFI.setParameters(intensityInt, setChoiceComparator(comparatorFragmentIntensity));
			for (int i=0; i < nb; i++){
				Spectrum spectrum = Spectra.getSpectraAsObservable().get(i);
				if (RecoverController.filterUsed){
					spectrum.setIsRecover(recoverIfFilterUsed(spectrum, filterFI));
				}
				else
					spectrum.setIsRecover(filterFI.isValid(spectrum));
			}
		} catch (NumberFormatException e){
			Alert alert = new Alert(AlertType.WARNING);
			arrayAlert.add(alert);
			alert.setTitle("No numeric parameters have been chosen");
			alert.setHeaderText("Please enter a numeric value for intensity");
			alert.showAndWait();
		}
		RecoverController.filterUsed = true;
	}
/********************************
Wrong Charge Filter
********************************/
	@FXML
	private void applyFilterWCToSpectrum(){
		for (int i=0; i < nb; i++){
			Spectrum spectrum = Spectra.getSpectraAsObservable().get(i);
			if (RecoverController.filterUsed){
				spectrum.setIsRecover(recoverIfFilterUsed(spectrum, filterWC));
			}
			else
				spectrum.setIsRecover(filterWC.isValid(spectrum));
		}
		RecoverController.filterUsed = true;
	}
	
/********************************
Identified Spectra Filter
********************************/
	@FXML
	private void checkIdentifiedSpectraFilter(){
		if (checkBoxIdentifiedSpectraFilter.isSelected()){
			setDisableControl(controlIS, "enable");
		}
		else
			initialize();
	}
	
	
	@FXML
	private void applyFilterISToSpectrum(){
			String [] arrayTitles = titles.getText().split("\n");
			filterIS.setParameters(arrayTitles);
			for (int i=0; i < nb; i++){
				Spectrum spectrum = Spectra.getSpectraAsObservable().get(i);
				if (RecoverController.filterUsed){
					spectrum.setIsIdentified(recoverIfFilterUsed(spectrum, filterIS));
				}
				else{
					spectrum.setIsIdentified(filterIS.isValid(spectrum));
				}
			}
		RecoverController.filterUsed = true;
	}
	
	@FXML
	private void handleClickBtnCancel(){
		// TODO close window
		dialogStage.close();
	}
	
	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
	
	public Integer changeTextFieldToInteger(TextField string){
		String valueOfTextField = string.getText() ;
		int integerOfTextField = Integer.parseInt(valueOfTextField);
		return integerOfTextField ;
	}
	
	public Float changeTextFieldToFloat(TextField string){
		String valueOfTextField = string.getText() ;
		float floatOfTextField = Float.parseFloat(valueOfTextField);
		return floatOfTextField;
	}
	

	
	public ComparisonTypes setChoiceComparator(ChoiceBox<String> string){
		if (string.getValue().equalsIgnoreCase("=")){
			return ComparisonTypes.EQUALS_TO;}
		
		if (string.getValue().equalsIgnoreCase("#")){
			return ComparisonTypes.NOT_EQUALS_TO;}
		if (string.getValue().equalsIgnoreCase(">")){
			return ComparisonTypes.GREATER_THAN;}
		if (string.getValue().equalsIgnoreCase(">=")){
			return ComparisonTypes.GREATER_OR_EQUAL;}
		if (string.getValue().equalsIgnoreCase("<")){
			return ComparisonTypes.LOWER_THAN;}
		if (string.getValue().equalsIgnoreCase("<=")){
			return ComparisonTypes.LOWER_OR_EQUAL;}
		return null;
	
	}

	public Boolean recoverIfFilterUsed(Spectrum spectrum, BasicFilter filter){
		if (spectrum.getIsRecover() == false)
			return false;
		else
			if (filter.isValid(spectrum) == true)
				return true;
			else
				return false;			
	}
	
	public void setDisableControl(ObservableList<Control> control, String string){
		Iterator<Control> itrControl = control.iterator();
		while(itrControl.hasNext()){
			if (string == "disable")
				itrControl.next().setDisable(true);
			else if (string =="enable")
				itrControl.next().setDisable(false);
		}
	}
}


