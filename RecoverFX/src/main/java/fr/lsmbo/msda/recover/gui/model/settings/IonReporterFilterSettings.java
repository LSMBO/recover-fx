/*
 * 
 */
package fr.lsmbo.msda.recover.gui.model.settings;

import java.util.List;

import fr.lsmbo.msda.recover.gui.model.IonReporter;

public class IonReporterFilterSettings extends RecoverSetting {

	private Boolean selectedFilter;
	private List<IonReporter> ionList;

	public IonReporterFilterSettings() {
		this.initialize();
	}

	public IonReporterFilterSettings(Boolean selectedFilter, List<IonReporter> ionList) {
		super();
		this.selectedFilter = selectedFilter;
		this.ionList = ionList;
		this.initialize();
	}
	
	public List<IonReporter> getIonList() {
		return ionList;
	}

	public Boolean getSelectedFilter() {
		return selectedFilter;
	}

	private void initialize() {
		this.name = "Ions Reporter";
		this.description = ""; // TODO write a proper description
	}

	public void setIonList(List<IonReporter> ionList) {
		this.ionList = ionList;
	}

	public void setSelectedFilter(Boolean selectedFilter) {
		this.selectedFilter = selectedFilter;
	}
	
	@Override
	public String toString() {
		String text = "selectedFilter: "+selectedFilter;
		for(IonReporter ion: ionList) {
			text += "\n" + ion.toString();
		}
		return text;
	}

}
