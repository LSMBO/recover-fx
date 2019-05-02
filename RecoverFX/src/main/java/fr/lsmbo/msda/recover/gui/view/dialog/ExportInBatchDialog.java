package fr.lsmbo.msda.recover.gui.view.dialog;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

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

	File mgfDirectory = null;
	File excelDirectory = null;
	private Map<File, File> mgfByIdentifiedTitlesFile = new HashMap<>();
	private ObservableList<File> mgfFiles = FXCollections.observableArrayList();
	private ObservableList<File> excelFiles = FXCollections.observableArrayList();
	private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

	public ExportInBatchDialog() {

		// Create notifications pane
		VBox warningPane = new VBox(2);
		Label emptyMgfDirWarningLabel = new Label("Choose a directory of mgf files. The directory must not be empty!");
		emptyMgfDirWarningLabel.setGraphic(new ImageView(IconResource.getImage(ICON.WARNING)));
		emptyMgfDirWarningLabel.setStyle(JavaFxUtils.RED_ITALIC);

		Label emptyExcelDirWarningLabel = new Label(
				"Choose a directory of excel files that contains titles to identify. The directory must not be empty!");
		emptyExcelDirWarningLabel.setGraphic(new ImageView(IconResource.getImage(ICON.WARNING)));
		emptyExcelDirWarningLabel.setStyle(JavaFxUtils.RED_ITALIC);
		warningPane.getChildren().addAll(emptyMgfDirWarningLabel, emptyExcelDirWarningLabel);

		// Create directory of MGF files components
		Label dirMgfLabel = new Label("Peaklist files location:");
		TextField dirMgfTF = new TextField();
		dirMgfTF.setTooltip(new Tooltip("Choose a peaklist file directory"));
		Button loadMgfButton = new Button("Load");
		loadMgfButton.setGraphic(new ImageView(IconResource.getImage(ICON.LOAD)));
		Label dirMgfTitleLabel = new Label("Peaklist files");
		dirMgfTitleLabel.setStyle(JavaFxUtils.TITLE);
		FilteredTableView<File> mgfFilesTable = new FilteredTableView<>(mgfFiles);
		FilterableStringTableColumn<File, String> mgfNameCol = new FilterableStringTableColumn<>("Peaklist File");
		mgfNameCol.setCellValueFactory(new PropertyValueFactory<File, String>("name"));
		mgfFilesTable.getColumns().setAll(mgfNameCol);
		mgfFilesTable.setColumnResizePolicy(mgfFilesTable.CONSTRAINED_RESIZE_POLICY);
		mgfFilesTable.autosize();
		mgfFilesTable.setPadding(new Insets(5, 5, 5, 5));
		mgfFilesTable.setMinHeight(200);
		// Style
		mgfFilesTable.setCursor(Cursor.CLOSED_HAND);

		// Make mgf table sortable via drag and drop
		mgfFilesTable.setRowFactory(tv -> {
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
					File draggedPerson = mgfFilesTable.getItems().remove(draggedIndex);

					int dropIndex;

					if (row.isEmpty()) {
						dropIndex = mgfFilesTable.getItems().size();
					} else {
						dropIndex = row.getIndex();
					}
					mgfFilesTable.getItems().add(dropIndex, draggedPerson);
					event.setDropCompleted(true);
					mgfFilesTable.getSelectionModel().select(dropIndex);
					event.consume();
				}
			});
			return row;
		});
		// Create directory of excel files components
		Label dirExcelLabel = new Label("Excel files location:");
		TextField dirExcelTF = new TextField();
		dirMgfTF.setTooltip(new Tooltip(
				"Choose a excel files diorectory. The excel files must contains a valid titles to identify"));
		Button loadExcelButton = new Button("Load");
		loadExcelButton.setGraphic(new ImageView(IconResource.getImage(ICON.LOAD)));
		Label dirExcelTitleLabel = new Label("Identified title files");
		dirExcelTitleLabel.setStyle(JavaFxUtils.TITLE);
		FilteredTableView<File> excelFilesTable = new FilteredTableView<>(excelFiles);
		FilterableStringTableColumn<File, String> excelFileCol = new FilterableStringTableColumn<>(
				"Identified Title File");
		excelFileCol.setCellValueFactory(new PropertyValueFactory<File, String>("name"));
		excelFilesTable.getColumns().setAll(excelFileCol);
		excelFilesTable.setColumnResizePolicy(excelFilesTable.CONSTRAINED_RESIZE_POLICY);
		excelFilesTable.autosize();
		excelFilesTable.setPadding(new Insets(5, 5, 5, 5));
		excelFilesTable.setMinHeight(200);
		excelFilesTable.setCursor(Cursor.CLOSED_HAND);
		// Make excel table sortable via drag and drop
		excelFilesTable.setRowFactory(tv -> {
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
					File draggedPerson = excelFilesTable.getItems().remove(draggedIndex);

					int dropIndex;

					if (row.isEmpty()) {
						dropIndex = excelFilesTable.getItems().size();
					} else {
						dropIndex = row.getIndex();
					}
					excelFilesTable.getItems().add(dropIndex, draggedPerson);
					event.setDropCompleted(true);
					excelFilesTable.getSelectionModel().select(dropIndex);
					event.consume();
				}
			});
			return row;
		});
		// Layout
		GridPane mainPane = new GridPane();
		mainPane.setAlignment(Pos.TOP_LEFT);
		mainPane.setPadding(new Insets(10));
		mainPane.setHgap(5);
		mainPane.setVgap(15);

		mainPane.add(warningPane, 0, 0, 6, 1);
		mainPane.add(dirMgfLabel, 1, 2, 1, 1);
		mainPane.add(dirMgfTF, 2, 2, 1, 1);
		mainPane.add(loadMgfButton, 3, 2, 1, 1);

		mainPane.add(dirExcelLabel, 4, 2, 1, 1);
		mainPane.add(dirExcelTF, 5, 2, 1, 1);
		mainPane.add(loadExcelButton, 6, 2, 1, 1);

		mainPane.add(dirMgfTitleLabel, 1, 4, 3, 1);
		mainPane.add(dirExcelTitleLabel, 4, 4, 3, 1);
		mainPane.add(mgfFilesTable, 1, 5, 3, 4);
		mainPane.add(excelFilesTable, 4, 5, 3, 4);

		/********************
		 * Main dialog pane *
		 ********************/

		// Create and display the main dialog pane
		DialogPane dialogPane = new DialogPane();
		dialogPane.setContent(mainPane);
		dialogPane.setHeaderText("Export In Batch");
		dialogPane.setGraphic(new ImageView(IconResource.getImage(ICON.EXPORT_DATA)));
		dialogPane.setPrefHeight(600);
		Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new ImageView(IconResource.getImage(ICON.EXPORT_DATA)).getImage());
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Button buttonOk = (Button) dialogPane.lookupButton(ButtonType.OK);
		this.setTitle("Export In Batch");
		this.setDialogPane(dialogPane);
		// Set components control
		loadMgfButton.setOnAction(evt -> {
			final DirectoryChooser chooser = new DirectoryChooser();
			FileUtils.configureDirChooser(chooser, "Choose a mgf directory");
			mgfDirectory = chooser.showDialog(stage);
			if (mgfDirectory != null && mgfDirectory.exists()) {
				mgfFiles.clear();
				dirMgfTF.setText(mgfDirectory.getAbsolutePath());
				getListFiles(mgfDirectory, mgfFiles, ".mgf");
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
		loadExcelButton.setOnAction(evt -> {
			final DirectoryChooser chooser = new DirectoryChooser();
			FileUtils.configureDirChooser(chooser, "Choose an excel directory");
			excelDirectory = chooser.showDialog(stage);
			if (excelDirectory != null && excelDirectory.exists()) {
				excelFiles.clear();
				dirExcelTF.setText(excelDirectory.getAbsolutePath());
				getListFiles(excelDirectory, excelFiles, "xls", "xlsx");
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

		// Enable Ok button.
		emptyMgfDirWarningLabel.visibleProperty().bind(mgfFilesTable.selectionModelProperty().isNull());
		emptyExcelDirWarningLabel.visibleProperty().bind(excelFilesTable.selectionModelProperty().isNull());
		buttonOk.disableProperty().bind(mgfFilesTable.getSelectionModel().selectedItemProperty().isNull());
		// On apply button
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