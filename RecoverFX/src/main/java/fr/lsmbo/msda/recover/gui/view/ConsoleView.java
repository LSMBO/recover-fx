/*
 * 
 */
package fr.lsmbo.msda.recover.gui.view;

import java.io.PrintStream;

import fr.lsmbo.msda.recover.gui.IconResource;
import fr.lsmbo.msda.recover.gui.IconResource.ICON;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;

/**
 * 
 * Creates and displays console view . Set the PrintStream to the console .
 * 
 * @author aromdhani
 *
 */

public class ConsoleView {

	public static final TabPane getInstance() {
		WebView webview = new WebView();
		PrintStream psOut = new PrintStream(new ConsoleStream(webview));
		System.setOut(psOut);
		System.setErr(psOut);
		webview.autosize();

		StackPane stckPane = new StackPane();
		stckPane.setPadding(new Insets(5, 5, 5, 5));
		stckPane.autosize();
		stckPane.getChildren().addAll(webview);

		TabPane tabPane = new TabPane();
		Tab tab = new Tab();
		tab.setText("Console");
		tab.setClosable(false);
		tab.setGraphic(new ImageView(IconResource.getImage(ICON.CONSOLE)));
		tab.setContent(stckPane);
		tabPane.getTabs().add(tab);
		return tabPane;

	}
}