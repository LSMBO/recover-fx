package fr.lsmbo.msda.recover.view.panel;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.OutputStream;
import java.io.PrintStream;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

/**
 * 
 * @author aromdhani
 *
 */
public class TaskPanel extends VBox {

	private StackPane st = new StackPane();
	private WebView consoleDisp = new WebView();

	public StackPane getSt() {
		return st;
	}

	public void setSt(StackPane st) {
		this.st = st;
	}

	public WebView getConsoleDisp() {
		return consoleDisp;
	}

	public void setConsoleDisp(WebView consoleDisp) {
		this.consoleDisp = consoleDisp;
	}

	public TaskPanel() {
		consoleDisp.setStyle("-fx-border-color: #C0C0C0; -fx-border-width: 1; -fx-border-radius:2;");
		consoleDisp.autosize();
		st.getChildren().addAll();
		new VBox(8, st);
	}

	public TaskPanel(StackPane st, WebView consoleDisp) {
		consoleDisp.setStyle("-fx-border-color: #C0C0C0; -fx-border-width: 1; -fx-border-radius:2;");
		consoleDisp.autosize();
		st.getChildren().addAll();
		new VBox(8, st);
	}
}
