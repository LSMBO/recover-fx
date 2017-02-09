package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.model.Spectrum;

public class IdentifiedSpectraFilter implements BasicFilter{
	String[] arrayTitle;
	
	public void setParameters(String[] _arrayTitle){
		arrayTitle = _arrayTitle;
	}

	@Override
	public Boolean isValid(Spectrum spectrum){
		for (int i = 0; i < arrayTitle.length; i++){
			String title = arrayTitle[i];
			if (spectrum.getTitle().equalsIgnoreCase(title)){	
				return true;
			}
		}return false;
	}
	
	@Override
	public String getFullDescription() {
		return "";
	}
}
