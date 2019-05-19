package fr.lsmbo.msda.recover.gui.model.settings;

/**
 * Spectrum titles selection from an excel file.
 * 
 * @author Aromdhani
 *
 */
public class SpectrumTitleSelector {

	private int rowNumber = 0;
	private String column = "";
	private String sheetName = "";
	private String filePath = "";

	/**
	 * Default constructor
	 */
	public SpectrumTitleSelector() {
		super();
	}

	/**
	 * 
	 * @param rowNumber
	 *            the row number
	 * @param column
	 *            the column name
	 * @param currentSheetName
	 *            the sheet name
	 * @param filePath
	 *            the file path
	 */
	public SpectrumTitleSelector(String filePath, String sheetName, String column, int rowNumber) {
		super();
		this.rowNumber = rowNumber;
		this.column = column;
		this.sheetName = sheetName;
		this.filePath = filePath;
	}

	/**
	 * @return the row number
	 */
	public final int getRowNumber() {
		return rowNumber;
	}

	/**
	 * @param rowNumber
	 *            the row number to set
	 */
	public final void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	/**
	 * @return the column
	 */
	public final String getColumn() {
		return column;
	}

	/**
	 * @param column
	 *            the column to set
	 */
	public final void setColumn(String column) {
		this.column = column;
	}

	/**
	 * @return the currentSheetName
	 */
	public final String getSheetName() {
		return sheetName;
	}

	/**
	 * @param currentSheetName
	 *            the current sheet name to set
	 */
	public final void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * @return the file path
	 */
	public final String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath
	 *            the file path to set
	 */
	public final void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void initialize() {
		rowNumber = 0;
		column = "";
		sheetName = "";
		filePath = "";
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("\n").append("###Spectrum titles file: ").append(filePath).append("\n").append("###Sheet name: ")
				.append(sheetName).append(" ; ").append("column: ").append(column).append(" ; ")
				.append("row number: ").append(rowNumber).append("\n");
		return str.toString();
	}

}
