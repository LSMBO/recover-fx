package fr.lsmbo.msda.recover.util;

import javafx.scene.layout.StackPane;
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
public class TitledBorderPane extends StackPane {
	public TitledBorderPane(String title, Node node, String toolTip) {
		Label titleLabel = new Label(" " + title + "");
		titleLabel.setStyle(StyleUtils.TITLED_BORDER_PANE_TITLE);
		this.setAlignment(titleLabel, Pos.TOP_LEFT);
		this.setMargin(titleLabel, new Insets(8, 0, 0, 0));
		this.setPadding(new Insets(10, 5, 10, 5));
		this.setStyle(StyleUtils.TITLED_BORDER_PANE);
		this.setPadding(new Insets(10, 5, 10, 5));
		this.getChildren().addAll(titleLabel, node);
	}
}
