/*
 * 
 */
package fr.lsmbo.msda.recover.gui.model;

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

	public String getFullDescription() {
		return "{" + propertyKey + "} " + name + ": " + regex;
	}

	public Integer getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public String getPropertyKey() {
		return propertyKey;
	}

	public String getRegex() {
		return regex;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPropertyKey(String key) {
		this.propertyKey = key;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	@Override
	public String toString() {
		return name;
	}
}
