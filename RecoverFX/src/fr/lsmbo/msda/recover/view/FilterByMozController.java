package fr.lsmbo.msda.recover.view;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import fr.lsmbo.msda.recover.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.Spectrum;
import fr.lsmbo.msda.recover.model.TextFieldConvertor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FilterByMozController {

	private Stage dialogStage;
	private ObservableList<String> comparatorIntensity = FXCollections.observableArrayList("=","#",">","<");
	private ObservableList<Spectrum> newSpectra = FXCollections.observableArrayList();
	
	@FXML
	private ChoiceBox<String> choiceBoxMoz;
	@FXML
	private TextField textFieldMoz;
	@FXML
	private Button btnOk;
	@FXML
	private TextField toleranceMoz;
	
	@FXML
	private void initialize(){
		choiceBoxMoz.setItems(comparatorIntensity);
	}
	
	private void applyFilterByMoz(){
		ObservableList<Spectrum> spectra = ListOfSpectra.getFirstSpectra().getSpectraAsObservable();
		Float wantedMoz = TextFieldConvertor.changeTextFieldToFloat(textFieldMoz);
		Float tolerance = TextFieldConvertor.changeTextFieldToFloat(toleranceMoz);
		
		Float minMoz = wantedMoz - tolerance;
		Float maxMoz = wantedMoz + tolerance;
		
		FilteredList<Spectrum> filteredSpectrum = new FilteredList<>(spectra, p -> true);
		filteredSpectrum.setPredicate(fs -> {
			if(choiceBoxMoz.getValue()=="=")
				if(fs.getMz()>=minMoz && fs.getMz()<=maxMoz)
					return true;
			
			if(choiceBoxMoz.getValue()=="#")
				if(fs.getMz()<minMoz || fs.getMz()>maxMoz)
					return true;
			
			if(choiceBoxMoz.getValue()=="<")
				if(fs.getMz()<maxMoz)
					return true;
			
			if(choiceBoxMoz.getValue()==">")
				if(fs.getMz()>minMoz)
					return true;
				
			return false;
		});
		newSpectra = filteredSpectrum;
	}
	
	@FXML
	private void handleClickOK(){
		applyFilterByMoz();
		dialogStage.close();
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	
	
}
