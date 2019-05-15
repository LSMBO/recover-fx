package fr.lsmbo.msda.recover.gui.view.dialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.google.jhsheets.filtered.FilteredTableView;
import org.google.jhsheets.filtered.tablecolumn.FilterableStringTableColumn;

import fr.lsmbo.msda.recover.gui.IconResource;
import fr.lsmbo.msda.recover.gui.IconResource.ICON;
import fr.lsmbo.msda.recover.gui.util.FileUtils;
import fr.lsmbo.msda.recover.gui.util.JavaFxUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * Creates and displays export in batch dialog.
 * 
 * @author Aromdhani
 *
 */
public class ExportInBatchDialog extends Dialog<Map<File, File>> {

	private File peakListDirectory = null;
	private File identifiedSpectraDirectory = null;
	private File outputDirectory = null;
	private Boolean isAppliedFilters = false;
	private Map<File, File> mgfByIdentifiedTitlesFile = new HashMap<>();
	private ObservableList<File> mgfFiles = FXCollections.observableArrayList();
	private ObservableList<File> excelFiles = FXCollections.observableArrayList();
	private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

	/**
	 * @return the output directory
	 */
	public final File getOutputDirectory() {
		return outputDirectory;
	}

	/**
	 * @param outputDirectory
	 *            the output directory to set
	 */
	public final void setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	/**
	 * Determines whether a filters from JSON file were loaded
	 * 
	 * @return the isAppliedFilters
	 */
	public final Boolean getApplyFilters() {
		return isAppliedFilters;
	}

	/**
	 * @param isAppliedFilters
	 *            the value to set
	 */
	public final void setApplyFilters(Boolean isAppliedFilters) {
		this.isAppliedFilters = isAppliedFilters;
	}

