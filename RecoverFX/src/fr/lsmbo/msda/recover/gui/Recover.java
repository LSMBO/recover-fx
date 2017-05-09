package fr.lsmbo.msda.recover.gui;

import java.io.IOException;

import fr.lsmbo.msda.recover.Main;
import fr.lsmbo.msda.recover.Session;
import fr.lsmbo.msda.recover.Views;
import fr.lsmbo.msda.recover.view.RecoverController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Open a new scene and load the controller linked with the view. Load peak
 * lists at the loading.
 * 
 * @author BL
 *
 */

public class Recover extends Application {
	public static Boolean useSecondPeaklist = false;
	private static RecoverController controller = new RecoverController();

	public static void run() {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// window title
		primaryStage.setTitle(Main.recoverTitle());
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Views.RECOVER);
			// AnchorPane page = (AnchorPane) loader.load();
			BorderPane page = (BorderPane) loader.load();
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			controller = loader.getController();
			controller.setDialogStage(primaryStage);

			// display frame
			primaryStage.setMaximized(true);
			primaryStage.show();

			// load files at the opening
			if (Session.CURRENT_FILE != null) {
				controller.loadFile(Session.CURRENT_FILE);
				useSecondPeaklist = true;
				controller.loadFile(Session.SECOND_FILE);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static RecoverController getRecoverController(){
		return controller;
	}

}
