package fr.lsmbo.msda.recover.model.settings;

public class WrongChargeFilterSettings extends RecoverSetting {

	private Boolean selectedFilter;

	public WrongChargeFilterSettings() {
		this.initialize();
	}

	public WrongChargeFilterSettings(Boolean selectedFilter) {
		super();
		this.selectedFilter = selectedFilter;
		this.initialize();
	}
	
	private void initialize() {
		this.name = "Wrong charge";
		this.description = ""; // TODO write a proper description
	}

	public Boolean getSelectedFilter() {
		return selectedFilter;
	}

	public void setSelectedFilter(Boolean selectedFilter) {
		this.selectedFilter = selectedFilter;
	}
	
	@Override
	public String toString() {
		return "selectedFilter: "+selectedFilter;
	}

}
