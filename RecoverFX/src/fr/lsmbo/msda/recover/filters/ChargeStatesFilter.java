package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.model.Spectrum;

public class ChargeStatesFilter implements BasicFilter {

	private Boolean keepCharge1 = true;
	private Boolean keepCharge2 = true;
	private Boolean keepCharge3 = true;
	private Boolean keepCharge4 = true;
	private Boolean keepCharge5 = true;
	private Boolean keepChargeAbove5 = true;
	private Boolean keepUnknownCharge = true;
	private  Boolean isUsed = false;
	
	public void setParameters(Boolean z1, Boolean z2, Boolean z3, Boolean z4, Boolean z5, Boolean zOver5, Boolean zUnknown) {
		keepCharge1 = z1;
		keepCharge2 = z2;
		keepCharge3 = z3;
		keepCharge4 = z4;
		keepCharge5 = z5;
		keepChargeAbove5 = zOver5;
		keepUnknownCharge = zUnknown;
	}
	
	// test if the spectra is valid or not
	@Override
	public Boolean isValid(Spectrum spectrum) {
		if(spectrum.getCharge() == -1 && !keepUnknownCharge) return false;
		if(spectrum.getCharge() == 1 && !keepCharge1) return false;
		if(spectrum.getCharge() == 2 && !keepCharge2) return false;
		if(spectrum.getCharge() == 3 && !keepCharge3) return false;
		if(spectrum.getCharge() == 4 && !keepCharge4) return false;
		if(spectrum.getCharge() == 5 && !keepCharge5) return false;
		if(spectrum.getCharge() >= 6 && !keepChargeAbove5) return false;
		return true;
	}
		
	// returns description
	@Override
	public String getFullDescription() {
		return "###Parameters used for Charge States Filter : \n"
				+"###";
	}
	
	public Boolean getKeepCharge1(){
		return keepCharge1;
	}

	public Boolean getKeepCharge2(){
		return keepCharge2;
	}
	
	public Boolean getKeepCharge3(){
		return keepCharge3;
	}
	
	public Boolean getKeepCharge4(){
		return keepCharge4;
	}
	
	public Boolean getKeepCharge5(){
		return keepCharge5;
	}
	
	public Boolean getKeepChargeAbove5(){
		return keepChargeAbove5;
	}
	
	public Boolean getKeepUnknownCharge(){
		return keepUnknownCharge;
	}
	
	@Override
	public Boolean getIsUsed(){
		return isUsed;
	}
	
	public void setIsUsed(Boolean _isUsed){
		this.isUsed = _isUsed ;
	}
}
