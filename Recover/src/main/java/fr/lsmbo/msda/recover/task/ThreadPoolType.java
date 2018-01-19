package fr.lsmbo.msda.recover.task;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author aromdhani
 *
 */
public class ThreadPoolType {

	private static HashMap<TYPE, ExecutorService> threadPoolType = new HashMap<TYPE, ExecutorService>();

	public enum TYPE {
		SHORTTASK, SINGLETHREAD, AVAILABLEPROCESSORS, MULTIPLETHREADS;
	}

	public static ExecutorService getThreadExecutor(TYPE executor) {
		if (!threadPoolType.containsKey(executor)) {
			switch (executor) {
			case SHORTTASK:
				threadPoolType.put(executor, Executors.newCachedThreadPool());
				break;
			case SINGLETHREAD:
				threadPoolType.put(executor, Executors.newSingleThreadExecutor());
				break;
			case AVAILABLEPROCESSORS:
				threadPoolType.put(executor, Executors.newWorkStealingPool());
				break;
			case MULTIPLETHREADS:
				threadPoolType.put(executor, Executors.newFixedThreadPool(10));
				break;
			default:
				break;
			}
		}
		return threadPoolType.get(executor);

	}
}
