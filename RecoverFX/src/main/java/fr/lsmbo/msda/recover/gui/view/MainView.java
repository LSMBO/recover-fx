package fr.lsmbo.msda.recover.gui.view;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.google.jhsheets.filtered.FilteredTableView;
import org.google.jhsheets.filtered.tablecolumn.ColumnFilterEvent;
import org.google.jhsheets.filtered.tablecolumn.FilterableBooleanTableColumn;
import org.google.jhsheets.filtered.tablecolumn.FilterableFloatTableColumn;
import org.google.jhsheets.filtered.tablecolumn.FilterableIntegerTableColumn;
import org.google.jhsheets.filtered.tablecolumn.FilterableStringTableColumn;

import fr.lsmbo.msda.recover.gui.IconResource;
import fr.lsmbo.msda.recover.gui.IconResource.ICON;
import fr.lsmbo.msda.recover.gui.Session;
import fr.lsmbo.msda.recover.gui.filters.ColumnFilters;
import fr.lsmbo.msda.recover.gui.filters.FilterRequest;
import fr.lsmbo.msda.recover.gui.filters.LowIntensityThresholdFilter;
import fr.lsmbo.msda.recover.gui.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.gui.model.ComputationTypes;
import fr.lsmbo.msda.recover.gui.model.Spectrum;
import fr.lsmbo.msda.recover.gui.util.JavaFxUtils;
import fr.lsmbo.msda.recover.gui.util.TaskRunner;
import fr.lsmbo.msda.recover.gui.view.model.RecoverViewModel;
import fr.lsmbo.msda.recover.gui.view.model.RecoverViewProperty;
import fr.lsmbo.msda.recover.gui.view.model.RecoverViewUPNProperty;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * Creates and displays the main view of RecoverFx.
 * 
 * @author Aromdhani
 *
 */
public class MainView extends StackPane {

	private RecoverViewModel model = null;
	private Spectrum selectedSpectrum = null;
	private TaskRunner taskRunner = null;
	private FilterRequest filterRequest = new FilterRequest();
	private final SwingNode swingNodeForChart = new SwingNode();
	private RecoverViewProperty viewProperty = new RecoverViewProperty();
	private RecoverViewUPNProperty filterLITProperty = new RecoverViewUPNProperty();
	// Filtered table
	private FilteredTableView<Spectrum> filteredTable;
	// Filtered columns
	private FilterableIntegerTableColumn<Spectrum, Integer> idColumn;
	private FilterableStringTableColumn<Spectrum, String> titleColumn;
	private FilterableFloatTableColumn<Spectrum, Float> mozColumn;
	private FilterableFloatTableColumn<Spectrum, Float> intensityColumn;
	private FilterableIntegerTableColumn<Spectrum, Integer> chargeColumn;
	private FilterableFloatTableColumn<Spectrum, Float> rtColumn;
	private FilterableIntegerTableColumn<Spectrum, Integer> nbrFragmentsColumn;
	private FilterableIntegerTableColumn<Spectrum, Integer> fragmentIntColumn;
	private FilterableIntegerTableColumn<Spectrum, Integer> UPNColumn;
	private FilterableBooleanTableColumn<Spectrum, Boolean> identifiedColumn;
	private FilterableBooleanTableColumn<Spectrum, Boolean> ionReporterColumn;
	private FilterableBooleanTableColumn<Spectrum, Boolean> wrongChargeColumn;
	private FilterableBooleanTableColumn<Spectrum, Boolean> flaggedColumn;
	private Label EmregenceValueLabel;
	private ChoiceBox<String> modeBaselineCmBox;

	/**
	 * Return the selected spectrum.
	 * 
	 * @return selectedSpectrum
	 */
	public Spectrum getSelectedSpectrum() {
		return selectedSpectrum;
	}

	/**
	 * @param selectedSpectrum
	 *            the selected spectrum to set
	 */
	public void setSelectedSpectrum(Spectrum selectedSpectrum) {
		this.selectedSpectrum = selectedSpectrum;
	}

	/**
	 * @return the view properties
	 */
	public RecoverViewProperty getViewProperties() {
		return viewProperty;
	}

	/**
	 * @param viewProperties
	 *            the view properties to set
	 */
	public void setViewProperties(RecoverViewProperty viewProperties) {
		this.viewProperty = viewProperties;
	}

