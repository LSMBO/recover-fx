package fr.lsmbo.msda.recover.gui.model.settings;

/**
 * Excel file properties model , to hold spectrum titles selection.
 * 
 * @author Aromdhani
 *
 */
public class FileSelectionParams {

	private int rowNumber = 0;
	private String column = "";
	private String currentSheetName = "";
	private String filePath = "";

	/**
	 * Default constructor
	 */
	public FileSelectionParams() {
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
	public FileSelectionParams(String filePath, String currentSheetName, String column, int rowNumber) {
		super();
		this.rowNumber = rowNumber;
		this.column = column;
		this.currentSheetName = currentSheetName;
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
	public final String getCurrentSheetName() {
		return currentSheetName;
	}

	/**
	 * @param currentSheetName
	 *            the current sheet name to set
	 */
	public final void setCurrentSheetName(String currentSheetName) {
		this.currentSheetName = currentSheetName;
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
		currentSheetName = "";
		filePath = "";
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("\n").append("##File properties: ").append("\n").append("##path:").append(filePath).append("\n")
				.append("##sheet name: ").append(currentSheetName).append("\n").append("##column: ").append(column)
				.append("\n").append("##row number: ").append(rowNumber).append("\n");

		return str.toString();
	}

}
