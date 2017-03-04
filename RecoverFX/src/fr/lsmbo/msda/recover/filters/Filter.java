package fr.lsmbo.msda.recover.filters;


import fr.lsmbo.msda.recover.lists.Filters;
import fr.lsmbo.msda.recover.lists.IonReporters;
import fr.lsmbo.msda.recover.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.IonReporter;
import fr.lsmbo.msda.recover.model.Spectrum;
import javafx.scene.control.Alert;

public class Filter {

	private Integer nb = ListOfSpectra.getFirstSpectra().getSpectraAsObservable().size();
	
	private HighIntensityThreasholdFilter filterHIT = (HighIntensityThreasholdFilter) Filters.getFilters().get("HIT");
	private LowIntensityThreasholdFilter filterLIT = (LowIntensityThreasholdFilter) Filters.getFilters().get("LIT");
	private FragmentIntensityFilter filterFI = (FragmentIntensityFilter) Filters.getFilters().get("FI");
	private WrongChargeFilter filterWC = (WrongChargeFilter) Filters.getFilters().get("WC");
	private IdentifiedSpectraFilter filterIS = (IdentifiedSpectraFilter) Filters.getFilters().get("IS");
	private IonReporterFilter filterIR = (IonReporterFilter) Filters.getFilters().get("IR");

	
	public void applyFilters() {
		if(filterIS != null){
				for (String t : filterIS.getArrayTitles()){
					filterIS.setIdentified(t);
				}

		}
		
		// TODO find a way to get all methods from this package
		for (int i =0; i < nb; i++){
			Spectrum spectrum = ListOfSpectra.getFirstSpectra().getSpectraAsObservable().get(i);
			
			for(int j=0; j <Filters.getFilterAsAnArray().size();j++){
				//First filter encountered
				if (j==0){
					
					//filter HIT
					if(Filters.getFilterAsAnArray().get(j)==0){
						spectrum.setIsRecover(filterHIT.isValid(spectrum));
					}
					
					if(Filters.getFilterAsAnArray().get(j)==1){
						spectrum.setIsRecover(filterLIT.isValid(spectrum));
					}
					
					if(Filters.getFilterAsAnArray().get(j)==2){
						spectrum.setIsRecover(filterFI.isValid(spectrum));
					}
					
					if(Filters.getFilterAsAnArray().get(j)==3){
						spectrum.setIsRecover(filterWC.isValid(spectrum));
					}
					
					if(Filters.getFilterAsAnArray().get(j)==4){
						spectrum.setIsRecover(filterIS.isValid(spectrum));
					}
					
					if(Filters.getFilterAsAnArray().get(j)==5){
						Integer nbIon = IonReporters.getIonReporters().size();
						for (int k=0; k < nbIon; k++){
							IonReporter ionReporter = IonReporters.getIonReporters().get(k);
							//Initialize parameter for an ion(i)
							filterIR.setParameters(ionReporter.getName(),ionReporter.getMoz(),ionReporter.getTolerance());
							
							if(k>=1)
								spectrum.setIsRecover(recoverIfSeveralIons(spectrum, filterIR));
							else
								spectrum.setIsRecover(filterIR.isValid(spectrum));
						}
					}
				}
				
				else{
					
					if (spectrum.getIsRecover()==false)
						break;
					
					if (spectrum.getIsRecover()==true){
						//filter HIT
						if(Filters.getFilterAsAnArray().get(j)==0){
							spectrum.setIsRecover(filterHIT.isValid(spectrum));
						}
						
						if(Filters.getFilterAsAnArray().get(j)==1){
							spectrum.setIsRecover(filterLIT.isValid(spectrum));
						}
						
						if(Filters.getFilterAsAnArray().get(j)==2){
							spectrum.setIsRecover(filterFI.isValid(spectrum));
						}
						
						if(Filters.getFilterAsAnArray().get(j)==3){
							spectrum.setIsRecover(filterWC.isValid(spectrum));
						}
						
						if(Filters.getFilterAsAnArray().get(j)==4){
							Integer nbIon = IonReporters.getIonReporters().size();
							for (int k=0; k < nbIon; k++){
								IonReporter ionReporter = IonReporters.getIonReporters().get(k);
								//Initialize parameter for an ion(i)
								filterIR.setParameters(ionReporter.getName(),ionReporter.getMoz(),ionReporter.getTolerance());
								
								if(k>=1)
									spectrum.setIsRecover(recoverIfSeveralIons(spectrum, filterIR));
								else
									spectrum.setIsRecover(filterIR.isValid(spectrum));
							}
						}
					}
				}
			}
		}
		ListOfSpectra.getFirstSpectra().checkRecoveredSpectra();
	}
	
	// tell if the spectrum is recovered or not
	public Boolean isRecover(Spectrum spectrum) {
		return true;
	}
	
	// returns a description of the filter's parameters (meant to be put in an export)
	public String toString() {
		return "";
	}
	
	public Boolean recoverIfSeveralIons(Spectrum spectrum, BasicFilter filter){
		if (spectrum.getIsRecover())
			return true;
		else
			if (filter.isValid(spectrum) == true)
				return true;
			else
				return false;		
	}
	
	public static void redoFromTheBeginning(){
		for (Spectrum sp : ListOfSpectra.getFirstSpectra().getSpectraAsObservable()){
			sp.setIsRecover(false);
			sp.setUpn(-1);
		}
		Filters.resetHashMap();
	}
}

