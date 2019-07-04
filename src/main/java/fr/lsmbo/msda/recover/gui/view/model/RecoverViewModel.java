package fr.lsmbo.msda.recover.gui.view.model;

import java.io.File;
import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.google.jhsheets.filtered.tablecolumn.IFilterableTableColumn;

import com.compomics.util.Export;
import com.compomics.util.enumeration.ImageType;

import fr.lsmbo.msda.recover.gui.IconResource.ICON;
import fr.lsmbo.msda.recover.gui.RecoverFx;
import fr.lsmbo.msda.recover.gui.Session;
import fr.lsmbo.msda.recover.gui.filters.FilterRequest;
import fr.lsmbo.msda.recover.gui.filters.Filters;
import fr.lsmbo.msda.recover.gui.io.ExporIntBatch;
import fr.lsmbo.msda.recover.gui.io.FilterWriterJson;
import fr.lsmbo.msda.recover.gui.io.PeaklistReader;
import fr.lsmbo.msda.recover.gui.io.PeaklistWriter;
import fr.lsmbo.msda.recover.gui.lists.IdentifiedSpectra;
import fr.lsmbo.msda.recover.gui.lists.IonReporters;
import fr.lsmbo.msda.recover.gui.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.gui.lists.ParsingRules;
import fr.lsmbo.msda.recover.gui.model.Spectrum;
import fr.lsmbo.msda.recover.gui.util.FileUtils;
import fr.lsmbo.msda.recover.gui.util.TaskRunner;
import fr.lsmbo.msda.recover.gui.view.MainView;
import fr.lsmbo.msda.recover.gui.view.SpectrumView;
import fr.lsmbo.msda.recover.gui.view.dialog.AboutDialog;
import fr.lsmbo.msda.recover.gui.view.dialog.ConfirmDialog;
import fr.lsmbo.msda.recover.gui.view.dialog.ExportInBatchDialog;
import fr.lsmbo.msda.recover.gui.view.dialog.FilterIonReporterDialog;
import fr.lsmbo.msda.recover.gui.view.dialog.FilterLoaderDialog;
import fr.lsmbo.msda.recover.gui.view.dialog.FilterViewerDialog;
import fr.lsmbo.msda.recover.gui.view.dialog.IdentifiedSpectraDialog;
import fr.lsmbo.msda.recover.gui.view.dialog.ParsingRulesDialog;
import fr.lsmbo.msda.recover.gui.view.dialog.ShowPopupDialog;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 * Creates and display the main view model. Defines UI actions.
 * 
 * @author Aromdhani
 * 
 */
public class RecoverViewModel {
	private static final Logger logger = LogManager.getLogger(RecoverViewModel.class);

