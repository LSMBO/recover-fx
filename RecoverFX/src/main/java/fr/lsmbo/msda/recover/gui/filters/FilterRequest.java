
package fr.lsmbo.msda.recover.gui.filters;

import java.util.ArrayList;
import java.util.List;

import org.google.jhsheets.filtered.operators.BooleanOperator;
import org.google.jhsheets.filtered.operators.NumberOperator;
import org.google.jhsheets.filtered.operators.StringOperator;

import fr.lsmbo.msda.recover.gui.io.ExportBatch;
import fr.lsmbo.msda.recover.gui.lists.IonReporters;
import fr.lsmbo.msda.recover.gui.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.gui.lists.Spectra;
import fr.lsmbo.msda.recover.gui.model.IonReporter;
import fr.lsmbo.msda.recover.gui.model.Spectrum;
import fr.lsmbo.msda.recover.gui.model.StatusFilterType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Compute and apply different filters to spectra. Recover filters used and scan
 * all the spectrum to apply these filters.
 * 
 * @author Aromdhani
 * 
 * @see IonReporterFilter
 * @see LowIntensityThresholdFilter
 * @see ColumnFilters
 *
 */
public class FilterRequest {

	private LowIntensityThresholdFilter filterLIT = null;
	private IdentifiedSpectraFilter filterIS = null;
	private IonReporterFilter filterIR = null;

	/**
	 * First of all, check if the filter identified spectra is used and apply
	 * this filter: scan all the title given and find the associated spectrum
	 * for this title. Then, scan the different spectrum and for all this
	 * spectrum,scan the different filter used (as an array of index, each index
	 * corresponding to a specific filter @see Filters). First filter
	 * encountered set the value of recover for this spectrum. If there are more
	 * than one filter, before applied the second filter check if the first
	 * filter return false for the spectrum (in this case, move to the next
	 * spectrum) If the value was true: apply this filter (true or false) and do
	 * it again for the third or more filter(check the value etc...)
	 * 
	 * If IonReporterFilter was used: special treatment (method
	 * recoverIfSeveralIons) because if a first ion reporter return a value of
	 * recover true we keep this value even if we have more ion reporter And
	 * finally, calculate number of spectrum recovered
	 * 
	 * @see IdentifiedSpectraFilter
	 * 
	 * 
	 * 
	 */
	public FilterRequest() {
		if (ColumnFilters.getApplied().containsKey("LIT"))
			this.filterLIT = (LowIntensityThresholdFilter) ColumnFilters.getApplied().get("LIT").get(0);
		if (ColumnFilters.getApplied().containsKey("IS"))
			this.filterIS = (IdentifiedSpectraFilter) ColumnFilters.getApplied().get("IS").get(0);
		if (ColumnFilters.getApplied().containsKey("IR"))
			this.filterIR = (IonReporterFilter) ColumnFilters.getApplied().get("IR").get(0);
	}

	/**
	 * Apply low intensity threshold filter for all spectrum.
	 * 
	 * @return <code>true</code> if all spectrum have been checked.
	 */
	public Boolean applyLIT() {
		Boolean isFinished = false;
		Spectra spectraToFilter = new Spectra();
		if (!ExportBatch.useBatchSpectra) {
			spectraToFilter = ListOfSpectra.getFirstSpectra();
		} else {
			spectraToFilter = ListOfSpectra.getBatchSpectra();
		}
		Integer numberOfSpectrum = spectraToFilter.getSpectraAsObservable().size();
		// Scan all the spectrum
		if (ColumnFilters.getApplied().containsKey("LIT")) {
			for (int i = 0; i < numberOfSpectrum; i++) {
				Spectrum spectrum = spectraToFilter.getSpectraAsObservable().get(i);
				spectrum.setIsRecovered(filterLIT.isValid(spectrum));
			}
			isFinished = true;
		}
		return isFinished;
	}

