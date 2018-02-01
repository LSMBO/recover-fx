package fr.lsmbo.msda.recover.view.popup;

import fr.lsmbo.msda.recover.util.WindowSize;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ParsingRulesPane extends Accordion {

	private ComboBox<String> parsingRulesCBox = null;

	public ParsingRulesPane() {
		Label pRulesLabel = new Label("Select a parsing rule");
		pRulesLabel.setPrefWidth(140);
		parsingRulesCBox = new ComboBox();
		parsingRulesCBox.getItems().addAll("Data-Analysis", "Mascot-Distiller", "Mascot-dll", "MsConvert", "Proline");
		parsingRulesCBox.getSelectionModel().selectFirst();
		parsingRulesCBox.setPrefWidth(130);
		HBox parsingRulesHbox = new HBox(5);
		parsingRulesHbox.getChildren().addAll(pRulesLabel, parsingRulesCBox);

		Label selectedPRuleLabel = new Label("Selected parsing rule");
		selectedPRuleLabel.setPrefWidth(140);
		TextField selectedPRuleTf = new TextField();
		selectedPRuleTf.setPrefWidth(130);
		HBox selectedPRHbox = new HBox(5);
		selectedPRHbox.getChildren().addAll(selectedPRuleLabel, selectedPRuleTf);
		Button testButton = new Button("Test");
		testButton.setPrefWidth(100);
		HBox insertDataHbox = new HBox(20);
		insertDataHbox.getChildren().addAll(parsingRulesHbox, selectedPRHbox, testButton);

		VBox filterPane1 = new VBox(50);
		filterPane1.getChildren().addAll(insertDataHbox);
		filterPane1.setPrefSize(WindowSize.popupPrefWidth, WindowSize.popupPrefHeight);
		TitledPane filetr1 = new TitledPane("Edit Parsing Rules", filterPane1);
		this.getPanes().addAll(filetr1);
		this.autosize();
		this.setExpandedPane(filetr1);
	}
}
