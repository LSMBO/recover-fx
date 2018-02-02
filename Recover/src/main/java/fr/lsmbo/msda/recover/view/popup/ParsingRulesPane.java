package fr.lsmbo.msda.recover.view.popup;

import javax.swing.table.TableColumn;

import fr.lsmbo.msda.recover.util.IconResource;
import fr.lsmbo.msda.recover.util.WindowSize;
import fr.lsmbo.msda.recover.util.IconResource.ICON;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ParsingRulesPane extends Accordion {

	private ComboBox<String> parsingRulesCBox = null;
	private TextField selectedPRuleTf = null;
	private TableView<String> parsingRuletable = null;

	public ParsingRulesPane() {
		Label pRulesLabel = new Label("Select a parsing rule");
		pRulesLabel.setPrefWidth(130);
		parsingRulesCBox = new ComboBox();
		parsingRulesCBox.getItems().addAll("Data-Analysis", "Mascot-Distiller", "Mascot-dll", "MsConvert", "Proline");
		parsingRulesCBox.getSelectionModel().selectFirst();
		parsingRulesCBox.setPrefWidth(160);
		HBox parsingRulesHbox = new HBox(15);
		parsingRulesHbox.getChildren().addAll(pRulesLabel, parsingRulesCBox);

		Label selectedPRuleLabel = new Label("Selected parsing rule");
		selectedPRuleLabel.setPrefWidth(130);
		selectedPRuleTf = new TextField();
		selectedPRuleTf.setPrefWidth(170);

		HBox selectedPRHbox = new HBox(15);
		selectedPRHbox.getChildren().addAll(selectedPRuleLabel, selectedPRuleTf);
		Button testButton = new Button("Test");
		testButton.setGraphic(new ImageView(IconResource.getImage(ICON.CHECK)));
		testButton.setPrefWidth(130);
		HBox insertDataHbox = new HBox(50);
		insertDataHbox.getChildren().addAll(parsingRulesHbox, selectedPRHbox, testButton);
		insertDataHbox.setAlignment(Pos.CENTER);
		// table
		parsingRuletable = new TableView<String>();
//		TableColumn retentionTimeCol = new TableColumn("Retention time");
//		TableColumn titleCol = new TableColumn("Title");
//		parsingRuletable.getColumns().addAll(retentionTimeCol, titleCol);
		parsingRuletable.setColumnResizePolicy(parsingRuletable.CONSTRAINED_RESIZE_POLICY);
		VBox filterPane1 = new VBox(50);
		filterPane1.getChildren().addAll(insertDataHbox);
		filterPane1.setPrefSize(WindowSize.popupPrefWidth, WindowSize.popupPrefHeight);
		TitledPane filetr1 = new TitledPane("Edit Parsing Rules", filterPane1);
		this.getPanes().addAll(filetr1);
		this.autosize();
		this.setExpandedPane(filetr1);
	}
}
