package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.Spectrum;
/**
 * Filter to remove spectrum when the m/z precursor is greater than the last fragment (max m/z).
 * @author BL
 *
 */
public class WrongChargeFilter implements BasicFilter {
	private Boolean isUsed = false;
//	private Boolean[] associatedSpectrum = new Boolean[Spectra.getSpectraAsObservable().size()];
	private int id = 5;

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
