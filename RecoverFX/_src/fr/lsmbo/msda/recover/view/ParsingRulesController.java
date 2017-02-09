package fr.lsmbo.msda.recover.view;

import fr.lsmbo.msda.recover.lists.ParsingRules;
import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.ParsingRule;
import fr.lsmbo.msda.recover.model.Spectrum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ParsingRulesController {
	
	private ObservableList<Spectrum> titles = FXCollections.observableArrayList();
	private ParsingRule selectedParsingRule = null;
	private Stage dialogStage;

	@FXML
	private ComboBox<String> cmbParsingRules;
	@FXML
	private TextField txtRegex;
	@FXML
	private Button btnTryIt;
	@FXML
	private Button btnApply;
	@FXML
	private Button btnCancel;
	@FXML
	private TableView<Spectrum> table;
	@FXML
	private TableColumn<Spectrum, String> colTitle;
	@FXML
	private TableColumn<Spectrum, Float> colNewRT;
	
	@FXML
	private void initialize() {
		
		// fill combobox
		for(ParsingRule pr: ParsingRules.get()) {
			cmbParsingRules.getItems().add(pr.getName());
		}
		
		// fill table
		titles.clear();
		Integer nb = Spectra.getSpectraAsObservable().size();
		if(nb > 5)
			nb = 5;
		for(int i = 0; i < nb; i++) {
			titles.add(Spectra.getSpectraAsObservable().get(i));
		}
		table.setItems(titles);
		colTitle.setCellValueFactory(new PropertyValueFactory<Spectrum, String>("title"));
		colNewRT.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("retentionTime"));
		colTitle.prefWidthProperty().bind(table.widthProperty().subtract(2).multiply(0.75));
		colNewRT.prefWidthProperty().bind(table.widthProperty().subtract(2).multiply(0.25));
		
		// default values
		ParsingRule pr = ParsingRules.getCurrentParsingRule();
		if(pr != null) {
			cmbParsingRules.setValue(pr.getName());
			txtRegex.setText(pr.getRegex());
		}
	}
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	
	@FXML
	private void handleChangeParsingRule() {
		selectedParsingRule = ParsingRules.get(cmbParsingRules.getValue());
		txtRegex.setText(selectedParsingRule.getRegex());
		tryRegex();
	}
	
	@FXML
	private void handleClickTryIt() {
		selectedParsingRule = new ParsingRule(null, txtRegex.getText(), null, -1);
		tryRegex();
	}
	
	@FXML
	private void handleClickBtnApply() {
		if(selectedParsingRule != null) {
			ParsingRules.setNewCurrentParsingRule(selectedParsingRule);
			Spectra.updateRetentionTimeFromTitle();
			dialogStage.close();
		} else {
			// FIXME the window is closing anyway
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No parsing rule chosen");
			alert.setHeaderText("No parsing rule have been chosen, therefore nothing will be done");
			alert.showAndWait();
		}
	}

	@FXML
	private void handleClickBtnCancel() {
		// TODO close window
		dialogStage.close();
	}
	
	private void tryRegex() {
		for(Spectrum s: titles) {
			s.setRetentionTimeFromTitle(selectedParsingRule.getRegex());
		}
		table.refresh();
	}
	
}
