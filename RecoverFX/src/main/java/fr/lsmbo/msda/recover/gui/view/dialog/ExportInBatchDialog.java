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
import fr.lsmbo.msda.recover.gui.model.AppliedFilters;
import fr.lsmbo.msda.recover.gui.model.settings.SpectrumTitleSelector;
import fr.lsmbo.msda.recover.gui.util.FileUtils;
import fr.lsmbo.msda.recover.gui.util.JavaFxUtils;
import fr.lsmbo.msda.recover.gui.view.model.ExportInBatchProperty;
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
public class ExportInBatchDialog extends Dialog<ExportInBatchProperty> {

	private ObservableList<File> peakListFiles = FXCollections.observableArrayList();
	private ObservableList<File> identifiedSpectraFiles = FXCollections.observableArrayList();
	private Map<File, File> identifiedSpectraByPeakList = new HashMap<>();
	private File peakListDirectory;
	private File identifiedSpectraDirectory;
	private File outputDirectory;
	private File jsonFile;
	private CheckBox applyFiltersChbX;
	private CheckBox loadFiltersChbX;
	private CheckBox addTitleSelectionChbX;
	private SpectrumTitleSelector spectrumTitleRange;
	private ExportInBatchProperty exportInBatchProperty;
	private AppliedFilters appliedFilters = AppliedFilters.NONE;

	private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

	/**
	 * @return the output directory.
	 * 
	 */
	public final File getOutputDirectory() {
		return outputDirectory;
	}

	/**
	 * @param outputDirectory the output directory to set.
	 */
	public final void setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	/**
	 * @return the the properties of export in batch
	 */
	public final ExportInBatchProperty getExportBatchProperty() {
		return exportInBatchProperty;
	}

	/**
	 * @param exportBatchProperty the properties of export in batch to set
	 */
	public final void setExportBatchProperty(ExportInBatchProperty exportBatchProperty) {
		this.exportInBatchProperty = exportBatchProperty;
	}

