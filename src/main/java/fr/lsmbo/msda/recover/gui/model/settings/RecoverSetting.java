/*
 * 
 */
package fr.lsmbo.msda.recover.gui.model.settings;

public class RecoverSetting {

	protected String name;
	protected String description;

	public RecoverSetting() {
	}

	public RecoverSetting(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

}
