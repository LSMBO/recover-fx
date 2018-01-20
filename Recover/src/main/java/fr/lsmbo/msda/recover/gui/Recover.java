package fr.lsmbo.msda.recover.gui;

import java.io.IOException;

import fr.lsmbo.msda.recover.Main;
import fr.lsmbo.msda.recover.Session;
import fr.lsmbo.msda.recover.Views;
import fr.lsmbo.msda.recover.task.TaskExecutor;
import fr.lsmbo.msda.recover.view.panel.*;
import fr.lsmbo.msda.recover.util.IconFactory;
import fr.lsmbo.msda.recover.view.RecoverController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import fr.lsmbo.msda.recover.view.panel.MainPanel;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import fr.lsmbo.msda.recover.util.WindowSize;
import fr.lsmbo.msda.recover.util.IconFactory.ICON;

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
	private static RecoverController recoverController = new RecoverController();

	public static void run() {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// window title
		mainStage = primaryStage;
		primaryStage.setTitle(Main.recoverTitle());
		primaryStage.getIcons().add(IconFactory.getImage(ICON.RECOVER));
		// FXMLLoader loader = new FXMLLoader();
		// loader.setLocation(Views.RECOVER);
		// AnchorPane page = (AnchorPane) loader.load();
		BorderPane page = MainPanel.getInstance();
		Scene scene = new Scene(page);
		primaryStage.setScene(scene);
		// recoverController = loader.getController();
		// recoverController.setDialogStage(primaryStage);
		primaryStage.setWidth(WindowSize.prefWidth);
		primaryStage.setMinWidth(WindowSize.minWidth);
		primaryStage.setHeight(WindowSize.prefHeight);
		primaryStage.setMinHeight(WindowSize.minHeight);
		// display frame
		primaryStage.show();

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
		// shutdown task executor
		TaskExecutor.getInstance().close();
	}
}
