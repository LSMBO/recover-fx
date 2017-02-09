package fr.lsmbo.msda.recover.filters;


import fr.lsmbo.msda.recover.model.Spectrum;

public class IonReporterFilter {

	private String name;
	private Float moz;
	private Float tolerance;
	
	public void setParameters(String name, Float moz, Float tolerance) {

	}
	
	@Override
	public Boolean isValid(Spectrum spectrum) {

	}

	@Override
	public String getFullDescription() {
		return "";
	}

}
