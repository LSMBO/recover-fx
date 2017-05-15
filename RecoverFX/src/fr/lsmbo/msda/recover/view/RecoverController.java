package fr.lsmbo.msda.recover.view;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.swing.SwingUtilities;

import org.jfree.chart.ChartPanel;

import fr.lsmbo.msda.recover.io.PeaklistWriter;
import fr.lsmbo.msda.recover.Main;
import fr.lsmbo.msda.recover.Session;
import fr.lsmbo.msda.recover.Views;
import fr.lsmbo.msda.recover.filters.Filter;
import fr.lsmbo.msda.recover.filters.FragmentIntensityFilter;
import fr.lsmbo.msda.recover.filters.HighIntensityThreasholdFilter;
import fr.lsmbo.msda.recover.filters.IdentifiedSpectraFilter;
import fr.lsmbo.msda.recover.filters.IonReporterFilter;
import fr.lsmbo.msda.recover.filters.LowIntensityThreasholdFilter;
import fr.lsmbo.msda.recover.gui.Recover;
import fr.lsmbo.msda.recover.io.ExportBatch;
import fr.lsmbo.msda.recover.io.PeaklistReader;
import fr.lsmbo.msda.recover.lists.Filters;
import fr.lsmbo.msda.recover.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.ComparisonSpectra;
import fr.lsmbo.msda.recover.model.ConstantComparisonSpectra;
import fr.lsmbo.msda.recover.model.Fragment;
import fr.lsmbo.msda.recover.model.Spectrum;
import fr.lsmbo.msda.recover.model.StatusBar;
import fr.lsmbo.msda.recover.model.StatusFilterType;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class RecoverController {

	private final int SIZE_COL_ID = 50;
	// private final int SIZE_COL_TITLE = 200;
	private final int SIZE_COL_MOZ = 100;
	private final int SIZE_COL_INTENSITY = 100;
	private final int SIZE_COL_CHARGE = 75;
	private final int SIZE_COL_RT = 75;
	private final int SIZE_COL_NBFRAGMENTS = 100;
	private final int SIZE_COL_UPN = 75;
	private final int SIZE_COL_IDENTIFIED = 75;
	private final int SIZE_COL_RECOVERED = 75;

	private static Boolean mnComparisonUsed = false;

	private Stage dialogStage;
	// private JFreeChart chart;
	private SpectrumChart spectrumChart;

	// @FXML
	// private MenuItem mnLoadPeaklist;
	@FXML
	private MenuItem mnLoadFirstPeaklist;
	@FXML
	private MenuItem mnLoadSecondPeaklist;
	@FXML
	private MenuItem mnExportFirstPeaklist;
	@FXML
	private MenuItem mnExportSecondPeaklist;
	// @FXML
	// private MenuItem mnExportPeaklist;
	@FXML
	private MenuItem mnExportBatch;
	@FXML
	private MenuItem mnQuit;
	@FXML
	private MenuItem mnOpenParsingRulesManager;
	@FXML
	private MenuItem mnOpenFilterManager;
	@FXML
	private CheckMenuItem mnUseFixedAxis;
	@FXML
	private MenuItem mnIdentifiedSpectra;
	@FXML
	private MenuItem mnComparisonSpectra;
	@FXML
	private MenuItem mnRecoverForFlag;

	@FXML
	private MenuItem mnResetRecover;
	@FXML
	public TableView<Spectrum> table;

	@FXML
	private TableColumn<Spectrum, Boolean> colFlag;
	@FXML
	private TableColumn<Spectrum, Integer> colId;
	@FXML
	private TableColumn<Spectrum, String> colTitle;

	@FXML
	private TableColumn<Spectrum, Float> colMoz;

	@FXML
	private TableColumn<Spectrum, Float> colInt;

	@FXML
	private TableColumn<Spectrum, Integer> colCharge;

	@FXML
	private TableColumn<Spectrum, Float> colRT;

	@FXML
	private TableColumn<Spectrum, Integer> colNbFragments;

	@FXML
	private TableColumn<Spectrum, Integer> colUPN;
	@FXML
	private TableColumn<Spectrum, Boolean> colIdentified;

	@FXML
	private TableColumn<Spectrum, Boolean> colRecover;

	@FXML
	private TableColumn<Spectrum, Integer> colNbMatch;

	@FXML
	private AnchorPane filterAnchor;

	@FXML
	private AnchorPane secondView;

	// @FXML
	// private AnchorPane chartAnchor;
	@FXML
	private SwingNode swingNodeForChart;
	@FXML
	private Label infoHIT;
	@FXML
	private Label infoLIT;
	@FXML
	private Label infoFI;
	@FXML
	private Label infoWC;
	@FXML
	private Label infoIS;
	@FXML
	private Label infoIR;
	@FXML
	private Label statusBar;

	private ComparisonSpectraController comparisonSpectraController = new ComparisonSpectraController();

	@FXML
	private void initialize() {

		// Left view
		Spectra spectra = ListOfSpectra.getFirstSpectra();
		// define spectrum list
		table.setItems(spectra.getSpectraAsObservable());
		colId.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("id"));
		colTitle.setCellValueFactory(new PropertyValueFactory<Spectrum, String>("title"));
		colMoz.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("mz"));
		colInt.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("intensity"));
		colCharge.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("charge"));
		colRT.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("retentionTime"));
		colNbFragments.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("nbFragments"));
		colUPN.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("upn"));
		// colIdentified.setCellValueFactory(new PropertyValueFactory<Spectrum,
		// Boolean>("isIdentified"));
		colIdentified.setCellValueFactory(cellData -> cellData.getValue().identifiedProperty());
		colIdentified.setCellFactory(CheckBoxTableCell.forTableColumn(colIdentified));
		// colRecover.setCellValueFactory(new PropertyValueFactory<Spectrum,
		// Boolean>("isRecover"));
		colRecover.setCellValueFactory(cellData -> cellData.getValue().recoveredProperty());
		colRecover.setCellFactory(CheckBoxTableCell.forTableColumn(colRecover));
		colNbMatch.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("nbMatch"));

		colFlag.setCellValueFactory(new PropertyValueFactory<Spectrum, Boolean>("isFlagged"));
		colFlag.setCellFactory(new Callback<TableColumn<Spectrum, Boolean>, TableCell<Spectrum, Boolean>>() {
			@Override
			public TableCell<Spectrum, Boolean> call(TableColumn<Spectrum, Boolean> param) {
				return new TableCell<Spectrum, Boolean>() {

					@Override
					public void updateItem(Boolean bool, boolean empty) {
						super.updateItem(bool, empty);

						if (!empty) {

							if (bool.booleanValue()) {
								ImageView imageView = new ImageView();
								Image flag = new Image("/flag.png");
								imageView.setImage(flag);
								imageView.setFitWidth(15);
								imageView.setFitHeight(18);
								setGraphic(imageView);
							}
						}
					}
				};
			}
		});

		// set column sizes
		colId.setPrefWidth(SIZE_COL_ID);
		colMoz.setPrefWidth(SIZE_COL_MOZ);
		colInt.setPrefWidth(SIZE_COL_INTENSITY);
		colCharge.setPrefWidth(SIZE_COL_CHARGE);
		colRT.setPrefWidth(SIZE_COL_RT);
		colNbFragments.setPrefWidth(SIZE_COL_NBFRAGMENTS);
		colUPN.setPrefWidth(SIZE_COL_UPN);
		colIdentified.setPrefWidth(SIZE_COL_IDENTIFIED);
		colRecover.setPrefWidth(SIZE_COL_RECOVERED);
		colTitle.prefWidthProperty()
				.bind(table.widthProperty()
						.subtract(SIZE_COL_ID + SIZE_COL_MOZ + SIZE_COL_INTENSITY + SIZE_COL_CHARGE + SIZE_COL_RT
								+ SIZE_COL_NBFRAGMENTS + SIZE_COL_UPN + SIZE_COL_IDENTIFIED + SIZE_COL_RECOVERED + 0));

		mnUseFixedAxis.setSelected(Session.USE_FIXED_AXIS);
		filterAnchor.setPrefWidth(100);
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			// Use the algorithm only if the reference spectrum have at least a
			// number of fragment equals to Session.NB_PEAKS
			if (mnComparisonUsed) {
				if (newSelection.getNbFragments() >= ConstantComparisonSpectra.getNbPeaks()) {
					ComparisonSpectra.main(newSelection);
				} else {
					System.out.println("not enough number of fragment");
					ComparisonSpectra.getValidSpectrum().initialize();
				}
			}
			// // set new data and title
			// chart.setData(SpectrumChart.getData(newSelection));
			// chart.setTitle(newSelection.getTitle());
			// // reset axis values because autoranging is off (necessary to
			// allow fixed axis)
			// resetChartAxis(newSelection);

			// chart = SpectrumChart.getPlot(newSelection);
			spectrumChart = new SpectrumChart(newSelection);
			ChartPanel chartPanel = new ChartPanel(spectrumChart.getChart());
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					swingNodeForChart.setContent(chartPanel);
				}
			});
		});

		ContextMenu contextMenuTable = new ContextMenu();
		MenuItem infoFilter = new MenuItem("Information about filters applied");
		MenuItem flaggedSpectrum = new MenuItem("Flag");
		contextMenuTable.getItems().addAll(infoFilter, flaggedSpectrum);

		table.setContextMenu(contextMenuTable);

		flaggedSpectrum.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Spectrum sp = table.getSelectionModel().selectedItemProperty().get();
				if (sp != null) {
					if (!sp.getIsFlagged() && !sp.getIsRecovered()) {
						sp.setIsFlagged(true);
					} else if (sp.getIsFlagged()) {
						sp.setIsFlagged(false);
						sp.setIsRecovered(false);
					}
					table.refresh();

				}
			}
		});

		infoFilter.setOnAction(new javafx.event.EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Spectrum sp = table.getSelectionModel().selectedItemProperty().get();
				if (sp != null) {
					Filter filter = new Filter();
					filter.applyFiltersForOneSpectrum(sp);

					// Filter HIT
					if (sp.getIsRecoverHIT() == StatusFilterType.TRUE) {
						infoHIT.setText(sp.getIsRecoverHIT().toString());
						infoHIT.setTextFill(Color.GREEN);
						infoHIT.setStyle("-fx-font-weight: bold;-fx-border-style: solid");
					} else if (sp.getIsRecoverHIT() == StatusFilterType.FALSE) {
						infoHIT.setText(sp.getIsRecoverHIT().toString());
						infoHIT.setTextFill(Color.RED);
						infoHIT.setStyle("-fx-font-weight: bold;-fx-border-style: solid");
					} else {
						infoHIT.setText(sp.getIsRecoverHIT().toString());
						infoHIT.setTextFill(Color.BLACK);
						infoHIT.setStyle("-fx-font-weight: normal;-fx-border-style: solid");
					}

					// Filter LIT
					if (sp.getIsRecoverLIT() == StatusFilterType.TRUE) {
						infoLIT.setText(sp.getIsRecoverLIT().toString());
						infoLIT.setTextFill(Color.GREEN);
						infoLIT.setStyle("-fx-font-weight: bold;-fx-border-style: solid");
					} else if (sp.getIsRecoverLIT() == StatusFilterType.FALSE) {
						infoLIT.setText(sp.getIsRecoverLIT().toString());
						infoLIT.setTextFill(Color.RED);
						infoLIT.setStyle("-fx-font-weight: bold;-fx-border-style: solid");
					} else {
						infoLIT.setText(sp.getIsRecoverLIT().toString());
						infoLIT.setTextFill(Color.BLACK);
						infoLIT.setStyle("-fx-font-weight: normal;-fx-border-style: solid");
					}

					// Filter FI
					if (sp.getIsRecoverFI() == StatusFilterType.TRUE) {
						infoFI.setText(sp.getIsRecoverFI().toString());
						infoFI.setTextFill(Color.GREEN);
						infoFI.setStyle("-fx-font-weight: bold;-fx-border-style: solid");
					} else if (sp.getIsRecoverFI() == StatusFilterType.FALSE) {
						infoFI.setText(sp.getIsRecoverFI().toString());
						infoFI.setTextFill(Color.RED);
						infoFI.setStyle("-fx-font-weight: bold;-fx-border-style: solid");
					} else {
						infoFI.setText(sp.getIsRecoverFI().toString());
						infoFI.setTextFill(Color.BLACK);
						infoFI.setStyle("-fx-font-weight: normal;-fx-border-style: solid");
					}

					// Filter WC
					if (sp.getIsRecoverWC() == StatusFilterType.TRUE) {
						infoWC.setText(sp.getIsRecoverWC().toString());
						infoWC.setTextFill(Color.GREEN);
						infoWC.setStyle("-fx-font-weight: bold;-fx-border-style: solid");
					} else if (sp.getIsRecoverWC() == StatusFilterType.FALSE) {
						infoWC.setText(sp.getIsRecoverWC().toString());
						infoWC.setTextFill(Color.RED);
						infoWC.setStyle("-fx-font-weight: bold;-fx-border-style: solid");
					} else {
						infoWC.setText(sp.getIsRecoverWC().toString());
						infoWC.setTextFill(Color.BLACK);
						infoWC.setStyle("-fx-font-weight: normal;-fx-border-style: solid");
					}

					// Filter IS
					if (sp.getIsRecoverIS() == StatusFilterType.TRUE) {
						infoIS.setText(sp.getIsRecoverIS().toString());
						infoIS.setTextFill(Color.GREEN);
						infoIS.setStyle("-fx-font-weight: bold;-fx-border-style: solid");
					} else if (sp.getIsRecoverIS() == StatusFilterType.FALSE) {
						infoIS.setText(sp.getIsRecoverIS().toString());
						infoIS.setTextFill(Color.RED);
						infoIS.setStyle("-fx-font-weight: bold;-fx-border-style: solid");
					} else {
						infoIS.setText(sp.getIsRecoverIS().toString());
						infoIS.setTextFill(Color.BLACK);
						infoIS.setStyle("-fx-font-weight: normal;-fx-border-style: solid");
					}

					// Filter IR
					if (sp.getIsRecoverIR() == StatusFilterType.TRUE) {
						infoIR.setText(sp.getIsRecoverIR().toString());
						infoIR.setTextFill(Color.GREEN);
						infoIR.setStyle("-fx-font-weight: bold;-fx-border-style: solid");
					} else if (sp.getIsRecoverIR() == StatusFilterType.FALSE) {
						infoIR.setText(sp.getIsRecoverIR().toString());
						infoIR.setTextFill(Color.RED);
						infoIR.setStyle("-fx-font-weight: bold;-fx-border-style: solid");
					} else {
						infoIR.setText(sp.getIsRecoverIR().toString());
						infoIR.setTextFill(Color.BLACK);
						infoIR.setStyle("-fx-font-weight: normal;-fx-border-style: solid");
					}

					// Tooltip for HIT and its parameters
					try {
						HighIntensityThreasholdFilter filterHIT = (HighIntensityThreasholdFilter) Filters.getFilters()
								.get("HIT");
						infoHIT.setTooltip(new Tooltip(filterHIT.getFullDescription() + "\n"
								+ "Number of fragment above threshold :" + sp.getNbFragmentAboveHIT()));
					} catch (NullPointerException e) {
					}

					// Tooltip for LIT and its parameters
					try {
						LowIntensityThreasholdFilter filterLIT = (LowIntensityThreasholdFilter) Filters.getFilters()
								.get("LIT");
						infoLIT.setTooltip(new Tooltip(filterLIT.getFullDescription()));
					} catch (NullPointerException e) {
					}

					// Tooltip for FI and its parameters
					try {
						FragmentIntensityFilter filterFI = (FragmentIntensityFilter) Filters.getFilters().get("FI");
						infoFI.setTooltip(new Tooltip(filterFI.getFullDescription()));
					} catch (NullPointerException e) {
					}

					// Tooltip for IS and its parameters
					try {
						IdentifiedSpectraFilter filterIS = (IdentifiedSpectraFilter) Filters.getFilters().get("IS");
						infoIS.setTooltip(new Tooltip(filterIS.getFullDescription()));
					} catch (NullPointerException e) {
					}

					// Tooltip for IR and its parameters
					try {
						IonReporterFilter filterIR = (IonReporterFilter) Filters.getFilters().get("IR");
						infoIR.setTooltip(new Tooltip(filterIR.getFullDescription()));
					} catch (NullPointerException e) {
					}

				}
			}

		});
		// table.getSelectionModel().selectedItemProperty().addListener((observable,
		// oldValue, newValue)->{
		//
		// });

		// // Right view
		// Spectra secondSpectra = ListOfSpectra.getSecondSpectra();
		// // define spectrum list
		// table1.setItems(secondSpectra.getSpectraAsObservable());
		// colId1.setCellValueFactory(new PropertyValueFactory<Spectrum,
		// Integer>("id"));
		// colTitle1.setCellValueFactory(new PropertyValueFactory<Spectrum,
		// String>("title"));
		// colMoz1.setCellValueFactory(new PropertyValueFactory<Spectrum,
		// Float>("mz"));
		// colInt1.setCellValueFactory(new PropertyValueFactory<Spectrum,
		// Float>("intensity"));
		// colCharge1.setCellValueFactory(new PropertyValueFactory<Spectrum,
		// Integer>("charge"));
		// colRT1.setCellValueFactory(new PropertyValueFactory<Spectrum,
		// Float>("retentionTime"));
		// colNbFragments1.setCellValueFactory(new
		// PropertyValueFactory<Spectrum, Integer>("nbFragments"));
		//// colUPN1.setCellValueFactory(new PropertyValueFactory<Spectrum,
		// Integer>("upn"));
		// colCosTheta.setCellValueFactory(new PropertyValueFactory<Spectrum,
		// Double>("cosTheta"));
		//// colRecover1.setCellValueFactory(new PropertyValueFactory<Spectrum,
		// Boolean>("isRecover"));
		// // set column sizes
		// colId1.setPrefWidth(SIZE_COL_ID);
		// colMoz1.setPrefWidth(SIZE_COL_MOZ);
		// colInt1.setPrefWidth(SIZE_COL_INTENSITY);
		// colCharge1.setPrefWidth(SIZE_COL_CHARGE);
		// colRT1.setPrefWidth(SIZE_COL_RT);
		// colNbFragments1.setPrefWidth(SIZE_COL_NBFRAGMENTS);
		//// colUPN1.setPrefWidth(SIZE_COL_UPN);
		// colCosTheta.setPrefWidth(SIZE_COL_IDENTIFIED);
		//// colRecover1.setPrefWidth(SIZE_COL_RECOVERED);
		// colTitle1.prefWidthProperty().bind(table1.widthProperty().subtract(SIZE_COL_ID
		// + SIZE_COL_MOZ
		// + SIZE_COL_INTENSITY + SIZE_COL_CHARGE + SIZE_COL_RT +
		// SIZE_COL_NBFRAGMENTS + 0));
		//
		// mnUseFixedAxis.setSelected(Session.USE_FIXED_AXIS);
		// filterAnchor1.setPrefWidth(100);
		// table1.getSelectionModel().selectedItemProperty().addListener((obs,
		// oldSelection, newSelection) -> {
		//
		// // // set new data and title
		// // chart.setData(SpectrumChart.getData(newSelection));
		// // chart.setTitle(newSelection.getTitle());
		// // // reset axis values because autoranging is off (necessary to
		// // allow fixed axis)
		// // resetChartAxis(newSelection);
		//
		// // chart = SpectrumChart.getPlot(newSelection);
		// spectrumChart = new SpectrumChart(newSelection,
		// Session.SECOND_FILE.getName());
		// ChartPanel chartPanel = new ChartPanel(spectrumChart.getChart());
		// SwingUtilities.invokeLater(new Runnable() {
		// @Override
		// public void run() {
		// swingNodeForChart1.setContent(chartPanel);
		// }
		// });
		// });
		//
		// ContextMenu contextMenuTable1 = new ContextMenu();
		// MenuItem resetTable1 = new MenuItem("Display all spectra");
		//
		// contextMenuTable1.getItems().addAll(resetTable1);
		// table1.setContextMenu(contextMenuTable1);
		//
		// resetTable1.setOnAction(new javafx.event.EventHandler<ActionEvent>()
		// {
		//
		// @Override
		// public void handle(ActionEvent event) {
		// ListOfSpectra.getSecondSpectra().resetCosTheta();
		// resetViewSecondPeaklist();
		// }
		// });

		// chartAnchor.getChildren().add(swingNodeForChart);

		// defineChartMenu();
	}

	// @FXML
	// private void handleMousePressed(MouseEvent m) {
	// if(m.isPrimaryButtonDown()) {
	// selectedXValue = m.getX() - chart.getXAxis().getLayoutX(); // important
	// to subtract layoutX to avoid a shift
	// rectZoom.setWidth(0);
	// rectZoom.setHeight(0);
	// }
	// }
	//
	// @FXML
	// private void handleMouseDragged(MouseEvent m) {
	// if(m.isPrimaryButtonDown()) {
	// double x = m.getX() - chart.getXAxis().getLayoutX();
	// // FIXME restraining movement to the chart area does not work (only on
	// the right)
	// rectZoom.setX(Math.min(x, selectedXValue));
	// rectZoom.setWidth(Math.abs(x - selectedXValue));
	// rectZoom.setHeight(chart.getYAxis().getHeight());
	// rectZoom.setVisible(true);
	// }
	// }
	//
	// @FXML
	// private void handleMouseReleased(MouseEvent m) {
	// // zoom
	// rectZoom.setWidth(0);
	// rectZoom.setHeight(0);
	// rectZoom.setVisible(false);
	// if(m.getButton().equals(MouseButton.SECONDARY)) {
	// // display a menu with some actions (such as reset zoom, fixed axis,
	// filters...)
	// chartMenu.show(chart, m.getScreenX(), m.getScreenY());
	// }
	// }

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	private FileChooser getFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select a peaklist file");
		// default folder is 'Documents'
		File initialDirectory = new File(
				System.getProperty("user.home") + System.getProperty("file.separator") + "Documents");
		// if it does not exist, then it's home folder
		if (!initialDirectory.exists())
			initialDirectory = new File(System.getProperty("user.home"));
		// if a file is already loaded then it's the same folder
		if (Session.CURRENT_FILE != null)
			initialDirectory = Session.CURRENT_FILE.getParentFile();
		fileChooser.setInitialDirectory(initialDirectory);
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All peaklists files", "*.*"),
				new FileChooser.ExtensionFilter("MGF", "*.mgf"), new FileChooser.ExtensionFilter("PKL", "*.pkl"));
		return fileChooser;
	}

	// Action to load the first table with a peaklist
	@FXML
	private void handleClickMenuLoadFirst() {
		FileChooser fileChooser = getFileChooser();
		File file = fileChooser.showOpenDialog(this.dialogStage);
		if (file != null) {
			Recover.useSecondPeaklist = false;
			Session.CURRENT_FILE = file;
			loadFile(file);
			// Filter f = new Filter();
			// f.applyFilters();
		}
		//check if the table is correctly fill
				if(table.getItems().size()==0){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("No spectra found");
					alert.setHeaderText("No spectra imported with this file, please load other file");
					alert.showAndWait();
					handleClickMenuLoadFirst();
				}
		statusBar.setText(StatusBar.refreshStatusBar());
		Filters.resetHashMap();
		// resetViewSecondPeaklist();
	}

	// Action to load the second table with a peaklist
	@FXML
	private void handleClickMenuLoadSecond() {
		FileChooser fileChooser = getFileChooser();
		File file = fileChooser.showOpenDialog(this.dialogStage);
		if (file != null) {
			Recover.useSecondPeaklist = true;
			Session.SECOND_FILE = file;
			loadFile(file);
			// Filter f = new Filter();
			// f.applyFilters();
		}
		//check if the table is correctly fill
				if(ListOfSpectra.getSecondSpectra().getSpectraAsObservable().size() ==0){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("No spectra found");
					alert.setHeaderText("No spectra imported with this file, please load other file");
					alert.showAndWait();
					handleClickMenuLoadSecond();
				}
		statusBar.setText(StatusBar.refreshStatusBar());
		table.refresh();
	}

	public void loadFile(File selectedFile) {
		long startTime = System.currentTimeMillis();
		PeaklistReader.load(selectedFile);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("ABU loading time: " + (double) totalTime / 1000 + " sec");
		System.out.println("ABU " + ListOfSpectra.getFirstSpectra().getNbSpectra() + " spectra");
		System.out.println("ABU " + ListOfSpectra.getSecondSpectra().getNbSpectra() + " spectra");
		table.setItems(ListOfSpectra.getFirstSpectra().getSpectraAsObservable());
		
		
		statusBar.setText(StatusBar.refreshStatusBar());

		this.dialogStage.setTitle(Main.recoverTitle());
		if (PeaklistReader.retentionTimesNotFound()) {
			// open a dialogbox to warn the user that he should try other
			// parsing rules
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Retention times are missing");
			alert.setHeaderText(
					"Retention times could not be extracted from titles, do you want to open the Parsing rules selection list ?");
			ButtonType btnYes = new ButtonType("Yes", ButtonData.YES);
			ButtonType btnNo = new ButtonType("No", ButtonData.NO);
			alert.getButtonTypes().setAll(btnYes, btnNo);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == btnYes) {
				System.out.println("yes");
				handleClickMenuParsingRules();
			} else {
				System.out.println("no");
			}

		}
	}

	@FXML
	private void handleClickMenuExportFirst() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save your new peaklist");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("MGF", "*.mgf"),
				new ExtensionFilter("PKL", "*.pkl"));
		File savedFile = fileChooser.showSaveDialog(this.dialogStage);
		if (savedFile != null) {
			Recover.useSecondPeaklist = false;
			PeaklistWriter.save(savedFile);
		}
		// System.out.println(bottomPanel.getDividerPositions()[0]);

	}

	@FXML
	private void handleClickMenuBatch() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Views.EXPORT_BATCH);
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Batch mode");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.dialogStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			ExportBatchController exportBatchController = loader.getController();
			exportBatchController.setDialogStage(dialogStage);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void handleClickMenuQuit() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit Recover ?");
		alert.setHeaderText("Are you sure ?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			System.exit(0);
		}
	}

	@FXML
	private void handleClickMenuFilters() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Views.FILTER);
			BorderPane page = (BorderPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Apply Filters");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.dialogStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			FilterController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			dialogStage.showAndWait();
			table.refresh();
			// table1.refresh();
			statusBar.setText(StatusBar.refreshStatusBar());
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	@FXML
	private void handleClickMenuParsingRules() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Views.PARSING_RULES);
			BorderPane page = (BorderPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Parsing rules");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.dialogStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			ParsingRulesController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			dialogStage.showAndWait();
			table.refresh();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleClickMenuFixedAxis() {
		Session.USE_FIXED_AXIS = !Session.USE_FIXED_AXIS;
		resetChartAxis(table.getSelectionModel().getSelectedItem());
		if (spectrumChart != null) {
			spectrumChart.changeAxisRange();
		}
	}

	@FXML
	private void handleClickMenuIdendifiedSpectra() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Views.IDENTIFIED_SPECTRA);
			AnchorPane page = (AnchorPane) loader.load();
			Stage identifiedSpectraStage = new Stage();
			identifiedSpectraStage.setTitle("Identified Spectra");
			identifiedSpectraStage.initModality(Modality.WINDOW_MODAL);
			identifiedSpectraStage.initOwner(this.dialogStage);
			Scene scene = new Scene(page);
			identifiedSpectraStage.setScene(scene);
			IdentifiedSpectraController controller = loader.getController();
			controller.setDialogStage(identifiedSpectraStage);
			identifiedSpectraStage.showAndWait();
			statusBar.setText(StatusBar.refreshStatusBar());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleClickMenuComparisonSpectra() {
		try {
			if (!mnComparisonUsed) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Views.COMPARISON_SPECTRA);
				BorderPane page = (BorderPane) loader.load();
				Stage comparisonSpectraStage = new Stage();
				comparisonSpectraStage.setTitle("Menu Comparison Spectra");
				comparisonSpectraStage.initModality(Modality.NONE);
				comparisonSpectraStage.initOwner(this.dialogStage);
				Scene scene = new Scene(page);
				comparisonSpectraStage.setScene(scene);
				// ComparisonSpectraController comparisonSpectraController =
				// loader.getController();
				comparisonSpectraController = loader.getController();
				// comparisonSpectraController.setDialogStage(comparisonSpectraStage);
				comparisonSpectraController.setDialogStage(comparisonSpectraStage);
				mnComparisonUsed = true;
				comparisonSpectraStage.showAndWait();
				if (!comparisonSpectraStage.isShowing()) {
					mnComparisonUsed = false;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void handleClickMenuResetRecover() {
		ListOfSpectra.getFirstSpectra().resetRecover();
		table.refresh();
		Filters.resetHashMap();
	}

	@FXML
	private void handleClickMenuRecoverForFlag() {
		ListOfSpectra.getFirstSpectra().setRecoverForFlaggedSpectrum();
		table.refresh();
	}

	public static void setMnComparisonUsed(Boolean bool) {
		mnComparisonUsed = bool;
	}

	// private void resetViewSecondPeaklist(){
	// table1.setItems(ListOfSpectra.getSecondSpectra().getSpectraAsObservable());
	// }

	// @FXML
	// private void handleCheckRecoverForIdentified() {
	// if (mnCheckRecoverForIdentified.isSelected()) {
	// mnUncheckRecoverForIdentified.setSelected(false);
	// IdentifiedSpectraFilter.setCheckRecoverIdentified(true);
	// IdentifiedSpectraFilter.setUncheckRecoverIdentified(false);
	// } else {
	// IdentifiedSpectraFilter.setCheckRecoverIdentified(false);
	// }
	// }
	//
	// @FXML
	// private void handleUncheckRecoverForIdentified() {
	// if (mnUncheckRecoverForIdentified.isSelected()) {
	// mnCheckRecoverForIdentified.setSelected(false);
	// IdentifiedSpectraFilter.setUncheckRecoverIdentified(true);
	// IdentifiedSpectraFilter.setCheckRecoverIdentified(false);
	// } else
	// IdentifiedSpectraFilter.setUncheckRecoverIdentified(false);
	// }
	//
	// @FXML
	// private void handleCheckRecoverForNonIdentified() {
	// if (mnCheckRecoverForNonIdentified.isSelected()) {
	// mnUncheckRecoverForNonIdentified.setSelected(false);
	// IdentifiedSpectraFilter.setCheckRecoverNonIdentified(true);
	// IdentifiedSpectraFilter.setUncheckRecoverNonIdentified(false);
	// } else
	// IdentifiedSpectraFilter.setCheckRecoverNonIdentified(false);
	// }
	//
	// @FXML
	// private void handleUncheckRecoverForNonIdentified() {
	// if (mnUncheckRecoverForNonIdentified.isSelected()) {
	// mnCheckRecoverForNonIdentified.setSelected(false);
	// IdentifiedSpectraFilter.setUncheckRecoverNonIdentified(true);
	// IdentifiedSpectraFilter.setCheckRecoverNonIdentified(false);
	// } else
	// IdentifiedSpectraFilter.setUncheckRecoverNonIdentified(false);
	//
	// }

	private void resetChartAxis(Spectrum spectrum) {
		// if(chart.getData().size() > 0) {
		// xAxis.setLowerBound(0);
		// xAxis.setUpperBound(SpectrumChart.getUpperBound(spectrum.getFragmentMaxMoz(),
		// true));
		// xAxis.setTickUnit(SpectrumChart.getTickUnit(xAxis.getUpperBound()));
		// yAxis.setLowerBound(0);
		// yAxis.setUpperBound(SpectrumChart.getUpperBound(spectrum.getFragmentMaxIntensity(),
		// false));
		// yAxis.setTickUnit(SpectrumChart.getTickUnit(yAxis.getUpperBound()));
		// }
	}

	public void refreshTable(){
		table.refresh();
	}
	// private void defineChartMenu() {
	// // display a menu with some actions (such as reset zoom, fixed axis,
	// filters...)
	// final MenuItem resetZoomItem = new MenuItem("Reset zoom");
	// resetZoomItem.setOnAction(new EventHandler<ActionEvent>() {
	// @Override
	// public void handle(ActionEvent event) {
	// System.out.println("Reset zoom");
	// }
	// });
	//
	// chartMenu = new ContextMenu(
	// resetZoomItem
	//// mnUseFixedAxis
	// );
	// }
}
