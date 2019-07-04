
package fr.lsmbo.msda.recover.gui.filters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.google.jhsheets.filtered.FilteredTableView;
import org.google.jhsheets.filtered.operators.BooleanOperator;
import org.google.jhsheets.filtered.operators.NumberOperator;
import org.google.jhsheets.filtered.operators.StringOperator;
import org.google.jhsheets.filtered.tablecolumn.IFilterableTableColumn;

import fr.lsmbo.msda.recover.gui.io.ExporIntBatch;
import fr.lsmbo.msda.recover.gui.io.IdentifiedSpectraFromExcel;
import fr.lsmbo.msda.recover.gui.lists.IdentifiedSpectra;
import fr.lsmbo.msda.recover.gui.lists.IonReporters;
import fr.lsmbo.msda.recover.gui.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.gui.lists.Spectra;
import fr.lsmbo.msda.recover.gui.model.IonReporter;
import fr.lsmbo.msda.recover.gui.model.Spectrum;
import fr.lsmbo.msda.recover.gui.model.StatusFilterType;
import fr.lsmbo.msda.recover.gui.model.settings.SpectrumTitleSelector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

/**
 * Compute and apply different filters to spectra. This class scan all the
 * spectrum to apply the entered filters.
 * 
 * @author Aromdhani
 * 
 * @see IonReporterFilter
 * @see LowIntensityThresholdFilter
 * @see IdentifiedSpectraFilter
 *
 */
public class FilterRequest {

	private static final Logger logger = LogManager.getLogger(FilterRequest.class);

	private static LowIntensityThresholdFilter filterLIT = null;
	private static IdentifiedSpectraFilter filterIS = null;
	private static IonReporterFilter filterIR = null;

