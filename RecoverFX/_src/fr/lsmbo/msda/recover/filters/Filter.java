package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.model.Spectrum;

public class Filter {

	// tell if the spectrum is recovered or not
	public Boolean isRecover(Spectrum spectrum) {
		return true;
	}
	
	// returns a description of the filter's parameters (meant to be put in an export)
	public String toString() {
		return "";
	}
}
