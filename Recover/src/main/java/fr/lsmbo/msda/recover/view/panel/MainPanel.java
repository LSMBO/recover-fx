package fr.lsmbo.msda.recover.view.panel;

import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import fr.lsmbo.msda.recover.util.IconFactory;
import fr.lsmbo.msda.recover.util.IconFactory.ICON;
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
	private SplitPane mainSplitPane = null;
	private VBox spectrumlistPane = null;
	private SplitPane spectrumPane = null;
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
		mainSplitPane = new SplitPane();
		spectrumlistPane = SpectrumListPanel.getInstance();
		spectrumPane = SpectrumPanel.getInstance();
		dataPane = new VBox();
		// create menu panel
		this.setTop(menuPane);
		// create main panel
		VBox mainPanel = new VBox(5);
		mainSplitPane.setOrientation(Orientation.VERTICAL);
		mainSplitPane.setPrefSize(WindowSize.mainPanePreferWidth, WindowSize.mainPanePreferHeight);

		// create tabPane
		TabPane tabPane = new TabPane();
		Tab tab = new Tab();
		tab.setText("Console");
		tab.setClosable(false);
		tab.setGraphic(new ImageView(IconFactory.getImage(ICON.CONSOLE)));
		tab.setContent(ConsolePanel.getInstance());
		tabPane.getTabs().add(tab);

		dataPane.getChildren().addAll(tabPane);
		dataPane.setPrefSize(WindowSize.mainPanePreferWidth, WindowSize.mainPanePreferHeight / 5);
		dataPane.setMaxHeight(WindowSize.mainPanePreferHeight / 4);

		mainSplitPane.getItems().addAll(spectrumlistPane, spectrumPane, dataPane);
		mainPanel.setPadding(new Insets(5, 10, 5, 10));
		mainPanel.getChildren().addAll(mainSplitPane);
		mainPanel.autosize();
		this.setCenter(mainPanel);
		this.autosize();
	}
}
