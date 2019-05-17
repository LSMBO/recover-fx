/*
 * 
 */
package fr.lsmbo.msda.recover.gui.model.settings;

/**
 * Builds user parameters.
 * 
 * @author Alexandre Burel
 * @author aromdhani
 *
 */
public class UserParams extends RecoverSetting {

	private String userName;
	private String timestamp;
	private String recoverVersion;

	private ComparisonSettings comparison;
	private ParsingRulesSettings parsingRules;

	public UserParams() {
		this("", "", "", new QualityFiltersSettings(), new ComparisonSettings(), new ParsingRulesSettings());
	}

	public UserParams(QualityFiltersSettings qualityFilters, ComparisonSettings comparison,
			ParsingRulesSettings parsingRules) {
		this("", "", "", qualityFilters, comparison, parsingRules);
	}

	public UserParams(String userName, String timestamp, String recoverVersion, QualityFiltersSettings qualityFilters,
			ComparisonSettings comparison, ParsingRulesSettings parsingRules) {
		super();
		this.userName = userName;
		this.timestamp = timestamp;
		this.recoverVersion = recoverVersion;
		this.comparison = comparison;
		this.parsingRules = parsingRules;
		this.initialize();
	}

	private void initialize() {
		this.name = "Current user parameters";
		this.description = ""; // TODO write a proper description
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getRecoverVersion() {
		return recoverVersion;
	}

	public void setRecoverVersion(String recoverVersion) {
		this.recoverVersion = recoverVersion;
	}

	public ComparisonSettings getComparison() {
		return comparison;
	}

	public void setComparison(ComparisonSettings comparison) {
		this.comparison = comparison;
	}

	public ParsingRulesSettings getParsingRules() {
		return parsingRules;
	}

	public void setParsingRules(ParsingRulesSettings parsingRules) {
		this.parsingRules = parsingRules;
	}

	@Override
	public String toString() {
		StringBuilder userParamsStr = new StringBuilder();
		userParamsStr.append("\n").append("##User: ").append(this.userName).append("\n").append("##Date: ")
				.append(this.timestamp).append("\n").append("##Version: ").append(this.recoverVersion).append("\n")
				.append("##comparison: ").append(this.comparison.toString()).append("\n").append("##Parsing rules: ")
				.append(this.parsingRules.toString()).append("\n");
		return userParamsStr.toString();
	}
}
