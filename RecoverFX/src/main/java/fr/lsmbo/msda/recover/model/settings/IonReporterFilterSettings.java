package fr.lsmbo.msda.recover.model.settings;

import java.util.List;
import fr.lsmbo.msda.recover.model.IonReporter;

public class IonReporterFilterSettings {

	private Boolean selectedFilter;
	private List<IonReporter> ionList;

	public IonReporterFilterSettings() {
	}

	public IonReporterFilterSettings(Boolean selectedFilter, List<IonReporter> ionList) {
		super();
		this.selectedFilter = selectedFilter;
		this.ionList = ionList;
	}

	public Boolean getSelectedFilter() {
		return selectedFilter;
	}

	public void setSelectedFilter(Boolean selectedFilter) {
		this.selectedFilter = selectedFilter;
	}

	public List<IonReporter> getIonList() {
		return ionList;
	}

	public void setIonList(List<IonReporter> ionList) {
		this.ionList = ionList;
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
