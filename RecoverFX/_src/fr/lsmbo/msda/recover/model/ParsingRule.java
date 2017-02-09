package fr.lsmbo.msda.recover.model;

public class ParsingRule {

	private String name;
	private String regex;
	private String propertyKey;
	private Integer index;

	public ParsingRule(String name, String regex, String key, Integer index) {
		this.name = name;
		this.regex = regex;
		this.propertyKey = key;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getPropertyKey() {
		return propertyKey;
	}

	public void setPropertyKey(String key) {
		this.propertyKey = key;
	}
	
	public String toString() {
		return name;
	}
	
	public String getFullDescription() {
		return "{" + propertyKey + "} " + name + ": " + regex;
	}
	
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
