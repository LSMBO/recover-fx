package fr.lsmbo.msda.recover.view.panel;

import javafx.scene.layout.VBox;

/**
 * 
 * @author aromdhani
 *
 */
public class DataPanel extends VBox {
	
	private static class Holder {
		private static final DataPanel dataPanel = new DataPanel();
	}

	private DataPanel() {
	}

	public static DataPanel getInstance() {
		return Holder.dataPanel;
	}
}
