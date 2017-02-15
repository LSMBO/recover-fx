package fr.lsmbo.msda.recover.view;



import fr.lsmbo.msda.recover.lists.IonReporters;
import fr.lsmbo.msda.recover.model.IonReporter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class IonReporterFilterController {
	
	private Stage stageIR;
	
	@FXML
	private TableView<IonReporter> tableIonReporter;
	@FXML
	private TableColumn<IonReporter, Float> colMoz;
	@FXML
	private TableColumn<IonReporter, Float> colTolerance;
	@FXML
	private TableColumn<IonReporter, String> colName;
	
	@FXML
	private void initialize(){
		tableIonReporter.setItems(IonReporters.getIonReporters());
		colMoz.setCellValueFactory(new PropertyValueFactory<IonReporter, Float>("moz"));
		colTolerance.setCellValueFactory(new PropertyValueFactory<IonReporter, Float>("tolerance"));
		colName.setCellValueFactory(new PropertyValueFactory<IonReporter, String>("name"));
	}

	public void setStageIR(Stage stageIR) {
		this.stageIR = stageIR;
	}
	

}
