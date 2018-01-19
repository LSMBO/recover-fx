package fr.lsmbo.msda.recover.view.popup;

import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;

import fr.lsmbo.msda.recover.util.*;
import fr.lsmbo.msda.recover.util.IconFactory.ICON;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author aromdhani
 *
 */
public class ApplyFilter extends Stage {
	private ExecutorService exec;

	public ApplyFilter(String popupTitle, Stage parentStage) {
		Stage popup = this;
		popup.initOwner(parentStage);
		popup.getIcons().add(IconFactory.getImage(ICON.APPLYFILTER));

		// button cancel

		Button buttonCancel = new Button(" Cancel ");
		buttonCancel.setStyle(StyleUtils.BUTTON_SHADOW);
		buttonCancel.setPrefWidth(WindowSize.BUTTON_WITDH);
		buttonCancel.setGraphic(new ImageView(IconFactory.getImage(ICON.CROSS)));
		buttonCancel.setOnAction((ActionEvent t) -> {
			popup.close();
		});

		// button apply
		Button buttonApply = new Button(" Apply ");
		buttonApply.setStyle(StyleUtils.BUTTON_SHADOW);
		buttonApply.setPrefWidth(WindowSize.BUTTON_WITDH);
		buttonApply.setGraphic(new ImageView(IconFactory.getImage(ICON.TICK)));
		buttonApply.setOnAction((ActionEvent t) -> {
			apply();
		});

		// buuon's panel
		HBox buttonsPanel = new HBox(60, buttonApply, buttonCancel);
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
		// popup.setAlwaysOnTop(true);
		// window prefered size
		popup.setWidth(WindowSize.popupPrefWidth);
		popup.setMinWidth(WindowSize.popupMinHeight);
		popup.setHeight(WindowSize.popupPrefHeight);
		popup.setMinHeight(WindowSize.popupMinHeight);
		popup.show();
	}

	public void apply() {
		exec = Executors.newCachedThreadPool();
		try {
			Callable<Void> connectionListener = () -> {
				System.out.println("Info appling filter ... ");
				return null;
			};
			exec.submit(connectionListener);
		} catch (Exception exc) {
			System.out.println("Error - while trying to apply filter");
			exc.printStackTrace();
			throw exc;
		} finally {
			exec.shutdown();
		}
	}
}
