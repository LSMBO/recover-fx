
package fr.lsmbo.msda.recover.gui.io;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.DataValidationConstraint.OperatorType;
import org.google.jhsheets.filtered.operators.BooleanOperator;
import org.google.jhsheets.filtered.operators.IFilterOperator;
import org.google.jhsheets.filtered.operators.IFilterOperator.Type;

import fr.lsmbo.msda.recover.gui.filters.FilterRequest;
import fr.lsmbo.msda.recover.gui.filters.Filters;
import fr.lsmbo.msda.recover.gui.lists.IdentifiedSpectra;
import fr.lsmbo.msda.recover.gui.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.gui.model.AppliedFilters;
import fr.lsmbo.msda.recover.gui.model.settings.SpectrumTitleSelector;
import fr.lsmbo.msda.recover.gui.view.model.ExportInBatchProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Export in batch. It will apply the filters from a json file or to filter out
 * the identified spectra within titles selection template from an excel file.
 * 
 * @author Aromdhani
 * @author LOMBART.benjamin
 *
 */
public class ExporIntBatch {
	private static final Logger logger = LogManager.getLogger(ExporIntBatch.class);
	public static Boolean useBatchSpectra = false;

	/**
	 * Apply filters on all peak list files and filter out the identified
	 * spectra within titles selection template from an excel file..
	 * 
	 * @param exportInBatchProperty
	 *            The properties of export in batch : output directory , map of
	 *            peak list files and identified spectra and the template of
	 *            spectrum title selection from an excel file .
	 * 
	 */
	public void run(ExportInBatchProperty exportInBatchProperty) {
		// Set batch mode to true
		useBatchSpectra = true;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		// Load filters from a JSON file
		if (exportInBatchProperty.getAppliedFilters().equals(AppliedFilters.LOADED)) {
			try {
				FilterReaderJson.load(exportInBatchProperty.getJsonFile());
				Filters.addAll(FilterReaderJson.getFiltersByNameMap());
			} catch (Exception e) {
				logger.error("Error while trying to load JSON file!", e);
			}
		}
		exportInBatchProperty.getIdentifiedSpectraByPeakList().forEach((peakListFile, identifiedSpectraFile) -> {
			try {
				// Step 1: load peak list file
				if (peakListFile != null && peakListFile.exists()) {
					System.out.println("Info - Loading peaklist file: " + peakListFile.getPath() + " ...");
					logger.info("Loading peaklist file: {} ...", peakListFile.getPath());
					PeaklistReader.load(peakListFile);
					// Step 2: get identified spectra
					if (identifiedSpectraFile != null && identifiedSpectraFile.exists()) {
						System.out.println(
								"Info - Getting identified spectra from: " + identifiedSpectraFile.getPath() + " ...");
						logger.info("Getting identified spectra from: {} ...", identifiedSpectraFile.getPath());
						System.out.println(exportInBatchProperty.getSpectrumTitleSelection().toString());
						SpectrumTitleSelector spectrumTitleSelector = exportInBatchProperty.getSpectrumTitleSelection();
						assert spectrumTitleSelector != null : "Spectrum title selection must not be null nor empty!";
						IdentifiedSpectra identifiedSpectra = new IdentifiedSpectra();
						IdentifiedSpectraFromExcel identifiedSpectraExcel = new IdentifiedSpectraFromExcel();
						identifiedSpectraExcel.setIdentifiedSpectra(identifiedSpectra);
						if (identifiedSpectraFile != null && identifiedSpectraFile.exists()) {
							assert !spectrumTitleSelector.getSheetName()
									.isEmpty() : "Spectrum title selection sheet name must not be empty";
							assert !spectrumTitleSelector.getColumn()
									.isEmpty() : "Spectrum title selection column must not be empty";
							assert spectrumTitleSelector
									.getRowNumber() > 0 : "Spectrum title selection row number must not be under 0";
							identifiedSpectraExcel.loadSpecTitleSelection(identifiedSpectraFile,
									spectrumTitleSelector.getSheetName(), spectrumTitleSelector.getColumn(),
									spectrumTitleSelector.getRowNumber());
							for (String title : identifiedSpectra.getArrayTitles()) {
								identifiedSpectra.setIdentified(title);
							}
							// Filter out the identified spectra
							System.out.println("Info - Filter out identified spectra ...");
							logger.info("Filter out identified spectra ...");
							final ObservableList<BooleanOperator> filters = FXCollections.observableArrayList();
							BooleanOperator booleanOperator = new BooleanOperator(Type.FALSE, false);
							filters.add(booleanOperator);
							FilterRequest.filterIdentifiedColumn(
									ListOfSpectra.getBatchSpectra().getSpectraAsObservable(), filters);
							System.out
									.println("Info - " + ListOfSpectra.getBatchSpectra().getSpectraAsObservable().size()
											+ " spectra left after filtering out identified spectra.");
							logger.info(ListOfSpectra.getBatchSpectra().getSpectraAsObservable().size()
									+ " spectra left after filtering out identified spectra.");
						}
					}
					// Step 3: apply current filters or loaded filters.
					if (!exportInBatchProperty.getAppliedFilters().equals(AppliedFilters.NONE)) {
						System.out.println("Info - Applying filters ...");
						logger.info("Applying filters ...");
						FilterRequest.applyAll(ListOfSpectra.getBatchSpectra().getSpectraAsObservable());
						ListOfSpectra.getBatchSpectra().getSpectraAsObservable()
								.forEach(spectrum -> spectrum.setIsRecovered(true));
						System.out.println("Info - Spectra size after applying filters: "
								+ ListOfSpectra.getBatchSpectra().getSpectraAsObservable().size());
						logger.info("Spectra size after applying filters: "
								+ ListOfSpectra.getBatchSpectra().getSpectraAsObservable().size());
					}
					// Step 4: export file
					System.out.println("Info - Exporting peak list file...");
					logger.info("Exporting peak list file...");
					File newFile = new File(exportInBatchProperty.getOutputDirectory() + File.separator
							+ dateFormat.format(date) + "_" + peakListFile.getName());
					if (newFile.createNewFile()) {
						PeaklistWriter.setFileReader(peakListFile);
						PeaklistWriter.save(newFile);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		useBatchSpectra = false;
	}

}