	/**
	 * @return the filter properties
	 */
	public RecoverViewUPNProperty getFilterProperties() {
		return filterLITProperty;
	}

	/**
	 * @param filterProperties
	 *            the filter properties to set
	 */
	public void setFilterProperties(RecoverViewUPNProperty filterProperties) {
		this.filterLITProperty = filterProperties;
	}

	/**
	 * @return the filtered table
	 */
	public FilteredTableView<Spectrum> getFilteredTable() {
		return filteredTable;
	}

	/**
	 * @param filteredTable
	 *            the filtered table to set
	 */
	public void setFilteredTable(FilteredTableView<Spectrum> filteredTable) {
		this.filteredTable = filteredTable;
	}

	/**
	 * @return the view model
	 */
	public RecoverViewModel getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the view model to set
	 */
	public void setModel(RecoverViewModel model) {
		this.model = model;
	}

	/**
	 * @return the task runner
	 */
	public TaskRunner getTaskRunner() {
		return taskRunner;
	}

	/**
	 * @param taskRunner
	 *            the task runner to set
	 */
	public void setTaskRunner(TaskRunner taskRunner) {
		this.taskRunner = taskRunner;
	}

	/**
	 * @return the swing node for chart
	 */
	public SwingNode getSwingNodeForChart() {
		return swingNodeForChart;
	}

