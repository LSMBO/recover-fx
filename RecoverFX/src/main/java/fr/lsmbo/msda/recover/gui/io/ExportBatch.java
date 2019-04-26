
package fr.lsmbo.msda.recover.gui.io;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import fr.lsmbo.msda.recover.gui.filters.FilterRequest;
import fr.lsmbo.msda.recover.gui.lists.IdentifiedSpectra;
import fr.lsmbo.msda.recover.gui.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.gui.model.Spectrum;
import fr.lsmbo.msda.recover.gui.util.FileUtils;

/**
 * Export in batch.It contains 3 list for files to process, titles for
 * identification and files processed. One hashMap to link a file with its
 * specific identification.
 * 
 * @author LOMBART.benjamin
 *
 */
public class ExportBatch {
	private static final Logger logger = LogManager.getLogger(ExportBatch.class);

	private ObservableList<File> listFilesToProcess = FXCollections.observableArrayList();
	private HashMap<File, ArrayList<String>> hashMapFileWithListTitles = new HashMap<File, ArrayList<String>>();
	private ObservableList<String> listTitles = FXCollections.observableArrayList();
	private ObservableList<File> listFilesProcessed = FXCollections.observableArrayList();
	private FilterRequest filterRequest = new FilterRequest();
	private String nameDirectoryFolder = "";
	public static Boolean useBatchSpectra = false;

	/**
	 * Add file in hashMAp with value null
	 * 
	 * @param file file which contains peaklist
	 * 
	 */
	public void addFileToProcessInHashMap(File file) {
		hashMapFileWithListTitles.put(file, null);
	}

	/**
	 * Add file in hashMap with its corresponding list of titles
	 * 
	 * @param file                       file which contains Peaklist
	 * @param specificListIdentification ArrayList with titles specific for a file
	 */
	public void addListTitlesWithCorrespondingFile(File file, ArrayList<String> specificListIdentification) {
		hashMapFileWithListTitles.put(file, specificListIdentification);
	}

