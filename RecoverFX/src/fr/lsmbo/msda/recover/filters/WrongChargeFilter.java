package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.model.Spectrum;

public class WrongChargeFilter implements BasicFilter {

	@Override
	public Boolean isValid(Spectrum spectrum) {
		if(spectrum.getCharge() == 1 && spectrum.getMz() > spectrum.getFragmentMaxMoz())
			return false;
		return true;
	}

	@Override
	public String getFullDescription() {
		return "";
	}

}
