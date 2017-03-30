package fr.lsmbo.msda.recover.view;

import java.io.File;

import fr.lsmbo.msda.recover.io.IdentifiedSpectraFromExcel;
import fr.lsmbo.msda.recover.lists.IdentifiedSpectra;
import fr.lsmbo.msda.recover.lists.ListOfSpectra;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class IdentifiedSpectraController {

	private Stage identifiedSpectraStage;
	private static IdentifiedSpectra identifiedSpectra = new IdentifiedSpectra();
	
	private static Boolean excelFileImported = false;

	@FXML
	private TextArea titles;
	@FXML
	private Button apply;
	@FXML
	private Button importTitlesFromExcel;
	@FXML
	private Button btnDeleteImport;
	@FXML
	private Label infoExcelFile;
	
	@FXML
	private void initialize(){
		if(excelFileImported){
			btnDeleteImport.setVisible(true);
			infoExcelFile.setVisible(true);
			infoExcelFile.setText(IdentifiedSpectraFromExcel.getTitle());
		}
		
	}

	public void setDialogStage(Stage _identifiedSpectraStage) {
		this.identifiedSpectraStage = _identifiedSpectraStage;
	}

	@FXML
	private void applyIdentificationOfSpectrum() {

		if(!excelFileImported){
		String[] arrayTitles = titles.getText().split("\n");
		identifiedSpectra.setArrayTitles(arrayTitles);
		}
		
		for (String t : identifiedSpectra.getArrayTitles()) {
			identifiedSpectra.setIdentified(t);
		}

		identifiedSpectraStage.close();
	}

	@FXML
	private void importExcelFile(){
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Import your excel file");
		filechooser.getExtensionFilters().addAll(new ExtensionFilter("File XLS","*.xlsx"));
		File excelFile = filechooser.showOpenDialog(this.identifiedSpectraStage);
		IdentifiedSpectraFromExcel.load(excelFile);
		excelFileImported = true;
		btnDeleteImport.setVisible(true);
		infoExcelFile.setVisible(true);
		infoExcelFile.setText(IdentifiedSpectraFromExcel.getTitle());
	}
	
	public static IdentifiedSpectra getIdentifiedSpectra(){
		return identifiedSpectra;
	}
	
	@FXML
	private void handleClickDeleteImport(){
		btnDeleteImport.setVisible(false);
		infoExcelFile.setText(null);
		infoExcelFile.setVisible(false);
		identifiedSpectra.setArrayTitles(null);
		excelFileImported = false;
		ListOfSpectra.getFirstSpectra().resetIdentified();
	}
}
