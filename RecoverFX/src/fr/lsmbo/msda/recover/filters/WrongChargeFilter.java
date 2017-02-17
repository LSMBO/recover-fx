package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.model.Spectrum;

public class WrongChargeFilter implements BasicFilter {
	private Boolean isUsed = false;

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
	@Override
	public Boolean getIsUsed(){
		return isUsed;
	}
	
	public void setIsUsed(Boolean _isUsed){
		this.isUsed = _isUsed ;
	}
}