	/**
	 * Apply is identified filter for all spectrum.
	 * 
	 * @return <code>true</code> if all spectrum have been checked.
	 */
	public Boolean applyIS() {
		Boolean isFinished = false;
		Spectra spectraToFilter = new Spectra();
		if (!ExportBatch.useBatchSpectra) {
			spectraToFilter = ListOfSpectra.getFirstSpectra();
		} else {
			spectraToFilter = ListOfSpectra.getBatchSpectra();
		}
		Integer numberOfSpectrum = spectraToFilter.getSpectraAsObservable().size();
		// Scan all the spectrum
		if (ColumnFilters.getApplied().containsKey("IS")) {
			for (int i = 0; i < numberOfSpectrum; i++) {
				Spectrum spectrum = spectraToFilter.getSpectraAsObservable().get(i);
				spectrum.setIsRecovered(filterIS.isValid(spectrum));
			}
			isFinished = true;
		}
		return isFinished;
	}

	/**
	 * Apply ion reporter filter for all spectrum.
	 * 
	 * @return <code>true</code> if all spectrum have been checked.
	 */
	public Boolean applyIR() {
		Boolean isFinished = false;
		Spectra spectraToFilter = new Spectra();
		if (!ExportBatch.useBatchSpectra) {
			spectraToFilter = ListOfSpectra.getFirstSpectra();
		} else {
			spectraToFilter = ListOfSpectra.getBatchSpectra();
		}
		Integer numberOfSpectrum = spectraToFilter.getSpectraAsObservable().size();
		// Scan all the spectrum
		if (ColumnFilters.getApplied().containsKey("IR")) {
			for (int i = 0; i < numberOfSpectrum; i++) {
				Spectrum spectrum = spectraToFilter.getSpectraAsObservable().get(i);
				Integer nbIon = IonReporters.getIonReporters().size();
				for (int k = 0; k < nbIon; k++) {
					IonReporter ionReporter = IonReporters.getIonReporters().get(k);
					// Initialize parameter for an ion(i)
					filterIR.setParameters(ionReporter.getName(), ionReporter.getMoz(), ionReporter.getTolerance());

					if (k >= 1)
						spectrum.setIonReporter(recoverIfSeveralIons(spectrum, filterIR));
					else
						spectrum.setIonReporter(filterIR.isValid(spectrum));
				}
			}
			isFinished = true;
		}
		return isFinished;

	}

	/**
	 * Return the used spectra to apply filters
	 * 
	 * @return the spectra to apply filters
	 */

	private Spectra getSpectra() {
		Spectra spectraToFilter = new Spectra();
		if (!ExportBatch.useBatchSpectra) {
			spectraToFilter = ListOfSpectra.getFirstSpectra();
		} else {
			spectraToFilter = ListOfSpectra.getBatchSpectra();
		}
		return spectraToFilter;
	}

	/**
	 * Keep the original items and use a copy of items whenever a filter is
	 * invoked
	 * 
	 * @return The original items (first spectra as observable)
	 */
	private ObservableList<Spectrum> getItems() {
		final ObservableList<Spectrum> copiedItems = FXCollections
				.observableArrayList(ListOfSpectra.getFirstSpectra().getSpectraAsObservable());
		return copiedItems;
	}

