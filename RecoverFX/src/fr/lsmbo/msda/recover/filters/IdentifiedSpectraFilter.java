package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.Spectrum;

public class IdentifiedSpectraFilter{
	private Boolean isUsed = false ;
	private String [] arrayTitles ;
	
	public void setParameters(String [] _arrayTitles){
		arrayTitles = _arrayTitles;
	}
	
	public void setIdentified(String title){
		Spectrum spectrum = Spectra.getSpectrumWithTitle(title);
		spectrum.setIsIdentified(true);
	}
	
	public Boolean getIsUsed(){
		return isUsed;
	}
	
	public void setIsUsed(Boolean _isUsed){
		this.isUsed = _isUsed ;
	}

	public String [] getArrayTitles(){
		return arrayTitles;
	}
	
//	@Override
//	public Boolean isValid(Spectrum spectrum){
//		for (int i = 0; i < arrayTitle.length; i++){
//			String title = arrayTitle[i];
//			if (spectrum.getTitle().equalsIgnoreCase(title)){	
//				return true;
//			}
//		}return false;
//	}
	
}
