
package fr.lsmbo.msda.recover.gui.view.dialog;

import java.util.ArrayList;
import java.util.List;

import fr.lsmbo.msda.recover.gui.IconResource;
import fr.lsmbo.msda.recover.gui.IconResource.ICON;
import fr.lsmbo.msda.recover.gui.filters.ColumnFilters;
import fr.lsmbo.msda.recover.gui.filters.IonReporterFilter;
import fr.lsmbo.msda.recover.gui.lists.IonReporters;
import fr.lsmbo.msda.recover.gui.model.IonReporter;
import fr.lsmbo.msda.recover.gui.util.JavaFxUtils;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Creates and displays ion reporter filter dialog
 * 
 * @author Aromdhani
 *
 */
public class FilterIonReporterDialog extends Dialog<Object> {

	private IonReporterFilter filterIR = new IonReporterFilter();

	/**
	 * Default constructor: Creates dialog components.
	 */
	public FilterIonReporterDialog() {

		// Create notifications pane
		VBox filter3WarningPane = new VBox(2);
		Label emptyDataTableErrorLabel = new Label("The table must not be empty. Please insert data!");
		emptyDataTableErrorLabel.setGraphic(new ImageView(IconResource.getImage(ICON.WARNING)));
		emptyDataTableErrorLabel.setStyle(JavaFxUtils.RED_ITALIC);
		filter3WarningPane.getChildren().addAll(emptyDataTableErrorLabel);

		// Create TiteledPane 2 components

		/* Create filter ion reporter */
		Label mzLabel = new Label("M/z");
		Label toleranceLabel = new Label("Tolerance");
		Label nameLabel = new Label("Name");
		TextField mzTextField = new TextField();
		TextField toleranceTextField = new TextField();
		TextField nameTextField = new TextField();
		Button insertDataButton = new Button("Insert data");
		Button clearDataButton = new Button("Clear all data");
		TableView<IonReporter> ionReporterTable = new TableView<IonReporter>(IonReporters.getIonReporters());
		TableColumn mzCol = new TableColumn("M/z");
		mzCol.setCellValueFactory(new PropertyValueFactory<IonReporter, Float>("moz"));
		TableColumn toleranceCol = new TableColumn("Tolerance");
		toleranceCol.setCellValueFactory(new PropertyValueFactory<IonReporter, Float>("tolerance"));
		TableColumn nameCol = new TableColumn("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<IonReporter, String>("name"));

		ionReporterTable.getColumns().addAll(mzCol, toleranceCol, nameCol);
		ionReporterTable.autosize();
		ionReporterTable.setColumnResizePolicy(ionReporterTable.CONSTRAINED_RESIZE_POLICY);

		// Layout
		GridPane filter3Pane = new GridPane();
		filter3Pane.setPadding(new Insets(10));
		filter3Pane.setHgap(25);
		filter3Pane.setVgap(25);
		filter3Pane.add(filter3WarningPane, 0, 0, 4, 1);
		filter3Pane.addRow(2, mzLabel, toleranceLabel, nameLabel);
		filter3Pane.addRow(3, mzTextField, toleranceTextField, nameTextField, ionReporterTable);
		filter3Pane.add(insertDataButton, 1, 4, 1, 1);
		filter3Pane.add(clearDataButton, 2, 4, 1, 1);

		// Check input fields
		// mz and tolerance accept only float values
		addFloatValidation(mzTextField);
		addFloatValidation(toleranceTextField);
		// Show notification
		insertDataButton.disableProperty().bind((mzTextField.textProperty().isEmpty()
				.or(toleranceTextField.textProperty().isEmpty().or(nameTextField.textProperty().isEmpty()))));
		SimpleBooleanProperty isEmptyTableProperty = new SimpleBooleanProperty(true);
		emptyDataTableErrorLabel.visibleProperty().bind(isEmptyTableProperty);
		// Add/remove ion reporter data
		insertDataButton.setOnAction(e -> {
			IonReporter ionReporter = new IonReporter(nameTextField.getText(), Float.parseFloat(mzTextField.getText()),
					Float.parseFloat(toleranceTextField.getText()));
			IonReporters.addIonReporter(ionReporter);
			if (ionReporterTable.getItems().isEmpty())
				isEmptyTableProperty.set(true);
			else
				isEmptyTableProperty.set(false);
		});
		clearDataButton.setOnAction(e -> {
			mzTextField.clear();
			toleranceTextField.clear();
			nameTextField.clear();
		});
		// Create and display the main dialog pane
		DialogPane dialogPane = new DialogPane();
		dialogPane.setContent(filter3Pane);
		dialogPane.setHeaderText("Apply Ion Reporter Filter");
		dialogPane.setGraphic(new ImageView(IconResource.getImage(ICON.APPLYFILTER)));
		dialogPane.setPrefSize(800, 500);
		Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new ImageView(IconResource.getImage(ICON.APPLYFILTER)).getImage());
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Button buttonOk = (Button) dialogPane.lookupButton(ButtonType.OK);
		this.setTitle("Ion Reporter Filter");
		this.setDialogPane(dialogPane);
		// Enable ok button when all selected filters are valid.
		buttonOk.disableProperty().bind(emptyDataTableErrorLabel.visibleProperty());
		// Initialize ion reporter filter
		if (ColumnFilters.getApplied().containsKey("IR") && ColumnFilters.getApplied().get("IR").size() > 0) {
			filterIR = (IonReporterFilter) ColumnFilters.getApplied().get("IR").get(0);
			if (ionReporterTable.getItems().isEmpty())
				isEmptyTableProperty.set(true);
			else
				isEmptyTableProperty.set(false);
		}
		// On apply button
		this.setResultConverter(buttonType -> {
			if (buttonType == ButtonType.OK) {
				// Filter by ion reporter
				List<Object> listIR = new ArrayList<>();
				listIR.add(filterIR);
				if (!IonReporters.getIonReporters().isEmpty()) {
					ColumnFilters.add("IR", listIR);
					return listIR;
				} else {
					return null;
				}
			} else {
				return null;
			}
		});
	}

	/**
	 * Input value must be a valid float
	 * 
	 * @param field
	 *            the textFiled to set text formatter.
	 **/
	private void addFloatValidation(TextField field) {
		field.getProperties().put("type", "float");
		field.setTextFormatter(new TextFormatter<>(c -> {
			if (c.isContentChange()) {
				if (c.getControlNewText().length() == 0) {
					return c;
				}
				try {
					Float.parseFloat(c.getControlNewText());
					return c;
				} catch (NumberFormatException e) {
				}
				return null;
			}
			return c;
		}));
	}
}