	/**
	 * Filter column spectrum id
	 * 
	 * @param newData
	 *            the list of spectrum to filter
	 * @param filters
	 *            the ObservableList of filters to apply
	 */
	public void filterIdColumn(ObservableList<Spectrum> newData, ObservableList<NumberOperator<Integer>> filters) {
		final List<Spectrum> remove = new ArrayList<>();
		for (NumberOperator<Integer> filter : filters) {
			for (Spectrum item : newData) {
				if (filter.getType() == NumberOperator.Type.EQUALS) {
					if (item.getId() != filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.NOTEQUALS) {
					if (item.getId() == filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.GREATERTHAN) {
					if (item.getId() <= filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.GREATERTHANEQUALS) {
					if (item.getId() < filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.LESSTHAN) {
					if (item.getId() >= filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.LESSTHANEQUALS) {
					if (item.getId() > filter.getValue())
						remove.add(item);
				}
			}
		}
		newData.removeAll(remove);
	}

	/**
	 * Filter column spectrum moz
	 * 
	 * @param newData
	 *            the list of spectrum to filter
	 * @param filters
	 *            the ObservableList of filters to apply
	 */
	public void filterMzColumn(ObservableList<Spectrum> newData, ObservableList<NumberOperator<Float>> filters) {
		final List<Spectrum> remove = new ArrayList<>();
		for (NumberOperator<Float> filter : filters) {
			for (Spectrum item : newData) {
				if (filter.getType() == NumberOperator.Type.EQUALS) {
					if (item.getMz() != filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.NOTEQUALS) {
					if (item.getMz() == filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.GREATERTHAN) {
					if (item.getMz() <= filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.GREATERTHANEQUALS) {
					if (item.getMz() < filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.LESSTHAN) {
					if (item.getMz() >= filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.LESSTHANEQUALS) {
					if (item.getMz() > filter.getValue())
						remove.add(item);
				}
			}
		}
		newData.removeAll(remove);
	}

	/**
	 * Filter column spectrum title
	 * 
	 * @param newData
	 *            the list of spectrum to filter
	 * @param filters
	 *            the ObservableList of filters to apply
	 */
	public void filterTitleColumn(ObservableList<Spectrum> newData, ObservableList<StringOperator> filters) {
		final List<Spectrum> remove = new ArrayList<>();
		for (StringOperator filter : filters) {
			for (Spectrum item : newData) {
				if (filter.getType() == StringOperator.Type.EQUALS) {
					if (!item.getTitle().equals(filter.getValue()))
						remove.add(item);
				} else if (filter.getType() == StringOperator.Type.NOTEQUALS) {
					if (item.getTitle().equals(filter.getValue()))
						remove.add(item);
				} else if (filter.getType() == StringOperator.Type.CONTAINS) {
					if (!item.getTitle().contains(filter.getValue()))
						remove.add(item);
				} else if (filter.getType() == StringOperator.Type.STARTSWITH) {
					if (!item.getTitle().startsWith(filter.getValue()))
						remove.add(item);
				} else if (filter.getType() == StringOperator.Type.ENDSWITH) {
					if (!item.getTitle().endsWith(filter.getValue()))
						remove.add(item);
				}
			}
		}
		newData.removeAll(remove);
	}

	/**
	 * Filter column spectrum intensity
	 * 
	 * @param newData
	 *            the list of spectrum to filter
	 * @param filters
	 *            the ObservableList of filters to apply
	 */
	public void filterIntensityColumn(ObservableList<Spectrum> newData, ObservableList<NumberOperator<Float>> filters) {
		final List<Spectrum> remove = new ArrayList<>();
		for (NumberOperator<Float> filter : filters) {
			for (Spectrum item : newData) {
				if (filter.getType() == NumberOperator.Type.EQUALS) {
					if (item.getIntensity() != filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.NOTEQUALS) {
					if (item.getIntensity() == filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.GREATERTHAN) {
					if (item.getIntensity() <= filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.GREATERTHANEQUALS) {
					if (item.getIntensity() < filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.LESSTHAN) {
					if (item.getIntensity() >= filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.LESSTHANEQUALS) {
					if (item.getIntensity() > filter.getValue())
						remove.add(item);
				}
			}
		}
		newData.removeAll(remove);
	}

	/**
	 * Filter column spectrum charge
	 * 
	 * @param newData
	 *            the list of spectrum to filter
	 * @param filters
	 *            the ObservableList of filters to apply
	 */
	public void filterChargeColumn(ObservableList<Spectrum> newData, ObservableList<NumberOperator<Integer>> filters) {
		final List<Spectrum> remove = new ArrayList<>();
		for (NumberOperator<Integer> filter : filters) {
			for (Spectrum item : newData) {
				if (filter.getType() == NumberOperator.Type.EQUALS) {
					if (item.getCharge() != filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.NOTEQUALS) {
					if (item.getCharge() == filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.GREATERTHAN) {
					if (item.getCharge() <= filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.GREATERTHANEQUALS) {
					if (item.getCharge() < filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.LESSTHAN) {
					if (item.getCharge() >= filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.LESSTHANEQUALS) {
					if (item.getCharge() > filter.getValue())
						remove.add(item);
				}
			}
		}
		newData.removeAll(remove);
	}

	/**
	 * Filter column spectrum retention time
	 * 
	 * @param newData
	 *            the list of spectrum to filter
	 * @param filters
	 *            the ObservableList of filters to apply
	 */
	public void filterRTColumn(ObservableList<Spectrum> newData, ObservableList<NumberOperator<Float>> filters) {
		final List<Spectrum> remove = new ArrayList<>();
		for (NumberOperator<Float> filter : filters) {
			for (Spectrum item : newData) {
				if (filter.getType() == NumberOperator.Type.EQUALS) {
					if (item.getRetentionTime() != filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.NOTEQUALS) {
					if (item.getRetentionTime() == filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.GREATERTHAN) {
					if (item.getRetentionTime() <= filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.GREATERTHANEQUALS) {
					if (item.getRetentionTime() < filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.LESSTHAN) {
					if (item.getRetentionTime() >= filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.LESSTHANEQUALS) {
					if (item.getRetentionTime() > filter.getValue())
						remove.add(item);
				}
			}
		}
		newData.removeAll(remove);
	}

	/**
	 * Filter column spectrum number of fragments
	 * 
	 * @param newData
	 *            the list of spectrum to filter
	 * @param filters
	 *            the ObservableList of filters to apply
	 */
	public void filterNbrFrgsColumn(ObservableList<Spectrum> newData, ObservableList<NumberOperator<Integer>> filters) {
		final List<Spectrum> remove = new ArrayList<>();
		for (NumberOperator<Integer> filter : filters) {
			for (Spectrum item : newData) {
				if (filter.getType() == NumberOperator.Type.EQUALS) {
					if (item.getNbFragments() != filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.NOTEQUALS) {
					if (item.getNbFragments() == filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.GREATERTHAN) {
					if (item.getNbFragments() <= filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.GREATERTHANEQUALS) {
					if (item.getNbFragments() < filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.LESSTHAN) {
					if (item.getNbFragments() >= filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.LESSTHANEQUALS) {
					if (item.getNbFragments() > filter.getValue())
						remove.add(item);
				}
			}
		}
		newData.removeAll(remove);
	}

	/**
	 * Filter on Max fragment intensity.
	 * 
	 * @param newData
	 *            the list of spectrum to filter
	 * 
	 * @param filters
	 *            the ObservableList of filters to apply
	 */
	public void filterFIntensityColumn(ObservableList<Spectrum> newData,
			ObservableList<NumberOperator<Integer>> filters) {
		final List<Spectrum> remove = new ArrayList<>();
		for (NumberOperator<Integer> filter : filters) {
			for (Spectrum item : newData) {
				if (filter.getType() == NumberOperator.Type.EQUALS) {
					if (item.getFragmentMaxIntensity() != filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.NOTEQUALS) {
					if (item.getFragmentMaxIntensity() == filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.GREATERTHAN) {
					if (item.getFragmentMaxIntensity() <= filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.GREATERTHANEQUALS) {
					if (item.getFragmentMaxIntensity() < filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.LESSTHAN) {
					if (item.getFragmentMaxIntensity() >= filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.LESSTHANEQUALS) {
					if (item.getFragmentMaxIntensity() > filter.getValue())
						remove.add(item);
				}
			}
		}
		newData.removeAll(remove);
	}

	/**
	 * Filter on useful peaks number to keep over the threshold.
	 * 
	 * @param newData
	 *            the list of spectrum to filter.
	 * @param filters
	 *            the ObservableList of filters to apply
	 * 
	 */
	public void filterUPNColumn(ObservableList<Spectrum> newData, ObservableList<NumberOperator<Integer>> filters) {
		final List<Spectrum> remove = new ArrayList<>();
		for (NumberOperator<Integer> filter : filters) {
			for (Spectrum item : newData) {
				if (filter.getType() == NumberOperator.Type.EQUALS) {
					if (item.getUpn() != filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.NOTEQUALS) {
					if (item.getUpn() == filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.GREATERTHAN) {
					if (item.getUpn() <= filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.GREATERTHANEQUALS) {
					if (item.getUpn() < filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.LESSTHAN) {
					if (item.getUpn() >= filter.getValue())
						remove.add(item);
				} else if (filter.getType() == NumberOperator.Type.LESSTHANEQUALS) {
					if (item.getUpn() > filter.getValue())
						remove.add(item);
				}
			}
		}
		newData.removeAll(remove);
	}

	// TODO It will be removed
	/**
	 * Filter column spectrum whether is recover
	 * 
	 * @param newData
	 *            the list of spectrum to filter
	 * @param filters
	 *            the ObservableList of filters to apply
	 */
	public void filterIonReporterColumn(ObservableList<Spectrum> newData, ObservableList<BooleanOperator> filters) {
		// Here's an example of how you could filter the ID column
		final List<Spectrum> remove = new ArrayList<>();
		for (BooleanOperator filter : filters) {
			for (Spectrum item : newData) {
				if (filter.getType() == BooleanOperator.Type.TRUE) {
					if (!item.getIonReporter().getValue())
						remove.add(item);
				} else if (filter.getType() == BooleanOperator.Type.FALSE) {
					if (item.getIonReporter().getValue())
						remove.add(item);
				}
			}
		}
		newData.removeAll(remove);
	}

	/**
	 * Filter column spectrum whether is recover
	 * 
	 * @param newData
	 *            the list of spectrum to filter
	 * @param filters
	 *            the ObservableList of filters to apply
	 */
	public void filterFlaggedColumn(ObservableList<Spectrum> newData, ObservableList<BooleanOperator> filters) {
		final List<Spectrum> remove = new ArrayList<>();
		for (BooleanOperator filter : filters) {
			for (Spectrum item : newData) {
				if (filter.getType() == BooleanOperator.Type.TRUE) {
					if (!item.getIsFlaggedProperty().getValue())
						remove.add(item);
				} else if (filter.getType() == BooleanOperator.Type.FALSE) {
					if (item.getIsFlaggedProperty().getValue())
						remove.add(item);
				}
			}
		}
		newData.removeAll(remove);
	}

	/**
	 * Filter column spectrum whether is identified
	 * 
	 * @param newData
	 *            the list of spectrum to filter
	 * @param filters
	 *            the ObservableList of filters to apply
	 */
	public void filterIdentifiedColumn(ObservableList<Spectrum> newData, ObservableList<BooleanOperator> filters) {
		final List<Spectrum> remove = new ArrayList<>();
		for (BooleanOperator filter : filters) {
			for (Spectrum item : newData) {
				if (filter.getType() == BooleanOperator.Type.TRUE) {
					if (!item.getIsIdentified())
						remove.add(item);
				} else if (filter.getType() == BooleanOperator.Type.FALSE) {
					if (item.getIsIdentified())
						remove.add(item);
				}
			}
		}
		newData.removeAll(remove);
	}

	/**
	 * Filter column spectrum whether is a wrong charge
	 * 
	 * @param newData
	 *            the list of spectrum to filter
	 * @param filters
	 *            the ObservableList of filters to apply
	 */
	public void filterWrongChargeColumn(ObservableList<Spectrum> newData, ObservableList<BooleanOperator> filters) {
		final List<Spectrum> remove = new ArrayList<>();
		for (BooleanOperator filter : filters) {
			for (Spectrum item : newData) {
				if (filter.getType() == BooleanOperator.Type.TRUE) {
					if (!item.getWrongCharge().getValue())
						remove.add(item);
				} else if (filter.getType() == BooleanOperator.Type.FALSE) {
					if (item.getWrongCharge().getValue())
						remove.add(item);
				}
			}
		}
		newData.removeAll(remove);
	}

	/**
	 * Apply a list of stored filters to a spectra.
	 * 
	 * @return <code>true</code> if all spectrum have been checked.
	 */
	public ObservableList<Spectrum> applyFilters(ObservableList<Spectrum> newData) {
		ColumnFilters.getApplied().forEach((k, v) -> {
			switch (k) {
			// Apply filter on flag column
			case "Flag": {
				final ObservableList<BooleanOperator> filters = FXCollections.observableArrayList();
				for (Object filter : v) {
					filters.add((BooleanOperator) filter);
				}
				filterFlaggedColumn(newData, filters);
				break;
			}
			// Apply filter on Id column
			case "Id": {
				final ObservableList<NumberOperator<Integer>> filters = FXCollections.observableArrayList();
				for (Object filter : v) {
					filters.add((NumberOperator<Integer>) filter);
				}
				filterIdColumn(newData, filters);
				break;
			}
			// Apply filter on Title column
			case "Title": {
				final ObservableList<StringOperator> filters = FXCollections.observableArrayList();
				for (Object filter : v) {
					filters.add((StringOperator) filter);
				}
				filterTitleColumn(newData, filters);
				break;
			}
			// Apply filter on Mz column
			case "Mz": {
				final ObservableList<NumberOperator<Float>> filters = FXCollections.observableArrayList();
				for (Object filter : v) {
					filters.add((NumberOperator<Float>) filter);
				}
				filterMzColumn(newData, filters);
				break;
			}
			// Apply filter on Intensity column
			case "Intensity": {
				final ObservableList<NumberOperator<Float>> filters = FXCollections.observableArrayList();
				for (Object filter : v) {
					filters.add((NumberOperator<Float>) filter);
				}
				filterIntensityColumn(newData, filters);
				break;
			}
			// Apply filter on Charge column
			case "Charge": {
				final ObservableList<NumberOperator<Integer>> filters = FXCollections.observableArrayList();
				for (Object filter : v) {
					filters.add((NumberOperator<Integer>) filter);
				}
				filterChargeColumn(newData, filters);
				break;
			}
			// Apply filter on Retention Time column
			case "Retention Time": {
				final ObservableList<NumberOperator<Float>> filters = FXCollections.observableArrayList();
				for (Object filter : v) {
					filters.add((NumberOperator<Float>) filter);
				}
				filterRTColumn(newData, filters);
				break;
			}
			// Apply filter on Fragment number column
			case "Fragment number": {
				final ObservableList<NumberOperator<Integer>> filters = FXCollections.observableArrayList();
				for (Object filter : v) {
					filters.add((NumberOperator) filter);
				}
				filterNbrFrgsColumn(newData, filters);
				break;
			}
			// Apply filter on Max fragment intensity column
			case "Max fragment intensity": {
				final ObservableList<NumberOperator<Integer>> filters = FXCollections.observableArrayList();
				for (Object filter : v) {
					filters.add((NumberOperator) filter);
				}
				filterFIntensityColumn(newData, filters);
				break;
			}
			// Apply filter on UPN column
			case "UPN": {
				final ObservableList<NumberOperator<Integer>> filters = FXCollections.observableArrayList();
				for (Object filter : v) {
					filters.add((NumberOperator<Integer>) filter);
				}
				filterUPNColumn(newData, filters);
				break;

			}
			// Apply filter on Identified column
			case "Identified": {
				final ObservableList<BooleanOperator> filters = FXCollections.observableArrayList();
				for (Object filter : v) {
					filters.add((BooleanOperator) filter);
				}
				filterIdentifiedColumn(newData, filters);
				break;
			}
			// Apply filter on Wrong charge column
			case "Ion Reporter": {
				final ObservableList<BooleanOperator> filters = FXCollections.observableArrayList();
				for (Object filter : v) {
					filters.add((BooleanOperator) filter);
				}
				filterIonReporterColumn(newData, filters);
				break;
			}
			// Apply filter on Wrong charge column
			case "Wrong charge": {
				final ObservableList<BooleanOperator> filters = FXCollections.observableArrayList();
				for (Object filter : v) {
					filters.add((BooleanOperator) filter);
				}
				filterWrongChargeColumn(newData, filters);
				break;
			}
			default:
				break;
			}
		});
		return newData;
	}

	/**
	 * Determines whether the spectrum is recovered
	 * 
	 * @param spectrum
	 *            the spectrum to check
	 * @return <code> true</code> if the spectrum is recovered
	 */
	public Boolean isRecover(Spectrum spectrum) {
		return spectrum.getIsRecovered();
	}

	/**
	 * 
	 * @param spectrum
	 *            a specific spectrum
	 * @param filter
	 * @return if the value of recover for a spectrum is true, return true else,
	 *         check if an ion reporter is present for this spectrum and return
	 *         true or false in the different case.
	 * 
	 */
	public Boolean recoverIfSeveralIons(Spectrum spectrum, BasicFilter filter) {
		if (spectrum.getIsRecovered())
			return true;
		else
			return filter.isValid(spectrum);

	}

	/** Restore default values: reset the value of recover and UPN */
	public static void restoreDefaultValues() {
		Spectra spectra = ListOfSpectra.getFirstSpectra();
		for (Spectrum sp : spectra.getSpectraAsObservable()) {
			sp.setIsRecovered(false);
			sp.setUpn(-1);
			sp.setIsRecoverHIT(StatusFilterType.NOT_USED);
			sp.setIsRecoverLIT(StatusFilterType.NOT_USED);
			sp.setIsRecoverFI(StatusFilterType.NOT_USED);
			sp.setIsRecoverWC(StatusFilterType.NOT_USED);
			sp.setIsRecoverIS(StatusFilterType.NOT_USED);
			sp.setIsRecoverIR(StatusFilterType.NOT_USED);
		}

	}

}
