package fr.lsmbo.msda.recover.view.panel;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.MenuBar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import fr.lsmbo.msda.recover.util.IconRessource;
import fr.lsmbo.msda.recover.util.IconRessource.ICON;
import fr.lsmbo.msda.recover.gui.Recover;
import fr.lsmbo.msda.recover.gui.Recover;

/**
 * 
 * @author aromdhani
 *
 */
public class MenuBarItems extends MenuBar {
	/**
	 * 
	 * @return menuBar
	 */
	public MenuBarItems() {
		// menu
		Menu fileMenu = new Menu("File");
		Menu actionsMenu = new Menu("Actions");
		Menu helpMenu = new Menu("Help");
		// file menu items
		MenuItem openFile = new MenuItem("Open File  ...  Ctrl+O ");
//		 System.out.println(IconRessource.getImage(ICON.LOAD));
//		ImageView imageView = new ImageView(IconRessource.getImage(ICON.LOAD));
//		imageView.setFitWidth(25);
//		imageView.setFitHeight(25);
//		openFile.setGraphic(imageView);
		MenuItem exportFile = new MenuItem("Export File  ...  Ctrl+E ");
		MenuItem exportInBatchFile = new MenuItem("Export in batch  ... ");
		MenuItem exitFile = new MenuItem("Exit Ctrl+Q");
		exitFile.setOnAction((ActionEvent t) -> {
			new ExitPopup("Exit Recover", "Are you sure you want to exit Recover ? ",Recover.mainStage);
		});
		fileMenu.getItems().addAll(openFile, exportFile, exportInBatchFile, exitFile);

		// action menu items
		MenuItem applyQFilterAction = new MenuItem(" Apply Quality Filter ");
		MenuItem editPRulesAction = new MenuItem(" Edit Parsing Rules ");
		MenuItem getIdentifiedSpecAction = new MenuItem(" Get Identified Spectra ");
		MenuItem getIdentifiedAxisAction = new MenuItem(" Use Identified Axis ");
		MenuItem resetRecoverAction = new MenuItem(" Reset Recover ");
		MenuItem flaggedSpecAction = new MenuItem(" Flagged Spectrum ");
		actionsMenu.getItems().addAll(applyQFilterAction, editPRulesAction, getIdentifiedSpecAction,
				getIdentifiedAxisAction, resetRecoverAction, flaggedSpecAction);
		// help menu items
		MenuItem getStartedHelp = new MenuItem(" Get started ");
		MenuItem aboutHelp = new MenuItem(" About ");
		helpMenu.getItems().addAll(getStartedHelp, aboutHelp);
		this.getMenus().addAll(fileMenu, actionsMenu, helpMenu);
	}

}
