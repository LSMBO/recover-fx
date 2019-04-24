
package fr.lsmbo.msda.recover.gui.filters;

import org.google.jhsheets.filtered.operators.BooleanOperator;
import org.google.jhsheets.filtered.operators.IFilterOperator;
import org.google.jhsheets.filtered.operators.NumberOperator;
import org.google.jhsheets.filtered.operators.StringOperator;

import fr.lsmbo.msda.recover.gui.io.ExportBatch;
import fr.lsmbo.msda.recover.gui.lists.IonReporters;
import fr.lsmbo.msda.recover.gui.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.gui.lists.Spectra;
import fr.lsmbo.msda.recover.gui.model.IonReporter;
import fr.lsmbo.msda.recover.gui.model.Spectrum;
import fr.lsmbo.msda.recover.gui.model.StatusFilterType;
import fr.lsmbo.msda.recover.gui.filters.ColumnFilters;

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
	 * Apply a list of filters for all spectrum.
	 * 
	 * @return <code>true</code> if all spectrum have been checked.
	 */
	public Boolean applyFilters() {
		Boolean isFinished = false;
		ColumnFilters.getApplied().forEach((k, v) -> {
			switch (k) {
			// Apply filter on flag column
			case "Flag": {
				for (Object filter : v) {
					IFilterOperator.Type type = ((BooleanOperator) filter).getType();
					Boolean value = ((BooleanOperator) filter).getValue();
				}
				break;
			}
			// Apply filter on Id column
			case "Id": {
				for (Object filter : v) {
					IFilterOperator.Type type = ((NumberOperator) filter).getType();
					Integer value = (Integer) ((NumberOperator) filter).getValue();
					
				}
				break;
			}
			// Apply filter on Title column
			case "Title": {
				for (Object filter : v) {
					IFilterOperator.Type type = ((StringOperator) filter).getType();
					String value = ((StringOperator) filter).getValue();
				}
				break;
			}
			// Apply filter on Mz column
			case "Mz": {
				for (Object filter : v) {
					IFilterOperator.Type type = ((NumberOperator) filter).getType();
					float value = (Float) ((NumberOperator) filter).getValue();
				}
				break;
			}
			// Apply filter on Intensity column
			case "Intensity": {
				for (Object filter : v) {
					IFilterOperator.Type type = ((NumberOperator) filter).getType();
					float value = (float) ((NumberOperator) filter).getValue();
				}
				break;
			}
			// Apply filter on Charge column
			case "Charge": {
				for (Object filter : v) {
					IFilterOperator.Type type = ((NumberOperator) filter).getType();
					Integer value = (Integer) ((NumberOperator) filter).getValue();
				}
				break;
			}
			// Apply filter on Retention Time column
			case "Retention Time": {
				for (Object filter : v) {
					IFilterOperator.Type type = ((NumberOperator) filter).getType();
					float value = (float) ((NumberOperator) filter).getValue();
				}
				break;
			}
			// Apply filter on Fragment number column
			case "Fragment number": {
				for (Object filter : v) {
					IFilterOperator.Type type = ((NumberOperator) filter).getType();
					Integer value = (Integer) ((NumberOperator) filter).getValue();
				}
				break;
			}
			// Apply filter on Max fragment intensity column
			case "Max fragment intensity": {
				for (Object filter : v) {
					IFilterOperator.Type type = ((NumberOperator) filter).getType();
					float value = (float) ((NumberOperator) filter).getValue();
				}
				break;
			}
			// Apply filter on UPN column
			case "UPN": {
				for (Object filter : v) {
					IFilterOperator.Type type = ((NumberOperator) filter).getType();
					Integer value = (Integer) ((NumberOperator) filter).getValue();
				}
				break;
			}
			// Apply filter on Identified column
			case "Identified": {
				for (Object filter : v) {
					IFilterOperator.Type type = ((BooleanOperator) filter).getType();
					Boolean value = ((BooleanOperator) filter).getValue();
				}
				break;
			}
			// Apply filter on Wrong charge column
			case "Ion Reporter": {
				for (Object filter : v) {
					IFilterOperator.Type type = ((BooleanOperator) filter).getType();
					Boolean value = ((BooleanOperator) filter).getValue();
				}
				break;
			}
			// Apply filter on Wrong charge column
			case "Wrong charge": {
				for (Object filter : v) {
					IFilterOperator.Type type = ((BooleanOperator) filter).getType();
					Boolean value = ((BooleanOperator) filter).getValue();
					System.out.println(type + "/" + value);
				}
				break;
			}

			default:
				break;
			}
		});
		return isFinished;
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
