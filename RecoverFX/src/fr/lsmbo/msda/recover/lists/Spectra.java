package fr.lsmbo.msda.recover.lists;

import java.util.ArrayList;
//import java.util.LinkedHashMap;

import fr.lsmbo.msda.recover.filters.Filter;
import fr.lsmbo.msda.recover.model.Spectrum;
import javafx.collections.FXCollections;
//import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Spectra {
	
	private ObservableList<Spectrum> spectra = initialiseList();
//	private static LinkedHashMap<Integer, Spectrum> spectraPerId;
	private Integer nbSpectra = 0;
	private Integer nbRecover = 0;
	private Integer nbIdentified = 0;
	
	
	public Spectra(){
		super();
	}
	
	private ObservableList<Spectrum> initialiseList() {
		ObservableList<Spectrum> list = FXCollections.observableArrayList();
//		list.addListener(new ListChangeListener<Spectrum>() {
//			@Override
//			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Spectrum> c) {
//				System.out.println(c.toString());
//			}
//		});
		return list;
	}
	
	public void initialize() {
		if(!spectra.isEmpty())
			spectra.clear();
//		if(spectraPerId == null)
//			spectraPerId = new LinkedHashMap<Integer, Spectrum>();
//		else
//			spectraPerId.clear();
		nbSpectra = 0;
		nbRecover = 0;
		nbIdentified = 0;
	}

	public void add(Spectrum spectrum) {
//		spectraPerId.put(spectrum.getId(), spectrum);
		spectra.add(spectrum);
		nbSpectra++;
	}
	
//	public static Spectrum get(Integer id) {
//		if(spectraPerId.containsKey(id))
//			return spectraPerId.get(id);
//		return null;
//	}

	public ObservableList<Spectrum> getSpectraAsObservable() {
		return spectra;
//		return FXCollections.observableArrayList(spectraPerId.values());
	}
	
	public void updateRetentionTimeFromTitle() {
		for(Spectrum spectrum: spectra) {
			spectrum.setRetentionTimeFromTitle();
		}
//		for(Integer id: spectraPerId.keySet()) {
//			spectraPerId.get(id).setRetentionTimeFromTitle();
//		}
	}
	public void checkRecoveredSpectra(){
		nbRecover = 0;
		Integer nb = getSpectraAsObservable().size();
		for(int i = 0; i < nb; i++){
			Spectrum spectrum = getSpectraAsObservable().get(i);
			if (spectrum.getIsRecover() == true)
				nbRecover++;
		}
	}

//		for(Integer id: spectraPerId.keySet()) {
//			Spectrum spectrum = spectraPerId.get(id);
//			int i = 0;
//			Boolean isRecover = true;
//			// check if recovered or not
//			while(isRecover && i < nbFilters) {
//				isRecover = filters.get(i++).isRecover(spectrum);
//			}
//			spectrum.setIsRecover(isRecover);
//			if(isRecover)
//				nbRecover++;
//			if(spectrum.getIsIdentified())
//				nbIdentified++;
//		}

	
	public Integer getNbSpectra() {
		return nbSpectra;
	}

	public Integer getNbRecover() {
		return nbRecover;
	}

	public  Integer getNbIdentified() {
		return nbIdentified;
	}
	
	public void removeOne() {
		if(spectra.size() > 0)
			spectra.remove(0);
	}
	
	public Spectrum getSpectrumWithTitle(String title){
		Integer nb = getSpectraAsObservable().size();
		Spectrum specificSpectrum = null;
		for (int i = 0; i < nb; i++){
			Spectrum spectrum = getSpectraAsObservable().get(i);
			if(spectrum.getTitle().equalsIgnoreCase(title)){
				specificSpectrum = spectrum;
				System.out.println(specificSpectrum.getTitle());
			}
		}

		return specificSpectrum;
	}
}
