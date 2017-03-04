package fr.lsmbo.msda.recover.model;

import javafx.scene.control.ChoiceBox;

public enum ComputationTypes {
	MEDIAN,
	AVERAGE;

public static ComputationTypes setChoiceMode(ChoiceBox<String> string){
	if (string.getValue().contains("Average")){
		return ComputationTypes.AVERAGE;
	}
	return ComputationTypes.MEDIAN;
}
}