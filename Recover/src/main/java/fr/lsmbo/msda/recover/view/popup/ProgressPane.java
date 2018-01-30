package fr.lsmbo.msda.recover.view.popup;

import fr.lsmbo.msda.recover.util.IconResource;
import fr.lsmbo.msda.recover.util.IconResource.ICON;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Builds Pop up with a progress bar
 * 
 * @author aromdhani
 *
 */
public class ProgressPane extends Stage {
	public ProgressBar workProgress;
	Label statusLabel;
	Stage Stage;

	public ProgressPane(String popupTitle, String message, Stage Stage) {
		Stage popup = this;
		popup.initOwner(Stage);
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.getIcons().add(IconResource.getImage(ICON.LOAD));
		popup.setTitle(popupTitle);
		// component
		workProgress = new ProgressBar();
		statusLabel = new Label(message);
		VBox root = new VBox(30);
		root.setAlignment(Pos.CENTER);
		Scene scene = new Scene(new VBox(5, root));
		root.getChildren().addAll(statusLabel, workProgress);
		popup.setTitle(popupTitle);
		popup.setScene(scene);
		popup.setMinWidth(250);
		popup.setMaxWidth(250);
		popup.setHeight(150);
		popup.setMaxHeight(150);
		popup.show();
	}
}
