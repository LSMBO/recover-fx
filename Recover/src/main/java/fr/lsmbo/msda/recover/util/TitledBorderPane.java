package fr.lsmbo.msda.recover.util;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import fr.lsmbo.msda.recover.util.IconFactory.ICON;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * @author aromdhani
 *
 */
public class TitledBorderPane extends StackPane {
	public TitledBorderPane(String title, Image icon, Node node, String toolTip) {
		VBox mainPanel = new VBox(2);
		ImageView imageView = new ImageView(icon);
		mainPanel.setMargin(imageView, new Insets(0, 1, 0, 0));
		mainPanel.getChildren().addAll(imageView, node);
		this.setStyle(StyleUtils.TITLED_BORDER_PANE);
		this.setPadding(new Insets(5, 5, 5, 5));
		this.getChildren().addAll(mainPanel);
	}
}
