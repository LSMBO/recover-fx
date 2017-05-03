package fr.lsmbo.msda.recover.io;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import com.sun.javafx.collections.MappingChange.Map;

import fr.lsmbo.msda.recover.filters.Filter;
import fr.lsmbo.msda.recover.lists.IdentifiedSpectra;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class ExportBatch {
	private ObservableList<File> listFile = FXCollections.observableArrayList();
	private HashMap<File, ArrayList<String>> listFileExcelFile = new HashMap<File, ArrayList<String>>();
	private ObservableList<String> listIdentification = FXCollections.observableArrayList();
	private ObservableList<File> listFileProcess = FXCollections.observableArrayList();
	private String directoryFolder = "";
	public static Boolean useBatchSpectra = false;

	public void addFileImportInHashMap(File file) {
		listFileExcelFile.put(file, null);
	}

	public void addSpecificifIdentification(File file, ArrayList<String> specificListIdentification) {
		listFileExcelFile.put(file, specificListIdentification);
	}

	public void makeSomeTest() {

		for (Entry<File, ArrayList<String>> file : listFileExcelFile.entrySet()) {
			if (file.getValue() != null) {
				System.out.println(file);
			} else
				System.out.println(file);
		}
	}

	public void Main() {

		useBatchSpectra = true;
		ObservableList<File> duplicateListFile = FXCollections.observableArrayList(listFile);
		if(!stopCompute()){

		for (File f : duplicateListFile) {
			PeaklistReader.load(f);

			// look if the file have a specific excel file for identification
			if (listFileExcelFile.get(f) == null) {
				doIdentification();
			} else {
				doSpecificIdentification(f);
			}

			Filter filter = new Filter();
			filter.applyFilters();

			File newFile = new File(directoryFolder + "\\" + f.getName());

			PeaklistWriter.setFileReader(f);
			PeaklistWriter.save(newFile);

			removeOneFromListFile();

			listFileProcess.add(newFile);
		}
		}
	}

	// add file form a List<File> to an observableList and check if the file is
	// already present in this observablelist
	public void addListFile(List<File> file) {
		for (File f : file) {
			if (isAlreadyPresentInListFile(f)) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("File duplicate");
				alert.setHeaderText(
						"This file : " + f.getName() + " is already present in the list. It will not add to its");
				alert.showAndWait();
			} else {
				listFile.add(f);
				addFileImportInHashMap(f);
			}
		}
	}

	// check if a file is already in the list. same object or if the name are
	// the same.
	private Boolean isAlreadyPresentInListFile(File newFile) {
		Boolean isPresent = false;

		for (File f : listFile) {
			if (newFile.equals(f) || newFile.getName().equals(f.getName())) {
				isPresent = true;
				break;

			} else {
				isPresent = false;
			}
		}
		return isPresent;
	}

	public ObservableList<File> getListFile() {
		return listFile;
	}

	public void resetListFile() {
		listFile.clear();
		listFileExcelFile.clear();
	}

	public void removeOneFromListFile() {
		if (listFile.size() > 0)
			listFile.remove(0);
	}

	public void addListIdentification(ArrayList<String> titles) {
		for (String t : titles) {
			if (isAlreadyPresentInListIdentification(t)) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Title duplicate");
				alert.setHeaderText(
						"This title : \"" + t + "\" is already present in the list. It will not add to its");
				alert.showAndWait();
			} else {
				listIdentification.add(t);
			}
		}
	}

	private Boolean isAlreadyPresentInListIdentification(String title) {
		Boolean isPresent = false;

		for (String t : listIdentification) {
			if (title.equals(t)) {
				isPresent = true;
				break;
			}
		}
		return isPresent;
	}

	public ObservableList<String> getListIdentification() {
		return listIdentification;
	}

	public void resetListIdentification() {
		listIdentification.clear();
	}

	public ObservableList<File> getListFileProcess() {
		return listFileProcess;
	}

	public void setDirectoryFolder(String folder) {
		directoryFolder = folder;
	}

	private void doIdentification() {
		IdentifiedSpectra identifiedSpectra = new IdentifiedSpectra();
		for (String t : listIdentification) {
			identifiedSpectra.setIdentified(t);
		}
	}

	private void doSpecificIdentification(File file) {
		IdentifiedSpectra identifiedSpectra = new IdentifiedSpectra();
		for (String t : listIdentification) {
			System.out.println("common identification : " + t);
			identifiedSpectra.setIdentified(t);
		}
		for (String t : listFileExcelFile.get(file)) {
			System.out.println("specific identification : " + t);
			identifiedSpectra.setIdentified(t);
		}
	}

	private Boolean isPresentInDirectoryFolder(File f) {
		Boolean isPresent = false;

		File folder = new File(directoryFolder);
		File[] listOfFilesDirectoryFolder = folder.listFiles();

		for (File file : listOfFilesDirectoryFolder) {
			if (f.getName().equals(file.getName())) {
				isPresent = true;
			}
		}
		return isPresent;
	}

	private Boolean stopCompute() {
		Boolean stopCompute = false;
		ObservableList<File> duplicateListFile = FXCollections.observableArrayList(listFile);
		String allFiles = "";

		for (File f : duplicateListFile) {
			// look if the file is already present in the folder
			if (isPresentInDirectoryFolder(f)) {
				allFiles += f.getName() + "\n";
			}
		}
		

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Same file in the directory folder");
		alert.setHeaderText("Following files are already present in the directory folder. Are you sure you want to overwrite them ?" +"\n" + allFiles);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.CANCEL) {
			stopCompute = true;
			;
		}
		
		return stopCompute;
	}
}
