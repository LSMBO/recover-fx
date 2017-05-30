package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.model.ComparisonTypes;
import fr.lsmbo.msda.recover.model.Spectrum;

/**
 * This class provide information about Fragment intensity filter (getter and setter). Filter spectrum according to maximal intensity of fragment.
 * 
 * @author BL
 *
 */

public class FragmentIntensityFilter implements BasicFilter {

	private int intensity;
	private ComparisonTypes comparator;
	private int id = 4;

	/**
	 * 
	 * @param _intensity
	 *	intensity to compare
	 * @param _comparator
	 *  comparator of intensity (equals, not equals, greater, greater or equals, lower, lower or equals)
	 */
	public void setParameters(int _intensity, ComparisonTypes _comparator) {
		intensity = _intensity;
		comparator = _comparator;
	}

	@Override
	/**
	 * Determine if a spectrum are keep or not by comparing maximal intensity of these fragment with a specific intensity
	 * @param spectrum
	 * Spectrum to apply fragment intensity filter
	 * @return true (spectrum are kept) or false (spectrum are rejected)
	 */
	public Boolean isValid(Spectrum spectrum) {
		switch (comparator) {
		case EQUALS_TO:
			if (spectrum.getFragmentMaxIntensity() == intensity)
				return false;
			break;
		case NOT_EQUALS_TO:
			if (spectrum.getFragmentMaxIntensity() != intensity)
				return false;
			break;
		case GREATER_THAN:
			if (spectrum.getFragmentMaxIntensity() > intensity)
				return false;
			break;
		case GREATER_OR_EQUAL:
			if (spectrum.getFragmentMaxIntensity() >= intensity)
				return false;
			break;
		case LOWER_THAN:
			if (spectrum.getFragmentMaxIntensity() < intensity)
				return false;
			break;
		case LOWER_OR_EQUAL:
			if (spectrum.getFragmentMaxIntensity() <= intensity)
				return false;
			break;
		default:
			return true;
		}
		return true;
	}

	@Override
	public String getFullDescription() {
		return "###Parameters used for Fragment Intensity Filter : \n" + "###Intensity : " + getIntensityFragment()
				+ " ; " + "Comparator : " + getComparator() + "\n";
	}

	/**
	 * 
	 * @return Value of intensity
	 */
	public int getIntensityFragment() {
		return intensity;
	}

	/**
	 * 
	 * @return A comparator of intensity
	 */
	public ComparisonTypes getComparator() {
		return comparator;
	}

	public int getId() {
		return id;
	}
}
