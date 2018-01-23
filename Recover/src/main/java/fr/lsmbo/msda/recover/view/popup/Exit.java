package fr.lsmbo.msda.recover.view.popup;

import javafx.stage.Stage;
import fr.lsmbo.msda.recover.util.IconResource;
import fr.lsmbo.msda.recover.util.StyleUtils;
import fr.lsmbo.msda.recover.util.WindowSize;
import fr.lsmbo.msda.recover.util.IconResource.ICON;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * @author aromdhani
 *
 */
public class Exit extends Stage {
	public Exit(String popupTitle, String message, Stage parentStage) {

		Stage popup = this;
		popup.initOwner(parentStage);
		popup.getIcons().add(IconResource.getImage(ICON.EXIT));

		Button buttonCancel = new Button(" Cancel ");
		buttonCancel.setStyle(StyleUtils.BUTTON_SHADOW);
		buttonCancel.setGraphic(new ImageView(IconResource.getImage(ICON.CROSS)));
		buttonCancel.setPrefWidth(WindowSize.BUTTON_WITDH);
		buttonCancel.setOnAction((ActionEvent t) -> {
			popup.close();
		});

		Button buttonExit = new Button(" Exit ");
		buttonExit.setStyle(StyleUtils.BUTTON_SHADOW);
		buttonExit.setGraphic(new ImageView(IconResource.getImage(ICON.TICK)));
		buttonExit.setPrefWidth(WindowSize.BUTTON_WITDH);
		buttonExit.setOnAction((ActionEvent t) -> {
			Platform.exit();
			System.exit(0);
		});
		VBox root = new VBox(30);
		root.setStyle(StyleUtils.DIALOG_MODAL);
		HBox buttonsPanel = new HBox(50, buttonExit, buttonCancel);
		buttonsPanel.setAlignment(Pos.BASELINE_CENTER);
		Label messageLabel = new Label(message);
		messageLabel.setStyle(StyleUtils.LABEL);
		messageLabel.setAlignment(Pos.BASELINE_CENTER);
		root.getChildren().addAll(messageLabel, buttonsPanel);
		// scene
		Scene scene = new Scene(new VBox(5, root));
		popup.setTitle(popupTitle);
		popup.setScene(scene);
		// popup.setAlwaysOnTop(true);
		popup.setWidth(400);
		popup.setMinWidth(400);
		popup.setHeight(150);
		popup.setMinHeight(150);
		popup.show();
	}
}
