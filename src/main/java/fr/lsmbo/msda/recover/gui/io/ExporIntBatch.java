
package fr.lsmbo.msda.recover.gui.io;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.google.jhsheets.filtered.operators.BooleanOperator;
import org.google.jhsheets.filtered.operators.IFilterOperator.Type;

import fr.lsmbo.msda.recover.gui.filters.FilterRequest;
import fr.lsmbo.msda.recover.gui.filters.Filters;
import fr.lsmbo.msda.recover.gui.lists.IdentifiedSpectra;
import fr.lsmbo.msda.recover.gui.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.gui.model.AppliedFilters;
import fr.lsmbo.msda.recover.gui.model.settings.SpectrumTitleSelector;
import fr.lsmbo.msda.recover.gui.view.model.ExportInBatchModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Export in batch. It let the user to choose to apply filters or not from a
 * JSON file or from current filters on all peaklists. Besides it helps the user
 * to filter out the identified spectra within titles selection template from
 * excel files.
 * 
 * @author Aromdhani
 * @author LOMBART.benjamin
 *
 */
public class ExporIntBatch {
	private static final Logger logger = LogManager.getLogger(ExporIntBatch.class);
	public static Boolean useBatchSpectra = false;

	/**
	 * Run the export in batch task. It will apply/or not the filters on all
	 * peaklists and filter out the identified spectra within titles selection
	 * template from excel files.
	 * 
	 * @param exportInBatchModel
	 *            The properties of export in batch model: output directory ,
	 *            map of peak list files, identified spectra and the template of
	 *            spectrum title selection from an excel file .
	 * 
	 */
	public void run(ExportInBatchModel exportInBatchModel) {
		// Set batch mode to true
		useBatchSpectra = true;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		// Load filters from a JSON file
		if (exportInBatchModel.getAppliedFilters().equals(AppliedFilters.LOADED)) {
			try {
				FilterReaderJson.load(exportInBatchModel.getJsonFile());
				Filters.addAll(FilterReaderJson.getFiltersByNameMap());
			} catch (Exception e) {
				logger.error("Error while trying to load JSON file!", e);
			}
		}
		exportInBatchModel.getIdentifiedSpectraByPeakList().forEach((peakListFile, identifiedSpectraFile) -> {
			try {
				// Step 1: load peaklist file
				if (peakListFile != null && peakListFile.exists()) {
					System.out.println("INFO - Loading peaklist file: " + peakListFile.getPath() + " ...");
					logger.info("Loading peaklist file: {} ...", peakListFile.getPath());
					PeaklistReader.load(peakListFile);
					// Step 2: get identified spectra
					if (identifiedSpectraFile != null && identifiedSpectraFile.exists()) {
						System.out.println(
								"INFO - Getting identified spectra from: " + identifiedSpectraFile.getPath() + " ...");
						logger.info("Getting identified spectra from: {} ...", identifiedSpectraFile.getPath());
						System.out.println(exportInBatchModel.getSpectrumTitleSelection().toString());
						SpectrumTitleSelector spectrumTitleSelector = exportInBatchModel.getSpectrumTitleSelection();
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
							System.out.println("INFO - Filter out identified spectra ...");
							logger.info("Filter out identified spectra ...");
							final ObservableList<BooleanOperator> filters = FXCollections.observableArrayList();
							BooleanOperator booleanOperator = new BooleanOperator(Type.FALSE, false);
							filters.add(booleanOperator);
							FilterRequest.filterIdentifiedColumn(
									ListOfSpectra.getBatchSpectra().getSpectraAsObservable(), filters);
							System.out
									.println("INFO - " + ListOfSpectra.getBatchSpectra().getSpectraAsObservable().size()
											+ " spectra left after filtering out identified spectra.");
							logger.info(ListOfSpectra.getBatchSpectra().getSpectraAsObservable().size()
									+ " spectra left after filtering out identified spectra.");
						}
					}
					// Step 3: apply current filters or loaded filters.
					if (!exportInBatchModel.getAppliedFilters().equals(AppliedFilters.NONE)) {
						System.out.println("INFO - Applying filters ...");
						logger.info("Applying filters ...");
						FilterRequest.applyAll(ListOfSpectra.getBatchSpectra().getSpectraAsObservable());
						ListOfSpectra.getBatchSpectra().getSpectraAsObservable()
								.forEach(spectrum -> spectrum.setIsRecovered(true));
						System.out.println("INFO - " + ListOfSpectra.getBatchSpectra().getSpectraAsObservable().size()
								+ " spectra left after applying filters.");
						logger.info(ListOfSpectra.getBatchSpectra().getSpectraAsObservable().size()
								+ " spectra left after applying filters.");
					}
					// Step 4: export file
					File newFile = new File(exportInBatchModel.getOutputDirectory() + File.separator
							+ dateFormat.format(date) + "_" + peakListFile.getName());
					if (newFile.createNewFile()) {
						PeaklistWriter.setFileReader(peakListFile);
						System.out.println("INFO - Exporting peaklist file " + newFile.getCanonicalPath() + " ...");
						logger.info("Exporting peaklist file {} ...", newFile.getCanonicalPath());
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
