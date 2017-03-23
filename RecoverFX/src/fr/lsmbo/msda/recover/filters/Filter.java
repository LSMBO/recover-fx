package fr.lsmbo.msda.recover.filters;

import fr.lsmbo.msda.recover.lists.Filters;
import fr.lsmbo.msda.recover.lists.IonReporters;
import fr.lsmbo.msda.recover.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.IonReporter;
import fr.lsmbo.msda.recover.model.Spectrum;
import fr.lsmbo.msda.recover.model.StatusFilterType;


/**
 * Calculate and apply different filters to spectra. Recover filters used and
 * scan all the spectrum to apply these filters: For a spectrum if the first
 * filter return a false value for recover, we check the next spectrum. If the
 * filter return a true value for recover, we check the second filter, then the
 * third etc until the last filter or a false value for recover Reset values
 * (recover, UPN, filters used).
 * 
 * @author BL
 * @see FragmentIntensityFilter, HighIntensityThreasholdFilter,
 *      IdentifiedSpectraFilter, IonReporterFilter,
 *      LowIntensityThreasholdFilter, WrongChargeFilter
 * @see Filters
 *
 */
public class Filter {

	private HighIntensityThreasholdFilter filterHIT = (HighIntensityThreasholdFilter) Filters.getFilters().get("HIT");
	private LowIntensityThreasholdFilter filterLIT = (LowIntensityThreasholdFilter) Filters.getFilters().get("LIT");
	private FragmentIntensityFilter filterFI = (FragmentIntensityFilter) Filters.getFilters().get("FI");
	private WrongChargeFilter filterWC = (WrongChargeFilter) Filters.getFilters().get("WC");
	private IdentifiedSpectraFilter filterIS = (IdentifiedSpectraFilter) Filters.getFilters().get("IS");
	private IonReporterFilter filterIR = (IonReporterFilter) Filters.getFilters().get("IR");

