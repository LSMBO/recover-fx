package fr.lsmbo.msda.recover.view.popup;

import java.util.ArrayList;
import java.util.List;

import fr.lsmbo.msda.recover.util.WindowSize;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FiltersPane extends Accordion {

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

	//
	private CheckBox fHighIntensityChbx = null;
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

	//
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
		removeFragmentIntTField = new TextField();
		/**
		 * Style
		 */
		List<Label> list = new ArrayList<Label>();
		// list.add(mostIntensetTField);
		// list.add(percentageTopLineTFiled);
		// list.add(numberPeaksTField);
		// list.add(emergenceTField);
		// list.add(minUsefulTFiled);
		// list.add(maxUsefulTField);
		// list.add(removeFragmentIntTField);

		list.add(mostIntensetLabel);
		list.add(percentageTopLineLabel);
		list.add(numberPeaksLabel);

		list.add(emergenceLabel);
		list.add(minUsefulLabel);
		list.add(maxUsefulLabel);
		list.add(removeFragmentIntLabel);
		
		for (Label label : list) {
			label.setMinWidth(130);
		}

		/**
		 * layout
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
		fLowInHbox.autosize();

		VBox fLowInVbox = new VBox(20);
		fLowInVbox.getChildren().addAll(fLowIntensityChbx, fLowInHbox);
		fLowInVbox.autosize();
		// line 3
		HBox fRemoveInHox = new HBox(10);
		fRemoveInHox.getChildren().addAll(removeFragmentIntLabel, removeFragInetesityCBox, removeFragmentIntTField);
		fRemoveInHox.autosize();

		VBox fRemoveInVbox = new VBox(20);
		fLowInVbox.getChildren().addAll(fFragmentIntensityChbx, fRemoveInHox);
		fRemoveInVbox.autosize();

		VBox filterPane1 = new VBox(20);
		filterPane1.getChildren().addAll(fHighInVbox, fLowInVbox, fRemoveInVbox);
		// end filter 1
		filterPane1.setPrefSize(WindowSize.popupPrefWidth, WindowSize.popupPrefHeight);
		filterPane1.autosize();
		// second filter
		VBox filterPanel2 = new VBox();
		filterPanel2.setPrefSize(WindowSize.popupPrefWidth, WindowSize.popupPrefHeight);
		VBox filterPanel3 = new VBox();
		filterPanel3.setPrefSize(WindowSize.popupPrefWidth, WindowSize.popupPrefHeight);
		TitledPane filetr1 = new TitledPane("Filter 1", filterPane1);
		filetr1.autosize();
		this.getPanes().addAll(filetr1, new TitledPane("Filter 2", filterPanel2),
				new TitledPane("Filter 3", filterPanel3));
		this.autosize();
		this.setExpandedPane(filetr1);

	}
}
