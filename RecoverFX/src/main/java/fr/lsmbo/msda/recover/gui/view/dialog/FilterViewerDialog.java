package fr.lsmbo.msda.recover.gui.view.dialog;

import java.util.ArrayList;
import java.util.Map;

import org.google.jhsheets.filtered.operators.BooleanOperator;
import org.google.jhsheets.filtered.operators.NumberOperator;
import org.google.jhsheets.filtered.operators.StringOperator;

import fr.lsmbo.msda.recover.gui.IconResource;
import fr.lsmbo.msda.recover.gui.IconResource.ICON;
import fr.lsmbo.msda.recover.gui.filters.ColumnFilters;
import fr.lsmbo.msda.recover.gui.filters.IonReporterFilter;
import fr.lsmbo.msda.recover.gui.filters.LowIntensityThresholdFilter;
import fr.lsmbo.msda.recover.gui.io.FilterReaderJson;
import fr.lsmbo.msda.recover.gui.lists.IdentifiedSpectra;
import fr.lsmbo.msda.recover.gui.util.FileUtils;
import fr.lsmbo.msda.recover.gui.util.JavaFxUtils;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Creates and displays filters loader dialog.
 * 
 * @author Aromdhani
 *
 */
public class FilterViewerDialog extends Dialog<Map<String, ObservableList<Object>>> {
	/**
	 * Default constructor
	 * 
	 */
	@SuppressWarnings("unchecked")
	public FilterViewerDialog() {
		// Create notifications label
		Label emptyFileWarningLabel = new Label(
				"Select filters parameters from the JSON file. Cancel an action will reset current filters!");
		emptyFileWarningLabel.setGraphic(new ImageView(IconResource.getImage(ICON.WARNING)));
		emptyFileWarningLabel.setStyle(JavaFxUtils.RED_ITALIC);
		// Create dialog components
		Label fileLocationLabel = new Label("Filters file location:");
		TextField fileLocationTF = new TextField();
		fileLocationTF.setPrefWidth(380);
		fileLocationTF.setTooltip(new Tooltip("Select filters parameters from the JSON file!"));
		Button loadFileButton = new Button("Load");
		loadFileButton.setGraphic(new ImageView(IconResource.getImage(ICON.LOAD)));
		StackPane root = new StackPane();
		root.setPadding(new Insets(5));
		// Layout
		GridPane mainPane = new GridPane();
		mainPane.setAlignment(Pos.TOP_LEFT);
		mainPane.setPadding(new Insets(10));
		mainPane.setHgap(15);
		mainPane.setVgap(15);

		mainPane.add(emptyFileWarningLabel, 0, 0, 3, 1);
		mainPane.addRow(2, fileLocationLabel, fileLocationTF, loadFileButton);
		mainPane.add(root, 0, 3, 3, 6);

		/********************
		 * Main dialog pane *
		 ********************/

		// Create and display the main dialog pane
		DialogPane dialogPane = new DialogPane();
		dialogPane.setContent(mainPane);
		dialogPane.setHeaderText("Load Filters");
		dialogPane.setGraphic(new ImageView(IconResource.getImage(ICON.LOAD)));
		dialogPane.setPrefSize(600, 500);
		Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new ImageView(IconResource.getImage(ICON.LOAD)).getImage());
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Button buttonOk = (Button) dialogPane.lookupButton(ButtonType.OK);
		this.setTitle("Load Filters");
		this.setDialogPane(dialogPane);
		// Set components control
		loadFileButton.setOnAction(evt -> {
			FileUtils.loadFiltersFrmJSON(file -> {
				try {
					FilterReaderJson.load(file);
					fileLocationTF.setText(file.getAbsolutePath());
					// Create the Root TreeItem
					TreeItem rootItem = new TreeItem("Filters");
					rootItem.setExpanded(true);
					rootItem.getChildren().addAll(getFilters());
					TreeView treeView = new TreeView(rootItem);
					root.getChildren().add(treeView);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}, stage);
		});
		// Enable Ok button.
		emptyFileWarningLabel.visibleProperty().bind(fileLocationTF.textProperty().isEmpty());
		buttonOk.disableProperty().bind(fileLocationTF.textProperty().isEmpty());
		// On apply button
		this.setResultConverter(buttonType -> {
			if (buttonType == ButtonType.OK) {
				return ColumnFilters.getApplied();
			} else {
				ColumnFilters.resetAll();
			}
			return null;
		});
	}

	/**
	 * Return the filter TreeItem values
	 * 
	 * @return TreeItem<String>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ArrayList<TreeItem> getFilters() {
		ArrayList<TreeItem> filtersItems = new ArrayList<>();
		ArrayList<TreeItem> items = new ArrayList<>();
		ColumnFilters.getApplied().forEach((k, v) -> {
			TreeItem filterName = new TreeItem(k);
			items.clear();
			for (Object filter : v) {
				if (filter instanceof NumberOperator<?>) {
					StringBuilder strBuilder = new StringBuilder();
					strBuilder.append("Type:").append(((NumberOperator<?>) filter).getType()).append(" ; ")
							.append("value: ").append(((NumberOperator<?>) filter).getValue());
					TreeItem desc = new TreeItem(strBuilder.toString());
					items.add(desc);
				}
				if (filter instanceof StringOperator) {
					StringBuilder strBuilder = new StringBuilder();
					strBuilder.append("Type:").append(((StringOperator) filter).getType()).append(" ; ")
							.append("value: ").append(((StringOperator) filter).getValue());
					TreeItem desc = new TreeItem(strBuilder.toString());
					items.add(desc);
				}
				if (filter instanceof BooleanOperator) {
					StringBuilder strBuilder = new StringBuilder();
					strBuilder.append("Type:").append(((BooleanOperator) filter).getType()).append(" ; ")
							.append("value: ").append(((BooleanOperator) filter).getValue());
					TreeItem<String> desc = new TreeItem(strBuilder.toString());
					items.add(desc);
				}
				if (filter instanceof LowIntensityThresholdFilter) {
					StringBuilder strBuilder = new StringBuilder();
					strBuilder.append("Type: ").append(((LowIntensityThresholdFilter) filter).getType()).append(" ; ")
							.append("value: ").append(((LowIntensityThresholdFilter) filter).getFullDescription());
					TreeItem desc = new TreeItem(strBuilder.toString());
					items.add(desc);
				}
				if (filter instanceof IdentifiedSpectra) {
					StringBuilder strBuilder = new StringBuilder();
					strBuilder.append("Type: ").append(((IdentifiedSpectra) filter).getType()).append(" ; ")
							.append("value: ").append(((IdentifiedSpectra) filter).getFullDescription());
					TreeItem desc = new TreeItem(strBuilder.toString());
					items.add(desc);
				}
				if (filter instanceof IonReporterFilter) {
					StringBuilder strBuilder = new StringBuilder();
					strBuilder.append("Type: ").append(((IonReporterFilter) filter).getType()).append(" ; ")
							.append("value: ").append(((IonReporterFilter) filter).getFullDescription());
					TreeItem desc = new TreeItem(strBuilder.toString());
					items.add(desc);
				}
			}
			filterName.getChildren().addAll(items);
			filtersItems.add(filterName);
		});
		return filtersItems;
	}
}