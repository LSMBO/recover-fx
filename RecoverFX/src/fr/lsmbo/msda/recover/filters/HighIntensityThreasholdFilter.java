package fr.lsmbo.msda.recover.filters;


import fr.lsmbo.msda.recover.model.Spectrum;

public class HighIntensityThreasholdFilter implements BasicFilter {

	int nbMostIntensePeaksToConsider;
	float percentageOfTopLine; // should be between 0 and 1
	int maxNbPeaks;
	
	public void setParameters(int _nbMostIntensePeaksToConsider, float _percentageOfTopLine, int _maxNbPeaks) {
		nbMostIntensePeaksToConsider = _nbMostIntensePeaksToConsider;
		percentageOfTopLine = _percentageOfTopLine;
		maxNbPeaks = _maxNbPeaks;
	}

	@Override
	public Boolean isValid(Spectrum spectrum) {
		// first calculate top line
		float topline = 0;

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
		return "";
	}

}