	public ExportInBatchDialog() {

		// Create notifications pane
		VBox warningPane = new VBox(2);
		Label emptyPeakListDirLabel = new Label("Choose a peaklist files directory. The directory must not be empty!");
		emptyPeakListDirLabel.setGraphic(new ImageView(IconResource.getImage(ICON.WARNING)));
		emptyPeakListDirLabel.setStyle(JavaFxUtils.RED_ITALIC);

		// Label emptyIdentifiedSpectraDir = new Label(
		// "Choose a directory of identified spectra files. The directory must
		// not be empty!");
		// emptyIdentifiedSpectraDir.setGraphic(new
		// ImageView(IconResource.getImage(ICON.WARNING)));
		// emptyIdentifiedSpectraDir.setStyle(JavaFxUtils.RED_ITALIC);

		Label emptyOutputDirLabel = new Label("Choose an output directory. The directory must not be empty!");
		emptyOutputDirLabel.setGraphic(new ImageView(IconResource.getImage(ICON.WARNING)));
		emptyOutputDirLabel.setStyle(JavaFxUtils.RED_ITALIC);
		warningPane.getChildren().addAll(emptyPeakListDirLabel, emptyOutputDirLabel);

		// Create directory of MGF files components
		Label peakListDirLabel = new Label("Peaklist files:");
		TextField peakListDirTF = new TextField();
		peakListDirTF.setTooltip(new Tooltip("Select a peaklist directory"));
		Button loadMgfButton = new Button("Load");
		loadMgfButton.setGraphic(new ImageView(IconResource.getImage(ICON.LOAD)));
		FilteredTableView<File> peakListsTable = new FilteredTableView<>(mgfFiles);
		FilterableStringTableColumn<File, String> peakListNameCol = new FilterableStringTableColumn<>("Peaklist File");
		peakListNameCol.setCellValueFactory(new PropertyValueFactory<File, String>("name"));
		peakListsTable.getColumns().setAll(peakListNameCol);
		peakListsTable.setColumnResizePolicy(peakListsTable.CONSTRAINED_RESIZE_POLICY);
		peakListsTable.autosize();
		peakListsTable.setPadding(new Insets(5, 5, 5, 5));
		peakListsTable.setMinHeight(200);
		peakListsTable.setCursor(Cursor.CLOSED_HAND);

		// Make peaklists table sortable via drag and drop
		peakListsTable.setRowFactory(tv -> {
			TableRow<File> row = new TableRow<>();
			row.setOnDragDetected(event -> {
				if (!row.isEmpty()) {
					Integer index = row.getIndex();
					Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
					db.setDragView(row.snapshot(null, null));
					ClipboardContent cc = new ClipboardContent();
					cc.put(SERIALIZED_MIME_TYPE, index);
					db.setContent(cc);
					event.consume();
				}
			});
			row.setOnDragOver(event -> {
				Dragboard db = event.getDragboard();
				if (db.hasContent(SERIALIZED_MIME_TYPE)) {
					if (row.getIndex() != ((Integer) db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
						event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
						event.consume();
					}
				}
			});
			row.setOnDragDropped(event -> {
				Dragboard db = event.getDragboard();
				if (db.hasContent(SERIALIZED_MIME_TYPE)) {
					int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
					File draggedPerson = peakListsTable.getItems().remove(draggedIndex);
					int dropIndex;
					if (row.isEmpty()) {
						dropIndex = peakListsTable.getItems().size();
					} else {
						dropIndex = row.getIndex();
					}
					peakListsTable.getItems().add(dropIndex, draggedPerson);
					event.setDropCompleted(true);
					peakListsTable.getSelectionModel().select(dropIndex);
					event.consume();
				}
			});
			return row;
		});
		// Create identified spectra files components
		Label identifiedSpectraDirLabel = new Label("Identified spectra files:");
		TextField identifiedSpectraDirTF = new TextField();
		peakListDirTF.setTooltip(new Tooltip(
				"Choose an excel files directory. The excel files must contains a valid titles to identify"));
		Button loadIdentifiedSpectraButton = new Button("Load");
		loadIdentifiedSpectraButton.setGraphic(new ImageView(IconResource.getImage(ICON.LOAD)));
		FilteredTableView<File> identifiedSpectraFilesTable = new FilteredTableView<>(excelFiles);
		FilterableStringTableColumn<File, String> identifiedSpectraFileCol = new FilterableStringTableColumn<>(
				"Identified Spectra File");
		identifiedSpectraFileCol.setCellValueFactory(new PropertyValueFactory<File, String>("name"));
		identifiedSpectraFilesTable.getColumns().setAll(identifiedSpectraFileCol);
		identifiedSpectraFilesTable.setColumnResizePolicy(identifiedSpectraFilesTable.CONSTRAINED_RESIZE_POLICY);
		identifiedSpectraFilesTable.autosize();
		identifiedSpectraFilesTable.setPadding(new Insets(5, 5, 5, 5));
		identifiedSpectraFilesTable.setMinHeight(200);
		identifiedSpectraFilesTable.setCursor(Cursor.CLOSED_HAND);
		// Make identified spectra table sortable via drag and drop
		identifiedSpectraFilesTable.setRowFactory(tv -> {
			TableRow<File> row = new TableRow<>();
			row.setOnDragDetected(event -> {
				if (!row.isEmpty()) {
					Integer index = row.getIndex();
					Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
					db.setDragView(row.snapshot(null, null));
					ClipboardContent cc = new ClipboardContent();
					cc.put(SERIALIZED_MIME_TYPE, index);
					db.setContent(cc);
					event.consume();
				}
			});

			row.setOnDragOver(event -> {
				Dragboard db = event.getDragboard();
				if (db.hasContent(SERIALIZED_MIME_TYPE)) {
					if (row.getIndex() != ((Integer) db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
						event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
						event.consume();
					}
				}
			});

			row.setOnDragDropped(event -> {
				Dragboard db = event.getDragboard();
				if (db.hasContent(SERIALIZED_MIME_TYPE)) {
					int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
					File draggedPerson = identifiedSpectraFilesTable.getItems().remove(draggedIndex);

					int dropIndex;

					if (row.isEmpty()) {
						dropIndex = identifiedSpectraFilesTable.getItems().size();
					} else {
						dropIndex = row.getIndex();
					}
					identifiedSpectraFilesTable.getItems().add(dropIndex, draggedPerson);
					event.setDropCompleted(true);
					identifiedSpectraFilesTable.getSelectionModel().select(dropIndex);
					event.consume();
				}
			});
			return row;
		});

		// Create output directory
		Label outputDirLabel = new Label("Output directory");
		TextField outputDirTF = new TextField();
		outputDirTF.setTooltip(new Tooltip("Choose an output directory"));
		Button loadOutputButton = new Button("Load");
		loadOutputButton.setGraphic(new ImageView(IconResource.getImage(ICON.LOAD)));
		// Create settings
		CheckBox applyFiltersChbX = new CheckBox("Apply current filters");
		applyFiltersChbX.setSelected(false);
		applyFiltersChbX.setTooltip(new Tooltip("Apply current filters for all peaklists file"));
		CheckBox loadFiltersChbX = new CheckBox("Load filters");
		loadFiltersChbX.setTooltip(new Tooltip("Load filetrs from a JSON file"));
		TextField filterTF = new TextField();
		Button loadFiltersButton = new Button("Load");
		loadFiltersButton.setGraphic(new ImageView(IconResource.getImage(ICON.LOAD)));
		// Style
		ArrayList<Label> labelList = new ArrayList<Label>();
		labelList.add(peakListDirLabel);
		labelList.add(identifiedSpectraDirLabel);
		labelList.add(outputDirLabel);
		labelList.forEach(label -> label.setMinWidth(120));

		ArrayList<TextField> textFieldList = new ArrayList<TextField>();
		textFieldList.add(peakListDirTF);
		textFieldList.add(identifiedSpectraDirTF);
		textFieldList.add(outputDirTF);
		textFieldList.add(filterTF);
		textFieldList.forEach(label -> label.setMinWidth(180));

		// Layout
		GridPane mainPane = new GridPane();
		mainPane.setAlignment(Pos.TOP_LEFT);
		mainPane.setPadding(new Insets(10));
		mainPane.setHgap(5);
		mainPane.setVgap(15);

		mainPane.add(warningPane, 0, 0, 6, 1);

		mainPane.add(peakListDirLabel, 0, 2, 1, 1);
		mainPane.add(peakListDirTF, 1, 2, 1, 1);
		mainPane.add(loadMgfButton, 2, 2, 1, 1);

		mainPane.add(identifiedSpectraDirLabel, 3, 2, 1, 1);
		mainPane.add(identifiedSpectraDirTF, 4, 2, 1, 1);
		mainPane.add(loadIdentifiedSpectraButton, 5, 2, 1, 1);

		mainPane.add(outputDirLabel, 0, 4, 1, 1);
		mainPane.add(outputDirTF, 1, 4, 1, 1);
		mainPane.add(loadOutputButton, 2, 4, 1, 1);

		mainPane.add(loadFiltersChbX, 0, 6, 1, 1);
		mainPane.add(filterTF, 1, 6, 1, 1);
		mainPane.add(loadFiltersButton, 2, 6, 1, 1);

		mainPane.add(peakListsTable, 0, 8, 3, 4);
		mainPane.add(identifiedSpectraFilesTable, 3, 8, 3, 4);

		/********************
		 * Main dialog pane *
		 ********************/

		// Create and display the main dialog pane
		DialogPane dialogPane = new DialogPane();
		dialogPane.setContent(mainPane);
		dialogPane.setHeaderText("Export In Batch");
		dialogPane.setGraphic(new ImageView(IconResource.getImage(ICON.EXPORT_DATA)));
		dialogPane.setPrefSize(780, 600);
		Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new ImageView(IconResource.getImage(ICON.EXPORT_DATA)).getImage());
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Button buttonOk = (Button) dialogPane.lookupButton(ButtonType.OK);
		this.setTitle("Export In Batch");
		this.setDialogPane(dialogPane);
		// Set components control
		loadMgfButton.setOnAction(evt -> {
			final DirectoryChooser chooser = new DirectoryChooser();
			FileUtils.configureDirChooser(chooser, "Peaklist directory");
			peakListDirectory = chooser.showDialog(stage);
			if (peakListDirectory != null && peakListDirectory.exists()) {
				mgfFiles.clear();
				peakListDirTF.setText(peakListDirectory.getAbsolutePath());
				getListFiles(peakListDirectory, mgfFiles, ".mgf");
				// Sort files with their names
				Collections.sort(mgfFiles, new Comparator<File>() {
					@Override
					public int compare(File o1, File o2) {
						// TODO Auto-generated method stub
						return o1.getName().compareTo(o2.getName());
					}
				});
			}
		});

		loadIdentifiedSpectraButton.setOnAction(evt -> {
			final DirectoryChooser chooser = new DirectoryChooser();
			FileUtils.configureDirChooser(chooser, "Identified spectra directory");
			identifiedSpectraDirectory = chooser.showDialog(stage);
			if (identifiedSpectraDirectory != null && identifiedSpectraDirectory.exists()) {
				excelFiles.clear();
				identifiedSpectraDirTF.setText(identifiedSpectraDirectory.getAbsolutePath());
				getListFiles(identifiedSpectraDirectory, excelFiles, "xls", "xlsx");
				// Sort files with their names
				Collections.sort(excelFiles, new Comparator<File>() {
					@Override
					public int compare(File o1, File o2) {
						// TODO Auto-generated method stub
						return o1.getName().compareTo(o2.getName());
					}
				});
			}
		});
		// Load output directory
		loadOutputButton.setOnAction(evt -> {
			final DirectoryChooser chooser = new DirectoryChooser();
			FileUtils.configureDirChooser(chooser, "Choose an output directory");
			outputDirectory = chooser.showDialog(stage);
			if (outputDirectory != null && outputDirectory.exists()) {
				outputDirTF.setText(outputDirectory.getAbsolutePath());
			}
		});

		// Load peakList
		loadFiltersButton.setOnAction(evt -> {
			FileUtils.loadFiltersFrmJSON(file -> {
				filterTF.setText(file.getPath());
			}, stage);
		});
		// Control
		filterTF.disableProperty().bind(loadFiltersChbX.selectedProperty().not());
		loadFiltersButton.disableProperty().bind(loadFiltersChbX.selectedProperty().not());
		// Enable Ok button.
		emptyPeakListDirLabel.visibleProperty().bind(peakListDirTF.textProperty().isEmpty());
		emptyOutputDirLabel.visibleProperty().bind(outputDirTF.textProperty().isEmpty());
		buttonOk.disableProperty()
				.bind(outputDirTF.textProperty().isEmpty().or(peakListDirTF.textProperty().isEmpty()));
		// On apply changes
		this.setResultConverter(buttonType -> {
			if (buttonType == ButtonType.OK) {
				Iterator<File> it1 = mgfFiles.iterator();
				Iterator<File> it2 = excelFiles.iterator();
				while (it1.hasNext()) {
					if (it2.hasNext()) {
						mgfByIdentifiedTitlesFile.put(it1.next(), it2.next());
					} else {
						mgfByIdentifiedTitlesFile.put(it1.next(), null);
					}
				}
			}
			return mgfByIdentifiedTitlesFile;
		});
	}

	/**
	 * Search and add the file in the list .
	 * 
	 * @param directory
	 *            the directory of files
	 * @list the list to update
	 * @extensions The list of extensions to search in directory
	 */
	public void getListFiles(File directory, ObservableList<File> list, String... extensions) {
		for (String extension : extensions) {
			for (File file : directory.listFiles()) {
				if (file.isFile() && file.getName().endsWith(extension)) {
					list.add(file);
				}
				if (file.isDirectory()) {
					getListFiles(file, list, extension);
				}
			}
		}
	}
}