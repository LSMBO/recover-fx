package fr.lsmbo.msda.recover.util;

import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * 
 * @author aromdhani
 *
 */
public class TitledBorderPane extends VBox {
	String color = "slategrey";
	String title = "";
	Node node = null;
	String toolTip = "";

	public TitledBorderPane(String title, Node node, String toolTip, String color) {
		Label titleLabel = new Label(" " + toolTip + "");
		titleLabel.setAlignment(Pos.TOP_LEFT);
		titleLabel.setStyle("-fx-font-size: 14;" + "-fx-font-weight: bold;" + "-fx-font-style: italic;"
				+ "-fx-text-fill: ${colorStr};" + "-fx-translate-y: -20;" + "-fx-translate-x: 15;");
		VBox contenNode = new VBox(5, node);
		contenNode.setPadding(new Insets(10, 5, 10, 5));
		this.setStyle("-fx-content-display: center;" + "-fx-border-color: " + color + ";" + "-fx-border-width: 1;"
				+ "-fx-border-radius: 2;");
		this.setPadding(new Insets(10, 5, 10, 5));
		this.getChildren().addAll(titleLabel, contenNode);
	}
}
