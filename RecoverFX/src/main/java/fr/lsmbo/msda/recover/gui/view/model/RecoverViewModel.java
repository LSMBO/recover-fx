package fr.lsmbo.msda.recover.gui.view.model;

import java.io.File;
import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.lsmbo.msda.recover.gui.IconResource.ICON;
import fr.lsmbo.msda.recover.gui.RecoverFx;
import fr.lsmbo.msda.recover.gui.Session;
import fr.lsmbo.msda.recover.gui.filters.ColumnFilters;
import fr.lsmbo.msda.recover.gui.filters.FilterRequest;
import fr.lsmbo.msda.recover.gui.io.ExportBatch;
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
import fr.lsmbo.msda.recover.gui.view.dialog.AboutDialog;
import fr.lsmbo.msda.recover.gui.view.dialog.ConfirmDialog;
import fr.lsmbo.msda.recover.gui.view.dialog.ExportInBatchDialog;
import fr.lsmbo.msda.recover.gui.view.dialog.FilterIonReporterDialog;
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

	private MainView view;
	private TaskRunner taskRunner;
	private static Stage stage;
	private ObservableList<Spectrum> items = FXCollections
			.observableArrayList(ListOfSpectra.getFirstSpectra().getSpectraAsObservable());

	public TaskRunner getTaskRunner() {
		return taskRunner;
	}

	public void setTaskRunner(TaskRunner taskRunner) {
		this.taskRunner = taskRunner;
	}

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		RecoverViewModel.stage = stage;
	}

	public ObservableList<Spectrum> getItems() {
		return items;
	}

	public void setItems(ObservableList<Spectrum> items) {
		this.items = items;
	}

	public MainView getView() {
		return view;
	}

	public void setView(MainView view) {
		this.view = view;
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
	 * Load and extract spectra from Peaklist file.
	 * 
	 * @param file
	 *            the Peaklist file to load.
	 */
	public void loadFile(File file) {
		taskRunner.doAsyncWork("Loading and extracting spectra from peaklist file", () -> {
			onInitialize();
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
				updateJfx(() -> items.setAll(ListOfSpectra.getFirstSpectra().getSpectraAsObservable()));
				refresh();
			}
			// Enable second Peakslist
			RecoverFx.useSecondPeaklist = true;
		}, (failure) -> {
			logger.debug("Loading and extracting spectra from peaklist file has failed!");
			// Disable use second peaklist
			RecoverFx.useSecondPeaklist = false;
		}, true, stage);
	}

	/**
	 * Initialize and reset RecoverFx parameters.
	 */
	private void onInitialize() {
		logger.info("Initialize the first spectra...");
		ListOfSpectra.getFirstSpectra().initialize();
	}

	/**
	 * Export peak list file. Set all the left spectra after applying the
	 * filters as recover.
	 */
	public void onExportFile() {
		ObservableList<Spectrum> filteredItems = FXCollections.observableArrayList(view.getFilteredTable().getItems());
		logger.debug("The filtered spectra number: {}", filteredItems.size());
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
					logger.debug("The file: {} has been exported in {}", file.getAbsolutePath(),
							(double) totalTime / 1000, " sec");
					System.out.println("INFO - The filtered spectra number: " + filteredItems.size() + ". The file: "
							+ file.getAbsolutePath() + " has been exported in: " + (double) totalTime / 1000 + " sec");
					return file;
				}, (sucess) -> {
					logger.debug("Exporting file: {} has been exported successfully!", file.getAbsolutePath());
				}, (failure) -> {
					logger.error("Exporting file: {} has failed!", file.getAbsolutePath());
				}, true, stage);
			}, stage);
		} else {
			logger.warn("Empty spectra. The filtred items are empty. No spectra will be recovered!");
			new ShowPopupDialog("Empty Spectra",
					"Empty spectra. The filtred items are empty. No spectra will be recovered!", stage);
		}
	}

	/**
	 * Apply all filters and identified spectra
	 */

	public void onExportInBatch() {
		ExportInBatchDialog exportInBatchDialog = new ExportInBatchDialog();
		exportInBatchDialog.showAndWait().ifPresent(identificationByPeakListMap -> {
			if (!identificationByPeakListMap.keySet().isEmpty()) {
				taskRunner.doAsyncWork("Exporting in batch", () -> {
					long startTime = System.currentTimeMillis();
					logger.debug("Start exporting in batch. The number of file to proceed : ",
							identificationByPeakListMap.keySet().size());
					System.out.println("INFO - Start exporting in batch. The number of file to proceed : "
							+ identificationByPeakListMap.keySet().size());
					ExportBatch exportOnBatch = new ExportBatch();
					exportOnBatch.run(identificationByPeakListMap);
					long endTime = System.currentTimeMillis();
					long totalTime = endTime - startTime;
					logger.debug("Exporting in batch has finished: {} ", (double) totalTime / 1000, " sec");
					System.out.println("INFO - Exporting in batch has finished: " + (double) totalTime / 1000 + " sec");
					return true;
				}, (sucess) -> {
					logger.debug("Exporting in batch has been finished successfully!");
				}, (failure) -> {
					logger.error("Exporting in batch has failed!", failure.getMessage());
				}, true, stage);
			}
		});
	}

	/**
	 * Load filters parameters from a JSON file, apply all filters on current
	 * spectra.
	 */
	public void onLoadFiltersFrmJsonFile() {
		FilterViewerDialog FilterLoaderDialog = new FilterViewerDialog();
		FilterLoaderDialog.showAndWait().ifPresent(filter -> {
			taskRunner.doAsyncWork("Loading filters parameters from a JSON file", () -> {
				FilterRequest filetrRequest = new FilterRequest();
				filetrRequest.applyAllFilters(items);
				return true;
			}, (isSucceeded) -> {
				if (isSucceeded) {
					logger.debug("Loading filter's parameters from a JSON file has finished successfully!");
					refresh();
				}
			}, (failure) -> {
				logger.error("Loading filter's parameters from a JSON file has failed!", failure.getMessage());
			}, true, stage);
		});
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
					logger.debug("Saving filters parameters in a JSON file has finished successfully!");
			}, (failure) -> {
				logger.error("Saving filters parameters in a JSON file has failed!", failure.getMessage());
			}, true, stage);

		}, stage);

	}

	/**
	 * Exit RecoverFx software. This action will reset all values and close the
	 * window. Make sure to save before to exit the software.
	 * 
	 */
	public void onExit() {
		logger.warn("Exit Recover");
		System.out.println("WARN - Exit Recover");
		new ConfirmDialog<Object>(ICON.EXIT, "Exit Recover", "Are you sure you want to exit Recover ?", () -> {
			Platform.exit();
			System.exit(0);
			return null;
		}, stage);
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
					FilterRequest filterRequest = new FilterRequest();
					Boolean isFinished = filterRequest.applyIR();
					return isFinished;
				}, (sucess) -> {
					logger.info(ColumnFilters.getFullDescription());
					System.out.println(ColumnFilters.getFullDescription());
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
				FilterRequest filterRequest = new FilterRequest();
				Boolean isFinished = filterRequest.applyLIT();
				return isFinished;
			}, (sucess) -> {
				System.out.println(ColumnFilters.getFullDescription());
				logger.info(ColumnFilters.getFullDescription());
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
					for (String t : ((IdentifiedSpectra) identifiedSpectra).getArrayTitles()) {
						((IdentifiedSpectra) identifiedSpectra).setIdentified(t);
					}
					isFinished = true;
					return isFinished;
				}, (sucess) -> {
					logger.info(ColumnFilters.getFullDescription());
					System.out.println(ColumnFilters.getFullDescription());
					logger.info("Getting identified spectra has finished successfully!");
					refresh();
				}, (failure) -> {
					logger.error("Getting identified spectra has failed!");
				}, true, stage);
			});
		} else {
			logger.debug(
					"Spectra not found. Make sure that the file is not empty or a file were imported.\nPlease load a new file!");
			new ShowPopupDialog("Spectra not found",
					"Spectra not found. Make sure that the file is not empty or a file were imported.\nPlease load a new file!",
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
			logger.debug(
					"Spectra not found. Make sure that the file is not empty or a file were imported.\nPlease load a new file!");
			new ShowPopupDialog("Spectra not found",
					"Spectra not found. Make sure that the file is not empty or a file were imported.\nPlease load a new file!",
					stage);
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
			taskRunner.doAsyncWork("Reset all filters", () -> {
				// Reset all filters to default values.
				FilterRequest filetrRequest = new FilterRequest();
				IonReporters.getIonReporters().clear();
				ColumnFilters.resetAll();
				filetrRequest.restoreDefaultValues();
				filetrRequest.applyAllFilters(items);
				// Restore default session parameters
				return true;
			}, (sucess) -> {
				updateItems();
			}, (failure) -> {
				logger.error("Reset all filters has failed!");
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
	 * Creates and displays a dialog about RecoverFx software: name, version and
	 * description.
	 * 
	 */
	public void onAboutRecoverFx() {
		AboutDialog aboutDialog = new AboutDialog();
		aboutDialog.showAndWait().ifPresent(RecoverFx -> {
			logger.info("About Recover software {}", RecoverFx);
			System.out.println("INFO - About Recover software: " + RecoverFx);
		});
	}

	/**
	 * Update the items in table view with the stored items.
	 * 
	 */
	private void updateItems() {
		items.setAll(ListOfSpectra.getFirstSpectra().getSpectraAsObservable());
		view.getFilteredTable().refresh();
	}

	/**
	 * Reset Session parameters to the default values.
	 * 
	 */
	private void resetSessionParams() {
		Session.FILE_HEADER = "";
		// TODO public static Boolean DATABASE_LOADED = false;
		Session.CURRENT_FILE = null;
		Session.SECOND_FILE = null;
		Session.CURRENT_REGEX_RT = "title.regex.data-analysis.rt";
		Session.USE_FIXED_AXIS = false;
		Session.HIGHEST_FRAGMENT_MZ = 0F;
		Session.HIGHEST_FRAGMENT_INTENSITY = 0F;
		Session.CALCULATED_NOISE_VALUE = 150F;
		Session.LOW_INTENSITY_THRESHOLD = 450F;
		Session.HIGH_INTENSITY_THRESHOLD = 2000F;
		Session.TOP_LINE = 2500F;
	}

	/**
	 * Update and notify the view with the changes.
	 * 
	 * @param spectrum
	 *            the selected spectrum. On load file, it select the first
	 *            spectrum.
	 * @param nbSpectra
	 *            the total number of spectrum in the file.
	 * @param nbIdentified
	 *            the number of identified spectrum.
	 * @param percentageIdentified
	 *            the percentage of identified spectrum.
	 */
	private void updateChanges(Spectrum spectrum, Integer nbSpectra, Integer nbIdentified, Float percentageIdentified) {
		view.getViewProperties().notify(spectrum, String.valueOf(nbSpectra), String.valueOf(nbIdentified),
				String.format("%.2f", percentageIdentified), Session.CURRENT_FILE.getName(), Session.CURRENT_REGEX_RT);
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
	 * Refresh the main view. Updates the view properties and refresh the table
	 * view.
	 */
	private void refresh() {
		updateJfx(() -> {
			updateChanges(ListOfSpectra.getFirstSpectra().getSpectraAsObservable().get(0),
					ListOfSpectra.getFirstSpectra().getNbSpectra(), ListOfSpectra.getFirstSpectra().getNbIdentified(),
					ListOfSpectra.getFirstSpectra().getPercentageIdentified());
			view.getFilteredTable().refresh();
		});
	}

	/**
	 * Update the view on Java-fx thread
	 * 
	 * @param r
	 *            Runnable to submit
	 */
	private void updateJfx(Runnable r) {
		Platform.runLater(r);
	}
}
