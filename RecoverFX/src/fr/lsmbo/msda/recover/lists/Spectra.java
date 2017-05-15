package fr.lsmbo.msda.recover.lists;

import fr.lsmbo.msda.recover.model.Spectrum;
import javafx.collections.FXCollections;
//import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * Regroup all spectrum as an ObservableList and make specific treatment like
 * add spectrum in the observable list, update retention time for all spectrum,
 * check the number of spectrum recovered, find a spectrum with its title.
 * 
 * @author BL
 *
 */
public class Spectra {

	private ObservableList<Spectrum> spectra = initialiseList();
	// private static LinkedHashMap<Integer, Spectrum> spectraPerId;
	private Integer nbSpectra = 0;
	private Integer nbRecover = 0;
	private Integer nbIdentified = 0;

	public Spectra() {
		super();
	}

	private ObservableList<Spectrum> initialiseList() {
		ObservableList<Spectrum> list = FXCollections.observableArrayList();
		// list.addListener(new ListChangeListener<Spectrum>() {
		// @Override
		// public void onChanged(javafx.collections.ListChangeListener.Change<?
		// extends Spectrum> c) {
		// System.out.println(c.toString());
		// }
		// });
		return list;
	}

	public void initialize() {
		if (!spectra.isEmpty())
			spectra.clear();
		// if(spectraPerId == null)
		// spectraPerId = new LinkedHashMap<Integer, Spectrum>();
		// else
		// spectraPerId.clear();
		nbSpectra = 0;
		nbRecover = 0;
		nbIdentified = 0;
	}

	public void addSpectrum(Spectrum spectrum) {
		// spectraPerId.put(spectrum.getId(), spectrum);
		spectra.add(spectrum);
		nbSpectra++;
	}

	// public static Spectrum get(Integer id) {
	// if(spectraPerId.containsKey(id))
	// return spectraPerId.get(id);
	// return null;
	// }

	public ObservableList<Spectrum> getSpectraAsObservable() {
		return spectra;
		// return FXCollections.observableArrayList(spectraPerId.values());
	}

	public void updateRetentionTimeFromTitle() {
		for (Spectrum spectrum : spectra) {
			spectrum.setRetentionTimeFromTitle();
		}
		// for(Integer id: spectraPerId.keySet()) {
		// spectraPerId.get(id).setRetentionTimeFromTitle();
		// }
	}

	/**
	 * Scan all the spectrum and increment the number of recover every time the
	 * value of recover for this spectrum will be true
	 */
	public void countRecoveredAndIdentifiedSpectra() {
		nbRecover = 0;
		nbIdentified = 0;
		Integer nb = getSpectraAsObservable().size();
		for (int i = 0; i < nb; i++) {
			Spectrum spectrum = getSpectraAsObservable().get(i);
			if (spectrum.getIsRecovered())
				nbRecover++;
			if (spectrum.getIsIdentified())
				nbIdentified++;
		}
	}

	// for(Integer id: spectraPerId.keySet()) {
	// Spectrum spectrum = spectraPerId.get(id);
	// int i = 0;
	// Boolean isRecover = true;
	// // check if recovered or not
	// while(isRecover && i < nbFilters) {
	// isRecover = filters.get(i++).isRecover(spectrum);
	// }
	// spectrum.setIsRecover(isRecover);
	// if(isRecover)
	// nbRecover++;
	// if(spectrum.getIsIdentified())
	// nbIdentified++;
	// }

	public Integer getNbSpectra() {
		return nbSpectra;
	}

	public Integer getNbRecover() {
		countRecoveredAndIdentifiedSpectra();
		return nbRecover;
	}

	public Integer getNbIdentified() {
		countRecoveredAndIdentifiedSpectra();
		return nbIdentified;
	}

	public void removeOne() {
		if (spectra.size() > 0)
			spectra.remove(0);
	}

	/**
	 * 
	 * @param title
	 *            title of spectrum we need to find
	 * @return Corresponding spectrum for this title
	 */
	public Spectrum getSpectrumWithTitle(String title) {
		Integer nb = getSpectraAsObservable().size();
		Spectrum specificSpectrum = null;
		for (int i = 0; i < nb; i++) {
			Spectrum spectrum = getSpectraAsObservable().get(i);
			if (spectrum.getTitle().equalsIgnoreCase(title)) {
				specificSpectrum = spectrum;
			}
		}

		return specificSpectrum;
	}

	public void resetRecover() {
		for (Spectrum sp : getSpectraAsObservable()) {
			sp.setIsRecovered(false);
			this.nbRecover = 0;
		}
	}

	public void resetIdentified() {
		for (Spectrum sp : getSpectraAsObservable()) {
			sp.setIsIdentified(false);
			this.nbIdentified = 0;
		}
	}

	public void resetCosTheta() {
		for (Spectrum sp : getSpectraAsObservable()) {
			sp.setCosThetha(0);
		}
	}

	public void setRecoverForFlaggedSpectrum() {
		for (Spectrum sp : getSpectraAsObservable()) {
			if(sp.getIsFlagged()){
				sp.setIsRecovered(true);
			}
		}
	}
}
