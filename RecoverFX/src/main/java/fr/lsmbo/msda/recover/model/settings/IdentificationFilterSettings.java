package fr.lsmbo.msda.recover.model.settings;

public class IdentificationFilterSettings {

	private Boolean selectedFilter;
	private Boolean recoverIdentified;
	private Boolean recoverNonIdentified;

	private String excelFilePath;
	private String excelSheetName;
	private String excelColumn;
	private Integer excelFirstRow;

	public IdentificationFilterSettings() {
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
	}

	public Boolean getSelectedFilter() {
		return selectedFilter;
	}

	public void setSelectedFilter(Boolean selectedFilter) {
		this.selectedFilter = selectedFilter;
	}

	public Boolean getRecoverIdentified() {
		return recoverIdentified;
	}

	public void setRecoverIdentified(Boolean recoverIdentified) {
		this.recoverIdentified = recoverIdentified;
	}

	public Boolean getRecoverNonIdentified() {
		return recoverNonIdentified;
	}

	public void setRecoverNonIdentified(Boolean recoverNonIdentified) {
		this.recoverNonIdentified = recoverNonIdentified;
	}

	public String getExcelFilePath() {
		return excelFilePath;
	}

	public void setExcelFilePath(String excelFilePath) {
		this.excelFilePath = excelFilePath;
	}

	public String getExcelSheetName() {
		return excelSheetName;
	}

	public void setExcelSheetName(String excelSheetName) {
		this.excelSheetName = excelSheetName;
	}

	public String getExcelColumn() {
		return excelColumn;
	}

	public void setExcelColumn(String excelColumn) {
		this.excelColumn = excelColumn;
	}

	public Integer getExcelFirstRow() {
		return excelFirstRow;
	}

	public void setExcelFirstRow(Integer excelFirstRow) {
		this.excelFirstRow = excelFirstRow;
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
