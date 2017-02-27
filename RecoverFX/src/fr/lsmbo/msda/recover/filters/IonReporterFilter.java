package fr.lsmbo.msda.recover.filters;


import fr.lsmbo.msda.recover.lists.IonReporters;
import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.IonReporter;
import fr.lsmbo.msda.recover.model.Spectrum;

public class IonReporterFilter implements BasicFilter{

	private String name;
	private Float moz;
	private Float tolerance;
	private Boolean isUsed = false;
	private Boolean[] associatedSpectrum = new Boolean[Spectra.getSpectraAsObservable().size()];
	private int id = 6;
	
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
		String allIons = "";
		for (IonReporter ir : IonReporters.getIonReporters()){
			allIons += "###" + ir.toString() + "\n";
		}
			
		return "###Ion Reporter Filter used with : " + IonReporters.getIonReporters().size() + " ion(s) reporter." + "\n" + allIons; 
	}
	@Override
	public Boolean getIsUsed(){
		return isUsed;
	}
	
	public void setIsUsed(Boolean _isUsed){
		this.isUsed = _isUsed ;
	}
	
	public Boolean[] getAssociatedSpectrum(){
		return associatedSpectrum;
	}
	
	public void setAssociatedSpectrum(Boolean[] associatedSpectrum){
		this.associatedSpectrum = associatedSpectrum;
	}
	
	public void addRecover(Boolean bool, int i){
		associatedSpectrum[i] = bool;
	}
	
	public int getId(){
		return id;
	}
}
