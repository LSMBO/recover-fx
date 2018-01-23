package fr.lsmbo.msda.recover.view.popup;

import fr.lsmbo.msda.recover.util.WindowSize;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class AccordionPanel extends Accordion {

	public AccordionPanel() {
		VBox filterPane1 = new VBox();
		filterPane1.setPrefSize(WindowSize.popupPrefWidth, WindowSize.popupPrefHeight);
		VBox filterPanel2 = new VBox();
		filterPanel2.setPrefSize(WindowSize.popupPrefWidth, WindowSize.popupPrefHeight);
		VBox filterPanel3 = new VBox();
		filterPanel3.setPrefSize(WindowSize.popupPrefWidth, WindowSize.popupPrefHeight);
		TitledPane filetr1 = new TitledPane("Filter 1", filterPane1);
		this.getPanes().addAll(filetr1, new TitledPane("Filter 2", filterPanel2),
				new TitledPane("Filter 3", filterPanel3));
		this.autosize();
		this.setExpandedPane(filetr1);
	    
		
	}
}
