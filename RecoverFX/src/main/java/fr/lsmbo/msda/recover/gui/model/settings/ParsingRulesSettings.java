/*
 * 
 */
package fr.lsmbo.msda.recover.gui.model.settings;

public class ParsingRulesSettings extends RecoverSetting {

	private String parsingRuleName;
	private String parsingRuleValue;

	public ParsingRulesSettings() {
		this.initialize();
	}

	public ParsingRulesSettings(String parsingRuleName, String parsingRuleValue) {
		super();
		this.parsingRuleName = parsingRuleName;
		this.parsingRuleValue = parsingRuleValue;
		this.initialize();
	}

	public String getParsingRuleName() {
		return parsingRuleName;
	}

	public String getParsingRuleValue() {
		return parsingRuleValue;
	}

	private void initialize() {
		this.name = "Retention times parsing rules";
		this.description = ""; // TODO write a proper description
	}

	public void setParsingRuleName(String parsingRuleName) {
		this.parsingRuleName = parsingRuleName;
	}

	public void setParsingRuleValue(String parsingRuleValue) {
		this.parsingRuleValue = parsingRuleValue;
	}

	@Override
	public String toString() {
		return "Parsing Rule Name:"+ parsingRuleName + " ; " + "parsingRuleValue: " + parsingRuleValue
				+ "\n";
	}

}
