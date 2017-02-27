package fr.lsmbo.msda.recover.filters;


import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.Spectrum;

public class HighIntensityThreasholdFilter implements BasicFilter {

	private int nbMostIntensePeaksToConsider;
	private float percentageOfTopLine; // should be between 0 and 1
	private int maxNbPeaks;
	private Boolean isUsed = false;
	private Boolean[] associatedSpectrum = new Boolean[Spectra.getSpectraAsObservable().size()];
	private int id = 0;
	
	public void setParameters(int _nbMostIntensePeaksToConsider, float _percentageOfTopLine, int _maxNbPeaks) {
		nbMostIntensePeaksToConsider = _nbMostIntensePeaksToConsider;
		percentageOfTopLine = _percentageOfTopLine;
		maxNbPeaks = _maxNbPeaks;
	}

	@Override
	public Boolean isValid(Spectrum spectrum) {
		// first calculate top line
		float topline = 0;

		if(nbMostIntensePeaksToConsider > spectrum.getNbFragments())
			return false;
		
		if(nbMostIntensePeaksToConsider > 0 ) {
			for(int i = 0; i < nbMostIntensePeaksToConsider; i++) {
				topline += spectrum.getSortedFragments().get(spectrum.getNbFragments() - i - 1).getIntensity();
			}
			topline /= nbMostIntensePeaksToConsider;
		}
		// then calculate threashold
		float threashold = topline * (1 - percentageOfTopLine);
		// invalidate if more than x peaks are above this value
		int i = 0;
		while(spectrum.getSortedFragments().get(spectrum.getNbFragments() - i - 1).getIntensity() > threashold) {
			
			if (i == spectrum.getNbFragments() - 1){ //break the while if all the fragment have been read
				break;
			}
			i++;
		}
		
		if(i > maxNbPeaks)
			return false;

		return true;
	}

	@Override
	public String getFullDescription() {
		return "###Parameters used for High Intensity Threshold Filter : \n"
				+ "###Number of most intense peaks to consider : " + getNbMostIntensePeaksToConsider() + " ; "
				+ "Percentage of top line : " + getPercentageOfTopLine() + " ; "
				+ "Maximum of number Peaks : " + getMaxNbPeaks() + "\n";
	}
	
	public int getNbMostIntensePeaksToConsider(){
		return nbMostIntensePeaksToConsider;
	}
	
	public float getPercentageOfTopLine(){
		return percentageOfTopLine;
	}
	
	public int getMaxNbPeaks(){
		return maxNbPeaks;
	}
	@Override
	public Boolean getIsUsed(){
		return isUsed;
	}
	
	public  void setIsUsed(Boolean _isUsed){
		this.isUsed = _isUsed ;
	}
	
	public Boolean[] getAssociatedSpectrum(){
		return associatedSpectrum;
	}
	
	public void setAssociatedSpectrum(Boolean[] associatedSpectrum){
		this.associatedSpectrum = associatedSpectrum;
	}
	
	public void addRecover(Boolean bool, int i){
		associatedSpectrum[i] = bool;
	}
	
	public int getId(){
		return id;
	}
}
