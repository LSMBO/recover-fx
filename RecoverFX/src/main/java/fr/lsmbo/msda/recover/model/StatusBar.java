package fr.lsmbo.msda.recover.model;

import fr.lsmbo.msda.recover.Session;
import fr.lsmbo.msda.recover.lists.ListOfSpectra;

public class StatusBar {
	
	public static String refreshStatusBar(){
		String text = "";
		try {
			int nbFirstSpectra = ListOfSpectra.getFirstSpectra().getNbSpectra() ;
			int nbSecondSpectra = ListOfSpectra.getSecondSpectra().getNbSpectra() ;
			int nbRecover = ListOfSpectra.getFirstSpectra().getNbRecover();
			int nbIdentified = ListOfSpectra.getFirstSpectra().getNbIdentified() ;
			String nameFirstPeaklist = Session.CURRENT_FILE.getName();
			String nameSecondPeaklist = Session.SECOND_FILE.getName();
			
			text = " First Peaklist: " + nameFirstPeaklist+ " : " + nbFirstSpectra + " spectra - " +
					" Recovered : " + nbRecover + " - Identified : " + nbIdentified 
					+ " // Second Peaklist: " + nameSecondPeaklist+ " : " + nbSecondSpectra + " spectra " 
					;
		} catch(Exception e) {
//			e.printStackTrace();
		}
		return text;
	} 
}