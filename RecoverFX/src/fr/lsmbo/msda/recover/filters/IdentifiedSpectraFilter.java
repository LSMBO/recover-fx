package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.Spectrum;

/**
 * Filter to identify a spectrum with the title and to set the value of recover
 * in different case(keep or rejected spectrum identified or keep or rejected
 * spectrum non identified).
 * 
 * @author BL
 *
 */
public class IdentifiedSpectraFilter {
	private Boolean isUsed = false;
	private String[] arrayTitles;
	private static Boolean checkRecoverIdentified = false;
	private static Boolean uncheckRecoverIdentified = false;
	private static Boolean checkRecoverNonIdentified = false;
	private static Boolean uncheckRecoverNonIdentified = false;
	private Spectra spectra;

	public void setParameters(String[] _arrayTitles) {
		arrayTitles = _arrayTitles;
	}

	// Find the spectrum with his title
	public void setIdentified(String title) {
		spectra = ListOfSpectra.choiceSpectra();
		Spectrum spectrum = spectra.getSpectrumWithTitle(title);
		spectrum.setIsIdentified(true);
	}

	public Boolean isValid(Spectrum spectrum) {
		if (spectrum.getIsIdentified() == true) {
			if (checkRecoverIdentified)
				return true;
			if (uncheckRecoverIdentified)
				return false;
		}
		if (spectrum.getIsIdentified() == false) {
			if (checkRecoverNonIdentified)
				return true;
			if (uncheckRecoverNonIdentified)
				return false;
		}
		return false;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean _isUsed) {
		this.isUsed = _isUsed;
	}

	public String[] getArrayTitles() {
		return arrayTitles;
	}

	public String getFullDescription() {
		String titles = "";
		for (String title : arrayTitles) {
			titles += "###" + title + " ;\n";
		}
		return "###Identified Spectra Filter used with " + getArrayTitles().length + " titles :\n" + titles;
	}

	public static void setCheckRecoverIdentified(Boolean _checkRecoverIdentified) {
		checkRecoverIdentified = _checkRecoverIdentified;
	}

	public static void setUncheckRecoverIdentified(Boolean _uncheckRecoverIdentified) {
		uncheckRecoverIdentified = _uncheckRecoverIdentified;
	}

	public static void setCheckRecoverNonIdentified(Boolean _checkRecoverNonIdentified) {
		checkRecoverNonIdentified = _checkRecoverNonIdentified;
	}

	public static void setUncheckRecoverNonIdentified(Boolean _uncheckRecoverNonIdentified) {
		uncheckRecoverNonIdentified = _uncheckRecoverNonIdentified;
	}
	// @Override
	// public Boolean isValid(Spectrum spectrum){
	// for (int i = 0; i < arrayTitle.length; i++){
	// String title = arrayTitle[i];
	// if (spectrum.getTitle().equalsIgnoreCase(title)){
	// return true;
	// }
	// }return false;
	// }

}
