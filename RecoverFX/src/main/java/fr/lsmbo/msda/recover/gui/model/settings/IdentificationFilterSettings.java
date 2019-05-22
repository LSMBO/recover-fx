/*
 * 
 */
package fr.lsmbo.msda.recover.gui.model.settings;

public class IdentificationFilterSettings extends RecoverSetting {

	private Boolean selectedFilter;
	private Boolean recoverIdentified;
	private Boolean recoverNonIdentified;

	private String excelFilePath;
	private String excelSheetName;
	private String excelColumn;
	private Integer excelFirstRow;

	public IdentificationFilterSettings() {
		this.initialize();
	}

	public IdentificationFilterSettings(Boolean selectedFilter, Boolean recoverIdentified, Boolean recoverNonIdentified, 
			String excelFilePath, String excelSheetName, String excelColumn, Integer excelFirstRow) {
		super();
		this.selectedFilter = selectedFilter;
		this.recoverIdentified = recoverIdentified;
		this.recoverNonIdentified = recoverNonIdentified;
		this.excelFilePath = excelFilePath;
		this.excelSheetName = excelSheetName;
		this.excelColumn = excelColumn;
		this.excelFirstRow = excelFirstRow;
		this.initialize();
	}
	
	public String getExcelColumn() {
		return excelColumn;
	}

	public String getExcelFilePath() {
		return excelFilePath;
	}

	public Integer getExcelFirstRow() {
		return excelFirstRow;
	}

	public String getExcelSheetName() {
		return excelSheetName;
	}

	public Boolean getRecoverIdentified() {
		return recoverIdentified;
	}

	public Boolean getRecoverNonIdentified() {
		return recoverNonIdentified;
	}

	public Boolean getSelectedFilter() {
		return selectedFilter;
	}

	private void initialize() {
		this.name = "Identification results";
		this.description = ""; // TODO write a proper description
	}

	public void setExcelColumn(String excelColumn) {
		this.excelColumn = excelColumn;
	}

	public void setExcelFilePath(String excelFilePath) {
		this.excelFilePath = excelFilePath;
	}

	public void setExcelFirstRow(Integer excelFirstRow) {
		this.excelFirstRow = excelFirstRow;
	}

	public void setExcelSheetName(String excelSheetName) {
		this.excelSheetName = excelSheetName;
	}

	public void setRecoverIdentified(Boolean recoverIdentified) {
		this.recoverIdentified = recoverIdentified;
	}

	public void setRecoverNonIdentified(Boolean recoverNonIdentified) {
		this.recoverNonIdentified = recoverNonIdentified;
	}

	public void setSelectedFilter(Boolean selectedFilter) {
		this.selectedFilter = selectedFilter;
	}
	
	@Override
	public String toString() {
		return 
				"selectedFilter: "+selectedFilter+ "\n" + 
				"recoverIdentified: "+recoverIdentified+ "\n" + 
				"recoverNonIdentified: "+recoverNonIdentified+ "\n" + 
				"excelFilePath: "+excelFilePath+ "\n" + 
				"excelSheetName: "+excelSheetName+ "\n" + 
				"excelColumn: "+excelColumn+ "\n" + 
				"excelFirstRow: "+excelFirstRow;
	}

}
