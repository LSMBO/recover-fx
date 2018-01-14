package fr.lsmbo.msda.recover.view.panel;

import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import fr.lsmbo.msda.recover.util.TitledBorderPane;;

/**
 * 
 * @author aromdhani
 *
 */
public class MainPanel extends BorderPane {
	// initialize menu
	public static VBox menuPane = new MenuPanel();
	public static VBox spectrumListPane = new TitledBorderPane("", new VBox(), "");
	public static VBox spectrumPane = new TitledBorderPane("", new VBox(), "");
	public static VBox dataPane = new VBox();

	// create main panel
	public MainPanel() {
		this.setTop(menuPane);
		VBox SpectrumPanel = new VBox(5);
		SpectrumPanel.setPadding(new Insets(5, 10, 5, 10));
		SpectrumPanel.getChildren().addAll(spectrumListPane, spectrumPane);
		this.setCenter(SpectrumPanel);
		this.setBottom(dataPane);
		this.autosize();
	}
}
