package fr.lsmbo.msda.recover.lists;

import fr.lsmbo.msda.recover.model.IonReporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Initialize an observable list of ion reporter. Method to add ion reporter inside the list and to get the list.
 * @author BL
 *
 */
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