	/**
	 * First of all, check if the filter identified spectra is used and apply
	 * this filter: scan all the title given and find the associated spectrum
	 * for this title
	 * 
	 * @see IdentifiedSpectraFilter
	 * 
	 *      Then, scan the different spectrum and for all this spectrum,scan the
	 *      different filter used (as an array of index, each index
	 *      corresponding to a specific filter @see Filters). First filter
	 *      encountered set the value of recover for this spectrum. If there are
	 *      more than one filter, before applied the second filter check if the
	 *      first filter return false for the spectrum (in this case, move to
	 *      the next spectrum) If the value was true: apply this filter (true or
	 *      false) and do it again for the third or more filter(check the value
	 *      etc...)
	 * 
	 *      If IonReporterFilter was used: special treatment (method
	 *      recoverIfSeveralIons) because if a first ion reporter return a value
	 *      of recover true we keep this value even if we have more ion reporter
	 *      And finally, calculate number of spectrum recovered
	 * 
	 */
	public void applyFilters() {
		Spectra spectra = ListOfSpectra.getFirstSpectra();

		Integer nb = spectra.getSpectraAsObservable().size();

		// TODO find a way to get all methods from this package
		// Scan all the spectrum
		for (int i = 0; i < nb; i++) {
			Spectrum spectrum = spectra.getSpectraAsObservable().get(i);

			// Scan all the filter used
			for (int j = 0; j < Filters.getFilterAsAnArray().size(); j++) {
				// First filter encountered
				if (j == 0) {
					// filter HIT
					if (Filters.getFilterAsAnArray().get(j) == 0) {
						spectrum.setIsRecover(filterHIT.isValid(spectrum));
						// // save information about this filter for this
						// spectrum
						// if (filterHIT.isValid(spectrum))
						// spectrum.setIsRecoverHIT(StatusFilterType.TRUE);
						// else
						// spectrum.setIsRecoverHIT(StatusFilterType.FALSE);
					}

					if (Filters.getFilterAsAnArray().get(j) == 1) {
						spectrum.setIsRecover(filterLIT.isValid(spectrum));
						// if (filterLIT.isValid(spectrum))
						// spectrum.setIsRecoverLIT(StatusFilterType.TRUE);
						// else
						// spectrum.setIsRecoverLIT(StatusFilterType.FALSE);
					}

					if (Filters.getFilterAsAnArray().get(j) == 2) {
						spectrum.setIsRecover(filterFI.isValid(spectrum));
						// if (filterFI.isValid(spectrum))
						// spectrum.setIsRecoverFI(StatusFilterType.TRUE);
						// else
						// spectrum.setIsRecoverFI(StatusFilterType.FALSE);
					}

					if (Filters.getFilterAsAnArray().get(j) == 3) {
						spectrum.setIsRecover(filterWC.isValid(spectrum));
						// if (filterWC.isValid(spectrum))
						// spectrum.setIsRecoverWC(StatusFilterType.TRUE);
						// else
						// spectrum.setIsRecoverWC(StatusFilterType.FALSE);
					}

					if (Filters.getFilterAsAnArray().get(j) == 4) {
						spectrum.setIsRecover(filterIS.isValid(spectrum));
						// if (filterIS.isValid(spectrum))
						// spectrum.setIsRecoverIS(StatusFilterType.TRUE);
						// else
						// spectrum.setIsRecoverIS(StatusFilterType.FALSE);
					}

					if (Filters.getFilterAsAnArray().get(j) == 5) {
						Integer nbIon = IonReporters.getIonReporters().size();
						for (int k = 0; k < nbIon; k++) {
							IonReporter ionReporter = IonReporters.getIonReporters().get(k);
							// Initialize parameter for an ion(i)
							filterIR.setParameters(ionReporter.getName(), ionReporter.getMoz(),
									ionReporter.getTolerance());

							if (k >= 1)
								spectrum.setIsRecover(recoverIfSeveralIons(spectrum, filterIR));
							else
								spectrum.setIsRecover(filterIR.isValid(spectrum));
						}
						// if(spectrum.getIsRecover())
						// spectrum.setIsRecoverIR(StatusFilterType.TRUE);
						// else
						// spectrum.setIsRecoverIR(StatusFilterType.FALSE);
					}
				}

				else {
					// If the previous filter return a false value for recover,
					// move to the next spectrum
					if (spectrum.getIsRecover() == false) {
						break;
					}
					if (spectrum.getIsRecover() == true) {
						// filter HIT
						if (Filters.getFilterAsAnArray().get(j) == 0) {
							spectrum.setIsRecover(filterHIT.isValid(spectrum));
							// if (filterHIT.isValid(spectrum))
							// spectrum.setIsRecoverHIT(StatusFilterType.TRUE);
							// else
							// spectrum.setIsRecoverHIT(StatusFilterType.FALSE);
						}

						if (Filters.getFilterAsAnArray().get(j) == 1) {
							spectrum.setIsRecover(filterLIT.isValid(spectrum));
							// if (filterLIT.isValid(spectrum))
							// spectrum.setIsRecoverLIT(StatusFilterType.TRUE);
							// else
							// spectrum.setIsRecoverLIT(StatusFilterType.FALSE);
						}

						if (Filters.getFilterAsAnArray().get(j) == 2) {
							spectrum.setIsRecover(filterFI.isValid(spectrum));
							// if (filterFI.isValid(spectrum))
							// spectrum.setIsRecoverFI(StatusFilterType.TRUE);
							// else
							// spectrum.setIsRecoverFI(StatusFilterType.FALSE);
						}

						if (Filters.getFilterAsAnArray().get(j) == 3) {
							spectrum.setIsRecover(filterWC.isValid(spectrum));
							// if (filterWC.isValid(spectrum))
							// spectrum.setIsRecoverWC(StatusFilterType.TRUE);
							// else
							// spectrum.setIsRecoverWC(StatusFilterType.FALSE);
						}

						if (Filters.getFilterAsAnArray().get(j) == 4) {
							spectrum.setIsRecover(filterIS.isValid(spectrum));
							// if (filterIS.isValid(spectrum))
							// spectrum.setIsRecoverIS(StatusFilterType.TRUE);
							// else
							// spectrum.setIsRecoverIS(StatusFilterType.FALSE);
						}

						if (Filters.getFilterAsAnArray().get(j) == 5) {
							Integer nbIon = IonReporters.getIonReporters().size();
							for (int k = 0; k < nbIon; k++) {
								IonReporter ionReporter = IonReporters.getIonReporters().get(k);
								// Initialize parameter for an ion(i)
								filterIR.setParameters(ionReporter.getName(), ionReporter.getMoz(),
										ionReporter.getTolerance());

								if (k >= 1)
									spectrum.setIsRecover(recoverIfSeveralIons(spectrum, filterIR));
								else
									spectrum.setIsRecover(filterIR.isValid(spectrum));
							}
							// if(spectrum.getIsRecover())
							// spectrum.setIsRecoverIR(StatusFilterType.TRUE);
							// else
							// spectrum.setIsRecoverIR(StatusFilterType.FALSE);
						}
					}
				}
			}
		}
		// Set the number of spectrum recover after utilization of filters
		spectra.checkRecoveredAndIdentifiedSpectra();
	}

