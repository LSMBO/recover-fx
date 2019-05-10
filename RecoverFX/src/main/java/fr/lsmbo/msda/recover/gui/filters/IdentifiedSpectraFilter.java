package fr.lsmbo.msda.recover.gui.filters;

import fr.lsmbo.msda.recover.gui.model.Spectrum;

/**
 * Filter to identify a spectrum with the title and to set the value of recover
 * in different case(keep or rejected spectrum identified or keep or rejected
 * spectrum non identified).
 * 
 * @author BL
 * @author Aromdhani
 */

public class IdentifiedSpectraFilter implements BasicFilter {

	private Boolean recoverSpectrumIdentified = false;
	private Boolean recoverSpectrumNonIdentified = false;
    private String titlesFile;
	/**
	 * @return the titlesFile
	 */
	public final String getTitlesFile() {
		return titlesFile;
	}

	/**
	 * @param titlesFile the titlesFile to set
	 */
	public final void setTitlesFile(String titlesFile) {
		this.titlesFile = titlesFile;
	}

	public void setParameters(Boolean _recoverSpectrumIdentified, Boolean _recoverSpectrumNonIdentified) {
		recoverSpectrumIdentified = _recoverSpectrumIdentified;
		recoverSpectrumNonIdentified = _recoverSpectrumNonIdentified;
	}

	/**
	 * Determines whether the spectrum is valid.
	 * 
	 * @param spectrum
	 *            the spectrum to check
	 */
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

	/***
	 * Return the description of the filter
	 * 
	 * @return the full description of the filter .
	 */
	@Override
	public String getFullDescription() {
		return "###Identified Spectra Filter";
	}

	/**
	 * Determeines whether the spectrum is identified
	 * 
	 * @return <code>true</code> if identified
	 */
	public Boolean getRecoverSpectrumIdentified() {
		return recoverSpectrumIdentified;
	}

	/**
	 * Determines whether the spectrum is non identified
	 * 
	 * @return <code>true</code> if non identified
	 */
	public Boolean getRecoverSpectrumNonIdentified() {
		return recoverSpectrumNonIdentified;
	}

	@Override
	public String getType() {
		return this.getClass().getName();
	}
}
