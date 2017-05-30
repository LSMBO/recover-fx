package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.model.ComputationTypes;
import fr.lsmbo.msda.recover.model.Fragment;
import fr.lsmbo.msda.recover.model.Spectrum;

/**
 * This class provide information about low intensity threshold. Filter spectrum according to threshold (baseline * emergence , baseline 
 * can be compute by median or average of fragments intensities) and the number of peaks we want above this threshold.
 * 
 * @author BL
 *
 */

public class LowIntensityThresholdFilter implements BasicFilter {

	private float emergence;
	private int minUPN;
	private int maxUPN;
	private ComputationTypes mode;
	private int id = 1;

	/**
	 * 
	 * @param _emergence
	 * 	Value of the emergence (multiplier of baseline)
	 * @param _minUPN
	 * 	Minimum useful peak number - minimum number of peaks above the threshold
	 * @param _maxUPN
	 * 	Maximum useful peak number - maximum number of peaks above the threshold
	 * <p> if the number is equal to 0, filter consider only _minUPN</p>
	 *
	 * @param _mode
	 * 	value of the baseline : average or median of fragments intensities
	 */
	public void setParameters(float _emergence, int _minUPN, int _maxUPN, ComputationTypes _mode) {
		emergence = _emergence;
		minUPN = _minUPN;
		maxUPN = _maxUPN;
		mode = _mode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.lsmbo.msda.recover.filters.BasicFilter#isValid(fr.lsmbo.msda.recover.
	 * model.Spectrum) test if the spectra is valid or not this method actually
	 * does the main test
	 */
	@Override
	public Boolean isValid(Spectrum spectrum) {
		// first get the raw baseline
		float baseline = spectrum.getMedianFragmentsIntensities();
		if (mode == ComputationTypes.AVERAGE)
			baseline = spectrum.getAverageFragmentsIntensities();
		// then multiply with Emergence to get the threshold
		float threashold = baseline * emergence;
		spectrum.setLowIntensityThreshold(threashold);
		// then count peaks below minUPN and above maxUPN
		int nbFragmentsAboveThreshold = 0;
		for (int i = 0; i < spectrum.getNbFragments(); i++) {
			Fragment fragment = spectrum.getFragments().get(i);
			if (fragment.getIntensity() > threashold)
				nbFragmentsAboveThreshold++;
		}

		// set useful peaks number for the spectrum
		spectrum.setUpn(nbFragmentsAboveThreshold);

		// check if the spectrum is valid or not
		if (maxUPN != 0) {
			if (nbFragmentsAboveThreshold >= minUPN && nbFragmentsAboveThreshold <= maxUPN)
				return true;
		} else if (maxUPN == 0) {
			if (nbFragmentsAboveThreshold >= minUPN)
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString() returns description (should be used in
	 * the tooltiptext)
	 */
	@Override
	public String getFullDescription() {
		return "###Parameters used for Low Intensity Threshold Filter : \n" + "###Emergence : " + getEmergence() + " ; " + "minimum UPN : " + getMinUPN() + " ; " + "maximum UPN : " + getMaxUPN()
				+ " ; " + "Baseline calculate with : " + getMode() + "\n";
	}

	public float getEmergence() {
		return emergence;
	}

	public int getMinUPN() {
		return minUPN;
	}

	public int getMaxUPN() {
		return maxUPN;
	}

	public ComputationTypes getMode() {
		return mode;
	}

	public int getId() {
		return id;
	}
}
