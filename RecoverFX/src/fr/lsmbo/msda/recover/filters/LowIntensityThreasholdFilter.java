package fr.lsmbo.msda.recover.filters;


import fr.lsmbo.msda.recover.model.ComputationTypes;
import fr.lsmbo.msda.recover.model.Fragment;
import fr.lsmbo.msda.recover.model.Spectrum;

/**
 * Filter to keep specific spectrum with a low intensity threshold. Baseline is
 * calculated with the average of intensity of all fragment or with the median
 * of these fragments. Then the threshold is defined as baseline * emergence.
 * All peaks above this threshold will be considered as a useful peak. The
 * spectrum will be recover if useful peak number is included between min and
 * max useful peak number.
 * 
 * @author BL
 *
 */

public class LowIntensityThreasholdFilter implements BasicFilter {

	private float emergence;
	private int minUPN;
	private int maxUPN;
	private ComputationTypes mode;
	private Boolean isUsed = false;
	// private Boolean[] associatedSpectrum = new
	// Boolean[Spectra.getSpectraAsObservable().size()];
	private int id = 1;

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
		int nbFragmentsAboveThreashold = 0;
		for (int i = 0; i < spectrum.getNbFragments(); i++) {
			Fragment fragment = spectrum.getFragments().get(i);
			if (fragment.getIntensity() > threashold)
				nbFragmentsAboveThreashold++;
		}

		// set useful peaks number for the spectrum
		spectrum.setUpn(nbFragmentsAboveThreashold);

		// check if the spectrum is valid or not
		if (nbFragmentsAboveThreashold >= minUPN && nbFragmentsAboveThreashold <= maxUPN)
			return true;
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
		return "###Parameters used for Low Intensity Threshold Filter : \n" + "###Emergence : " + getEmergence() + " ; "
				+ "minimum UPN : " + getMinUPN() + " ; " + "maximum UPN : " + getMaxUPN() + " ; "
				+ "Baseline calculate with : " + getMode() + "\n";
	}

	@Override
	public Boolean getIsUsed() {
		return isUsed;
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

	public void setIsUsed(Boolean _isUsed) {
		this.isUsed = _isUsed;
	}

	// public Boolean[] getAssociatedSpectrum(){
	// return associatedSpectrum;
	// }
	//
	// public void setAssociatedSpectrum(Boolean[] associatedSpectrum){
	// this.associatedSpectrum = associatedSpectrum;
	// }
	//
	// public void addRecover(Boolean bool, int i){
	// associatedSpectrum[i] = bool;
	// }

	public int getId() {
		return id;
	}
}
