package fr.lsmbo.msda.recover.service;

import java.io.File;

import fr.lsmbo.msda.recover.io.PeaklistReader;
import fr.lsmbo.msda.recover.lists.ListOfSpectra;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Service to import .mgf and .pkl files
 * 
 * @author aromdhani 
 *
 */
public class ImportFileService extends Service<Void> {
	private File file;

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	protected Task<Void> createTask() {
		// TODO Auto-generated method stub
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				long startTime = System.currentTimeMillis();
				System.out.println("Info Start Service loading file ...");
				PeaklistReader.load(file);
				long endTime = System.currentTimeMillis();
				long totalTime = endTime - startTime;
				System.out.println("Info loading time: " + (double) totalTime / 1000 + " secondes");
				System.out.println("Info " + ListOfSpectra.getFirstSpectra().getNbSpectra() + " spectra");
				System.out.println("Info " + ListOfSpectra.getSecondSpectra().getNbSpectra() + " spectra");
				return null;
			}
		};
	}
}
