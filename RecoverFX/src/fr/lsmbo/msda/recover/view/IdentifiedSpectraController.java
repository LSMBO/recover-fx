package fr.lsmbo.msda.recover.view;

import fr.lsmbo.msda.recover.lists.IdentifiedSpectra;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class IdentifiedSpectraController {

	private Stage identifiedSpectraStage;
	private IdentifiedSpectra identifiedSpectra = new IdentifiedSpectra();

	@FXML
	private TextArea titles;
	@FXML
	private Button apply;
	@FXML
	private Button importTitlesFromExcel;

	public void setDialogStage(Stage _identifiedSpectraStage) {
		this.identifiedSpectraStage = _identifiedSpectraStage;
	}

	public void applyIdentificationOfSpectrum() {

		String[] arrayTitles = titles.getText().split("\n");
		identifiedSpectra.setArrayTitles(arrayTitles);

		for (String t : identifiedSpectra.getArrayTitles()) {
			identifiedSpectra.setIdentified(t);
		}

		identifiedSpectraStage.close();
	}

}
