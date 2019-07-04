/*
 * 
 */
package fr.lsmbo.msda.recover.gui.view;

import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.scene.web.WebView;

/**
 * Wraps, set the style of the output text to the console.
 * 
 * @author Aromdhani
 * 
 *
 */
public class ConsoleStream extends OutputStream {

	private WebView webView;
	private String htmlHeader = "<html> <head>" + "<script language=\"javascript\" type=\"text/javascript\">"
			+ "function toBottom(){" + "window.print(\" in toBottom()\")"
			+ "window.scroll(0,document.body.scrollHeight);} </script> </head>"
			+ "<body style =\"font-size:12px;\" onload='toBottom()'><kbd>" + "</body>" + "</html> ";
	private StringBuilder strBuilder = new StringBuilder(htmlHeader);

	/**
	 * Set the web view as pane of output
	 * 
	 * @param webView
	 *            the web view to set
	 */
	public ConsoleStream(WebView webView) {
		this.webView = webView;

	}

	/**
	 * Add a new lines to the output.
	 * 
	 * @param newlines
	 *            the new lines to add
	 */
	public synchronized void addText(String newlines) {
		strBuilder.append(textMatch("<span style=\"white-space:pre-line\">" + newlines + "</span>"));
		Platform.runLater(() -> {
			webView.getEngine().loadContent(strBuilder.toString());
		});
	}

	/**
	 * Determines whether is a new line.
	 * 
	 * @param text
	 *            to test
	 * @return <code>true</code> if the text matches
	 */
	public boolean isTextMatches(String text) {
		boolean find = false;
		String pattern = "(?s)(?i).*" + text + ".*";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(text);
		if (m.find())
			return find = true;
		return find;
	}

	/**
	 * Add the style to the text of output
	 * 
	 * @param text
	 *            the text to add style
	 * @return the colored text
	 */
	public String textMatch(String text) {
		String color = "black";
		if (text.contains("error") || text.contains("Exception") || text.contains("fail")) {
			color = "red";
		} else if (text.contains("warn")) {
			color = "orange";
		} else if (text.contains("success")) {
			color = "green";
		} else {
			color = "black";
		}
		return "<kbd style =\'color:" + color + "\'>" + text + "</kbd>";
	}

	@Override
	public void write(byte b[]) throws IOException {
		// TODO Auto-generated method stub
		String str = new String(b);
		addText(str);
	}

	@Override
	public void write(byte b[], int off, int len) throws IOException {
		String str = new String(b, off, len);
		addText(str);
	}

	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
		addText(String.valueOf((char) b));
	}
}