	public ExportInBatchDialog() {

		// Create notifications pane
		VBox warningPane = new VBox(2);
		Label emptyPeakListDirLabel = new Label(
				"Choose a peaklist files directory. Make sure that you have selected a valid directory!");
		emptyPeakListDirLabel.setGraphic(new ImageView(IconResource.getImage(ICON.WARNING)));
		emptyPeakListDirLabel.setStyle(JavaFxUtils.RED_ITALIC);

		Label emptyJsonFileLabel = new Label("Choose a filters file. Make sure that you have selected a valid file!");
		emptyJsonFileLabel.setGraphic(new ImageView(IconResource.getImage(ICON.WARNING)));
		emptyJsonFileLabel.setStyle(JavaFxUtils.RED_ITALIC);
		Label emptyOutputDirLabel = new Label(
				"Choose an output directory.  Make sure that you have selected a valid directory!");
		emptyOutputDirLabel.setGraphic(new ImageView(IconResource.getImage(ICON.WARNING)));
		emptyOutputDirLabel.setStyle(JavaFxUtils.RED_ITALIC);
		Label excelLabel = new Label(
				"Enter a sheet name and a column name of titles to identify from excel file!");
		excelLabel.setGraphic(new ImageView(IconResource.getImage(ICON.WARNING)));
		excelLabel.setStyle(JavaFxUtils.RED_ITALIC);
		warningPane.getChildren().addAll(emptyPeakListDirLabel, emptyOutputDirLabel, emptyJsonFileLabel, excelLabel);

		// Create directory of MGF files components
		Label peakListDirLabel = new Label("Peaklist files:");
		TextField peakListDirTF = new TextField();
		peakListDirTF.setTooltip(new Tooltip("Select a peaklist directory"));
		Button loadMgfButton = new Button("Load");
		loadMgfButton.setGraphic(new ImageView(IconResource.getImage(ICON.LOAD)));
		FilteredTableView<File> peakListsTable = new FilteredTableView<>(peakListFiles);
		FilterableStringTableColumn<File, String> peakListNameCol = new FilterableStringTableColumn<>("Peaklist File");
		peakListNameCol.setCellValueFactory(new PropertyValueFactory<File, String>("name"));
		peakListsTable.getColumns().setAll(peakListNameCol);
		peakListsTable.setColumnResizePolicy(peakListsTable.CONSTRAINED_RESIZE_POLICY);
		peakListsTable.autosize();
		peakListsTable.setPadding(new Insets(5, 5, 5, 5));
		peakListsTable.setMinHeight(200);
		peakListsTable.setCursor(Cursor.CLOSED_HAND);

		// Make peaklist table sortable via drag and drop
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
		FilteredTableView<File> identifiedSpectraFilesTable = new FilteredTableView<>(identifiedSpectraFiles);
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
		applyFiltersChbX = new CheckBox("Apply current filters");
		applyFiltersChbX.setSelected(false);
		applyFiltersChbX.setTooltip(new Tooltip("Apply current filters for all peaklists file"));
		loadFiltersChbX = new CheckBox("Load filters");
		loadFiltersChbX.setSelected(false);
		loadFiltersChbX.setTooltip(new Tooltip("Load filetrs from a JSON file"));
		TextField loadFilterTF = new TextField();
		Button loadFiltersButton = new Button("Load");
		loadFiltersButton.setGraphic(new ImageView(IconResource.getImage(ICON.LOAD)));

		// Create excel selection
		addTitleSelectionChbX = new CheckBox("Add the template of titles selection from excel file.");
		addTitleSelectionChbX.setSelected(false);
		addTitleSelectionChbX
				.setTooltip(new Tooltip("Add the template of titles selection to identify from excel file."));
		Label sheetLabel = new Label("Sheet name: ");
		TextField sheetTF = new TextField();
		sheetTF.setTooltip(new Tooltip("Enter the sheet name"));
		Label columnLabel = new Label("Column name: ");
		TextField columnTF = new TextField();
		columnTF.setPromptText("Example : A3");
		columnTF.setTooltip(new Tooltip("Enter the column name to identify spectra titles. Exmaple: \"A3\""));

		// Style
		ArrayList<Label> labelList = new ArrayList<Label>();
		labelList.add(peakListDirLabel);
		labelList.add(identifiedSpectraDirLabel);
		labelList.add(outputDirLabel);
		labelList.add(sheetLabel);
		labelList.add(columnLabel);
		labelList.forEach(label -> label.setMinWidth(120));

		ArrayList<TextField> textFieldList = new ArrayList<TextField>();
		textFieldList.add(peakListDirTF);
		textFieldList.add(identifiedSpectraDirTF);
		textFieldList.add(outputDirTF);
		textFieldList.add(loadFilterTF);
		textFieldList.add(sheetTF);
		textFieldList.add(columnTF);
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
		mainPane.add(loadFilterTF, 1, 6, 1, 1);
		mainPane.add(loadFiltersButton, 2, 6, 1, 1);
		mainPane.add(applyFiltersChbX, 3, 6, 1, 1);

		mainPane.add(addTitleSelectionChbX, 0, 7, 2, 1);
		mainPane.add(sheetLabel, 3, 7, 1, 1);
		mainPane.add(sheetTF, 4, 7, 1, 1);
		mainPane.add(columnLabel, 3, 8, 1, 1);
		mainPane.add(columnTF, 4, 8, 1, 1);

		mainPane.add(peakListsTable, 0, 9, 3, 4);
		mainPane.add(identifiedSpectraFilesTable, 3, 9, 3, 4);

		/********************
		 * Main dialog pane *
		 ********************/

		// Create and display the main dialog pane
		DialogPane dialogPane = new DialogPane();
		dialogPane.setContent(mainPane);
		dialogPane.setHeaderText("Export In Batch");
		dialogPane.setGraphic(new ImageView(IconResource.getImage(ICON.EXPORT_DATA)));
		dialogPane.setPrefSize(790, 720);
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
				peakListFiles.clear();
				peakListDirTF.setText(peakListDirectory.getAbsolutePath());
				getListFiles(peakListDirectory, peakListFiles, ".mgf");
				// Sort files with their names
				Collections.sort(peakListFiles, new Comparator<File>() {
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
				identifiedSpectraFiles.clear();
				identifiedSpectraDirTF.setText(identifiedSpectraDirectory.getAbsolutePath());
				getListFiles(identifiedSpectraDirectory, identifiedSpectraFiles, "xls", "xlsx");
				// Sort files with their names
				Collections.sort(identifiedSpectraFiles, new Comparator<File>() {

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
		// Load JSON file
		loadFiltersButton.setOnAction(evt -> {
			FileUtils.loadFiltersFrmJSON(file -> {
				loadFilterTF.setText(file.getPath());
			}, stage);
		});

		loadFiltersChbX.setOnAction(e -> {
			updateAppliedFilters();
		});
		applyFiltersChbX.setOnAction(e -> {
			updateAppliedFilters();
		});
		addTitleSelectionChbX.setOnAction(e -> {

		});
		// Control
		loadFilterTF.disableProperty().bind(loadFiltersChbX.selectedProperty().not());
		loadFiltersButton.disableProperty().bind(loadFiltersChbX.selectedProperty().not());
		emptyPeakListDirLabel.visibleProperty().bind(peakListDirTF.textProperty().isEmpty());
		emptyOutputDirLabel.visibleProperty().bind(outputDirTF.textProperty().isEmpty());
		emptyJsonFileLabel.visibleProperty()
				.bind(loadFiltersChbX.selectedProperty().and(loadFilterTF.textProperty().isEmpty()));

		sheetLabel.disableProperty().bind(addTitleSelectionChbX.selectedProperty().not());
		columnLabel.disableProperty().bind(addTitleSelectionChbX.selectedProperty().not());
		sheetTF.disableProperty().bind(addTitleSelectionChbX.selectedProperty().not());
		columnTF.disableProperty().bind(addTitleSelectionChbX.selectedProperty().not());
		excelLabel.visibleProperty().bind(addTitleSelectionChbX.selectedProperty()
				.and(columnTF.textProperty().isEmpty().or(sheetTF.textProperty().isEmpty())));

		// Enable Ok button.
		buttonOk.disableProperty().bind(outputDirTF.textProperty().isEmpty().or(peakListDirTF.textProperty().isEmpty()
				.or(excelLabel.visibleProperty().or(emptyJsonFileLabel.visibleProperty()))));
		// On apply changes
		this.setResultConverter(buttonType -> {
			if (buttonType == ButtonType.OK) {
				exportInBatchProperty = new ExportInBatchProperty();
				// Compute the filters to apply
				exportInBatchProperty.setAppliedFilters(appliedFilters);
				if (appliedFilters.equals(AppliedFilters.LOADED)) {
					if (new File(loadFilterTF.getText()).exists()) {
						jsonFile = new File(loadFilterTF.getText());
					}
					exportInBatchProperty.setJsonFile(jsonFile);
				}
				exportInBatchProperty.setOutputDirectory(outputDirectory);
				if (addTitleSelectionChbX.isSelected()) {
					int index = Integer.parseInt(columnTF.getText().replaceAll("\\D+", ""));
					String column = columnTF.getText().replaceAll("\\d+", "");
					spectrumTitleRange = new SpectrumTitleSelector();
					spectrumTitleRange.setSheetName(sheetTF.getText());
					spectrumTitleRange.setColumn(column);
					spectrumTitleRange.setRowNumber(index);
					exportInBatchProperty.setSpectrumTitleRange(spectrumTitleRange);
				}
				// Return the peak lists file by identified spectra files=
				Iterator<File> it1 = peakListFiles.iterator();
				Iterator<File> it2 = identifiedSpectraFiles.iterator();
				while (it1.hasNext()) {
					if (it2.hasNext()) {
						identifiedSpectraByPeakList.put(it1.next(), it2.next());
					} else {
						identifiedSpectraByPeakList.put(it1.next(), null);
					}
				}
				exportInBatchProperty.setIdentifiedSpectraByPeakList(identifiedSpectraByPeakList);
				return exportInBatchProperty;
			} else {
				return null;
			}
		});
	}

	/**
	 * Search and add the file in the list .
	 * 
	 * @param directory the directory of files
	 * @list the list to update
	 * @extensions The list of extensions to search in directory
	 */
	private void getListFiles(File directory, ObservableList<File> list, String... extensions) {
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

	/**
	 * Update the applied filters.
	 */
	private void updateAppliedFilters() {
		if (applyFiltersChbX.isSelected()) {
			appliedFilters = AppliedFilters.CURRENT;
		} else if (loadFiltersChbX.isSelected()) {
			appliedFilters = AppliedFilters.LOADED;
		} else {
			appliedFilters = AppliedFilters.NONE;
		}
	}

}