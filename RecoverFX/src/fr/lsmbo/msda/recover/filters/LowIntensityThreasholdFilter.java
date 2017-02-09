package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.model.ComputationTypes;
import fr.lsmbo.msda.recover.model.Fragment;
import fr.lsmbo.msda.recover.model.Spectrum;

public class LowIntensityThreasholdFilter implements BasicFilter {

	private int emergence;
	private int minUPN;
	private int maxUPN;
	private ComputationTypes mode;

	public void setParameters(int _emergence, int _minUPN, int _maxUPN, ComputationTypes _mode) {
		emergence = _emergence;
		minUPN = _minUPN;
		maxUPN = _maxUPN;
		mode = _mode;
	}
	
	/* (non-Javadoc)
	 * @see fr.lsmbo.msda.recover.filters.BasicFilter#isValid(fr.lsmbo.msda.recover.model.Spectrum)
	 * test if the spectra is valid or not
	 * this method actually does the main test
	 */
	@Override
	public Boolean isValid(Spectrum spectrum) {
		// first get the raw baseline 
		float baseline = spectrum.getMedianFragmentsIntensities();
		if(mode == ComputationTypes.AVERAGE) baseline = spectrum.getAverageFragmentsIntensities();
		// then multiply with Emergence to get the threshold
		float threashold = baseline * emergence;
		// then count peaks below minUPN and above maxUPN
		int nbFragmentsAboveThreashold = 0;
		for(int i = 0; i < spectrum.getNbFragments(); i++) {
			Fragment fragment = spectrum.getFragments().get(i);
			if(fragment.getIntensity() > threashold) nbFragmentsAboveThreashold++;
		}
		
		//set useful peaks number for the spectrum
		spectrum.setUpn(nbFragmentsAboveThreashold);
		
		//check if the spectrum is valid or not
		if(nbFragmentsAboveThreashold >= minUPN && nbFragmentsAboveThreashold <= maxUPN)
			return true;
		return false;
		
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * returns description (should be used in the tooltiptext)
	 */
	@Override
	public String getFullDescription() {
		return "";
	}
}
