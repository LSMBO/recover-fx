package fr.lsmbo.msda.recover.view;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import fr.lsmbo.msda.recover.Views;
import fr.lsmbo.msda.recover.filters.BasicFilter;
import fr.lsmbo.msda.recover.filters.ChargeStatesFilter;
import fr.lsmbo.msda.recover.filters.FragmentIntensityFilter;
import fr.lsmbo.msda.recover.filters.HighIntensityThreasholdFilter;
import fr.lsmbo.msda.recover.filters.IdentifiedSpectraFilter;
import fr.lsmbo.msda.recover.filters.IonReporterFilter;
import fr.lsmbo.msda.recover.filters.LowIntensityThreasholdFilter;
import fr.lsmbo.msda.recover.filters.PrecursorIntensityFilter;
import fr.lsmbo.msda.recover.filters.WrongChargeFilter;
import fr.lsmbo.msda.recover.lists.Filters;
import fr.lsmbo.msda.recover.lists.IonReporters;
import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.ComparisonTypes;
import fr.lsmbo.msda.recover.model.ComputationTypes;
import fr.lsmbo.msda.recover.model.IonReporter;
import fr.lsmbo.msda.recover.view.IdentifiedSpectraFilterController;

import fr.lsmbo.msda.recover.model.Spectrum;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class FiltersController {
	
	private Stage dialogStage;
	private Boolean redoFromTheBeginning = true;
	private int nbFiltersSelected = 0;
	private ArrayList<Integer> arrayFilterSelected = new ArrayList<Integer>();
	
	//instance filters
	private HighIntensityThreasholdFilter filterHIT = new HighIntensityThreasholdFilter() ;
	private LowIntensityThreasholdFilter filterLIT = new LowIntensityThreasholdFilter() ;
	private ChargeStatesFilter filterCS = new ChargeStatesFilter();
	private PrecursorIntensityFilter filterPI = new PrecursorIntensityFilter();
	private FragmentIntensityFilter filterFI = new FragmentIntensityFilter();
	private WrongChargeFilter filterWC = new WrongChargeFilter();
	private IdentifiedSpectraFilter filterIS = new IdentifiedSpectraFilter();
	private IonReporterFilter filterIR = new IonReporterFilter();
	
	
	private ObservableList<String> modeBaselineList = FXCollections.observableArrayList("Average of all peaks","Median of all peaks");
	private ObservableList<String> comparatorIntensity = FXCollections.observableArrayList("=","#",">",">=","<","<=");
	
	//Instance control of one filter as a list.
	private ObservableList<Control> controlHIT = FXCollections.observableArrayList();
	private ObservableList<Control> controlLIT = FXCollections.observableArrayList();
	private ObservableList<Control> controlCS = FXCollections.observableArrayList();
	private ObservableList<Control> controlPI = FXCollections.observableArrayList();
	private ObservableList<Control> controlFI = FXCollections.observableArrayList();
	private ObservableList<Control> controlIS = FXCollections.observableArrayList();
	private ObservableList<Control> controlIR = FXCollections.observableArrayList();
	
	private ObservableList<Alert> arrayAlert = FXCollections.observableArrayList();
	
	//States of filters used. each index correspond to a filter, if a filter is used : 1 if not 0.
	
	private Integer nb = Spectra.getSpectraAsObservable().size(); 
	
	/*******************************************
	 Control for High Intensity Threshold Filter
	 *******************************************/
	@FXML
	private CheckBox checkBoxHighIntensityThresholdFilter;
	@FXML
	private TextField mostIntensePeaksToConsider;
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
	@FXML
	private Button buttonIdentifiedSpectra;
	
	/********************************
	 Control for Ion ReporterFilter
	 ********************************/
	@FXML 
	private CheckBox checkBoxIonReporterFilter;
	@FXML
	private TableView<IonReporter> tableIonReporter;
	@FXML
	private TableColumn<IonReporter, Float> colMoz;
	@FXML
	private TableColumn<IonReporter, Float> colTolerance;
	@FXML
	private TableColumn<IonReporter, String> colName;
	@FXML
	private Button buttonIonReporter;
	@FXML
	private TextField mozIonReporter;
	@FXML
	private TextField toleranceIonReporter;
	@FXML
	private TextField nameIonReporter;
	@FXML
	private Button buttonInsertIonReporter;
	@FXML
	private Button buttonResetIonReporter;
	
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
			controlIS.addAll(buttonIdentifiedSpectra, titles);
			controlIR.addAll(tableIonReporter, buttonIonReporter, mozIonReporter, toleranceIonReporter, nameIonReporter, buttonInsertIonReporter, buttonResetIonReporter);
			
			//disable all control
			
			setDisableControl(controlHIT,"disable");
			setDisableControl(controlLIT,"disable");
			setDisableControl(controlCS,"disable");
			setDisableControl(controlPI,"disable");
			setDisableControl(controlFI,"disable");
			setDisableControl(controlIS,"disable");
			setDisableControl(controlIR,"disable");
			
			//Initialize values and set the first value for choice boxes
			modeBaseline.setItems(modeBaselineList);
			modeBaseline.getSelectionModel().selectFirst();
			
			comparatorPrecursorIntensity.setItems(comparatorIntensity);
			comparatorPrecursorIntensity.getSelectionModel().selectFirst();
			
			comparatorFragmentIntensity.setItems(comparatorIntensity);
			comparatorFragmentIntensity.getSelectionModel().selectFirst();
			
			//Initialize table view for Ion Reporter
			tableIonReporter.setItems(IonReporters.getIonReporters());
			colMoz.setCellValueFactory(new PropertyValueFactory<IonReporter, Float>("moz"));
			colTolerance.setCellValueFactory(new PropertyValueFactory<IonReporter, Float>("tolerance"));
			colName.setCellValueFactory(new PropertyValueFactory<IonReporter, String>("name"));
			
			if(Filters.nbFilterUsed() !=0)
				arrayFilterSelected.clear();
				//initialize previous values of the filterHIT
				if ((Filters.getFilters().get("HIT"))!=null){
					filterHIT = (HighIntensityThreasholdFilter) Filters.getFilters().get("HIT");
					checkBoxHighIntensityThresholdFilter.setSelected(true);
					checkHighIntensityThresholdFilter();
					mostIntensePeaksToConsider.setText(Integer.toString(filterHIT.getNbMostIntensePeaksToConsider()));
					percentageOfTopLine.setText(Float.toString(filterHIT.getPercentageOfTopLine()));
					maxNbPeaks.setText(Integer.toString(filterHIT.getMaxNbPeaks()));
				}
			
				//initialize previous values of the filterLIT
				if ((Filters.getFilters().get("LIT"))!=null){
					filterLIT = (LowIntensityThreasholdFilter) Filters.getFilters().get("LIT");
					checkBoxLowIntensityThresholdFilter.setSelected(true);
					checkLowIntensityThresholdFilter();
					emergence.setText(Integer.toString(filterLIT.getEmergence()));
					minUPN.setText(Integer.toString(filterLIT.getMinUPN()));
					maxUPN.setText(Integer.toString(filterLIT.getMaxUPN()));
					if (filterLIT.getMode() == ComputationTypes.MEDIAN)
						modeBaseline.getSelectionModel().selectLast();
				}
				
				//initialize previous values of the filterCS
				if  ((Filters.getFilters().get("CS"))!=null){
					filterCS = (ChargeStatesFilter) Filters.getFilters().get("CS");
					checkBoxChargeStatesFilter.setSelected(true);
					checkChargeStatesFilter();
					charge1.setSelected(!filterCS.getKeepCharge1());
					charge2.setSelected(!filterCS.getKeepCharge2());
					charge3.setSelected(!filterCS.getKeepCharge3());
					charge4.setSelected(!filterCS.getKeepCharge4());
					charge5.setSelected(!filterCS.getKeepCharge5());
					chargeOver5.setSelected(!filterCS.getKeepChargeAbove5());
					chargeUnknown.setSelected(!filterCS.getKeepUnknownCharge());
				}
				
				//initialize previous values of the filterPI
				if  ((Filters.getFilters().get("PI"))!=null){
					filterPI = (PrecursorIntensityFilter) Filters.getFilters().get("PI");
					checkBoxPrecursorIntensityFilter.setSelected(true);
					checkPrecursorIntensityFilter();
					precursorIntensity.setText(Integer.toString(filterPI.getIntensityPrecursor()));
					comparatorPrecursorIntensity.getSelectionModel().select(setIntegerToPrecursorComparator());
				}
				
				//initialize previous values of the filterFI
				if  ((Filters.getFilters().get("FI"))!=null){
					filterFI = (FragmentIntensityFilter) Filters.getFilters().get("FI");
					checkBoxFragmentIntensityFilter.setSelected(true);
					checkFragmentIntensityFilter();
					fragmentIntensity.setText(Integer.toString(filterFI.getIntensityFragment()));
					comparatorFragmentIntensity.getSelectionModel().select(setIntegerToFragmentComparator());
				}
				
				
				if  ((Filters.getFilters().get("WC"))!=null){
					checkBoxWrongChargeFilter.setSelected(true);
				}
				
				//initialize previous values of the filterIS
				if  ((Filters.getFilters().get("IS"))!=null){
					filterIS = (IdentifiedSpectraFilter) Filters.getFilters().get("IS");
					checkBoxIdentifiedSpectraFilter.setSelected(true);
					checkIdentifiedSpectraFilter();
					String allTitle = "";
					for (String st : filterIS.getArrayTitles()){
						allTitle += st + "\n";
					}
					titles.setText(allTitle);
				}
				
				if  ((Filters.getFilters().get("IR"))!=null){
					filterIR = (IonReporterFilter) Filters.getFilters().get("IR");
					checkBoxIonReporterFilter.setSelected(true);
					checkIonReporterFilter();
				}
	}
	
	
	
	@FXML
	private void handleClickBtnApply(){
		nbAndArrayFiltersSelected();
		
		
		if (redoFromTheBeginning){
			for (Spectrum sp : Spectra.getSpectraAsObservable()){
				sp.setIsRecover(false);
				sp.setUpn(-1);
			}
			Filters.resetHashMap();
			filterHIT.setIsUsed(false); filterLIT.setIsUsed(false); filterCS.setIsUsed(false); filterPI.setIsUsed(false);
			filterFI.setIsUsed(false); filterWC.setIsUsed(false); filterIS.setIsUsed(false); filterIR.setIsUsed(false);		
		}

		//filterHIT
		if (checkBoxHighIntensityThresholdFilter.isSelected()){
			applyFilterHITToSpectrum();
			System.out.println(filterHIT.getFullDescription());
		}
		//filterLIT
		if (checkBoxLowIntensityThresholdFilter.isSelected()){
			applyFilterLITToSpectrum();
			System.out.println(filterLIT.getFullDescription());
		}
		//filterCS
		if (checkBoxChargeStatesFilter.isSelected()){
			applyFilterCSToSpectrum();
			System.out.println(filterCS.getFullDescription());
		}
		//filterPI
		if (checkBoxPrecursorIntensityFilter.isSelected()){
			applyFilterPIToSpectrum();
			System.out.println(filterPI.getFullDescription());
		}
		//filterFI
		if (checkBoxFragmentIntensityFilter.isSelected()){
			applyFilterFIToSpectrum();
			System.out.println(filterFI.getFullDescription());
		}
		//filterWC
		if (checkBoxWrongChargeFilter.isSelected()){
			applyFilterWCToSpectrum();
		}
		//filterIS
		if (checkBoxIdentifiedSpectraFilter.isSelected()){
			applyFilterISToSpectrum();
			System.out.println(filterIS.getFullDescription());
		}
		//filterIR
		if (checkBoxIonReporterFilter.isSelected()){
			applyFilterIRToSpectrum();
			System.out.println(filterIR.getFullDescription());
		}
		//initialize variable nbRecover after apply filter
		Spectra.checkRecoveredSpectra();
		
		//close the windows only if there isn't alert.
		if (arrayAlert.size() == 0){
			dialogStage.close();
		}
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
			setDisableControl(controlHIT, "disable");
	}
	
	@FXML
	private void applyFilterHITToSpectrum(){
		try{
			//Convert value of the parameters in the text area.
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
		
		//Scan all the spectrum and apply the filter in different case
		for (int i=0; i < nb; i++){
			Spectrum spectrum = Spectra.getSpectraAsObservable().get(i);
			
			//Check if we can used this filter ( nb of most intense peak need to be lower than nb fragment
			if (isUsable(mostIntensePeaksToConsiderInt, spectrum.getNbFragments())){	
				applyFilterInDifferentCase(spectrum, filterHIT);
				
			}
			 
			else{
				mostIntensePeaksToConsiderExceedNbFragment = true;
			}	
		}
		
		//Display an alert to prevent the user.
		if (mostIntensePeaksToConsiderExceedNbFragment){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText("Some spectrum have number of fragment lower than the variable most intense peaks to consider."
					+ "\n In this case, the value recover will be false !!!");
			alert.showAndWait();
		}
			
		}catch (NumberFormatException e){
			Alert alert = new Alert(AlertType.WARNING);
			arrayAlert.add(alert);
			alert.setTitle("No numeric parameters have been chosen");
			alert.setHeaderText("Please enter numeric values for Most intense peaks/Number peaks and float for percentage");
			alert.showAndWait();
		}
		
		//set the filter as used
		filterHIT.setIsUsed(true);
		
		//save the filter into a hashmap
		Filters.add("HIT", filterHIT);
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
			setDisableControl(controlLIT, "disable");
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
				applyFilterInDifferentCase(spectrum, filterLIT);
				
			}
			
		}catch (NumberFormatException e){
			Alert alert = new Alert(AlertType.WARNING);
			arrayAlert.add(alert);
			alert.setTitle("No numeric parameters have been chosen");
			alert.setHeaderText("Please enter a numeric value for Emergence/Min useful peaks/Max useful peaks");
			alert.showAndWait();
		}
		
		filterLIT.setIsUsed(true);
		
		//save the filter into a hashmap
		Filters.add("LIT", filterLIT);
	}
	
	//link the value of the string in the choiceBox with the corresponding computationType
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
			setDisableControl(controlCS, "disable");
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
		Boolean[] associatedSpectrum = new Boolean[nb];
		filterCS.setParameters(keepCharge1,keepCharge2,keepCharge3,keepCharge4,keepCharge5,keepChargeOver5,keepChargeUnknown);
		
		for (int i=0; i < nb; i++){
			Spectrum spectrum = Spectra.getSpectraAsObservable().get(i);
			applyFilterInDifferentCase(spectrum, filterCS);
			
			//value of spectrum for this filter save in an array
			associatedSpectrum[i] = spectrum.getIsRecover();
			
		}
		filterCS.setAssociatedSpectrum(associatedSpectrum);
		filterCS.setIsUsed(true);
		
		//save the filter into a hashmap
		Filters.add("CS", filterCS);
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
			setDisableControl(controlPI, "disable");
	}
	
	@FXML
	private void applyFilterPIToSpectrum(){
		try{
			Integer intensityInt = changeTextFieldToInteger(precursorIntensity);
			
			filterPI.setParameters(intensityInt, setChoiceComparator(comparatorPrecursorIntensity));
			
			for (int i=0; i < nb; i++){
				Spectrum spectrum = Spectra.getSpectraAsObservable().get(i);
				applyFilterInDifferentCase(spectrum, filterPI);
			}
			
		} catch (NumberFormatException e){
			Alert alert = new Alert(AlertType.WARNING);
			arrayAlert.add(alert);
			alert.setTitle("No numeric parameters have been chosen");
			alert.setHeaderText("Please enter a numeric value for intensity");
			alert.showAndWait();
		}
		
		filterPI.setIsUsed(true);
		//save the filter into a hashmap
		Filters.add("PI", filterPI);
	}
	
	//link the comparisonTypes with the corresponding index in the collection
	private Integer setIntegerToPrecursorComparator(){
		Integer intComparator = 0;
		if (filterPI.getComparator() == ComparisonTypes.NOT_EQUALS_TO)
			intComparator = 1;
		if (filterPI.getComparator() == ComparisonTypes.GREATER_THAN)
			intComparator = 2;
		if (filterPI.getComparator() == ComparisonTypes.GREATER_OR_EQUAL)
			intComparator = 3;
		if (filterPI.getComparator() == ComparisonTypes.LOWER_THAN)
			intComparator = 4;
		if (filterPI.getComparator() == ComparisonTypes.LOWER_OR_EQUAL)
			intComparator = 5;
		return intComparator;
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
			setDisableControl(controlFI, "disable");
	}
	
	@FXML
	private void applyFilterFIToSpectrum(){
		try{
			Integer intensityInt = changeTextFieldToInteger(fragmentIntensity);
			
			filterFI.setParameters(intensityInt, setChoiceComparator(comparatorFragmentIntensity));
			
			for (int i=0; i < nb; i++){
				Spectrum spectrum = Spectra.getSpectraAsObservable().get(i);
				applyFilterInDifferentCase(spectrum, filterFI);
			}
			
		} catch (NumberFormatException e){
			Alert alert = new Alert(AlertType.WARNING);
			arrayAlert.add(alert);
			alert.setTitle("No numeric parameters have been chosen");
			alert.setHeaderText("Please enter a numeric value for intensity");
			alert.showAndWait();
		}
		
		filterFI.setIsUsed(true);
		//save the filter into a hashmap
		Filters.add("FI", filterFI);
	}
	
	//link the comparisonTypes with the corresponding index in the collection
	private Integer setIntegerToFragmentComparator(){
		Integer intComparator = 0;
		if (filterFI.getComparator() == ComparisonTypes.NOT_EQUALS_TO)
			intComparator = 1;
		if (filterFI.getComparator() == ComparisonTypes.GREATER_THAN)
			intComparator = 2;
		if (filterFI.getComparator() == ComparisonTypes.GREATER_OR_EQUAL)
			intComparator = 3;
		if (filterFI.getComparator() == ComparisonTypes.LOWER_THAN)
			intComparator = 4;
		if (filterFI.getComparator() == ComparisonTypes.LOWER_OR_EQUAL)
			intComparator = 5;
		return intComparator;
	}
