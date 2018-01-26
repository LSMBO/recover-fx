package fr.lsmbo.msda.recover.view.component;

import java.io.PrintStream;

import com.sun.media.jfxmedia.logging.Logger;

import fr.lsmbo.msda.recover.util.StyleUtils;
import fr.lsmbo.msda.recover.util.WindowSize;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

/**
 * 
 * @author aromdhani
 *
 */

public class ConsolePanel extends StackPane {

	private WebView webview = null;

	private static class Holder {
    	private final static  ConsolePanel consolePanel= new ConsolePanel();
    }

	public static ConsolePanel getInstance()
	{
		return Holder.consolePanel;
	}

	private ConsolePanel() {
		webview = new WebView();
		PrintStream psOut = new PrintStream(new Console(webview));
		System.setOut(psOut);
		System.setErr(psOut);
		webview.autosize();
		this.setStyle(StyleUtils.WEBVIEW_BORDER);
		this.setPadding(new Insets(5, 5, 5, 5));
		this.autosize();
		this.getChildren().addAll(webview);
	}
}
