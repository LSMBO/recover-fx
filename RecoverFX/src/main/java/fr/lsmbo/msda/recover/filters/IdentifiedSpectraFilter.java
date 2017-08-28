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

	private Boolean recoverSpectrumIdentified = false;
	private Boolean recoverSpectrumNonIdentified = false;


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



	public String getFullDescription() {

		return "";
	}

	public Boolean getRecoverSpectrumIdentified() {
		return recoverSpectrumIdentified;
	}

	public Boolean getRecoverSpectrumNonIdentified() {
		return recoverSpectrumNonIdentified;
	}


}
