package fr.lsmbo.msda.recover.io;

import java.io.File;

import java.util.List;

import fr.lsmbo.msda.recover.filters.Filter;
import fr.lsmbo.msda.recover.lists.IdentifiedSpectra;
import fr.lsmbo.msda.recover.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.view.ExportBatchController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ExportBatch {
	private static ObservableList<File> listFile = FXCollections.observableArrayList();
	private static ObservableList<String> listIdentification = FXCollections.observableArrayList();
	private static ObservableList<File> listFileProcess = FXCollections.observableArrayList();
	
	
	private static String directoryFolder = "";
	
	public static Boolean useBatchSpectra = false ;
	
	public static void initialize(){
		listFile.clear();
		listIdentification.clear();
		listFileProcess.clear();
		directoryFolder = "";
		
	}
	
	public static void Main(){
		useBatchSpectra = true;
		ObservableList<File> duplicateListFile = FXCollections.observableArrayList(listFile);
		
		for(File f : duplicateListFile){
			
			PeaklistReader.load(f);
			doIdentification();
			Filter filter = new Filter();
			filter.applyFilters();
			File newFile = new File(directoryFolder + "\\" + f.getName());
			PeaklistWriter.setFileReader(f);
			PeaklistWriter.save(newFile);
			
			removeOneFromListFile();
			listFileProcess.add(newFile);
//			System.out.println("Number of spectrum : " + spectra.getSpectraAsObservable().size() +" number of recover : " + spectra.getNbRecover() +" number of identified :  " + spectra.getNbIdentified());
		}
	}
	
	
	public static void addListFile(List<File> file){
		//TODO add method to avoid duplicate file
		for(File f : file){
			listFile.add(f);
		}
	}
	
	public static ObservableList<File> getListFile(){
		return listFile ;
	}
	
	public static void resetListFile(){
		listFile.clear();
	}
	
	public static void removeOneFromListFile(){
		if (listFile.size() > 0)
			listFile.remove(0);
	}
	
	public static void addListIdentification(String[] titles){
		for(String t : titles){
			listIdentification.add(t);
		}
	}
	
	public static ObservableList<String> getListIdentification(){
		return listIdentification;
	}
	
	public static void resetListIdentification(){
		listIdentification.clear();
	}
	
	public static ObservableList<File> getListFileProcess(){
		return listFileProcess;
	}
	
	public static void setDirectoryFolder(String folder){
		directoryFolder = folder;
	}
	
	private static void doIdentification(){
		IdentifiedSpectra identifiedSpectra = new IdentifiedSpectra();
		for(String t : listIdentification){
			identifiedSpectra.setIdentified(t);
		}
	}
}
