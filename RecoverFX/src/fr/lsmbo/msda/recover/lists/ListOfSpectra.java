package fr.lsmbo.msda.recover.lists;

import fr.lsmbo.msda.recover.gui.Recover;

/**
 * Keep the information for the two list of spectrum and method to access either
 * the first or the second spectra
 * 
 * @author BL
 *
 */
public class ListOfSpectra {

	private static Spectra firstSpectra = new Spectra();
	private static Spectra secondSpectra = new Spectra();
	private static Spectra batchSpectra = new Spectra();

	private static Spectra[] arraySpectra = { firstSpectra, secondSpectra, batchSpectra };

	public static Spectra getFirstSpectra() {
		return arraySpectra[0];
	}

	public static Spectra getSecondSpectra() {
		return arraySpectra[1];
	}
	
	public static Spectra getBatchSpectra() {
		return arraySpectra[2];
	}

	public static void addFirstSpectra(Spectra spectra) {
		arraySpectra[0] = spectra;
	}

	public static void addSecondSpectra(Spectra spectra) {
		arraySpectra[1] = spectra;
	}
	
	public static void addBatchSpectra(Spectra spectra){
		arraySpectra[2] = spectra ;
	}

	public static Spectra choiceSpectra() {
		if (Recover.useSecondPeaklist)
			return ListOfSpectra.getSecondSpectra();
		return ListOfSpectra.getFirstSpectra();
	}
}
