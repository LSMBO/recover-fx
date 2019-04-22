package fr.lsmbo.msda.recover.gui.filters;

import fr.lsmbo.msda.recover.gui.model.Spectrum;

public interface BasicFilter {

	/**
	 * Determines whether a spectrum is valid.
	 * 
	 * @param spectrum
	 *            the spectrum to check
	 * @return <code>true</code> if the spectrum is valid otherwise
	 *         <code>false</code>
	 */
	Boolean isValid(Spectrum spectrum);

	/**
	 *Return the full description of the filter
	 * @return String
	 */
	String getFullDescription();
	/**
	 * Return filter type
	 * @return String
	 */
	public String getType();
}
