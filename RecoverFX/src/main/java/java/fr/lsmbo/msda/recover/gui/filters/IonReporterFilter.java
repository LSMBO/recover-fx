package fr.lsmbo.msda.recover.gui.filters;

import fr.lsmbo.msda.recover.gui.lists.IonReporters;
import fr.lsmbo.msda.recover.gui.model.IonReporter;
import fr.lsmbo.msda.recover.gui.model.Spectrum;

/**
 * Filter to keep specific spectrum according to specific ion. For a specific
 * m/z with a tolerance, check if the spectrum have this ion, in this case the
 * value for recover will be true.
 * 
 * @author BL
 *
 */
public class IonReporterFilter implements BasicFilter {

	private String name;
	private Float moz;
	private Float tolerance;

	public void setParameters(String name, Float moz, Float tolerance) {
		this.name = name;
		this.moz = moz;
		this.tolerance = tolerance;
	}

	@Override
	public Boolean isValid(Spectrum spectrum) {
		float mozMin = moz - tolerance;
		float mozMax = moz + tolerance;
		boolean ionReporterFound = false;

		for (int i = 0; i < spectrum.getNbFragments(); i++) {
			float mozFragment = spectrum.getFragments().get(i).getMz();

			if (mozFragment > mozMin && mozFragment < mozMax) {
				ionReporterFound = true;
				break;
			}
		}

		if (ionReporterFound)
			return true;
		return false;
	}

	@Override
	public String getFullDescription() {
		String allIons = "";
		for (IonReporter ir : IonReporters.getIonReporters()) {
			allIons += "###" + ir.toString() + "\n";
		}

		return "###Ion Reporter Filter used with : " + IonReporters.getIonReporters().size() + " ion(s) reporter."
				+ "\n" + allIons;
	}
	@Override
	public String getType() {
		return this.getClass().getName();
	}

}
