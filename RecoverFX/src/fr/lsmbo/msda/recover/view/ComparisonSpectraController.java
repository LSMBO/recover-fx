package fr.lsmbo.msda.recover.view;

import java.io.IOException;

import javax.swing.SwingUtilities;

import org.jfree.chart.ChartPanel;

import fr.lsmbo.msda.recover.Session;
import fr.lsmbo.msda.recover.Views;
import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.ComparisonSpectra;
import fr.lsmbo.msda.recover.model.Fragment;
import fr.lsmbo.msda.recover.model.Spectrum;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ComparisonSpectraController {

	private Stage dialogComparisonSpectraStage;
	
	private final int SIZE_COL_ID = 50;
	private final int SIZE_COL_MOZ = 100;
	private final int SIZE_COL_INTENSITY = 100;
	private final int SIZE_COL_CHARGE = 75;
	private final int SIZE_COL_RT = 75;
	private final int SIZE_COL_NBFRAGMENTS = 100;
	
	private SpectrumChart spectrumChart;

	@FXML
	private TableView<Spectrum> tableComparison;
	@FXML
	private TableColumn<Spectrum, Integer> colId;
	@FXML
	private TableColumn<Spectrum, String> colTitle;
	@FXML
	private TableColumn<Spectrum, Float> colMoz;
	@FXML
	private TableColumn<Spectrum, Float> colDeltaMoz;
	@FXML
	private TableColumn<Spectrum, Float> colInt;
	@FXML
	private TableColumn<Spectrum, Integer> colCharge;
	@FXML
	private TableColumn<Spectrum, Float> colRT;
	@FXML
	private TableColumn<Spectrum, Integer> colDeltaRT;
	@FXML
	private TableColumn<Spectrum, Integer> colNbFragments;
	@FXML
	private TableColumn<Spectrum, Integer> colNbPeaksIdentical;
	@FXML
	private TableColumn<Spectrum, Double> colCosTheta;
	@FXML
	private SwingNode swingNodeForChart;
	
	private Spectrum referenceSpectrum;

	@FXML
	private MenuItem mnSettingsParameters;

	@FXML
	private void initialize() {
		Spectra validSpectra = ComparisonSpectra.getValidSpectrum();
		
		
		if(validSpectra==null){
		tableComparison.setItems(null);
		}
		else{
			
			tableComparison.setItems(validSpectra.getSpectraAsObservable());
			colId.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("id"));
			colTitle.setCellValueFactory(new PropertyValueFactory<Spectrum, String>("title"));
			colMoz.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("mz"));
			colDeltaMoz.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("deltaMozWithRS"));
			colInt.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("intensity"));
			colCharge.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("charge"));
			colRT.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("retentionTime"));
			colDeltaRT.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("deltaRetentionTimeWithRS"));
			colNbFragments.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("nbFragments"));
			colNbPeaksIdentical.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("nbPeaksIdenticalWithRS"));
			 colCosTheta.setCellValueFactory(new PropertyValueFactory<Spectrum, Double>("cosTheta"));
			// set column sizes
			colId.setPrefWidth(SIZE_COL_ID);
			colMoz.setPrefWidth(SIZE_COL_MOZ);
			colInt.setPrefWidth(SIZE_COL_INTENSITY);
			colCharge.setPrefWidth(SIZE_COL_CHARGE);
			colRT.setPrefWidth(SIZE_COL_RT);
			colNbFragments.setPrefWidth(SIZE_COL_NBFRAGMENTS);


//			mnUseFixedAxis.setSelected(Session.USE_FIXED_AXIS);
//			filterAnchor.setPrefWidth(100);
			tableComparison.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
					
				// // set new data and title
				// chart.setData(SpectrumChart.getData(newSelection));
				// chart.setTitle(newSelection.getTitle());
				// // reset axis values because autoranging is off (necessary to
				// allow fixed axis)
				// resetChartAxis(newSelection);

				if(tableComparison.getItems()!=null){
					referenceSpectrum = ComparisonSpectra.getReferenceSpectrum();
				// chart = SpectrumChart.getPlot(newSelection);
				spectrumChart = new SpectrumChart(referenceSpectrum,newSelection);
				ChartPanel chartPanel = new ChartPanel(spectrumChart.getChart());
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						swingNodeForChart.setContent(chartPanel);
					}
				});
				}
			});
		}
	}

	public void setDialogStage(Stage comparisonSpectraStage) {
		this.dialogComparisonSpectraStage = comparisonSpectraStage;
	}
	
	

	@FXML
	private void handleClickMenuSettingsParameters() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Views.COMPARISON_SETTINGS);
			AnchorPane page = (AnchorPane) loader.load();
			Stage comparisonSettingsStage = new Stage();
			comparisonSettingsStage.setTitle("Settings parameters for comparison spectra");
			comparisonSettingsStage.initModality(Modality.WINDOW_MODAL);
			comparisonSettingsStage.initOwner(this.dialogComparisonSpectraStage);
			Scene scene = new Scene(page);
			comparisonSettingsStage.setScene(scene);
			ComparisonSettingsController comparisonSettingsController = loader.getController();
			comparisonSettingsController.setDialogStage(comparisonSettingsStage);
			comparisonSettingsStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
