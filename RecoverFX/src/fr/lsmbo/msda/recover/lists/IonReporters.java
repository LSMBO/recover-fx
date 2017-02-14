package fr.lsmbo.msda.recover.lists;

import fr.lsmbo.msda.recover.model.IonReporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class IonReporters {

	private static ObservableList<IonReporter> ionReporters;
	
	private static void initialize() {
		System.out.println(ionReporters.isEmpty());
		if(ionReporters.isEmpty()) {
			// lazy loading
			ionReporters = FXCollections.observableArrayList();
		}
	}
	
	public static ObservableList<IonReporter> getIonReporters() {
		initialize();
		return ionReporters;
	}
	
	public static void add(IonReporter ir) {
		initialize();
		ionReporters.add(ir);
	}
	
}
