package fr.lsmbo.msda.recover.gui.lists;

import java.util.ArrayList;

import fr.lsmbo.msda.recover.gui.io.ExporIntBatch;
import fr.lsmbo.msda.recover.gui.model.Spectrum;

/**
 * Set and get the identified spectra via their titles
 * 
 * @author Aromdhani
 * @author BL
 *
 */

public class IdentifiedSpectra {
	private ArrayList<String> arrayTitles = new ArrayList<>();
	private Spectra spectra;

	/**
	 * Add an array of titles
	 * 
	 * @param allTitles
	 *            the titles to add
	 */
	public void addAllTitles(ArrayList<String> allTitles) {
		arrayTitles.addAll(allTitles);
	}

	/**
	 * Add a title
	 * 
	 * @param title
	 *            the title to add
	 */
	public void addTitle(String title) {
		arrayTitles.add(title);
	}

	/**
	 * Return titles
	 * 
	 * @return an array of titles
	 */
	public ArrayList<String> getArrayTitles() {
		return arrayTitles;
	}

	/**
	 * Return the number of titles
	 * 
	 * @return String
	 */
	public String getFullDescription() {
		StringBuilder filterStr = new StringBuilder();
		filterStr.append("###Parameters used for Identidfied Spectra :").append("\n").append("###Titles number: ")
				.append(arrayTitles.size() - 1).append(" ; ");
		return filterStr.toString();
	}

	/**
	 * Return type
	 * 
	 * @return the className
	 */
	public String getType() {
		return this.getClass().getName();
	}

	/**
	 * Reset an array of titles
	 */
	public void resetArrayTitles() {
		arrayTitles.clear();
	}

	/***
	 * 
	 * @param titles
	 *            the tiles to set
	 */

	public void setArrayTitles(ArrayList<String> titles) {
		this.arrayTitles = titles;
	}

	/** Find the spectrum with its title */
	public void setIdentified(String title) {
		// Get the wanted spectra
		if (!ExporIntBatch.useBatchSpectra) {
			spectra = ListOfSpectra.getFirstSpectra();
		} else {
			spectra = ListOfSpectra.getBatchSpectra();
		}
		Spectrum spectrum = spectra.getSpectrumWithTitle(title);
		if (spectrum != null) {
			spectrum.setIsIdentified(true);
		}
	}

}
