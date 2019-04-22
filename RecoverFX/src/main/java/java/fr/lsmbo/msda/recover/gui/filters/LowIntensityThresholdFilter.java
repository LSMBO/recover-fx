package fr.lsmbo.msda.recover.gui.filters;

import fr.lsmbo.msda.recover.gui.model.ComputationTypes;
import fr.lsmbo.msda.recover.gui.model.Fragment;
import fr.lsmbo.msda.recover.gui.model.Spectrum;

/**
 * Provide information about low intensity threshold. Filter spectrum according
 * to threshold (baseline * emergence , baseline can be compute by median or
 * average of fragments intensities) and the number of peaks we want above this
 * threshold.
 * 
 * @author BL
 * @author Aromdhani
 *
 */

public class LowIntensityThresholdFilter implements BasicFilter {

	private float emergence;
	private int minUPN;
	private int maxUPN;
	private ComputationTypes mode;

	/**
	 * 
	 * @param _emergence
	 *            value of the emergence (multiplier of baseline)
	 * @param _minUPN
	 *            minimum useful peak number - minimum number of peaks above the
	 *            threshold
	 * @param _maxUPN
	 *            maximum useful peak number - maximum number of peaks above the
	 *            threshold
	 *            <p>
	 *            if the number is equal to 0, filter consider only _minUPN
	 *            </p>
	 *
	 * @param _mode
	 *            value of the baseline : average or median of fragments
	 *            intensities
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
	/**
	 * Determines wether the spectrum is valid.
	 * 
	 * @param spectrum
	 *            the spectrum to check.
	 */
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

		// Set useful peaks number for the spectrum
		spectrum.setUpn(nbFragmentsAboveThreshold);

		// Check if the spectrum is valid or not
		if (maxUPN != 0) {
			if (nbFragmentsAboveThreshold >= minUPN && nbFragmentsAboveThreshold <= maxUPN)
				return true;
		} else if (maxUPN == 0) {
			if (nbFragmentsAboveThreshold >= minUPN)
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder filterStr = new StringBuilder();
		filterStr.append("###Parameters used for Low Intensity Threshold Filter:").append("\n").append("###Emergence: ")
				.append(getEmergence()).append(" ; ").append("minimum UPN: ").append(" ; ").append("maximum UPN: ")
				.append(getMaxUPN()).append(" ; ").append("Baseline calculate with: ").append(getMode()).append("\n");
		return filterStr.toString();
	}

	/**
	 * @return the emergence value
	 */
	public float getEmergence() {
		return emergence;
	}

	/**
	 * @return the min useful peakns number
	 */
	public int getMinUPN() {
		return minUPN;
	}

	/**
	 * @return the max useful peaks number
	 */
	public int getMaxUPN() {
		return maxUPN;
	}

	/**
	 * @return the computation type
	 */
	public ComputationTypes getMode() {
		return mode;
	}

	@Override
	public String getFullDescription() {
		// TODO Auto-generated method stub
		StringBuilder filterStr = new StringBuilder();
		filterStr.append("###Parameters used for Low Intensity Threshold Filter:").append("\n").append("###Emergence: ")
				.append(getEmergence()).append(" ; ").append("minimum UPN: ").append(getMinUPN()).append(" ; ")
				.append("maximum UPN: ").append(getMaxUPN()).append(" ; ").append("Baseline calculate with: ")
				.append(getMode()).append("\n");
		return filterStr.toString();
	}

	@Override
	public String getType() {
		return this.getClass().getName();
	}
}
