
package fr.lsmbo.msda.recover.gui.filters;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.google.jhsheets.filtered.FilteredTableView;
import org.google.jhsheets.filtered.operators.BooleanOperator;
import org.google.jhsheets.filtered.operators.NumberOperator;
import org.google.jhsheets.filtered.operators.StringOperator;
import org.google.jhsheets.filtered.tablecolumn.IFilterableTableColumn;

import fr.lsmbo.msda.recover.gui.io.ExportBatch;
import fr.lsmbo.msda.recover.gui.lists.IonReporters;
import fr.lsmbo.msda.recover.gui.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.gui.lists.Spectra;
import fr.lsmbo.msda.recover.gui.model.IonReporter;
import fr.lsmbo.msda.recover.gui.model.Spectrum;
import fr.lsmbo.msda.recover.gui.model.StatusFilterType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

/**
 * Compute and apply different filters to spectra. Recover filters used and scan
 * all the spectrum to apply these filters.
 * 
 * @author Aromdhani
 * 
 * @see IonReporterFilter
 * @see LowIntensityThresholdFilter
 * @see Filters
 *
 */
public class FilterRequest {

	private static LowIntensityThresholdFilter filterLIT = null;
	private static IdentifiedSpectraFilter filterIS = null;
	private static IonReporterFilter filterIR = null;

	/**
	 * @return the filterLIT
	 */
	public LowIntensityThresholdFilter getFilterLIT() {
		return filterLIT;
	}

	/**
	 * @param filterLIT
	 *            the filterLIT to set
	 */
	public void setFilterLIT(LowIntensityThresholdFilter filterLIT) {
		FilterRequest.filterLIT = filterLIT;
	}

	/**
	 * @return the filterIS
	 */
	public IdentifiedSpectraFilter getFilterIS() {
		return filterIS;
	}

	/**
	 * @param filterIS
	 *            the filterIS to set
	 */
	public void setFilterIS(IdentifiedSpectraFilter filterIS) {
		FilterRequest.filterIS = filterIS;
	}

	/**
	 * @return the filterIR
	 */
	public IonReporterFilter getFilterIR() {
		return filterIR;
	}

	/**
	 * @param filterIR
	 *            the filterIR to set
	 */
	public void setFilterIR(IonReporterFilter filterIR) {
		FilterRequest.filterIR = filterIR;
	}

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
	 * @see LowIntensityThresholdFilter
	 * @see IonReporterFilter
	 * 
	 * 
	 */

	public FilterRequest() {
	}

	/**
	 * Return the used spectra to apply filters
	 * 
	 * @return the spectra to apply filters
	 */
	public static Spectra getSpectraTofilter() {
		Spectra spectraToFilter = new Spectra();
		if (!ExportBatch.useBatchSpectra) {
			spectraToFilter = ListOfSpectra.getFirstSpectra();
		} else {
			spectraToFilter = ListOfSpectra.getBatchSpectra();
		}
		return spectraToFilter;
	}

