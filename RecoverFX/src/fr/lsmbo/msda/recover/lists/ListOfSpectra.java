package fr.lsmbo.msda.recover.lists;

public class ListOfSpectra {

	private static Spectra firstSpectra = new Spectra();
	private static Spectra secondSpectra = new Spectra();
	
	private static Spectra[] arraySpectra = {firstSpectra,secondSpectra};
	
	
	public static Spectra getFirstSpectra(){
		return arraySpectra[0];
	}
	
	public static Spectra getSecondSpectra(){
		return arraySpectra[1];
	}
	
	public static void addFirstSpectra(Spectra spectra){
		arraySpectra[0] = spectra;
	}
	
	public static void addSecondSpectra(Spectra spectra){
		arraySpectra[1] = spectra;
	}
}
