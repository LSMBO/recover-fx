package fr.lsmbo.msda.recover.view.popup;

import javafx.stage.Modality;
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
import fr.lsmbo.msda.recover.util.IconResource.ICON;

/**
 * 
 * @author aromdhani
 *
 */
public class ParsingRules extends Stage implements WorkPopup {
	Stage popup = this;

	public ParsingRules(String popupTitle, Stage parentStage) {
		popup.initOwner(parentStage);
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.getIcons().add(IconResource.getImage(ICON.EDIT));
		// button cancel
		Button buttonCancel = new Button(" Cancel ");
		buttonCancel.setStyle(StyleUtils.BUTTON_SHADOW);
		buttonCancel.setPrefWidth(WindowSize.BUTTON_WITDH);
		buttonCancel.setGraphic(new ImageView(IconResource.getImage(ICON.CROSS)));
		buttonCancel.setOnAction((ActionEvent t) -> {
			popup.close();
		});
		// button apply
		Button buttonApply = new Button(" Apply ");
		buttonApply.setStyle(StyleUtils.BUTTON_SHADOW);
		buttonApply.setPrefWidth(WindowSize.BUTTON_WITDH);
		buttonApply.setGraphic(new ImageView(IconResource.getImage(ICON.TICK)));
		buttonApply.setOnAction((ActionEvent t) -> {
			apply();
		});

		// panel of buttons
		HBox buttonsPanel = new HBox(60, buttonApply, buttonCancel);
		buttonsPanel.setAlignment(Pos.BASELINE_CENTER);

		// load panels's component
		VBox lodPanel = new VBox(20);
		lodPanel.getChildren().addAll(new ParsingRulesPane());
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

	@Override
	public void apply() {

		// get task executor instance
		TaskExecutor task = TaskExecutor.getInstance();
		task.executorService = ThreadPoolType.getThreadExecutor(TYPE.SHORTTASK);
		try {
			Future<?> f = task.submitRunnabletask(() -> {
				System.out.println("Info editing parsing rules with the new parameters {}");
			});
			f.get();
			while (!f.isDone()) {
				System.out.println("task is running ...");
			}
			if (f.isDone()) {
				popup.close();
			}

		} catch (Exception e) {
			System.out.println("error while trying to edit parsing rules!" + e);
		}
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		popup.close();
	}

}
