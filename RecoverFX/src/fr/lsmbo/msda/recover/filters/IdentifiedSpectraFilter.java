package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.Spectrum;

public class IdentifiedSpectraFilter{
	
	public void setIdentified(String title){
		Spectrum spectrum = Spectra.getSpectrumWithTitle(title);
		spectrum.setIsIdentified(true);
	}
	
//	String[] arrayTitle;
//	
//	public void setParameters(String[] _arrayTitle){
//		arrayTitle = _arrayTitle;
//	}
	
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
