package fr.lsmbo.msda.recover.gui.model;

import javafx.beans.property.SimpleStringProperty;

public class ExcelFile {
	private final String name;
	private final String column;

	public ExcelFile(String name, String column) {
		this.name = name;
		this.column = column;
	}
}