	public void applyFiltersForOneSpectrum(Spectrum spectrum) {
		for (int j = 0; j < Filters.getFilterAsAnArray().size(); j++) {
			if (Filters.getFilterAsAnArray().get(j) == 0) {
				// save information about this filter for this spectrum
				if (filterHIT.isValid(spectrum))
					spectrum.setIsRecoverHIT(StatusFilterType.TRUE);
				else
					spectrum.setIsRecoverHIT(StatusFilterType.FALSE);
			}

			if (Filters.getFilterAsAnArray().get(j) == 1) {
				if (filterLIT.isValid(spectrum))
					spectrum.setIsRecoverLIT(StatusFilterType.TRUE);
				else
					spectrum.setIsRecoverLIT(StatusFilterType.FALSE);
			}

			if (Filters.getFilterAsAnArray().get(j) == 2) {
				if (filterFI.isValid(spectrum))
					spectrum.setIsRecoverFI(StatusFilterType.TRUE);
				else
					spectrum.setIsRecoverFI(StatusFilterType.FALSE);
			}

			if (Filters.getFilterAsAnArray().get(j) == 3) {
				if (filterWC.isValid(spectrum))
					spectrum.setIsRecoverWC(StatusFilterType.TRUE);
				else
					spectrum.setIsRecoverWC(StatusFilterType.FALSE);
			}

			if (Filters.getFilterAsAnArray().get(j) == 4) {
				if (filterIS.isValid(spectrum))
					spectrum.setIsRecoverIS(StatusFilterType.TRUE);
				else
					spectrum.setIsRecoverIS(StatusFilterType.FALSE);
			}

			if (Filters.getFilterAsAnArray().get(j) == 5) {
				Integer nbIon = IonReporters.getIonReporters().size();
				for (int k = 0; k < nbIon; k++) {
					IonReporter ionReporter = IonReporters.getIonReporters().get(k);
					// Initialize parameter for an ion(i)
					filterIR.setParameters(ionReporter.getName(), ionReporter.getMoz(), ionReporter.getTolerance());

					if (filterIR.isValid(spectrum)) {
						spectrum.setIsRecoverIR(StatusFilterType.TRUE);
						break;
					} else
						spectrum.setIsRecoverIR(StatusFilterType.FALSE);
				}
			}
		}
	}

	// tell if the spectrum is recovered or not
	public Boolean isRecover(Spectrum spectrum) {
		return true;
	}

	// returns a description of the filter's parameters (meant to be put in an
	// export)
	public String toString() {
		return "";
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
		if (spectrum.getIsRecover())
			return true;
		else if (filter.isValid(spectrum) == true)
			return true;
		else
			return false;
	}

	// reset value of recover and upn (in other term, redo the application of
	// filter from the beginning when a new window is open)
	public static void redoFromTheBeginning() {

		Spectra spectra = ListOfSpectra.getFirstSpectra();

		for (Spectrum sp : spectra.getSpectraAsObservable()) {
			sp.setIsRecover(false);
			sp.setUpn(-1);
			sp.setIsRecoverHIT(StatusFilterType.NOT_USED);
			sp.setIsRecoverLIT(StatusFilterType.NOT_USED);
			sp.setIsRecoverFI(StatusFilterType.NOT_USED);
			sp.setIsRecoverWC(StatusFilterType.NOT_USED);
			sp.setIsRecoverIS(StatusFilterType.NOT_USED);
			sp.setIsRecoverIR(StatusFilterType.NOT_USED);
		}
		Filters.resetHashMap();
	}

}
