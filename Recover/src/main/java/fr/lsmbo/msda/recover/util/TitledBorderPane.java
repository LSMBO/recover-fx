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
	public TitledBorderPane(String title, Node node, String toolTip) {
		Label titleLabel = new Label(" " + toolTip + "");
		titleLabel.setAlignment(Pos.TOP_LEFT);
		titleLabel.setStyle(StyleUtils.TITLED_BORDER_PANE_TITLE);
		VBox contenNode = new VBox(5, node);
		contenNode.setPadding(new Insets(10, 5, 10, 5));
		this.setStyle(StyleUtils.TITLED_BORDER_PANE);
		this.setPadding(new Insets(10, 5, 10, 5));
		this.getChildren().addAll(titleLabel, contenNode);
	}
}
