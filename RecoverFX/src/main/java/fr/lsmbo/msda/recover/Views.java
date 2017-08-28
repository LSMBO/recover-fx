package fr.lsmbo.msda.recover;

import java.net.URL;

public class Views {

	private final static String path = "view/fxml/";
	public final static URL RECOVER = Main.class.getResource(path + "RecoverView.fxml");
	public final static URL PARSING_RULES = Main.class.getResource(path + "ParsingRulesView.fxml");
	public final static URL FILTER = Main.class.getResource(path + "FilterView.fxml");
	public final static URL IDENTIFIED_SPECTRA = Main.class.getResource(path + "IdentifiedSpectraView.fxml");
	public final static URL COMPARISON_SPECTRA = Main.class.getResource(path + "ComparisonSpectraView.fxml");
	public final static URL COMPARISON_SETTINGS = Main.class.getResource(path + "ComparisonSettingsView.fxml");
	public final static URL EXPORT_BATCH = Main.class.getResource(path + "ExportBatchView.fxml");
	public final static URL IDENTIFIED_SPECTRA_FOR_BATCH = Main.class.getResource(path + "IdentifiedSpectraForBatchView.fxml");
	public final static URL INFORMATION_EXCEL = Main.class.getResource(path + "InformationExcelView.fxml");
}
