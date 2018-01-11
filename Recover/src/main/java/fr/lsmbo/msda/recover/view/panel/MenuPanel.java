package fr.lsmbo.msda.recover.view.panel;

import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.MenuBar;

/**
 * 
 * @author aromdhani
 *
 */

public class MenuPanel extends VBox {
	public static MenuBar menuBar = new MenuBarItems();
	public MenuPanel() {
		this.getChildren().addAll(menuBar);
		this.setPadding(new Insets(10, 5, 10, 5));
	}
}