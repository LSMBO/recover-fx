package fr.lsmbo.msda.recover.task;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.lsmbo.msda.recover.task.ThreadPoolType.TYPE;

/**
 * Asynchronous task executor
 * 
 * @author aromdhani
 *
 */

public class TaskExecutor {
	//private final Logger logger = LogManager.getLogger(TaskExecutor.class);
	public ExecutorService executorService = ThreadPoolType.getThreadExecutor(TYPE.SHORTTASK);

	private TaskExecutor() {
	}

	private static class Holder {
		private final static TaskExecutor instance = new TaskExecutor();
	}

	public static TaskExecutor getInstance() {
		return Holder.instance;

	}

	/**
	 * execute a task
	 * 
	 * @param task
	 */
	public void execute(Runnable task) {
		executorService.execute(createWrappedRunnableTask(task));
	}

	/**
	 * execute task with delay
	 * 
	 * @param task
	 * @param startTimout
	 */
	public void execute(Runnable task, long startTimout) {
		executorService.execute(createWrappedRunnableTask(task));
	}

	/**
	 * 
	 * @param create
	 *            <T> Callable<T> task
	 * @return Callable task
	 */
	public <T> Callable<T> creatCallable(final Callable<T> task) {
		return () -> {
			try {
				return task.call();
			} catch (Exception e) {
				System.err.println("Error while trying to execute task ");
				throw e;
			}
		};
	}

	/**
	 * create Runnable task
	 * 
	 * @param task
	 */
	public Runnable createWrappedRunnableTask(final Runnable task) {
		return () -> {
			try {
				task.run();
			} catch (Exception e) {
				System.err.println("Error while trying to execute runnable task");
				throw e;
			} 
		};
	}

	/**
	 * submit task to executorService
	 * 
	 * @param task
	 * @return
	 */

	public Future<?> submitRunnabletask(final Runnable task) {
		return executorService.submit(createWrappedRunnableTask(task));
	}

	/**
	 * submit Callable to executorService
	 * 
	 * @param task
	 * @return
	 */

	public <T> Future<T> submitCallableTask(final Callable<T> task) {
		return executorService.submit(creatCallable(task));
	}

	/**
	 * shutdown executor of services
	 */
	public void close() {
		try {
			executorService.shutdown();
			executorService.awaitTermination(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.err.println("Error while trying to shutdown executor service");
		} finally {
			if (!executorService.isTerminated()) {
				System.err.println("Cancel non finished tasks");
			}
			executorService.shutdownNow();
			System.out.println("shutdown of executorService.");
		}

	}
}
