package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.ComparisonTypes;
import fr.lsmbo.msda.recover.model.Spectrum;

public class FragmentIntensityFilter implements BasicFilter {

	private int intensity;
	private ComparisonTypes comparator;
	private Boolean isUsed = false;
//	private Boolean[] associatedSpectrum = new Boolean[Spectra.getSpectraAsObservable().size()];
	private int id = 4;
	
	public void setParameters(int _intensity, ComparisonTypes _comparator) {
		intensity = _intensity;
		comparator = _comparator;
	}
	
	@Override
	public Boolean isValid(Spectrum spectrum) {
		switch(comparator) {
		case EQUALS_TO:
			if(spectrum.getFragmentMaxIntensity() == intensity) return false; 
			break;
		case NOT_EQUALS_TO:
			if(spectrum.getFragmentMaxIntensity() != intensity) return false; 
			break;
		case GREATER_THAN:
			if(spectrum.getFragmentMaxIntensity() > intensity) return false; 
			break;
		case GREATER_OR_EQUAL:
			if(spectrum.getFragmentMaxIntensity() >= intensity) return false; 
			break;
		case LOWER_THAN:
			if(spectrum.getFragmentMaxIntensity() < intensity) return false; 
			break;
		case LOWER_OR_EQUAL:
			if(spectrum.getFragmentMaxIntensity() <= intensity) return false; 
			break;
		default: return true;
		}
		return true;
	}

	@Override
	public String getFullDescription() {
		return "###Parameters used for Fragment Intensity Filter : \n"
				+"###Intensity : " + getIntensityFragment() + " ; "
				+"Comparator : " + getComparator() + "\n";
	}
	
	public int getIntensityFragment(){
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
		this.isUsed = _isUsed;
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
