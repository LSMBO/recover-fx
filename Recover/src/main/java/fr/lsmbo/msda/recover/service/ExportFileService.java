package fr.lsmbo.msda.recover.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Service to export files
 * 
 * @author aromdhani
 *
 */
public class ExportFileService extends Service<Void> {

	@Override
	protected Task<Void> createTask() {
		// TODO Auto-generated method stub
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {

				long startTime = System.currentTimeMillis();
				System.out.println("Info Start Service Export file ...");
				// Export in batch;
				long endTime = System.currentTimeMillis();
				long totalTime = endTime - startTime;
				System.out.println("Info Export file time: " + (double) totalTime / 1000 + " secondes");
				return null;
			}
		};
	}
}
