package fr.lsmbo.msda.recover.model.settings;

public class PrecursorIntensityFilterSettings extends RecoverSetting {

	private Boolean selectedFilter;
	private String operator;
	private Integer intensity;

	public PrecursorIntensityFilterSettings() {
		this.initialize();
	}

	public PrecursorIntensityFilterSettings(Boolean selectedFilter, String operator, Integer intensity) {
		super();
		this.selectedFilter = selectedFilter;
		this.operator = operator;
		this.intensity = intensity;
		this.initialize();
	}
	
	private void initialize() {
		this.name = "Precursor intensity";
		this.description = ""; // TODO write a proper description
	}

	public Boolean getSelectedFilter() {
		return selectedFilter;
	}

	public void setSelectedFilter(Boolean selectedFilter) {
		this.selectedFilter = selectedFilter;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getIntensity() {
		return intensity;
	}

	public void setIntensity(Integer intensity) {
		this.intensity = intensity;
	}
	
	@Override
	public String toString() {
		return 
				"selectedFilter: "+selectedFilter+ "\n" + 
				"operator: "+operator+ "\n" + 
				"intensity: "+intensity;
	}

}
