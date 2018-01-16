package fr.lsmbo.msda.recover.view.panel;

import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import fr.lsmbo.msda.recover.util.TitledBorderPane;
import fr.lsmbo.msda.recover.util.WindowSize;;

/**
 * 
 * @author aromdhani
 *
 */
public class MainPanel extends BorderPane {
	// menu panel
	private final static VBox menuPane = new MenuPanel();

	// main panel
	public final static SplitPane splitPane = new SplitPane();
	public final static VBox spectrumlistPane = new VBox();
	public final static VBox spectrumPane = new VBox();
	public final static VBox dataPane = new VBox();

	// create main panel
	public MainPanel() {
		this.setTop(menuPane);

		VBox SpectrumPanel = new VBox(5);
		splitPane.setOrientation(Orientation.VERTICAL);
		splitPane.setPrefSize(WindowSize.mainPanePreferWidth, WindowSize.mainPanePreferHeight);
		splitPane.getItems().addAll(spectrumlistPane, spectrumPane, dataPane);
		SpectrumPanel.setPadding(new Insets(5, 10, 5, 10));
		SpectrumPanel.getChildren().addAll(splitPane);
		SpectrumPanel.autosize();
		this.setCenter(SpectrumPanel);
		this.autosize();

	}
}
