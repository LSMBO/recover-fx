/*
 * 
 */
package fr.lsmbo.msda.recover.gui.view.dialog;

import java.util.function.Supplier;

import fr.lsmbo.msda.recover.gui.IconResource;
import fr.lsmbo.msda.recover.gui.IconResource.ICON;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Creates and displays pop up dialog
 * 
 * @author aromdhani
 * @param <T>
 *
 */
public class ConfirmDialog<T> extends Stage {
	private String popupTitle;
	private String message;
	private Supplier<T> action;
	private Stage parentStage;

	public String getPopupTitle() {
		return popupTitle;
	}

	public void setPopupTitle(String popupTitle) {
		this.popupTitle = popupTitle;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Supplier<T> getAction() {
		return action;
	}

	public void setAction(Supplier<T> action) {
		this.action = action;
	}

	public Stage getParentStage() {
		return parentStage;
	}

	public void setParentStage(Stage parentStage) {
		this.parentStage = parentStage;
	}

	public ConfirmDialog(ICON icon, String popupTitle, String message, Supplier<T> action, Stage parentStage) {
		Stage popup = this;
		popup.initOwner(parentStage);
		popup.getIcons().add(IconResource.getImage(icon));
		// Close this dialog on cancel button pressed
		Button buttonCancel = new Button(" Cancel ");
		buttonCancel.setGraphic(new ImageView(IconResource.getImage(ICON.CROSS)));
		buttonCancel.setOnAction((e) -> {
			popup.close();
		});
		// Set action on ok button pressed
		Button buttonOk = new Button(" OK ");
		buttonOk.setGraphic(new ImageView(IconResource.getImage(ICON.TICK)));
		buttonOk.setOnAction((e) -> {
			action.get();
		});
		// Create dialog content
		VBox root = new VBox(30);
		root.setPadding(new Insets(20, 10, 10, 10));
		// Create buttons panel
		HBox buttonsPanel = new HBox(20, buttonOk, buttonCancel);
		buttonsPanel.setAlignment(Pos.CENTER);
		// Create message Label
		Label messageLabel = new Label(message);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(messageLabel, buttonsPanel);
		// Create scene
		Scene scene = new Scene(new VBox(5, root));
		popup.setTitle(popupTitle);
		popup.setScene(scene);
		// Size
		popup.setWidth(400);
		popup.setHeight(160);
		popup.setResizable(false);
		popup.show();
	}
}