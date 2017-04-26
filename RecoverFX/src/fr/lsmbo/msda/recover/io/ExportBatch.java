package fr.lsmbo.msda.recover.io;

import java.io.File;

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
	private static ObservableList<File> listFile = FXCollections.observableArrayList();
	private static ObservableList<String> listIdentification = FXCollections.observableArrayList();
	private static ObservableList<File> listFileProcess = FXCollections.observableArrayList();

	private static String directoryFolder = "";

	public static Boolean useBatchSpectra = false;

	//Method call every time batch mode was used. Clear all lists and directory folder
	public static void initialize() {
		listFile.clear();
		listIdentification.clear();
		listFileProcess.clear();
		directoryFolder = "";

	}

	public static void Main() {
		useBatchSpectra = true;
		ObservableList<File> duplicateListFile = FXCollections.observableArrayList(listFile);

		for (File f : duplicateListFile) {

			if(isPresentInDirectoryFolder(f)){
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Same file in the directory folder");
				alert.setHeaderText(f.getName() + "is already present in the directory folder. Are you sure you want to overwrite it ?");
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == ButtonType.CANCEL){
					continue;
				}
			}
			
			PeaklistReader.load(f);
			
			doIdentification();
			
			Filter filter = new Filter();
			filter.applyFilters();
			
			File newFile = new File(directoryFolder + "\\" + f.getName());
			
			PeaklistWriter.setFileReader(f);
			PeaklistWriter.save(newFile);

			removeOneFromListFile();
			
			listFileProcess.add(newFile);

			// System.out.println("Number of spectrum : " +
			// spectra.getSpectraAsObservable().size() +" number of recover : "
			// + spectra.getNbRecover() +" number of identified : " +
			// spectra.getNbIdentified());
		}
	}

	//add file form a List<File> to an observableList and check if the file is already present in this observablelist
	public static void addListFile(List<File> file) {
		for (File f : file) {
			if (isAlreadyPresentInListFile(f)) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("File duplicate");
				alert.setHeaderText("This file : " + f.getName() + " is already present in the list. It will not add to its");
				alert.showAndWait();
			} else {
				listFile.add(f);
			}
		}
	}

	//check if a file is already in the list. same object or if the name are the same.
	private static Boolean isAlreadyPresentInListFile(File newFile) {
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

	
	public static ObservableList<File> getListFile() {
		return listFile;
	}

	public static void resetListFile() {
		listFile.clear();
	}

	public static void removeOneFromListFile() {
		if (listFile.size() > 0)
			listFile.remove(0);
	}

	
	public static void addListIdentification(String[] titles) {
		for (String t : titles) {
			if (isAlreadyPresentInListIdentification(t)) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Title duplicate");
				alert.setHeaderText("This title : \"" + t + "\" is already present in the list. It will not add to its");
				alert.showAndWait();
			} else {
				listIdentification.add(t);
			}
		}
	}

	private static Boolean isAlreadyPresentInListIdentification(String title) {
		Boolean isPresent = false;

		for (String t : listIdentification) {
			if (title.equals(t)) {
				isPresent = true;
				break;
			}
		}
		return isPresent;
	}

	public static ObservableList<String> getListIdentification() {
		return listIdentification;
	}

	public static void resetListIdentification() {
		listIdentification.clear();
	}

	public static ObservableList<File> getListFileProcess() {
		return listFileProcess;
	}

	public static void setDirectoryFolder(String folder) {
		directoryFolder = folder;
	}

	private static void doIdentification() {
		IdentifiedSpectra identifiedSpectra = new IdentifiedSpectra();
		for (String t : listIdentification) {
			identifiedSpectra.setIdentified(t);
		}
	}
	
	private static Boolean isPresentInDirectoryFolder(File f){
		Boolean isPresent = false;
		
		File folder = new File(directoryFolder);
		File[] listOfFilesDirectoryFolder = folder.listFiles();
		
		for(File file : listOfFilesDirectoryFolder){
			if(f.getName().equals(file.getName())){
				isPresent = true;
			}
		}
		
		return isPresent;
	}
}