	@SuppressWarnings(value = { "unchecked", "static-access", "rawtypes" })
	public MainView(RecoverViewModel model) {
		// Create the glassePane
		VBox glassPane = new VBox();
		ProgressIndicator progressIndicator = new ProgressIndicator();
		progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
		progressIndicator.setVisible(true);
		glassPane.getChildren().add(progressIndicator);
		glassPane.setAlignment(Pos.CENTER);
		glassPane.setVisible(false);

		// Create the main view
		BorderPane mainView = new BorderPane();
		mainView.setPrefSize(1400, 800);
		// Create menu and menu items
		MenuBar menuBar = new MenuBar();
		/* File menu items */
		Menu fileMenu = new Menu(" File ");
		// Load file
		MenuItem openFile = new MenuItem(" Open file  ... ");
		openFile.setGraphic(new ImageView(IconResource.getImage(ICON.LOAD)));
		openFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
		openFile.setOnAction((ActionEvent t) -> {
			model.onChooseFile();
		});

		// Export file
		MenuItem exportFile = new MenuItem(" Export file  ... ");
		exportFile.setGraphic(new ImageView(IconResource.getImage(ICON.EXPORT)));
		exportFile.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
		exportFile.setOnAction((ActionEvent t) -> {
			model.onExportFile();
		});
		// Export in batch
		MenuItem exportInBatchFile = new MenuItem(" Export in batch  ... ");
		exportInBatchFile.setGraphic(new ImageView(IconResource.getImage(ICON.EXPORT_DATA)));
		exportInBatchFile.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN));
		exportInBatchFile.setOnAction((e) -> {
			model.onExportInBatch();
		});
		// Save filter parameters
		MenuItem loadFiltersFrmJsonFile = new MenuItem(" Load filters from JSON... ");
		loadFiltersFrmJsonFile.setGraphic(new ImageView(IconResource.getImage(ICON.LOAD)));
		loadFiltersFrmJsonFile.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));
		loadFiltersFrmJsonFile.setOnAction((e) -> {
			model.onLoadFiltersFrmJsonFile();
		});
		// Save filter parameters
		MenuItem saveFiltersToJsonFile = new MenuItem(" Save filters to JSON... ");
		saveFiltersToJsonFile.setGraphic(new ImageView(IconResource.getImage(ICON.SAVE)));
		saveFiltersToJsonFile.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		saveFiltersToJsonFile.setOnAction((e) -> {
			model.onSaveFiltersToJsonFile();
		});
		// Exit Recover
		MenuItem exitFile = new MenuItem(" Exit ");
		exitFile.setGraphic(new ImageView(IconResource.getImage(ICON.EXIT)));
		exitFile.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
		exitFile.setOnAction((e) -> {
			model.onExit();
		});
		fileMenu.getItems().addAll(openFile, exportFile, exportInBatchFile, loadFiltersFrmJsonFile,
				saveFiltersToJsonFile, exitFile);

		/* Action menu items */
		Menu actionsMenu = new Menu(" Actions ");
		// Add ion reporter
		MenuItem addIonReporterAction = new MenuItem(" Add ion reporter ");
		addIonReporterAction.setGraphic(new ImageView(IconResource.getImage(ICON.APPLYFILTER)));
		addIonReporterAction.setOnAction((ActionEvent t) -> {
			model.onAddIonReporter();
		});
		// Edit Parsing Rules
		MenuItem editPRulesAction = new MenuItem(" Edit parsing rules ");
		editPRulesAction.setGraphic(new ImageView(IconResource.getImage(ICON.EDIT)));
		editPRulesAction.setOnAction((ActionEvent t) -> {
			model.onEditParsingRules();
		});
		// Get Identified Spectra
		MenuItem getIdentifiedSpecAction = new MenuItem(" Get identified spectra ");
		getIdentifiedSpecAction.setGraphic(new ImageView(IconResource.getImage(ICON.IDENTIFIEDSPECTRA)));
		getIdentifiedSpecAction.setOnAction((ActionEvent t) -> {
			model.onGetIdentifiedSpectra();
		});
		// Flagged Spectrum
		MenuItem flaggedSpecAction = new MenuItem(" Reset flagged spectra ");
		flaggedSpecAction.setGraphic(new ImageView(IconResource.getImage(ICON.FLAG)));
		flaggedSpecAction.setOnAction((ActionEvent t) -> {
			model.onResetFlagSpectrum();
		});
		// Reset Recover
		MenuItem resetRecoverAction = new MenuItem(" Reset recover ");
		resetRecoverAction.setGraphic(new ImageView(IconResource.getImage(ICON.RESET)));
		resetRecoverAction.setOnAction((ActionEvent t) -> {
			model.onResetRecover();
		});
		actionsMenu.getItems().addAll(addIonReporterAction, editPRulesAction, getIdentifiedSpecAction,
				flaggedSpecAction, resetRecoverAction);
		/* Help menu items */
		// User guide menu item
		Menu helpMenu = new Menu(" Help ");
		MenuItem userGuide = new MenuItem(" User guide ");
		userGuide.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN));
		userGuide.setGraphic(new ImageView(IconResource.getImage(ICON.HELP)));
		userGuide.setOnAction((ActionEvent t) -> {
			model.onOpenUserGuide();
		});
		// About Recover menu item
		MenuItem aboutRecover = new MenuItem(" About ");
		aboutRecover.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));
		aboutRecover.setGraphic(new ImageView(IconResource.getImage(ICON.INFORMATION)));
		aboutRecover.setOnAction((ActionEvent t) -> {
			model.onAboutRecoverFx();
		});
		helpMenu.getItems().addAll(userGuide, aboutRecover);
		menuBar.getMenus().addAll(fileMenu, actionsMenu, helpMenu);
		mainView.setTop(menuBar);

		/***********************
		 * Filtered table view *
		 ***********************/
		filteredTable = new FilteredTableView<>(model.getItems());

		// Id column
		idColumn = new FilterableIntegerTableColumn<>("Id");
		idColumn.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("id"));

		// Title Column
		titleColumn = new FilterableStringTableColumn<>("Title");
		titleColumn.setCellValueFactory(new PropertyValueFactory<Spectrum, String>("title"));

		// Mz Column
		mozColumn = new FilterableFloatTableColumn<>("Mz");
		mozColumn.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("mz"));

		// Intensity Column
		intensityColumn = new FilterableFloatTableColumn<>("Intensity");
		intensityColumn.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("intensity"));

		// Charge Column
		chargeColumn = new FilterableIntegerTableColumn<>("Charge");
		chargeColumn.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("charge"));

		// Wrong charge Column
		wrongChargeColumn = new FilterableBooleanTableColumn<>("Wrong charge");
		wrongChargeColumn.setCellValueFactory(cellData -> cellData.getValue().getWrongCharge());
		wrongChargeColumn.setCellFactory(CheckBoxTableCell.forTableColumn(wrongChargeColumn));

		// RT Column
		rtColumn = new FilterableFloatTableColumn<>("Retention Time");
		rtColumn.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("retentionTime"));

		// Fragment number
		nbrFragmentsColumn = new FilterableIntegerTableColumn<>("Fragment number");
		nbrFragmentsColumn.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("nbFragments"));

		// Fragment intensity
		fragmentIntColumn = new FilterableIntegerTableColumn<>("Max fragment intensity");
		fragmentIntColumn.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("fragmentMaxIntensity"));

		// UPN Column
		UPNColumn = new FilterableIntegerTableColumn<>("UPN");
		UPNColumn.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("upn"));

		// Identified Column
		identifiedColumn = new FilterableBooleanTableColumn<>("Identified");
		identifiedColumn.setCellValueFactory(cellData -> cellData.getValue().identifiedProperty());
		identifiedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(identifiedColumn));

		// Recover Column
		ionReporterColumn = new FilterableBooleanTableColumn<>("Ion Reporter");
		ionReporterColumn.setCellValueFactory(cellData -> cellData.getValue().getIonReporter());
		ionReporterColumn.setCellFactory(CheckBoxTableCell.forTableColumn(ionReporterColumn));

		// Flag Column
		flaggedColumn = new FilterableBooleanTableColumn<>("Flag");
		flaggedColumn.setCellValueFactory(cellData -> cellData.getValue().getIsFlaggedProperty());
		flaggedColumn.setCellFactory(new Callback<TableColumn<Spectrum, Boolean>, TableCell<Spectrum, Boolean>>() {

			@Override
			public TableCell<Spectrum, Boolean> call(TableColumn<Spectrum, Boolean> param) {
				return new TableCell<Spectrum, Boolean>() {

					private final ImageView image = new ImageView(IconResource.getImage(ICON.FLAG));

					@Override
					public void updateItem(Boolean item, boolean empty) {
						super.updateItem(item, empty);
						setGraphic(item == null || !item ? null : image);
					}
				};
			}
		});
		filteredTable.getColumns().setAll(flaggedColumn, idColumn, titleColumn, mozColumn, intensityColumn,
				chargeColumn, rtColumn, nbrFragmentsColumn, fragmentIntColumn, UPNColumn, identifiedColumn,
				ionReporterColumn, wrongChargeColumn);

		filteredTable.autosize();
		filteredTable.setColumnResizePolicy(filteredTable.CONSTRAINED_RESIZE_POLICY);
		filteredTable.setPadding(new Insets(5, 5, 5, 5));
		filteredTable.setMinHeight(250);

		/*****************
		 * Spectrum Pane *
		 *****************/

		// Create spectrum panel
		SplitPane spectrumPane = new SplitPane();
		spectrumPane.setOrientation(Orientation.HORIZONTAL);
		spectrumPane.setPrefHeight(320);
		spectrumPane.setDividerPositions(0.35f, 0.65f);
		// Create scroll pane and tab pane
		TabPane tabPane = new TabPane();
		// Create general information pane
		Label generalLabel = new Label("General Properties:");
		generalLabel.setStyle(JavaFxUtils.TITLE);
		Label fileNameLabel = new Label("Current file name:");
		fileNameLabel.setTooltip(new Tooltip("File name"));
		Label fileName = new Label("empty");
		fileName.textProperty().bind(viewProperty.getSessionFileNameProperty());
		Label specNumberLabel = new Label("Spectrum number:");
		specNumberLabel.setTooltip(new Tooltip("Spectrum number"));
		Label specNumber = new Label();
		specNumber.textProperty().bind(viewProperty.getSpectrumNbProperty());
		HBox specNumberPane = new HBox(2);
		specNumberPane.getChildren().addAll(specNumberLabel, specNumber);
		// Recover spectrum
		Label recoverNumberLabel = new Label("Recover number:");
		recoverNumberLabel.setTooltip(new Tooltip("The number of recovred spectrum"));
		Label recoverNumber = new Label("100");
		recoverNumber.textProperty().bind(viewProperty.getRecoveredNbProperty());
		// Percentage of
		Label percentRecoveredLabel = new Label("Percentage of recovery:");
		percentRecoveredLabel.setTooltip(new Tooltip("The percentage of recovred spectrum"));
		Label percentageReocvered = new Label("0");
		percentageReocvered.textProperty().bind(viewProperty.getRecoveredPercProperty());
		// Identified spectrum
		Label identifiedNumberLabel = new Label("Identified number:");
		identifiedNumberLabel.setTooltip(new Tooltip("The number of identified spectrum"));
		Label identifiedNumber = new Label("0");
		identifiedNumber.textProperty().bind(viewProperty.getIdentifiedNbProperty());
		// Percentage of identified
		Label percentIdentifiedLabel = new Label("Percentage of identified:");
		percentIdentifiedLabel.setTooltip(new Tooltip("The percentage of identified spectrum"));
		Label percentageIdentified = new Label("0");
		percentageIdentified.textProperty().bind(viewProperty.getIdentifiedPercProperty());
		// Create general pane
		GridPane generalInfosPane = new GridPane();
		generalInfosPane.setPadding(new Insets(10));
		generalInfosPane.setHgap(15);
		generalInfosPane.setVgap(5);
		generalInfosPane.add(generalLabel, 0, 0, 1, 1);
		generalInfosPane.addRow(4, fileNameLabel, fileName);
		// generalInfosPane.addRow(5, secondFileNameLabel, secondFileName);
		generalInfosPane.addRow(5, specNumberLabel, specNumber);
		// generalInfosPane.addRow(7, matchedNumberLabel, matchedNumber);
		generalInfosPane.addRow(6, recoverNumberLabel, recoverNumber);
		generalInfosPane.addRow(7, percentRecoveredLabel, percentageReocvered, new Label("%"));
		generalInfosPane.addRow(8, identifiedNumberLabel, identifiedNumber);
		generalInfosPane.addRow(9, percentIdentifiedLabel, percentageIdentified, new Label("%"));
		generalInfosPane.autosize();
		// Create spectrum information pane
		GridPane spectrumInfosPane = new GridPane();
		spectrumInfosPane.setPadding(new Insets(10));
		spectrumInfosPane.setHgap(15);
		spectrumInfosPane.setVgap(5);
		Label spectrumPropertiesLabel = new Label("Spectrum Properties:");
		spectrumPropertiesLabel.setStyle(JavaFxUtils.TITLE);
		// Spectrum title
		Label spectrumTitleLabel = new Label("Title:");
		spectrumTitleLabel.setTooltip(new Tooltip("Spectrum title"));
		Label spectrumTitle = new Label("empty");
		// Spectrum Precursor
		Label spectrumPrecursorLabel = new Label("Precursor (m/z, intensity):");
		spectrumPrecursorLabel.setTooltip(new Tooltip("Spectrum precursor (m/z,Intensity)"));
		Label spectrumPrecursor = new Label("empty");
		// Spectrum fragment number
		Label fragmentNumberLabel = new Label("Fragment number:");
		fragmentNumberLabel.setTooltip(new Tooltip("Fragment number"));
		Label fragmentNumber = new Label(Integer.toString(0));
		// Spectrum fragment max MOZ
		Label fragmentMaxMozLabel = new Label("Fragment max Moz:");
		fragmentMaxMozLabel.setTooltip(new Tooltip("Fragment max Moz"));
		Label fragmentMaxMoz = new Label(Float.toString(0));
		// Spectrum fragment max intensity
		Label fragmentMaxIntensityLabel = new Label("Fragment max intensity:");
		fragmentMaxIntensityLabel.setTooltip(new Tooltip("Fragment max intensity"));
		Label fragmentMaxIntensity = new Label(Float.toString(0));
		// Spectrum average fragment intensities
		Label averageFrgIntensitiesLabel = new Label("Average fragment intensity:");
		averageFrgIntensitiesLabel.setTooltip(new Tooltip("Average fragment intensity"));
		Label averageFrgIntensities = new Label(Float.toString(0));
		// Spectrum median fragment intensities
		Label medianFrgIntensitiesLabel = new Label("Median fragment intensity:");
		medianFrgIntensitiesLabel.setTooltip(new Tooltip("Median fragment intensity"));
		Label medianFrgIntensities = new Label(Float.toString(0));

		spectrumInfosPane.add(spectrumPropertiesLabel, 0, 0, 1, 1);
		spectrumInfosPane.addRow(4, spectrumTitleLabel, spectrumTitle);
		spectrumInfosPane.addRow(5, spectrumPrecursorLabel, spectrumPrecursor);
		spectrumInfosPane.addRow(6, fragmentNumberLabel, fragmentNumber);
		spectrumInfosPane.addRow(7, fragmentMaxMozLabel, fragmentMaxMoz);
		spectrumInfosPane.addRow(8, fragmentMaxIntensityLabel, fragmentMaxIntensity);
		spectrumInfosPane.addRow(9, averageFrgIntensitiesLabel, averageFrgIntensities);
		spectrumInfosPane.addRow(10, medianFrgIntensitiesLabel, medianFrgIntensities);
		spectrumInfosPane.autosize();
		// Create user settings pane
		GridPane userSettingsPane = new GridPane();
		userSettingsPane.setPadding(new Insets(10));
		userSettingsPane.setHgap(10);
		userSettingsPane.setVgap(5);

		Label userSettingsPopertiesTitle = new Label("User Settings Properties:");
		userSettingsPopertiesTitle.setStyle(JavaFxUtils.TITLE);

		Label userNameLabel = new Label("User name:");
		userNameLabel.setTooltip(new Tooltip("User name"));
		Label userName = new Label(Session.parameters.getUserName());

		Label appliedQualityFilterLabel = new Label("Quality filters:");
		appliedQualityFilterLabel.setTooltip(new Tooltip("User quality filters"));
		Label appliedQualityFilter = new Label("");
		// Label appliedQualityFilter = new
		// Label(Session.parameters.getQualityFilters().toString());
		Label chartSettingsLabel = new Label("Chart settings:");
		chartSettingsLabel.setTooltip(new Tooltip("User chart settings"));
		Label chartSettings = new Label("");
		// Label chartSettings = new
		// Label(Session.parameters.getChart().toString());
		Label comparaisonLabel = new Label("Comparaison:");
		comparaisonLabel.setTooltip(new Tooltip("User comparaison settings"));
		Label comparaison = new Label("");
		// Label comparaison = new
		// Label(Session.parameters.getComparison().toString());
		Label regexRtLabel = new Label("Current regex RT:");
		regexRtLabel.setTooltip(new Tooltip("Current regex to retrieve retention time"));
		Label regexRt = new Label(Session.CURRENT_REGEX_RT);
		regexRt.textProperty().bind(viewProperty.getRegexProperty());

		// Emergence value via slider
		Label EmregenceLabel = new Label("Emergence: ");
		EmregenceValueLabel = new Label("0.0");
		Slider emergenceSlider = new Slider();
		emergenceSlider.setMin(0);
		emergenceSlider.setMax(15.0);
		emergenceSlider.setValue(0.0);
		emergenceSlider.setShowTickLabels(true);
		emergenceSlider.setMajorTickUnit(1);
		emergenceSlider.setBlockIncrement(1);
		emergenceSlider.valueProperty().addListener((obs, oldval, newVal) -> {
			emergenceSlider.setValue(newVal.intValue());
			EmregenceValueLabel.setText(String.valueOf(newVal.intValue()));
		});

		Label baseLineLabel = new Label("Select a baseline: ");
		modeBaselineCmBox = new ChoiceBox<String>();
		modeBaselineCmBox
				.setTooltip(new Tooltip("Choose a value of the baseline: average or median of fragments intensities"));
		modeBaselineCmBox.getItems().addAll("Average of all peaks", "Median of all peaks");
		modeBaselineCmBox.getSelectionModel().selectFirst();
		Button applyButton = new Button("Apply");
		applyButton.setGraphic(new ImageView(IconResource.getImage(ICON.TICK)));
		applyButton.setOnAction(evt -> {
			filterLITProperty.setEmergence(Float.valueOf(EmregenceValueLabel.getText()));
			filterLITProperty.setMode(modeBaselineCmBox.getSelectionModel().getSelectedItem());
			LowIntensityThresholdFilter filterLIT = new LowIntensityThresholdFilter();
			filterLIT.setParameters(filterLITProperty.getEmergence().get(), 0, 0,
					ComputationTypes.getMode(modeBaselineCmBox));
			ObservableList<Object> filterList = FXCollections.observableArrayList();
			filterList.add(filterLIT);
			ColumnFilters.add("LIT", filterList);
			model.onApplyLowIntThresholdFilter();
		});
		userSettingsPane.add(userSettingsPopertiesTitle, 0, 0, 1, 1);
		userSettingsPane.addRow(4, userNameLabel, userName);
		userSettingsPane.addRow(5, appliedQualityFilterLabel, appliedQualityFilter);
		userSettingsPane.addRow(6, chartSettingsLabel, chartSettings);
		userSettingsPane.addRow(7, comparaisonLabel, comparaison);
		userSettingsPane.addRow(8, regexRtLabel, regexRt);
		userSettingsPane.addRow(9, baseLineLabel, modeBaselineCmBox);
		userSettingsPane.addRow(11, EmregenceLabel, EmregenceValueLabel);
		userSettingsPane.add(emergenceSlider, 0, 12, 2, 1);
		userSettingsPane.add(applyButton, 2, 12, 1, 1);
		userSettingsPane.autosize();

		Tab genrealTab = new Tab(" General ");
		genrealTab.setContent(generalInfosPane);
		genrealTab.setClosable(false);
		Tab spectrumTab = new Tab(" Spectrum ");
		spectrumTab.setContent(spectrumInfosPane);
		spectrumTab.setClosable(false);
		// User settings components
		Tab userSettingsTab = new Tab("User Settings");
		userSettingsTab.setContent(userSettingsPane);
		userSettingsTab.setClosable(false);
		tabPane.getTabs().addAll(genrealTab, spectrumTab, userSettingsTab);
		tabPane.getSelectionModel().select(genrealTab);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollPane.prefHeightProperty().bind(tabPane.heightProperty());
		scrollPane.prefWidthProperty().bind(tabPane.widthProperty());
		scrollPane.setPadding(new Insets(10));
		scrollPane.setContent(tabPane);

		tabPane.prefHeightProperty().bind(scrollPane.heightProperty());
		tabPane.prefWidthProperty().bind(scrollPane.widthProperty());
		scrollPane.autosize();
		// Create spectrum view
		BorderPane graphicsPane = new BorderPane();
		// Create and Set SwingContent(swingNode);
		graphicsPane.setCenter(swingNodeForChart);
		spectrumPane.getItems().addAll(scrollPane, graphicsPane);
		// Create a split pane
		SplitPane splitPane = new SplitPane();
		splitPane.setOrientation(Orientation.VERTICAL);
		splitPane.autosize();
		splitPane.getItems().addAll(filteredTable, spectrumPane, ConsoleView.getInstance());
		splitPane.setDividerPositions(0.2f, 0.7f, 0.1f);
		// Create the main view
		mainView.setCenter(splitPane);
		this.getChildren().addAll(mainView, glassPane);
		// Create an instance of task runner
		this.taskRunner = new TaskRunner(this, glassPane, new Label());

		// Set the selected row as flagged on double click
		filteredTable.setRowFactory(tv -> {
			TableRow<Spectrum> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					Spectrum spectrum = row.getItem();
					if (!spectrum.getIsFlagged())
						spectrum.setIsFlagged(true);
					else
						spectrum.setIsFlagged(false);
				}
			});
			return row;
		});

		// Update the view when spectrum property has updated
		viewProperty.getSpectrumProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				selectedSpectrum = newValue;
				// Create the spectrum chart node
				SpectrumView spectrumView = new SpectrumView(selectedSpectrum);
				// Update the spectrum/fragments properties
				spectrumTitle.setText(selectedSpectrum.getTitle());
				spectrumPrecursor.setText(Float.toString(selectedSpectrum.getMz()) + ", "
						+ Float.toString(selectedSpectrum.getIntensity()));
				fragmentNumber.setText(Integer.toString(selectedSpectrum.getNbFragments()));
				fragmentMaxMoz.setText(Float.toString(selectedSpectrum.getFragmentMaxMoz()));
				fragmentMaxIntensity.setText(Float.toString(selectedSpectrum.getFragmentMaxIntensity()));
				averageFrgIntensities.setText(Float.toString(selectedSpectrum.getAverageFragmentsIntensities()));
				medianFrgIntensities.setText(Float.toString(selectedSpectrum.getMedianFragmentsIntensities()));
				// Update the spectrum view
				SwingUtilities.invokeLater(() -> {
					swingNodeForChart.setContent(spectrumView.getSpectrumPanel());
				});
			} else {
				selectedSpectrum = null;
				spectrumTitle.setText("empty");
				spectrumPrecursor.setText("empty");
				fragmentNumber.setText(Integer.toString(0));
				fragmentMaxMoz.setText(Float.toString(0));
				fragmentMaxIntensity.setText(Float.toString(0));
				averageFrgIntensities.setText(Float.toString(0));
				medianFrgIntensities.setText(Float.toString(0));
				// Update the spectrum view
				SwingUtilities.invokeLater(() -> {
					swingNodeForChart.setContent(new JPanel());
				});
			}
			updateOnJfx(() -> {
				filteredTable.refresh();
			});
		});
		// Update view on spectrum selection
		filteredTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				selectedSpectrum = newValue;
				viewProperty.getSpectrumProperty().setValue(selectedSpectrum);
				filteredTable.refresh();
			}
		});

		filteredTable.addEventHandler(ColumnFilterEvent.FILTER_CHANGED_EVENT, new EventHandler<ColumnFilterEvent>() {
			@Override
			public void handle(ColumnFilterEvent t) {
				// TODO
				// Refresh the the filtered on JFX thread.
				updateOnJfx(() -> {
					ColumnFilters.add(t.sourceColumn().getText(), t.sourceColumn().getFilters());
					applyColumnFilters();
					System.out.println(ColumnFilters.getFullDescription());
				});
			}
		});

	}

	/**
	 * Keep the original items and use a copy of items whenever a filter is
	 * invoked
	 * 
	 * @return The original items (first spectra as observable)
	 */
	private ObservableList<Spectrum> initializeItems() {
		final ObservableList<Spectrum> initialItems = FXCollections
				.observableArrayList(ListOfSpectra.getFirstSpectra().getSpectraAsObservable());
		return initialItems;
	}

	/**
	 * Apply all column filters and refresh the table view.
	 */
	private void applyColumnFilters() {
		// Filter the list of spectrum ...
		Integer nbRecover = 0;
		Float percentageRecover = (float) 0;
		ObservableList<Spectrum> newData = initializeItems();
		System.out.println("INFO - Initial spectra number: " + newData.size());
		// Filter on id
		filterRequest.filterIdColumn(newData, idColumn.getFilters());
		// Filter on M/z
		filterRequest.filterMzColumn(newData, mozColumn.getFilters());
		// Filter on title
		filterRequest.filterTitleColumn(newData, titleColumn.getFilters());
		// Filter on spectrum intensity
		filterRequest.filterIntensityColumn(newData, intensityColumn.getFilters());
		// Filter on charge
		filterRequest.filterChargeColumn(newData, chargeColumn.getFilters());
		// Filter on retention time
		filterRequest.filterRTColumn(newData, rtColumn.getFilters());
		// Filter on fragment intensity
		filterRequest.filterFIntensityColumn(newData, fragmentIntColumn.getFilters());
		// Filter on UPN
		filterRequest.filterUPNColumn(newData, UPNColumn.getFilters());
		// Filter on fragment number
		filterRequest.filterNbrFrgsColumn(newData, nbrFragmentsColumn.getFilters());
		// Filter on identified spectrum
		filterRequest.filterIdentifiedColumn(newData, identifiedColumn.getFilters());
		// Filter on flagged spectrum
		filterRequest.filterFlaggedColumn(newData, flaggedColumn.getFilters());
		// Filter on ion reporter spectrum
		filterRequest.filterIonReporterColumn(newData, ionReporterColumn.getFilters());
		// Filter on wrong charge column
		filterRequest.filterWrongChargeColumn(newData, wrongChargeColumn.getFilters());
		System.out.println("INFO - " + newData.size() + " spectra left after applying filters");
		filteredTable.getItems().setAll(newData);
		filteredTable.refresh();
		if (newData.size() > 0 && initializeItems().size() > 0) {
			nbRecover = newData.size();
			percentageRecover = (((float) nbRecover / (float) initializeItems().size()) * 100);
		}
		viewProperty.setRecoveredNb(String.valueOf(nbRecover));
		viewProperty.setRecoveredPerc(String.format("%.2f", percentageRecover));
	}

	/**
	 * Update the table view on Java-Fx thread
	 * 
	 * @param r
	 *            Runnable to submit
	 */
	private void updateOnJfx(Runnable r) {
		Platform.runLater(r);
	}

}
