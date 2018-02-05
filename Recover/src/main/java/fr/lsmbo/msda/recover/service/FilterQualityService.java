package fr.lsmbo.msda.recover.service;

import java.io.File;

import fr.lsmbo.msda.recover.io.PeaklistReader;
import fr.lsmbo.msda.recover.lists.ListOfSpectra;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Service to apply quality filter on all spectrum list
 * 
 * @author aromdhani
 *
 */
public class FilterQualityService extends Service<Void> {

	@Override
	protected Task<Void> createTask() {
		// TODO Auto-generated method stub
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {

				long startTime = System.currentTimeMillis();
				System.out.println("Info Start Servie Quality filter ...");
				// Export in batch;
				long endTime = System.currentTimeMillis();
				long totalTime = endTime - startTime;
				System.out.println("Info Qulaity filter time: " + (double) totalTime / 1000 + " secondes");
				return null;
			}
		};
	}
}
