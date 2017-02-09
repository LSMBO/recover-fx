package fr.lsmbo.msda.recover.lists;

import java.util.ArrayList;

import fr.lsmbo.msda.recover.model.IonReporter;

public class IonReporters {

	private static ArrayList<IonReporter> ionReporters;
	
	private static void initialize() {
		if(ionReporters == null) {
			// lazy loading
			ionReporters = new ArrayList<IonReporter>();
		}
	}
	
	public static ArrayList<IonReporter> getIonReporters() {
		initialize();
		return ionReporters;
	}
	
	public static void add(IonReporter ir) {
		initialize();
		ionReporters.add(ir);
	}
	
}
