package fr.lsmbo.msda.recover.view.component;

import javax.swing.SwingUtilities;

import org.jfree.chart.ChartPanel;

import fr.lsmbo.msda.recover.model.Spectrum;
import fr.lsmbo.msda.recover.util.IconResource;
import fr.lsmbo.msda.recover.util.IconResource.ICON;
import fr.lsmbo.msda.recover.view.SpectrumChart;
import fr.lsmbo.msda.recover.util.WindowSize;

import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * 
 * @author aromdhani
 *
 */
public class Table extends TableView<Spectrum> {
	// table
	private TableColumn<Spectrum, Integer> colId = null;
	private TableColumn<Spectrum, String> colTitle = null;
	private TableColumn<Spectrum, Float> colMoz = null;
	private TableColumn<Spectrum, Float> colInt = null;
	private TableColumn<Spectrum, Integer> colCharge = null;
	private TableColumn<Spectrum, Float> colRT = null;
	private TableColumn<Spectrum, Integer> colNbFragments = null;
	private TableColumn<Spectrum, Integer> colUPN = null;
	private TableColumn<Spectrum, Boolean> colIdentified = null;
	private TableColumn<Spectrum, Boolean> colRecover = null;
	private TableColumn<Spectrum, Integer> colNbMatch = null;
	private TableColumn<Spectrum, Boolean> colFlag = null;

	// columns
	public TableColumn<Spectrum, Boolean> getColFlag() {
		return colFlag;
	}

	public void setColFlag(TableColumn<Spectrum, Boolean> colFlag) {
		this.colFlag = colFlag;
	}

	public TableColumn<Spectrum, Integer> getColId() {
		return colId;
	}

	public void setColId(TableColumn<Spectrum, Integer> colId) {
		this.colId = colId;
	}

	public TableColumn<Spectrum, String> getColTitle() {
		return colTitle;
	}

	public void setColTitle(TableColumn<Spectrum, String> colTitle) {
		this.colTitle = colTitle;
	}

	public TableColumn<Spectrum, Float> getColMoz() {
		return colMoz;
	}

	public void setColMoz(TableColumn<Spectrum, Float> colMoz) {
		this.colMoz = colMoz;
	}

	public TableColumn<Spectrum, Float> getColInt() {
		return colInt;
	}

	public void setColInt(TableColumn<Spectrum, Float> colInt) {
		this.colInt = colInt;
	}

	public TableColumn<Spectrum, Integer> getColCharge() {
		return colCharge;
	}

	public void setColCharge(TableColumn<Spectrum, Integer> colCharge) {
		this.colCharge = colCharge;
	}

	public TableColumn<Spectrum, Float> getColRT() {
		return colRT;
	}

	public void setColRT(TableColumn<Spectrum, Float> colRT) {
		this.colRT = colRT;
	}

	public TableColumn<Spectrum, Integer> getColNbFragments() {
		return colNbFragments;
	}

	public void setColNbFragments(TableColumn<Spectrum, Integer> colNbFragments) {
		this.colNbFragments = colNbFragments;
	}

	public TableColumn<Spectrum, Integer> getColUPN() {
		return colUPN;
	}

	public void setColUPN(TableColumn<Spectrum, Integer> colUPN) {
		this.colUPN = colUPN;
	}

	public TableColumn<Spectrum, Boolean> getColIdentified() {
		return colIdentified;
	}

	public void setColIdentified(TableColumn<Spectrum, Boolean> colIdentified) {
		this.colIdentified = colIdentified;
	}

	public TableColumn<Spectrum, Boolean> getColRecover() {
		return colRecover;
	}

	public void setColRecover(TableColumn<Spectrum, Boolean> colRecover) {
		this.colRecover = colRecover;
	}

	public TableColumn<Spectrum, Integer> getColNbMatch() {
		return colNbMatch;
	}

	public void setColNbMatch(TableColumn<Spectrum, Integer> colNbMatch) {
		this.colNbMatch = colNbMatch;
	}

	private static class Holder {
		private final static Table listPanel = new Table();
	}

	public static Table getInstance() {
		return Holder.listPanel;
	}

	// create a panel with a table View of Spectrum
	private Table() {
		// id
		colId = new TableColumn<>("Id");
		colId.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("id"));

		// title
		colTitle = new TableColumn<>("Title");
		colTitle.setCellValueFactory(new PropertyValueFactory<Spectrum, String>("title"));
		// Mz
		colMoz = new TableColumn<>("Mz");
		colMoz.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("mz"));
		// Intensity
		colInt = new TableColumn<>("Intensity");
		colInt.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("intensity"));
		// charge
		colCharge = new TableColumn<>("Charge");
		colCharge.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("charge"));
		// RT
		colRT = new TableColumn<>("Retention Time");
		colRT.setCellValueFactory(new PropertyValueFactory<Spectrum, Float>("retentionTime"));
		// nombre des fragments
		colNbFragments = new TableColumn<>("Fragment number");
		colNbFragments.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("nbFragments"));
		// Upn
		colUPN = new TableColumn<>("Upn");
		colUPN.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("upn"));
		// Identified
		colIdentified = new TableColumn<>("Identified");
		colIdentified.setCellValueFactory(cellData -> cellData.getValue().identifiedProperty());
		colIdentified.setCellFactory(CheckBoxTableCell.forTableColumn(colIdentified));
		// Recover
		colRecover = new TableColumn<>("Recover");
		colRecover.setCellValueFactory(cellData -> cellData.getValue().recoveredProperty());
		colRecover.setCellFactory(CheckBoxTableCell.forTableColumn(colRecover));
		// number of match
		colNbMatch = new TableColumn<>("Number Match");
		colNbMatch.setCellValueFactory(new PropertyValueFactory<Spectrum, Integer>("nbMatch"));
		// flag
		colFlag = new TableColumn<>("Flag");
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
								ImageView imageView = new ImageView(IconResource.getImage(ICON.FLAG));
								imageView.setFitWidth(15);
								imageView.setFitHeight(18);
								setGraphic(imageView);
							}
						}
					}
				};
			}
		});

		this.getColumns().addAll(colFlag, colId, colTitle, colMoz, colInt, colCharge, colRT, colNbFragments, colUPN,
				colIdentified, colRecover, colNbMatch);
		this.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

			// // set new data and title
			// chart.setData(SpectrumChart.getData(newSelection));
			// chart.setTitle(newSelection.getTitle());
			// // reset axis values because autoranging is off (necessary to
			// allow fixed axis)
			// resetChartAxis(newSelection);

			// chart = SpectrumChart.getPlot(newSelection);
			// SpectrumChart spectrumChart = new SpectrumChart(newSelection);
			// ChartPanel chartPanel = new ChartPanel(spectrumChart.getChart());
			// SwingUtilities.invokeLater(new Runnable() {
			// @Override
			// public void run() {
			// SpectrumPanel.getInstance().getSwingNodeForChart().setContent(spectrumChart);
			// }
			// });
		});

		this.autosize();
		this.setColumnResizePolicy(this.CONSTRAINED_RESIZE_POLICY);
		this.setPrefSize(WindowSize.mainPanePreferWidth, WindowSize.mainPanePreferHeight / 5);
		this.setPadding(new Insets(5, 5, 5, 5));
	}
}
