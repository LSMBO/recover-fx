package fr.lsmbo.msda.recover.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.scene.web.WebView;

/**
 * 
 * @author aromdhani
 *
 */
public class Console extends OutputStream {

	private WebView webView;
	private String htmlHeader = "<html> <head>" + "<script language=\"javascript\" type=\"text/javascript\">"
			+ "function toBottom(){" + "window.print(\" in toBottom()\")"
			+ "window.scroll(0,document.body.scrollHeight);" + "}" + "</script>" + "</head>"
			+ "<body style =\"font-size:12px;\" onload='toBottom()'><kbd>" + "</body>" + "</html> ";
	private StringBuilder strBuilder = new StringBuilder(htmlHeader);

	public Console(WebView webView) {
		this.webView = webView;
	}

	public synchronized void addText(String newlines) {

		strBuilder.append("<span style=\"white-space:pre-line\">" + newlines + "</span>")
				.append(System.lineSeparator());
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				webView.getEngine().loadContent(strBuilder.toString());
			}
		});
	}

	public String textMatch(String text) {
		String color = "balck";
		if (isTextMatches("error") || isTextMatches("exception") || isTextMatches("fail")) {
			color = "red";
		} else if (isTextMatches("warn")) {
			color = "orange";
		} else if (isTextMatches("info") || isTextMatches("success")) {
			color = "green";
		} else {
			color = "balck";
		}
		return "<kbd style =\'color:" + color + "\'</bd>";
	}

	public boolean isTextMatches(String text) {
		boolean find = false;
		String pattern = "(?s)(?i).*" + text + ".*";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(text);
		if (m.find())
			return find = true;
		return find;
	}

	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
		addText(String.valueOf((char) b));
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
}
