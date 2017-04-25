package fr.lsmbo.msda.recover.view;

import fr.lsmbo.msda.recover.lists.IdentifiedSpectra;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class IdentifiedSpectraForBatchController {

	private Stage identifiedSpectraForBatchStage;
	private static IdentifiedSpectra identifiedSpectra = new IdentifiedSpectra();
	
	@FXML
	private TextArea titles;
	@FXML
	private Button apply;
	
	public void setDialogStage(Stage _identifiedSpectraStage) {
		this.identifiedSpectraForBatchStage = _identifiedSpectraStage;
	}

	@FXML
	private void applyIdentificationOfSpectrum(){
		String[] arrayTitles = titles.getText().split("\n");
		identifiedSpectra.setArrayTitles(arrayTitles);
		
		identifiedSpectraForBatchStage.close();
	}
	
	public static IdentifiedSpectra getIdentifiedSpectra(){
		return identifiedSpectra ;
	}
}
