package fr.lsmbo.msda.recover.filters;


import fr.lsmbo.msda.recover.model.ComparisonTypes;
import fr.lsmbo.msda.recover.model.Spectrum;

public class PrecursorIntensityFilter implements BasicFilter {

	private int intensity;
	private ComparisonTypes comparator;
	private Boolean isUsed =false;
	
	public void setParameters(int _intensity, ComparisonTypes _comparator) {
		intensity = _intensity;
		comparator = _comparator;
	}
	
	@Override
	public Boolean isValid(Spectrum spectrum) {
		switch(comparator) {
		case EQUALS_TO:
			if(spectrum.getIntensity() == intensity) return false; 
			break;
		case NOT_EQUALS_TO:
			if(spectrum.getIntensity() != intensity) return false; 
			break;
		case GREATER_THAN:
			if(spectrum.getIntensity() > intensity) return false; 
			break;
		case GREATER_OR_EQUAL:
			if(spectrum.getIntensity() >= intensity) return false; 
			break;
		case LOWER_THAN:
			if(spectrum.getIntensity() < intensity) return false; 
			break;
		case LOWER_OR_EQUAL:
			if(spectrum.getIntensity() <= intensity) return false; 
			break;
		default: return true;
		}
		return true;
	}

	@Override
	public String getFullDescription() {
		return "";
	}
	
	public int getIntensityPrecursor(){
		return intensity;
	}
	
	public ComparisonTypes getComparator(){
		return comparator;
	}
	
	@Override
	public Boolean getIsUsed(){
		return isUsed;
	}
	
	public void setIsUsed(Boolean _isUsed){
		this.isUsed = _isUsed ;
	}
}
