package fr.lsmbo.msda.recover.lists;

import fr.lsmbo.msda.recover.io.ExportBatch;
import fr.lsmbo.msda.recover.model.Spectrum;

public class IdentifiedSpectra {
	private String[] arrayTitles;
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

	public String[] getArrayTitles() {
		return arrayTitles;
	}

	public void setArrayTitles(String[] titles) {
		this.arrayTitles = titles;
	}
	
	
}
