package fr.lsmbo.msda.recover.view.panel;

import fr.lsmbo.msda.recover.util.WindowSize;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

/**
 * 
 * @author aromdhani
 *
 */
public class SpectrumListPanel extends VBox {
	// create table
	private TableView<String> table = null;

	public TableView<String> getTable() {
		return table;
	}

	private static class Holder {
		private final static SpectrumListPanel listPanel = new SpectrumListPanel();
	}

	public static SpectrumListPanel getInstance() {
		return Holder.listPanel;
	}

	private SpectrumListPanel() {
		table = new TableView<>();
		TableColumn idCol = new TableColumn("Id");
		TableColumn specNameCol = new TableColumn("Spectrum Name");
		TableColumn precMzCol = new TableColumn("Precu M/Z");
		TableColumn precIntensityCol = new TableColumn("Precu Intensity");
		TableColumn precChargeCol = new TableColumn("Precu Charge");
		TableColumn rtCol = new TableColumn("RT");
		TableColumn totalPeakNumberCol = new TableColumn("Total Peak Number");
		TableColumn identifiedCol = new TableColumn("Identified");
		TableColumn recoverCol = new TableColumn("Recover");
		table.getColumns().addAll(idCol, specNameCol, precMzCol, precIntensityCol, precChargeCol, rtCol,
				totalPeakNumberCol, identifiedCol, recoverCol);
		table.autosize();
		table.setColumnResizePolicy(table.CONSTRAINED_RESIZE_POLICY);
		this.setPrefSize(WindowSize.mainPanePreferWidth, WindowSize.mainPanePreferHeight / 5);
		this.setPadding(new Insets(5, 5, 5, 5));
		this.getChildren().addAll(table);
	}
}
