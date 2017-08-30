package fr.lsmbo.msda.recover.model.settings;

public class WrongChargeFilterSettings {

	private Boolean selectedFilter;

	public WrongChargeFilterSettings() {
	}

	public WrongChargeFilterSettings(Boolean selectedFilter) {
		super();
		this.selectedFilter = selectedFilter;
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
