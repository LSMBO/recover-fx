/*
 * 
 */
package fr.lsmbo.msda.recover.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		// Initialize configuration parameters.
		Config.initialize();
		if (args.length == 0) {
			logger.info("Start RecoverFx");
			RecoverFx.run();
		} else {
			logger.info("Add CLI");
		}
	}

	public static String recoverTitle() {
		String title = Session.RECOVER_RELEASE_NAME + " " + Session.RECOVER_RELEASE_VERSION + " ("
				+ Session.RECOVER_RELEASE_DATE + ")";
		return title;
	}

}
