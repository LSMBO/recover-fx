package fr.lsmbo.msda.recover.view.panel;

import javafx.stage.Stage;
import fr.lsmbo.msda.recover.util.IconRessource;
import fr.lsmbo.msda.recover.util.StyleUtils;
import fr.lsmbo.msda.recover.util.IconRessource.ICON;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * 
 * @author aromdhani
 *
 */
public class AboutPopup extends Stage {
	public AboutPopup(String popupTitle, String message, Hyperlink link, Stage parentStage) {

		Stage popup = this;
		this.initOwner(parentStage);
		// component
		Button buttonOk = new Button(" Ok ");
		buttonOk.setStyle(StyleUtils.BUTTON_SHADOW);
		buttonOk.setGraphic(new ImageView(IconRessource.getImage(ICON.TICK)));
		buttonOk.setPrefWidth(100);
		buttonOk.setOnAction((ActionEvent t) -> {
			popup.close();
		});

		HBox buttonsPanel = new HBox(buttonOk);
		buttonsPanel.setAlignment(Pos.BASELINE_CENTER);
		Label messageLabel = new Label(message);
		messageLabel.setStyle(StyleUtils.LABEL);

		VBox messagePanel = new VBox(5);
		HBox linkPanel = new HBox(link);
		linkPanel.setAlignment(Pos.BASELINE_CENTER);
		messagePanel.getChildren().addAll(messageLabel, linkPanel);
		messageLabel.setAlignment(Pos.BASELINE_CENTER);
		
		VBox root = new VBox(20);
		root.setStyle(StyleUtils.DIALOG_MODAL);
		root.getChildren().addAll(messagePanel, new Line(), buttonsPanel);
		// scene
		Scene scene = new Scene(new VBox(5, root));
		popup.setTitle(popupTitle);
		popup.setScene(scene);
		popup.setAlwaysOnTop(true);
		popup.setWidth(400);
		popup.setMinWidth(600);
		popup.setHeight(200);
		popup.setMinHeight(200);
		popup.show();
	}
}
