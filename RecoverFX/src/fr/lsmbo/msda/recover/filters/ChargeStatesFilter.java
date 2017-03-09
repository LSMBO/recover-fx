package fr.lsmbo.msda.recover.filters;

import java.util.ArrayList;

import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.Spectrum;
/**
 * filter to keep spectrum according to specific charges: 1,2,3,4,5 or above 5.
 * Take different Boolean in parameters to use this filter. A charge will be kept (Boolean = true) or rejected (Boolean = false)
 * If the Boolean is false for a specific charge, all the spectrum with this charge will not be recover.
 * @author BL
 * 
 */
public class ChargeStatesFilter implements BasicFilter {

	private Boolean keepCharge1 = true;
	private Boolean keepCharge2 = true;
	private Boolean keepCharge3 = true;
	private Boolean keepCharge4 = true;
	private Boolean keepCharge5 = true;
	private Boolean keepChargeAbove5 = true;
	private Boolean keepUnknownCharge = true;
	private Boolean isUsed = false;
//	private Boolean[] associatedSpectrum = new Boolean[Spectra.getSpectraAsObservable().size()];
	private int id = 2;
	
	/**
	 * set the different parameters (Boolean) to this class
	 * @param z1
	 *		Boolean of charge 1 = true if we keep this charge
	 * @param z2
	 * @param z3
	 * @param z4
	 * @param z5
	 * @param zOver5
	 * @param zUnknown
	 */
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
	/**
	 * @param spectrum
	 * 		A specific spectrum
	 * @return
	 * 		Boolean false or true to set the value of recover
	 * @see Spectrum
	 */
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
				+"###keep charge 1+ : " + getKeepCharge1() + " ; "
				+"keep charge 2+ : " + getKeepCharge2() + " ; "
				+"keep charge 3+ : " + getKeepCharge3() + " ; "
				+"keep charge 4+ : " + getKeepCharge4() + " ; "
				+"keep charge 5+ : " + getKeepCharge5() + " ; "
				+"keep charge above 5+ : " + getKeepChargeAbove5() + " ; "
				+"keep unkwnon charge : " + getKeepUnknownCharge() + "\n";
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
	
//	public Boolean[] getAssociatedSpectrum(){
//		return associatedSpectrum;
//	}
//	
//	public void setAssociatedSpectrum(Boolean[] associatedSpectrum){
//		this.associatedSpectrum = associatedSpectrum;
//	}
//	
//	public void addRecover(Boolean bool, int i){
//		associatedSpectrum[i] = bool;
//	}
	
	public int getId(){
		return id;
	}
}
