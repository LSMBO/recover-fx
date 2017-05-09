package fr.lsmbo.msda.recover.view;

import java.util.ArrayList;

import fr.lsmbo.msda.recover.lists.IdentifiedSpectra;
import fr.lsmbo.msda.recover.model.ConvertorArrayToArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class IdentifiedSpectraForBatchController {

	private Stage identifiedSpectraForBatchStage;
	private IdentifiedSpectra identifiedSpectra;

	@FXML
	private TextArea titles;
	@FXML
	private Button apply;

	public void setDialogStage(Stage _identifiedSpectraStage) {
		this.identifiedSpectraForBatchStage = _identifiedSpectraStage;
	}

	@FXML
	private void applyIdentificationOfSpectrum() {
		String[] arrayTitles = titles.getText().split("\n");
		ArrayList<String> arrayListTitles = ConvertorArrayToArrayList.arrayToArrayListString(arrayTitles);
		identifiedSpectra.setArrayTitles(arrayListTitles);

		identifiedSpectraForBatchStage.close();
	}

	public IdentifiedSpectra getIdentifiedSpectra() {
		return identifiedSpectra;
	}

	public void setidentifiedSpectra(IdentifiedSpectra _identifiedSpectra){
		this.identifiedSpectra = _identifiedSpectra;
	}
	
	
}
