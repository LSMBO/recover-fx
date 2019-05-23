/*
 * 
 */
package fr.lsmbo.msda.recover.gui.model.settings;

/**
 * Builds user parameters.
 * 
 * @author Alexandre Burel
 * @author Aromdhani
 *
 */
public class UserParams extends RecoverSetting {

	private String userName;
	private String timestamp;
	private String recoverVersion;

	private ComparisonSettings comparison;
	private ParsingRulesSettings parsingRules;

	public UserParams() {
		this("", "", "", new ComparisonSettings(), new ParsingRulesSettings());
	}

	public UserParams(ComparisonSettings comparison, ParsingRulesSettings parsingRules) {
		this("", "", "", comparison, parsingRules);
	}

	public UserParams(String userName, String timestamp, String recoverVersion, ComparisonSettings comparison,
			ParsingRulesSettings parsingRules) {
		super();
		this.userName = userName;
		this.timestamp = timestamp;
		this.recoverVersion = recoverVersion;
		this.comparison = comparison;
		this.parsingRules = parsingRules;
		this.initialize();
	}

	public ComparisonSettings getComparison() {
		return comparison;
	}

	public ParsingRulesSettings getParsingRules() {
		return parsingRules;
	}

	public String getRecoverVersion() {
		return recoverVersion;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getUserName() {
		return userName;
	}

	private void initialize() {
		this.name = "Current user parameters";
		this.description = ""; // TODO write a proper description
	}

	public void setComparison(ComparisonSettings comparison) {
		this.comparison = comparison;
	}

	public void setParsingRules(ParsingRulesSettings parsingRules) {
		this.parsingRules = parsingRules;
	}

	public void setRecoverVersion(String recoverVersion) {
		this.recoverVersion = recoverVersion;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		StringBuilder userParamsStr = new StringBuilder();
		userParamsStr.append("\n").append("##Version: ").append(this.recoverVersion).append("\n")
				.append("##Comparison: ").append(this.comparison.toString()).append("\n").append("##Parsing rules: ")
				.append(this.parsingRules.toString()).append("\n");
		return userParamsStr.toString();
	}
}
