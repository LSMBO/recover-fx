package fr.lsmbo.msda.recover.model;

import fr.lsmbo.msda.recover.Session;
import fr.lsmbo.msda.recover.lists.ListOfSpectra;

public class StatusBar {
	
	private static String title = "This is the status bar :";
	
	public static String getInformation(){
		int nbFirstSpectra = ListOfSpectra.getFirstSpectra().getNbSpectra() ;
		int nbSecondSpectra = ListOfSpectra.getSecondSpectra().getNbSpectra() ;
		int nbRecover = ListOfSpectra.getFirstSpectra().getNbRecover();
		int nbIdentified = ListOfSpectra.getFirstSpectra().getNbIdentified() ;
		String nameFirstPeaklist = Session.CURRENT_FILE.getName();
		String nameSecondPeaklist = Session.SECOND_FILE.getName();
		
		return title + " First Peaklist (Left side) " + nameFirstPeaklist+ " : " + nbFirstSpectra + " spectra /" 
				+ " Second Peaklist (Right side) " + nameSecondPeaklist+ " : " + nbSecondSpectra + " spectra /" +
				" Recovered : " + nbRecover + " / Identified : " + nbIdentified ;
	} 
}
