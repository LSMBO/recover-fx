/*
 * 
 */
package fr.lsmbo.msda.recover.gui.view.dialog;

import fr.lsmbo.msda.recover.gui.IconResource;
import fr.lsmbo.msda.recover.gui.IconResource.ICON;
import fr.lsmbo.msda.recover.gui.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.gui.lists.ParsingRules;
import fr.lsmbo.msda.recover.gui.model.ParsingRule;
import fr.lsmbo.msda.recover.gui.model.Spectrum;
import fr.lsmbo.msda.recover.gui.util.JavaFxUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Creates and displays parsing rules dialog to edit parsing rules.
 * 
 * @author aromdhani
 *
 */
public class ParsingRulesDialog extends Dialog<ParsingRule> {
	private ObservableList<Spectrum> titles = FXCollections.observableArrayList();
	private ParsingRule selectedParsingRule = null;
	private TableView<Spectrum> table;

	public ParsingRulesDialog() {

		// Create notifications pane
		VBox parsingRuleWarningPane = new VBox(2);
		Label parsingRuleWarningLabel = new Label("Parsing rule must not be empty. Select a parsing rule.");
		parsingRuleWarningLabel.setGraphic(new ImageView(IconResource.getImage(ICON.WARNING)));
		parsingRuleWarningLabel.setStyle(JavaFxUtils.RED_ITALIC);
		parsingRuleWarningPane.getChildren().addAll(parsingRuleWarningLabel);

		// Create Parsing rules dialog components
		Label parsingRuleLabel = new Label("Select a parsing rule: ");
		Label selectedParsingRuleLabel = new Label("Selected parsing rule: ");
		TextField selectedParsingRuleTF = new TextField();
		selectedParsingRuleTF.setTooltip(new Tooltip("Enter a parsing rule"));
		// Set parsing rules values
		ComboBox<String> selectedParsingCmBox = new ComboBox<String>();
		for (ParsingRule parsingRule : ParsingRules.get()) {
			selectedParsingCmBox.getItems().add(parsingRule.getName());
		}
		// Set table view values
		titles.clear();
		table = new TableView<Spectrum>(titles);
		TableColumn<Spectrum, String> title = new TableColumn<Spectrum, String>("Title");
		title.setCellValueFactory(new PropertyValueFactory<Spectrum, String>("title"));
		TableColumn<Spectrum, Float> newRT = new TableColumn<Spectrum, Float>("Retention time");
		newRT.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("retentionTime"));
		table.getColumns().addAll(title, newRT);
		table.autosize();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		selectedParsingCmBox.setOnAction((e) -> {
			selectedParsingRule = ParsingRules.get(selectedParsingCmBox.getValue());
			selectedParsingRuleTF.setText(selectedParsingRule.getRegex());
			tryRegex();
		});
		Integer nb = ListOfSpectra.getFirstSpectra().getSpectraAsObservable().size();
		if (nb > 5)
			nb = 5;
		for (int i = 0; i < nb; i++) {
			titles.add(ListOfSpectra.getFirstSpectra().getSpectraAsObservable().get(i));
		}
		// Set default value
		// default values
		ParsingRule pr = ParsingRules.getCurrentParsingRule();
		if (pr != null) {
			selectedParsingCmBox.setValue(pr.getName());
			selectedParsingRuleTF.setText(pr.getRegex());
		}
		// Show notification
		parsingRuleWarningLabel.visibleProperty().bind(selectedParsingRuleTF.textProperty().isEmpty()
				.or(selectedParsingCmBox.getSelectionModel().selectedItemProperty().isNull()));

		// Layout
		GridPane parsingRulesPane = new GridPane();
		parsingRulesPane.setAlignment(Pos.CENTER);
		parsingRulesPane.setPadding(new Insets(10));
		parsingRulesPane.setHgap(25);
		parsingRulesPane.setVgap(25);
		parsingRulesPane.add(parsingRuleWarningPane, 0, 0, 5, 1);
		parsingRulesPane.addRow(1, parsingRuleLabel, selectedParsingCmBox, selectedParsingRuleLabel,
				selectedParsingRuleTF);
		parsingRulesPane.add(table, 0, 2, 5, 3);

		/********************
		 * Main dialog pane *
		 ********************/

		// Create and display the main dialog pane
		DialogPane dialogPane = new DialogPane();
		dialogPane.setContent(parsingRulesPane);
		dialogPane.setHeaderText("Edit parsing rule");
		dialogPane.setGraphic(new ImageView(IconResource.getImage(ICON.EDIT)));
		Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new ImageView(IconResource.getImage(ICON.EDIT)).getImage());
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialogPane.setPrefSize(800, 500);
		Button buttonOk = (Button) dialogPane.lookupButton(ButtonType.OK);
		this.setTitle("Parsing Rule");
		this.setDialogPane(dialogPane);

		// Enable Ok button when input fields are valid.
		buttonOk.disableProperty().bind(parsingRuleWarningLabel.visibleProperty());
		// On apply button
		this.setResultConverter(buttonType -> {
			if ((buttonType == ButtonType.OK) && (selectedParsingRule != null)) {
				return this.selectedParsingRule;
			} else {
				return null;
			}
		});
	}

	/**
	 * Try Regex; set the selected parsing rule from GUI as to retrieve
	 * retention time from title.
	 */
	private void tryRegex() {
		for (Spectrum s : titles) {
			s.setRetentionTimeFromTitle(selectedParsingRule.getRegex());
		}
		table.refresh();
	}

}