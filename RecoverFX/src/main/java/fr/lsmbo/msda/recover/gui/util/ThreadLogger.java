package fr.lsmbo.msda.recover.gui.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




/**
 * Utility class to log <em>uncaught</em> Exception terminating a running
 * thread.
 * 
 * @author aromdhani
 * 
 */
public class ThreadLogger implements Thread.UncaughtExceptionHandler {

	/* Instance variables */
	private final Logger m_logger;

	/* Constructors */
	/**
	 * Create a new ThreadLogger from a given Logger.
	 * 
	 * @param logger
	 *            Logger used to log uncaught exceptions
	 */
	public ThreadLogger(final Logger logger) {

		if (logger == null) {
			throw new IllegalArgumentException("Logger is null");
		}

		m_logger = logger;
	}

	/**
	 * Create a new ThreadLogger from a Logger name.
	 * 
	 * @param loggerName
	 *            Name of Logger used to log uncaught exceptions
	 */
	public ThreadLogger(final String loggerName) {

		if (loggerName == null || loggerName.trim().isEmpty()) {
			throw new IllegalArgumentException("Invalid loggerName");
		}

		m_logger = LogManager.getLogger(loggerName);
	}

	/**
	 * Create a new ThreadLogger with a default Logger.
	 */
	public ThreadLogger() {
		this(LogManager.getLogger(ThreadLogger.class));
	}

	/* Public methods */
	/**
	 * Handle given <em>uncaught</em> Exception terminating t by logging it.
	 * 
	 * @param t
	 *            The thread
	 * @param e
	 *            The exception
	 * 
	 * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException
	 */
	@Override
	public void uncaughtException(final Thread t, final Throwable e) {
		assert (m_logger != null) : "ThreadLogger.uncaughtException() m_logger is null";

		if ((t != null) && (e != null)) {
			m_logger.error("Uncaught Exception in thread \"" + t.getName() + '\"', e);
		}

	}

}
