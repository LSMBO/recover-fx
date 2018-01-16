package fr.lsmbo.msda.recover.view.popup;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.File;
import fr.lsmbo.msda.recover.util.*;
import fr.lsmbo.msda.recover.gui.Recover;

/**
 * 
 * @author aromdhani
 *
 */
public class LoadFilePopup extends Stage {
	public LoadFilePopup(String popupTitle, Stage parentStage) {
		Stage popup = this;
		this.initOwner(parentStage);
		final FileChooser fileChooser = new FileChooser();
		
		// button cancel
		Button buttonCancel = new Button(" Cancel ");
		buttonCancel.setStyle(StyleUtils.BUTTON_SHADOW);
		buttonCancel.setPrefWidth(WindowSize.BUTTON_WITDH);
		buttonCancel.setOnAction((ActionEvent t) -> {
			popup.close();
		});
		
		// button open
		Button buttonOpen = new Button(" Open ");
		buttonOpen.setStyle(StyleUtils.BUTTON_SHADOW);
		buttonOpen.setPrefWidth(WindowSize.BUTTON_WITDH);
		buttonOpen.setOnAction((ActionEvent t) -> {
			FileUtils.cofigureFileChooser(fileChooser, "Select mgf or raw files");
			File file = fileChooser.showOpenDialog(Recover.mainStage);
			if (file != null) {
				FileUtils.open(file);
			}
		});

		// buuon's panel
		HBox buttonsPanel = new HBox(60, buttonOpen, buttonCancel);
		buttonsPanel.setAlignment(Pos.BASELINE_CENTER);

		// load panels's component
		VBox lodPanel = new VBox(20);

		VBox root = new VBox(20);
		root.setStyle(StyleUtils.DIALOG_MODAL);
		root.getChildren().addAll(lodPanel, buttonsPanel);
		// scene
		Scene scene = new Scene(new VBox(5, root));
		popup.setTitle(popupTitle);
		popup.setScene(scene);
		popup.setAlwaysOnTop(true);
		// window prefered size
		popup.setWidth(WindowSize.popupPrefWidth);
		popup.setMinWidth(WindowSize.popupMinHeight);
		popup.setHeight(WindowSize.popupPrefHeight);
		popup.setMinHeight(WindowSize.popupMinHeight);
		popup.show();
	}
}