	/**
	 * Apply entered filters
	 * 
	 * @param newData
	 *            the data to filter out.
	 * @return the new data after applying all filters.
	 */
	public static ObservableList<Spectrum> applyAll(ObservableList<Spectrum> newData) {
		TreeMap<String, ObservableList<Object>> filtersByNameTreeMap = new TreeMap<>();
		filtersByNameTreeMap.putAll(Filters.getAll());
		filtersByNameTreeMap.forEach((name, appliedFilters) -> {
			switch (name) {
			// Apply filter on flag column
			case "Flag": {
				final ObservableList<BooleanOperator> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if (((BooleanOperator) filter) != null)
						filters.add((BooleanOperator) filter);
				}
				filterFlaggedColumn(newData, filters);
				break;
			}
			// Apply filter on Id column
			case "Id": {
				final ObservableList<NumberOperator<Integer>> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((NumberOperator<Integer>) filter != null)
						filters.add((NumberOperator<Integer>) filter);
				}
				filterIdColumn(newData, filters);
				break;
			}
			// Apply filter on Title column
			case "Title": {
				final ObservableList<StringOperator> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((StringOperator) filter != null)
						filters.add((StringOperator) filter);
				}
				filterTitleColumn(newData, filters);
				break;
			}
			// Apply filter on Mz column
			case "Mz": {
				final ObservableList<NumberOperator<Float>> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((NumberOperator<Float>) filter != null)
						filters.add((NumberOperator<Float>) filter);
				}
				filterMzColumn(newData, filters);
				break;
			}
			// Apply filter on Intensity column
			case "Intensity": {
				final ObservableList<NumberOperator<Float>> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((NumberOperator<Float>) filter != null)
						filters.add((NumberOperator<Float>) filter);
				}
				filterIntensityColumn(newData, filters);
				break;
			}
			// Apply filter on Charge column
			case "Charge": {
				final ObservableList<NumberOperator<Integer>> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((NumberOperator<Integer>) filter != null)
						filters.add((NumberOperator<Integer>) filter);
				}
				filterChargeColumn(newData, filters);
				break;
			}
			// Apply filter on Retention Time column
			case "Retention time": {
				final ObservableList<NumberOperator<Float>> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((NumberOperator<Float>) filter != null)
						filters.add((NumberOperator<Float>) filter);
				}
				filterRTColumn(newData, filters);
				break;
			}
			// Apply filter on Fragment number column
			case "Fragment number": {
				final ObservableList<NumberOperator<Integer>> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((NumberOperator<Integer>) filter != null)
						filters.add((NumberOperator<Integer>) filter);
				}
				filterNbrFrgsColumn(newData, filters);
				break;
			}
			// Apply filter on Max fragment intensity column
			case "Max fragment intensity": {
				final ObservableList<NumberOperator<Float>> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((NumberOperator<Float>) filter != null)
						filters.add((NumberOperator<Float>) filter);
				}
				filterFIntensityColumn(newData, filters);
				break;
			}
			// Apply filter on UPN column
			case "UPN": {
				final ObservableList<NumberOperator<Integer>> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((NumberOperator<Integer>) filter != null)
						filters.add((NumberOperator<Integer>) filter);
				}
				filterUPNColumn(newData, filters);
				break;
			}
			// Apply filter on Identified column
			case "Identified": {
				final ObservableList<BooleanOperator> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if (((BooleanOperator) filter) != null)
						filters.add((BooleanOperator) filter);
				}
				filterIdentifiedColumn(newData, filters);
				break;
			}
			// Apply filter on Wrong charge column
			case "Ion Reporter": {
				final ObservableList<BooleanOperator> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if (((BooleanOperator) filter) != null)
						filters.add((BooleanOperator) filter);
				}
				filterIonReporterColumn(newData, filters);
				break;
			}
			// Apply filter on Wrong charge column
			case "Wrong charge": {
				final ObservableList<BooleanOperator> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if (((BooleanOperator) filter) != null)
						filters.add((BooleanOperator) filter);
				}
				filterWrongChargeColumn(newData, filters);
				break;
			}
			case "LIT": {
				final ObservableList<LowIntensityThresholdFilter> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					filters.add((LowIntensityThresholdFilter) filter);
				}
				applyLIT();
				break;
			}
			// Apply ion reporter filter
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
	 * Apply ion reporter filter for all spectrum.
	 * 
	 * @return <code>true</code> if all spectrum have been checked.
	 */

	public static Boolean applyIR() {
		try {
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
			}
			return true;
		} catch (Exception e) {
			logger.error("Error while trying to apply ion reporter filter!", e);
			return false;
		}
	}

	/**
	 * Apply is identified spectra filter for all spectrum.
	 * 
	 * @return <code>true</code> if all spectrum have been checked.
	 */
	public static Boolean applyIS() {
		try {
			if (Filters.getAll().containsKey("IS")) {
				filterIS = (IdentifiedSpectraFilter) Filters.getAll().get("IS").get(0);
				assert filterIS != null : "The filter is idenified spectra must not be null";
				SpectrumTitleSelector specTitleParams = filterIS.getFileParams();
				IdentifiedSpectra identifiedSpectra = new IdentifiedSpectra();
				IdentifiedSpectraFromExcel identifiedSpectraExcel = new IdentifiedSpectraFromExcel();
				identifiedSpectraExcel.setIdentifiedSpectra(identifiedSpectra);
				File excelFile = new File(specTitleParams.getFilePath());
				if (excelFile != null && excelFile.exists()) {
					identifiedSpectraExcel.loadSpecTitleSelection(excelFile, specTitleParams.getSheetName(),
							specTitleParams.getColumn(), specTitleParams.getRowNumber());
					for (String title : identifiedSpectra.getArrayTitles()) {
						identifiedSpectra.setIdentified(title);
					}
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("Error while trying to apply is idenfied spectra filter!", e);
			return false;
		}

	}

	/**
	 * Apply low intensity threshold filter for all spectrum.
	 * 
	 * @return <code>true</code> if all spectrum have been checked.
	 */
	public static Boolean applyLIT() {
		try {
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
			}
			return true;
		} catch (Exception e) {
			logger.error("Error while trying to apply low intensity threshold filter!", e);
			return false;
		}
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
	 * Filter on Max fragment intensity.
	 * 
	 * @param newData
	 *            the list of spectrum to filter
	 * 
	 * @param filters
	 *            the ObservableList of filters to apply
	 */
	public static void filterFIntensityColumn(ObservableList<Spectrum> newData,
			ObservableList<NumberOperator<Float>> filters) {
		final List<Spectrum> remove = new ArrayList<>();
		for (NumberOperator<Float> filter : filters) {
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
	 * Return the used spectra to apply filters
	 * 
	 * @return the spectra to apply filters
	 */
	public static Spectra getSpectraTofilter() {
		Spectra spectraToFilter = new Spectra();
		if (!ExporIntBatch.useBatchSpectra) {
			spectraToFilter = ListOfSpectra.getFirstSpectra();
		} else {
			spectraToFilter = ListOfSpectra.getBatchSpectra();
		}
		return spectraToFilter;
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
	 * Update the table columns filters with current filters.
	 * 
	 * @see Filters
	 * 
	 * @param tableView
	 *            the table to update its columns filters
	 */
	public static void updateColumnFilters(FilteredTableView tableView) {
		TreeMap<String, ObservableList<Object>> filtersByNameTreeMap = new TreeMap<>();
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
				column.getFilters().setAll(filters);
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
				column.getFilters().setAll(filters);
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
				column.getFilters().setAll(filters);
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
				column.getFilters().setAll(filters);
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
				column.getFilters().setAll(filters);
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
				column.getFilters().setAll(filters);
				break;
			}
			// Apply filter on Retention Time column
			case "Retention time": {
				final IFilterableTableColumn column = getTableColumnByName(tableView, "Retention time");
				final ObservableList<NumberOperator<Float>> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					if ((NumberOperator<Float>) filter != null)
						filters.add((NumberOperator<Float>) filter);
				}
				column.getFilters().setAll(filters);
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
				column.getFilters().setAll(filters);
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
				column.getFilters().setAll(filters);
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
				column.getFilters().setAll(filters);
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
				column.getFilters().setAll(filters);
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
				column.getFilters().setAll(filters);
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
				column.getFilters().setAll(filters);
				break;
			}
			// Apply low intensity threshold filter
			case "LIT": {
				final ObservableList<LowIntensityThresholdFilter> filters = FXCollections.observableArrayList();
				for (Object filter : appliedFilters) {
					filters.add((LowIntensityThresholdFilter) filter);
				}
				applyLIT();
				break;
			}
			// Apply ion reporter filter
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

	}

	/**
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
	 * @return Ion reporter filter
	 */
	public IonReporterFilter getFilterIR() {
		return filterIR;
	}

	/**
	 * @return Is identified spectra filter
	 */
	public IdentifiedSpectraFilter getFilterIS() {
		return filterIS;
	}

	/**
	 * @return the low intensity threshold filter
	 */
	public LowIntensityThresholdFilter getFilterLIT() {
		return filterLIT;
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
	 * Restore default values of all spectra.
	 * 
	 */
	public void restoreDefaultValues() {
		Spectra spectra = null;
		if (!ExporIntBatch.useBatchSpectra) {
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
	 * @param filterIR
	 *            the ion reporter filter to set
	 */
	public void setFilterIR(IonReporterFilter filterIR) {
		FilterRequest.filterIR = filterIR;
	}

	/**
	 * @param filterIS
	 *            the is identified spectra filter to set
	 */
	public void setFilterIS(IdentifiedSpectraFilter filterIS) {
		FilterRequest.filterIS = filterIS;
	}

	/**
	 * @param filterLIT
	 *            the low intensity threshold filter to set
	 */
	public void setFilterLIT(LowIntensityThresholdFilter filterLIT) {
		FilterRequest.filterLIT = filterLIT;
	}

}
