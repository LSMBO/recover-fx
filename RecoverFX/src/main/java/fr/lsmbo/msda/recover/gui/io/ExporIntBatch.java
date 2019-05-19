
package fr.lsmbo.msda.recover.gui.io;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.lsmbo.msda.recover.gui.filters.FilterRequest;
import fr.lsmbo.msda.recover.gui.filters.Filters;
import fr.lsmbo.msda.recover.gui.lists.IdentifiedSpectra;
import fr.lsmbo.msda.recover.gui.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.gui.model.AppliedFilters;
import fr.lsmbo.msda.recover.gui.view.model.ExportInBatchProperty;

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
	 * Apply filters on all peak list files and filter out the identified spectra
	 * within titles selection template from an excel file..
	 * 
	 * @param identifiedSpectraByPeakList map of peak list files and identified
	 *                                    spectra files.
	 * @param outputDir                   the output directory where the output
	 *                                    files will be exported.
	 * @param type                        the type of applied filters.
	 * @param jsonFile                    the JSON file to load. The loaded filters
	 *                                    will be applied for all peak list files.
	 */
	public void run(ExportInBatchProperty exportInBatchProperty) {
		// Set batch mode to true
		useBatchSpectra = true;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		// Load filters from a JSON file
		if (exportInBatchProperty.getAppliedFilters().equals(AppliedFilters.LOADEDFILTERS)) {
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
						System.out.println(exportInBatchProperty.getSpectrumTitleRange().toString());
						IdentifiedSpectra identifiedSpectra = new IdentifiedSpectra();
						IdentifiedSpectraFromExcel identifiedSpectraExcel = new IdentifiedSpectraFromExcel();
						identifiedSpectraExcel.setIdentifiedSpectra(identifiedSpectra);
						if (identifiedSpectraFile != null && identifiedSpectraFile.exists()) {
							identifiedSpectraExcel.loadFromSelection(identifiedSpectraFile,
									exportInBatchProperty.getSpectrumTitleRange().getSheetName(),
									exportInBatchProperty.getSpectrumTitleRange().getColumn(),
									exportInBatchProperty.getSpectrumTitleRange().getRowNumber());
							for (String title : identifiedSpectra.getArrayTitles()) {
								identifiedSpectra.setIdentified(title);
							}
						}
					}
					// Step 3: apply filters
					if (!exportInBatchProperty.getAppliedFilters().equals(AppliedFilters.NONE)) {
						System.out.println("Info - Applying filters ...");
						FilterRequest.applyAll(ListOfSpectra.getBatchSpectra().getSpectraAsObservable());
						ListOfSpectra.getBatchSpectra().getSpectraAsObservable()
								.forEach(spectrum -> spectrum.setIsRecovered(true));
						System.out.println("Info - Spectra size after applying filters: "
								+ ListOfSpectra.getBatchSpectra().getSpectraAsObservable().size());
					}
					// Step 4: export file
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
