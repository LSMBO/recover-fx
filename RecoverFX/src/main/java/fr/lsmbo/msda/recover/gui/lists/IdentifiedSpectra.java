package fr.lsmbo.msda.recover.gui.lists;

import java.util.ArrayList;

import fr.lsmbo.msda.recover.gui.io.ExportBatch;
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

	/** Find the spectrum with its title */
	public void setIdentified(String title) {
		// Get the wanted spectra
		if (!ExportBatch.useBatchSpectra) {
			spectra = ListOfSpectra.getFirstSpectra();
		} else {
			spectra = ListOfSpectra.getBatchSpectra();
		}
		Spectrum spectrum = spectra.getSpectrumWithTitle(title);

		if (spectrum != null) {
			spectrum.setIsIdentified(true);
		}
	}

	public ArrayList<String> getArrayTitles() {
		return arrayTitles;
	}

	public void setArrayTitles(ArrayList<String> titles) {
		this.arrayTitles = titles;
	}

	public void addTitle(String title) {
		arrayTitles.add(title);
	}

	public void addAllTitles(ArrayList<String> allTitles) {
		arrayTitles.addAll(allTitles);
	}

	public void resetArrayTitles() {
		arrayTitles.clear();
	}

	public String getType() {
		return this.getClass().getName();
	}

	public String getFullDescription() {
		StringBuilder filterStr = new StringBuilder();
		filterStr.append("###Parameters used for Identidfied Spectra :").append("\n").append("###Titles number: ")
				.append(arrayTitles.size() - 1).append(" ; ");
		return filterStr.toString();
	}

}
