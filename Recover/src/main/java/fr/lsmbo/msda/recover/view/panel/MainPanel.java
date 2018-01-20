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
		// create menu panel
		this.setTop(menuPane);
		// create main panel
		VBox mainPanel = new VBox(5);
		splitPane.setOrientation(Orientation.VERTICAL);
		splitPane.setPrefSize(WindowSize.mainPanePreferWidth, WindowSize.mainPanePreferHeight);
       
		dataPane.getChildren().addAll(new ConsolePanel());
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
