package fr.lsmbo.msda.recover.view.panel;

import javafx.stage.Stage;
import fr.lsmbo.msda.recover.util.StyleUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * @author aromdhani
 *
 */
public class ExitPopup extends Stage {
	public ExitPopup(String popupTitle, String message, Stage parentStage) {
		Stage popup = this;
		this.initOwner(parentStage);

		Button buttonCancel = new Button(" Cancel ");
		buttonCancel.setStyle(StyleUtils.BUTTON_SHADOW);
		buttonCancel.setPrefWidth(75);
		buttonCancel.setOnAction((ActionEvent t) -> {
			popup.close();
		});

		Button buttonExit = new Button(" Exit ");
		buttonExit.setStyle(StyleUtils.BUTTON_SHADOW);
		buttonExit.setPrefWidth(75);
		buttonExit.setOnAction((ActionEvent t) -> {
			Platform.exit();
			System.exit(0);
		});

		VBox root = new VBox(20);
		root.setStyle(StyleUtils.DIALOG_MODAL);
		HBox buttonsPanel = new HBox(60, buttonExit, buttonCancel);
		buttonsPanel.setAlignment(Pos.BASELINE_CENTER);
		Label messageLabel = new Label(message);
		messageLabel.setStyle(StyleUtils.LABEL);
		messageLabel.setAlignment(Pos.BASELINE_CENTER);
		root.getChildren().addAll(messageLabel, buttonsPanel);
		// scene
		Scene scene = new Scene(new VBox(5, root));
		popup.setTitle(popupTitle);
		popup.setScene(scene);
		popup.setAlwaysOnTop(true);
		popup.setWidth(400);
		popup.setMinWidth(400);
		popup.setHeight(150);
		popup.setMinHeight(150);
		popup.show();
	}
}
