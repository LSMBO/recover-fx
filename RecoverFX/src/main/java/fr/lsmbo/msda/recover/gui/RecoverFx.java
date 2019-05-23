package fr.lsmbo.msda.recover.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.lsmbo.msda.recover.gui.IconResource.ICON;
import fr.lsmbo.msda.recover.gui.view.MainView;
import fr.lsmbo.msda.recover.gui.view.dialog.ShowPopupDialog;
import fr.lsmbo.msda.recover.gui.view.model.RecoverViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Creates GUI and start RecoverFx application.
 * 
 * @author Aromdhani
 *
 */
public class RecoverFx extends Application {
	private static final Logger logger = LogManager.getLogger(RecoverFx.class);
	public static Boolean useSecondPeaklist = false;

	public static void run() {
		launch();
	}

	@Override
	public void init() throws Exception {
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Thread.currentThread().setUncaughtExceptionHandler((thread, ex) -> {
			logger.error("Exception was thrown : {}", ex);
			ex.printStackTrace();
			logger.error(ex);
			new ShowPopupDialog("Exception", "Exception was thrown on Java-FX thread: " + ex.getMessage(),
					primaryStage);
		});
		RecoverViewModel model = new RecoverViewModel();
		RecoverViewModel.setStage(primaryStage);
		MainView view = new MainView(model);
		model.setView(view);
		model.setTaskRunner(view.getTaskRunner());
		primaryStage.getIcons().add(new ImageView(IconResource.getImage(ICON.RECOVER)).getImage());
		Scene scene = new Scene(view, 1224, 800);
		// Add style sheets
		scene.getStylesheets().add("/css/style.css");
		// Set software name and release version
		primaryStage.setTitle(Session.RECOVER_RELEASE_NAME + " " + Session.RECOVER_RELEASE_VERSION);
		primaryStage.setScene(scene);
		primaryStage.show();
		// Load initial file
		if (Session.CURRENT_FILE != null) {
			model.loadFile(Session.CURRENT_FILE);
		}
	}

	@Override
	public void stop() {
		// TODO Make sure that all resources were closed
	}
}