/********************************
Wrong Charge Filter
********************************/
	@FXML
	private void applyFilterWCToSpectrum(){
		for (int i=0; i < nb; i++){
			Spectrum spectrum = Spectra.getSpectraAsObservable().get(i);
			applyFilterInDifferentCase(spectrum, filterWC);
		}
		
		filterWC.setIsUsed(true);
		//save the filter into a hashmap
		Filters.add("WC", filterWC);
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
			setDisableControl(controlIS, "disable");
	}
	
	public void SetTitles(TextArea titles){
		this.titles = titles ;
	}
	
	@FXML
	private void applyFilterISToSpectrum(){
			String [] arrayTitles = titles.getText().split("\n");
			
			filterIS.setParameters(arrayTitles);
			
			try{
				
				for (String t : arrayTitles){
					filterIS.setIdentified(t);
				}
				
			}catch(NullPointerException e){
				Alert alert = new Alert(AlertType.WARNING);
				arrayAlert.add(alert);
				alert.setTitle("Title(s) not corresponding");
				alert.setHeaderText("Any of your titles correspond to spectrum. Please check your titles.\n"
						+ "Don't forget one title per line.\n"
						+ "EX:\n"
						+ "Cmpd X, +MSn(xxx.xxx), xx.xx min\n"
						+ "Cmpd Y, +MSn(xxx.xxx), xx.xx min");
				alert.showAndWait();
			}
			
			filterIS.setIsUsed(true);
			//save the filter into a hashmap
			Filters.add("IS", filterIS);
			
//			filterIS.setParameters(arrayTitles);
//			for (int i=0; i < nb; i++){
//				Spectrum spectrum = Spectra.getSpectraAsObservable().get(i);
//				if (RecoverController.filterUsed){
//					spectrum.setIsIdentified(recoverIfFilterUsed(spectrum, filterIS));
//				}
//				else{
//					spectrum.setIsIdentified(filterIS.isValid(spectrum));
//				}
//			}
	}
	@FXML
	private void openISFilter(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Views.FILTER_IDENTIFIED_SPECTRA);
			AnchorPane page = (AnchorPane) loader.load();
			Stage stageIS = new Stage();
			stageIS.setScene(new Scene(page));
			stageIS.initOwner(dialogStage);
			stageIS.initModality(Modality.WINDOW_MODAL);
			stageIS.setTitle("Identified Spectra Filter");
			IdentifiedSpectraFilterController controller = loader.getController();
			controller.setStageIS(stageIS);
			stageIS.show();
		} catch(IOException e) {
			e.printStackTrace();
			
		}
	}
	
