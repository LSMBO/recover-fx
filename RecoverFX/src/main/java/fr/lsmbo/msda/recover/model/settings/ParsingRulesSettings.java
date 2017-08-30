package fr.lsmbo.msda.recover.model.settings;

public class ParsingRulesSettings {

	private String parsingRuleName;
	private String parsingRuleValue;

	public ParsingRulesSettings() {
	}

	public ParsingRulesSettings(String parsingRuleName, String parsingRuleValue) {
		super();
		this.parsingRuleName = parsingRuleName;
		this.parsingRuleValue = parsingRuleValue;
	}

	public String getParsingRuleName() {
		return parsingRuleName;
	}

	public void setParsingRuleName(String parsingRuleName) {
		this.parsingRuleName = parsingRuleName;
	}

	public String getParsingRuleValue() {
		return parsingRuleValue;
	}

	public void setParsingRuleValue(String parsingRuleValue) {
		this.parsingRuleValue = parsingRuleValue;
	}
	
	@Override
	public String toString() {
		return 
				"parsingRuleName: "+parsingRuleName+ "\n" + 
				"parsingRuleValue: "+parsingRuleValue;
	}

}