	/**
	 * Apply all stored filters to the list of peak list files.
	 * 
	 * @param identificationByMgfMap contains peak list files and identification
	 *                               files.
	 */
	public void run(Map<File, File> identificationByPeakListMap) {
		// Boolean to specify we are in batch mode
		useBatchSpectra = true;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		identificationByPeakListMap.forEach((peakListFile, identificationFile) -> {
			try {
				PeaklistReader.load(peakListFile);
				ObservableList<Spectrum> newData = filterRequest
						.applyFilters(ListOfSpectra.getBatchSpectra().getSpectraAsObservable());
				System.out.println(newData.size());
				File newFile = new File(peakListFile.getParent() + File.separator + dateFormat.format(date) + "_"
						+ peakListFile.getName());
				if (newFile.createNewFile()) {
					PeaklistWriter.setFileReader(newFile);
					PeaklistWriter.save(newFile);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		useBatchSpectra = false;
	}

	/**
	 * Routine to process several files. Each file are load, identify (specifically
	 * or in common), filter and export.
	 */
	public void Main() {

		// Boolean to specify we are in batch mode
		useBatchSpectra = true;

		// Use a duplicate list to treat all files and remove each file treated
		// in origin list
		ObservableList<File> duplicateListFileToProcess = FXCollections.observableArrayList(listFilesToProcess);
		if (!stopCompute()) {
			for (File f : duplicateListFileToProcess) {
				PeaklistReader.load(f);
				// Look if the file have a specific excel file for
				// identification
				if (hashMapFileWithListTitles.get(f) == null) {
					doIdentification();
				} else {
					doSpecificIdentification(f);
				}
				File newFile = new File(nameDirectoryFolder + File.separator + f.getName());

				// Specify the file we need to read to export him
				PeaklistWriter.setFileReader(f);
				PeaklistWriter.save(newFile);
				removeFileFromListFileToProcess();
				listFilesProcessed.add(newFile);
			}
		}
	}

	/**
	 * Add each file contained in a list of file to an observable list and to an
	 * hashmap
	 * 
	 * @param file A list of files to be process
	 */
	public void addFilesInObservableList(List<File> file) {
		for (File f : file) {
			if (isAlreadyPresentInListFileToProcess(f)) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("File duplicate");
				alert.setHeaderText(
						"This file : " + f.getName() + " is already present in the list. It will not add to its");
				alert.showAndWait();
			} else {
				listFilesToProcess.add(f);
				addFileToProcessInHashMap(f);
			}
		}
	}

	/**
	 * Check if file is already in the list. Two possibility to check if files are
	 * equals : they have same pathway or the pathway isn't the same but they are
	 * the same name. Ex : C:\Users\LOMBART.benjamin\Desktop\X004081MROLM.mgf
	 * C:\Users\LOMBART.benjamin\workspace\RecoverFX\bin\test\X004081MROLM.mgf
	 * 
	 * @param newFile file will be processed
	 * @return boolean if the file is present or not
	 *
	 */
	private Boolean isAlreadyPresentInListFileToProcess(File newFile) {
		Boolean isPresent = false;

		for (File f : listFilesToProcess) {

			if (newFile.equals(f) || newFile.getName().equals(f.getName())) {
				isPresent = true;
				break;

			} else {
				isPresent = false;
			}
		}
		return isPresent;
	}

	/**
	 * 
	 * @return ObservableList with files to process
	 * 
	 */
	public ObservableList<File> getListFilesToProcess() {
		return listFilesToProcess;
	}

	/**
	 * Reset files to process in observable list and hashmap
	 */
	public void resetListFileToProcess() {
		listFilesToProcess.clear();
		hashMapFileWithListTitles.clear();
	}

	/**
	 * Remove the first file in the list of file to process
	 */
	public void removeFileFromListFileToProcess() {
		if (listFilesToProcess.size() > 0)
			listFilesToProcess.remove(0);
	}

	/**
	 * add titles for identification in observable list after doing verification
	 * 
	 * @param titles ArrayList of titles to make identification
	 */
	public void addTitlesInObservableList(ArrayList<String> titles) {
		for (String t : titles) {
			if (t.isEmpty()) {
				continue;
			}
			if (isAlreadyPresentInListTitles(t)) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Title duplicate");
				alert.setHeaderText(
						"This title : \"" + t + "\" is already present in the list. It will not add to its");
				alert.showAndWait();
			} else {
				listTitles.add(t);
			}
		}
	}

	/**
	 * Check if title is already present in the list
	 * 
	 * @param title
	 * @return boolean if the title are present or not in the list
	 */
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

	/**
	 * 
	 * @return Observable list with titles to make identification
	 */
	public ObservableList<String> getListTitles() {
		return listTitles;
	}

	/**
	 * reset the list of titles
	 */
	public void resetListTitles() {
		listTitles.clear();
	}

	/**
	 * 
	 * @return Observable List with files processed
	 */
	public ObservableList<File> getListFilesProcessed() {
		return listFilesProcessed;
	}

	/**
	 * 
	 * @param folder the folder to export files
	 */
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

		for (String t : hashMapFileWithListTitles.get(file)) {
			identifiedSpectra.setIdentified(t);
		}
	}

	/**
	 * Check if the file we want to export is already in the folder
	 * 
	 * @param f file to process
	 * @return boolean if the file is already in the folder or not
	 */
	private Boolean isPresentInDirectoryFolder(File f) {
		Boolean isPresent = false;

		File folder = new File(nameDirectoryFolder);
		// get all the file in the folder
		File[] listOfFilesInDirectoryFolder = folder.listFiles();

		for (File file : listOfFilesInDirectoryFolder) {
			if (f.getName().equals(file.getName())) {
				isPresent = true;
			}
		}
		return isPresent;
	}

	/**
	 * If files are already present in the folder, display an alert to prevent the
	 * user
	 * 
	 * @return boolean to stop export or not according to the result of the user
	 */
	private Boolean stopCompute() {
		Boolean stopCompute = false;

		ObservableList<File> duplicateListFile = FXCollections.observableArrayList(listFilesToProcess);
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
			alert.setHeaderText(
					"Following files are already present in the directory folder. Are you sure you want to overwrite them ?"
							+ "\n" + allFiles);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.CANCEL) {
				stopCompute = true;
				;
			}
		}

		return stopCompute;
	}
}