	/**
	 * Apply low intensity threshold filter for all spectrum.
	 * 
	 * @return <code>true</code> if all spectrum have been checked.
	 */
	public static Boolean applyLIT() {
		Boolean isFinished = false;
		Spectra spectraToFilter = getSpectraTofilter();
		Integer numberOfSpectrum = spectraToFilter.getSpectraAsObservable().size();
		if (Filters.getAll().containsKey("LIT")) {
			filterLIT = (LowIntensityThresholdFilter) Filters.getAll().get("LIT").get(0);
			assert filterLIT != null : "The filter low intensity threshold must not be null";
			// Scan all the spectrum
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
	public static Boolean applyIS() {
		Boolean isFinished = false;
		Spectra spectraToFilter = getSpectraTofilter();
		Integer numberOfSpectrum = spectraToFilter.getSpectraAsObservable().size();
		if (Filters.getAll().containsKey("IS")) {
			filterIS = (IdentifiedSpectraFilter) Filters.getAll().get("IS").get(0);
			assert filterIS != null : "The filter is idenified spectra must not be null";
			// Scan all the spectrum
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

	public static Boolean applyIR() {
		Boolean isFinished = false;
		Spectra spectraToFilter = getSpectraTofilter();
		Integer numberOfSpectrum = spectraToFilter.getSpectraAsObservable().size();
		// Scan all the spectrum
		if (Filters.getAll().containsKey("IR")) {
			filterIR = (IonReporterFilter) Filters.getAll().get("IR").get(0);
			assert filterIR != null : "The filter ion reporter spectra must not be null";
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
	 * Filter column spectrum id
	 * 
	 * @param newData
	 *            the list of spectrum to filter
	 * @param filters
	 *            the ObservableList of filters to apply
	 */
	public static void filterIdColumn(ObservableList<Spectrum> newData,
			ObservableList<NumberOperator<Integer>> filters) {
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
	public static void filterMzColumn(ObservableList<Spectrum> newData, ObservableList<NumberOperator<Float>> filters) {
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
	public static void filterTitleColumn(ObservableList<Spectrum> newData, ObservableList<StringOperator> filters) {
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
	public static void filterIntensityColumn(ObservableList<Spectrum> newData,
			ObservableList<NumberOperator<Float>> filters) {
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
	public static void filterChargeColumn(ObservableList<Spectrum> newData,
			ObservableList<NumberOperator<Integer>> filters) {
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
	public static void filterRTColumn(ObservableList<Spectrum> newData, ObservableList<NumberOperator<Float>> filters) {
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
	public static void filterNbrFrgsColumn(ObservableList<Spectrum> newData,
			ObservableList<NumberOperator<Integer>> filters) {
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
	public static void filterFIntensityColumn(ObservableList<Spectrum> newData,
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
	public static void filterUPNColumn(ObservableList<Spectrum> newData,
			ObservableList<NumberOperator<Integer>> filters) {
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

	/**
	 * Filter column spectrum whether is recover
	 * 
	 * @param newData
	 *            the list of spectrum to filter
	 * @param filters
	 *            the ObservableList of filters to apply
	 */
	public static void filterIonReporterColumn(ObservableList<Spectrum> newData,
			ObservableList<BooleanOperator> filters) {
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
	public static void filterFlaggedColumn(ObservableList<Spectrum> newData, ObservableList<BooleanOperator> filters) {
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
	public static void filterIdentifiedColumn(ObservableList<Spectrum> newData,
			ObservableList<BooleanOperator> filters) {
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
	public static void filterWrongChargeColumn(ObservableList<Spectrum> newData,
			ObservableList<BooleanOperator> filters) {
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
	 * Apply all stored filters to a spectra. the filters will be sorted in
	 * order to apply low intensity threshold filter(LIT) filter before useful
	 * peaks number (UPN) filter. The filter name used as default comparator
	 * 'String'
	 * 
	 * @return <code>true</code> if all spectrum have been checked.
	 */
	public static ObservableList<Spectrum> applyAllFilters(FilteredTableView tableView,
			ObservableList<Spectrum> newData) {

		TreeMap<String, ObservableList<Object>> filtersByNameTreeMap = new TreeMap<>();
		filtersByNameTreeMap.clear();
		filtersByNameTreeMap.putAll(Filters.getAll());
		filtersByNameTreeMap.forEach((name, appliedFilters) -> {
			switch (name) {
			// Apply filter on flag column
			case "Flag": {
				final IFilterableTableColumn column = getTableColumnByName(tableView, "Flag");
				final ObservableList<BooleanOperator> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if (((BooleanOperator) filter) != null)
						filters.add((BooleanOperator) filter);
				}
				if (!ExportBatch.useBatchSpectra) {
					column.getFilters().setAll(filters);
					filterFlaggedColumn(newData, filters);
				} else {
					filterFlaggedColumn(newData, filters);
				}
				break;
			}
			// Apply filter on Id column
			case "Id": {
				final IFilterableTableColumn column = getTableColumnByName(tableView, "Id");
				final ObservableList<NumberOperator<Integer>> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((NumberOperator<Integer>) filter != null)
						filters.add((NumberOperator<Integer>) filter);
				}
				if (!ExportBatch.useBatchSpectra) {
					column.getFilters().setAll(filters);
					filterIdColumn(newData, filters);
				} else {
					filterIdColumn(newData, filters);
				}
				break;
			}
			// Apply filter on Title column
			case "Title": {
				final IFilterableTableColumn column = getTableColumnByName(tableView, "Title");
				final ObservableList<StringOperator> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((StringOperator) filter != null)
						filters.add((StringOperator) filter);
				}
				if (!ExportBatch.useBatchSpectra) {
					column.getFilters().setAll(filters);
					filterTitleColumn(newData, filters);
				} else {
					filterTitleColumn(newData, filters);
				}
				break;
			}
			// Apply filter on Mz column
			case "Mz": {
				final IFilterableTableColumn column = getTableColumnByName(tableView, "Mz");
				final ObservableList<NumberOperator<Float>> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((NumberOperator<Float>) filter != null)
						filters.add((NumberOperator<Float>) filter);
				}
				if (!ExportBatch.useBatchSpectra) {
					column.getFilters().setAll(filters);
					filterMzColumn(newData, filters);
				} else {
					filterMzColumn(newData, filters);
				}
				break;
			}
			// Apply filter on Intensity column
			case "Intensity": {
				final IFilterableTableColumn column = getTableColumnByName(tableView, "Intensity");
				final ObservableList<NumberOperator<Float>> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((NumberOperator<Float>) filter != null)
						filters.add((NumberOperator<Float>) filter);
				}
				if (!ExportBatch.useBatchSpectra) {
					column.getFilters().setAll(filters);
					filterIntensityColumn(newData, filters);
				} else {
					filterIntensityColumn(newData, filters);
				}
				break;
			}
			// Apply filter on Charge column
			case "Charge": {
				final IFilterableTableColumn column = getTableColumnByName(tableView, "Charge");
				final ObservableList<NumberOperator<Integer>> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((NumberOperator<Integer>) filter != null)
						filters.add((NumberOperator<Integer>) filter);
				}
				if (!ExportBatch.useBatchSpectra) {
					column.getFilters().setAll(filters);
					filterChargeColumn(newData, filters);
				} else {
					filterChargeColumn(newData, filters);
				}
				break;
			}
			// Apply filter on Retention Time column
			case "Retention Time": {
				final IFilterableTableColumn column = getTableColumnByName(tableView, "Retention Time");
				final ObservableList<NumberOperator<Float>> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((NumberOperator<Float>) filter != null)
						filters.add((NumberOperator<Float>) filter);
				}
				if (!ExportBatch.useBatchSpectra) {
					column.getFilters().setAll(filters);
					filterRTColumn(newData, filters);
				} else {
					filterRTColumn(newData, filters);
				}
				break;
			}
			// Apply filter on Fragment number column
			case "Fragment number": {
				final IFilterableTableColumn column = getTableColumnByName(tableView, "Fragment number");
				final ObservableList<NumberOperator<Integer>> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((NumberOperator<Integer>) filter != null)
						filters.add((NumberOperator<Integer>) filter);
				}
				if (!ExportBatch.useBatchSpectra) {
					column.getFilters().setAll(filters);
					filterNbrFrgsColumn(newData, filters);
				} else {
					filterNbrFrgsColumn(newData, filters);
				}
				break;
			}
			// Apply filter on Max fragment intensity column
			case "Max fragment intensity": {
				final IFilterableTableColumn column = getTableColumnByName(tableView, "Max fragment intensity");
				final ObservableList<NumberOperator<Integer>> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((NumberOperator<Integer>) filter != null)
						filters.add((NumberOperator<Integer>) filter);
				}
				if (!ExportBatch.useBatchSpectra) {
					column.getFilters().setAll(filters);
					filterFIntensityColumn(newData, filters);
				} else {
					filterFIntensityColumn(newData, filters);
				}
				break;
			}
			// Apply filter on UPN column
			case "UPN": {
				final IFilterableTableColumn column = getTableColumnByName(tableView, "UPN");
				final ObservableList<NumberOperator<Integer>> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((NumberOperator<Integer>) filter != null)
						filters.add((NumberOperator<Integer>) filter);
				}
				if (!ExportBatch.useBatchSpectra) {
					column.getFilters().setAll(filters);
					filterUPNColumn(newData, filters);
				} else {
					filterUPNColumn(newData, filters);
				}
				break;

			}
			// Apply filter on Identified column
			case "Identified": {
				final IFilterableTableColumn column = getTableColumnByName(tableView, "Identified");
				final ObservableList<BooleanOperator> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if (((BooleanOperator) filter) != null)
						filters.add((BooleanOperator) filter);
				}
				if (!ExportBatch.useBatchSpectra) {
					column.getFilters().setAll(filters);
					filterIdentifiedColumn(newData, filters);
				} else {
					filterIdentifiedColumn(newData, filters);
				}
				break;
			}
			// Apply filter on Wrong charge column
			case "Ion Reporter": {
				final IFilterableTableColumn column = getTableColumnByName(tableView, "Ion Reporter");
				final ObservableList<BooleanOperator> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if (((BooleanOperator) filter) != null)
						filters.add((BooleanOperator) filter);
				}
				if (!ExportBatch.useBatchSpectra) {
					column.getFilters().setAll(filters);
					filterIonReporterColumn(newData, filters);
				} else {
					filterIonReporterColumn(newData, filters);
				}
				break;
			}
			// Apply filter on Wrong charge column
			case "Wrong charge": {
				final IFilterableTableColumn column = getTableColumnByName(tableView, "Wrong charge");
				final ObservableList<BooleanOperator> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if (((BooleanOperator) filter) != null)
						filters.add((BooleanOperator) filter);
				}
				if (!ExportBatch.useBatchSpectra) {
					column.getFilters().setAll(filters);
					filterWrongChargeColumn(newData, filters);
				} else {
					filterWrongChargeColumn(newData, filters);
				}
				break;
			}
			// Apply filter IdentifiedSpectraFilter
			case "IS": {
				final ObservableList<IdentifiedSpectraFilter> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					filters.add((IdentifiedSpectraFilter) filter);
				}
				applyIS();
				break;
			}
			// Apply filter LowIntensityThresholdFilter
			case "LIT": {
				final ObservableList<LowIntensityThresholdFilter> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					filters.add((LowIntensityThresholdFilter) filter);
				}
				applyLIT();
				break;
			}
			// Apply filter IonReporterFilter
			case "IR": {
				final ObservableList<IonReporterFilter> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					filters.add((IonReporterFilter) filter);
				}
				applyIR();
				break;
			}
			// Default
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
	public static Boolean recoverIfSeveralIons(Spectrum spectrum, BasicFilter filter) {
		if (spectrum.getIsRecovered())
			return true;
		else
			return filter.isValid(spectrum);

	}

	/**
	 * Restore default values of all spectra.
	 * 
	 */
	public void restoreDefaultValues() {
		Spectra spectra = null;
		if (!ExportBatch.useBatchSpectra) {
			spectra = ListOfSpectra.getFirstSpectra();
		} else {
			spectra = ListOfSpectra.getBatchSpectra();
		}
		for (Spectrum sp : spectra.getSpectraAsObservable()) {
			sp.setIsRecovered(false);
			sp.setIsIdentified(false);
			sp.setIonReporter(false);
			sp.setUpn(-1);
			sp.setIsRecoverLIT(StatusFilterType.NOT_USED);
			sp.setIsRecoverIS(StatusFilterType.NOT_USED);
			sp.setIsRecoverIR(StatusFilterType.NOT_USED);
		}
	}

	/**
	 * 
	 * @param tableView
	 *            The table view to get the columns
	 * @param name
	 *            The column name to search
	 * @return The table column
	 */
	private static <T> IFilterableTableColumn<?, ?> getTableColumnByName(FilteredTableView<T> tableView, String name) {
		for (TableColumn<T, ?> col : tableView.getColumns())
			if (col.getText().equals(name))
				return (IFilterableTableColumn<?, ?>) col;
		return null;
	}

}
