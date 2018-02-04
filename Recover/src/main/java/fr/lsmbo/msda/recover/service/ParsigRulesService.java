package fr.lsmbo.msda.recover.service;

import java.io.File;

import fr.lsmbo.msda.recover.io.PeaklistReader;
import fr.lsmbo.msda.recover.lists.ListOfSpectra;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Service to export files
 * 
 * @author aromdhani
 *
 */
public class ParsigRulesService extends Service<Void> {

	@Override
	protected Task<Void> createTask() {
		// TODO Auto-generated method stub
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				long startTime = System.currentTimeMillis();
				System.out.println("Info Start Service Parsing rules ...");
				long endTime = System.currentTimeMillis();
				long totalTime = endTime - startTime;
				System.out.println("Info Parsing rules time: " + (double) totalTime / 1000 + " secondes");
				return null;
			}
		};
	}
}
