package fr.lsmbo.msda.recover.lists;

import java.util.ArrayList;

import fr.lsmbo.msda.recover.io.ExportBatch;
import fr.lsmbo.msda.recover.model.Spectrum;

public class IdentifiedSpectra {
	private ArrayList<String> arrayTitles = new ArrayList<>();
	private Spectra spectra;

	// Find the spectrum with his title
	public void setIdentified(String title) {
		if(!ExportBatch.useBatchSpectra){
		spectra = ListOfSpectra.getFirstSpectra();
		} else {
			spectra = ListOfSpectra.getBatchSpectra();
		}
		
		Spectrum spectrum = spectra.getSpectrumWithTitle(title);
		
		if(spectrum != null){
		spectrum.setIsIdentified(true);
		}
	}

	public ArrayList<String> getArrayTitles() {
		return arrayTitles;
	}

	public void setArrayTitles(ArrayList<String> titles) {
		this.arrayTitles = titles;
	}
	
	public void addTitle(String title){
		arrayTitles.add(title);
	}
	
	public void addAllTitles(ArrayList<String> allTitles){
		arrayTitles.addAll(allTitles);
	}
	
	public void resetArrayTitles(){
		arrayTitles.clear();
	}
	

}
