package fr.lsmbo.msda.recover.view.component;

import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.MenuBar;

/**
 * 
 * @author aromdhani
 *
 */

public class MenuPanel extends VBox {
	private MenuBar menuBar = null;

	public MenuBar getMenuBar() {
		return menuBar;
	}

	private static class Holder {
		private static final MenuPanel menuPanel = new MenuPanel();
	}

	public static MenuPanel getInstance() {
		return Holder.menuPanel;
	}

	private MenuPanel() {
		menuBar = MenuBarItems.getInstance();
		this.getChildren().addAll(menuBar);
		this.setPadding(new Insets(10, 5, 10, 5));
	}
}