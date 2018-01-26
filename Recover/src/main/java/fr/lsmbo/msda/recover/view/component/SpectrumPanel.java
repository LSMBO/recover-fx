package fr.lsmbo.msda.recover.view.component;

import fr.lsmbo.msda.recover.util.IconResource;
import fr.lsmbo.msda.recover.util.IconResource.ICON;
import fr.lsmbo.msda.recover.util.TitledBorderPane;
import fr.lsmbo.msda.recover.util.WindowSize;
import fr.lsmbo.msda.recover.view.SpectrumChart;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * @author aromdhani
 *
 */
public class SpectrumPanel extends SplitPane {
	private TitledBorderPane infoPane = null;
	private TitledBorderPane graphPane = null;
	private SwingNode swingNodeForChart = new SwingNode();

	public SwingNode getSwingNodeForChart() {
		return swingNodeForChart;
	}

	public void setSwingNodeForChart(SwingNode swingNodeForChart) {
		this.swingNodeForChart = swingNodeForChart;
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
		infoPane = new TitledBorderPane("", IconResource.getImage(ICON.DATAQUERY), new VBox(), "");
		infoPane.setPrefWidth(WindowSize.mainPanePreferWidth / 4);
		
		graphPane = new TitledBorderPane("", IconResource.getImage(ICON.GRAPH), swingNodeForChart, "");
		this.getItems().addAll(infoPane, graphPane);
	}

	public static SpectrumPanel getInstance() {
		return Holder.spectrumPanel;
	}
}
