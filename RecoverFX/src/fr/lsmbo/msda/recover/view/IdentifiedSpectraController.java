package fr.lsmbo.msda.recover.view;

import java.io.File;

import fr.lsmbo.msda.recover.io.IdentifiedSpectraFromExcel;
import fr.lsmbo.msda.recover.lists.IdentifiedSpectra;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class IdentifiedSpectraController {

	private Stage identifiedSpectraStage;
	private static IdentifiedSpectra identifiedSpectra = new IdentifiedSpectra();
	
	private Boolean excelFileImported = false;

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

		if(!excelFileImported){
		String[] arrayTitles = titles.getText().split("\n");
		identifiedSpectra.setArrayTitles(arrayTitles);
		}
		
		for (String t : identifiedSpectra.getArrayTitles()) {
			identifiedSpectra.setIdentified(t);
		}

		identifiedSpectraStage.close();
	}

	public void importExcelFile(){
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Import your excel file");
		filechooser.getExtensionFilters().addAll(new ExtensionFilter("File XLS","*.xlsx"));
		File excelFile = filechooser.showOpenDialog(this.identifiedSpectraStage);
		IdentifiedSpectraFromExcel.load(excelFile);
		excelFileImported = true;
	}
	
	public static IdentifiedSpectra getIdentifiedSpectra(){
		return identifiedSpectra;
	}
}
