/*
 * 
 */
package fr.lsmbo.msda.recover.gui.lists;

/**
 * Keep the information for the two list of spectrum and method to access either
 * the first or the second spectra or spectra in batch mode
 * 
 * @author BL
 *
 */
public class ListOfSpectra {

	private static Spectra firstSpectra = new Spectra();
	private static Spectra secondSpectra = new Spectra();
	private static Spectra batchSpectra = new Spectra();

	private static Spectra[] arraySpectra = { firstSpectra, secondSpectra, batchSpectra };

	/**
	 * 
	 * @return an array of spectra of the first(main) file.
	 */
	public static Spectra getFirstSpectra() {
		return arraySpectra[0];
	}

	/**
	 * 
	 * @return an array of spectra of the second file.
	 */
	public static Spectra getSecondSpectra() {
		return arraySpectra[1];
	}

	/**
	 * 
	 * @return an array of spectra of the file in batch.
	 */
	public static Spectra getBatchSpectra() {
		return arraySpectra[2];
	}

	/**
	 * 
	 * @param spectra
	 *            the spectra to add
	 */
	public static void addFirstSpectra(Spectra spectra) {
		arraySpectra[0] = spectra;
	}

	/**
	 * 
	 * @param spectra
	 *            the spectra to add
	 */
	public static void addSecondSpectra(Spectra spectra) {
		arraySpectra[1] = spectra;
	}

	/**
	 * 
	 * @param spectra
	 *            the spectra to add
	 */
	public static void addBatchSpectra(Spectra spectra) {
		arraySpectra[2] = spectra;
	}
}
