package fr.lsmbo.msda.recover.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import fr.lsmbo.msda.recover.view.FiltersController;

public class IdentifiedSpectraFilterController {
	private Stage dialogStage;
	
	@FXML
	private TextArea titles;
	@FXML
	private Button applyISFilter;
	@FXML
	private FiltersController FC;
	
	@FXML
	private void returnTitleToFilterController(){
		FC.SetTitles(titles);
	}
	
	public void setStageIS(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
