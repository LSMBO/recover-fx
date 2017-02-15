package fr.lsmbo.msda.recover.lists;

import fr.lsmbo.msda.recover.model.IonReporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class IonReporters {

	private static ObservableList<IonReporter> ionReporters = initializeListIon();;
	
	private static ObservableList<IonReporter> initializeListIon() {
		ObservableList<IonReporter> list = FXCollections.observableArrayList();
		return list;
	}
	
	public static ObservableList<IonReporter> getIonReporters() {
		return ionReporters;
	}
	
	public static void add(IonReporter ir) {
		ionReporters.add(ir);
	}
	
}