	private static Stage stage;

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		RecoverViewModel.stage = stage;
	}

	private MainView view;

	private TaskRunner taskRunner;

	private ObservableList<Spectrum> items = FXCollections
			.observableArrayList(ListOfSpectra.getFirstSpectra().getSpectraAsObservable());

	public ObservableList<Spectrum> getItems() {
		return items;
	}

	public TaskRunner getTaskRunner() {
		return taskRunner;
	}

	public MainView getView() {
		return view;
	}

	/**
	 * Update the items in table view with the stored items.
	 * 
	 */
	public void initializeItems() {
		items.setAll(ListOfSpectra.getFirstSpectra().getSpectraAsObservable());
		view.getFilteredTable().refresh();
	}

	/**
	 * Determines whether the used spectra is not empty and there are a
	 * validated file to use.
	 * 
	 * @return <code>true</code> if the spectra is not empty otherwise
	 *         <code>false</code>.
	 */
	private Boolean isValidatedFirstSpectra() {
		return (Session.CURRENT_FILE != null && Session.CURRENT_FILE.exists()
				&& ListOfSpectra.getFirstSpectra().getSpectraAsObservable().size() > 0);
	}

	/**
	 * Load and extract spectra from Peaklist file.
	 * 
	 * @param file
	 *            the peaklist file to load.
	 */
	public void loadFile(File file) {
		taskRunner.doAsyncWork("Loading and extracting spectra from peaklist file", () -> {
			// Reset all column filters and customized filters
			Filters.resetAll();
			resetColumnFilters();
			// Initialize first spectra
			ListOfSpectra.getFirstSpectra().initialize();
			RecoverFx.useSecondPeaklist = false;
			Session.CURRENT_FILE = file;
			long startTime = System.currentTimeMillis();
			PeaklistReader.load(file);
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("INFO - loading time: " + (double) totalTime / 1000 + " sec");
			System.out.println("INFO - " + ListOfSpectra.getFirstSpectra().getNbSpectra() + " spectra found.");
			System.out.println("INFO - " + ListOfSpectra.getSecondSpectra().getNbSpectra() + " spectra found.");
			return file;
		}, (sucess) -> {
			logger.info("Loading and extracting spectra from peaklist file: {} has finished successfully!",
					file.getAbsolutePath());
			if (!RecoverFx.useSecondPeaklist) {
				updateJfx(() -> {
					items.setAll(ListOfSpectra.getFirstSpectra().getSpectraAsObservable());
					if (!items.isEmpty()) {
						updateChanges(items.get(0), ListOfSpectra.getFirstSpectra().getNbSpectra(), 0,
								Float.valueOf((items.size() / ListOfSpectra.getFirstSpectra().getNbSpectra()) * 100),
								ListOfSpectra.getFirstSpectra().getNbIdentified(),
								ListOfSpectra.getFirstSpectra().getPercentageIdentified());
						view.getFilteredTable().refresh();
					}
				});
			}
			// Enable second peaks list
			RecoverFx.useSecondPeaklist = true;
		}, (failure) -> {
			logger.error("Loading and extracting spectra from peaklist file has failed!");
			// Disable use second peaks list
			RecoverFx.useSecondPeaklist = false;
		}, true, stage);
	}

	/**
	 * Creates and displays a dialog about RecoverFx software: name, version and
	 * description.
	 * 
	 */
	public void onAboutRecoverFx() {
		AboutDialog aboutDialog = new AboutDialog();
		aboutDialog.showAndWait().ifPresent(RecoverFx -> {
			logger.info("About RecoverFx software {}", RecoverFx);
			System.out.println("INFO - About RecoverFx software: " + RecoverFx);
		});
	}

	/**
	 * Creates and display a dialog to add an ion reporter list. Apply ion
	 * reporter filter.
	 * 
	 * @see IonReporters
	 */
	public void onAddIonReporter() {
		if (isValidatedFirstSpectra()) {
			FilterIonReporterDialog filterDialog = new FilterIonReporterDialog();
			filterDialog.showAndWait().ifPresent(filter -> {
				taskRunner.doAsyncWork("Applying ion reporter filter", () -> {
					Boolean isFinished = FilterRequest.applyIR();
					return isFinished;
				}, (sucess) -> {
					logger.info(Filters.getFullDescription());
					System.out.println(Filters.getFullDescription());
					logger.info("Applying filter by ion reporter has finished successfully!");
					refresh();
				}, (failure) -> {
					logger.error("Applying filter by ion reporter has failed!");
				}, true, stage);
			});
		} else {
			logger.warn(
					"Spectra not found. Make sure that the file is not empty or a file were imported.\nPlease load a new file!");
			new ShowPopupDialog("Spectra not found",
					"Spectra not found. Make sure that the file is not empty or a file were imported.\nPlease load a new file!",
					stage);
		}
	}

	/**
	 * Apply low intensity threshold filter. The low intensity threshold filter
	 * use the emergence and the mode entered by the user as parameters.
	 * 
	 * @see FilterRequest
	 */
	public void onApplyLowIntThresholdFilter() {
		if (isValidatedFirstSpectra()) {
			taskRunner.doAsyncWork("Applying low intensity threshold filter", () -> {
				Boolean isFinished = FilterRequest.applyLIT();
				return isFinished;
			}, (sucess) -> {
				System.out.println(Filters.getFullDescription());
				logger.info(Filters.getFullDescription());
				logger.info("Applying filter lower intensity threshold has finished successfully!");
				refresh();
			}, (failure) -> {
				logger.error("Applying low intensity threshold filter has failed!");
			}, true, stage);
		} else {
			logger.warn(
					"Spectra not found. Make sure that the file is not empty or a file were imported.\nPlease load a new file!");
			new ShowPopupDialog("Spectra not found",
					"Spectra not found. Make sure that the file is not empty or a file were imported.\nPlease load a new file!",
					stage);
		}
	}

	/**
	 * Creates and displays parsing rules dialog. If a parsing rules is present.
	 * It will update the current parsing rules.
	 * 
	 * @see ParsingRules
	 */
	public void onEditParsingRules() {
		if (isValidatedFirstSpectra()) {
			ParsingRulesDialog parsingRulesDialog = new ParsingRulesDialog();
			parsingRulesDialog.showAndWait().ifPresent(selectedParsingRule -> {
				taskRunner.doAsyncWork("Editing parsing rules", () -> {
					ParsingRules.setNewCurrentParsingRule(selectedParsingRule);
					ListOfSpectra.getFirstSpectra().updateRetentionTimeFromTitle();
					return selectedParsingRule;
				}, (sucess) -> {
					logger.info("###{}", selectedParsingRule.getFullDescription());
					System.out.println("###" + selectedParsingRule.getFullDescription());
					refresh();
				}, (failure) -> {
					logger.error("Editing parsing rules has failed!");
				}, true, stage);
			});
		} else {
			logger.warn(
					"Spectra not found. Make sure that the file is not empty or a file were imported.\nPlease load a new file!");
			new ShowPopupDialog("Spectra not found",
					"Spectra not found. Make sure that the file is not empty or a file were imported.\nPlease load a new file!",
					stage);
		}
	}

	/**
	 * Exit RecoverFx software. This action will reset all values and close the
	 * window. Make sure to save before to exit the software.
	 * 
	 */
	public void onExit() {
		logger.warn("Exit recover-fx");
		System.out.println("WARN - Exit recover-fx");
		new ConfirmDialog<Object>(ICON.EXIT, "Exit recover-fx", "Are you sure you want to exit recover-fx ?", () -> {
			Platform.exit();
			System.exit(0);
			return null;
		}, stage);
	}

	/**
	 * Export peaks list file. Set all the left spectra after applying the
	 * filters as recover.
	 */
	public void onExportFile() {
		ObservableList<Spectrum> filteredItems = FXCollections.observableArrayList(view.getFilteredTable().getItems());
		logger.info("The filtered spectra number: {}", filteredItems.size());
		if (filteredItems.size() > 0) {
			FileUtils.exportPeakListAs(file -> {
				taskRunner.doAsyncWork("Exporting spectra to " + file.getName() + " file", () -> {
					long startTime = System.currentTimeMillis();
					ListOfSpectra.getFirstSpectra().getSpectraAsObservable().stream().parallel().forEach(spectrum -> {
						if (filteredItems.contains(spectrum))
							spectrum.setIsRecovered(true);
						else
							spectrum.setIsRecovered(false);
					});
					PeaklistWriter.save(file);
					long endTime = System.currentTimeMillis();
					long totalTime = endTime - startTime;
					logger.info("The file: {} has been exported in {}", file.getAbsolutePath(),
							(double) totalTime / 1000, " sec");
					System.out.println("INFO - The filtered spectra number: " + filteredItems.size() + ". The file: "
							+ file.getAbsolutePath() + " has been exported in: " + (double) totalTime / 1000 + " sec");
					return file;
				}, (sucess) -> {
					logger.info("Exporting file: {} has been exported successfully!", file.getAbsolutePath());
				}, (failure) -> {
					logger.error("Exporting file: {} has failed!", file.getAbsolutePath());
				}, true, stage);
			}, stage);
		} else {
			logger.warn("Empty spectra. The filtred spectra are empty. The exported file will be empty!");
			new ShowPopupDialog("Empty Spectra",
					"Empty spectra. The filtred spectra are empty.  The exported file will be empty!", stage);
		}
	}

	/**
	 * Export in batch all filters and identified spectra
	 * 
	 */

	public void onExportInBatch() {
		// Create export batch dialog
		ExportInBatchDialog exportInBatchDialog = new ExportInBatchDialog();
		exportInBatchDialog.showAndWait().ifPresent(exportInBatchProperty -> {
			if (!exportInBatchProperty.getIdentifiedSpectraByPeakList().keySet().isEmpty()) {
				taskRunner.doAsyncWork("Exporting in batch", () -> {
					long startTime = System.currentTimeMillis();
					logger.info(
							"Start exporting peaklists in batch. The number of file to proceed:{} .The output directory : {}. {}  will be applied.",
							exportInBatchProperty.getIdentifiedSpectraByPeakList().keySet().size(),
							exportInBatchProperty.getOutputDirectory().getPath(),
							exportInBatchProperty.getAppliedFilters().toString());
					System.out.println("INFO - Start exporting peaklists in batch. The number of file to proceed : "
							+ exportInBatchProperty.getIdentifiedSpectraByPeakList().keySet().size()
							+ ".\n The output directory: " + exportInBatchProperty.getOutputDirectory().getPath() + "\n"
							+ exportInBatchProperty.getAppliedFilters().toString() + " will be applied from: "
							+ exportInBatchProperty.getJsonFile());
					ExporIntBatch exportBatch = new ExporIntBatch();
					try {
						exportBatch.run(exportInBatchProperty);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					long endTime = System.currentTimeMillis();
					long totalTime = endTime - startTime;
					logger.info("Exporting in batch has finished in: {} ", (double) totalTime / 1000, " sec");
					System.out.println(
							"INFO - Exporting in batch has finished in: " + (double) totalTime / 1000 + " sec");
					return true;
				}, (sucess) -> {
					logger.info("Exporting in batch has been finished successfully!");
				}, (failure) -> {
					logger.error("Exporting in batch has failed!", failure.getMessage());
				}, true, stage);
			}
		});
	}

	/**
	 * Creates and displays a dialog to load the titles. It determines the
	 * identified spectra.
	 * 
	 * @see IdentifiedSpectra
	 */
	public void onGetIdentifiedSpectra() {
		if (isValidatedFirstSpectra()) {
			IdentifiedSpectraDialog identifiedSpectraDialog = new IdentifiedSpectraDialog();
			identifiedSpectraDialog.showAndWait().ifPresent(identifiedSpectra -> {
				taskRunner.doAsyncWork("Getting identified Spectra", () -> {
					Boolean isFinished = false;
					for (String title : ((IdentifiedSpectra) identifiedSpectra).getArrayTitles()) {
						((IdentifiedSpectra) identifiedSpectra).setIdentified(title);
					}
					isFinished = true;
					return isFinished;
				}, (sucess) -> {
					logger.info(Filters.getFullDescription());
					System.out.println(Filters.getFullDescription());
					logger.info("Getting identified spectra has finished successfully!");
					refresh();
				}, (failure) -> {
					logger.error("Getting identified spectra has failed!");
				}, true, stage);
			});
		} else {
			logger.info(
					"Spectra not found. Make sure that the file is not empty or a peak list file were imported.\nPlease load a new peak list file!");
			new ShowPopupDialog("Spectra not found",
					"Spectra not found. Make sure that the file is not empty or a peak list file were imported.\nPlease load a new peak list file!",
					stage);
		}
	}

	/**
	 * Load filters parameters from a JSON file, apply all filters on current
	 * spectra.
	 */
	public void onLoadFiltersFrmJsonFile() {
		FilterLoaderDialog FilterLoaderDialog = new FilterLoaderDialog();
		FilterLoaderDialog.showAndWait().ifPresent(filter -> {
			// Reset columns filters
			resetColumnFilters();
			initializeItems();
			taskRunner.doAsyncWork("Loading filter's settings from a JSON file", () -> {
				FilterRequest.applyAll(items);
				return true;
			}, (isSucceeded) -> {
				if (isSucceeded) {
					logger.info("Loading filter's settings from a JSON file has finished successfully!");
					updateJfx(() -> {
						FilterRequest.updateColumnFilters(view.getFilteredTable());
					});
					refresh();
				}
			}, (failure) -> {
				logger.error("Loading filter's settings from a JSON file has failed!", failure.getMessage());
			}, true, stage);
		});
	}

	/**
	 * Open and extract spectra from Peaklist file.
	 * 
	 * @param file
	 *            the Peaklist file to open.
	 */
	public void onOpenFile() {
		FileUtils.openPeakListFile(file -> {
			loadFile(file);
		}, stage);
	}

	/**
	 * Open the user guide file(RecoverFx_user_guide.pdf).
	 * 
	 */
	public void onOpenUserGuide() {
		try {
			logger.info("Open user guide file: RecoverFx_user_guide.pdf.");
			System.out.println("INFO - Open user guide file: RecoverFx_user_guide.pdf.");
			URI srcPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
			String path = new File(srcPath).getParent().replaceAll("\\\\", "/") + File.separator + "config"
					+ File.separator + "documentation" + File.separator + "RecoverFx_user_guide.pdf";
			FileUtils.showFile(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Error while trying to open user guide file: RecoverFx_user_guide.pdf", e);
		}
	}

	/**
	 * Reset filters; this action will restore the default values of filters,
	 * parsing rules to retrieve the RT from titles and update the view
	 * properties.
	 * 
	 */
	public void onResetFilters() {
		if (isValidatedFirstSpectra()) {
			// Reset columns filters
			resetColumnFilters();
			taskRunner.doAsyncWork("Reset all filters", () -> {
				// Reset all filters to default values.
				FilterRequest filetrRequest = new FilterRequest();
				IonReporters.getIonReporters().clear();
				Filters.resetAll();
				filetrRequest.restoreDefaultValues();
				return true;
			}, (sucess) -> {
				initializeItems();
				logger.error("Reset all filters has finished successfully!");
			}, (failure) -> {
				logger.error("Reset all filters has failed!");
			}, true, stage);
		} else {
			logger.warn(
					"Spectra not found. Make sure that the file is not empty or a peak list file were imported.\nPlease load a new peak list file!");
			new ShowPopupDialog("Spectra not found",
					"Spectra not found. Make sure that the file is not empty or a peak list file were imported.\nPlease load a new peak list file!",
					stage);
		}
	}

	/**
	 * Reset all flagged spectra . It helps the user to reset all flagged
	 * spectrums.
	 * 
	 */
	public void onResetFlagSpectrum() {
		if (isValidatedFirstSpectra()) {
			taskRunner.doAsyncWork("Reset flagged spectra", () -> {
				Boolean isFinished = false;
				ListOfSpectra.getFirstSpectra().getSpectraAsObservable().forEach(spectrum -> {
					if (spectrum.getIsFlagged())
						spectrum.setIsFlagged(false);
				});
				isFinished = true;
				return isFinished;
			}, (sucess) -> {
				logger.info("Reset flagged spectra has finished successfully!");
				refresh();
			}, (failure) -> {
				logger.error("Reset flagged spectra has failed!");
			}, true, stage);
		} else {
			logger.info(
					"Spectra not found. Make sure that the file is not empty or a peak list file were imported.\nPlease load a new peak list file!");
			new ShowPopupDialog("Spectra not found",
					"Spectra not found. Make sure that the file is not empty or a peak list file were imported.\nPlease load a new peak list file!",
					stage);
		}
	}

	/**
	 * Save applied filters parameters in a JSON file
	 * 
	 */
	public void onSaveFiltersToJsonFile() {
		FileUtils.saveFilterAs(file -> {
			taskRunner.doAsyncWork("Saving filters parameters in JSON file", () -> {
				Boolean isSucceeded = false;
				try {
					FilterWriterJson.saveFilter(file);
					isSucceeded = true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Error while trying to save filters parameters in a JSON file ", e);
				}
				return isSucceeded;
			}, (isSucceeded) -> {
				if (isSucceeded)
					logger.info("Saving filters parameters in a JSON file has finished successfully!");
			}, (failure) -> {
				logger.error("Saving filters parameters in a JSON file has failed!", failure.getMessage());
			}, true, stage);

		}, stage);
	}

	/**
	 * Show filters parameters applied on current spectra.
	 */
	public void onViewCurrentFilters() {
		FilterViewerDialog FilterLoaderDialog = new FilterViewerDialog();
		FilterLoaderDialog.showAndWait().ifPresent(filtersByName -> {
			System.out.println(Filters.getFullDescription());
			logger.info(Filters.getFullDescription());
		});
	}

	/**
	 * Refresh the main view. Updates the view properties and refresh the table
	 * view.
	 */
	private void refresh() {
		updateJfx(() -> {
			if (!items.isEmpty()) {
				updateChanges(items.get(0), ListOfSpectra.getFirstSpectra().getNbSpectra(), items.size(),
						Float.valueOf((items.size() / ListOfSpectra.getFirstSpectra().getNbSpectra()) * 100),
						ListOfSpectra.getFirstSpectra().getNbIdentified(),
						ListOfSpectra.getFirstSpectra().getPercentageIdentified());
				view.getFilteredTable().refresh();
			}
		});
	}

	/***
	 * Clear table columns filters
	 */
	public void resetColumnFilters() {
		view.getFilteredTable().getColumns().forEach(column -> {
			((IFilterableTableColumn) column).getFilters().clear();
		});
	}

	public void setItems(ObservableList<Spectrum> items) {
		this.items = items;
	}

	public void setTaskRunner(TaskRunner taskRunner) {
		this.taskRunner = taskRunner;
	}

	public void setView(MainView view) {
		this.view = view;
	}

	/**
	 * Update and notify the view with the changes.
	 * 
	 * @param spectrum
	 *            the selected spectrum. On load file, it select the first
	 *            spectrum.
	 * @param nbFiltered
	 *            the number of remained spectra after applying the filters
	 * @param percentageFiltered
	 *            the percentage of remained spectra after applying the filters
	 * @param nbSpectra
	 *            the total number of spectrum in the file.
	 * @param nbIdentified
	 *            the number of identified spectrum.
	 * @param percentageIdentified
	 *            the percentage of identified spectrum.
	 */
	private void updateChanges(Spectrum spectrum, Integer nbSpectra, Integer nbFiltered, Float percentageFiltered,
			Integer nbIdentified, Float percentageIdentified) {
		view.getViewProperties().notify(spectrum, String.valueOf(nbSpectra), String.valueOf(nbFiltered),
				String.format("%.2f", percentageIdentified), String.valueOf(nbIdentified),
				String.format("%.2f", percentageIdentified), Session.CURRENT_FILE.getName(), Session.CURRENT_REGEX_RT);
	}

	/**
	 * Update the view on Java-Fx thread
	 * 
	 * @param r
	 *            Runnable to submit
	 */
	private void updateJfx(Runnable r) {
		Platform.runLater(r);
	}

	/**
	 * Export the current chart in the mainview 
	 */
	public void exportChart(SpectrumView spectrumView) {

		FileUtils.saveChartAs(file -> {
			try {
			} catch (Exception e) {

			}
		}, stage);
	}
}
