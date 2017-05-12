package fr.lsmbo.msda.recover.io;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import fr.lsmbo.msda.recover.filters.Filter;
import fr.lsmbo.msda.recover.lists.IdentifiedSpectra;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class ExportBatch {

	private ObservableList<File> listFileToProcess = FXCollections.observableArrayList();
	private HashMap<File, ArrayList<String>> hashMapFileWithListTitles = new HashMap<File, ArrayList<String>>();
	private ObservableList<String> listTitles = FXCollections.observableArrayList();
	private ObservableList<File> listFileProcessed = FXCollections.observableArrayList();

	private String nameDirectoryFolder = "";
	public static Boolean useBatchSpectra = false;

	public void addFileToProcessInHashMap(File file) {
		hashMapFileWithListTitles.put(file, null);
	}

	public void addListTitlesWithCorrespondingFile(File file, ArrayList<String> specificListIdentification) {
		hashMapFileWithListTitles.put(file, specificListIdentification);
	}

	// public void makeSomeTest() {
	//
	// for (Entry<File, ArrayList<String>> file : listFileExcelFile.entrySet())
	// {
	// if (file.getValue() != null) {
	// System.out.println(file);
	// } else
	// System.out.println(file);
	// }
	// }

	public void Main() {

		useBatchSpectra = true;
		ObservableList<File> duplicateListFileToProcess = FXCollections.observableArrayList(listFileToProcess);
		if (!stopCompute()) {

			for (File f : duplicateListFileToProcess) {
				PeaklistReader.load(f);

				// look if the file have a specific excel file for
				// identification
				if (hashMapFileWithListTitles.get(f) == null) {
					doIdentification();
				} else {
					doSpecificIdentification(f);
				}

				Filter filter = new Filter();
				filter.applyFilters();

				File newFile = new File(nameDirectoryFolder + "\\" + f.getName());

				PeaklistWriter.setFileReader(f);
				PeaklistWriter.save(newFile);

				removeFileFromListFileToProcess();

				listFileProcessed.add(newFile);
			}
		}
	}

	// add file from a List<File> to an observableList and the hashmap and check if the file is
	// already present in this observablelist
	public void addFilesInObservableList(List<File> file) {
		for (File f : file) {
			if (isAlreadyPresentInListFileToProcess(f)) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("File duplicate");
				alert.setHeaderText("This file : " + f.getName() + " is already present in the list. It will not add to its");
				alert.showAndWait();
			} else {
				listFileToProcess.add(f);
				addFileToProcessInHashMap(f);
			}
		}
	}

	// check if a file is already in the list. same object or if the name are
	// the same.
	private Boolean isAlreadyPresentInListFileToProcess(File newFile) {
		Boolean isPresent = false;

		for (File f : listFileToProcess) {
			// two possibility to check if files are equals. they have same pathway or the pathway
			// isn't the same but they are the same name.
			// Ex : C:\Users\LOMBART.benjamin\Desktop\X004081MROLM.mgf
			//		C:\Users\LOMBART.benjamin\workspace\RecoverFX\bin\test\X004081MROLM.mgf
			if (newFile.equals(f) || newFile.getName().equals(f.getName())) {
				isPresent = true;
				break;

			} else {
				isPresent = false;
			}
		}
		return isPresent;
	}

	public ObservableList<File> getListFileToProcess() {
		return listFileToProcess;
	}

	public void resetListFileToProcess() {
		listFileToProcess.clear();
		hashMapFileWithListTitles.clear();
	}

	public void removeFileFromListFileToProcess() {
		if (listFileToProcess.size() > 0)
			listFileToProcess.remove(0);
	}

	public void addTitlesInObservableList(ArrayList<String> titles) {
		for (String t : titles) {
			if (t.isEmpty()) {
				continue;
			}
			if (isAlreadyPresentInListTitles(t)) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Title duplicate");
				alert.setHeaderText("This title : \"" + t + "\" is already present in the list. It will not add to its");
				alert.showAndWait();
			} else {
				listTitles.add(t);
			}
		}
	}

	private Boolean isAlreadyPresentInListTitles(String title) {
		Boolean isPresent = false;

		for (String t : listTitles) {
			if (title.equals(t)) {
				isPresent = true;
				break;
			}
		}
		return isPresent;
	}

	public ObservableList<String> getListTitles() {
		return listTitles;
	}

	public void resetListTitles() {
		listTitles.clear();
	}

	public ObservableList<File> getListFileProcessed() {
		return listFileProcessed;
	}

	public void setNameDirectoryFolder(String folder) {
		nameDirectoryFolder = folder;
	}

	private void doIdentification() {
		IdentifiedSpectra identifiedSpectra = new IdentifiedSpectra();
		for (String t : listTitles) {
			identifiedSpectra.setIdentified(t);
		}
	}

	private void doSpecificIdentification(File file) {
		IdentifiedSpectra identifiedSpectra = new IdentifiedSpectra();

		for (String t : listTitles) {
			identifiedSpectra.setIdentified(t);
		}

		for (String t : hashMapFileWithListTitles.get(file)) {
			identifiedSpectra.setIdentified(t);
		}
	}

	private Boolean isPresentInDirectoryFolder(File f) {
		Boolean isPresent = false;

		File folder = new File(nameDirectoryFolder);
		//get all the file in the folder
		File[] listOfFilesInDirectoryFolder = folder.listFiles();

		for (File file : listOfFilesInDirectoryFolder) {
			if (f.getName().equals(file.getName())) {
				isPresent = true;
			}
		}
		return isPresent;
	}

	private Boolean stopCompute() {
		Boolean stopCompute = false;

		ObservableList<File> duplicateListFile = FXCollections.observableArrayList(listFileToProcess);
		String allFiles = "";

		for (File f : duplicateListFile) {
			// look if the file is already present in the folder
			if (isPresentInDirectoryFolder(f)) {
				allFiles += f.getName() + "\n";
			}
		}

		if (allFiles != "") {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Same file in the directory folder");
			alert.setHeaderText("Following files are already present in the directory folder. Are you sure you want to overwrite them ?" + "\n" + allFiles);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.CANCEL) {
				stopCompute = true;
				;
			}
		}

		return stopCompute;
	}
}
