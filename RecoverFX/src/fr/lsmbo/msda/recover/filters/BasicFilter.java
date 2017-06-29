package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.model.Spectrum;

public interface BasicFilter {

	// test if the spectra is valid or not
	Boolean isValid(Spectrum spectrum);

	// returns description
	String getFullDescription();



}
