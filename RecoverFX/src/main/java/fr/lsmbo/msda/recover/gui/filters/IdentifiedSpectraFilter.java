package fr.lsmbo.msda.recover.gui.filters;

import fr.lsmbo.msda.recover.gui.model.Spectrum;
import fr.lsmbo.msda.recover.gui.model.settings.SpectrumTitleSelector;

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
	private SpectrumTitleSelector fileParams = null;

	/**
	 * @return the file parameters
	 */
	public final SpectrumTitleSelector getFileParams() {
		return fileParams;
	}

	/**
	 * @param fileParams
	 *            the file parameters to set
	 */
	public final void setFileParams(SpectrumTitleSelector fileParams) {
		this.fileParams = fileParams;
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
		if (this.fileParams != null)
			return fileParams.toString();
		else {
			return "###Spectrum titles";
		}
	}

	/**
	 * Determines whether the spectrum is identified
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

	/**
	 * @return filter type . it's the class name.
	 */
	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
