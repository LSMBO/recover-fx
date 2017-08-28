package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.model.Spectrum;

/**
 * This class provide information about high intensity threshold filter. Filter spectrum according
 * to number of peak wanted and the top line calculate with the number of peaks to consider and a
 * percentage.
 * 
 * @author BL
 *
 */
public class HighIntensityThresholdFilter implements BasicFilter {

	private int nbMostIntensePeaksToConsider;
	private float percentageOfTopLine; // should be between 0 and 1
	private int maxNbPeaks;

	/**
	 * 
	 * @param _nbMostIntensePeaksToConsider
	 *            number of the most intense peaks of the spectrum to consider to compute the top
	 *            line
	 * @param _percentageOfTopLine
	 *            value of the percentage of the top line
	 * @param _maxNbPeaks
	 *            number of maximum peaks of the spectrum above the threshold to keep it
	 */
	public void setParameters(int _nbMostIntensePeaksToConsider, float _percentageOfTopLine, int _maxNbPeaks) {
		nbMostIntensePeaksToConsider = _nbMostIntensePeaksToConsider;
		percentageOfTopLine = _percentageOfTopLine;
		maxNbPeaks = _maxNbPeaks;
	}

	/**
	 * Determine if spectrum are keep or not and set information about the threshold, the top line and the number of fragment above the threshold for this spectrum.
	 * <p>WARNING : if the number of fragment are lower than the number of most intense peaks to consider this method return false</p>
	 * @param spectrum
	 *            spectrum to apply high intensity threshold filter
	 * @return true (spectrum are kept) or false (spectrum are rejected)
	 *
	 */
	public Boolean isValid(Spectrum spectrum) {
		// first calculate top line
		float topline = 0;

		if (nbMostIntensePeaksToConsider > spectrum.getNbFragments())
			return false;

		if (nbMostIntensePeaksToConsider > 0) {
			for (int i = 0; i < nbMostIntensePeaksToConsider; i++) {
				topline += spectrum.getSortedFragments().get(spectrum.getNbFragments() - i - 1).getIntensity();
			}
			topline /= nbMostIntensePeaksToConsider;
		}
		// then calculate threshold
		float threshold = topline * (1 - percentageOfTopLine);

		spectrum.setHighIntensityThreshold(threshold);
		spectrum.setTopLine(topline);
		// invalidate if more than x peaks are above this value
		int i = 0;
		while (spectrum.getSortedFragments().get(spectrum.getNbFragments() - i - 1).getIntensity() > threshold) {

			if (i == spectrum.getNbFragments() - 1) { // break the while if all
														// the fragment have
														// been read
				break;
			}
			i++;
		}

		spectrum.setNbFragmentAboveHIT(i);

		if (i > maxNbPeaks)
			return false;
		return true;

	}

	@Override
	public String getFullDescription() {
		return "###Parameters used for High Intensity Threshold Filter : \n" + "###Number of most intense peaks to consider : " + getNbMostIntensePeaksToConsider() + " ; "
				+ "Percentage of top line : " + getPercentageOfTopLine() + " ; " + "Maximum of number Peaks : " + getMaxNbPeaks() + "\n";
	}

	/**
	 * 
	 * @return number of most intense peaks to consider for the spectrum
	 */
	public int getNbMostIntensePeaksToConsider() {
		return nbMostIntensePeaksToConsider;
	}

	/**
	 * 
	 * @return the percentage of the top line
	 */
	public float getPercentageOfTopLine() {
		return percentageOfTopLine;
	}

	/**
	 * 
	 * @return the number of maximum peaks for the spectrum above threshold to keep it
	 */
	public int getMaxNbPeaks() {
		return maxNbPeaks;
	}

}
