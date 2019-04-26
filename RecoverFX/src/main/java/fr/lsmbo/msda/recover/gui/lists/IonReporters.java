/*
 * 
 */
package fr.lsmbo.msda.recover.gui.lists;

import fr.lsmbo.msda.recover.gui.model.IonReporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Initialize an observable list of ion reporter. Method to add ion reporter
 * inside the list and to get the list.
 * 
 * @author BL
 *
 */
public class IonReporters {

	private static ObservableList<IonReporter> ionReporters = initializeListIon();

	/***
	 * 
	 * @return initilaize the ion reporters list
	 */
	private static ObservableList<IonReporter> initializeListIon() {
		ObservableList<IonReporter> list = FXCollections.observableArrayList();
		return list;
	}

	/**
	 * 
	 * @return the ion reportes as an observable list.
	 */
	public static ObservableList<IonReporter> getIonReporters() {
		return ionReporters;
	}

	/**
	 * Add an ion reporter to the ion reporter list.
	 * 
	 * @param ir
	 *            the ion reporter to add
	 */
	public static void addIonReporter(IonReporter ir) {
		ionReporters.add(ir);
	}

}
