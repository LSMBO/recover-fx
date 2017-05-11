package fr.lsmbo.msda.recover.filters;

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
	private Boolean recoverSpectrumIdentified = false;
	private Boolean recoverSpectrumNonIdentified = false;

	// public void setParameters(String[] _arrayTitles) {
	// arrayTitles = _arrayTitles;
	// }

	public void setParameters(Boolean _recoverSpectrumIdentified, Boolean _recoverSpectrumNonIdentified) {
		recoverSpectrumIdentified = _recoverSpectrumIdentified;
		recoverSpectrumNonIdentified = _recoverSpectrumNonIdentified;
	}

	public Boolean isValid(Spectrum spectrum) {
		if (spectrum.getIsIdentified() == true) {
			if (recoverSpectrumIdentified)
				return true;
		}
		if (spectrum.getIsIdentified() == false) {
			if (recoverSpectrumNonIdentified)
				return true;
		}
		return false;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean _isUsed) {
		this.isUsed = _isUsed;
	}

	public String getFullDescription() {
		// String titles = "";
		// for (String title : arrayTitles) {
		// titles += "###" + title + " ;\n";
		// }
		// return "###Identified Spectra Filter used with " +
		// getArrayTitles().length + " titles :\n" + titles;
		return "";
	}

	public Boolean getRecoverSpectrumIdentified() {
		return recoverSpectrumIdentified;
	}

	public Boolean getRecoverSpectrumNonIdentified() {
		return recoverSpectrumNonIdentified;
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
