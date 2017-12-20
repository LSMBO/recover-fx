package fr.lsmbo.msda.recover.model.settings;

import java.util.List;
import fr.lsmbo.msda.recover.model.IonReporter;

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

	private void initialize() {
		this.name = "Ions Reporter";
		this.description = ""; // TODO write a proper description
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
		String text = "selectedFilter: " + selectedFilter;
		for (IonReporter ion : ionList) {
			text += "\n" + ion.toString();
		}
		return text;
	}

}
