package fr.lsmbo.msda.recover.view.popup;

import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.concurrent.Future;

import fr.lsmbo.msda.recover.task.TaskExecutor;
import fr.lsmbo.msda.recover.task.ThreadPoolType;
import fr.lsmbo.msda.recover.task.ThreadPoolType.TYPE;
import fr.lsmbo.msda.recover.util.*;
import fr.lsmbo.msda.recover.util.IconFactory.ICON;

/**
 * 
 * @author aromdhani
 *
 */
public class ApplyFilter extends Stage {
	Stage popup = this;

	public ApplyFilter(String popupTitle, Stage parentStage) {
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
		lodPanel.getChildren().addAll(new AccordionPanel());
		VBox root = new VBox(20);
		root.setStyle(StyleUtils.DIALOG_MODAL);
		root.getChildren().addAll(lodPanel, buttonsPanel);

		// scene
		Scene scene = new Scene(new VBox(5, root));
		popup.setTitle(popupTitle);
		popup.setScene(scene);
		// window preferred size
		popup.setWidth(WindowSize.popupPrefWidth);
		popup.setMinWidth(WindowSize.popupMinHeight);
		popup.setHeight(WindowSize.popupPrefHeight);
		popup.setMinHeight(WindowSize.popupMinHeight);
		popup.show();
	}

	public void apply() {
		TaskExecutor task = TaskExecutor.getInstance();
		task.executorService = ThreadPoolType.getThreadExecutor(TYPE.SHORTTASK);
		try {
			Future<?> f = task.submitRunnabletask(() -> {
				System.out.println("Info applying filter with the new parameters {}");
			});
			f.get();
			while (!f.isDone()) {
				System.out.println("task is running ...");

			}
		} catch (Exception e) {
			System.out.println("error while trying to apply filter: " + e);
		} finally {
			popup.close();
		}
	}
}
