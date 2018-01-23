package fr.lsmbo.msda.recover.view.popup;

import fr.lsmbo.msda.recover.util.WindowSize;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class ParsingRulesPane extends Accordion {

	public ParsingRulesPane() {
		VBox filterPane1 = new VBox();
		filterPane1.setPrefSize(WindowSize.popupPrefWidth, WindowSize.popupPrefHeight);
		TitledPane filetr1 = new TitledPane("Edit Parsing Rules", filterPane1);
		this.getPanes().addAll(filetr1);
		this.autosize();
		this.setExpandedPane(filetr1);

	}
}
