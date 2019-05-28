package fr.lsmbo.msda.recover.gui.view.dialog;

import java.util.ArrayList;
import java.util.Map;

import org.google.jhsheets.filtered.operators.BooleanOperator;
import org.google.jhsheets.filtered.operators.NumberOperator;
import org.google.jhsheets.filtered.operators.StringOperator;

import fr.lsmbo.msda.recover.gui.IconResource;
import fr.lsmbo.msda.recover.gui.IconResource.ICON;
import fr.lsmbo.msda.recover.gui.filters.Filters;
import fr.lsmbo.msda.recover.gui.filters.IonReporterFilter;
import fr.lsmbo.msda.recover.gui.filters.LowIntensityThresholdFilter;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Creates and displays filters viewer dialog.
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

		// Create dialog components
		Label fileLocationLabel = new Label("The current filters are :");
		fileLocationLabel.setPrefWidth(580);
		StackPane root = new StackPane();
		root.setPadding(new Insets(5));
		// Set components control
		TreeItem rootItem = new TreeItem("Filters");
		rootItem.setExpanded(true);
		rootItem.getChildren().addAll(getFilters());
		TreeView treeView = new TreeView(rootItem);
		root.getChildren().add(treeView);
		// Layout
		GridPane mainPane = new GridPane();
		mainPane.setAlignment(Pos.TOP_LEFT);
		mainPane.setPadding(new Insets(10));
		mainPane.setHgap(15);
		mainPane.setVgap(15);
		mainPane.addRow(0, fileLocationLabel);
		mainPane.add(root, 0, 1, 1, 6);

		/********************
		 * Main dialog pane *
		 ********************/

		// Create and display the main dialog pane
		DialogPane dialogPane = new DialogPane();
		dialogPane.setContent(mainPane);
		dialogPane.setHeaderText("View Filters");
		dialogPane.setGraphic(new ImageView(IconResource.getImage(ICON.FILTER)));
		dialogPane.setPrefSize(600, 500);
		Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new ImageView(IconResource.getImage(ICON.FILTER)).getImage());
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Button buttonOk = (Button) dialogPane.lookupButton(ButtonType.OK);
		this.setTitle("View Filters");
		this.setDialogPane(dialogPane);

		// On apply button
		this.setResultConverter(buttonType -> {
			if (buttonType == ButtonType.OK) {
				return Filters.getAll();
			} else {
				return null;
			}
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
		Filters.getAll().forEach((name, filterList) -> {
			TreeItem filterName = new TreeItem(name);
			items.clear();
			for (Object filter : filterList) {
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
				// if (filter instanceof IdentifiedSpectraFilter) {
				// StringBuilder strBuilder = new StringBuilder();
				// strBuilder.append("Type: ").append(((IdentifiedSpectraFilter)
				// filter).getType()).append(" ; ")
				// .append("value: ").append(((IdentifiedSpectraFilter)
				// filter).getFullDescription());
				// TreeItem desc = new TreeItem(strBuilder.toString());
				// items.add(desc);
				// }
				if (filter instanceof IonReporterFilter) {
					StringBuilder strBuilder = new StringBuilder();
					strBuilder.append("Type: ").append(((IonReporterFilter) filter).getType()).append(" ; ")
							.append("value: ").append(((IonReporterFilter) filter).getFullDescription());
					TreeItem desc = new TreeItem(strBuilder.toString());
					items.add(desc);
				}
			}
			filterName.getChildren().addAll(items);
			filterName.setGraphic(new ImageView(IconResource.getImage(ICON.FUNNEL)));
			filtersItems.add(filterName);

		});
		return filtersItems;
	}
}