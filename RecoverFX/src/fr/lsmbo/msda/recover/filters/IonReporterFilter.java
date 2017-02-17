package fr.lsmbo.msda.recover.filters;


import fr.lsmbo.msda.recover.model.Spectrum;

public class IonReporterFilter implements BasicFilter{

	private String name;
	private Float moz;
	private Float tolerance;
	private Boolean isUsed = false;
	
	public void setParameters(String _name, Float _moz, Float _tolerance) {
		name = _name;
		moz = _moz ;
		tolerance = _tolerance ;
	}
	
	@Override
	public Boolean isValid(Spectrum spectrum) {
		float mozMin = moz - tolerance;
		float mozMax = moz + tolerance;
		boolean ionReporterFound = false;
		
		for(int i=0; i < spectrum.getNbFragments(); i++){
			float mozFragment = spectrum.getFragments().get(i).getMz();
//			System.out.println("nb fragment : "+ spectrum.getNbFragments() + " fragment : " + (i+1) + " mozFragment : " + mozFragment + " mozMin : " + mozMin + " mozMax : " + mozMax);
			if (mozFragment > mozMin && mozFragment < mozMax){
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
		return "";
	}
	@Override
	public Boolean getIsUsed(){
		return isUsed;
	}
	
	public void setIsUsed(Boolean _isUsed){
		this.isUsed = _isUsed ;
	}
}
