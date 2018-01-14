package fr.lsmbo.msda.recover.view.panel;

import javafx.stage.Stage;
import fr.lsmbo.msda.recover.util.StyleUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
public class LoadFilePopup extends Stage {
	public LoadFilePopup(String popupTitle,  VBox node, Stage parentStage) {
		Stage popup = this;
		this.initOwner(parentStage);
		Button buttonCancel = new Button(" Cancel ");
		buttonCancel.setStyle(StyleUtils.BUTTON_SHADOW);
		buttonCancel.setPrefWidth(75);
		buttonCancel.setOnAction((ActionEvent t) -> {
			popup.close();
		});

		Button buttonApply = new Button(" Apply ");
		buttonApply.setStyle(StyleUtils.BUTTON_SHADOW);
		buttonApply.setPrefWidth(75);
		buttonApply.setOnAction((ActionEvent t) -> {
			Platform.exit();
			System.exit(0);
		});
		VBox root = new VBox(20);
		root.setStyle(StyleUtils.DIALOG_MODAL);
		HBox buttonsPanel = new HBox(60, buttonApply, buttonCancel);
		buttonsPanel.setAlignment(Pos.BASELINE_CENTER);
		
        VBox lodPanel = new VBox(20);
		root.getChildren().addAll(lodPanel, buttonsPanel);
		// scene
		Scene scene = new Scene(new VBox(5, root));
		popup.setTitle(popupTitle);
		popup.setScene(scene);
		popup.setAlwaysOnTop(true);
		popup.setWidth(600);
		popup.setMinWidth(400);
		popup.setHeight(400);
		popup.setMinHeight(400);
		popup.show();
	}
}
