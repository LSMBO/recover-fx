package fr.lsmbo.msda.recover.gui;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import fr.lsmbo.msda.recover.Main;
import fr.lsmbo.msda.recover.Session;
import fr.lsmbo.msda.recover.Views;
import fr.lsmbo.msda.recover.util.IconResource;
import fr.lsmbo.msda.recover.view.RecoverController;
import fr.lsmbo.msda.recover.view.component.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import fr.lsmbo.msda.recover.util.WindowSize;
import fr.lsmbo.msda.recover.util.IconResource.ICON;

/**
 * Open a new scene and load the controller linked with the view. Load peak
 * lists at the loading.
 * 
 * @author BL
 *
 */

public class Recover extends Application {

	public static Boolean useSecondPeaklist = false;
	public static Stage mainStage = null;
	public static ExecutorService executor = null;
	private static RecoverController recoverController = new RecoverController();

	@Override
	public void init() {
		// initial first executor
		executor = Executors.newWorkStealingPool();
	}

	public static void run() {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;
		primaryStage.setTitle(Main.recoverTitle());
		primaryStage.getIcons().add(IconResource.getImage(ICON.RECOVER));
		BorderPane page = MainPanel.getInstance();
		Scene scene = new Scene(page);
		primaryStage.setScene(scene);
		primaryStage.setWidth(WindowSize.prefWidth);
		primaryStage.setMinWidth(WindowSize.minWidth);
		primaryStage.setHeight(WindowSize.prefHeight);
		primaryStage.setMinHeight(WindowSize.minHeight);
		primaryStage.show();
		// new RecoverController();
		// load files at the opening
		if (Session.CURRENT_FILE != null) {
			// recoverController.loadFile(Session.CURRENT_FILE);
			// useSecondPeaklist = true;
			// recoverController.loadFile(Session.SECOND_FILE);
		}
	}

	public static RecoverController getRecoverController() {
		return recoverController;
	}

	@Override
	public void stop() {
		// shutdown Executor
		close();
	}

	public void close() {
		try {
			executor.shutdown();
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println("Error while trying to shutdown executor service");
		} finally {
			if (!executor.isTerminated()) {
				System.out.println("Error Cancel non finished tasks");
			}
			executor.shutdownNow();
			System.out.println("Info shutdown of ExecutorService.");
		}
	}
}
