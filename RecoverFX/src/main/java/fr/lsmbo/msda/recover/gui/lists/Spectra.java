/*
 * 
 */
package fr.lsmbo.msda.recover.gui.lists;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.function.Predicate;

import fr.lsmbo.msda.recover.gui.model.Spectrum;
import fr.lsmbo.msda.recover.gui.model.StatusFilterType;

/**
 * Regroup all spectrum as an ObservableList and make specific treatment: Add
 * spectrum in the observable list, update retention time for all spectrum,
 * check the number of spectrum recovered and find a spectrum with its title.
 * 
 * @author BL
 * @author aromdhani
 */
public class Spectra {

	private ObservableList<Spectrum> spectra = initialiseList();
	private Integer nbSpectra = 0;
	private Integer nbRecover = 0;
	private Integer nbIdentified = 0;
	private Integer nbMatched = 0;
	private Float percentageRecover = 0F;
	private Float percentageIdentified = 0F;

	/**
	 * Default constructor
	 */
	public Spectra() {
		super();
	}

	/**
	 * Initialize the list.
	 * 
	 * @return an empty observableArrayList
	 */
	private ObservableList<Spectrum> initialiseList() {
		ObservableList<Spectrum> list = FXCollections.observableArrayList();
		return list;
	}

	/**
	 * Initialize the spectra.
	 */
	public void initialize() {
		if (!spectra.isEmpty())
			spectra.clear();
		nbSpectra = 0;
		nbRecover = 0;
		nbIdentified = 0;
		percentageIdentified = 0F;
		percentageRecover = 0F;
		nbMatched = 0;
	}

	/**
	 * Add a spectrum
	 * 
	 * @param spectrum
	 *            the spectrum to add
	 */
	public void addSpectrum(Spectrum spectrum) {

		spectra.add(spectrum);
		nbSpectra++;
	}

	/**
	 * Return an observable list of spectra.
	 * 
	 * @return spectra
	 */
	public ObservableList<Spectrum> getSpectraAsObservable() {
		return spectra;
	}

	/**
	 * Update the retention time from the titles.
	 */
	public synchronized void updateRetentionTimeFromTitle() {
		for (Spectrum spectrum : spectra) {
			spectrum.setRetentionTimeFromTitle();
		}
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

	/**
	 * Return the number of spectra.
	 * 
	 * @return nbSpectra
	 */
	public Integer getNbSpectra() {
		return nbSpectra;
	}

	/**
	 * Return the number of recovered spectra.
	 * 
	 * @return nbRecover
	 */
	public Integer getNbRecover() {
		countRecoveredAndIdentifiedSpectra();
		return nbRecover;
	}

	/**
	 * Return the number of identified spectra.
	 * 
	 * @return nbIdentified
	 */
	public Integer getNbIdentified() {
		countRecoveredAndIdentifiedSpectra();
		return nbIdentified;
	}

	/**
	 * Remove the first spectra.
	 */
	public void removeOne() {
		if (spectra.size() > 0)
			spectra.remove(0);
	}

	/**
	 * 
	 * @param title
	 *            title of spectrum that we need to find
	 * @return specificSpectrum
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

	/**
	 * Compute the percentage of recovered spectrum
	 */
	private void computePercentageRecover() {
		if (nbSpectra != 0) {
			percentageRecover = (((float) nbRecover / (float) nbSpectra) * 100);
		}
	}

	/**
	 * Compute the percentage of identified spectrum
	 */
	private void computePercentageIdentified() {
		if (nbSpectra != 0) {
			percentageIdentified = (((float) nbIdentified / (float) nbSpectra) * 100);
		}

	}

	/**
	 * Return the percentage of recovered spectrum
	 */
	public Float getPercentageRecover() {
		computePercentageRecover();
		return percentageRecover;
	}

	/**
	 * Return the percentage of identified spectrum
	 */
	public Float getPercentageIdentified() {
		computePercentageIdentified();
		return percentageIdentified;
	}

	/**
	 * Reset the recovered spectrum
	 */
	public synchronized void resetRecover() {
		for (Spectrum sp : getSpectraAsObservable()) {
			sp.setIsRecovered(false);
			this.nbRecover = 0;
		}
	}

	/**
	 * Reset the identified spectrum
	 */
	public synchronized void resetIdentified() {
		for (Spectrum sp : getSpectraAsObservable()) {
			sp.setIsIdentified(false);
			this.nbIdentified = 0;
		}
	}

	/**
	 * Reset the Cos theta
	 */
	public synchronized void resetCosTheta() {
		for (Spectrum sp : getSpectraAsObservable()) {
			sp.setCosThetha(0);
		}
	}

	/**
	 * Set recovered for flagged spectrum
	 */
	public void setRecoverForFlaggedSpectrum() {
		for (Spectrum sp : getSpectraAsObservable()) {
			if (sp.getIsFlagged()) {
				sp.setIsRecovered(true);
			}
		}
	}

	/**
	 * Count the number of matched spectrum
	 */
	private void countNbMatched() {
		nbMatched = 0;
		for (Spectrum sp : getSpectraAsObservable()) {
			if (sp.getNbMatch() != 0) {
				nbMatched++;
			}
		}
	}

	/**
	 * Return the number of matched spectrum
	 */
	public Integer getNbMatched() {
		countNbMatched();
		return nbMatched;
	}

	/**
	 * Reset all filters
	 */
	public synchronized void resetFilters() {
		getSpectraAsObservable().stream().forEach(spectrum -> {
			spectrum.setIsRecoverLIT(StatusFilterType.NOT_USED);
			spectrum.setIsRecoverIS(StatusFilterType.NOT_USED);
			spectrum.setIsRecoverIR(StatusFilterType.NOT_USED);
		});
	}
}
