package fr.lsmbo.msda.recover.view.component;

import java.io.File;

import fr.lsmbo.msda.recover.util.IconResource;
import fr.lsmbo.msda.recover.util.IconResource.ICON;
import fr.lsmbo.msda.recover.util.TitledBorderPane;
import fr.lsmbo.msda.recover.util.WindowSize;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * builds spectrum panel with the graph of spectrum and their information
 * 
 * @author aromdhani
 *
 */
public class SpectrumPanel extends SplitPane {

	private TitledBorderPane infoPane = null;
	private TitledBorderPane graphPane = null;
	private WebView nodeForChart = null;
	private StringBuilder strBuilder = null;
	private WebEngine webEngine = null;

	public StringBuilder getStrBuilder() {
		return strBuilder;
	}

	public void setStrBuilder(StringBuilder strBuilder) {
		this.strBuilder = strBuilder;
	}

	public WebEngine getWebEngine() {
		return webEngine;
	}

	public void setWebEngine(WebEngine webEngine) {
		this.webEngine = webEngine;
	}

	public WebView getNodeForChart() {
		return nodeForChart;
	}

	public void setNodeForChart(WebView nodeForChart) {
		this.nodeForChart = nodeForChart;
	}

	public TitledBorderPane getInfoPane() {
		return infoPane;
	}

	public void setInfoPane(TitledBorderPane infoPane) {
		this.infoPane = infoPane;
	}

	public TitledBorderPane getGraphPane() {
		return graphPane;
	}

	public void setGraphPane(TitledBorderPane graphPane) {
		this.graphPane = graphPane;
	}

	private static class Holder {
		private static final SpectrumPanel spectrumPanel = new SpectrumPanel();
	}

	private SpectrumPanel() {
		this.setOrientation(Orientation.HORIZONTAL);
		this.setPadding(new Insets(5, 5, 5, 5));
		nodeForChart = new WebView();
		// File file = new File();
		WebEngine webengine = nodeForChart.getEngine();
		webengine.loadContent("");
		infoPane = new TitledBorderPane("", IconResource.getImage(ICON.DATAQUERY), new VBox(), "");
		infoPane.setPrefWidth(WindowSize.mainPanePreferWidth / 4);
		infoPane.setPrefHeight(WindowSize.mainPanePreferWidth / 3);
		graphPane = new TitledBorderPane("", IconResource.getImage(ICON.GRAPH), nodeForChart, "");
		this.getItems().addAll(infoPane, graphPane);
	}

	public static SpectrumPanel getInstance() {
		return Holder.spectrumPanel;
	}
}
