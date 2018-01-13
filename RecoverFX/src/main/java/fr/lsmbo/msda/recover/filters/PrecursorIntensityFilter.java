package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.model.ComparisonTypes;
import fr.lsmbo.msda.recover.model.Spectrum;

/**
 * Filter to remove specific spectrum according to intensity of precursor. Make
 * a comparison with these precursor ( equals, different, greater, lower). For
 * example, lower than xxx intensity, all the spectrum with a precursor's
 * intensity lower than this value will be removed.
 *
 * @author BL
 *
 */
public class PrecursorIntensityFilter implements BasicFilter {

	private int intensity;
	private ComparisonTypes comparator;

	public void setParameters(int _intensity, ComparisonTypes _comparator) {
		intensity = _intensity;
		comparator = _comparator;
	}

	@Override
	public Boolean isValid(Spectrum spectrum) {
		switch (comparator) {
		case EQUALS_TO:
			if (spectrum.getIntensity() == intensity)
				return false;
			break;
		case NOT_EQUALS_TO:
			if (spectrum.getIntensity() != intensity)
				return false;
			break;
		case GREATER_THAN:
			if (spectrum.getIntensity() > intensity)
				return false;
			break;
		case GREATER_OR_EQUAL:
			if (spectrum.getIntensity() >= intensity)
				return false;
			break;
		case LOWER_THAN:
			if (spectrum.getIntensity() < intensity)
				return false;
			break;
		case LOWER_OR_EQUAL:
			if (spectrum.getIntensity() <= intensity)
				return false;
			break;
		default:
			return true;
		}
		return true;
	}

	@Override
	public String getFullDescription() {
		return "###Parameters used for Fragment Intensity Filter : \n" + "###Intensity : " + getIntensityPrecursor() + " ; " + "Comparator : " + getComparator() + "\n";
	}

	public int getIntensityPrecursor() {
		return intensity;
	}

	public ComparisonTypes getComparator() {
		return comparator;
	}


}