/********************************
Ion Reporter Filter
********************************/
	@FXML
	private void checkIonReporterFilter(){
		
		if (checkBoxIonReporterFilter.isSelected()){
			setDisableControl(controlIR, "enable");
		}
		
		else
			setDisableControl(controlIR, "disable");
	}
	
	@FXML
	private void insertIonToTableView(){
		
		Float mozIonReporterFloat = changeTextFieldToFloat(mozIonReporter);
		Float toleranceIonReporterFloat = changeTextFieldToFloat(toleranceIonReporter);
		IonReporters.add(new IonReporter(nameIonReporter.getText(),mozIonReporterFloat,toleranceIonReporterFloat));
		tableIonReporter.refresh();
	}
	
	@FXML
	private void resetIonToTableView(){
		IonReporters.getIonReporters().clear();
	}
	
	@FXML
	private void applyFilterIRToSpectrum(){
		Integer nbIon = IonReporters.getIonReporters().size();
		
		//scan all the ion reporter
		for (int i=0; i <nbIon; i++){
			IonReporter ionReporter = IonReporters.getIonReporters().get(i);
			
			//Initialise parameter for an ion(i)
			filterIR.setParameters(ionReporter.getName(),ionReporter.getMoz(),ionReporter.getTolerance());
			
			//Scan all the spectrum
			for (int j=0; j < nb; j++){
				Spectrum spectrum = Spectra.getSpectraAsObservable().get(j);
				
				//First case: A filter was used and it's not this filter
				if (Filters.nbFilterUsed()>=1 && !filterIR.getIsUsed()){
					
					//condition when more than one ion is used to filter
					if (i>=1){
						spectrum.setIsRecover(recoverIfSeveralIons(spectrum, filterIR));
					}
					
					else
						spectrum.setIsRecover(recoverIfFilterUsed(spectrum, filterIR));
					
				}
				
				//Second case: Any filter was used or the filter used is this one (reset in some way)
				else if ((Filters.nbFilterUsed()==1 && filterIR.getIsUsed()) || Filters.nbFilterUsed()==0){
					
					if (i>=1){
						spectrum.setIsRecover(recoverIfSeveralIons(spectrum, filterIR));
					}
					
					else
						spectrum.setIsRecover(filterIR.isValid(spectrum));
					
				}

			}
		}
		
		filterIR.setIsUsed(true);
		//save the filter into a hashmap
		Filters.add("IR", filterIR);
	}
