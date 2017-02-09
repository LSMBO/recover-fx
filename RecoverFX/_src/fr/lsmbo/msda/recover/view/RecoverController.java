package fr.lsmbo.msda.recover.view;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.pixelduke.javafx.chart.XYBarChart;

import fr.lsmbo.msda.recover.Main;
import fr.lsmbo.msda.recover.Session;
import fr.lsmbo.msda.recover.Views;
import fr.lsmbo.msda.recover.io.PeaklistReader;
import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.Fragment;
import fr.lsmbo.msda.recover.model.Spectrum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RecoverController {
	
	private Stage dialogStage;
//	private ObservableList<String> chartMasses = FXCollections.observableArrayList();
	
	@FXML
	private MenuItem mnLoadPeaklist;
	@FXML
	private MenuItem mnExportPeaklist;
	@FXML
	private MenuItem mnExportBatch;
	@FXML
	private MenuItem mnQuit;
	@FXML
	private MenuItem mnOpenParsingRulesManager;
	@FXML
	private MenuItem mnOpenFilterManager;
	@FXML
	private TableView<Spectrum> table;
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
	private AnchorPane chartAnchor;

	@FXML
	private void initialize() {
		// define spectrum list
		table.setItems(Spectra.getSpectraAsObservable());
        colId.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("id"));
		colTitle.setCellValueFactory(new PropertyValueFactory<Spectrum, String>("title"));
		colMoz.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("mz"));
		colInt.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("intensity"));
		colCharge.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("charge"));
		colRT.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("retentionTime"));
		colNbFragments.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("nbFragments"));
		colUPN.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("upn"));
		colIdentified.setCellValueFactory(new PropertyValueFactory<Spectrum, Boolean>("isIdentified"));
		colIdentified.setCellValueFactory(new PropertyValueFactory<Spectrum, Boolean>("isRecover"));
		
//		xAxis.setCategories(chartMasses);
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//			chartMasses.clear();
//			XYChart.Series<String, Float> series = new Series<String, Float>();
//			for(Fragment f: newSelection.getFragments()) {
//				String strMoz = String.valueOf(f.getMz());
//				chartMasses.add(strMoz);
//				series.getData().add(new Data<String, Float>(strMoz, f.getIntensity()));
//			}
//			
//			xAxis.setCategories(chartMasses);
//			chart.getData().add(series);
			NumberAxis xAxis = new NumberAxis("M/z", 0, newSelection.getFragmentMaxMoz(), 100);
			NumberAxis yAxis = new NumberAxis("Intensity", 0, newSelection.getFragmentMaxIntensity(), 100);
//			XYBarChart<Float, Float> chart = new XYBarChart<Float, Float>(xAxis, yAxis);
		});
	}
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	
	private FileChooser getFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select a peaklist file");
		// default folder is 'Documents'
		File initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Documents");
		// if it does not exist, then it's home folder
		if(!initialDirectory.exists())
			initialDirectory = new File(System.getProperty("user.home"));
		// if a file is already loaded then it's the same folder
		if(Session.CURRENT_FILE != null)
			initialDirectory = Session.CURRENT_FILE.getParentFile();
		fileChooser.setInitialDirectory(initialDirectory);
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All peaklists files", "*.*"),
                new FileChooser.ExtensionFilter("MGF", "*.mgf"),
                new FileChooser.ExtensionFilter("PKL", "*.pkl")
            );
		return fileChooser;
	}
	
	@FXML
	private void handleClickMenuLoad() {
		FileChooser fileChooser = getFileChooser();
		File file = fileChooser.showOpenDialog(this.dialogStage);
		if(file != null)
			loadFile(file);
	}
	
	public void loadFile(File selectedFile) {
		long startTime = System.currentTimeMillis();
		PeaklistReader.load(selectedFile);
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("ABU loading time: "+totalTime+" ms");
		System.out.println("ABU "+Spectra.getNbSpectra()+" spectra");
		this.dialogStage.setTitle(Main.recoverTitle());
		if(PeaklistReader.retentionTimesNotFound()) {
			// open a dialogbox to warn the user that he should try other parsing rules
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Retention times are missing");
			alert.setHeaderText("Retention times could not be extracted from titles, do you want to open the Parsing rules selection list ?");
			ButtonType btnYes = new ButtonType("Yes", ButtonData.YES);
			ButtonType btnNo = new ButtonType("No", ButtonData.NO);
			alert.getButtonTypes().setAll(btnYes, btnNo);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == btnYes){
				System.out.println("yes");
				handleClickMenuParsingRules();
			} else {
				System.out.println("no");
			}
			
		}
	}
	
	@FXML
	private void handleClickMenuExport() {
		
	}
	
	@FXML
	private void handleClickMenuBatch() {
	}
	
	@FXML
	private void handleClickMenuQuit() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit Recover ?");
		alert.setHeaderText("Are you sure ?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			System.exit(0);
		}
	}
	
	@FXML
	private void handleClickMenuFilters() {
		
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
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
//	@FXML
//	private void handleTableSelection() {
//		Spectrum spectrum = table.getSelectionModel().getSelectedItem();
//		
//		chartMasses.clear();
//		XYChart.Series<Float, Float> series = new Series<Float, Float>(); 
//		for(Fragment f: spectrum.getFragments()) {
//			chartMasses.add(""+f.getMz());
//			series.getData().add(new Data<Float, Float>(f.getMz(), f.getIntensity()));
//		}
//		
//		xAxis.setCategories(chartMasses);
//		chart.getData().add(series);
//	}
	
//	public void setSpectrum(Spectrum spectrum) {
//		
//		chartMasses.clear();
//		
//		XYChart.Series<Float, Float> series = new Series<Float, Float>(); 
//		for(Fragment f: spectrum.getFragments()) {
//			chartMasses.add(""+f.getMz());
//			series.getData().add(new Data<Float, Float>(f.getMz(), f.getIntensity()));
//		}
//		
//		xAxis.setCategories(chartMasses);
//		chart.getData().add(series);
//	}
}
