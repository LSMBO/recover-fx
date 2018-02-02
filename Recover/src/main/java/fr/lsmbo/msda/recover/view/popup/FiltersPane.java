package fr.lsmbo.msda.recover.view.popup;

import java.util.ArrayList;
import java.util.List;

import fr.lsmbo.msda.recover.util.IconResource;
import fr.lsmbo.msda.recover.util.WindowSize;
import fr.lsmbo.msda.recover.util.IconResource.ICON;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FiltersPane extends Accordion {

	//
	private CheckBox fHighIntensityChbx = null;

	public CheckBox getfHighIntensityChbx() {
		return fHighIntensityChbx;
	}

	public void setfHighIntensityChbx(CheckBox fHighIntensityChbx) {
		this.fHighIntensityChbx = fHighIntensityChbx;
	}

	public TextField getMostIntensetTField() {
		return mostIntensetTField;
	}

	public void setMostIntensetTField(TextField mostIntensetTField) {
		this.mostIntensetTField = mostIntensetTField;
	}

	public TextField getPercentageTopLineTFiled() {
		return percentageTopLineTFiled;
	}

	public void setPercentageTopLineTFiled(TextField percentageTopLineTFiled) {
		this.percentageTopLineTFiled = percentageTopLineTFiled;
	}

	public TextField getNumberPeaksTField() {
		return numberPeaksTField;
	}

	public void setNumberPeaksTField(TextField numberPeaksTField) {
		this.numberPeaksTField = numberPeaksTField;
	}

	public CheckBox getfLowIntensityChbx() {
		return fLowIntensityChbx;
	}

	public void setfLowIntensityChbx(CheckBox fLowIntensityChbx) {
		this.fLowIntensityChbx = fLowIntensityChbx;
	}

	public TextField getEmergenceTField() {
		return emergenceTField;
	}

	public void setEmergenceTField(TextField emergenceTField) {
		this.emergenceTField = emergenceTField;
	}

	public TextField getMinUsefulTFiled() {
		return minUsefulTFiled;
	}

	public void setMinUsefulTFiled(TextField minUsefulTFiled) {
		this.minUsefulTFiled = minUsefulTFiled;
	}

	public TextField getMaxUsefulTField() {
		return maxUsefulTField;
	}

	public void setMaxUsefulTField(TextField maxUsefulTField) {
		this.maxUsefulTField = maxUsefulTField;
	}

	public CheckBox getfFragmentIntensityChbx() {
		return fFragmentIntensityChbx;
	}

	public void setfFragmentIntensityChbx(CheckBox fFragmentIntensityChbx) {
		this.fFragmentIntensityChbx = fFragmentIntensityChbx;
	}

	public ComboBox<String> getRemoveFragInetesityCBox() {
		return removeFragInetesityCBox;
	}

	public void setRemoveFragInetesityCBox(ComboBox<String> removeFragInetesityCBox) {
		this.removeFragInetesityCBox = removeFragInetesityCBox;
	}

	public TextField getRemoveFragmentIntTField() {
		return removeFragmentIntTField;
	}

	public void setRemoveFragmentIntTField(TextField removeFragmentIntTField) {
		this.removeFragmentIntTField = removeFragmentIntTField;
	}

	public CheckBox getfWrongChargeChbx() {
		return fWrongChargeChbx;
	}

	public void setfWrongChargeChbx(CheckBox fWrongChargeChbx) {
		this.fWrongChargeChbx = fWrongChargeChbx;
	}

	public CheckBox getfIdentifiedSpectraChbx() {
		return fIdentifiedSpectraChbx;
	}

	public void setfIdentifiedSpectraChbx(CheckBox fIdentifiedSpectraChbx) {
		this.fIdentifiedSpectraChbx = fIdentifiedSpectraChbx;
	}

	public CheckBox getfIonReporterChbx() {
		return fIonReporterChbx;
	}

	public void setfIonReporterChbx(CheckBox fIonReporterChbx) {
		this.fIonReporterChbx = fIonReporterChbx;
	}

	public TableView<String> getIonReporterTable() {
		return ionReporterTable;
	}

	public void setIonReporterTable(TableView<String> ionReporterTable) {
		this.ionReporterTable = ionReporterTable;
	}

	private TextField mostIntensetTField = null;
	private TextField percentageTopLineTFiled = null;
	private TextField numberPeaksTField = null;

	//
	private CheckBox fLowIntensityChbx = null;
	private TextField emergenceTField = null;
	private TextField minUsefulTFiled = null;
	private TextField maxUsefulTField = null;

	//
	private CheckBox fFragmentIntensityChbx = null;
	private ComboBox<String> removeFragInetesityCBox = null;
	private TextField removeFragmentIntTField = null;
	//
	private CheckBox fWrongChargeChbx = null;
	private CheckBox fIdentifiedSpectraChbx = null;
	private CheckBox fIonReporterChbx = null;
	//
	TableView<String> ionReporterTable = null;

	public FiltersPane() {
		// component
		fHighIntensityChbx = new CheckBox("Filter by high intensity threshold");

		Label mostIntensetLabel = new Label("Most peaks to consider");
		Label percentageTopLineLabel = new Label("Percentage of top line");
		Label numberPeaksLabel = new Label("Number of Peaks");

		mostIntensetTField = new TextField();
		percentageTopLineTFiled = new TextField();
		numberPeaksTField = new TextField();
		//
		fLowIntensityChbx = new CheckBox("Filter by low intensity threshold");

		Label emergenceLabel = new Label("Emergence  ");
		Label minUsefulLabel = new Label("Min useful peaks number");
		Label maxUsefulLabel = new Label("Max useful peaks number");
		emergenceTField = new TextField();
		minUsefulTFiled = new TextField();
		maxUsefulTField = new TextField();

		//
		fFragmentIntensityChbx = new CheckBox("Filter by fragment intensity");
		Label removeFragmentIntLabel = new Label("Remove fragment intensity");
		removeFragInetesityCBox = new ComboBox<String>();
		removeFragInetesityCBox.getItems().addAll("=", "<", "<=", ">", ">=", "!=");
		removeFragInetesityCBox.getSelectionModel().selectFirst();
		removeFragmentIntTField = new TextField();
		//
		Label mZLabel = new Label("M/Z");
		mZLabel.setMinWidth(100);

		Label toleranceLabel = new Label("Tolerance");
		toleranceLabel.setMinWidth(100);
		Label nameLabel = new Label("Name");
		nameLabel.setMinWidth(100);
		// accept only double value
		TextField mZTf = new TextField();
		mZTf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,9})?")) {
					mZTf.setText(oldValue);
				}
			}
		});
		mZTf.setTooltip(new Tooltip("Please insert numeric values"));
		// accept only double value
		TextField toleranceTf = new TextField();
		toleranceTf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,9})?")) {
					toleranceTf.setText(oldValue);
				}
			}
		});
		toleranceTf.setTooltip(new Tooltip("Please insert numeric values"));
		TextField nameTf = new TextField();

		/**
		 * Style
		 */

		List<Label> list = new ArrayList<Label>();

		list.add(mostIntensetLabel);
		list.add(percentageTopLineLabel);
		list.add(numberPeaksLabel);
		list.add(emergenceLabel);
		list.add(minUsefulLabel);
		list.add(maxUsefulLabel);
		for (Label label : list) {
			label.setPrefWidth(140);
		}
		List<TextField> listTf = new ArrayList<TextField>();

		listTf.add(mostIntensetTField);
		listTf.add(percentageTopLineTFiled);
		listTf.add(numberPeaksTField);
		listTf.add(emergenceTField);
		listTf.add(minUsefulTFiled);
		listTf.add(maxUsefulTField);
		listTf.add(removeFragmentIntTField);
		listTf.add(mZTf);
		listTf.add(toleranceTf);
		listTf.add(nameTf);
		for (TextField tf : listTf) {
			tf.setPrefWidth(130);
		}

		/**
		 * Layout
		 */

		// filter 1
		// line 1
		HBox fMostInetnseHbox = new HBox(5);
		fMostInetnseHbox.getChildren().addAll(mostIntensetLabel, mostIntensetTField);

		HBox fToplineHbox = new HBox(5);
		fToplineHbox.getChildren().addAll(percentageTopLineLabel, percentageTopLineTFiled);

		HBox fnumberPeaksHbox = new HBox(5);
		fnumberPeaksHbox.getChildren().addAll(numberPeaksLabel, numberPeaksTField);

		HBox fHighInHbox = new HBox(20);
		fHighInHbox.getChildren().addAll(fMostInetnseHbox, fToplineHbox, fnumberPeaksHbox);
		fHighInHbox.disableProperty().bind(this.fHighIntensityChbx.selectedProperty().not());
		fHighInHbox.autosize();

		VBox fHighInVbox = new VBox(20);
		fHighInVbox.getChildren().addAll(fHighIntensityChbx, fHighInHbox);
		fHighInVbox.autosize();

		// line 2
		HBox femergenceHbox = new HBox(5);
		femergenceHbox.getChildren().addAll(emergenceLabel, emergenceTField);

		HBox fMinUsefulHbox = new HBox(5);
		fMinUsefulHbox.getChildren().addAll(minUsefulLabel, minUsefulTFiled);

		HBox fMaxUsefulHbox = new HBox(5);
		fMaxUsefulHbox.getChildren().addAll(maxUsefulLabel, maxUsefulTField);

		HBox fLowInHbox = new HBox(20);
		fLowInHbox.getChildren().addAll(femergenceHbox, fMinUsefulHbox, fMaxUsefulHbox);
		fLowInHbox.disableProperty().bind(this.fLowIntensityChbx.selectedProperty().not());
		fLowInHbox.autosize();

		VBox fLowInVbox = new VBox(20);
		fLowInVbox.getChildren().addAll(fLowIntensityChbx, fLowInHbox);
		fLowInVbox.autosize();
		// line 3
		HBox fRemoveInHox = new HBox(20);
		fRemoveInHox.getChildren().addAll(removeFragmentIntLabel, removeFragInetesityCBox, removeFragmentIntTField);
		fRemoveInHox.autosize();

		VBox fRemoveInVbox = new VBox(20);
		fLowInVbox.getChildren().addAll(fFragmentIntensityChbx, fRemoveInHox);
		fRemoveInHox.disableProperty().bind(this.fFragmentIntensityChbx.selectedProperty().not());
		fRemoveInVbox.autosize();

		VBox filterPane1 = new VBox(25);
		filterPane1.getChildren().addAll(fHighInVbox, fLowInVbox, fRemoveInVbox);
		filterPane1.setPrefSize(WindowSize.popupPrefWidth, WindowSize.popupPrefHeight);
		filterPane1.autosize();
		// end filter 1

		// filter 2
		// line 1
		fWrongChargeChbx = new CheckBox("Filter Wrong Charge");
		// line 2
		fIdentifiedSpectraChbx = new CheckBox("Filter Identifed Spectra");
		ToggleGroup tg = new ToggleGroup();
		RadioButton identifiedSpecRB = new RadioButton("Recover For Identified Spectra");
		identifiedSpecRB.setToggleGroup(tg);
		RadioButton noIdentifiedSpecRB = new RadioButton("Recover For No Identified Spectra");
		noIdentifiedSpecRB.setToggleGroup(tg);

		HBox toggleGroupHbox = new HBox(150);
		toggleGroupHbox.getChildren().addAll(identifiedSpecRB, noIdentifiedSpecRB);
		toggleGroupHbox.setAlignment(Pos.BASELINE_CENTER);

		VBox identifedSpecVbox = new VBox(25);
		identifedSpecVbox.getChildren().addAll(fIdentifiedSpectraChbx, toggleGroupHbox);
		toggleGroupHbox.disableProperty().bind(this.fIdentifiedSpectraChbx.selectedProperty().not());

		VBox filterPanel2 = new VBox(50);
		filterPanel2.getChildren().addAll(new Label(), fWrongChargeChbx, identifedSpecVbox);
		filterPanel2.setPrefSize(WindowSize.popupPrefWidth, WindowSize.popupPrefHeight);
		// end filter 2

		// Filter 3
		fIonReporterChbx = new CheckBox("Filter Ion Reporter");

		// first line
		HBox fMzHbox = new HBox(5);
		fMzHbox.getChildren().addAll(mZLabel, mZTf);

		HBox fToleranceHbox = new HBox(5);
		fToleranceHbox.getChildren().addAll(toleranceLabel, toleranceTf);

		HBox fNameHbox = new HBox(5);
		fNameHbox.getChildren().addAll(nameLabel, nameTf);
		Button addBut = new Button("Add");
		addBut.setGraphic(new ImageView(IconResource.getImage(ICON.PLUS)));
		addBut.setPrefWidth(100);
		HBox insertDataHbox = new HBox(20);
		insertDataHbox.getChildren().addAll(fMzHbox, fToleranceHbox, fNameHbox, addBut);
		// end first line

		// second line
		ionReporterTable = new TableView<String>();
		TableColumn mzCol = new TableColumn("Mz");
		TableColumn tolCol = new TableColumn("Tolerance");
		TableColumn nameCol = new TableColumn("Name");
		ionReporterTable.setColumnResizePolicy(ionReporterTable.CONSTRAINED_RESIZE_POLICY);
		ionReporterTable.getColumns().addAll(mzCol, tolCol, nameCol);
		ionReporterTable.setPrefWidth(WindowSize.popupPrefWidth * 0.75);
		Button resetBut = new Button("Reset");
		resetBut.setGraphic(new ImageView(IconResource.getImage(ICON.RESET)));
		resetBut.setPrefWidth(100);
		HBox showDataHbox = new HBox(20);
		showDataHbox.getChildren().addAll(ionReporterTable, resetBut);
		// end second line
		VBox filterPanel3 = new VBox(50);
		filterPanel3.getChildren().addAll(fIonReporterChbx, insertDataHbox, showDataHbox);
		insertDataHbox.disableProperty().bind(this.fIonReporterChbx.selectedProperty().not());
		showDataHbox.disableProperty().bind(this.fIonReporterChbx.selectedProperty().not());
		// end filter 3

		filterPanel3.setPrefSize(WindowSize.popupPrefWidth, WindowSize.popupPrefHeight);
		TitledPane filetr1 = new TitledPane("Filter 1", filterPane1);
		filetr1.autosize();
		this.getPanes().addAll(filetr1, new TitledPane("Filter 2", filterPanel3),
				new TitledPane("Filter 3", filterPanel2));
		this.autosize();
		this.setExpandedPane(filetr1);
	}
}