//	@FXML
//	private void applyFilterIRToSpectrum(){
//		Float mozIonReporterFloat = changeTextFieldToFloat(mozIonReporter);
//		Float toleranceIonReporterFloat = changeTextFieldToFloat(toleranceIonReporter);
//		filterIR.setParameters(nameIonReporter.getText(), mozIonReporterFloat, toleranceIonReporterFloat);
//		for (int i=0; i < nb; i++){
//			Spectrum spectrum = Spectra.getSpectraAsObservable().get(i);
//			if (RecoverController.filterUsed){
//				spectrum.setIsRecover(recoverIfFilterUsed(spectrum, filterIR));
//			}
//			else{
//				spectrum.setIsRecover(filterIR.isValid(spectrum));
//			}
//		}
//	RecoverController.filterUsed = true;
//	}
	
	@FXML
	private void openIRFilter(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Views.FILTER_ION_REPORTER);
			AnchorPane page = (AnchorPane) loader.load();
			Stage stageIR = new Stage();
			stageIR.setScene(new Scene(page));
			stageIR.initOwner(dialogStage);
			stageIR.initModality(Modality.WINDOW_MODAL);
			stageIR.setTitle("Identified Spectra Filter");
			IonReporterFilterController controller = loader.getController();
			controller.setStageIR(stageIR);
			stageIR.showAndWait();
		} catch(IOException e) {
			e.printStackTrace();
			
		}
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
	
	//Used when a filter is already applied. If the spectrum is not recover => not recover
	//if the spectrum is recover,check if it is recover with the new filter :
	//yes => recover
	//no => not recover
	public Boolean recoverIfFilterUsed(Spectrum spectrum, BasicFilter filter){
		if (spectrum.getIsRecover() == false)
			return false;
		else
			if (filter.isValid(spectrum) == true)
				return true;
			else
				return false;			
	}
	
	public Boolean recoverIfSeveralIons(Spectrum spectrum, BasicFilter filter){
		if (spectrum.getIsRecover())
			return true;
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
	
	
//	public Integer nbFiltersUsed(){
//		Integer nbFiltersUsed = 0;
//		for (Boolean b : filtersUsed){
//			if(b == true){
//				nbFiltersUsed++;
//			}
//		}
//		return nbFiltersUsed;
//	}
	
	public void applyFilterInDifferentCase(Spectrum spectrum, BasicFilter filter){
		//First case: A filter was used and it's not this filter
		if (Filters.nbFilterUsed()>=1 && !filter.getIsUsed()){
			spectrum.setIsRecover(recoverIfFilterUsed(spectrum, filter));
		}
		
		//Second case: Any filter was used or the filter used is this one (reset in some way)
		else if ((Filters.nbFilterUsed()==1 && filter.getIsUsed()) || Filters.nbFilterUsed()==0){
			spectrum.setIsRecover(filter.isValid(spectrum));
		}
	}
	
	//recover number of filter used and put it in an array.
	public void nbAndArrayFiltersSelected(){
		//filterHIT
		if (checkBoxHighIntensityThresholdFilter.isSelected()){
			nbFiltersSelected++;
			arrayFilterSelected.add(filterHIT.getId());
		}
		//filterLIT
		if (checkBoxLowIntensityThresholdFilter.isSelected()){
			nbFiltersSelected++;
			arrayFilterSelected.add(filterLIT.getId());
		}
		//filterCS
		if (checkBoxChargeStatesFilter.isSelected()){
			nbFiltersSelected++;
			arrayFilterSelected.add(filterCS.getId());
		}
		//filterPI
		if (checkBoxPrecursorIntensityFilter.isSelected()){
			nbFiltersSelected++;
			arrayFilterSelected.add(filterPI.getId());
		}
		//filterFI
		if (checkBoxFragmentIntensityFilter.isSelected()){
			nbFiltersSelected++;
			arrayFilterSelected.add(filterFI.getId());
		}
		//filterWC
		if (checkBoxWrongChargeFilter.isSelected()){
			nbFiltersSelected++;
			arrayFilterSelected.add(filterWC.getId());
		}
		//filterIR
		if (checkBoxIonReporterFilter.isSelected()){
			nbFiltersSelected++;
			arrayFilterSelected.add(filterIR.getId());
		}
	}
	
	public void filterToApply(int id){
		
	}
	
}


