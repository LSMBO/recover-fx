package fr.lsmbo.msda.recover.view.panel;

import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import fr.lsmbo.msda.recover.util.WindowSize;;

/**
 * 
 * @author aromdhani
 *
 */
public class MainPanel extends BorderPane {
	// menu panel
	private VBox menuPane = null;
	
	// main panel
	private SplitPane splitPane = null;
	private VBox spectrumlistPane = null;
	private VBox spectrumPane = null;
	private VBox dataPane = null;

	private static class Holder {
		private static final MainPanel mainPanel = new MainPanel();
	}

	public static MainPanel getInstance() {
		return Holder.mainPanel;
	}

	// create main panel
	private MainPanel() {
		menuPane = MenuPanel.getInstance();
		splitPane = new SplitPane();
		spectrumlistPane = ListSpetrumPanel.getInstance();
		spectrumPane = new VBox();
		dataPane = new VBox();
		// create menu panel
		this.setTop(menuPane);
		// create main panel
		VBox mainPanel = new VBox(5);
		splitPane.setOrientation(Orientation.VERTICAL);
		splitPane.setPrefSize(WindowSize.mainPanePreferWidth, WindowSize.mainPanePreferHeight);

		dataPane.getChildren().addAll(ConsolePanel.getInstance());
		dataPane.setPrefSize(WindowSize.mainPanePreferWidth, WindowSize.mainPanePreferHeight / 4);
		dataPane.setMaxHeight(WindowSize.mainPanePreferHeight / 4);

		splitPane.getItems().addAll(spectrumlistPane, spectrumPane, dataPane);
		mainPanel.setPadding(new Insets(5, 10, 5, 10));
		mainPanel.getChildren().addAll(splitPane);
		mainPanel.autosize();
		this.setCenter(mainPanel);
		this.autosize();
	}
}
